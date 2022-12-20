# plugin-phpstorm-typo3-commit-template

![Build](https://github.com/Inf166/plugin-phpstorm-typo3-commit-template/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)

<!-- Plugin description -->
This plugin allows to create a commit message with the following template:

```html
[<FLAGS>][<TYPE>] <SUBJECT>

Breaking Changes: (optional) 
<BREAKING CHANGES HERE>    
    
Tasks: (optional) 
<TASK LIST HERE&>

To-Do's: (optional) 
<TO-DOs HERE>

refs: #<TICKET NUMBER HERE>
```

From the commit guidelines of TYPO3 [here](https://docs.typo3.org/m/typo3/guide-contributionworkflow/main/en-us/Appendix/CommitMessage.html).

## Usage

Open your Commit-Dialog by clicking the module or pressing `Control/CMD + K`.<br>
You can open the template dialog by pressing `Control + Shift + K`.<br>

<img src="src/main/resources/images/open_dialog.png" alt="open_dialog" width="550">
<br>

Once you are in the dialog, you can navigate by using the `Tab` Key.<br>
Fill the form as you see fit.<br>

<img src="src/main/resources/images/dialog.png" alt="dialog" width="550">
<img src="src/main/resources/images/filled_dialog.png" alt="filled_dialog" width="550">
<br>

If you are happy with your commit message, press `Apply` and the message will be templated
and inserted into your commit message dialog<br>
Please note, that the template is dynamic. So filling breaking changes will result in the `[!!!]` flag being set.<br>
This also works with the to-do list which results in the `[WIP]` flag being set.<br>
If you do not want any flags, just fill the `Done Tasks` Section, so only the Type will be displayed.<br>

<img src="src/main/resources/images/inserted_dialog.png" alt="inserted_dialog" width="550"><br>

## Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "plugin-phpstorm-typo3-commit-template"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/Inf166/plugin-phpstorm-typo3-commit-template/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

<!-- Plugin description end -->

---
## Acknowledgements

### Origins
This Plugin has already once been made by me. But I wanted to refactor not only my Jetbrains Marketplace Account but also the Code Base and Design of the Plugin.
If you want to take a look at the previous Code: [Check it out here.](https://github.com/Inf166/plugin-phpstorm-commit-template)

### Git commit template Plugin for IntelliJ
Forked from: [[MobileTribe/commit-template-idea-plugin]](https://github.com/MobileTribe/commit-template-idea-plugin).
I used parts of his code, but heavily modified it and am planning on changing more.

### Plugin based on the IntelliJ Platform Plugin Template

You can make your own Plugin using this [Template.](https://github.com/JetBrains/intellij-platform-plugin-template)
