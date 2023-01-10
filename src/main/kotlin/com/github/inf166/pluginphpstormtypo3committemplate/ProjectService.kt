package com.github.inf166.pluginphpstormtypo3committemplate

import com.intellij.openapi.project.Project
import com.github.inf166.pluginphpstormtypo3committemplate.MyBundle

class ProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
