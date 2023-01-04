package com.github.inf166.pluginphpstormtypo3committemplate.helper

import com.intellij.ui.Gray
import java.awt.Color

class Constants {
    companion object {
        const val noSpace : Int = 0
        const val smallSpace : Int = 5
        const val largeSpace : Int = 8

        const val textAreaColumns : Int = 34
        const val textAreaRows : Int = 5
        const val textAreaWidth : Int = 507
        const val textAreaHeight : Int = 107

        const val textFieldColumns : Int = 38
        const val textFieldWidth : Int = 80
        const val textFieldHeight : Int = 32

        val placeholderColor: Color = Gray._120
        val borderColorLight: Color = Gray._107

        const val bulletPoint: String = "*"
        const val issueIndicator: String = "#"

        val changeTypes = arrayOf(
            "FEATURE",
            "TASK",
            "BUGFIX",
            "SECURITY",
            "DOCS",
        )
        const val LabelForTasks: String = "Tasks:"
        const val LabelForBreakingChanges: String = "Breaking-Changes:"
        const val LabelForTodos: String = "To-Do's:"

        const val LabelForRelated: String = "Related:"
        const val LabelForResolves: String = "Resolves:"
        const val LabelForRelease: String = "Releases:"
        const val LabelForDepends: String = "Depends:"


    }
}