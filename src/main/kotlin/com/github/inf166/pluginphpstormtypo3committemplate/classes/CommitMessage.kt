package com.github.inf166.pluginphpstormtypo3committemplate.classes

import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.StringUtils.isNotBlank
import java.util.*
import java.util.regex.Pattern

class CommitMessage {
    private var changeType: ChangeType? = null
    var shortDescription: String? = null
        private set
    var longDescription: String
        private set
    var breakingChanges: String
        private set
    var workInProgress: String
        private set
    var closedIssues: String
        private set

    private constructor() {
        longDescription = ""
        breakingChanges = ""
        workInProgress = ""
        closedIssues = ""
    }

    constructor(
        changeType: ChangeType?, shortDescription: String?, longDescription: String,
        breakingChanges: String, workInProgress: String, closedIssues: String
    ) {
        this.changeType = changeType
        this.shortDescription = shortDescription
        this.longDescription = longDescription
        this.breakingChanges = breakingChanges
        this.workInProgress = workInProgress
        this.closedIssues = closedIssues
    }

    override fun toString(): String {
        val builder = StringBuilder()
        if (isNotBlank(breakingChanges)) {
            builder.append("[!!!]")
        }
        if (isNotBlank(workInProgress)) {
            builder.append("[WIP]")
        }
        builder.append(changeType?.label() ?: "")
        builder
            .append(" ")
            .append(shortDescription)
        if (isNotBlank(breakingChanges)) {
            val breaking = "Breaking Changes: \n$breakingChanges"
            builder
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append(breaking)
        }
        if (isNotBlank(longDescription)) {
            val tasks = "Tasks: \n$longDescription"
            builder
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append(tasks)
        }
        if (isNotBlank(workInProgress)) {
            val wip = "To-Do's: \n$workInProgress"
            builder
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append(wip)
        }
        if (isNotBlank(closedIssues)) {
            builder.append(System.lineSeparator())
            for (closedIssue in closedIssues.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                builder
                    .append(System.lineSeparator())
                    .append("refs: ")
                    .append(formatClosedIssue(closedIssue))
            }
        }
        return builder.toString()
    }

    private fun formatClosedIssue(closedIssue: String): String {
        val trimmed = closedIssue.trim { it <= ' ' }
        return (if (StringUtils.isNumeric(trimmed)) "#" else "") + trimmed
    }

    fun getChangeType(): ChangeType? {
        return changeType
    }

    companion object {
        val COMMIT_FIRST_LINE_FORMAT = Pattern.compile("[^([a-z]+)(\\((.+)\\))?] (.+)")
        val COMMIT_CLOSES_FORMAT = Pattern.compile("refs: (.+)")
        fun parse(message: String): CommitMessage {
            val commitMessage = CommitMessage()
            try {
                var matcher = COMMIT_FIRST_LINE_FORMAT.matcher(message)
                if (!matcher.find()) return commitMessage
                commitMessage.changeType = ChangeType.valueOf(matcher.group(1).uppercase(Locale.getDefault()))
                commitMessage.shortDescription = matcher.group(3)
                val strings = message.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                if (strings.size < 2) return commitMessage
                var pos = 1
                var stringBuilder: StringBuilder
                stringBuilder = StringBuilder()
                while (pos < strings.size) {
                    val lineString = strings[pos]
                    if (lineString.startsWith("Breaking Changes:") || lineString.startsWith("To-Do's:") || lineString.startsWith(
                            "refs:"
                        )
                    ) break
                    stringBuilder.append(lineString).append('\n')
                    pos++
                }
                commitMessage.longDescription = stringBuilder.toString().trim { it <= ' ' }
                stringBuilder = StringBuilder()
                while (pos < strings.size) {
                    val lineString = strings[pos]
                    if (lineString.startsWith("refs:")) break
                    stringBuilder.append(lineString).append('\n')
                    pos++
                }
                commitMessage.breakingChanges = stringBuilder.toString().trim { it <= ' ' }
                    .replace("Breaking Changes: ", "")
                matcher = COMMIT_CLOSES_FORMAT.matcher(message)
                stringBuilder = StringBuilder()
                while (matcher.find()) {
                    stringBuilder.append(matcher.group(1)).append(',')
                }
                if (stringBuilder.length > 0) stringBuilder.delete(stringBuilder.length - 1, stringBuilder.length)
                commitMessage.closedIssues = stringBuilder.toString()
            } catch (e: RuntimeException) {
            }
            return commitMessage
        }
    }
}