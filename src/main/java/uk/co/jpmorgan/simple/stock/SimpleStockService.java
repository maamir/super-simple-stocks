package uk.co.jpmorgan.simple.stock;

import uk.co.jpmorgan.simple.stock.model.Stock;
import uk.co.jpmorgan.simple.stock.model.Trade;

public interface SimpleStockService {

	/**
	 * Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price.
	 * 
	 * @param trade
	 * @return
	 */
	boolean recordTrade(Trade trade);
	
	/**
	 * Calculate Volume Weighted Stock Price based on trades in past 15 minutes.
	 * 
	 * @param stockSymbol
	 * @return
	 */
	double calculateStockPrice(String stockSymbol);
	
	/**
	 * Calculate the GBCE All Share Index using the geometric mean of prices for all stocks.
	 * 
	 * @return
	 */
	double calculateGBCEShareindex();
	
	/**
	 * For given stock and price as input, calculate the dividend yield.
	 * 
	 * @param stock
	 * @param price
	 * @return
	 */
	double calculateDividendYield(Stock stock, double price);
	
	/**
	 * For given stock and price as input, calculate the pe ratio.
	 * 
	 * @param stock
	 * @param price
	 * @return
	 */
	double calculatePERatio(Stock stock, double price);
}
