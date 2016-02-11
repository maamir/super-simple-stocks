package uk.co.jpmorgan.simple.stock.trade;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.co.jpmorgan.logging.annotation.InjectLogger;
import uk.co.jpmorgan.simple.stock.SimpleStockService;
import uk.co.jpmorgan.simple.stock.model.Stock;
import uk.co.jpmorgan.simple.stock.model.Trade;
import uk.co.jpmorgan.simple.stock.model.Trade.TradeTypeIndicator;
import uk.co.jpmorgan.simple.stock.util.StockDataGenerator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * File: TradeProcessor.java
 * 
 * This is responsible for creating trades and using {@link SimpleStockService}
 * calculates trade and stock variables and output to console.
 * 
 * @author Muhammad Aamir
 *
 */
@Singleton
public class TradeProcessor {

	/** The main service to process stock service **/
	private final SimpleStockService simpleStockService;

	/** The class for generating data **/
	private final StockDataGenerator stockDataGenerator;

	/** Default logger **/
	@InjectLogger
	private Logger logger;

	@Inject
	public TradeProcessor(SimpleStockService simpleStockService, StockDataGenerator stockDataGenerator) {
		this.simpleStockService = simpleStockService;
		this.stockDataGenerator = stockDataGenerator;
	}

	public void doTrade() {
		Random rand = new Random();
		List<Stock> stocks = stockDataGenerator.getStocks();

		try {
			for (Stock stock : stocks) {
				Trade trade = new Trade(stock, rand.nextInt(100),
						rand.nextDouble() + 102, new Date(),
						rand.nextBoolean() ? TradeTypeIndicator.BUY
								: TradeTypeIndicator.SELL);
				simpleStockService.recordTrade(trade);
			}

			for (Stock stock : stocks) {
				System.out.println(String.format(
						"****************** %s **************",
						stock.getStockSymbol()));
				System.out.println(String.format(
						"Dividend yield: %.2f",
						simpleStockService.calculateDividendYield(stock,
								rand.nextDouble())));
				System.out.println(String.format(
						"PE Ratio: %.2f",
						simpleStockService.calculatePERatio(stock,
								rand.nextDouble())));
				System.out.println(String.format("Stock Price: %.2f",
						simpleStockService.calculateStockPrice(stock
								.getStockSymbol())));
			}

			System.out.println("--------------- GBCE -------------");
			System.out.println(simpleStockService.calculateGBCEShareindex());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error processing trades", e);
		}
	}
}
