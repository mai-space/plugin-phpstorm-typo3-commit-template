package com.github.inf166.pluginphpstormtypo3committemplate.helper

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.ui.CurrentBranchComponent

object GitUtils {
    fun extractBranchName(project: Project): String {
        var currentBranchName = ""
        currentBranchName = CurrentBranchComponent.getCurrentBranch(project, project.baseDir)?.branchName.toString()
        val issueNoInBranchNameRegex = Regex("[0-9]+")
        val issueNumberMatches = issueNoInBranchNameRegex.findAll(currentBranchName)
        currentBranchName = issueNumberMatches.map{ it.value }.joinToString()
        return currentBranchName
    }
}
