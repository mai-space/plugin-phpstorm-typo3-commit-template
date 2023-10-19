package com.github.maispace.pluginphpstormtypo3committemplate.template.partials

import com.github.maispace.pluginphpstormtypo3committemplate.utilities.Constants
import javax.swing.BorderFactory
import javax.swing.border.Border

class Border {
    companion object {
        fun getBorder(): Border {
            val border: Border = BorderFactory.createLineBorder(Constants.borderColorLight, 1)
            return BorderFactory.createCompoundBorder(
                border,
                BorderFactory.createEmptyBorder(3, 3, 3, 3)
            )
        }
    }
}