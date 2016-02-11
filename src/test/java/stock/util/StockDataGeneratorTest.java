package uk.co.jpmorgan.simple.stock.util;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import uk.co.jpmorgan.simple.stock.model.Stock.StockType;

/**
 * File: StockDataGeneratorTest.java
 * 
 * @author r567514
 *
 */
public class StockDataGeneratorTest {

	/** Test class **/
	private StockDataGenerator stockDataGenerator = new StockDataGenerator();
	
	@Test
	public void testCreateStock() {
		int initalSize = stockDataGenerator.getStocks().size();
		assertFalse(stockDataGenerator.getStocks().isEmpty());
		
		stockDataGenerator.createStock("TEA", StockType.COMMON, 0d, 0d, 100d, 150d);
		assertFalse(stockDataGenerator.getStocks().isEmpty());
		assertTrue(stockDataGenerator.getStocks().size() > initalSize);
	}	
}
