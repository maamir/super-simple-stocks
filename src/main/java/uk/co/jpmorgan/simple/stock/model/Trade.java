package uk.co.jpmorgan.simple.stock.model;

import java.util.Date;

/**
 * File: Trade.java
 * 
 * Object to capture/record trade details.
 * 
 * @author r567514
 *
 */
public class Trade {

	private Stock stock;
	private int quantity;
	private double price;
	private Date tradeTime;
	private TradeTypeIndicator tradeIndicator = TradeTypeIndicator.BUY;
	
	public Trade() {}	

	public Trade(Stock stock, int quantity, double price, Date tradeTime,
			TradeTypeIndicator tradeIndicator) {
		super();
		this.stock = stock;
		this.quantity = quantity;
		this.price = price;
		this.tradeTime = tradeTime;
		this.tradeIndicator = tradeIndicator;
	}

	/**
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}



	/**
	 * @param stock the stock to set
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
	}



	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}



	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}



	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}



	/**
	 * @return the tradeTime
	 */
	public Date getTradeTime() {
		return tradeTime;
	}



	/**
	 * @param tradeTime the tradeTime to set
	 */
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}



	/**
	 * @return the tradeIndicator
	 */
	public TradeTypeIndicator getTradeIndicator() {
		return tradeIndicator;
	}



	/**
	 * @param tradeIndicator the tradeIndicator to set
	 */
	public void setTradeIndicator(TradeTypeIndicator tradeIndicator) {
		this.tradeIndicator = tradeIndicator;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		result = prime * result
				+ ((tradeIndicator == null) ? 0 : tradeIndicator.hashCode());
		result = prime * result
				+ ((tradeTime == null) ? 0 : tradeTime.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		if (tradeIndicator != other.tradeIndicator)
			return false;
		if (tradeTime == null) {
			if (other.tradeTime != null)
				return false;
		} else if (!tradeTime.equals(other.tradeTime))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Trade [stock=" + stock + ", quantity=" + quantity + ", price="
				+ price + ", tradeTime=" + tradeTime + ", tradeIndicator="
				+ tradeIndicator + "]";
	}

	public enum TradeTypeIndicator {
		BUY, SELL
	}
}
