/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;


import common.Transaction;


import java.net.Socket;

/**
 *
 * @author fahadi
 */
public interface MySocket {
    
    public Socket getSocket();
    public void processTransaction(Transaction tx);
    public void processExit();
    public Object recieveData(); 
 
    
}
