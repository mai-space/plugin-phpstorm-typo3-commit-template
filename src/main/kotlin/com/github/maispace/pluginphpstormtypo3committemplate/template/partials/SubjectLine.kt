package com.github.maispace.pluginphpstormtypo3committemplate.template.partials

import com.github.maispace.pluginphpstormtypo3committemplate.utilities.Constants
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.ComponentValidator
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.DocumentAdapter
import org.jdesktop.swingx.prompt.PromptSupport
import java.awt.BorderLayout
import java.awt.Font
import java.util.function.Supplier
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
            commitType.border = Border.getBorder()
            commitType.selectedItem = previousChangeType
            return commitType
        }
        fun getSubjectInput(
            placeholder: String = "",
            toolTipText: String = "",
            previousSubject: String = "",
            project: Project?
        ): JTextField {
            val inputField = JTextField(Constants.TEXT_FIELD_COLUMNS)
            PromptSupport.setPrompt(placeholder, inputField)
            PromptSupport.setFontStyle(Font.ITALIC, inputField)
            PromptSupport.setForeground(Constants.placeholderColor, inputField)
            inputField.toolTipText = toolTipText
            inputField.border = Border.getBorder()
            inputField.text = previousSubject

            if (project != null) {
                val validatorSupplier = Supplier {
                    val text = inputField.text
                    if (text.length >= Constants.REMOVE_ABOVE_CHARACTER_COUNT) {
                        ValidationInfo(Constants.MESSAGE, inputField)
                    } else {
                        if (text.length >= Constants.WARN_AT_CHARACTER_COUNT) {
                            ValidationInfo(Constants.MESSAGE, inputField).asWarning()
                        } else {
                            null
                        }
                    }
                }
                ComponentValidator(project).withValidator(validatorSupplier).installOn(inputField)
            }

            inputField.document.addDocumentListener(object : DocumentAdapter() {
                override fun textChanged(e: javax.swing.event.DocumentEvent) {
                    ComponentValidator.getInstance(inputField).ifPresent { v: ComponentValidator -> v.revalidate() }
                }
            })

            return inputField
        }
        fun getSubjectLine(
            type : ComboBox<String>,
            typeLabel : String = "",
            subject : JTextField,
            subjectLabel : String = ""
        ): JPanel {
            val subjectLine = JPanel(BorderLayout(Constants.SMALL_SPACE, Constants.NO_SPACE))

            val commitTypeRow = JPanel(BorderLayout(Constants.NO_SPACE, Constants.SMALL_SPACE))
            val commitTypeLabel = JLabel(typeLabel)
            commitTypeRow.add(commitTypeLabel, BorderLayout.NORTH)
            commitTypeRow.add(type, BorderLayout.SOUTH)

            subjectLine.add(commitTypeRow, BorderLayout.WEST)

            val subjectRow = JPanel(BorderLayout(Constants.NO_SPACE, Constants.SMALL_SPACE))
            val commitSubjectLabel = JLabel(subjectLabel)
            subjectRow.add(commitSubjectLabel, BorderLayout.NORTH)
            subjectRow.add(subject, BorderLayout.SOUTH)

            subjectLine.add(subjectRow, BorderLayout.CENTER)

            return subjectLine
        }
    }
}