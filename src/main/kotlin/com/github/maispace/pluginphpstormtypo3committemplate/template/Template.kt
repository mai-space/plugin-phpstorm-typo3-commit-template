package com.github.maispace.pluginphpstormtypo3committemplate.template

import com.github.maispace.pluginphpstormtypo3committemplate.settings.PersistentSettings
import com.github.maispace.pluginphpstormtypo3committemplate.template.partials.Changelog
import com.github.maispace.pluginphpstormtypo3committemplate.template.partials.Reference
import com.github.maispace.pluginphpstormtypo3committemplate.template.partials.Spacer
import com.github.maispace.pluginphpstormtypo3committemplate.template.partials.SubjectLine
import com.github.maispace.pluginphpstormtypo3committemplate.utilities.CommitMessage
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.*

class Template(private val project: Project?, private val dataContext: DataContext, oldCommitMessage: CommitMessage?): DialogWrapper(project) {

    private var oldCommitMessage: CommitMessage?

    private lateinit var container: JPanel

    private lateinit var typeDropdown: ComboBox<String>

    private lateinit var subjectInputField: JTextField
    private lateinit var relatedInputField: JTextField
    private lateinit var resolvesInputField: JTextField
    private lateinit var releaseInputField: JTextField
    private lateinit var dependencyInputField: JTextField

    private lateinit var taskTextArea: JTextArea
    private lateinit var breakingTextArea: JTextArea
    private lateinit var todoTextArea: JTextArea

    init {
        this.oldCommitMessage = oldCommitMessage
        title = "Commit Message Template by TYPO3 Guidelines"
        setOKButtonText("Apply")
        init()
    }
    override fun createCenterPanel(): JComponent {

        container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.PAGE_AXIS)

            typeDropdown = SubjectLine.getCommitType(
                PersistentSettings.instance.changeTypes.split(",").map { it.trim() },
                "Select the Type of your Commit",
                this.oldCommitMessage?.changeType ?: ""
            )
            subjectInputField = SubjectLine.getSubjectInput(
                "A short description",
                "Write a brief summary of what the change does now",
                this.oldCommitMessage?.subjectLine ?: ""
            )
        if (PersistentSettings.instance.useFlags && PersistentSettings.instance.useSubjectLine) {
            container.add(
                SubjectLine.getSubjectLine(
                    typeDropdown,
                    "Type of Commit",
                    subjectInputField,
                    "Subject of Commit"
                )
            )
            container.add(Spacer.getComponentSpacer())
        }

            container.add(Changelog.getLabel("${PersistentSettings.instance.labelForTasks} "))
            container.add(Spacer.getLabelSpacer())
            taskTextArea = Changelog.getTextArea(
                "${PersistentSettings.instance.bulletPoint} Added something",
                "List the things you have done",
                this.oldCommitMessage?.doneTasks ?: ""
            )
        if (PersistentSettings.instance.useTaskList) {
            container.add(Changelog.getScrollPane(taskTextArea))
            container.add(Spacer.getComponentSpacer())
        }
            container.add(Changelog.getLabel("${PersistentSettings.instance.labelForBreakingChanges} "))
            container.add(Spacer.getLabelSpacer())
            breakingTextArea = Changelog.getTextArea(
                "${PersistentSettings.instance.bulletPoint} Done something dangerous",
                "List things you have done that could result in issues",
                this.oldCommitMessage?.breakingChanges ?: ""
            )
        if (PersistentSettings.instance.useBreakingList) {
            container.add(Changelog.getScrollPane(breakingTextArea))
            container.add(Spacer.getComponentSpacer())
        }
            container.add(Changelog.getLabel("${PersistentSettings.instance.labelForTodos} "))
            container.add(Spacer.getLabelSpacer())
            todoTextArea = Changelog.getTextArea(
                "${PersistentSettings.instance.bulletPoint} Need to do this",
                "List open tasks that have to be done",
                this.oldCommitMessage?.todoList ?: ""
            )
        if (PersistentSettings.instance.useToDoList) {
            container.add(Changelog.getScrollPane(todoTextArea))
            container.add(Spacer.getComponentSpacer())
        }

            relatedInputField = Reference.getInputField(
                "1234 3456",
                "Add issues related to this change which are not resolved",
                this.oldCommitMessage?.relatedNumber ?: ""
            )
        if (PersistentSettings.instance.useRelatedReference) {
            container.add(
                Reference.getLabelWithInput(
                    "${PersistentSettings.instance.labelForRelated} ",
                    relatedInputField,
                    true,
                    this.project,
                    this.dataContext
                )
            )
            container.add(Spacer.getComponentSpacer())
        }
            resolvesInputField = Reference.getInputField(
                "1234 3456",
                "Add issues to this which are resolved by your Changes",
                this.oldCommitMessage?.resolvesNumber ?: ""
            )
        if (PersistentSettings.instance.useResolvesReference) {
            container.add(
                Reference.getLabelWithInput(
                    "${PersistentSettings.instance.labelForResolves} ",
                    resolvesInputField,
                    true,
                    this.project,
                    this.dataContext
                )
            )
            container.add(Spacer.getComponentSpacer())
        }
            releaseInputField = Reference.getInputField(
                "main, 11.5",
                "This is a comma separated list of the target versions you intend to apply this fix on",
                this.oldCommitMessage?.releasesVersion ?: ""
            )
        if (PersistentSettings.instance.useReleaseReference) {
            container.add(Reference.getLabelWithInput("${PersistentSettings.instance.labelForRelease} ", releaseInputField))
            container.add(Spacer.getComponentSpacer())
        }
            dependencyInputField = Reference.getInputField(
                "ChangeId, OfCorePatch",
                "For TYPO3 documentation patches. Refer to the corresponding TYPO3 Core patch",
                this.oldCommitMessage?.dependencyPatch ?: ""
            )
        if (PersistentSettings.instance.useDependsReference) {
            container.add(Reference.getLabelWithInput("${PersistentSettings.instance.labelForDepends} ", dependencyInputField))
            container.add(Spacer.getComponentSpacer())
        }
        return container
    }

    fun getCommitMessage(): CommitMessage {
        return CommitMessage(
            typeDropdown.getItemAt(typeDropdown.selectedIndex).toString(),
            subjectInputField.text.trim { it <= ' ' },
            taskTextArea.text.trim { it <= ' ' },
            breakingTextArea.text.trim { it <= ' ' },
            todoTextArea.text.trim { it <= ' ' },
            relatedInputField.text.trim { it <= ' ' },
            resolvesInputField.text.trim { it <= ' ' },
            releaseInputField.text.trim { it <= ' ' },
            dependencyInputField.text.trim { it <= ' ' }
        )
    }
}