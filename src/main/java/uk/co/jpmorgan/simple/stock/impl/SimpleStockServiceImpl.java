package uk.co.jpmorgan.simple.stock.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import uk.co.jpmorgan.simple.stock.SimpleStockService;
import uk.co.jpmorgan.simple.stock.model.Stock;
import uk.co.jpmorgan.simple.stock.model.Trade;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class SimpleStockServiceImpl implements SimpleStockService {

	/** Contains trades for stocks **/
	private Map<String, List<Trade>> stockTrades = new HashMap<String, List<Trade>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.co.jpmorgan.simple.stock.SimpleStockService#recordTrade(uk.co.jpmorgan
	 * .simple.stock.model.Trade)
	 */
	@Override
	public boolean recordTrade(Trade trade) {
		if (trade != null) {
			String stockSymbol = trade.getStock().getStockSymbol();

			if (!stockTrades.containsKey(stockSymbol)) {
				stockTrades.put(stockSymbol, Lists.<Trade> newArrayList());
			}
			return stockTrades.get(stockSymbol).add(trade);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.co.jpmorgan.simple.stock.SimpleStockService#calculateStockPrice(java
	 * .lang.String)
	 */
	@Override
	public double calculateStockPrice(String stockSymbol) {
		List<Trade> trades = stockTrades.get(stockSymbol);

		double stockPrice = 0.0;
		double tradePriceSummation = 0.0;
		int tradeStockQuantities = 0;

		if (!trades.isEmpty()) {
			Iterable<Trade> tradesPast15Minutes = Iterables.filter(trades,
					new Predicate<Trade>() {
						@Override
						public boolean apply(Trade trade) {
							long now = new Date().getTime();
							long tradeTime = trade.getTradeTime().getTime();

							// Difference of time should be less/equal 15
							// minutes.
							return TimeUnit.MILLISECONDS.toMinutes(now
									- tradeTime) <= 15;
						}

					});

			for (Trade trade : tradesPast15Minutes) {
				tradePriceSummation += (trade.getPrice() * trade.getQuantity());
				tradeStockQuantities += trade.getQuantity();
			}
		}

		if (tradeStockQuantities > 0) {
			stockPrice = tradePriceSummation / tradeStockQuantities;
		}

		return stockPrice;
	}

	/*
	 * (non-Javadoc)
	 * @see uk.co.jpmorgan.simple.stock.SimpleStockService#calculateGBCEShareindex()
	 */
	@Override
	public double calculateGBCEShareindex() {
		double gm = 0.0;
		int totalTrades = 0;
		
		for(List<Trade> trades : stockTrades.values()) {
			for(Trade trade: trades) {
				gm = totalTrades > 0 ? (gm * trade.getPrice()) : trade.getPrice(); 
				totalTrades++;
			}
		}
		
		return Math.pow(gm, 1/totalTrades);
	}

	/*
	 * (non-Javadoc)
	 * @see uk.co.jpmorgan.simple.stock.SimpleStockService#calculateDividendYield(java.lang.String, double)
	 */
	@Override
	public double calculateDividendYield(Stock stock, double price) {
		stock.setPrice(price);
		return stock.getDividendYield();
	}

	/*
	 * (non-Javadoc)
	 * @see uk.co.jpmorgan.simple.stock.SimpleStockService#calculatePERatio(java.lang.String, double)
	 */
	@Override
	public double calculatePERatio(Stock stock, double price) {
		stock.setPrice(price);
		return stock.getDividendYield();
	}

}
