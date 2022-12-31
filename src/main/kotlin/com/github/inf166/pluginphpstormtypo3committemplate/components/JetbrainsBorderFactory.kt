package com.github.inf166.pluginphpstormtypo3committemplate.components

import com.github.inf166.pluginphpstormtypo3committemplate.helper.Constants
import javax.swing.BorderFactory
import javax.swing.border.Border

class JetbrainsBorderFactory {
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