package com.ardev.idroid.common.ext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class  ValuesUtils {

private static String TAG_START = "<resources";
private static String TAG_END = "</resources>";
private static String HEAD = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
private static String T_COLORS = "\t<color name=\"%1$s\">%2$s</color>\n";
private static String T_STRINGS = "\t<string name=\"%1$s\">%2$s</string>\n";
 
	
	
	public static void writeInFile( int position, String path,  String name, String value, String type){
		try {

			List<String> mlist = readData(path);

			BufferedWriter writer = new BufferedWriter(new FileWriter(path));

			for (int i = 0; i<mlist.size(); i++) {
				
				if (i == (position + 1)) writer.write(String.format( getFormat(), type,  attrNameFormat(name), getTypeValue(value)));
				
				else if(mlist.get(i).contains(TAG_END)) writer.write( "\n" + mlist.get(i));
				
				else writer.write(mlist.get(i).contains(TAG_START) ? HEAD + "\n" + mlist.get(i)+"\n\n" :"\t"+mlist.get(i)+"\n");


			}
			writer.close();

			//show
		} catch (Exception e) { }

	}	
	
	
	
	
	
	
	
	
	public static void addItemInFile( String path, String format, String name, String value){
	try {

List<String> mlist = readData(path);

BufferedWriter writer = new BufferedWriter(new FileWriter(path));
	
	for (int i = 0; i<mlist.size(); i++) {
		if (mlist.get(i).contains(TAG_END)) writer.write(String.format( format, attrNameFormat(name), getTypeValue(value)) + "\n" + mlist.get(i));
		
		else writer.write(mlist.get(i).contains(TAG_START) ? HEAD + "\n" + mlist.get(i) +"\n\n" :"\t"+mlist.get(i)+"\n");
			}
			writer.close();
				 
				 //show
} catch (Exception e) { }

			}	
	
	
	
	
	
	
	
	
	private static List<String> readData(String path){
	List<String> list = new ArrayList<>();
	try {
	
	BufferedReader reader = new BufferedReader(new FileReader(path));
	
	String line;
		
	while ((line = reader.readLine()) != null){

		if(!line.trim().isEmpty() && !line.trim().contains(HEAD)) list.add(line.trim());
	
		}
		reader.close();
	
	} catch (Exception e) {	}
     return list;
	   }
	
	
	
	
public static String attrNameFormat(String name) {

char first = String.valueOf(name.trim().charAt(0)).toLowerCase().toCharArray()[0];
	return name.trim().replace(name.charAt(0), first).replace(" ", "_");
		 
}

private static String getTypeValue(String value) {
return isColor(value) ? value.toUpperCase().trim() : value.trim();
}


public static boolean isAttrNameFormat(String name) {
return name.matches("^[0-9a-zA-Z_]+$");


}


 private static boolean isColor(String color) {
        return color.matches("^#([0-9a-fA-F]{3}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})$");
    }

private static String getFormat() {
return "\t<%1$s name=\"%2$s\">%3$s</%1$s>\n";
}

private String getColorAbrr( String color) {

return color.matches("^#([0-9a-fA-F]{3})$") ? (color + color.replace("#", "")) : "";
		
}



}