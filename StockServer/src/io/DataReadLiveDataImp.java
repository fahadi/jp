package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import stockserver.StockRequestHandler;
import uility.Config;
import utility.MyClassLoader;

import common.Item;
/**
 * 
 * @author fahadi
 *
 */
public class DataReadLiveDataImp implements DataRead {
	private static final Logger LOG = Logger
			.getLogger(DataReadLiveDataImp.class.getName());
	

	
	
	
	/**
	 * DataRead reads stock and price data from file and adds this to the Item Object
	 * If required the whole reading of data could be more abstract by creating a DAOFactory
	 * The reading of data is very simple as required for this exercise
	 */
	@Override
	public void dataRead(List<Item> itemList) {
		String dataFile = Config.configProp.getProperty("data.file");
		
		FileInputStream inputStream=null;
		
		BufferedReader reader = null;
		
		if(itemList==null){
			itemList = new ArrayList<Item>();
		}
		else{
			itemList.clear();
		}
		
		try {
			
			inputStream = new FileInputStream(dataFile);
			if (inputStream == null)
				throw new FileNotFoundException("File '" + dataFile
						+ "' not found in the classpath");
			else{
				reader = new BufferedReader(new InputStreamReader(inputStream));
				String line = reader.readLine();
		            while(line != null){
		            	String [] lineList = line.split(",");
		            	
		            	Item item = (Item)MyClassLoader.loadClass(Config.configProp.getProperty("item.type"));
		            	item.setStockCode(lineList[0]);
		            	item.setLastDividend(new Integer(Integer.parseInt(lineList[1])));
		            	item.setStockPrice(new Float(Float.parseFloat(lineList[2])));
		            	itemList.add(item);
		            	
		                line = reader.readLine();
		            }			
	           	}
		} catch (FileNotFoundException e) {
			Logger.getLogger(StockRequestHandler.class.getName()).log(
					Level.SEVERE, null, e);
		} catch (IOException e) {
			Logger.getLogger(StockRequestHandler.class.getName()).log(
					Level.SEVERE, null, e);
		} finally {
			try {
				reader.close();
				inputStream.close();
			} catch (IOException e) {
				Logger.getLogger(StockRequestHandler.class.getName()).log(
						Level.SEVERE, null, e);
			}
		}

		
	}
	
}