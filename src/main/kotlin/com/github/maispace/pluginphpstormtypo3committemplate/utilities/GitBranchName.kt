package com.github.maispace.pluginphpstormtypo3committemplate.utilities

import com.github.maispace.pluginphpstormtypo3committemplate.settings.PersistentSettings
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import git4idea.branch.GitBranchUtil

object GitBranchName {
    fun extractIssueNo(project: Project, dataContext: DataContext): String {
        // TODO: Remove dataContext
        // TODO: Remove deprecated method
        val repository = GitBranchUtil.getCurrentRepository(project) ?: return ""
        val branchName = repository.currentBranchName?: return ""

        val issueNoInBranchNameRegex = Regex(PersistentSettings.instance.regexForIssueNo)
        val issueNumberMatches = issueNoInBranchNameRegex.findAll(branchName)
        return issueNumberMatches.map{ it.value }.joinToString()
    }
}
