package com.github.inf166.pluginphpstormtypo3committemplate.services

import com.intellij.openapi.project.Project
import com.github.inf166.pluginphpstormtypo3committemplate.services.notifications.TemplateNotifications

class ProjectService(project: Project) {

    init {

        val projectNotifier = TemplateNotifications()
        projectNotifier.notifyUpdatable(project)
    }
}
