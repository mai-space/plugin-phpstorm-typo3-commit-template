package com.github.maispace.pluginphpstormtypo3committemplate.utilities

import com.github.maispace.pluginphpstormtypo3committemplate.settings.PersistentSettings
import com.intellij.ui.Gray
import com.jetbrains.rd.generator.nova.PredefinedType
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
    }
}