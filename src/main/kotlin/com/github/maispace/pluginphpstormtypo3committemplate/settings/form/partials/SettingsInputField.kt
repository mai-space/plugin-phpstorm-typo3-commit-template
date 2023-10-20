package com.github.maispace.pluginphpstormtypo3committemplate.settings.form.partials

import com.github.maispace.pluginphpstormtypo3committemplate.template.partials.Border
import com.github.maispace.pluginphpstormtypo3committemplate.utilities.Constants
import org.jdesktop.swingx.prompt.PromptSupport
import java.awt.Dimension
import java.awt.Font
import javax.swing.*

class SettingsInputField {
    companion object {
        fun getLabelWithInput(
            labelText: String = "",
            settingsInput: JTextField,
            panel: JPanel
        ): JPanel {
            val referenceLabel = JLabel(labelText)
            referenceLabel.preferredSize = Dimension(180, 32)
            panel.add(referenceLabel)
            panel.add(settingsInput)
            return panel
        }
        fun getInputField(
            placeholder: String = "",
            toolTipText: String = ""): JTextField {
            // Init and Style for TextField
            val settingsInput = JTextField(Constants.TEXTFIELDCOLUMNS)
            PromptSupport.setPrompt(placeholder, settingsInput)
            PromptSupport.setFontStyle(Font.ITALIC, settingsInput)
            PromptSupport.setForeground(Constants.placeholderColor, settingsInput)
            settingsInput.border = Border.getBorder()

            // Set Values
            settingsInput.toolTipText = toolTipText
            return settingsInput
        }
    }
}