package com.github.maispace.pluginphpstormtypo3committemplate.utilities

import com.intellij.ui.Gray
import java.awt.Color

class Constants {
    companion object {
        const val NO_SPACE : Int = 0
        const val SMALL_SPACE : Int = 5
        const val LARGE_SPACE : Int = 8

        const val TEXTAREA_COLUMNS : Int = 34
        const val TEXTAREA_ROWS : Int = 5
        const val TEXTAREA_WIDTH : Int = 507
        const val TEXTAREA_HEIGHT : Int = 107

        const val TEXT_FIELD_COLUMNS : Int = 38
        const val TEXT_FIELD_WIDTH : Int = 80
        const val TEXT_FIELD_HEIGHT : Int = 32

        val placeholderColor: Color = Gray._120
        val borderColorLight: Color = Gray._107

        // TYPO3 GUIDELINE RULES
        const val WARN_AT_CHARACTER_COUNT : Int = 52
        const val REMOVE_ABOVE_CHARACTER_COUNT : Int = 80
        const val MESSAGE = "Keep the whole line below 52 characters if possible, but below 80 in any case"

    }
}