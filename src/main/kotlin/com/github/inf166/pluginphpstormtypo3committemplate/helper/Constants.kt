package com.github.inf166.pluginphpstormtypo3committemplate.helper

import com.github.inf166.pluginphpstormtypo3committemplate.settings.PersistentSettings
import com.intellij.ui.scale.JBUIScale
import com.intellij.ui.Gray
import java.awt.Color


class Constants {
    companion object {
        // Spaces
        const val noSpace : Int = 0
        const val smallSpace : Int = 5
        const val largeSpace : Int = 8

        // Text Area Sizes
        const val textAreaColumns : Int = 34
        const val textAreaRows : Int = 5
        const val textAreaWidth : Int = 507
        const val textAreaHeight : Int = 107

        // Text Field Sizes
        const val textFieldColumns : Int = 38
        const val textFieldWidth : Int = 80
        const val textFieldHeight : Int = 32

        // Colors
        val placeholderColor: Color = Gray._120
        val borderColorLight: Color = Gray._107

        // Commit Message - Types, Labels and Indicators
        // Also found in MyBundle.properties
        var bulletPoint: String = PersistentSettings.instance.bulletPoint
        var issueIndicator: String = PersistentSettings.instance.issueIndicator

        var changeTypes: List<String> = PersistentSettings.instance.changeTypes.split(",").map { it.trim() }
        var labelForTasks: String = PersistentSettings.instance.labelForTasks
        var labelForBreakingChanges: String = PersistentSettings.instance.labelForBreakingChanges
        var labelForTodos: String = PersistentSettings.instance.labelForTodos

        var labelForRelated: String = PersistentSettings.instance.labelForRelated
        var labelForResolves: String = PersistentSettings.instance.labelForResolves
        var labelForRelease: String = PersistentSettings.instance.labelForRelease
        var labelForDepends: String = PersistentSettings.instance.labelForDepends

    }
}