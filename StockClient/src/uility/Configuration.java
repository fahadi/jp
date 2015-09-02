package uility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Configuration {
	
	private static final Logger LOG = Logger.getLogger(Configuration.class.getName());
	
	static public Properties configProp = new Properties();
	
	public Configuration() throws IOException{
		
		String propFileName = "config.properties";
	
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		configProp.load(inputStream);
		if (inputStream == null) {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		inputStream.close();
		
		
	}
	
	

}
