/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.Date;

/**
 * 
 * @author fahadi
 */
public interface Bucket {

	public void addItem(Transaction transaction);

	/**
	 * 
	 * @param transaction
	 */
	public void removeItem(Transaction transaction);

}