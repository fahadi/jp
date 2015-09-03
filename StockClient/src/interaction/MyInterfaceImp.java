/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interaction;

import common.Item;
import common.Transaction;


import common.TransactionType;
import connections.MySocket;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import uility.Configuration;
import utility.MyClassLoader;


/**
 * This human interface class can be replaced by another implementation, so that processing of selection stock quotes can be
 * handled as required example this could be replaced with an implementation that takes xml files as input.
 * 
 * @author fahadi
 *
 */
public class MyInterfaceImp implements MyInterface{
    
    private static final String EXIT= "E";
    private static final String BUY= "B";
    private static final String SELL= "S";
    private static final String DIVIDEND= "D";
    private static final String PE_RATION= "P";
    
    
    private MySocket mySocket= null;
    private static final Logger LOG = Logger.getLogger(MyInterfaceImp.class.getName());
    
    /**
     * Configure this object to use a specific MySocket implementation 
     * This will allow different socket behaviour to be plugged in as requirements change
     */
    public MyInterfaceImp(){
    	try {
    		mySocket =(MySocket)MyClassLoader.loadClass(Configuration.configProp.getProperty("machine.socket"));
		} catch (Exception e) {
			 LOG.log(Level.SEVERE, null, e);
		}
    	
    }

    /**
     * This method displays the user commands and signals the server to send live stock data 
     * It then calls the cLIOptions method so that it can wait for user input
     */
    @Override
    public void displayQuote() {
    	
    	this.displayUsage();
        System.out.println("Stock Quote Board");
        
        List<Item> list = null;
		list = (List<Item>)this.mySocket.recieveData();
        
        int count=0;
        for(Item item:list){
            System.out.print("("+count+") ");
            System.out.print("Stock Code \t"+item.getStockCode());
            System.out.println("Stock Price \t"+item.getStockPrice());
            count++;
        }
        this.cLIOptions(list);
    }

    /**
     * This method is used to process user commands from the command line 
     */
    @Override
    public void cLIOptions(List<Item> itemList) {
    	
  
        Scanner sc = new Scanner(System.in);
       
        while(sc.hasNextLine()) {
            int sizeOfItems = itemList.size();
            String [] tokens = sc.nextLine().split(" ");
            
            if(tokens.length > 3 ){
            	this.displayUsage();
                
            } else{
                
                try{
                    String firstParam = tokens[0].toUpperCase();
                    if(firstParam.equals(EXIT)){
                    	mySocket.processExit();
                        break;
                    }
                    String secParam = tokens[1].toUpperCase();
                    Integer listSelection = Integer.parseInt(firstParam);
                    if(secParam.equals(BUY) || secParam.equals(SELL)){
                        String thrdParam = tokens[2].toUpperCase();
                        Integer qty = Integer.parseInt(thrdParam);
                        Integer transType=null;
                        switch (secParam) {
                            case BUY:
                                transType = TransactionType.BUY;
                                break;
                            case SELL:
                                transType = TransactionType.SELL;
                                break;
                        }
                        
                        if(listSelection < itemList.size()){
                         mySocket.processTransaction(
                                 new Transaction(itemList.get(listSelection),qty,transType,new Date(System.currentTimeMillis())));
                        }
                         
                         
                    }else if(secParam.equals(DIVIDEND)){
                    	Item item = itemList.get(listSelection);
                    	double yield = (double) (item.getLastDividend()/item.getStockPrice());
                    	System.out.printf("The yield is %.2f",yield);
                        System.out.println("");
                    }else if(secParam.equals(PE_RATION)){
                    	Item item = itemList.get(listSelection);
                    	double yield = (double) (item.getStockPrice()/item.getLastDividend());
                    	System.out.printf("The P/E ratio is %.2f",yield);
                        System.out.println("");
                    }
                    
                }
                catch(NumberFormatException nfe){
                    LOG.log(Level.INFO,nfe.getLocalizedMessage());
                }
                catch(ArrayIndexOutOfBoundsException npe){
                    LOG.log(Level.INFO,npe.getLocalizedMessage());
                }
            }
        }
        
       
        try {
            mySocket.getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(MyInterfaceImp.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                mySocket.getSocket().close();
            } catch (IOException ex) {
                Logger.getLogger(MyInterfaceImp.class.getName()).log(Level.SEVERE, null, ex);
            }
             
        }       
    }
    
    private void displayUsage(){
    	System.out.println("########################################");
      	System.out.println("Usage : Enter E to Exit Client");
        System.out.println("Usage : Choose Stock Number, Enter B for Buying OR S for Selling, Enter Number for Quantity");
        System.out.println("Usage : Choose Stock Number, Enter D to calculate Dividend");
        System.out.println("Usage : Choose Stock Number, Enter P to calculate P/E Ratio");
        System.out.println("Usage : Example 1 B 157 ");
        System.out.println("########################################");
    	
    }
}
