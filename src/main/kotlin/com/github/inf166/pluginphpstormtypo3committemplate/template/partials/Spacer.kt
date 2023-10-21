package com.github.inf166.pluginphpstormtypo3committemplate.template.partials

import com.github.inf166.pluginphpstormtypo3committemplate.utilities.Constants
import java.awt.Component
import java.awt.Dimension
import javax.swing.Box

class Spacer {
    companion object {
        fun getLabelSpacer(): Component? {
            return Box.createRigidArea(Dimension(Constants.NO_SPACE, Constants.SMALL_SPACE))
        }
        fun getComponentSpacer(): Component? {
            return Box.createRigidArea(Dimension(Constants.NO_SPACE, Constants.LARGE_SPACE))
        }
    }
}