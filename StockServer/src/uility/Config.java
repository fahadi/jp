/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fahadi
 */
public class Config {
    
	static public Properties configProp = new Properties();
	
    public Config() throws IOException{
    	String propFileName = "server.properties";
    	
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		configProp.load(inputStream);
		if (inputStream == null) {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		inputStream.close();
		
		
    }
 
}
