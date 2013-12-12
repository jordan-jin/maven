/**
 * @author jinbos
 * @version 00.00.01
 * @date 2012.11.09
 * @category kr.co.etribe.framework.log
 * @description DB 이외의 보관용 Data를 위해 생성되는 Data Log이며 Log파일로 생성된다. 생성기준은 날짜기준이다(yyyyMMdd)
 */
package pporan.maven.framework.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import pporan.maven.framework.data.EData;


public class ELog {
	
	private static ResourceBundle rb = ResourceBundle.getBundle("properties.ELogConfig");
	
	private static int log_level = Integer.parseInt(rb.getString("elog_level"));
	/**
	 * 
	 * @param eMap
	 * @param logType(String) : debug, info, error 
	 */
	public static synchronized void elogSet(EData eMap, String logType){
		int type_level = Integer.parseInt(rb.getString(logType));

		if(type_level >= log_level){
			ELogFactory elFactory = new ELogFactory();
			
			String traceId = elFactory.guidKey();
			elFactory.startTran(traceId, type_level+"");
			
			if(logType.equals("debug_level"))
				setDebug(elFactory, eMap);
			if(logType.equals("info_level"))
				setInfo(elFactory, eMap);
			if(logType.equals("error_level"))
				setError(elFactory, eMap);
			
			elFactory.endTran(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		}
			
	}
	

	private static void setDebug(ELogFactory elFactory, EData eMap) {
		
		String[] dKeys = rb.getString("debug").split(",");
				
		for(int i=0; i<dKeys.length; i++){
			elFactory.setElement(dKeys[i], eMap.getString(dKeys[i]));
		}
	}
	
	private static void setInfo(ELogFactory elFactory, EData eMap) {
		
		String[] iKeys = rb.getString("info").split(",");
		
		for(int i=0; i<iKeys.length; i++){
			elFactory.setElement(iKeys[i], eMap.getString(iKeys[i]));
		}
	}
	
	private static void setError(ELogFactory elFactory, EData eMap) {
		
		String[] eKeys = rb.getString("error").split(",");
		for(int i=0; i<eKeys.length; i++){
			elFactory.setElement(eKeys[i], eMap.getString(eKeys[i]));
		}
	}
}
