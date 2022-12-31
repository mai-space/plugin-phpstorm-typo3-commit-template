package com.github.inf166.pluginphpstormtypo3committemplate.components

import com.github.inf166.pluginphpstormtypo3committemplate.helper.Constants
import org.jdesktop.swingx.prompt.PromptSupport
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class Reference {
    companion object {
        fun getLabelWithInput(labelText: String = "", referenceInput : JTextField): JPanel {
            val referenceRow = JPanel(BorderLayout(Constants.smallSpace,Constants.smallSpace))
            val referenceLabel = JLabel(labelText)
            referenceLabel.preferredSize = Dimension(Constants.textFieldWidth,Constants.textFieldHeight)
            referenceRow.add(referenceLabel, BorderLayout.WEST)
            referenceRow.add(referenceInput, BorderLayout.CENTER)
            return referenceRow
        }
        fun getInputField(
            placeholder: String = "",
            toolTipText: String = "",
            previousCommitMessage: String = ""): JTextField {
            val referenceInput = JTextField(Constants.textFieldColumns)
            PromptSupport.setPrompt(placeholder, referenceInput)
            PromptSupport.setFontStyle(Font.ITALIC, referenceInput)
            PromptSupport.setForeground(Constants.placeholderColor, referenceInput)
            referenceInput.toolTipText = toolTipText
            referenceInput.border = JetbrainsBorderFactory.getBorder()
            referenceInput.text = previousCommitMessage
            return referenceInput
        }
    }
}