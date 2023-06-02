<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# plugin-phpstorm-typo3-commit-template Changelog

## [Unreleased]

## [2022.3.4] - 2023-04-03
Tasks:

- Make Plugin avaible for Version 2021 of PHPStorm

## [2022.3.3] - 2023-04-02
Tasks:

- Add newest Version 2023.1 to targeted versions

## [2022.3.2] - 2023-03-27
Tasks:

- Fix the extractIssueNo for PHPStorm
- Load lib git4idea

## [2022.3.1] - 2023-03-22
Tasks:

- Add null check for branch name util
- Remove MacOS Shortcuts
- New versioning style

## [1.0.6] - 2023-01-09

### Added
- Fixed Mac Shortcut error on startup
- Fixed show form
- Added settings panel
- Added persistent Settings

### Future Improvements
- Add Gitmoji Support (#18 )
- Add more settings to the reference list (like , seperated lists)  (#18 )
- Disable fields of the template from the settings (#18 )

## [1.0.5] - 2023-01-03

### Added
- Add tooltips and placeholders
- Fixed TextArea resizing pushes other content over the panel
- Auto-Add Star for focus into TextArea
- Auto-Add Star for each `return` press
- Enhance Action Icon
- Used deprecated method in GitUtils in order to save import of git4idea
- Added ´getIssueNoFromBranchName´ Button
- Added GitUtils for Project Branch name
- Fix mac shortcut

## [1.0.4] - 2022-12-21

### Added
- Refined regex patterns
- Fixed cancel Prompt results in Message Override
- Add multiple related issues function
- Add resolve, depends and release options based on guideline
- Fixed parsing of changelogs
- Parse Changelogs and replace dash with star

### General
- Fixed missing Link in Readme

### Code enhancements
- Local 'var' is never modified and can be declared as 'val'
- Redundant semicolon
- Unused import directive
- Redundant modality modifier
- Redundant visibility modifier
- Accessor call that can be replaced with property access syntax
- Join declaration and assignment

## [1.0.3] - 2022-12-21

### Added
- Keyboard Shortcuts (untested)
- Reopening Commit Template parses old commit Message into Dialog

## [1.0.1] - 2022-12-20

### Added
- Choose Type of Commit
- Subject Line
- Done tasks, breaking changes, to-do changelog
- Dynamic Flags based on Input

[Unreleased]: https://github.com/mai-space/plugin-phpstorm-typo3-commit-template/compare/v2022.3.4...HEAD
[2022.3.4]: https://github.com/mai-space/plugin-phpstorm-typo3-commit-template/compare/v2022.3.3...v2022.3.4
[2022.3.3]: https://github.com/mai-space/plugin-phpstorm-typo3-commit-template/compare/v2022.3.2...v2022.3.3
[2022.3.2]: https://github.com/mai-space/plugin-phpstorm-typo3-commit-template/compare/v2022.3.1...v2022.3.2
[2022.3.1]: https://github.com/mai-space/plugin-phpstorm-typo3-commit-template/compare/v1.0.6...v2022.3.1
[1.0.6]: https://github.com/mai-space/plugin-phpstorm-typo3-commit-template/compare/v1.0.5...v1.0.6
[1.0.5]: https://github.com/mai-space/plugin-phpstorm-typo3-commit-template/compare/v1.0.4...v1.0.5
[1.0.4]: https://github.com/mai-space/plugin-phpstorm-typo3-commit-template/compare/v1.0.3...v1.0.4
[1.0.3]: https://github.com/mai-space/plugin-phpstorm-typo3-commit-template/commits/v1.0.3
[1.0.1]: https://github.com/mai-space/plugin-phpstorm-typo3-commit-template/commit/2fe81ec28aeb953fdea4652ea969df56581a4cdc
