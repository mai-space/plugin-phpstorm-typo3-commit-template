package com.github.inf166.pluginphpstormtypo3committemplate.utilities

import com.github.inf166.pluginphpstormtypo3committemplate.settings.PersistentSettings
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import git4idea.branch.GitBranchUtil

object GitBranchName {
    fun extractIssueNo(project: Project, dataContext: DataContext): String {
        val repository = GitBranchUtil.guessRepositoryForOperation(project, dataContext) ?: return ""
        val branchName = repository.currentBranch?.name ?: return ""

        val issueNoInBranchNameRegex = Regex(PersistentSettings.instance.regexForIssueNo)
        val issueNumberMatches = issueNoInBranchNameRegex.findAll(branchName)
        val issueNumbers = issueNumberMatches.map { it.value }.toList()
        return issueNumbers.joinToString(" ").replace(",", "")
    }
}
