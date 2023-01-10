package com.github.inf166.pluginphpstormtypo3committemplate.settings

import com.intellij.openapi.options.SearchableConfigurable
import com.github.inf166.pluginphpstormtypo3committemplate.components.TemplateSettingsForm
import javax.swing.JComponent
import org.jetbrains.annotations.Nls

class TemplateConfigurable : SearchableConfigurable {
    private val settingsForm = TemplateSettingsForm()
    private val persistentSettings = PersistentSettings.instance

    companion object {
        const val ID = "com.github.inf166.pluginphpstormtypo3committemplate"
        const val DISPLAY_NAME = "Commit Template with TYPO3 Commit Message Guidelines"
    }
    @Nls
    override fun getDisplayName(): String = DISPLAY_NAME
    override fun getId(): String = ID

    override fun createComponent(): JComponent {
        return settingsForm.createForm()
    }
    override fun isModified(): Boolean {
        return persistentSettings.bulletPoint != settingsForm.bulletPoint() ||
                persistentSettings.issueIndicator != settingsForm.issueIndicator() ||
                persistentSettings.changeTypes != settingsForm.changeTypes() ||
                persistentSettings.labelForTasks != settingsForm.labelForTasks() ||
                persistentSettings.labelForBreakingChanges != settingsForm.labelForBreakingChanges() ||
                persistentSettings.labelForTodos != settingsForm.labelForTodos() ||
                persistentSettings.labelForRelated != settingsForm.labelForRelated() ||
                persistentSettings.labelForResolves != settingsForm.labelForResolves() ||
                persistentSettings.labelForRelease != settingsForm.labelForRelease() ||
                persistentSettings.labelForDepends != settingsForm.labelForDepends()
    }
    override fun apply() {
        persistentSettings.bulletPoint = settingsForm.bulletPoint()
        persistentSettings.issueIndicator = settingsForm.issueIndicator()
        persistentSettings.changeTypes = settingsForm.changeTypes()
        persistentSettings.labelForTasks = settingsForm.labelForTasks()
        persistentSettings.labelForBreakingChanges = settingsForm.labelForBreakingChanges()
        persistentSettings.labelForTodos = settingsForm.labelForTodos()
        persistentSettings.labelForRelated = settingsForm.labelForRelated()
        persistentSettings.labelForResolves = settingsForm.labelForResolves()
        persistentSettings.labelForRelease = settingsForm.labelForRelease()
        persistentSettings.labelForDepends = settingsForm.labelForDepends()
    }
    override fun reset() {
        settingsForm.bulletPointInput.text = persistentSettings.bulletPoint
        settingsForm.issueIndicatorInput.text = persistentSettings.issueIndicator
        settingsForm.changeTypesInput.text = persistentSettings.changeTypes
        settingsForm.labelForTasksInput.text = persistentSettings.labelForTasks
        settingsForm.labelForBreakingChangesInput.text = persistentSettings.labelForBreakingChanges
        settingsForm.labelForTodosInput.text = persistentSettings.labelForTodos
        settingsForm.labelForRelatedInput.text = persistentSettings.labelForRelated
        settingsForm.labelForResolvesInput.text = persistentSettings.labelForResolves
        settingsForm.labelForReleaseInput.text = persistentSettings.labelForRelease
        settingsForm.labelForDependsInput.text = persistentSettings.labelForDepends
    }
}
