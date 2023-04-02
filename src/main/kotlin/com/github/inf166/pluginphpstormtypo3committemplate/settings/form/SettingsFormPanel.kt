package com.github.inf166.pluginphpstormtypo3committemplate.settings.form

import com.github.inf166.pluginphpstormtypo3committemplate.MyBundle
import com.github.inf166.pluginphpstormtypo3committemplate.settings.form.partials.SettingsInputField
import com.github.inf166.pluginphpstormtypo3committemplate.template.partials.Spacer
import com.github.inf166.pluginphpstormtypo3committemplate.utilities.Constants
import com.intellij.ui.IdeBorderFactory
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JTextField

class SettingsFormPanel {
    private var useFlags: JCheckBox = JCheckBox("Use Flags like [FEATURE] and [!!!]")
    private var useSubjectLine: JCheckBox = JCheckBox("Use the Subject Line")
    private var useTaskList: JCheckBox = JCheckBox("Use Task list")
    private var useBreakingList: JCheckBox = JCheckBox("Use Breaking Changes list")
    private var useToDoList: JCheckBox = JCheckBox("Use Todo list")
    private var useRelatedReference: JCheckBox = JCheckBox("Use related issueno reference field")
    private var useResolvesReference: JCheckBox = JCheckBox("Use resolves issueno reference field")
    private var useReleaseReference: JCheckBox = JCheckBox("Use releases on reference field")
    private var useDependsReference: JCheckBox = JCheckBox("Use depends on reference field")
    var bulletPointInput: JTextField = SettingsInputField.getInputField(
        MyBundle.getMessage("bulletPoint"),
        "Will be shown in the Changelog Lists"
    )
    var issueIndicatorInput: JTextField = SettingsInputField.getInputField(
        MyBundle.getMessage("issueIndicator"),
        "Will be shown in the Reference Lists"
    )
    var changeTypesInput: JTextField = SettingsInputField.getInputField(
        MyBundle.getMessage("changeTypes"),
        "Will be shown in the Dropdown for change Types"
    )
    var labelForTasksInput: JTextField = SettingsInputField.getInputField(
        MyBundle.getMessage("labelForTasks"),
        "Will be the Title for the done Tasks Changelog"
    )
    var labelForBreakingChangesInput: JTextField = SettingsInputField.getInputField(
        MyBundle.getMessage("labelForBreakingChanges"),
        "Will be the Title for the breaking Changes Changelog"
    )
    var labelForTodosInput: JTextField = SettingsInputField.getInputField(
        MyBundle.getMessage("labelForTodos"),
        "Will be the Title for the To-Do's List"
    )
    var labelForRelatedInput: JTextField = SettingsInputField.getInputField(
        MyBundle.getMessage("labelForRelated"),
        "Will be the identifier for the related Reference"
    )
    var labelForResolvesInput: JTextField = SettingsInputField.getInputField(
        MyBundle.getMessage("labelForResolves"),
        "Will be the identifier for the resolves Reference"
    )
    var labelForReleaseInput: JTextField = SettingsInputField.getInputField(
        MyBundle.getMessage("labelForRelease"),
        "Will be the identifier for the releases Reference"
    )
    var labelForDependsInput: JTextField = SettingsInputField.getInputField(
        MyBundle.getMessage("labelForDepends"),
        "Will be the identifier for the depends Reference"
    )
    fun createForm() : JPanel {
        val containerPanel = JPanel()
        containerPanel.layout = BoxLayout(containerPanel, BoxLayout.PAGE_AXIS)

        val enOrDisableFieldsPanel = JPanel()
        enOrDisableFieldsPanel.layout = GridLayout(9, 1, 0, Constants.largeSpace)
        enOrDisableFieldsPanel.border = IdeBorderFactory.createTitledBorder("Enable or Disable Template Fields")
        enOrDisableFieldsPanel.add(useFlags)
        enOrDisableFieldsPanel.add(Spacer.getComponentSpacer())
        enOrDisableFieldsPanel.add(useSubjectLine)
        enOrDisableFieldsPanel.add(Spacer.getComponentSpacer())
        enOrDisableFieldsPanel.add(useTaskList)
        enOrDisableFieldsPanel.add(Spacer.getComponentSpacer())
        enOrDisableFieldsPanel.add(useBreakingList)
        enOrDisableFieldsPanel.add(Spacer.getComponentSpacer())
        enOrDisableFieldsPanel.add(useToDoList)
        enOrDisableFieldsPanel.add(Spacer.getComponentSpacer())
        enOrDisableFieldsPanel.add(useRelatedReference)
        enOrDisableFieldsPanel.add(Spacer.getComponentSpacer())
        enOrDisableFieldsPanel.add(useResolvesReference)
        enOrDisableFieldsPanel.add(Spacer.getComponentSpacer())
        enOrDisableFieldsPanel.add(useReleaseReference)
        enOrDisableFieldsPanel.add(Spacer.getComponentSpacer())
        enOrDisableFieldsPanel.add(useDependsReference)
        enOrDisableFieldsPanel.add(Spacer.getComponentSpacer())
        containerPanel.add(enOrDisableFieldsPanel)
        containerPanel.add(Spacer.getComponentSpacer())

        val labelConstraints = GridBagConstraints()
        labelConstraints.gridwidth = 1
        labelConstraints.anchor = GridBagConstraints.LINE_START
        val inputConstraints = GridBagConstraints()
        inputConstraints.gridwidth = 3
        inputConstraints.anchor = GridBagConstraints.LINE_END
        inputConstraints.fill = GridBagConstraints.HORIZONTAL


        val listIndicatorPanel = JPanel()
        listIndicatorPanel.layout = GridBagLayout()
        listIndicatorPanel.border = IdeBorderFactory.createTitledBorder("List Indicators")
        SettingsInputField.getLabelWithInput(
            "Bulletpoint Indicator",
            bulletPointInput,
            listIndicatorPanel
        )
        SettingsInputField.getLabelWithInput(
            "Issue Number Indicator",
            issueIndicatorInput,
            listIndicatorPanel
        )
        containerPanel.add(listIndicatorPanel)
        containerPanel.add(Spacer.getComponentSpacer())

        val changeTypesPanel = JPanel()
        changeTypesPanel.layout = GridLayout(1, 2, Constants.largeSpace, Constants.largeSpace)
        changeTypesPanel.border = IdeBorderFactory.createTitledBorder("Change Types")
        SettingsInputField.getLabelWithInput(
            "Define the change Types",
            changeTypesInput,
            changeTypesPanel
        )
        containerPanel.add(changeTypesPanel)
        containerPanel.add(Spacer.getComponentSpacer())

        val labelsPanel = JPanel()
        labelsPanel.layout = GridLayout(7, 2, Constants.largeSpace, Constants.largeSpace)
        labelsPanel.border = IdeBorderFactory.createTitledBorder("Labels")
        SettingsInputField.getLabelWithInput(
            "Label for tasks",
            labelForTasksInput,
            labelsPanel
        )
        SettingsInputField.getLabelWithInput(
            "Label for Breaking Changes",
            labelForBreakingChangesInput,
            labelsPanel
        )
        SettingsInputField.getLabelWithInput(
            "Label for To-Do's",
            labelForTodosInput,
            labelsPanel
        )
        SettingsInputField.getLabelWithInput(
            "Label for Related IssueNo",
            labelForRelatedInput,
            labelsPanel
        )
        SettingsInputField.getLabelWithInput(
            "Label for Resolved IssueNo",
            labelForResolvesInput,
            labelsPanel
        )
        SettingsInputField.getLabelWithInput(
            "Label for Release Targets",
            labelForReleaseInput,
            labelsPanel
        )
        SettingsInputField.getLabelWithInput(
            "Label for Dependencies",
            labelForDependsInput,
            labelsPanel
        )
        containerPanel.add(labelsPanel)

        return containerPanel
    }

    fun bulletPoint(): String {
        return if (bulletPointInput.text != "") {
            bulletPointInput.text
        } else {
            MyBundle.getMessage("bulletPoint")
        }
    }
    fun issueIndicator(): String {
        return if (issueIndicatorInput.text != "") {
            issueIndicatorInput.text
        } else {
            MyBundle.getMessage("issueIndicator")
        }
    }
    fun changeTypes(): String {
        return if (changeTypesInput.text != "") {
            changeTypesInput.text
        } else {
            MyBundle.getMessage("changeTypes")
        }
    }
    fun labelForTasks(): String {
        return if (labelForTasksInput.text != "") {
            labelForTasksInput.text
        } else {
            MyBundle.getMessage("labelForTasks")
        }
    }
    fun labelForBreakingChanges(): String {
        return if (labelForBreakingChangesInput.text != "") {
            labelForBreakingChangesInput.text
        } else {
            MyBundle.getMessage("labelForBreakingChanges")
        }
    }
    fun labelForTodos(): String {
        return if (labelForTodosInput.text != "") {
            labelForTodosInput.text
        } else {
            MyBundle.getMessage("labelForTodos")
        }
    }
    fun labelForRelated(): String {
        return if (labelForRelatedInput.text != "") {
            labelForRelatedInput.text
        } else {
            MyBundle.getMessage("labelForRelated")
        }
    }
    fun labelForResolves(): String {
        return if (labelForResolvesInput.text != "") {
            labelForResolvesInput.text
        } else {
            MyBundle.getMessage("labelForResolves")
        }
    }
    fun labelForRelease(): String {
        return if (labelForReleaseInput.text != "") {
            labelForReleaseInput.text
        } else {
            MyBundle.getMessage("labelForRelease")
        }
    }
    fun labelForDepends(): String {
        return if (labelForDependsInput.text != "") {
            labelForDependsInput.text
        } else {
            MyBundle.getMessage("labelForDepends")
        }
    }
}