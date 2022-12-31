package com.github.inf166.pluginphpstormtypo3committemplate.components

import com.github.inf166.pluginphpstormtypo3committemplate.helper.Constants
import com.intellij.ui.components.JBScrollPane
import org.jdesktop.swingx.prompt.PromptSupport
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.KeyboardFocusManager
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextArea

class Changelog {
    companion object {
        fun getLabel(labelText: String = ""): JPanel {
            val labelPanel = JPanel(BorderLayout(Constants.noSpace,Constants.noSpace))
            val doneTasksLabel = JLabel(labelText)
            labelPanel.add(doneTasksLabel, BorderLayout.WEST)
            return labelPanel
        }
        fun getTextArea(placeholder : String = "", toolTipText : String = "", previousCommitMessage : String = ""): JTextArea {
            val newTextArea = JTextArea(Constants.textAreaRows,Constants.textAreaColumns)
            PromptSupport.setPrompt(placeholder, newTextArea)
            PromptSupport.setFontStyle(Font.ITALIC, newTextArea)
            PromptSupport.setForeground(Constants.placeholderColor, newTextArea)
            newTextArea.toolTipText = toolTipText
            newTextArea.minimumSize = Dimension(Constants.textAreaWidth,Constants.textAreaHeight)
            newTextArea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null)
            newTextArea.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null)
            newTextArea.text = previousCommitMessage
            return newTextArea
        }
        fun getScrollPane(textArea : JTextArea): JBScrollPane {
            val newScrollPane = JBScrollPane(textArea)
            newScrollPane.border = JetbrainsBorderFactory.getBorder()
            return newScrollPane
        }
    }
}