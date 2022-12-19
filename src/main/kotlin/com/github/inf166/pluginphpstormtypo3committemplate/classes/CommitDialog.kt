package com.github.inf166.pluginphpstormtypo3committemplate.classes

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.*

class CommitDialog internal constructor(project: Project?) :
    DialogWrapper(project) {
    private val panel: CommitPanel = CommitPanel()

    init {
        title = "Commit Message Template by TYPO3 Guidelines"
        setOKButtonText("Apply")
        init()
    }

    override fun createCenterPanel(): JComponent? {
        return panel.getMainPanel()
    }

    val commitMessage: CommitMessage?
        get() = panel.getCommitMessage()
}