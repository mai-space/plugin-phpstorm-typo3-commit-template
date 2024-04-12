# TYPO3 Commit Template

![Build](https://github.com/mai-space/plugin-phpstorm-typo3-commit-template/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/com.github.inf166.pluginphpstormtypo3committemplate.svg)](https://plugins.jetbrains.com/plugin/20719-commit-template-with-typo3-commit-message-guidelines)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/com.github.inf166.pluginphpstormtypo3committemplate.svg)](https://plugins.jetbrains.com/plugin/20719-commit-template-with-typo3-commit-message-guidelines)

<!-- Plugin description -->
Inspired by the commit guidelines of TYPO3 [here](https://docs.typo3.org/m/typo3/guide-contributionworkflow/main/en-us/Appendix/CommitMessage.html).

## Usage

Open your Commit-Dialog by clicking the module or pressing `Control/CMD + K`.<br>
You can open the template dialog by pressing `Control + Shift + K`.<br>

<img src="https://raw.githubusercontent.com/mai-space/plugin-phpstorm-typo3-commit-template/main/src/main/resources/images/shortcut-to-dialog.png" alt="shortcut-to-dialog" width="550">
<br>

Once you are in the dialog, you can navigate by using the `Tab` Key.<br>
Fill the form as you see fit.<br>

<img src="https://raw.githubusercontent.com/mai-space/plugin-phpstorm-typo3-commit-template/main/src/main/resources/images/open-dialog.png" alt="dialog" width="550">
<br>

If you are happy with your commit message, press `Apply` and the message will be templated
and inserted into your commit message dialog.<br>

<img src="https://raw.githubusercontent.com/mai-space/plugin-phpstorm-typo3-commit-template/main/src/main/resources/images/filled-commit-message.png" alt="filled_dialog" width="550">
<br>

Please note, that the template is dynamic. So filling breaking changes will result in the `[!!!]` flag being set.<br>
This also works with the to-do list which results in the `[WIP]` flag being set.<br>
If you do not want any flags, just fill the `Done Tasks` Section, so only the Type will be displayed.<br>
As you can see in the Image above, by clicking the branch icon next to the resolves and related issue number list, the current issue number in the currently checked out branch will be inserted. For Example a branch named like this "feature/12345-a-new-feature" will result in an issue number "12345" being inserted into the relative inputfield. But when applied, will have the format #12345.<br>
If you use your keyboard to traverse the dialog window, you will automatically get a new list item (* ) for each focus into an empty textarea or by pressing the return key, while you are focussed. If you leave a text area that only contains an empty list item (* ) it will be deleted.<br>
If the Task-list becomes too long horizontal, the text will wrap. If it becomes to long vertically, you can scroll up and down inside the textarea.<br>
For every Element in the Dialog, a placeholder shows you the suggested format and while hovering, you get a nice descriptive tool tip.<br>
The Commit message gets parsed, after you hit `Apply` which leads to any list item starting with "- " to become "* " and the issue numbers will all be listed in separate lines and starting with an #. For more Information on why that is, please read the Guideline Documentation of TYPO3.<br>

<img src="https://raw.githubusercontent.com/mai-space/plugin-phpstorm-typo3-commit-template/main/src/main/resources/images/inserted-commit-message.png" alt="message" width="550"><br>

If you want to fix a typo or add items, simply click the TYPO3 Icon or hit the Shortcut again. Your Commit Message will be reopend and can be edited.<br>

<img src="https://raw.githubusercontent.com/mai-space/plugin-phpstorm-typo3-commit-template/main/src/main/resources/images/fix-commit-message.png" alt="message" width="550"><br>

<img src="https://raw.githubusercontent.com/mai-space/plugin-phpstorm-typo3-commit-template/main/src/main/resources/images/fixed-commit-message.png" alt="message" width="550"><br>

## Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "plugin-phpstorm-typo3-commit-template"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/mai-space/plugin-phpstorm-typo3-commit-template/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

<!-- Plugin description end -->

---
## Acknowledgements

### Origins
This Plugin has already once been made by me. But I wanted to refactor not only my Jetbrains Marketplace Account but also the Code Base and Design of the Plugin.
If you want to take a look at the previous Code: [Check it out here.](https://github.com/mai-space/plugin-phpstorm-commit-template)

### Plugin based on the IntelliJ Platform Plugin Template

You can make your own Plugin using this [Template.](https://github.com/JetBrains/intellij-platform-plugin-template)
