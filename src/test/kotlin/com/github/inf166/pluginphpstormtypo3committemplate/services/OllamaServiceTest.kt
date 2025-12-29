package com.github.inf166.pluginphpstormtypo3committemplate.services

import org.junit.Assert.assertEquals
import org.junit.Test

class OllamaServiceTest {

    @Test
    fun testRemoveMarkdownBolding() {
        val input = "This is **bold** and this is __also bold__ and this is ***bold and italic***."
        val expected = "This is bold and this is also bold and this is *bold and italic*."
        assertEquals(expected, OllamaService.removeMarkdownBolding(input))
    }

    @Test
    fun testRemoveMarkdownBoldingNoMatch() {
        val input = "This is just normal text."
        val expected = "This is just normal text."
        assertEquals(expected, OllamaService.removeMarkdownBolding(input))
    }

    @Test
    fun testRemoveMarkdownBoldingUnbalanced() {
        val input = "This is **bold but not closed"
        val expected = "This is **bold but not closed"
        assertEquals(expected, OllamaService.removeMarkdownBolding(input))
    }
}
