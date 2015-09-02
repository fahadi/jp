/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author fahadi
 */
@XmlRootElement(name = "stockItems")
@XmlAccessorType (XmlAccessType.FIELD)
public class StockItemImp implements Item, Serializable {
	private static final long serialVersionUID = 1710160340203674668L;

	private String stockCode;
	private Float stockPrice;
	private Integer lastDivident;

	
	@Override
	public String getStockCode() {
		return this.stockCode;
	}

	@Override
	public Float getStockPrice() {
		return this.stockPrice;
	}

	@XmlElement
	@Override
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	@XmlElement
	@Override
	public void setStockPrice(Float price) {
		this.stockPrice = price;
	}

	@XmlElement
	@Override
	public void setLastDividend(Integer dividend) {
		this.lastDivident = dividend;
		
	}

	@Override
	public Integer getLastDividend() {
		return this.lastDivident ;
	}

}
