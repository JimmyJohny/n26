/**
 * 
 */
package com.n26.challenge.util;

import java.util.List;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.expression.ExpressionLanguage;
import net.sf.oval.expression.ExpressionLanguageJavaScriptImpl;

/**
 * @author jimmy
 *
 */

public class BeanValidator extends Validator {

	private static ExpressionLanguage js = new ExpressionLanguageJavaScriptImpl();

	public BeanValidator() {
		getExpressionLanguageRegistry().registerExpressionLanguage("js", js);
	}

	@Override
	public List<ConstraintViolation> validate(Object object) {

		try {
			return super.validate(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
