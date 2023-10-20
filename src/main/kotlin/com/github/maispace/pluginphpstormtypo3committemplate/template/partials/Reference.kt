package com.github.maispace.pluginphpstormtypo3committemplate.template.partials

import com.github.maispace.pluginphpstormtypo3committemplate.utilities.Constants
import com.github.maispace.pluginphpstormtypo3committemplate.utilities.GitBranchName
import com.github.maispace.pluginphpstormtypo3committemplate.utilities.Icons
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import org.jdesktop.swingx.prompt.PromptSupport
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ActionEvent
import javax.swing.*


class Reference {
    companion object {
        fun getLabelWithInput(
            labelText: String = "",
            referenceInput: JTextField,
            showReloadButton: Boolean = false,
            project: Project? = null,
            dataContext: DataContext? = null
        ): JPanel {
            val referenceRow = JPanel(BorderLayout(Constants.SMALL_SPACE, Constants.SMALL_SPACE))
            val referenceLabel = JLabel(labelText)
            referenceLabel.preferredSize = Dimension(Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_HEIGHT)

            referenceRow.add(referenceLabel, BorderLayout.WEST)
            referenceRow.add(referenceInput, BorderLayout.CENTER)

            val branchIssueNoButton = JButton()
            if (showReloadButton && project != null && dataContext != null) {
                branchIssueNoButton.action = object : AbstractAction() {
                    override fun actionPerformed(ae: ActionEvent) {
                        val issueNo = GitBranchName.extractIssueNo(project, dataContext)
                        if (issueNo.isNotEmpty() && !referenceInput.text.contains(issueNo)) {
                            referenceInput.text += if (referenceInput.text.isEmpty()) {
                                issueNo
                            } else {
                                " $issueNo"
                            }
                        }
                    }
                }
                branchIssueNoButton.icon = Icons.ReloadIssueNoFromBranchName
                branchIssueNoButton.preferredSize = Dimension(Constants.TEXT_FIELD_HEIGHT, Constants.TEXT_FIELD_HEIGHT)
                branchIssueNoButton.toolTipText = "Get Issue Number from branch name"
                referenceRow.add(branchIssueNoButton, BorderLayout.EAST)
            }
            return referenceRow
        }
        fun getInputField(
            placeholder: String = "",
            toolTipText: String = "",
            previousCommitMessage: String = ""): JTextField {
            // Init and Style for TextField
            val referenceInput = JTextField(Constants.TEXT_FIELD_COLUMNS)
            PromptSupport.setPrompt(placeholder, referenceInput)
            PromptSupport.setFontStyle(Font.ITALIC, referenceInput)
            PromptSupport.setForeground(Constants.placeholderColor, referenceInput)
            referenceInput.border = Border.getBorder()

            // Set Values
            referenceInput.toolTipText = toolTipText
            referenceInput.text = previousCommitMessage
            return referenceInput
        }
    }
}