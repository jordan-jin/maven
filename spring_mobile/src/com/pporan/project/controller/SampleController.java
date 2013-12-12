package com.pporan.project.controller;

import org.apache.log4j.Logger;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pporan.maven.framework.data.EData;

@Controller
public class SampleController {

	private Logger logger = Logger.getLogger(SampleController.class);
	
	@RequestMapping("/main")
	public ModelAndView _main(EData eMap){
		ModelAndView mav = new ModelAndView();
		logger.debug("@@@@@@@@@@@ terminal = "+eMap.get("terminal"));
		return mav;
	}
	
	@RequestMapping("/sample/sample")
	public ModelAndView _sample(EData eMap){
		ModelAndView mav = new ModelAndView();

		return mav;
	}
	
	@RequestMapping("/sample/sample2")
	@ResponseBody
	public String _sample2(EData eMap){
		String reStr = "";
		
		
		return reStr;
	}
	
	
}
