package com.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
 
public class TranslateReadJSONFile {
	public static List<Object> getTimeStamp(File f,String targetWord) {
		List<Object> time = new ArrayList<Object>();
		
		String fileName = f.getName()+"translate";
		String address = System.getProperty("user.home") + "\\Documents\\VideoSearch\\";
		String jsonfile = address + fileName + ".json";
		
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader(jsonfile));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray jsonArray = (JSONArray) jsonObject.get("text");
			
			for(int i=0; i<jsonArray.size();i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				String word = result.get("단어").toString();
				
				String s1 = word.toUpperCase();
				String s2 = targetWord.toUpperCase();

				if ( s2.equals(s1)) {
					time.add(result.get("시작 시간"));
				}
			}
			
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return time;
	}
}