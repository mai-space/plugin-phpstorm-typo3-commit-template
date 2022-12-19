package com.github.inf166.pluginphpstormtypo3committemplate.classes

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.CommitMessageI
import com.intellij.openapi.vcs.VcsDataKeys
import com.intellij.openapi.vcs.ui.Refreshable

class CreateCommitAction : AnAction(), DumbAware {
    override fun actionPerformed(actionEvent: AnActionEvent) {
        val commitPanel = getCommitPanel(actionEvent) ?: return
        val dialog = CommitDialog(actionEvent.project)
        dialog.show()
        if (dialog.exitCode == DialogWrapper.OK_EXIT_CODE) {
            commitPanel.setCommitMessage(dialog.commitMessage.toString())
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