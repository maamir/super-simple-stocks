package uk.co.jpmorgan.simple.stock.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.co.jpmorgan.logging.annotation.InjectLogger;
import uk.co.jpmorgan.simple.stock.SimpleStockService;
import uk.co.jpmorgan.simple.stock.exception.SimpleStockException;
import uk.co.jpmorgan.simple.stock.model.Stock;
import uk.co.jpmorgan.simple.stock.model.Trade;
import uk.co.jpmorgan.simple.stock.repository.StockTradesRepository;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * File: SimpleStockServiceImpl.java
 * 
 * @author Muhammad Aamir
 *
 */
public class SimpleStockServiceImpl implements SimpleStockService {

	/** The repository for {@link Trade} **/
	private final StockTradesRepository stockTradesRepository;
	
	/** Default logger **/
	@InjectLogger 
	private Logger logger;
	
	@Inject
	public SimpleStockServiceImpl(StockTradesRepository stockTradesRepository) {
		this.stockTradesRepository = stockTradesRepository;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.co.jpmorgan.simple.stock.SimpleStockService#recordTrade(uk.co.jpmorgan
	 * .simple.stock.model.Trade)
	 */
	@Override
	public boolean recordTrade(Trade trade) {
		try {
		if (trade != null) {
			logger.info(String.format("Recoding trade %s", trade));
			return stockTradesRepository.addTrade(trade);
		}
		} catch(Exception e) {
			logger.log(Level.SEVERE, "Error occured while recording trade", e);
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
		List<Trade> trades = stockTradesRepository.getTrades(stockSymbol);

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

		if (stockTradesRepository.hasTrades()) {
			for (List<Trade> trades : stockTradesRepository.getAllTrades()) {
				for (Trade trade : trades) {
					gm = totalTrades > 0 ? (gm * trade.getPrice()) : trade
							.getPrice();
					totalTrades++;
				}
			}

			return Math.pow(gm, 1.0d / totalTrades);
		}
		return 0.0;
	}

	/*
	 * (non-Javadoc)
	 * @see uk.co.jpmorgan.simple.stock.SimpleStockService#calculateDividendYield(java.lang.String, double)
	 */
	@Override
	public double calculateDividendYield(Stock stock, double price) {
		if(stock == null) {
			logger.log(Level.WARNING, "No stock available to calcuate DividendYield");
			return 0.0;
		}
		if(price <= 0.0) {
			logger.severe("Price should be greater than 0");
			throw new SimpleStockException("Price should not be 0");
		}
		stock.setPrice(price);
		return stock.getDividendYield();
	}

	/*
	 * (non-Javadoc)
	 * @see uk.co.jpmorgan.simple.stock.SimpleStockService#calculatePERatio(java.lang.String, double)
	 */
	@Override
	public double calculatePERatio(Stock stock, double price) {
		if(stock == null) {
			logger.log(Level.WARNING, "No stock available to calcuate PE ratio");
			return 0.0;
		}
		if(price <= 0.0) {
			logger.severe("Price should be greater than 0");
			throw new SimpleStockException("Price should not be 0");
		}
		stock.setPrice(price);
		return stock.getDividendYield();
	}
}
