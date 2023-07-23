package com.ardev.tools.parser;

import com.ardev.idroid.ext.ColorUtils;
import com.ardev.idroid.ui.main.fragment.preview.model.Item;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.Toast;
import java.util.List;
import android.app.Activity;
import java.util.concurrent.Executors;
import android.content.Context;

public class ValuesXmlParser {
	Activity activity;
	private File file;
	String tag;
	public static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
	private static final String RESOURCES = "resources";
	private static final String NAME = "name";
	private OnParseListener listener;
	private XmlPullParser parser;
	List<Item> list = new ArrayList<Item>();

	public ValuesXmlParser(Activity activity) {
		this.activity = activity;

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			parser = factory.newPullParser();

		} catch (Exception e) {

		}

	}

	public ValuesXmlParser setParser(String path) {

		this.file = new File(path);

		Executors.newSingleThreadExecutor().execute(() -> {

	if (file.exists() && file.isFile()) workParsing(file);

	activity.runOnUiThread(() -> { if (listener != null) listener.onFinish(list); } );


		});
		return this;

	}

	public void workParsing(File path) {

		try {

			Reader reader = new FileReader(path);

			parser.setInput(reader);
			parser.nextTag();
			readResources(parser);
			reader.close();

		} catch (Exception e) {

		}

	}

	private void readResources(XmlPullParser parser) throws XmlPullParserException, IOException {

		parser.require(XmlPullParser.START_TAG, null, RESOURCES);
		while (parser.next() != XmlPullParser.END_TAG) {
	if (parser.getEventType() != XmlPullParser.START_TAG) continue;
	
			String name = parser.getName();
			
				if (name.equals("string")) readString(parser, name);
			else if (name.equals("dimen")) 	readDimen(parser, name);
  			else if (name.equals("integer")) 	readInteger(parser, name);
  			  else if (name.equals("bool")) 	readBool(parser, name);
		    else if (name.equals("color")) 	readColor(parser, name);
		    else if (name.equals("item")) readItem(parser, name);
		
			else skip(parser);
			
				
			

		}

	}
		
		private void readAttribute(XmlPullParser parser, String typeTag) throws XmlPullParserException, IOException {
		
	parser.require(XmlPullParser.START_TAG, null, typeTag);
		String name = parser.getAttributeValue(null, NAME);
		int line = parser.getLineNumber();
		String value = getText(parser);
	parser.require(XmlPullParser.END_TAG, null, typeTag);
		
	list.add(new Item(file.getPath(), name, value, typeTag, line));
	}

		private void readString(XmlPullParser parser, String typeTag) throws XmlPullParserException, IOException {
		
	parser.require(XmlPullParser.START_TAG, null, "string");
		String name = parser.getAttributeValue(null, NAME);
		String translatable = parser.getAttributeValue(null, "translatable");
		int line = parser.getLineNumber();
		String value = getText(parser);
	parser.require(XmlPullParser.END_TAG, null, "string");
		
	list.add(new Item(file.getPath(), name, value, typeTag, line));
	}
	
		private void readDimen(XmlPullParser parser, String typeTag) throws XmlPullParserException, IOException {
		
	parser.require(XmlPullParser.START_TAG, null, "dimen");
		String name = parser.getAttributeValue(null, NAME);
		int line = parser.getLineNumber();
		String value = getText(parser);
	parser.require(XmlPullParser.END_TAG, null, "dimen");
		
	list.add(new Item(file.getPath(), name, value, typeTag, line));
	}

		private void readInteger(XmlPullParser parser, String typeTag) throws XmlPullParserException, IOException {
		
	parser.require(XmlPullParser.START_TAG, null, "integer");
		String name = parser.getAttributeValue(null, NAME);
		int line = parser.getLineNumber();
		String value = getText(parser);
	parser.require(XmlPullParser.END_TAG, null, "integer");
		
	list.add(new Item(file.getPath(), name, value, typeTag, line));
	}
	
		private void readBool(XmlPullParser parser, String typeTag) throws XmlPullParserException, IOException {
		
	parser.require(XmlPullParser.START_TAG, null, "bool");
		String name = parser.getAttributeValue(null, NAME);
		int line = parser.getLineNumber();
		String value = getText(parser);
	parser.require(XmlPullParser.END_TAG, null, "bool");
		
	list.add(new Item(file.getPath(), name, value, typeTag, line));
	}
	
		private void readColor(XmlPullParser parser, String typeTag) throws XmlPullParserException, IOException {
		
	parser.require(XmlPullParser.START_TAG, null, "color");
		String name = parser.getAttributeValue(null, NAME);
		int line = parser.getLineNumber();
		String value = getText(parser);
	parser.require(XmlPullParser.END_TAG, null, "color");
	
		if(ColorUtils.isColorFormat(value)) {
	list.add(new Item(file.getPath(), name, value, typeTag, line));
		}
	}
	
		private void  readItem(XmlPullParser parser, String typeTag) throws XmlPullParserException, IOException {
		
	parser.require(XmlPullParser.START_TAG, null, typeTag);
		String name = parser.getAttributeValue(null, NAME);
		String translatable = parser.getAttributeValue(null, "translatable");
		String type = parser.getAttributeValue(null, "type");
		int line = parser.getLineNumber();
		String value = getText(parser);
	parser.require(XmlPullParser.END_TAG, null, typeTag);
	
		if( type.equals("color") && ColorUtils.isColorFormat(value)) {
	list.add(new Item(file.getPath(), name, value, type, line));
	
		} else {
	list.add(new Item(file.getPath(), name, value, type, line));
	}
	
	}

	

	private String getText(XmlPullParser parser) throws IOException, XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {

			result = parser.getText();

			parser.nextTag();
		}
		return result;
	}
	

	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

	public ValuesXmlParser setOnParseListener(OnParseListener listener) {
		this.listener = listener;
		return this;
	}

	public interface OnParseListener {
		void onFinish(List<Item> list);
	}

}