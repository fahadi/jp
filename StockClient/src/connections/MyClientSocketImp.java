/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;


import common.Transaction;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import uility.Configuration;

/**
 * This implementation knows how to communicate with the server 
 * @author fahadi
 */
public class MyClientSocketImp implements MySocket{
    
    
    private static final Logger LOG = Logger.getLogger(MyClientSocketImp.class.getName());
    private Socket client;
    private final String EXIT="E";
    private OutputStream outToServer=null;
    private InputStream inToClient=null;
    
    
    /**
     * configure this object so that it connects to the server using data from the properties files
     */
    public MyClientSocketImp(){
           try {
            this.client = new Socket(Configuration.configProp.getProperty("server"),
            		Integer.parseInt(Configuration.configProp.getProperty("server.port.number")));
            
            outToServer= this.client.getOutputStream();
            inToClient = this.client.getInputStream();
            
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Socket getSocket() {
        return this.client;
    }

    /**
     * Send the transaction data to the server 
     */
    @Override
    public void processTransaction(Transaction tx) {
    	ObjectOutputStream out=null;
        try {
            out = new ObjectOutputStream(outToServer);
            out.flush();
            out.writeObject(tx);
            out.flush();
            System.out.println("Sent Transaction");
            
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * Send signal to Server to send Live Stock data to client
     */
	@Override
	public Object recieveData() {
		ObjectOutputStream out=null;
		ObjectInputStream in =null;
        try {
            out = new ObjectOutputStream(outToServer);
            out.flush();
            out.writeObject("Give me List");
            out.flush();
            System.out.println("Request for list sent");
            
            in = new ObjectInputStream(inToClient);
            Object o = in.readObject();
            if(o instanceof List){
            	return o;
            }
            
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
			LOG.log(Level.SEVERE, null, e);
		} 
		return null;
	}
	
	/**
	 * send signal to Server that the client wants to disconnect
	 */

	@Override
	public void processExit() {
		ObjectOutputStream out=null;
        try {
            out = new ObjectOutputStream(outToServer);
            out.flush();
            out.writeObject(this.EXIT);
            out.flush();
            System.out.println("Sent Transaction");
            
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally{
        	try {
				outToServer.close();
			} catch (IOException e) {
				  LOG.log(Level.SEVERE, null, e);
			}
        	try {
				inToClient.close();
			} catch (IOException e) {
				  LOG.log(Level.SEVERE, null, e);
			}
        	
        }
		
	}


}
