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
            val relatedNumberPattern = Pattern.compile("((?<=Related: )(.+))")
            val resolvesNumberPattern = Pattern.compile("((?<=Resolves: )(.+))")
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

            matcher = relatedNumberPattern.matcher(oldCommitMessage)
            if (matcher.find()) {
                val relatedNumberMatches = matcher.group(1)
                val relatedNumbers = relatedNumberMatches.split(" ")
                var currentNumber = 0
                while (currentNumber < relatedNumbers.size) {
                    this.relatedNumber += relatedNumbers[currentNumber].replace("#", "")
                    currentNumber++
                    if (currentNumber < relatedNumbers.size) this.relatedNumber += " "
                }
            } else {
                this.relatedNumber = ""
            }
            matcher = resolvesNumberPattern.matcher(oldCommitMessage)
            if (matcher.find()) {
                val resolvesNumberMatches = matcher.group(1)
                val resolvesNumbers = resolvesNumberMatches.split(" ")
                var currentNumber = 0
                while (currentNumber < resolvesNumbers.size) {
                    this.resolvesNumber += resolvesNumbers[currentNumber].replace("#", "")
                    currentNumber++
                    if (currentNumber < resolvesNumbers.size) this.resolvesNumber += " "
                }
            } else {
                this.resolvesNumber = ""
            }
            matcher = releasesVersionPattern.matcher(oldCommitMessage)
            if (matcher.find()) {
                val releasesVersionMatches = matcher.group(1)
                val releasesVersions = releasesVersionMatches.split(" ")
                var currentVersion = 0
                while (currentVersion < releasesVersions.size) {
                    this.releasesVersion += releasesVersions[currentVersion].replace(",", "")
                    currentVersion++
                    if (currentVersion < releasesVersions.size) this.releasesVersion += " "
                }
            } else {
                this.releasesVersion = ""
            }
            matcher = dependencyPatchPattern.matcher(oldCommitMessage)
            if (matcher.find()) {
                val dependencyPatchMatches = matcher.group(1)
                val dependencyPatches = dependencyPatchMatches.split(" ")
                var currentPatch = 0
                while (currentPatch < dependencyPatches.size) {
                    this.dependencyPatch += dependencyPatches[currentPatch].replace(",", "")
                    currentPatch++
                    if (currentPatch < dependencyPatches.size) this.dependencyPatch += " "
                }
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

        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "Breaking-Changes: ", this.breakingChanges)
        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "Tasks: ", this.doneTasks)
        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "To-Do's: ", this.todoList)
        formattedCommitMessage.append(System.lineSeparator())
        formattedCommitMessage = addReferences(formattedCommitMessage, "Related: ", this.relatedNumber, "#", true)
        formattedCommitMessage = addReferences(formattedCommitMessage, "Resolves: ", this.resolvesNumber, "#", true)
        formattedCommitMessage = addReferences(formattedCommitMessage, "Releases: ", this.releasesVersion, ",", false)
        formattedCommitMessage = addReferences(formattedCommitMessage, "Depends: ", this.dependencyPatch, ",", false)

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

    private fun addReferences(formattedCommitMessage: StringBuilder, referenceTitle: String, referenceList: String, referenceDelimitor: String, addbefore: Boolean): StringBuilder {
        if (!isNotBlank(referenceList)) return formattedCommitMessage
        formattedCommitMessage.append(System.lineSeparator())
        formattedCommitMessage.append(referenceTitle)
        val referenceItems = referenceList.split(" ")
        var iterator = 0
        while (iterator < referenceItems.size) {
            if (addbefore) {
                formattedCommitMessage.append(referenceDelimitor)
                formattedCommitMessage.append(referenceItems[iterator])
            } else {
                formattedCommitMessage.append(referenceItems[iterator])
                if (iterator < referenceItems.size -1) formattedCommitMessage.append(referenceDelimitor)
            }
            iterator++
            if (iterator < referenceItems.size) formattedCommitMessage.append(" ")
        }
        return formattedCommitMessage
    }

    private fun addChangeNotes(formattedCommitMessage: StringBuilder, changeNotesTitle: String, changeNotes: String): StringBuilder {
        if(!isNotBlank(changeNotes)) return formattedCommitMessage
        val formattedChangeNotes = changeNotes.replace("- ", "* ")
        formattedCommitMessage
            .append(System.lineSeparator())
            .append(System.lineSeparator())
            .append("$changeNotesTitle\n$formattedChangeNotes")

        return formattedCommitMessage
    }
}