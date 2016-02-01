package uk.co.jpmorgan.app;

import java.util.Date;
import java.util.List;
import java.util.Random;

import uk.co.jpmorgan.simple.stock.SimpleStockService;
import uk.co.jpmorgan.simple.stock.impl.SimpleStockServiceImpl;
import uk.co.jpmorgan.simple.stock.model.Stock;
import uk.co.jpmorgan.simple.stock.model.Trade;
import uk.co.jpmorgan.simple.stock.model.Trade.TradeTypeIndicator;
import uk.co.jpmorgan.simple.stock.util.StockDataGenerator;

public class Application {

	private static SimpleStockService simpleStockService = new SimpleStockServiceImpl(); 
	
	public static void main(String[] args) {		
		List<Stock> stocks = StockDataGenerator.getInstance().getStocks();

		for(Stock stock : stocks){
			doTrade(stock);
		}
		
		for(Stock stock: stocks) {
			Random random = new Random();
			
			System.out.println(String.format("****************** %s **************", stock.getStockSymbol()));
			System.out.println(String.format("Dividend yield: %.2f", simpleStockService.calculateDividendYield(stock, random.nextDouble())));
			System.out.println(String.format("PE Ratio: %.2f", simpleStockService.calculatePERatio(stock, random.nextDouble())));
			System.out.println(String.format("Stock Price: %.2f", simpleStockService.calculateStockPrice(stock.getStockSymbol())));
		}
		
		System.out.println("--------------- GBCE -------------");
		System.out.println(simpleStockService.calculateGBCEShareindex());
	}

	private static void doTrade(Stock stock) {
		Random rand = new Random();
		
		Trade trade = new Trade(stock, rand.nextInt(100), rand.nextDouble() + 102 , new Date(), rand.nextBoolean() ? TradeTypeIndicator.BUY : TradeTypeIndicator.SELL);
		simpleStockService.recordTrade(trade);
	}

}
