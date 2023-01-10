package com.github.inf166.pluginphpstormtypo3committemplate.template.partials

import com.github.inf166.pluginphpstormtypo3committemplate.utilities.Constants
import com.github.inf166.pluginphpstormtypo3committemplate.utilities.GitBranchName
import com.github.inf166.pluginphpstormtypo3committemplate.utilities.Icons
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
            project: Project? = null
        ): JPanel {
            val referenceRow = JPanel(BorderLayout(Constants.smallSpace, Constants.smallSpace))
            val referenceLabel = JLabel(labelText)
            referenceLabel.preferredSize = Dimension(Constants.textFieldWidth, Constants.textFieldHeight)

            referenceRow.add(referenceLabel, BorderLayout.WEST)
            referenceRow.add(referenceInput, BorderLayout.CENTER)

            val branchIssueNoButton = JButton()
            if (showReloadButton && project != null && project.let { GitBranchName.extractIssueNo(it) } != "") {
                branchIssueNoButton.action = object : AbstractAction() {
                    override fun actionPerformed(ae: ActionEvent) {
                        referenceInput.text += " ${ project.let { GitBranchName.extractIssueNo(it) } }"
                    }
                }
                branchIssueNoButton.icon = Icons.ReloadIssueNoFromBranchName
                branchIssueNoButton.preferredSize = Dimension(Constants.textFieldHeight, Constants.textFieldHeight)
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
            val referenceInput = JTextField(Constants.textFieldColumns)
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