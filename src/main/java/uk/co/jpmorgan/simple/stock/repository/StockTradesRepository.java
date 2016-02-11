package uk.co.jpmorgan.simple.stock.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.co.jpmorgan.logging.annotation.InjectLogger;
import uk.co.jpmorgan.simple.stock.exception.SimpleStockException;
import uk.co.jpmorgan.simple.stock.model.Trade;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Singleton;

/**
 * File: StockTradesRepository.java
 * 
 * Repository to hold {@link Trade} and all actions related to {@link Trade}
 * which is get, add and size.
 * 
 * @author r567514
 *
 */
@Singleton
public class StockTradesRepository {

	/** Build trades with stock symbol **/
	private Map<String, List<Trade>> stockTrades;

	/** Default logger **/
	@InjectLogger
	private Logger logger;

	public StockTradesRepository() {
		stockTrades = Maps.<String, List<Trade>> newHashMap();
	}

	/**
	 * Returns the value to which the specified stockSymbol is mapped,
     * or {@code null} if this map contains no mapping for the stockSymbol.
     *  
	 * @param stockSymbol
	 * @return
	 */
	public List<Trade> getTrades(String stockSymbol) {
		return stockTrades.get(stockSymbol);
	}

	/**
	 * Returns a {@link Collection} of {@link Trade} 
	 * view of the values contained in this map.
	 * 
	 * @return
	 */
	public Collection<List<Trade>> getAllTrades() {
		return stockTrades.values();
	}

	/**
	 * Initialise and add trade for given stock.
	 * 
	 * @param trade
	 * @return
	 */
	public boolean addTrade(Trade trade) {
		try {
			if (trade != null) {
				String stockSymbol = trade.getStock().getStockSymbol();

				if (!stockTrades.containsKey(stockSymbol)) {
					stockTrades.put(stockSymbol, Lists.<Trade> newArrayList());
				}
				return stockTrades.get(stockSymbol).add(trade);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error adding trade", e);
			throw new SimpleStockException("Error adding trade" + e.getMessage(), e);
		}
		return false;
	}

	/**
	 * Return total number of trades for all stocks.
	 * 
	 * @return
	 */
	public int getTotalTrades() {
		int totalTrades = 0;
		for (List<Trade> trades : stockTrades.values()) {
			totalTrades += trades.size();
		}
		return totalTrades;
	}
	
	/**
	 * Return total number of trades for given stock.
	 * 
	 * @param stockSymbol
	 * @return
	 */
	public int getTotalTrades(String stockSymbol) {
		return stockTrades.get(stockSymbol).size();
	}

	/**
	 * Returns <tt>true</tt> if no trades been added/processed.
	 * 
	 * @return
	 */
	public boolean hasTrades() {
		return !stockTrades.isEmpty();
	}
}
