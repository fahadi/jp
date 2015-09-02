/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package common;

/**
 * 
 * @author fahadi
 */
public interface Item {

	public String getStockCode();

	public Float getStockPrice();

	public void setStockCode(String stockCode);

	public void setStockPrice(Float price);
	
	public void setLastDividend(Integer dividend);
	
	public Integer getLastDividend();
}
