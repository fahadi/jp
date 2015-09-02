/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author fahadi
 */
@XmlRootElement(name = "transactions")
@XmlAccessorType (XmlAccessType.FIELD)
public class StockBucketImp implements Bucket, Serializable {
	private static final long serialVersionUID = 7755456426051861850L;

	@XmlElementWrapper
	@XmlAnyElement
	private final List<Transaction> itemList;

	public StockBucketImp() {
        this.itemList = new ArrayList<>();
    }

	@Override
	public void addItem(Transaction transacton) {
		itemList.add(transacton);
	}

	@Override
	public void removeItem(Transaction transaction) {
		itemList.remove(transaction);
	}

	@Override
	public String toString() {
		if(!itemList.isEmpty()){
			StringBuilder sb = new StringBuilder();
			sb.append("Bought Stock");
			sb.append("\n");
			for (Transaction t : itemList) {
				sb.append(t.getItem().getStockCode());
				sb.append(" ");
				sb.append(t.getItem().getStockPrice());
				sb.append(" ");
				sb.append(t.getQty());
				sb.append(" ");
				sb.append((t.getTx() == 1) ? "BUY" : "SELL");
				sb.append(" ");
				sb.append(t.gettStamp());
				sb.append("\n");
			}
			return sb.toString();
		}
		return "";
	}

}
