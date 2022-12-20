package com.github.inf166.pluginphpstormtypo3committemplate.components

import com.github.inf166.pluginphpstormtypo3committemplate.helper.FormattedCommitMessage
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.Gray
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.KeyboardFocusManager
import javax.swing.*
import javax.swing.border.Border

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
        val border: Border = BorderFactory.createLineBorder(Gray._107, 1)
        val componentBorder = BorderFactory.createCompoundBorder(
            border,
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
        )

        container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.PAGE_AXIS)

        subjectLine = JPanel(BorderLayout(3,0))

        commitTypeRow = JPanel(BorderLayout(0,3))
        val commitTypeLabel = JLabel("Type of Commit")
        commitType = ComboBox<String>()
        commitType.addItem("FEATURE")
        commitType.addItem("TASK")
        commitType.addItem("BUGFIX")
        commitType.addItem("SECURITY")
        commitType.addItem("DOCS")
        commitType.maximumSize = commitType.preferredSize
        commitType.border = componentBorder
        commitTypeRow.add(commitTypeLabel, BorderLayout.NORTH)
        commitTypeRow.add(commitType, BorderLayout.SOUTH)
        subjectLine.add(commitTypeRow, BorderLayout.WEST)

        subjectRow = JPanel(BorderLayout(0,3))
        val commitSubjectLabel = JLabel("Subject of Commit")
        commitSubject = JTextField(34)
        commitSubject.border = componentBorder
        subjectRow.add(commitSubjectLabel, BorderLayout.NORTH)
        subjectRow.add(commitSubject, BorderLayout.SOUTH)
        subjectLine.add(subjectRow, BorderLayout.CENTER)

        container.add(subjectLine)
        container.add(Box.createRigidArea(Dimension(0,8)))

        taskRow = JPanel(BorderLayout(0,3))
        val doneTasksLabel = JLabel("Done Tasks: ")
        doneTasks = JTextArea(5,34)
        doneTasks.border = componentBorder
        doneTasks.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null)
        doneTasks.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null)
        taskRow.add(doneTasksLabel, BorderLayout.NORTH)
        taskRow.add(doneTasks, BorderLayout.SOUTH)

        container.add(taskRow)
        container.add(Box.createRigidArea(Dimension(0,8)))

        breakingRow = JPanel(BorderLayout(0,3))
        val breakingChangesLabel = JLabel("Breaking-Changes: ")
        breakingChanges = JTextArea(5,34)
        breakingChanges.border = componentBorder
        breakingChanges.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null)
        breakingChanges.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null)
        breakingRow.add(breakingChangesLabel, BorderLayout.NORTH)
        breakingRow.add(breakingChanges, BorderLayout.SOUTH)

        container.add(breakingRow)
        container.add(Box.createRigidArea(Dimension(0,8)))

        todoRow = JPanel(BorderLayout(0,3))
        val todoListLabel = JLabel("To-Do's: ")
        todoList = JTextArea(5,34)
        todoList.border = componentBorder
        todoList.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null)
        todoList.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null)
        todoRow.add(todoListLabel, BorderLayout.NORTH)
        todoRow.add(todoList, BorderLayout.SOUTH)

        container.add(todoRow)
        container.add(Box.createRigidArea(Dimension(0,8)))

        referenceRow = JPanel(BorderLayout(3,0))
        val commitIssueLabel = JLabel("Issue No.: ")
        issueNumber = JTextField(38)
        issueNumber.border = componentBorder
        referenceRow.add(commitIssueLabel, BorderLayout.WEST)
        referenceRow.add(issueNumber, BorderLayout.CENTER)

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