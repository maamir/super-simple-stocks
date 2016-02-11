package uk.co.jpmorgan.logging;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import uk.co.jpmorgan.logging.annotation.InjectLogger;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * @author r567514
 *
 */
public class Log4jTypeListener implements TypeListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.inject.spi.TypeListener#hear(com.google.inject.TypeLiteral,
	 * com.google.inject.spi.TypeEncounter)
	 */
	@Override
	public <T> void hear(TypeLiteral<T> typeLiteral,
			TypeEncounter<T> typeEncounter) {
		Class<?> clazz = typeLiteral.getRawType();

		while (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.getType() == Logger.class
						&& field.isAnnotationPresent(InjectLogger.class)) {
					typeEncounter.register(new Log4JMembersInjector<T>(field));
				}
			}

			clazz = clazz.getSuperclass();
		}
	}

}
