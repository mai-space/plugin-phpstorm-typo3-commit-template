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
                    val subject = callOllama(settings.ollamaPromptSubject, gitDiff)

                    // Generate body (tasks)
                    indicator.text = "Generating task list..."
                    indicator.fraction = 0.6
                    val tasks = callOllama(settings.ollamaPromptBody, gitDiff)

                    indicator.text = "Completed"
                    indicator.fraction = 1.0

                    callback(GenerationResult(
                        subject = subject.trim().take(50),
                        tasks = formatAsBulletList(tasks)
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
        connection.connectTimeout = 5000
        connection.readTimeout = 60000

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
            throw RuntimeException("Ollama API returned error code: $responseCode")
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
}
