package com.github.inf166.pluginphpstormtypo3committemplate.utilities

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import git4idea.branch.GitBranchUtil

object GitBranchName {
    fun extractIssueNo(project: Project, dataContext: DataContext): String {
        val repository = GitBranchUtil.guessRepositoryForOperation(project, dataContext) ?: return ""
        val branchName = repository.currentBranch?.name ?: return ""
        val issueNoInBranchNameRegex = Regex("[0-9]+")
        val issueNumberMatches = issueNoInBranchNameRegex.findAll(branchName)
        return issueNumberMatches.map{ it.value }.joinToString()
    }
}
