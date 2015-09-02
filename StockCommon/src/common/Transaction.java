/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author fahadi
 */
@XmlRootElement(name = "transaction")
@XmlAccessorType (XmlAccessType.FIELD)
public class Transaction implements Serializable {
	private static final long serialVersionUID = 6114872353798187790L;

	private Item item;
	private Integer qty;
	private Integer tx;
	private Date tStamp;

	public Transaction(){
		
	}
	
	public Transaction(Item item, Integer qty, Integer tx, Date date) {
		this.item = item;
		this.qty = qty;
		this.tx = tx;
		this.tStamp = date;

	}

	
	public Item getItem() {
		return item;
	}

	@XmlElement
	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getQty() {
		return qty;
	}

	@XmlElement
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getTx() {
		return tx;
	}

	@XmlElement
	public void setTx(Integer tx) {
		this.tx = tx;
	}

	/**
	 * @return the tStamp
	 */
	public Date gettStamp() {
		return tStamp;
	}

	/**
	 * @param tStamp
	 *            the tStamp to set
	 */
	@XmlElement
	public void settStamp(Date tStamp) {
		this.tStamp = tStamp;
	}

}
