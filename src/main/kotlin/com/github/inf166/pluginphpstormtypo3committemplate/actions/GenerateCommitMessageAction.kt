package com.github.inf166.pluginphpstormtypo3committemplate.actions

import com.github.inf166.pluginphpstormtypo3committemplate.services.OllamaService
import com.github.inf166.pluginphpstormtypo3committemplate.settings.PersistentSettings
import com.github.inf166.pluginphpstormtypo3committemplate.utilities.CommitMessage
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.CommitMessageI
import com.intellij.openapi.vcs.VcsDataKeys
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.openapi.vcs.ui.Refreshable
import git4idea.repo.GitRepositoryManager
import javax.swing.SwingUtilities

class GenerateCommitMessageAction : AnAction(), DumbAware {

    override fun actionPerformed(actionEvent: AnActionEvent) {
        val project = actionEvent.project ?: return
        val commitPanel = getCommitPanel(actionEvent) ?: return

        // Get git diff
        val gitDiff = getGitDiff(project)
        if (gitDiff.isEmpty()) {
            Messages.showWarningDialog(
                project,
                "No changes detected. Please stage or modify files before generating commit message.",
                "No Changes"
            )
            return
        }

        // Generate with AI
        val ollamaService = OllamaService(project)
        ollamaService.generateCommitMessage(gitDiff) { result ->
            SwingUtilities.invokeLater {
                if (result.error != null) {
                    Messages.showErrorDialog(project, result.error, "AI Generation Failed")
                } else {
                    // Create commit message based on enabled fields
                    val commitMessage = buildCommitMessage(result.subject, result.tasks)
                    commitPanel.setCommitMessage(commitMessage)
                }
            }
        }
    }

    private fun buildCommitMessage(subject: String, tasks: String): String {
        val settings = PersistentSettings.instance
        val commitMessage = CommitMessage()
        
        // Set subject if enabled
        if (settings.useSubjectLine && subject.isNotEmpty()) {
            commitMessage.subjectLine = subject
        }
        
        // Set tasks if enabled
        if (settings.useTaskList && tasks.isNotEmpty()) {
            commitMessage.doneTasks = tasks
        }
        
        // Use default change type if flags are enabled
        if (settings.useFlags) {
            val changeTypes = settings.changeTypes.split(",").map { it.trim() }
            if (changeTypes.isNotEmpty()) {
                commitMessage.changeType = changeTypes[0] // Use first type as default
            }
        }
        
        return commitMessage.getFormattedCommitMessage()
    }

    private fun getGitDiff(project: com.intellij.openapi.project.Project): String {
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

            return diffBuilder.toString().take(MAX_DIFF_LENGTH) // Limit to avoid overwhelming the LLM
        } catch (e: Exception) {
            return ""
        }
    }

    companion object {
        private const val MAX_DIFF_LENGTH = 4000

        private fun getCommitPanel(e: AnActionEvent?): CommitMessageI? {
            if (e == null) {
                return null
            }
            val data = Refreshable.PANEL_KEY.getData(e.dataContext)
            return if (data is CommitMessageI) {
                data
            } else VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(e.dataContext)
        }
    }
}
