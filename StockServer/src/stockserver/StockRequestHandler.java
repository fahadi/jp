/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockserver;

import common.Bucket;
import common.Item;
import common.Transaction;
import io.DataRead;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import uility.Config;
import utility.MyClassLoader;


/**
 *This is the request handler from a client
 *The main server spawns this as a new Thread to handle multiple clients
 * @author fahadi
 */
public class StockRequestHandler extends Thread {

    private static final Logger LOG = Logger.getLogger(StockRequestHandler.class.getName());

    private Socket socket = null;
    private Bucket bucket = null;
    private DataRead dataRead = null;
    private List<Item> liveDataList; 

    public StockRequestHandler(Socket socket) {
        this.socket = socket;
        this.bucket = (Bucket)MyClassLoader.loadClass(Config.configProp.getProperty("bucket"));
        this.dataRead = (DataRead)MyClassLoader.loadClass(Config.configProp.getProperty("data.input.type"));
        this.liveDataList = new ArrayList<Item>();
        this.dataRead.dataRead(this.liveDataList);
    }
    
    
    
    @Override
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos=null;
        while (true) {
            try {
                InputStream is = this.socket.getInputStream();                
                ois = new ObjectInputStream(is);
                System.out.println("Recieved Connection");
                Object o = ois.readObject();
                Transaction t = null;
                if (o instanceof Transaction) {
                    t = (Transaction) o;
                    bucket.addItem(t);
                }
                if (o instanceof String) {
                	String str = (String)o;
                	if(!str.equals("E")){
	                	OutputStream os = this.socket.getOutputStream();
	                	oos = new ObjectOutputStream(os);
	                	oos.flush();
	                	oos.writeObject(this.liveDataList);
	                	oos.flush();
                	}
                	else{
                		break;
                	}
                	
                }
                
                System.out.println(bucket.toString());
            } catch (IOException ex) {
                
                Logger.getLogger(StockRequestHandler.class.getName()).
                        log(Level.SEVERE, null, ex);
                break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StockRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
        
        this.writeBuckToDisk();
        
        try {
			ois.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, null, e);
		}
        try {
			oos.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, null, e);
		}

    }
    
    /**
     * write stock items to disk as a final save
     */
    private void writeBuckToDisk() {
    	long id = generateRandomLong();
    	 try {

    		 String location = Config.configProp.getProperty("save.file.location");
    		 
    			 FileOutputStream fout = new FileOutputStream(location+String.valueOf(System.currentTimeMillis())+".dat");
    				ObjectOutputStream oos = new ObjectOutputStream(fout);   
    				oos.writeObject(bucket);
    				oos.close();
    				System.out.println("Done");

    		      } catch (IOException e) {
    		    		LOG.log(Level.SEVERE, null, e);
    		      }

    		}
	



	/**
     * generate unique id for file name
     * @return
     */
    private long generateRandomLong(){    	
    	   Random randomno = new Random();
    	   return randomno.nextLong();
    }

}


