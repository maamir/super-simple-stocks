package uk.co.jpmorgan.app.module;

import uk.co.jpmorgan.logging.Log4jTypeListener;
import uk.co.jpmorgan.simple.stock.SimpleStockService;
import uk.co.jpmorgan.simple.stock.impl.SimpleStockServiceImpl;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * File: SimpleStockModule.java
 * 
 * Guice uses bindings to map types to their implementations.
 * A module is a collection of bindings specified using fluent.
 * 
 * @author r567514
 *
 */
public class SimpleStockModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(SimpleStockService.class).to(SimpleStockServiceImpl.class);
		bindListener(Matchers.any(), new Log4jTypeListener());
	}
}
