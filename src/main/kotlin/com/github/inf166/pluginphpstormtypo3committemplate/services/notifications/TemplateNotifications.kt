package com.github.inf166.pluginphpstormtypo3committemplate.services.notifications

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import javax.annotation.Nullable

class TemplateNotifications {

    fun notifyUpdatable(
        @Nullable project: Project?
    ) {

        NotificationGroupManager.getInstance()
            .getNotificationGroup("TYPO3 Commit Template Update available")
            .createNotification(
                "There is an Update available for the Plugin TYPO3 Commit Message Template",
                NotificationType.INFORMATION
            )
            .addAction(ListPluginsAction("Go to settings"))
            .notify(project)
    }
}