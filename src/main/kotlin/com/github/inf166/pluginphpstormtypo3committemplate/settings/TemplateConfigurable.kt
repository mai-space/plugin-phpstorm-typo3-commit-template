package com.github.inf166.pluginphpstormtypo3committemplate.settings

import com.github.inf166.pluginphpstormtypo3committemplate.settings.form.SettingsFormPanel
import com.intellij.openapi.options.SearchableConfigurable
import javax.swing.JComponent
import org.jetbrains.annotations.Nls

class TemplateConfigurable : SearchableConfigurable {
    private val settingsFormPanel = SettingsFormPanel()
    private val persistentSettings = PersistentSettings.instance

    companion object {
        const val ID = "com.github.inf166.pluginphpstormtypo3committemplate"
        const val DISPLAY_NAME = " TYPO3 Commit Template"
    }
    @Nls
    override fun getDisplayName(): String = DISPLAY_NAME
    override fun getId(): String = ID

    override fun createComponent(): JComponent {
        return settingsFormPanel.createForm()
    }
    override fun isModified(): Boolean {
        return persistentSettings.bulletPoint != settingsFormPanel.bulletPoint() ||
                persistentSettings.issueIndicator != settingsFormPanel.issueIndicator() ||
                persistentSettings.changeTypes != settingsFormPanel.changeTypes() ||
                persistentSettings.labelForTasks != settingsFormPanel.labelForTasks() ||
                persistentSettings.labelForBreakingChanges != settingsFormPanel.labelForBreakingChanges() ||
                persistentSettings.labelForTodos != settingsFormPanel.labelForTodos() ||
                persistentSettings.labelForRelated != settingsFormPanel.labelForRelated() ||
                persistentSettings.labelForResolves != settingsFormPanel.labelForResolves() ||
                persistentSettings.labelForRelease != settingsFormPanel.labelForRelease() ||
                persistentSettings.labelForDepends != settingsFormPanel.labelForDepends()
    }
    override fun apply() {
        persistentSettings.bulletPoint = settingsFormPanel.bulletPoint()
        persistentSettings.issueIndicator = settingsFormPanel.issueIndicator()
        persistentSettings.changeTypes = settingsFormPanel.changeTypes()
        persistentSettings.labelForTasks = settingsFormPanel.labelForTasks()
        persistentSettings.labelForBreakingChanges = settingsFormPanel.labelForBreakingChanges()
        persistentSettings.labelForTodos = settingsFormPanel.labelForTodos()
        persistentSettings.labelForRelated = settingsFormPanel.labelForRelated()
        persistentSettings.labelForResolves = settingsFormPanel.labelForResolves()
        persistentSettings.labelForRelease = settingsFormPanel.labelForRelease()
        persistentSettings.labelForDepends = settingsFormPanel.labelForDepends()
    }
    override fun reset() {
        settingsFormPanel.bulletPointInput.text = persistentSettings.bulletPoint
        settingsFormPanel.issueIndicatorInput.text = persistentSettings.issueIndicator
        settingsFormPanel.changeTypesInput.text = persistentSettings.changeTypes
        settingsFormPanel.labelForTasksInput.text = persistentSettings.labelForTasks
        settingsFormPanel.labelForBreakingChangesInput.text = persistentSettings.labelForBreakingChanges
        settingsFormPanel.labelForTodosInput.text = persistentSettings.labelForTodos
        settingsFormPanel.labelForRelatedInput.text = persistentSettings.labelForRelated
        settingsFormPanel.labelForResolvesInput.text = persistentSettings.labelForResolves
        settingsFormPanel.labelForReleaseInput.text = persistentSettings.labelForRelease
        settingsFormPanel.labelForDependsInput.text = persistentSettings.labelForDepends
    }
}
