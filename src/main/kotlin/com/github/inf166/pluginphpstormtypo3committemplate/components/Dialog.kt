package com.github.inf166.pluginphpstormtypo3committemplate.components

import com.github.inf166.pluginphpstormtypo3committemplate.helper.FormattedCommitMessage
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.Gray
import com.intellij.ui.components.JBScrollPane
import org.jdesktop.swingx.prompt.PromptSupport
import java.awt.*
import javax.swing.*
import javax.swing.border.Border

class Dialog(project: Project?, oldCommitMessage: FormattedCommitMessage?): DialogWrapper(project) {

    private var oldCommitMessage: FormattedCommitMessage?

    private lateinit var container: JPanel
    private lateinit var subjectLine: JPanel
    private lateinit var commitTypeRow: JPanel
    private lateinit var subjectRow: JPanel
    private lateinit var taskRow: JPanel
    private lateinit var breakingRow: JPanel
    private lateinit var todoRow: JPanel
    private lateinit var relatedRow: JPanel
    private lateinit var resolvesRow: JPanel
    private lateinit var releaseRow: JPanel
    private lateinit var dependencyRow: JPanel

    private lateinit var taskScrollPane: JBScrollPane
    private lateinit var breakingScrollPane: JBScrollPane
    private lateinit var todoScrollPane: JBScrollPane

    private lateinit var commitType: ComboBox<String>

    private lateinit var commitSubject: JTextField
    private lateinit var relatedNumber: JTextField
    private lateinit var resolvesNumber: JTextField
    private lateinit var releasesVersion: JTextField
    private lateinit var dependencyPatch: JTextField

    private lateinit var doneTasks: JTextArea
    private lateinit var breakingChanges: JTextArea
    private lateinit var todoList: JTextArea

    init {
        this.oldCommitMessage = oldCommitMessage
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
        for (changeType in this.oldCommitMessage?.changeTypes!!) {
            commitType.addItem(changeType)
        }
        commitType.maximumSize = commitType.preferredSize
        commitType.toolTipText = "Select the Type of your Commit"
        commitType.border = componentBorder
        commitType.selectedItem = this.oldCommitMessage?.changeType ?: ""
        commitTypeRow.add(commitTypeLabel, BorderLayout.NORTH)
        commitTypeRow.add(commitType, BorderLayout.SOUTH)
        subjectLine.add(commitTypeRow, BorderLayout.WEST)

        subjectRow = JPanel(BorderLayout(0,3))
        val commitSubjectLabel = JLabel("Subject of Commit")
        commitSubject = JTextField(34)
        PromptSupport.setPrompt("A short description", commitSubject)
        PromptSupport.setFontStyle(Font.ITALIC, commitSubject)
        PromptSupport.setForeground(Gray._120, commitSubject)
        commitSubject.toolTipText = "Write a brief summary of what the change does now"
        commitSubject.border = componentBorder
        commitSubject.text = this.oldCommitMessage?.subjectLine ?: ""
        subjectRow.add(commitSubjectLabel, BorderLayout.NORTH)
        subjectRow.add(commitSubject, BorderLayout.SOUTH)
        subjectLine.add(subjectRow, BorderLayout.CENTER)

        container.add(subjectLine)
        container.add(Box.createRigidArea(Dimension(0,8)))

        taskRow = JPanel(BorderLayout(0,0))
        val doneTasksLabel = JLabel("Done Tasks: ")
        taskRow.add(doneTasksLabel, BorderLayout.WEST)
        doneTasks = JTextArea(5,34)
        PromptSupport.setPrompt("* Added something", doneTasks)
        PromptSupport.setFontStyle(Font.ITALIC, doneTasks)
        PromptSupport.setForeground(Gray._120, doneTasks)
        doneTasks.toolTipText = "List the things you have done"
        doneTasks.minimumSize = Dimension(507, 107)
        doneTasks.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null)
        doneTasks.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null)
        doneTasks.text = this.oldCommitMessage?.doneTasks ?: ""
        taskScrollPane = JBScrollPane(doneTasks)
        taskScrollPane.border = componentBorder
        container.add(taskRow)
        container.add(Box.createRigidArea(Dimension(0,3)))
        container.add(taskScrollPane)
        container.add(Box.createRigidArea(Dimension(0,8)))

        breakingRow = JPanel(BorderLayout(0,0))
        val breakingChangesLabel = JLabel("Breaking-Changes: ")
        breakingRow.add(breakingChangesLabel, BorderLayout.WEST)
        breakingChanges = JTextArea(5,34)
        PromptSupport.setPrompt("* Done something dangerous", breakingChanges)
        PromptSupport.setFontStyle(Font.ITALIC, breakingChanges)
        PromptSupport.setForeground(Gray._120, breakingChanges)
        breakingChanges.toolTipText = "List things you have done that could result in issues"
        breakingChanges.minimumSize = Dimension(507, 107)
        breakingChanges.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null)
        breakingChanges.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null)
        breakingChanges.text = this.oldCommitMessage?.breakingChanges ?: ""
        breakingScrollPane = JBScrollPane(breakingChanges)
        breakingScrollPane.border = componentBorder
        container.add(breakingRow)
        container.add(Box.createRigidArea(Dimension(0,3)))
        container.add(breakingScrollPane)
        container.add(Box.createRigidArea(Dimension(0,8)))

        todoRow = JPanel(BorderLayout(0,0))
        val todoListLabel = JLabel("To-Do's: ")
        todoRow.add(todoListLabel, BorderLayout.WEST)
        todoList = JTextArea(5,34)
        PromptSupport.setPrompt("* Need to do this", todoList)
        PromptSupport.setFontStyle(Font.ITALIC, todoList)
        PromptSupport.setForeground(Gray._120, todoList)
        todoList.toolTipText = "List open tasks that have to be done"
        todoList.minimumSize = Dimension(507, 107)
        todoList.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null)
        todoList.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null)
        todoList.text = this.oldCommitMessage?.todoList ?: ""
        todoScrollPane = JBScrollPane(todoList)
        todoScrollPane.border = componentBorder
        container.add(todoRow)
        container.add(Box.createRigidArea(Dimension(0,3)))
        container.add(todoScrollPane)
        container.add(Box.createRigidArea(Dimension(0,8)))

        relatedRow = JPanel(BorderLayout(3,3))
        val relatedLabel = JLabel("Related: ")
        relatedLabel.preferredSize = Dimension(80,32)
        relatedNumber = JTextField(38)
        PromptSupport.setPrompt("1234 3456", relatedNumber)
        PromptSupport.setFontStyle(Font.ITALIC, relatedNumber)
        PromptSupport.setForeground(Gray._120, relatedNumber)
        relatedNumber.toolTipText = "Add issues related to this change which are not resolved"
        relatedNumber.border = componentBorder
        relatedNumber.text = this.oldCommitMessage?.relatedNumber ?: ""
        relatedRow.add(relatedLabel, BorderLayout.WEST)
        relatedRow.add(relatedNumber, BorderLayout.CENTER)

        container.add(relatedRow)
        container.add(Box.createRigidArea(Dimension(0,8)))

        resolvesRow = JPanel(BorderLayout(3,3))
        val resolvesLabel = JLabel("Resolves: ")
        resolvesLabel.preferredSize = Dimension(80,32)
        resolvesNumber = JTextField(38)
        PromptSupport.setPrompt("1234 3456", resolvesNumber)
        PromptSupport.setFontStyle(Font.ITALIC, resolvesNumber)
        PromptSupport.setForeground(Gray._120, resolvesNumber)
        resolvesNumber.toolTipText = "Add issues to this which are resolved by your Changes"
        resolvesNumber.border = componentBorder
        resolvesNumber.text = this.oldCommitMessage?.resolvesNumber ?: ""
        resolvesRow.add(resolvesLabel, BorderLayout.WEST)
        resolvesRow.add(resolvesNumber, BorderLayout.CENTER)

        container.add(resolvesRow)
        container.add(Box.createRigidArea(Dimension(0,8)))

        releaseRow = JPanel(BorderLayout(3,3))
        val releaseLabel = JLabel("Release: ")
        releaseLabel.preferredSize = Dimension(80,32)
        releasesVersion = JTextField(38)
        PromptSupport.setPrompt("main, 11.5", releasesVersion)
        PromptSupport.setFontStyle(Font.ITALIC, releasesVersion)
        PromptSupport.setForeground(Gray._120, releasesVersion)
        releasesVersion.toolTipText = "This is a comma separated list of the target versions you intend to apply this fix on"
        releasesVersion.border = componentBorder
        releasesVersion.text = this.oldCommitMessage?.releasesVersion ?: ""
        releaseRow.add(releaseLabel, BorderLayout.WEST)
        releaseRow.add(releasesVersion, BorderLayout.CENTER)

        container.add(releaseRow)
        container.add(Box.createRigidArea(Dimension(0,8)))

        dependencyRow = JPanel(BorderLayout(3,3))
        val dependencyLabel = JLabel("Depends: ")
        dependencyLabel.preferredSize = Dimension(80,32)
        dependencyPatch = JTextField(38)
        PromptSupport.setPrompt("ChangeId, OfCorePatch", dependencyPatch)
        PromptSupport.setFontStyle(Font.ITALIC, dependencyPatch)
        PromptSupport.setForeground(Gray._120, dependencyPatch)
        dependencyPatch.toolTipText = "For TYPO3 documentation patches. Refer to the corresponding TYPO3 Core patch"
        dependencyPatch.border = componentBorder
        dependencyPatch.text = this.oldCommitMessage?.dependencyPatch ?: ""
        dependencyRow.add(dependencyLabel, BorderLayout.WEST)
        dependencyRow.add(dependencyPatch, BorderLayout.CENTER)

        container.add(dependencyRow)
        container.add(Box.createRigidArea(Dimension(0,8)))

        return container
    }

    fun getCommitMessage(): FormattedCommitMessage {
        return FormattedCommitMessage(
            commitType.getItemAt(commitType.selectedIndex).toString(),
            commitSubject.text.trim { it <= ' ' },
            doneTasks.text.trim { it <= ' ' },
            breakingChanges.text.trim { it <= ' ' },
            todoList.text.trim { it <= ' ' },
            relatedNumber.text.trim { it <= ' ' },
            resolvesNumber.text.trim { it <= ' ' },
            releasesVersion.text.trim { it <= ' ' },
            dependencyPatch.text.trim { it <= ' ' }
        )
    }
}