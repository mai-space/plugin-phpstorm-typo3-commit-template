package com.github.inf166.pluginphpstormtypo3committemplate.helper

import org.apache.commons.lang.StringUtils.isNotBlank
import java.util.regex.Pattern

class FormattedCommitMessage {

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
            val changeTypePattern = Pattern.compile("(?<=\\[)(${Constants.changeTypes.joinToString("|")})(?=])")
            val subjectPattern = Pattern.compile("[]+?] (.+)")
            val releasesVersionPattern = Pattern.compile("((?<=${Constants.LabelForRelease} )(.+))")
            val dependencyPatchPattern = Pattern.compile("((?<=${Constants.LabelForDepends} )(.+))")

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

            val relatedNumberRegex = Regex("((?<=${Constants.LabelForRelated} )(.+))")
            val relatedNumberMatches = relatedNumberRegex.findAll(oldCommitMessage)
            this.relatedNumber = relatedNumberMatches.map { it.groupValues[1] }
                .joinToString()
                .replace("${Constants.issueIndicator}", "")
                .replace(",", "")

            val resolvesNumberRegex = Regex("((?<=${Constants.LabelForResolves} )(.+))")
            val resolvesNumberMatches = resolvesNumberRegex.findAll(oldCommitMessage)
            this.resolvesNumber = resolvesNumberMatches.map { it.groupValues[1] }
                .joinToString()
                .replace("${Constants.issueIndicator}", "")
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
                if (changeLog.startsWith("${Constants.LabelForBreakingChanges} \n")) {
                    val stringBuilder: java.lang.StringBuilder = StringBuilder()
                    stringBuilder.append(changeLog).append('\n')
                    this.breakingChanges = stringBuilder.toString().trim().replace("- ", "${Constants.bulletPoint} ").replace("${Constants.LabelForBreakingChanges} \n", "")
                }
                if (changeLog.startsWith("${Constants.LabelForTasks} \n")) {
                    val stringBuilder: java.lang.StringBuilder = StringBuilder()
                    stringBuilder.append(changeLog).append('\n')
                    this.doneTasks = stringBuilder.toString().trim().replace("- ", "${Constants.bulletPoint} ").replace("${Constants.LabelForTasks} \n", "")
                }
                if (changeLog.startsWith("To-Do's: \n")) {
                    val stringBuilder: java.lang.StringBuilder = StringBuilder()
                    stringBuilder.append(changeLog).append('\n')
                    this.todoList = stringBuilder.toString().trim().replace("- ", "${Constants.bulletPoint} ").replace("${Constants.LabelForTodos} \n", "")
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

        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "${Constants.LabelForBreakingChanges} ", this.breakingChanges)
        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "${Constants.LabelForTasks} ", this.doneTasks)
        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "${Constants.LabelForTodos} ", this.todoList)

        val relatedNumberMatches: List<String> = this.relatedNumber.split(" ")
        relatedNumberMatches.forEach {
            if(isNotBlank(it)) {
                formattedCommitMessage.append(System.lineSeparator())
                formattedCommitMessage.append("${Constants.LabelForRelated} ${Constants.issueIndicator}$it")
            }
        }

        val resolvesNumberMatches: List<String> = this.resolvesNumber.split(" ")
        resolvesNumberMatches.forEach {
            if(isNotBlank(it)) {
                formattedCommitMessage.append(System.lineSeparator())
                formattedCommitMessage.append("${Constants.LabelForResolves} ${Constants.issueIndicator}$it")
            }
        }
        formattedCommitMessage.append(System.lineSeparator())
        formattedCommitMessage = addReferences(formattedCommitMessage, "${Constants.LabelForRelease} ", this.releasesVersion)
        formattedCommitMessage = addReferences(formattedCommitMessage, "${Constants.LabelForDepends} ", this.dependencyPatch)

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
        val formattedChangeNotes = changeNotes.replace("- ", "${Constants.bulletPoint} ")
        formattedCommitMessage
            .append(System.lineSeparator())
            .append("$changeNotesTitle\n$formattedChangeNotes")
            .append(System.lineSeparator())

        return formattedCommitMessage
    }
}