package uk.co.jpmorgan.app;

import uk.co.jpmorgan.app.module.SimpleStockModule;
import uk.co.jpmorgan.simple.stock.trade.TradeProcessor;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * File: Application.java
 * 
 * Main execution performed here and all classes instance
 * get created from Google {@link Guice}.
 * 
 * @author Muhammad Aamir
 *
 */
public class Application {
	
	/**
	 * The modules are the building blocks of an injector, 
	 * which is Guice's object-graph builder. First we create 
	 * the injector, and then we can use that to build the TradeProcessor.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {		
		Injector injector = Guice.createInjector(new SimpleStockModule());
		TradeProcessor processor = injector.getInstance(TradeProcessor.class);
		processor.doTrade();
	}
}
