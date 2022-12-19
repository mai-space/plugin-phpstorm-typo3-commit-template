package com.github.inf166.pluginphpstormtypo3committemplate.classes

import com.intellij.ui.JBColor
import java.awt.Color
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.*


class CommitPanel {
    private var mainPanel: JPanel = JPanel()
    private var shortDescription: JTextField = JTextField()
    private var breakingChanges: JTextArea = JTextArea()
    private var longDescription: JTextArea = JTextArea()
    private var workInProgress: JTextArea = JTextArea()
    private var closedIssues: JTextField = JTextField()
    private var featureRadioButton: JRadioButton = JRadioButton()
    private var bugfixRadioButton: JRadioButton = JRadioButton()
    private var docsRadioButton: JRadioButton = JRadioButton()
    private var securityRadioButton: JRadioButton = JRadioButton()
    private var taskRadioButton: JRadioButton = JRadioButton()
    private var changeTypeGroup: ButtonGroup = ButtonGroup()

    fun CommitPanel() {
        val lightGrey: Color = JBColor(Color(111, 111, 111, 255), Color(111, 111, 111, 255))
        val border = BorderFactory.createLineBorder(lightGrey)
        breakingChanges.border =
            BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10))
        longDescription.border =
            BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10))
        workInProgress.border =
            BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10))
        longDescription.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_TAB) {
                    if (e.modifiersEx > 0) {
                        longDescription.transferFocusBackward()
                    } else {
                        longDescription.transferFocus()
                    }
                    e.consume()
                }
            }
        })
        breakingChanges.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_TAB) {
                    if (e.modifiersEx > 0) {
                        breakingChanges.transferFocusBackward()
                    } else {
                        breakingChanges.transferFocus()
                    }
                    e.consume()
                }
            }
        })
        workInProgress.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_TAB) {
                    if (e.modifiersEx > 0) {
                        workInProgress.transferFocusBackward()
                    } else {
                        workInProgress.transferFocus()
                    }
                    e.consume()
                }
            }
        })
    }

    fun getMainPanel(): JPanel? {
        return mainPanel
    }

    fun getCommitMessage(): CommitMessage? {
        return CommitMessage(
            getSelectedChangeType(),
            shortDescription.text.trim { it <= ' ' },
            longDescription.text.trim { it <= ' ' },
            breakingChanges.text.trim { it <= ' ' },
            workInProgress.text.trim { it <= ' ' },
            closedIssues.text.trim { it <= ' ' }
        )
    }

    private fun getSelectedChangeType(): ChangeType? {
        val buttons = changeTypeGroup.elements
        while (buttons.hasMoreElements()) {
            val button = buttons.nextElement()
            if (button.isSelected) {
                return ChangeType.valueOf(button.actionCommand.uppercase(Locale.getDefault()))
            }
        }
        return null
    }
}