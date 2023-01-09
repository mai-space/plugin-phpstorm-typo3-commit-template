package com.github.inf166.pluginphpstormtypo3committemplate.components

import com.github.inf166.pluginphpstormtypo3committemplate.helper.Constants
import org.jdesktop.swingx.prompt.PromptSupport
import java.awt.BorderLayout
import java.awt.Font
import javax.swing.*

class SettingsInputField {
    companion object {
        fun getLabelWithInput(
            labelText: String = "",
            settingsInput: JTextField,
        ): JPanel {
            val referenceRow = JPanel(BorderLayout(Constants.smallSpace,Constants.smallSpace))
            val referenceLabel = JLabel(labelText)

            referenceRow.add(referenceLabel, BorderLayout.WEST)
            referenceRow.add(settingsInput, BorderLayout.CENTER)
            return referenceRow
        }
        fun getInputField(
            placeholder: String = "",
            toolTipText: String = ""): JTextField {
            // Init and Style for TextField
            val settingsInput = JTextField(Constants.textFieldColumns)
            PromptSupport.setPrompt(placeholder, settingsInput)
            PromptSupport.setFontStyle(Font.ITALIC, settingsInput)
            PromptSupport.setForeground(Constants.placeholderColor, settingsInput)
            settingsInput.border = JetbrainsBorderFactory.getBorder()

            // Set Values
            settingsInput.toolTipText = toolTipText
            return settingsInput
        }
    }
}