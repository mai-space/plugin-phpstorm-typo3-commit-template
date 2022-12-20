package com.github.inf166.pluginphpstormtypo3committemplate.components

import com.github.inf166.pluginphpstormtypo3committemplate.helper.FormattedCommitMessage
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.*

public final class Dialog(project: Project?): DialogWrapper(project) {
    private lateinit var container: JPanel
    private lateinit var subjectLine: JPanel
    private lateinit var commitTypeRow: JPanel
    private lateinit var subjectRow: JPanel
    private lateinit var taskRow: JPanel
    private lateinit var breakingRow: JPanel
    private lateinit var todoRow: JPanel
    private lateinit var referenceRow: JPanel

    private lateinit var commitType: ComboBox<String>

    private lateinit var commitSubject: JTextField
    private lateinit var issueNumber: JTextField

    private lateinit var doneTasks: JTextArea
    private lateinit var breakingChanges: JTextArea
    private lateinit var todoList: JTextArea

    init {
        title = "Commit Message Template by TYPO3 Guidelines"
        setOKButtonText("Apply")
        init()
    }
    override fun createCenterPanel(): JComponent {
        container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.PAGE_AXIS)

        subjectLine = JPanel()
        subjectLine.layout = BoxLayout(subjectLine, BoxLayout.LINE_AXIS)

        commitTypeRow = JPanel(BorderLayout())
        val commitTypeLabel = JLabel("Type of Commit")
        commitType = ComboBox<String>()
        commitType.addItem("FEATURE")
        commitType.addItem("TASK")
        commitType.addItem("BUGFIX")
        commitType.addItem("SECURITY")
        commitType.addItem("DOCS")
        commitTypeRow.add(commitTypeLabel, BorderLayout.NORTH)
        commitTypeRow.add(commitType, BorderLayout.SOUTH)
        subjectLine.add(commitTypeRow)

        subjectRow = JPanel(BorderLayout())
        val commitSubjectLabel = JLabel("Subject of Commit")
        commitSubject = JTextField()
        subjectRow.add(commitSubjectLabel, BorderLayout.NORTH)
        subjectRow.add(commitSubject, BorderLayout.SOUTH)
        subjectLine.add(subjectRow)

        container.add(subjectLine)

        taskRow = JPanel(BorderLayout())
        val doneTasksLabel = JLabel("Done Tasks: ")
        doneTasks = JTextArea()
        taskRow.add(doneTasksLabel, BorderLayout.NORTH)
        taskRow.add(doneTasks, BorderLayout.SOUTH)

        container.add(taskRow)

        breakingRow = JPanel(BorderLayout())
        val breakingChangesLabel = JLabel("Breaking-Changes: ")
        breakingChanges = JTextArea()
        breakingRow.add(breakingChangesLabel, BorderLayout.NORTH)
        breakingRow.add(breakingChanges, BorderLayout.SOUTH)

        container.add(breakingRow)

        todoRow = JPanel(BorderLayout())
        val todoListLabel = JLabel("To-Do's: ")
        todoList = JTextArea()
        todoRow.add(todoListLabel, BorderLayout.NORTH)
        todoRow.add(todoList, BorderLayout.SOUTH)

        container.add(todoRow)

        referenceRow = JPanel(FlowLayout())
        val commitIssueLabel = JLabel("Issue No.:")
        issueNumber = JTextField()
        referenceRow.add(commitIssueLabel)
        referenceRow.add(issueNumber)

        container.add(referenceRow)

        return container
    }

    fun getCommitMessage(): FormattedCommitMessage {
        return FormattedCommitMessage(
            commitType.getItemAt(commitType.selectedIndex).toString(),
            commitSubject.text.trim { it <= ' ' },
            doneTasks.text.trim { it <= ' ' },
            breakingChanges.text.trim { it <= ' ' },
            todoList.text.trim { it <= ' ' },
            issueNumber.text.trim { it <= ' ' }
        )
    }
}