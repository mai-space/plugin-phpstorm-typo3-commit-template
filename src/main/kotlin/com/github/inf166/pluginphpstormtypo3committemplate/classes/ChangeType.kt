package com.github.inf166.pluginphpstormtypo3committemplate.classes

enum class ChangeType(val title: String, val description: String) {
    FEATURE(
        "Features",
        "A new feature (also small additions)"
    ),
    DOCS(
        "Documentations",
        "This tag is used for changes regarding the documentation"
    ),
    BUGFIX(
        "Bugfixes",
        "A fix for a bug"
    ),
    SECURITY(
        "Security",
        "Visualizes that a change fixes a security issue. This tag is used by the Security Team."
    ),
    TASK(
        "Tasks",
        "Anything not covered by the above categories. E.g. Refactoring of a component"
    );

    fun label(): String {
        return String.format("[%s]", name)
    }

    override fun toString(): String {
        return String.format("[%s] - %s", label(), description)
    }
}