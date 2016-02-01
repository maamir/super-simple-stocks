package uk.co.jpmorgan.simple.stock.model;

/**
 * File: Stock.java
 * 
 * Object contains all variables requires to represent Stock.
 * 
 * @author r567514
 *
 */
public class Stock {

	private String stockSymbol;
	private StockType stockType;
	private double lastDividend;
	private double fixedDividend;
	private double parValue;
	private double price;
	
	public Stock(){}

	public Stock(String stockSymbol, StockType stockType, double lastDividend,
			double fixedDividend, double parValue, double price) {
		super();
		this.stockSymbol = stockSymbol;
		this.stockType = stockType;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
		this.price = price;
	}

	/**
	 * @return the stockSymbol
	 */
	public String getStockSymbol() {
		return stockSymbol;
	}



	/**
	 * @param stockSymbol the stockSymbol to set
	 */
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}



	/**
	 * @return the stockType
	 */
	public StockType getStockType() {
		return stockType;
	}



	/**
	 * @param stockType the stockType to set
	 */
	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}



	/**
	 * @return the lastDividend
	 */
	public double getLastDividend() {
		return lastDividend;
	}



	/**
	 * @param lastDividend the lastDividend to set
	 */
	public void setLastDividend(double lastDividend) {
		this.lastDividend = lastDividend;
	}



	/**
	 * @return the fixedDividend
	 */
	public double getFixedDividend() {
		return fixedDividend;
	}



	/**
	 * @param fixedDividend the fixedDividend to set
	 */
	public void setFixedDividend(double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}



	/**
	 * @return the parValue
	 */
	public double getParValue() {
		return parValue;
	}


	/**
	 * @param parValue the parValue to set
	 */
	public void setParValue(double parValue) {
		this.parValue = parValue;
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
	
	public double getDividendYield() {
		double dividendYield = 0.00;
		
		if(StockType.COMMON.equals(stockType)) {
			dividendYield = lastDividend / price;
		} else { // Preferred
			dividendYield = (fixedDividend * parValue) / price;
		}
		
		return dividendYield;
	}
	
	public double getPERatio() {
		return price / getDividendYield();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Stock [stockSymbol=" + stockSymbol + ", stockType=" + stockType
				+ ", lastDividend=" + lastDividend + ", fixedDividend="
				+ fixedDividend + ", parValue=" + parValue + ", price="
				+ price + ", DividendYield=" + getDividendYield()
				+ ", PERatio=" + getPERatio() + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fixedDividend);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lastDividend);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(parValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((stockSymbol == null) ? 0 : stockSymbol.hashCode());
		result = prime * result
				+ ((stockType == null) ? 0 : stockType.hashCode());
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Stock other = (Stock) obj;
		if (Double.doubleToLongBits(fixedDividend) != Double
				.doubleToLongBits(other.fixedDividend))
			return false;
		if (Double.doubleToLongBits(lastDividend) != Double
				.doubleToLongBits(other.lastDividend))
			return false;
		if (Double.doubleToLongBits(parValue) != Double
				.doubleToLongBits(other.parValue))
			return false;
		if (stockSymbol == null) {
			if (other.stockSymbol != null)
				return false;
		} else if (!stockSymbol.equals(other.stockSymbol))
			return false;
		if (stockType != other.stockType)
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		return true;
	}


	public enum StockType {
		COMMON("Common"), PREFFERED("Preferred");
		
		private String description;
		private StockType(String description){
			this.description = description;
		}
		
		public String getDescription(){
			return description;
		}
	}
}
