package com.github.inf166.pluginphpstormtypo3committemplate.components

import com.github.inf166.pluginphpstormtypo3committemplate.MyBundle
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.JTextField

class TemplateSettingsForm {
    var bulletPointInput: JTextField = SettingsInputField.getInputField(
        "*",
        "Will be shown in the Changelog Lists"
    )
    var issueIndicatorInput: JTextField = SettingsInputField.getInputField(
        "#",
        "Will be shown in the Reference Lists"
    )
    var changeTypesInput: JTextField = SettingsInputField.getInputField(
        "FEATURE,TASK,BUGFIX,SECURITY,DOCS",
        "Will be shown in the Dropdown for change Types"
    )
    var labelForTasksInput: JTextField = SettingsInputField.getInputField(
        "Tasks:",
        "Will be the Title for the done Tasks Changelog"
    )
    var labelForBreakingChangesInput: JTextField = SettingsInputField.getInputField(
        "Breaking-Changes:",
        "Will be the Title for the breaking Changes Changelog"
    )
    var labelForTodosInput: JTextField = SettingsInputField.getInputField(
        "To-Do's:",
        "Will be the Title for the To-Do's List"
    )
    var labelForRelatedInput: JTextField = SettingsInputField.getInputField(
        "Related:",
        "Will be the identifier for the related Reference"
    )
    var labelForResolvesInput: JTextField = SettingsInputField.getInputField(
        "Resolves:",
        "Will be the identifier for the resolves Reference"
    )
    var labelForReleaseInput: JTextField = SettingsInputField.getInputField(
        "Releases:",
        "Will be the identifier for the releases Reference"
    )
    var labelForDependsInput: JTextField = SettingsInputField.getInputField(
        "Depends:",
        "Will be the identifier for the depends Reference"
    )
    fun createForm() : JPanel {
        val containerPanel = JPanel()
        containerPanel.layout = BoxLayout(containerPanel, BoxLayout.PAGE_AXIS)
        val etchedBorder = BorderFactory.createEtchedBorder()

        val listIndicatorPanel = JPanel()
        listIndicatorPanel.layout = BoxLayout(listIndicatorPanel, BoxLayout.PAGE_AXIS)
        listIndicatorPanel.border = BorderFactory.createTitledBorder(etchedBorder,"List Indicators")
        listIndicatorPanel.add(SettingsInputField.getLabelWithInput(
            "Bulletpoint Indicator",
            bulletPointInput
        ))
        listIndicatorPanel.add(Spacer.getComponentSpacer())
        listIndicatorPanel.add(SettingsInputField.getLabelWithInput(
            "Issue Number Indicator",
            issueIndicatorInput
        ))
        containerPanel.add(listIndicatorPanel)
        containerPanel.add(Spacer.getComponentSpacer())

        val changeTypesPanel = JPanel()
        changeTypesPanel.layout = BoxLayout(changeTypesPanel, BoxLayout.PAGE_AXIS)
        changeTypesPanel.border = BorderFactory.createTitledBorder(etchedBorder,"Change Types")
        changeTypesPanel.add(Spacer.getComponentSpacer())
        changeTypesPanel.add(SettingsInputField.getLabelWithInput(
            "Define the change Types",
            changeTypesInput
        ))
        containerPanel.add(changeTypesPanel)
        containerPanel.add(Spacer.getComponentSpacer())

        val labelsPanel = JPanel()
        labelsPanel.layout = BoxLayout(labelsPanel, BoxLayout.PAGE_AXIS)
        labelsPanel.border = BorderFactory.createTitledBorder(etchedBorder,"Labels")
        labelsPanel.add(Spacer.getComponentSpacer())
        labelsPanel.add(SettingsInputField.getLabelWithInput(
            "Label for tasks",
            labelForTasksInput
        ))
        labelsPanel.add(Spacer.getComponentSpacer())
        labelsPanel.add(SettingsInputField.getLabelWithInput(
            "Label for Breaking Changes",
            labelForBreakingChangesInput
        ))
        labelsPanel.add(Spacer.getComponentSpacer())
        labelsPanel.add(SettingsInputField.getLabelWithInput(
            "Label for To-Do's",
            labelForTodosInput
        ))
        labelsPanel.add(Spacer.getComponentSpacer())
        labelsPanel.add(SettingsInputField.getLabelWithInput(
            "Label for Related IssueNo",
            labelForRelatedInput
        ))
        labelsPanel.add(Spacer.getComponentSpacer())
        labelsPanel.add(SettingsInputField.getLabelWithInput(
            "Label for Resolved IssueNo",
            labelForResolvesInput
        ))
        labelsPanel.add(Spacer.getComponentSpacer())
        labelsPanel.add(SettingsInputField.getLabelWithInput(
            "Label for Release Targets",
            labelForReleaseInput
        ))
        labelsPanel.add(Spacer.getComponentSpacer())
        labelsPanel.add(SettingsInputField.getLabelWithInput(
            "Label for Dependencies",
            labelForDependsInput
        ))
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