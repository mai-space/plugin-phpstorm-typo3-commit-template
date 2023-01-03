<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# plugin-phpstorm-typo3-commit-template Changelog

## [Unreleased]

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

[Unreleased]: https://github.com/Inf166/plugin-phpstorm-typo3-commit-template/compare/v1.0.5...HEAD
[1.0.5]: https://github.com/Inf166/plugin-phpstorm-typo3-commit-template/compare/v1.0.4...v1.0.5
[1.0.4]: https://github.com/Inf166/plugin-phpstorm-typo3-commit-template/compare/v1.0.3...v1.0.4
[1.0.3]: https://github.com/Inf166/plugin-phpstorm-typo3-commit-template/commits/v1.0.3
[1.0.1]: https://github.com/Inf166/plugin-phpstorm-typo3-commit-template/commit/2fe81ec28aeb953fdea4652ea969df56581a4cdc
