package com.github.inf166.pluginphpstormtypo3committemplate

import com.github.inf166.pluginphpstormtypo3committemplate.components.Prompt
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware

class ShowCommitTemplateAction : AnAction(), DumbAware {
    override fun actionPerformed(actionEvent: AnActionEvent) {
        val dialog = Prompt(actionEvent.project)
        dialog.show()
    }
}