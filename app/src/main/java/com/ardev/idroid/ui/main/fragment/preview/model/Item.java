package com.ardev.idroid.ui.main.fragment.preview.model;

import io.github.rosemoe.sora.widget.CodeEditor;

public class Item {
	String filePath, name, value, type;
	int lineNumber;

	public Item(String filePath, String name, String value, String type, int lineNumber) {
		this.filePath = filePath;
		this.name = name;
		this.value = value;
		this.type = type;
		this.lineNumber = lineNumber;
	}

	public String getFilePath() {

		return filePath;
	}

	public String getName() {

		return name;
	}

	public String getValue() {
		return value;
	}
	
	public String getType() {

		return type;
	}

	public int getLineNumber() {

		return lineNumber;
	}
}