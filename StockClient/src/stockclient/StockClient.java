/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockclient;


import interaction.MyInterface;
import java.io.IOException;
import uility.Configuration;
import utility.MyClassLoader;
/**
 * This class loads the required client human interface that will interact with the server. 
 * @author fahadi
 */
public class StockClient {
    
    private MyInterface myInterface=null;
    

    public static void main(String[] args) throws IOException {
        new StockClient().setup();
        
    }
    
    public void setup() throws IOException{
    	new Configuration();
        myInterface = (MyInterface) MyClassLoader.loadClass(Configuration.configProp.getProperty("machine.interface"));
        myInterface.displayQuote();
    }
    
}
