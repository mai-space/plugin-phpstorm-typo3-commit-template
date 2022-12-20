package com.github.inf166.pluginphpstormtypo3committemplate.helper

import org.apache.commons.lang.StringUtils.isNotBlank
import org.apache.commons.lang.StringUtils.startsWith
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
    var issueNumber: String

    constructor() {
        this.changeType = ""
        this.subjectLine = ""
        this.doneTasks = ""
        this.breakingChanges = ""
        this.todoList = ""
        this.issueNumber = ""
    }

    constructor(oldCommitMessage: String) {
        this.changeType = ""
        this.subjectLine = ""
        this.doneTasks = oldCommitMessage
        this.breakingChanges = ""
        this.todoList = ""
        this.issueNumber = ""
        try {
            var possibleTypes: String = ""
            var i = 0
            while (i < this.changeTypes.size) {
                possibleTypes += changeType
                i++
                if (i < this.changeTypes.size) possibleTypes += "|"
            }
            var changeTypePattern = Pattern.compile("(?<=\\[)(FEATURE|TASK|BUGFIX|SECURITY|DOCS)(?=\\])")
            var subjectPattern = Pattern.compile("[^([a-z]+)(\\((.+)\\))?] (.+)")
            var issueNumberPattern = Pattern.compile("((?<=refs: \\n#)(.+))")

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

            matcher = issueNumberPattern.matcher(oldCommitMessage)
            if (matcher.find()) {
                this.issueNumber = matcher.group(1)
            } else {
                this.issueNumber = ""
            }

            var changeLogs = oldCommitMessage.split("\n\n")
            this.doneTasks = ""
            for (changeLog in changeLogs) {
                if (changeLog.startsWith("Breaking-Changes: \n")) {
                    var stringBuilder: java.lang.StringBuilder = StringBuilder()
                    stringBuilder.append(changeLog).append('\n');
                    this.breakingChanges = stringBuilder.toString().trim().replace("Breaking-Changes: \n", "")
                }
                if (changeLog.startsWith("Tasks: \n")) {
                    var stringBuilder: java.lang.StringBuilder = StringBuilder()
                    stringBuilder.append(changeLog).append('\n');
                    this.doneTasks = stringBuilder.toString().trim().replace("Tasks: \n", "")
                }
                if (changeLog.startsWith("To-Do's: \n")) {
                    var stringBuilder: java.lang.StringBuilder = StringBuilder()
                    stringBuilder.append(changeLog).append('\n');
                    this.todoList = stringBuilder.toString().trim().replace("To-Do's: \n", "")
                }
            }

        } catch (e: RuntimeException) {
            println(e)
        }

    }

    constructor(
        changeType: Any?, subjectLine: String, doneTasks: String,
        breakingChanges: String, todoList: String, issueNumber: String
    ) {
        this.changeType = changeType.toString()
        this.subjectLine = subjectLine
        this.doneTasks = doneTasks
        this.breakingChanges = breakingChanges
        this.todoList = todoList
        this.issueNumber = issueNumber
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
        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "refs: ", "#"+this.issueNumber)

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

    private fun addChangeNotes(formattedCommitMessage: StringBuilder, changeNotesTitle: String, changeNotes: String): StringBuilder {
        if(startsWith(changeNotes,"#") && changeNotes.length == 1 ) return formattedCommitMessage
        if(isNotBlank(changeNotes)) {
            formattedCommitMessage
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("$changeNotesTitle\n$changeNotes")
        }

        return formattedCommitMessage
    }
}