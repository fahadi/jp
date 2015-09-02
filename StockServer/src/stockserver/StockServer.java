/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stockserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import uility.Config;

/**
 *
 * @author fahadi
 */
public class StockServer extends Thread{

  private ServerSocket serverSocket;
  private Integer portNumber;
  private boolean stockServerRunning=false;
  private static StockServer stockServer=null;
 
  /**
   * constructor is private so only 1 server is returned
   */
  private StockServer(){
      
  }
  
    public static void main(String[] args) {
        StockServer ss = StockServer.getStockServerInstance();
        ss.startStockServer();
    }
  
    /**
     * Only one server on the Jvm will be returned
     * @return
     */
  public static StockServer getStockServerInstance(){
      if(stockServer==null){
    	  
          stockServer = new StockServer();
      }
      return stockServer;
  }
    
  /**
   * Start the server
   */
  public void startStockServer(){
	  Config config =null;
      try{
    	  config = new Config();   	  
          portNumber = Integer.parseInt(Config.configProp.getProperty("server.port.number"));
          this.serverSocket = new ServerSocket(portNumber);
          this.start();
      }
      catch(IOException e){
          e.printStackTrace();
      }
  }
  
  /**
   * Once a new client has connected spawn a new thread to handle the client connection request response
   */
  @Override
  public void run(){
      this.stockServerRunning=true;
      
      while(this.stockServerRunning){
          try{
              System.out.println("Listening for connection "+portNumber);
              Socket socket = serverSocket.accept();
              new StockRequestHandler(socket).start();
          }
          catch(IOException e){
            Logger.getLogger(StockRequestHandler.class.getName()).log(Level.SEVERE, null, e);
          }
      }
  }
  
}
