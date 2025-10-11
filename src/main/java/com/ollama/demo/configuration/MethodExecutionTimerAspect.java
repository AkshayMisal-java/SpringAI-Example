package com.ollama.demo.configuration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class MethodExecutionTimerAspect {

    @Around("@annotation(TrackExecutionTime)") // Custom annotation for methods to track
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to miliseconds

        System.out.println("Method " + joinPoint.getSignature().getName() + " executed in " + duration + " ms");
        // If controller returns ModelAndView or uses ModelMap, add attribute
        if (result instanceof ModelAndView mav) {
            mav.addObject("executionTimeMs", duration);
            return mav;
        }
        
        // If controller method returns a String (view name)
        // and has Model as parameter, Spring will already have that model
        // so we just set it in RequestScope for Thymeleaf access
		try {
			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (attrs != null) {
				HttpServletRequest request = attrs.getRequest();
				request.setAttribute("executionTimeMs", duration + " ms");
			}
		} catch (Exception ignored) {
		}
		
		// If already ExecutionResponse, set duration
//	    if (result instanceof ExecutionResponse) {
//	        ExecutionResponse<?> er = (ExecutionResponse<?>) result;
//	        er.setExecutionTimeMs(durationMs);
//	        return er;
//	    }
        
        return result;
    }
}