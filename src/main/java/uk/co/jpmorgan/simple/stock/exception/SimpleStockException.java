package uk.co.jpmorgan.simple.stock.exception;

/**
 * File: SimpleStockException.java
 * 
 * @author Muhammad Aamir
 *
 */
public class SimpleStockException extends RuntimeException {

	private static final long serialVersionUID = -8445918631860673302L;

	public SimpleStockException() {
		super();
	}

	public SimpleStockException(String msg) {
		super(msg);
	}

	public SimpleStockException(String message, Throwable cause) {
		super(message, cause);
	}
}
