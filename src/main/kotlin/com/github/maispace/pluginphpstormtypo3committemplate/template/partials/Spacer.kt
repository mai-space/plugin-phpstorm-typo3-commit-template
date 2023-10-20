package com.github.maispace.pluginphpstormtypo3committemplate.template.partials

import com.github.maispace.pluginphpstormtypo3committemplate.utilities.Constants
import java.awt.Component
import java.awt.Dimension
import javax.swing.Box

class Spacer {
    companion object {
        fun getLabelSpacer(): Component? {
            return Box.createRigidArea(Dimension(Constants.NOSPACER, Constants.SMALLSPACER))
        }
        fun getComponentSpacer(): Component? {
            return Box.createRigidArea(Dimension(Constants.NOSPACER, Constants.LARGESPACER))
        }
    }
}