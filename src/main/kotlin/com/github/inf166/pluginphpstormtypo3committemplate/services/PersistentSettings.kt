package com.github.inf166.pluginphpstormtypo3committemplate.services

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.github.inf166.pluginphpstormtypo3committemplate.MyBundle
import com.intellij.openapi.application.ApplicationManager

@State(name = "typo3-commit-message-template", storages = [(Storage("typo3-commit-message-template.xml"))])
class PersistentSettings(
    var bulletPoint: String = MyBundle.getMessage("bulletPoint"),
    var issueIndicator: String = MyBundle.getMessage("issueIndicator"),
    var changeTypes: String = MyBundle.getMessage("changeTypes"),
    var labelForTasks: String = MyBundle.getMessage("labelForTasks"),
    var labelForBreakingChanges: String = MyBundle.getMessage("labelForBreakingChanges"),
    var labelForTodos: String = MyBundle.getMessage("labelForTodos"),
    var labelForRelated: String = MyBundle.getMessage("labelForRelated"),
    var labelForResolves: String = MyBundle.getMessage("labelForResolves"),
    var labelForRelease: String = MyBundle.getMessage("labelForRelease"),
    var labelForDepends: String = MyBundle.getMessage("labelForDepends")
) : PersistentStateComponent<PersistentSettings> {

    override fun getState() = this
    override fun loadState(state: PersistentSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }
    companion object {
        val instance: PersistentSettings
            get() = ApplicationManager.getApplication().getService(PersistentSettings::class.java)
    }
}
