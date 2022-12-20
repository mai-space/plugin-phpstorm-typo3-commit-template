package com.github.inf166.pluginphpstormtypo3committemplate.helper

import org.apache.commons.lang.StringUtils.isNotBlank
import java.util.*
import com.github.inf166.pluginphpstormtypo3committemplate.components.TypeRadioButtonGroup

class FormattedCommitMessage {
    private var changeType: TypeRadioButtonGroup? = null
    private var subjectLine: String
    private var doneTasks: String
    private var breakingChanges: String
    private var workInProgress: String
    private var closedIssue: String

    private constructor() {
        this.subjectLine = ""
        this.doneTasks = ""
        this.breakingChanges = ""
        this.workInProgress = ""
        this.closedIssue = ""
    }

    constructor(
        changeType: TypeRadioButtonGroup?, subjectLine: String, doneTasks: String,
        breakingChanges: String, workInProgress: String, closedIssue: String
    ) {
        this.changeType = changeType
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

        val map: MutableMap<String, String> = HashMap()
        map["Breaking-Changes: "] = this.breakingChanges
        map["Tasks: "] = this.doneTasks
        map["To-Do's: "] = this.workInProgress
        map["refs: "] = "#$this.closedIssue"
        val changeNotes: Array<Pair<String, String>> = map.entries.map { Pair(it.key, it.value) } .toTypedArray()

        formattedCommitMessage = addChangeNotes(formattedCommitMessage, changeNotes)

        return formattedCommitMessage.toString()
    }

    private fun setMessageFlags(formattedCommitMessage: StringBuilder, changeType: TypeRadioButtonGroup?, breakingChanges: String, workInProgress: String): StringBuilder {
        if (isNotBlank(breakingChanges)) {
            formattedCommitMessage.append("[!!!]")
        }
        if (isNotBlank(workInProgress)) {
            formattedCommitMessage.append("[WIP]")
        }
        if (changeType != null) {
            formattedCommitMessage.append(changeType.label())
        }

        return formattedCommitMessage
    }

    private fun addChangeNotes(formattedCommitMessage: StringBuilder, changeNotes: Array<Pair<String, String>>): StringBuilder {
        changeNotes.forEach {
            if(isNotBlank(it.second)) {
                formattedCommitMessage
                    .append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append("$it.first\n$it.second")
            }
        }

        return formattedCommitMessage
    }
}