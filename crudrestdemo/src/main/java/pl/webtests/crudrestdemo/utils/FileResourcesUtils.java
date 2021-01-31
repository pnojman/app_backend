package pl.webtests.crudrestdemo.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileResourcesUtils {
	
	protected JSONObject deserializeJsonFileToJsonObject(String pathFile) throws ParseException {
	    	
	    	InputStream is = getFileFromResourceAsStream(pathFile);
			String jsonString = new BufferedReader(
				      new InputStreamReader(is, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
			
			
			JSONParser parser = new JSONParser();
			return (JSONObject) parser.parse(jsonString);
	 }
	
	private InputStream getFileFromResourceAsStream(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
	}
        
}
