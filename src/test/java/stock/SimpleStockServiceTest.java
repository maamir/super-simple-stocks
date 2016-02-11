package uk.co.jpmorgan.simple.stock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.co.jpmorgan.logging.Log4jTypeListener;
import uk.co.jpmorgan.simple.stock.impl.SimpleStockServiceImpl;
import uk.co.jpmorgan.simple.stock.model.Stock;
import uk.co.jpmorgan.simple.stock.model.Trade;
import uk.co.jpmorgan.simple.stock.model.Trade.TradeTypeIndicator;
import uk.co.jpmorgan.simple.stock.repository.StockTradesRepository;
import uk.co.jpmorgan.simple.stock.util.StockDataGenerator;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;

/**
 * File: SimpleStockServiceTest.java
 * 
 * @author r567514
 *
 */
public class SimpleStockServiceTest {

	private SimpleStockService simpleStockService;
	@Mock
	private StockTradesRepository stockTradesRepository;

	@Before
	public void setUpMocks() {
		MockitoAnnotations.initMocks(this);
		simpleStockService = new SimpleStockServiceImpl(stockTradesRepository);

		// Requires to inject logger
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bindListener(Matchers.any(), new Log4jTypeListener());
			}
		});
	
		injector.injectMembers(simpleStockService);
	}

	@Inject
	public void setSimpleStockService(SimpleStockService simpleStockService) {
		this.simpleStockService = simpleStockService;
	}

	@Test
	public void testRecordTrade() {
		assertFalse(simpleStockService.recordTrade(null));

		Trade trade = getMockTrades().get(0);
		when(stockTradesRepository.addTrade(trade)).thenReturn(false);
		assertFalse(simpleStockService.recordTrade(trade));

		when(stockTradesRepository.addTrade(trade)).thenReturn(true);
		assertTrue(simpleStockService.recordTrade(trade));

		reset(stockTradesRepository);
	}

	@Test
	public void testCalculateStockPrice() {
		assertTrue(simpleStockService.calculateStockPrice(null) == 0.0);
		assertTrue(simpleStockService.calculateStockPrice("N/A") == 0.0);

		when(stockTradesRepository.getTrades("TEA"))
				.thenReturn(getMockTrades());
		double stockPrice = simpleStockService.calculateStockPrice("TEA");
		assertFalse(stockPrice == 0.0);
		assertTrue(stockPrice > 0.0);
		assertTrue(stockPrice > 102.0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCalculateGBCEShareindex() {
		when(stockTradesRepository.getAllTrades()).thenReturn(null);
		assertTrue(simpleStockService.calculateGBCEShareindex() == 0.0);

		when(stockTradesRepository.getAllTrades()).thenReturn(
				Lists.<List<Trade>> newArrayList());
		assertTrue(simpleStockService.calculateGBCEShareindex() == 0.0);

		when(stockTradesRepository.hasTrades()).thenReturn(true);
		when(stockTradesRepository.getAllTrades()).thenReturn(
				Lists.<List<Trade>> newArrayList(getMockTrades()));
		double shareIndex = simpleStockService.calculateGBCEShareindex();
		assertFalse(shareIndex == 0.0);
		assertTrue(shareIndex > 0.0);
		assertTrue(shareIndex > 102.0);
	}

	private List<Trade> getMockTrades() {
		List<Trade> trades = Lists.<Trade> newArrayList();
		List<Stock> stocks = new StockDataGenerator().getStocks();
		Random rand = new Random();

		for (Stock stock : stocks) {
			trades.add(new Trade(stock, rand.nextInt(100),
					rand.nextDouble() + 102, new Date(),
					rand.nextBoolean() ? TradeTypeIndicator.BUY
							: TradeTypeIndicator.SELL));
		}

		return trades;
	}
}
