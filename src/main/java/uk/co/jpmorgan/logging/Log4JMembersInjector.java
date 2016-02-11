package uk.co.jpmorgan.logging;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import com.google.inject.MembersInjector;

/**
 * @author r567514
 *
 */
public class Log4JMembersInjector<T> implements MembersInjector<T> {

	private final Field field;
	private final Logger logger;

	Log4JMembersInjector(Field field) {
		this.field = field;
		this.logger = Logger.getLogger(field.getDeclaringClass().getName());
		field.setAccessible(true);
	}

	public void injectMembers(T t) {
		try {
			field.set(t, logger);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
