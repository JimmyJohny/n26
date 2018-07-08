/**
 * 
 */
package com.n26.challenge.util;


import java.util.List;

import net.sf.oval.ConstraintViolation;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * @author jimmy
 *
 */

public class ServiceInterceptor implements MethodInterceptor {

	private static BeanValidator beanValidator =new BeanValidator();

	@Override
	public Object invoke(MethodInvocation methodInvocation)  {
		try {

			Object[] args= methodInvocation.getArguments();
			if(args != null && args.length>0 && args[0] != null) {
				
				List<ConstraintViolation> violations =beanValidator.validate(args[0]);
				if(violations.size() >0){
					return HttpStatus.NO_CONTENT;
				}else 
					return methodInvocation.proceed();
			}else
			return methodInvocation.proceed();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return new ResponseEntity(HttpStatus.PRECONDITION_FAILED);
	}




}
