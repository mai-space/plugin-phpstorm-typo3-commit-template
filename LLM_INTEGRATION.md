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
3. Open the template dialog with `Control + Shift + M`
4. Click the **"Generate with AI"** button at the top of the dialog
5. The AI will analyze your git changes and populate the enabled fields

## How It Works

1. The plugin extracts the git diff of your staged and unstaged changes
2. Sends the diff to Ollama with your configured prompts
3. Receives AI-generated suggestions for:
   - Subject line (if enabled)
   - Task list (if enabled)
4. Populates the fields in the template dialog
5. You can review and edit before applying

## Customizing Prompts

You can customize the prompts in settings to match your commit style:

**Example Subject Prompt:**
```
Based on the following git diff, generate a concise commit message subject line (max 50 characters) following TYPO3 conventions:
```

**Example Body Prompt:**
```
Based on the following git diff, generate bullet points describing the changes made in TYPO3 changelog format:
```

## Notes

- The plugin limits the git diff to 4000 characters to avoid overwhelming the LLM
- Only enabled fields (configured in settings) will be populated
- You must have Ollama running locally for this feature to work
- The generation happens in the background with a progress indicator
