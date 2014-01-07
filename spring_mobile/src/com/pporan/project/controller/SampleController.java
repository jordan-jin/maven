package com.pporan.project.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import configration.SpringSetting;

import pporan.maven.framework.data.EData;
import pporan.maven.framework.db.DatabaseHandler;

@Controller
public class SampleController {

	private Logger logger = Logger.getLogger(SampleController.class);
	
	DatabaseHandler databaseHandler;
	
	public SampleController(){
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringSetting.class);
		databaseHandler = ctx.getBean(DatabaseHandler.class);
	}
	
	
	
	@RequestMapping("/main")
	public ModelAndView _main(EData eMap){
//		public ModelAndView _main(Map<String, String> pathVariables){
		ModelAndView mav = new ModelAndView();
//		logger.debug("@@@@@@@@@@@ terminal = "+pathVariables.get("terminal"));
		logger.debug("@@@@@@@@@@@ terminal = "+eMap.get("terminal"));
		databaseHandler.selectItem(DatabaseHandler.DB_MSSQL, "Common.testQuery", eMap);
		
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
	
	@RequestMapping("/json/test.json")
	public JSONObject getShopInJSON(EData eMap) {
//		ModelAndView mav = new ModelAndView();
//		eMap.put("key1", "key1");
//		eMap.put("key2", "key2");
//		eMap.put("key3", "key3");
//		eMap.put("key4", "key4");
//		eMap.put("value1", "value1");
//		eMap.put("value2", "value2");
//		eMap.put("value3", "value3");
//		eMap.put("value4", "value4");
//		mav.addObject("data", JSONObject.fromObject(eMap));
		return JSONObject.fromObject(eMap);
 
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
