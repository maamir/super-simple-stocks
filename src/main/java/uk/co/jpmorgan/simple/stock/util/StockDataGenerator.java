package uk.co.jpmorgan.simple.stock.util;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;

import uk.co.jpmorgan.simple.stock.model.Stock;
import uk.co.jpmorgan.simple.stock.model.Stock.StockType;

/**
 * File: StockDataGenerator.java
 * 
 * It generates stock data or {@link StockDataGenerator#createStock(String, StockType, int, Double, int)}
 * can be used to generate stock.
 * 
 * @author r567514
 *
 */
@Singleton
public final class StockDataGenerator {

	private List<Stock> stocks = new ArrayList<Stock>();
	
	/**
	 * Data setup from table in document
	 */
	public StockDataGenerator() {
		createStock("TEA", StockType.COMMON, 0d, 0d, 100d, 150d);
		createStock("POP", StockType.COMMON, 8d, 0d, 100d, 215d);
		createStock("ALE", StockType.COMMON, 23d, 0d, 60d, 87d);
		createStock("GIN", StockType.PREFFERED, 8d, 2.00, 100d, 23.66d);
		createStock("JOE", StockType.COMMON, 13d, 0d, 250d, 55d);
	}
	
	/**
	 * Create stocks and add to stock list.
	 * 
	 * @param stockSymbol
	 * @param type
	 * @param lastDividend
	 * @param fixedDividend
	 * @param parValue
	 */
	public void createStock(String stockSymbol, StockType stockType, double lastDividend, double fixedDividend, double parValue, double tickerPrice) {
		Stock stock = new Stock(stockSymbol, stockType, lastDividend, fixedDividend, parValue, tickerPrice);
		stocks.add(stock);
	}
	
	/**
	 * Return all sticks
	 * @return
	 */
	public List<Stock> getStocks(){
		return stocks;
	}
}
