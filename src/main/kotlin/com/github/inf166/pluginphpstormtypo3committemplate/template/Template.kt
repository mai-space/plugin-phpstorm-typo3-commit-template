package com.github.inf166.pluginphpstormtypo3committemplate.template

import com.github.inf166.pluginphpstormtypo3committemplate.services.OllamaService
import com.github.inf166.pluginphpstormtypo3committemplate.settings.PersistentSettings
import com.github.inf166.pluginphpstormtypo3committemplate.template.partials.Changelog
import com.github.inf166.pluginphpstormtypo3committemplate.template.partials.Reference
import com.github.inf166.pluginphpstormtypo3committemplate.template.partials.Spacer
import com.github.inf166.pluginphpstormtypo3committemplate.template.partials.SubjectLine
import com.github.inf166.pluginphpstormtypo3committemplate.utilities.CommitMessage
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.changes.ChangeListManager
import git4idea.GitUtil
import git4idea.repo.GitRepositoryManager
import javax.swing.*
import java.awt.FlowLayout

class Template(private val project: Project?, private val dataContext: DataContext,
               private var oldCommitMessage: CommitMessage?
) :
    DialogWrapper(project) {

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
        title = "Commit Message Template by TYPO3 Guidelines"
        setOKButtonText("Apply")
        init()
    }

    override fun createCenterPanel(): JComponent {

        container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.PAGE_AXIS)

        // Add AI generation button at the top
        val aiButtonPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        val generateButton = JButton("Generate with AI")
        generateButton.addActionListener {
            generateWithAI()
        }
        aiButtonPanel.add(generateButton)
        container.add(aiButtonPanel)
        container.add(Spacer.getComponentSpacer())

        typeDropdown = SubjectLine.getCommitType(
            PersistentSettings.instance.changeTypes.split(",").map { it.trim() },
            "Select the Type of your Commit",
            this.oldCommitMessage?.changeType ?: ""
        )
        subjectInputField = SubjectLine.getSubjectInput(
            "A short description",
            "Write a brief summary of what the change does now",
            this.oldCommitMessage?.subjectLine ?: "",
            project
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
        } else {
            if (PersistentSettings.instance.useFlags) {
                container.add(
                    typeDropdown
                )
            } else {
                container.add(
                    subjectInputField
                )
            }
            container.add(Spacer.getComponentSpacer())
        }

        taskTextArea = Changelog.getTextArea(
            "${PersistentSettings.instance.bulletPoint} Added something",
            "List the things you have done",
            this.oldCommitMessage?.doneTasks ?: ""
        )
        if (PersistentSettings.instance.useTaskList) {
            container.add(Changelog.getLabel("${PersistentSettings.instance.labelForTasks} "))
            container.add(Spacer.getLabelSpacer())
            container.add(Changelog.getScrollPane(taskTextArea))
            container.add(Spacer.getComponentSpacer())
        }

        breakingTextArea = Changelog.getTextArea(
            "${PersistentSettings.instance.bulletPoint} Done something dangerous",
            "List things you have done that could result in issues",
            this.oldCommitMessage?.breakingChanges ?: ""
        )
        if (PersistentSettings.instance.useBreakingList) {
            container.add(Changelog.getLabel("${PersistentSettings.instance.labelForBreakingChanges} "))
            container.add(Spacer.getLabelSpacer())
            container.add(Changelog.getScrollPane(breakingTextArea))
            container.add(Spacer.getComponentSpacer())
        }

        todoTextArea = Changelog.getTextArea(
            "${PersistentSettings.instance.bulletPoint} Need to do this",
            "List open tasks that have to be done",
            this.oldCommitMessage?.todoList ?: ""
        )
        if (PersistentSettings.instance.useToDoList) {
            container.add(Changelog.getLabel("${PersistentSettings.instance.labelForTodos} "))
            container.add(Spacer.getLabelSpacer())
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
            container.add(
                Reference.getLabelWithInput(
                    "${PersistentSettings.instance.labelForRelease} ",
                    releaseInputField
                )
            )
            container.add(Spacer.getComponentSpacer())
        }

        dependencyInputField = Reference.getInputField(
            "ChangeId, OfCorePatch",
            "For TYPO3 documentation patches. Refer to the corresponding TYPO3 Core patch",
            this.oldCommitMessage?.dependencyPatch ?: ""
        )
        if (PersistentSettings.instance.useDependsReference) {
            container.add(
                Reference.getLabelWithInput(
                    "${PersistentSettings.instance.labelForDepends} ",
                    dependencyInputField
                )
            )
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

    private fun generateWithAI() {
        val gitDiff = getGitDiff()
        if (gitDiff.isEmpty()) {
            Messages.showWarningDialog(
                project,
                "No changes detected. Please stage or modify files before generating commit message.",
                "No Changes"
            )
            return
        }

        val ollamaService = OllamaService(project)
        ollamaService.generateCommitMessage(gitDiff) { result ->
            SwingUtilities.invokeLater {
                if (result.error != null) {
                    Messages.showErrorDialog(project, result.error, "AI Generation Failed")
                } else {
                    // Populate enabled fields
                    if (PersistentSettings.instance.useSubjectLine && result.subject.isNotEmpty()) {
                        subjectInputField.text = result.subject
                    }
                    if (PersistentSettings.instance.useTaskList && result.tasks.isNotEmpty()) {
                        taskTextArea.text = result.tasks
                    }
                }
            }
        }
    }

    private fun getGitDiff(): String {
        if (project == null) return ""

        try {
            val changeListManager = ChangeListManager.getInstance(project)
            val changes = changeListManager.allChanges

            if (changes.isEmpty()) {
                return ""
            }

            val gitRepositoryManager = GitRepositoryManager.getInstance(project)
            val repositories = gitRepositoryManager.repositories
            
            if (repositories.isEmpty()) {
                return ""
            }

            val repository = repositories.firstOrNull() ?: return ""
            val git = GitUtil.getRepositoryManager(project).getRepositoryForRoot(repository.root) ?: return ""

            // Get diff of staged and unstaged changes
            val diffBuilder = StringBuilder()
            
            for (change in changes) {
                change.beforeRevision?.let { before ->
                    change.afterRevision?.let { after ->
                        diffBuilder.append("File: ${after.file.path}\n")
                        diffBuilder.append("---\n")
                        
                        val beforeContent = before.content ?: ""
                        val afterContent = after.content ?: ""
                        
                        if (beforeContent != afterContent) {
                            diffBuilder.append("Before:\n$beforeContent\n")
                            diffBuilder.append("After:\n$afterContent\n")
                            diffBuilder.append("\n")
                        }
                    }
                } ?: run {
                    // New file
                    change.afterRevision?.let { after ->
                        diffBuilder.append("New file: ${after.file.path}\n")
                        diffBuilder.append("---\n")
                        diffBuilder.append(after.content ?: "")
                        diffBuilder.append("\n\n")
                    }
                }
            }

            return diffBuilder.toString().take(4000) // Limit to avoid overwhelming the LLM
        } catch (e: Exception) {
            return ""
        }
    }
}