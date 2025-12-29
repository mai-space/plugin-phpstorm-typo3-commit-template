# LLM Integration Documentation

## Overview
This plugin now supports AI-powered commit message generation using a local LLM via Ollama.

## Configuration

### Settings Location
Navigate to: **Settings/Preferences** > **Tools** > **TYPO3 Commit Template** > **Ollama LLM Settings**

### Required Settings
1. **Ollama URL**: The URL of your local Ollama instance (default: `http://localhost:11434`)
2. **Ollama Model**: The name of the Ollama model to use (default: `llama3.2`)
3. **Subject Prompt**: Prompt used to generate the commit subject line
4. **Body Prompt**: Prompt used to generate the commit body/tasks

## Usage

1. Install and run Ollama locally:
   ```bash
   # Install Ollama from https://ollama.ai
   # Pull a model
   ollama pull llama3.2
   ```

2. Open the commit dialog with `Control/CMD + K`
3. Choose one of two ways to generate:
   - **Quick Generation**: Click the **"Generate with AI"** button next to the template button
   - **Template Dialog**: Open the template dialog with `Control + Shift + M`, fill in details, then click **"Generate with AI"** at the bottom
4. The AI will analyze your git changes and populate the enabled fields

## How It Works

1. The plugin extracts the git diff of your staged and unstaged changes
2. Sends the diff to Ollama with your configured prompts
3. The prompts strictly instruct the model to return only raw text (no formatting, explanations, or greetings)
4. Response is cleaned to remove any remaining LLM artifacts
5. Receives AI-generated suggestions for:
   - Subject line (if enabled)
   - Task list (if enabled)
6. Populates the fields directly or in the template dialog
7. You can review and edit before applying

## Two Generation Modes

### Direct Generation
Click the "Generate with AI" button in the commit dialog (next to "Show Template"):
- Instantly generates and populates commit message
- Uses your enabled fields and settings
- No dialog - direct insertion

### Template Dialog Generation
Open template dialog (`Ctrl+Shift+M`), then click "Generate with AI" at the bottom:
- Generates within the template
- Allows editing before applying
- Full control over all fields

## Customizing Prompts

You can customize the prompts in settings to match your commit style. The default prompts are optimized to return clean, raw text:

**Default Subject Prompt:**
```
Generate ONLY a concise commit message subject line (max 50 characters) from the git diff below. Reply with ONLY the subject line text, no explanations, no formatting (NO BOLDING), no quotes, no prefixes. Just the raw subject line text.
```

**Default Body Prompt:**
```
Generate ONLY bullet points describing the changes from the git diff below. Reply with ONLY the bullet points (one per line, starting with *), no explanations, no greeting, no conclusion, no extra text, no markdown formatting (NO BOLDING). Just the raw bullet point list.
```

## Text Cleaning

The plugin automatically cleans LLM responses to ensure high-quality output:

**Subject Line Cleaning:**
- Removes common prefixes ("Subject:", "Here is", etc.)
- Removes quotes and unnecessary punctuation
- Removes markdown bolding (**bold**, __bold__)
- Takes only the first meaningful line
- Strips trailing punctuation

**Body Text Cleaning:**
- Filters out conversational phrases ("Here are the changes", etc.)
- Removes wrapper text and explanations
- Removes markdown bolding (**bold**, __bold__)
- Ensures proper bullet point formatting
- Validates output structure

## Notes

- The plugin limits the git diff to 4000 characters to avoid overwhelming the LLM
- Only enabled fields (configured in settings) will be populated
- You must have Ollama running locally for this feature to work
- The generation happens in the background with a progress indicator
- Prompts are designed to minimize LLM artifacts, but cleaning logic provides additional safety
