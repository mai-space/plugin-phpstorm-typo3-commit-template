package com.github.inf166.pluginphpstormtypo3committemplate.template.partials

import com.github.inf166.pluginphpstormtypo3committemplate.settings.PersistentSettings
import com.github.inf166.pluginphpstormtypo3committemplate.utilities.Constants
import com.intellij.ui.components.JBScrollPane
import org.jdesktop.swingx.prompt.PromptSupport
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.KeyboardFocusManager
import java.awt.event.ActionEvent
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.awt.event.KeyEvent
import javax.swing.*


class Changelog {
    companion object {
        fun getLabel(labelText: String = ""): JPanel {
            val labelPanel = JPanel(BorderLayout(Constants.NO_SPACE, Constants.NO_SPACE))
            val doneTasksLabel = JLabel(labelText)
            labelPanel.add(doneTasksLabel, BorderLayout.WEST)
            return labelPanel
        }
        fun getTextArea(placeholder : String = "", toolTipText : String = "", previousCommitMessage : String = ""): JTextArea {
            // Init and Style TextArea
            val newTextArea = JTextArea(Constants.TEXTAREA_ROWS, Constants.TEXTAREA_COLUMNS)
            PromptSupport.setPrompt(placeholder, newTextArea)
            PromptSupport.setFontStyle(Font.ITALIC, newTextArea)
            PromptSupport.setForeground(Constants.placeholderColor, newTextArea)
            newTextArea.minimumSize = Dimension(Constants.TEXTAREA_WIDTH, Constants.TEXTAREA_HEIGHT)
            newTextArea.lineWrap = true
            newTextArea.wrapStyleWord = true

            // Set Values
            newTextArea.toolTipText = toolTipText
            newTextArea.text = previousCommitMessage

            // On Press Enter Key add new Value
            newTextArea.inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter")
            newTextArea.actionMap.put("enter", object : AbstractAction() {
                override fun actionPerformed(ae: ActionEvent) {
                    addNewLineto(newTextArea)
                }

                private fun addNewLineto(newTextArea: JTextArea) {
                    val stringBuilder: java.lang.StringBuilder = StringBuilder()
                    stringBuilder.append(newTextArea.text).append('\n').append("${PersistentSettings.instance.bulletPoint} ")
                    newTextArea.text = stringBuilder.toString()
                }
            })

            // Enhance Focus Behaviour
            newTextArea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null)
            newTextArea.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null)
            newTextArea.addFocusListener(object: FocusAdapter() {
                override fun focusGained(e: FocusEvent?) {
                    var textArea = JTextArea()
                    try {
                        if (e != null) {
                            textArea = e.component as JTextArea
                        }
                        val input: String = textArea.text.toString()
                        if (input == "") {
                            textArea.text = "${PersistentSettings.instance.bulletPoint} "
                        }
                    } catch (ignored: ClassCastException) {
                    }
                }
                override fun focusLost(e: FocusEvent?) {
                    var textArea = JTextArea()
                    try {
                        if (e != null) {
                            textArea = e.component as JTextArea
                        }
                        val input: String = textArea.text.toString()
                        if (input == "${PersistentSettings.instance.bulletPoint} ") {
                            textArea.text = ""
                        }
                    } catch (ignored: ClassCastException) {
                    }
                }
            })
            return newTextArea
        }
        fun getScrollPane(textArea : JTextArea): JBScrollPane {
            val newScrollPane = JBScrollPane(textArea)
            newScrollPane.border = Border.getBorder()
            return newScrollPane
        }
    }
}