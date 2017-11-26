package com.eter.response.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.eter.response.CommonResponseConstant;
import com.eter.response.CommonResponseGenerator;
import com.eter.response.entity.CommonResponse;
import com.eter.response.exception.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GlobalErrorHandler implements HandlerExceptionResolver {

	@Autowired
	CommonResponseGenerator respGen;

	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		HandlerMethod hm = (HandlerMethod) handler;
		Logger log = LoggerFactory.getLogger(hm.getBeanType());
		log.error("- Controller throw exception {}.{}()", hm.getBeanType(), hm.getMethod().getName(), ex);
		if (AnnotationUtils.findAnnotation(hm.getBeanType(), RestController.class) != null) {
			return restControllerErrorHandler(ex);
		}
		return null;
	}

	private ModelAndView restControllerErrorHandler(Exception ex) {
		MappingJackson2JsonView mav = new MappingJackson2JsonView(jacksonObjectMapper);
		mav.setExtractValueFromSingleKeyModel(true);
		CommonResponse<Void> commonResponse;
		if (ex instanceof UserException) {
			UserException usEx = (UserException) ex;
			commonResponse = respGen.generateCommonResponse(usEx.getErrorCode(), usEx.getErrorDesc(), Void.class);
			
		} else {
			commonResponse = respGen.generateCommonResponse(CommonResponseConstant.GENERAL_ERROR_CODE, CommonResponseConstant.GENERAL_ERROR_DESC, Void.class);
		}
		mav.addStaticAttribute("response", commonResponse);
		return new ModelAndView(mav);
	}

}
