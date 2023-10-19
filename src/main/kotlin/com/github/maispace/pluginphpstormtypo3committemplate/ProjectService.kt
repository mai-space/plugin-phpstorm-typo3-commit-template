package com.github.mai

import com.github.maispace.pluginphpstormtypo3committemplate.MyBundle

import com.intellij.openapi.project.Project

class ProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
