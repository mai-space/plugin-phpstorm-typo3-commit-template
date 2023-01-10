package com.github.inf166.pluginphpstormtypo3committemplate.actions

import com.github.inf166.pluginphpstormtypo3committemplate.template.Template
import com.github.inf166.pluginphpstormtypo3committemplate.utilities.CommitMessage
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.CommitMessageI
import com.intellij.openapi.vcs.VcsDataKeys
import com.intellij.openapi.vcs.ui.Refreshable

class ShowCommitTemplateAction : AnAction(), DumbAware {
    override fun actionPerformed(actionEvent: AnActionEvent) {
        val commitPanel = getCommitPanel(actionEvent) ?: return
        val intelliJcommitPannel: CommitMessageI = getCommitPanel(actionEvent) ?: return
        var commitMessageString = ""
        if (commitPanel is CheckinProjectPanel) {
            commitMessageString = commitPanel.commitMessage
        }

        val prompt: Template = if(commitMessageString.length > 1) {
            val oldCommitMessage = CommitMessage(commitMessageString)
            Template(actionEvent.project, oldCommitMessage)
        } else {
            val emptyCommitMessage = CommitMessage()
            Template(actionEvent.project, emptyCommitMessage)
        }

        prompt.show()

        if (prompt.exitCode == DialogWrapper.OK_EXIT_CODE) {
            intelliJcommitPannel.setCommitMessage(prompt.getCommitMessage().getFormattedCommitMessage())
        }
    }

    companion object {
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