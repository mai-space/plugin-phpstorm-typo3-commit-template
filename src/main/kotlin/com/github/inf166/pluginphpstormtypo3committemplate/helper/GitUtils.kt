package com.github.inf166.pluginphpstormtypo3committemplate.helper

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.ui.CurrentBranchComponent
import com.intellij.openapi.vfs.VirtualFile


object GitUtils {
    fun extractBranchName(project: Project): String {
        var currentBranchName = ""
        val currentProjectFile = project.projectFile as VirtualFile
        currentBranchName = CurrentBranchComponent.getCurrentBranch(project, currentProjectFile)?.branchName.toString()
        val issueNoInBranchNameRegex = Regex("[0-9]+")
        val issueNumberMatches = issueNoInBranchNameRegex.findAll(currentBranchName)
        currentBranchName = issueNumberMatches.map{ it.value }.joinToString()
        return currentBranchName
    }
}
