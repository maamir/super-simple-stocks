package uk.co.jpmorgan.simple.stock.repository;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uk.co.jpmorgan.simple.stock.model.Stock;
import uk.co.jpmorgan.simple.stock.model.Trade;
import uk.co.jpmorgan.simple.stock.model.Trade.TradeTypeIndicator;
import uk.co.jpmorgan.simple.stock.util.StockDataGenerator;

import com.google.common.collect.Lists;

/**
 * File: StockTradesRepositoryTest.java
 * 
 * @author r567514
 *
 */
public class StockTradesRepositoryTest {

	private StockTradesRepository repository;
	
	@Before
	public void setUp() {
		repository = new StockTradesRepository();
	}
	
	@Test
	public void testAddTrade() {
		//Initial checks
		assertTrue(repository.getTotalTrades() == 0);
		assertTrue(repository.getAllTrades().isEmpty());
		assertFalse(repository.hasTrades());
		
		assertFalse(repository.addTrade(null));
		
		Trade trade = getMockTrades().get(0);
		assertTrue(repository.addTrade(trade));
		assertFalse(repository.getAllTrades().isEmpty());
		assertTrue(repository.getTotalTrades() == 1);
	}
	
	@Test
	public void testGetTrades() {
		assertTrue(repository.getTrades(null) == null);
		
		Trade trade = getMockTrades().get(0);
		repository.addTrade(trade);
		List<Trade> trades = repository.getTrades(trade.getStock().getStockSymbol());
		assertTrue(trades != null);
		assertFalse(trades.isEmpty());
		assertTrue(trades.size() == 1);
		assertTrue(repository.hasTrades());
		assertTrue(trade.equals(trades.get(0)));
	}
	
	@Test
	public void testGetAllTrades() {
		assertTrue(repository.getAllTrades().isEmpty());
		
		Trade trade = getMockTrades().get(0);
		repository.addTrade(trade);
		assertFalse(repository.getAllTrades().isEmpty());
	}
	
	private List<Trade> getMockTrades() {
		List<Trade> trades = Lists.<Trade>newArrayList();
		List<Stock> stocks = new StockDataGenerator().getStocks();
		Random rand = new Random();
		
		for(Stock stock: stocks){
			trades.add(new Trade(stock, rand.nextInt(100), rand.nextDouble() + 102, new Date(),
					rand.nextBoolean() ? TradeTypeIndicator.BUY : TradeTypeIndicator.SELL));
		}
		
		return trades;
	}
}
