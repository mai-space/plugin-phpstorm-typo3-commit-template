package com.github.maispace.pluginphpstormtypo3committemplate.utilities

import com.github.maispace.pluginphpstormtypo3committemplate.utilities.Constants
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import git4idea.branch.GitBranchUtil

object GitBranchName {
    fun extractIssueNo(project: Project, dataContext: DataContext): String {
        // TODO: Remove dataContext
        // TODO: Remove deprecated method
        val repository = GitBranchUtil.getCurrentRepository(project) ?: return ""
        val branchName = repository.currentBranchName?: return ""

        val issueNoInBranchNameRegex = Regex(Constants.regexForIssueNo)
        val issueNumberMatches = issueNoInBranchNameRegex.findAll(branchName)
        return issueNumberMatches.map{ it.value }.joinToString()
    }
}
