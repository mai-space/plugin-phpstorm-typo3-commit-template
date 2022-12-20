package com.github.inf166.pluginphpstormtypo3committemplate.helper

import org.apache.commons.lang.StringUtils.isNotBlank
import org.apache.commons.lang.StringUtils.startsWith
import java.util.*

class FormattedCommitMessage {
    private var changeType: String
    private var subjectLine: String
    private var doneTasks: String
    private var breakingChanges: String
    private var workInProgress: String
    private var closedIssue: String

    private constructor() {
        this.changeType = ""
        this.subjectLine = ""
        this.doneTasks = ""
        this.breakingChanges = ""
        this.workInProgress = ""
        this.closedIssue = ""
    }

    constructor(
        changeType: Any?, subjectLine: String, doneTasks: String,
        breakingChanges: String, workInProgress: String, closedIssue: String
    ) {
        this.changeType = changeType.toString()
        this.subjectLine = subjectLine
        this.doneTasks = doneTasks
        this.breakingChanges = breakingChanges
        this.workInProgress = workInProgress
        this.closedIssue = closedIssue
    }

    fun getFormattedCommitMessage(): String {
        var formattedCommitMessage = StringBuilder()

        formattedCommitMessage = setMessageFlags(formattedCommitMessage, this.changeType, this.breakingChanges, this.workInProgress)

        formattedCommitMessage
            .append(" ")
            .append(this.subjectLine)

        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "Breaking-Changes: ", this.breakingChanges)
        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "Tasks: ", this.doneTasks)
        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "To-Do's: ", this.workInProgress)
        formattedCommitMessage = addChangeNotes(formattedCommitMessage, "refs: ", "#"+this.closedIssue)

        return formattedCommitMessage.toString()
    }

    private fun setMessageFlags(formattedCommitMessage: StringBuilder, changeType: String, breakingChanges: String, workInProgress: String): StringBuilder {
        if (isNotBlank(breakingChanges)) {
            formattedCommitMessage.append("[!!!]")
        }
        if (isNotBlank(workInProgress)) {
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