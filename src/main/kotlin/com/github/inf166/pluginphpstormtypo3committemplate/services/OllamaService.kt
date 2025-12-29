package com.github.inf166.pluginphpstormtypo3committemplate.services

import com.github.inf166.pluginphpstormtypo3committemplate.settings.PersistentSettings
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class OllamaService(private val project: Project?) {
    private val logger = Logger.getInstance(OllamaService::class.java)
    private val settings = PersistentSettings.instance
    private val gson = Gson()

    companion object {
        private const val MAX_SUBJECT_LENGTH = 50
        private const val CONNECT_TIMEOUT_MS = 5000
        private const val READ_TIMEOUT_MS = 60000

        fun removeMarkdownBolding(text: String): String {
            return text.replace(Regex("\\*\\*(.*?)\\*\\*"), "$1")
                .replace(Regex("__(.*?)__"), "$1")
        }
    }

    data class GenerationResult(
        val subject: String = "",
        val tasks: String = "",
        val breakingChanges: String = "",
        val todos: String = "",
        val error: String? = null
    )

    fun generateCommitMessage(gitDiff: String, callback: (GenerationResult) -> Unit) {
        ProgressManager.getInstance().run(object : Task.Backgroundable(project, "Generating Commit Message with AI...", true) {
            override fun run(indicator: ProgressIndicator) {
                try {
                    indicator.text = "Connecting to Ollama..."
                    indicator.fraction = 0.1

                    // Generate subject line
                    indicator.text = "Generating subject line..."
                    indicator.fraction = 0.3
                    val rawSubject = callOllama(settings.ollamaPromptSubject, gitDiff)
                    val cleanedSubject = cleanSubjectLine(rawSubject)

                    // Generate body (tasks)
                    indicator.text = "Generating task list..."
                    indicator.fraction = 0.6
                    val rawTasks = callOllama(settings.ollamaPromptBody, gitDiff)
                    val cleanedTasks = cleanAndFormatBody(rawTasks)

                    indicator.text = "Completed"
                    indicator.fraction = 1.0

                    callback(GenerationResult(
                        subject = cleanedSubject.take(MAX_SUBJECT_LENGTH),
                        tasks = cleanedTasks
                    ))
                } catch (e: Exception) {
                    logger.error("Failed to generate commit message with Ollama", e)
                    callback(GenerationResult(error = "Failed to connect to Ollama: ${e.message}"))
                }
            }
        })
    }

    private fun callOllama(prompt: String, context: String): String {
        val url = URL("${settings.ollamaUrl}/api/generate")
        val connection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true
        connection.connectTimeout = CONNECT_TIMEOUT_MS
        connection.readTimeout = READ_TIMEOUT_MS

        val requestBody = JsonObject().apply {
            addProperty("model", settings.ollamaModel)
            addProperty("prompt", "$prompt\n\n$context")
            addProperty("stream", false)
        }

        OutputStreamWriter(connection.outputStream).use { writer ->
            writer.write(gson.toJson(requestBody))
            writer.flush()
        }

        val responseCode = connection.responseCode
        if (responseCode != HttpURLConnection.HTTP_OK) {
            val errorBody = try {
                BufferedReader(InputStreamReader(connection.errorStream)).use { it.readText() }
            } catch (e: Exception) {
                "Unable to read error details"
            }
            throw RuntimeException("Ollama API returned error code $responseCode: $errorBody")
        }

        val response = BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
            reader.readText()
        }

        val jsonResponse = gson.fromJson(response, JsonObject::class.java)
        return jsonResponse.get("response")?.asString ?: ""
    }

    private fun formatAsBulletList(text: String): String {
        val lines = text.split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { line ->
                // Remove existing bullet points or numbers
                val cleaned = line.removePrefix("*").removePrefix("-").removePrefix("•")
                    .replace(Regex("^\\d+\\.\\s*"), "")
                    .trim()
                if (cleaned.isNotEmpty()) {
                    "* $cleaned"
                } else {
                    null
                }
            }
            .filterNotNull()

        return lines.joinToString("\n")
    }

    private fun cleanSubjectLine(rawSubject: String): String {
        var cleaned = rawSubject.trim()
        
        // Remove common LLM artifacts
        cleaned = cleaned.removePrefix("Subject:").removePrefix("subject:")
            .removePrefix("Subject line:").removePrefix("subject line:")
            .removePrefix("Commit message:").removePrefix("commit message:")
            .trim()
        
        // Remove quotes
        cleaned = cleaned.removeSurrounding("\"").removeSurrounding("'").trim()
        
        // Remove any prefixes like "Here is...", "The subject...", etc.
        val unwantedPrefixes = listOf(
            "here is", "here's", "the subject", "commit message", 
            "subject line", "i suggest", "i would suggest", "this commit"
        )
        for (prefix in unwantedPrefixes) {
            if (cleaned.lowercase().startsWith(prefix)) {
                cleaned = cleaned.substring(prefix.length).trim()
                    .removePrefix(":").removePrefix("-").trim()
            }
        }
        
        // Take first line only
        cleaned = cleaned.lines().firstOrNull { it.trim().isNotEmpty() } ?: cleaned
        
        // Remove markdown bolding
        cleaned = removeMarkdownBolding(cleaned)

        // Remove trailing punctuation if present
        cleaned = cleaned.trimEnd('.', '!', '?')
        
        return cleaned
    }

    private fun cleanAndFormatBody(rawBody: String): String {
        var cleaned = rawBody.trim()
        
        // Remove markdown bolding
        cleaned = removeMarkdownBolding(cleaned)

        // Remove common LLM wrapper text
        val unwantedPhrases = listOf(
            "here are the bullet points",
            "here are the changes",
            "the changes are",
            "i've generated",
            "here is the list",
            "below are",
            "the following changes"
        )
        
        val lines = cleaned.lines().toMutableList()
        
        // Remove lines that contain unwanted phrases
        val filteredLines = lines.filter { line ->
            val lowerLine = line.trim().lowercase()
            !unwantedPhrases.any { phrase -> lowerLine.contains(phrase) }
        }
        
        // Format as bullet list
        return formatAsBulletList(filteredLines.joinToString("\n"))
    }
}
