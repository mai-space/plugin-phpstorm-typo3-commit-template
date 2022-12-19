package com.github.inf166.pluginphpstormtypo3committemplate.components

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.*
class Prompt internal constructor(project: Project?) :
    DialogWrapper(project) {
    private var panel: JPanel = JPanel()

    init {
        title = "Commit Message Template by TYPO3 Guidelines"
        setOKButtonText("Apply")
        init()
    }

    override fun createCenterPanel(): JComponent {
        return panel
    }
}