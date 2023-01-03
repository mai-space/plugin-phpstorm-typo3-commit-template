package com.github.inf166.pluginphpstormtypo3committemplate.helper

import org.apache.commons.lang.StringUtils.isNotBlank
import java.util.regex.Pattern

class FormattedCommitMessage {
   val changeTypes = arrayOf(
        "FEATURE",
        "TASK",
        "BUGFIX",
        "SECURITY",
        "DOCS",
    )

    var changeType: String
    var subjectLine: String
    var doneTasks: String
    var breakingChanges: String
    var todoList: String
    var relatedNumber: String
    var resolvesNumber: String
    var releasesVersion: String
    var dependencyPatch: String

    constructor() {
        this.changeType = ""
        this.subjectLine = ""
        this.doneTasks = ""
        this.breakingChanges = ""
        this.todoList = ""
        this.relatedNumber = ""
        this.resolvesNumber = ""
        this.releasesVersion = ""
        this.dependencyPatch = ""
    }

    constructor(oldCommitMessage: String) {
        this.changeType = ""
        this.subjectLine = ""
        this.doneTasks = oldCommitMessage
        this.breakingChanges = ""
        this.todoList = ""
        this.relatedNumber = ""
        this.resolvesNumber = ""
        this.releasesVersion = ""
        this.dependencyPatch = ""
        try {
            val changeTypePattern = Pattern.compile("(?<=\\[)(FEATURE|TASK|BUGFIX|SECURITY|DOCS)(?=])")
            val subjectPattern = Pattern.compile("[]+?] (.+)")
            val releasesVersionPattern = Pattern.compile("((?<=Releases: )(.+))")
            val dependencyPatchPattern = Pattern.compile("((?<=Depends: )(.+))")

            var matcher = changeTypePattern.matcher(oldCommitMessage)
            if (matcher.find()) {
                this.changeType = matcher.group(1)
            } else {
                this.changeType = ""
            }

            matcher = subjectPattern.matcher(oldCommitMessage)
            if (matcher.find()) {
                this.subjectLine = matcher.group(1)
            } else {
                this.subjectLine = ""
            }

            val relatedNumberRegex = Regex("((?<=Related: )(.+))")
            val relatedNumberMatches = relatedNumberRegex.findAll(oldCommitMessage)
            this.relatedNumber = relatedNumberMatches.map { it.groupValues[1] }
                .joinToString()
                .replace("#", "")
                .replace(",", "")

            val resolvesNumberRegex = Regex("((?<=Resolves: )(.+))")
            val resolvesNumberMatches = resolvesNumberRegex.findAll(oldCommitMessage)
            this.resolvesNumber = resolvesNumberMatches.map { it.groupValues[1] }
                .joinToString()
                .replace("#", "")
                .replace(",", "")

            matcher = releasesVersionPattern.matcher(oldCommitMessage)
            if (matcher.find()) {
                val releasesVersionMatches = matcher.group(1)
                this.releasesVersion = releasesVersionMatches
            } else {
                this.releasesVersion = ""
            }

            matcher = dependencyPatchPattern.matcher(oldCommitMessage)
            if (matcher.find()) {
                val dependencyPatchMatches = matcher.group(1)
                this.dependencyPatch = dependencyPatchMatches
            } else {
                this.dependencyPatch = ""
            }

            val changeLogs = oldCommitMessage.split("\n\n")
            this.doneTasks = ""
            for (changeLog in changeLogs) {
                if (changeLog.startsWith("Breaking-Changes: \n")) {
                    val stringBuilder: java.lang.StringBuilder = StringBuilder()
                    stringBuilder.append(changeLog).append('\n')
                    this.breakingChanges = stringBuilder.toString().trim().replace("- ", "* ").replace("Breaking-Changes: \n", "")
                }
                if (changeLog.startsWith("Tasks: \n")) {
                    val stringBuilder: java.lang.StringBuilder = StringBuilder()
                    stringBuilder.append(changeLog).append('\n')
                    this.doneTasks = stringBuilder.toString().trim().replace("- ", "* ").replace("Tasks: \n", "")
                }
                if (changeLog.startsWith("To-Do's: \n")) {
                    val stringBuilder: java.lang.StringBuilder = StringBuilder()
                    stringBuilder.append(changeLog).append('\n')
                    this.todoList = stringBuilder.toString().trim().replace("- ", "* ").replace("To-Do's: \n", "")
                }
            }

        } catch (e: RuntimeException) {
            println(e)
        }

    }

    constructor(
        changeType: Any?, subjectLine: String, doneTasks: String,
        breakingChanges: String, todoList: String, relatedNumber: String,
        resolvesNumber: String, releasesVersion: String, dependencyPatch: String,
    ) {
        this.changeType = changeType.toString()
        this.subjectLine = subjectLine
        this.doneTasks = doneTasks
        this.breakingChanges = breakingChanges
        this.todoList = todoList
        this.relatedNumber = relatedNumber
        this.resolvesNumber = resolvesNumber
        this.releasesVersion = releasesVersion
        this.dependencyPatch = dependencyPatch
    }

    fun getFormattedCommitMessage(): String {
        var formattedCommitMessage = StringBuilder()

        formattedCommitMessage = setMessageFlags(formattedCommitMessage, this.changeType, this.breakingChanges, this.todoList)

        formattedCommitMessage
            .append(" ")
            .append(this.subjectLine)
        formattedCommitMessage.append(System.lineSeparator())

        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "Breaking-Changes: ", this.breakingChanges)
        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "Tasks: ", this.doneTasks)
        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "To-Do's: ", this.todoList)

        val relatedNumberMatches: List<String> = this.relatedNumber.split(" ")
        relatedNumberMatches.forEach {
            if(isNotBlank(it)) {
                formattedCommitMessage.append(System.lineSeparator())
                formattedCommitMessage.append("Related: #$it")
            }
        }

        val resolvesNumberMatches: List<String> = this.resolvesNumber.split(" ")
        resolvesNumberMatches.forEach {
            if(isNotBlank(it)) {
                formattedCommitMessage.append(System.lineSeparator())
                formattedCommitMessage.append("Resolves: #$it")
            }
        }
        formattedCommitMessage.append(System.lineSeparator())
        formattedCommitMessage = addReferences(formattedCommitMessage, "Releases: ", this.releasesVersion)
        formattedCommitMessage = addReferences(formattedCommitMessage, "Depends: ", this.dependencyPatch)

        return formattedCommitMessage.toString()
    }

    private fun setMessageFlags(formattedCommitMessage: StringBuilder, changeType: String, breakingChanges: String, todoList: String): StringBuilder {
        if (isNotBlank(breakingChanges)) {
            formattedCommitMessage.append("[!!!]")
        }
        if (isNotBlank(todoList)) {
            formattedCommitMessage.append("[WIP]")
        }
        formattedCommitMessage.append("[$changeType]")

        return formattedCommitMessage
    }

    private fun addReferences(formattedCommitMessage: StringBuilder, referenceTitle: String, referenceList: String): StringBuilder {
        if (!isNotBlank(referenceList)) return formattedCommitMessage
        formattedCommitMessage.append(referenceTitle)
        formattedCommitMessage.append(referenceList)
        formattedCommitMessage.append(System.lineSeparator())
        return formattedCommitMessage
    }

    private fun addChangeNotes(formattedCommitMessage: StringBuilder, changeNotesTitle: String, changeNotes: String): StringBuilder {
        if(!isNotBlank(changeNotes)) return formattedCommitMessage
        val formattedChangeNotes = changeNotes.replace("- ", "* ")
        formattedCommitMessage
            .append(System.lineSeparator())
            .append("$changeNotesTitle\n$formattedChangeNotes")
            .append(System.lineSeparator())

        return formattedCommitMessage
    }
}