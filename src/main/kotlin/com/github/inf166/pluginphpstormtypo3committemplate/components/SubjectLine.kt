package com.github.inf166.pluginphpstormtypo3committemplate.components

import com.github.inf166.pluginphpstormtypo3committemplate.helper.Constants
import com.intellij.openapi.ui.ComboBox
import org.jdesktop.swingx.prompt.PromptSupport
import java.awt.BorderLayout
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class SubjectLine {
    companion object {
        fun getCommitType(
            changeTypes: List<String>,
            toolTipText: String = "",
            previousChangeType: String = ""
        ): ComboBox<String> {
            val commitType = ComboBox<String>()
            for (changeType in changeTypes) {
                commitType.addItem(changeType)
            }
            commitType.maximumSize = commitType.preferredSize
            commitType.toolTipText = toolTipText
            commitType.border = JetbrainsBorderFactory.getBorder()
            commitType.selectedItem = previousChangeType
            return commitType
        }
        fun getSubjectInput(
            placeholder : String = "",
            toolTipText : String = "",
            previousSubject : String = ""
        ): JTextField {
            val inputField = JTextField(Constants.textFieldColumns)
            PromptSupport.setPrompt(placeholder, inputField)
            PromptSupport.setFontStyle(Font.ITALIC, inputField)
            PromptSupport.setForeground(Constants.placeholderColor, inputField)
            inputField.toolTipText = toolTipText
            inputField.border = JetbrainsBorderFactory.getBorder()
            inputField.text = previousSubject
            return inputField
        }
        fun getSubjectLine(
            type : ComboBox<String>,
            typeLabel : String = "",
            subject : JTextField,
            subjectLabel : String = ""
        ): JPanel {
            val subjectLine = JPanel(BorderLayout(Constants.smallSpace,Constants.noSpace))

            val commitTypeRow = JPanel(BorderLayout(Constants.noSpace,Constants.smallSpace))
            val commitTypeLabel = JLabel(typeLabel)
            commitTypeRow.add(commitTypeLabel, BorderLayout.NORTH)
            commitTypeRow.add(type, BorderLayout.SOUTH)

            subjectLine.add(commitTypeRow, BorderLayout.WEST)

            val subjectRow = JPanel(BorderLayout(Constants.noSpace,Constants.smallSpace))
            val commitSubjectLabel = JLabel(subjectLabel)
            subjectRow.add(commitSubjectLabel, BorderLayout.NORTH)
            subjectRow.add(subject, BorderLayout.SOUTH)

            subjectLine.add(subjectRow, BorderLayout.CENTER)

            return subjectLine
        }
    }
}