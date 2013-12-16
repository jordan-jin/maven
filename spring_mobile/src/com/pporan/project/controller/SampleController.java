package com.pporan.project.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pporan.maven.framework.data.EData;

@Controller
public class SampleController {

	private Logger logger = Logger.getLogger(SampleController.class);
	
	@RequestMapping("/main")
	public ModelAndView _main(EData eMap){
//		public ModelAndView _main(Map<String, String> pathVariables){
		ModelAndView mav = new ModelAndView();
//		logger.debug("@@@@@@@@@@@ terminal = "+pathVariables.get("terminal"));
		logger.debug("@@@@@@@@@@@ terminal = "+eMap.get("terminal"));
		return mav;
	}
	
	@RequestMapping("/main2")
	public ModelAndView _main2(HttpServletRequest req){
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	@RequestMapping("/sample/sample")
	public ModelAndView _sample(EData eMap){
		ModelAndView mav = new ModelAndView();

		return mav;
	}
	
	@RequestMapping("/sample/sample2")
	@ResponseBody
	public String _sample2(HttpServletRequest req){
		String reStr = "";
		
		
		return reStr;
	}
//	@RequestMapping("/main")
//	public ModelAndView _main(EData eMap){
//		ModelAndView mav = new ModelAndView();
//		logger.debug("@@@@@@@@@@@ terminal = "+eMap.get("terminal"));
//		return mav;
//	}
//	
//	@RequestMapping("/sample/sample")
//	public ModelAndView _sample(EData eMap){
//		ModelAndView mav = new ModelAndView();
//		
//		return mav;
//	}
//	
//	@RequestMapping("/sample/sample2")
//	@ResponseBody
//	public String _sample2(EData eMap){
//		String reStr = "";
//		
//		
//		return reStr;
//	}
	
	
}
