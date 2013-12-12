/**
 * @author jinbos
 * @version 00.00.01
 * @date 2012.11.09
 * @category kr.co.etribe.framework.log
 * @description DB 이외의 보관용 Data를 위해 생성되는 Data Log이며 Log파일로 생성된다. 생성기준은 날짜기준이다(yyyyMMdd)
 */
package pporan.maven.framework.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.ResourceBundle;


import org.apache.log4j.Logger;

import pporan.maven.framework.util.PropertiesConfiguration;


public class ELogFactory{
	private static Logger logger = Logger.getLogger(ELogFactory.class);
	/*--------------------------------------------------------------------------*/
	/* 전역 변수 선언 (환경변수) */
	/*--------------------------------------------------------------------------*/
	private String isELogThisLogInd = ""; 		/* Log표시 */
	private String isELogErrLogPath = ""; 		/* Err Log표시 */
	private String isELogWriteInd = ""; 			/* TLO Log표시 */
	private String isELogLogPath = ""; 			/* Log Home */
	private String isELogServiceName = ""; 		/* Service Code */
	private String isELogServerCode = ""; 		/* Server Code */
	private String isELogServerID = ""; 			/* Server Code */
	private String isELogWriteTerm = ""; 		/* 파일생성주기 */
	/*--------------------------------------------------------------------------*/
	/* 전역 변수 선언 */
	/*--------------------------------------------------------------------------*/
	private final String lsSep = "|"; 			/* 로그내 구분자 */
	private String lsLogInd = ""; 				/* LOG_IND */
	private String lsLogFileName = ""; 			/* Log FileName */
	private String lsLogDirName = ""; 			/* Log Directory */
	private String lsSeparator = ""; 			/* Separator */
	private StringBuffer sb = new StringBuffer(); /* StringBuffer */
	private Random rand = new Random(); 		/* Random */	
	private String seqNumber = "0000";

	public ELogFactory(){
		
		try{
			/*--------------------------------------------------------------------------*/
			/* default_path 가져오기 */
			/*--------------------------------------------------------------------------*/
			String config_path = PropertiesConfiguration.getString("conf.properties", "elog_path");
			logger.debug("config_path>>>"+config_path);

			prtLog("Initialize");
			/*------------------------------------------------------------------*/
			/* 운영체제(UNIX, DOS)에 따른 Separator \, / */
			/*------------------------------------------------------------------*/
			lsSeparator = File.separator;
			
			prtLog("ElogConfig.properties 파일=" + config_path);
			
			InitConfig(config_path);
		}catch(Exception err){
			prtErr("초기화중오류발생:" + err);
		}
	}

	// ElogConfig.properties 파일을 파일 경로 파라미터로 가지고 오는 함수...
	public ELogFactory(String config_path){
		try{
			prtLog("Initialize");			
			prtLog("ElogConfig.properties 파일=" + config_path);
			
			InitConfig(config_path);
		}catch(Exception err){
			prtErr("초기화중오류발생:" + err);
		}
	}
	
	private void InitConfig(String config_path){
		try{
			/*------------------------------------------------------------------*/
			/* 환경변수 존재 여부 체크 */
			/*------------------------------------------------------------------*/
			String lsIniLine = ""; /* 라인별내용 */

//			File loIniFile = new File(config_path);
//			if(!loIniFile.exists()) /* 존재여부 */
//			{
//				logger.debug(config_path + " 파일이 존재하지 않습니다.");
//				return;
//			}
			/*------------------------------------------------------------------*/
			/* ElogConfig.properties 파일 읽어 배열에 저장 */
			/*------------------------------------------------------------------*/
			ResourceBundle rb = ResourceBundle.getBundle(config_path);
			Enumeration en = rb.getKeys();
			while(en.hasMoreElements()){
				String key = (String) en.nextElement();
				if("ELOG_THIS_LOG_IND".equals(key)){
					isELogThisLogInd = rb.getString(key);
				}else if("ELOG_WRITE_IND".equals(key)){
					isELogWriteInd = rb.getString(key);
				}else if("ELOG_LOG_PATH".equals(key)){
					isELogLogPath = asc2ksc(rb.getString(key));
					prtLog("isELogLogPath=" + isELogLogPath);
				}else if("ELOG_ERR_LOG_PATH".equals(key)){
					isELogErrLogPath = asc2ksc(rb.getString(key));
				}else if("ELOG_SERVICE_NAME".equals(key)){
					isELogServiceName = rb.getString(key);
				}else if("ELOG_SERVER_CODE".equals(key)){
					isELogServerCode = rb.getString(key);
				}else if("ELOG_SERVER_ID".equals(key)){
					isELogServerID = rb.getString(key);
				}else if("ELOG_WRITE_TERM".equals(key)){
					isELogWriteTerm = rb.getString(key);
				}
			}
			/*------------------------------------------------------------------*/
			/* 환경변수가 환경파일에 없거나 값이 없을 경우 */
			/*------------------------------------------------------------------*/
			if("".equals(isELogThisLogInd)){
				isELogThisLogInd = "false";
				prtErr("환경변수 ELOG_THIS_LOG_IND가 존재 하지 않아 false로 설정 합니다.");
			}
			if("".equals(isELogWriteInd)){
				isELogWriteInd = "false";
				prtErr("환경변수 ELOG_WRITE_IND가 존재 하지 않아 false로 설정 합니다.");
			}
			if("".equals(isELogLogPath)){
				prtErr("환경변수 ELOG_LOG_PATH가 존재 하지 않습니다.");
			}
			if("".equals(isELogErrLogPath)){
				prtErr("환경변수 ELOG_ERR_LOG_PATH가 존재 하지 않습니다.");
			}
			if("".equals(isELogServiceName)){
				prtErr("환경변수 ELOG_SERVICE_NAME가 존재 하지 않습니다.");
			}
			if("".equals(isELogServerCode)){
				prtErr("환경변수 ELOG_SERVER_CODE가 존재 하지 않습니다.");
			}
			if("".equals(isELogServerID)){
				prtErr("환경변수 ELOG_SERVER_ID가 존재 하지 않습니다.");
			}
			if("".equals(isELogWriteTerm)){
				isELogWriteTerm = "5";
				prtErr("환경변수 ELOG_WRITE_TERM가 존재 하지 않아 5으로 설정 합니다.");
			}
			/*------------------------------------------------------------------*/
			/* ELOG_ERR_LOG_PATH 폴더 Make */
			/*------------------------------------------------------------------*/
			File loTloErrLogPathDir = new File(isELogErrLogPath);
			prtLog("ELOG_ERR_LOG_PATH=" + isELogErrLogPath);
			if(!(loTloErrLogPathDir.exists() && loTloErrLogPathDir.isDirectory())){
				loTloErrLogPathDir.mkdir();
				prtLog("ELOG_ERR_LOG_PATH 디렉토리가 존재하지 않아 생성");
			}
		}catch(Exception err){
			prtErr("초기화중오류발생:" + err);
		}
	}

	/*--------------------------------------------------------------------------*/
	/* 공통StartTran. Directory, File명 결정. 기본 log문장 저장 */
	/*--------------------------------------------------------------------------*/
	public void startTran(String asSeq_Id, String asLog_type){

		logger.debug("=============== startTran =================");

		lsLogInd = "1";	//startTran 시작되었음을 Initial
		
		/*----------------------------------------------------------------------*/
		/* ELOG_LOG_PATH 폴더 */
		/*----------------------------------------------------------------------*/
		File isELogLogPathDir = new File(isELogLogPath);
		prtLog("ELOG_LOG_PATH=" + isELogLogPathDir);
		if(!(isELogLogPathDir.exists() && isELogLogPathDir.isDirectory())){
			isELogLogPathDir.mkdir();
			prtLog("ELOG_LOG_PATH 디렉토리가 존재하지 않아 생성");
		}
		/*----------------------------------------------------------------------*/
		/* Log형태를 Log문자열에 저장 */
		/*----------------------------------------------------------------------*/
		sb.delete(0,sb.length()); /* sb 초기화 */
		sb.append("SEQ_ID=").append(asSeq_Id).append(lsSep);
		sb.append("LOG_TIME=").append(lsSep);
		sb.append("LOG_TYPE=").append(asLog_type);
		
	}

	/*--------------------------------------------------------------------------*/
	/* 로그를 Name, Value 형태로 생성한다 */
	/*--------------------------------------------------------------------------*/
	public void setElement(String elmtName, String elmtValue){
		
		lsLogInd = "2";	//setElement 시작되었음을 Initial
		
		/*----------------------------------------------------------------------*/
		/* ELEMENT name, value에 해당하는 문자열 저장 */
		/*----------------------------------------------------------------------*/
		sb.append(lsSep);
		if(elmtName.length() > 0 || elmtValue.length() > 0)
			sb.append(elmtName).append("=").append(elmtValue);

		prtLog("setElement->" + elmtName + "=" + elmtValue);
	}

	/*--------------------------------------------------------------------------*/
	/* 공통EndTran. File 생성 */
	/*--------------------------------------------------------------------------*/
	public int endTran(String asLogTime){
		
		lsLogInd = "3";	//endTran 시작되었음을 Initial
		
		prtLog("endTran");
		/*----------------------------------------------------------------------*/
		/* Log 파일 Make */
		/*----------------------------------------------------------------------*/
		
		FileOutputStream lFO = null;
		
		try{
			String lsFilePath = "";
			/*------------------------------------------------------------------*/
			/* Log를 남기려고 할 경우만 실행 */
			/*------------------------------------------------------------------*/
			if("true".equals(isELogWriteInd.toLowerCase())){
				/*--------------------------------------------------------------*/
				/* LOG_TIME값 변경. 순서상 앞에 존재하므로 */
				/*--------------------------------------------------------------*/
				int liValueIdx = sb.toString().indexOf("LOG_TIME=");
				liValueIdx += "LOG_TIME=".length();
				sb.replace(liValueIdx,liValueIdx,asLogTime);
				/*--------------------------------------------------------------*/
				/* Term */
				/*--------------------------------------------------------------*/
				String lsLogTime = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
				/*--------------------------------------------------------------*/
				/* 생성주기를 확인 */
				/*--------------------------------------------------------------*/
				int liTerm = Integer.parseInt(isELogWriteTerm);
				int liMin = 0;
				if(liTerm > 0){
					liMin = Integer.parseInt(lsLogTime.substring(8,10)) * 60
							+ Integer.parseInt(lsLogTime.substring(10,12));
					liMin = ((liMin / liTerm) * liTerm) % 60;
				}
				/*--------------------------------------------------------------*/
				/* 현재 날짜를 이용해 Directory 지정 */
				/*--------------------------------------------------------------*/
				lsLogDirName = lsLogTime.substring(0,8);
				/*--------------------------------------------------------------*/
				/* 현재 날짜로 FileName 지정 */
				/*--------------------------------------------------------------*/
				lsLogFileName = isELogServiceName + "." + isELogServerCode + "."
						+ lsLogDirName;
				/*--------------------------------------------------------------*/
				/* 1시간 이상일 경우는 시간까지의 이름으로 log생성 */
				/*--------------------------------------------------------------*/
				if(liTerm == 0) /* 하루단위 */
				{
					lsLogFileName += "0000.log";
				}else if(liMin >= 10){
					lsLogFileName += lsLogTime.substring(8,10) + liMin + ".log";
				}else if(liMin == 0){
					lsLogFileName += lsLogTime.substring(8,10) + "00.log";
				}else{
					lsLogFileName += lsLogTime.substring(8,11) + liMin + ".log";
				}
				prtLog("FileName=" + lsLogFileName);
				
				lsFilePath = isELogLogPath + lsSeparator + lsLogDirName;
				/*--------------------------------------------------------------*/
				/* 오늘 날짜 폴더 */
				/*--------------------------------------------------------------*/
				File lsDateDir = new File(lsFilePath);

				if(!(lsDateDir.exists() && lsDateDir.isDirectory())){
					lsDateDir.mkdir();
					prtLog("오늘 날짜 디렉토리가 존재 하지 않아 생성 합니다");
				}
				/*--------------------------------------------------------------*/
				/* 현재 파일의 뒷시간에 파일이 존재하면 뒷시간 파일에 write */
				/*--------------------------------------------------------------*/
				String lsNow = lsLogFileName.substring(lsLogFileName.length() - 16,lsLogFileName.length() - 16 + 12);
				String lsAfter = "";
				String lsAfterFile = "";
				if(liTerm == 0){
					lsAfter = calcDateTime(lsNow,"DAY",1);
				}else{
					lsAfter = calcDateTime(lsNow,"MINUTE",liTerm);
				}
				/*--------------------------------------------------------------*/
				/* After시간이 현재시간과의 차이가 10초 내일때만 체크. */
				/*--------------------------------------------------------------*/
				File loFile;
				if(Long.parseLong(lsAfter) <= Long.parseLong(calcDateTime(lsLogTime,"SECOND",10))){
					lsAfterFile = lsFilePath.substring(0,lsFilePath.length() - 8)
							+ lsAfter.substring(0,8)
							+ lsSeparator
							+ lsLogFileName.substring(0,lsLogFileName.length() - 16)
							+ lsAfter.substring(0,12) + ".log";
					loFile = new File(lsAfterFile);
					/*----------------------------------------------------------*/
					/* File 객체 작성. 현재파일이 젤 마지막에 생성됐을 경우 */
					/*----------------------------------------------------------*/
					if(!loFile.exists()){
						loFile = new File(lsFilePath + lsSeparator
								+ lsLogFileName);
					}
					prtLog("After 체크함");
				}else{
					loFile = new File(lsFilePath + lsSeparator + lsLogFileName);
					prtLog("After 체크안함");
				}
				/*--------------------------------------------------------------*/
				/* FileOutputStream(.log) 객체 lFO 생성 */
				/*--------------------------------------------------------------*/
				prtLog("Log파일명 : "+loFile.getAbsolutePath());
				//logger.debug(">>>Log파일명 : "+loFile.getAbsolutePath());
				lFO = new FileOutputStream(loFile.getAbsolutePath(),true);
				/*---------------------------스-----------------------------------*/
				/* 파일의 마지막에 CrLf */
				/*--------------------------------------------------------------*/
				sb.append("\n");
				/*--------------------------------------------------------------*/
				/* 파일 Make */
				/*--------------------------------------------------------------*/
				if(loFile.canWrite()){
					lFO.write(sb.toString().getBytes());
					
					prtLog("endTran->Log파일에 write");
				}else{
					prtErr("endTran->Log파일에 write할 수 없습니다(canWrite=false)");
				}
			}
		}catch(Exception e){
			prtErr("(Log만드는 중 오류발생)" + e);
			return 0;
		}finally{
			
			try {
				if(lFO != null){
					lFO.close();
				}
			} catch (IOException e) {}
		}
		prtLog("endTran->" + sb.toString());
		return 1;
	}

	/*--------------------------------------------------------------------------*/
	/* Unique한 Key값. */
	/*--------------------------------------------------------------------------*/
		public String guidKey(){
		
		if(seqNumber.equals("9999"))
			seqNumber = "0000";

		this.seqNumber = String.valueOf((Integer.parseInt(seqNumber))+1);
		
		
		String leftPadding = "";
		for(int i = 0 ; i < 4-this.seqNumber.length() ; i++ )
		{
			
			leftPadding += "0";
			logger.debug("LeftPadding===>" + leftPadding);
		}
		
		this.seqNumber = leftPadding + this.seqNumber;
		
		logger.debug("SEQUENCE===>" + this.seqNumber);
		
		
		String lsRand1 = "" + Math.abs(rand.nextInt());
		
		if(lsRand1.length() < 2){
			for(int i = 0; i < 2 - lsRand1.length(); i++){
				lsRand1 = "0" + lsRand1;
			}
		}
		logger.debug("isELogServerID===>" + isELogServerID + " : " + isELogServerID.substring(1,3));
		logger.debug("lsRand1===>" + lsRand1 + " : " + lsRand1.substring(0,2));
		return (new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()))+lsRand1.substring(0,2)+this.seqNumber+isELogServerID.substring(1,3);
		
	}
	/*--------------------------------------------------------------------------*/
	/* 한글 깨짐 방지를 위해 */
	/*--------------------------------------------------------------------------*/
	private static String asc2ksc(String asAscii){
		try{
			return new String(asAscii.getBytes("8859_1"),"KSC5601");
		}catch(UnsupportedEncodingException e){
			return asAscii;
		}
	}

	/*--------------------------------------------------------------------------*/
	/* 날짜 계산 함수 */
	/* asDateTime=기준날짜(yyyyMMddHHmm) */
	/* asWhen=연산상대(년,월,일,시,분) */
	/* aiMinute=가감 수치 */
	/*--------------------------------------------------------------------------*/
	private String calcDateTime(String asDateTime, String asWhen, int aiTerm){
		try{
			int liWhen = 0;
			GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(asDateTime.substring(0,4)),Integer.parseInt(asDateTime.substring(4,6)),Integer.parseInt(asDateTime.substring(6,8)),Integer.parseInt(asDateTime.substring(8,10)),Integer.parseInt(asDateTime.substring(10,12)),(asDateTime.length() > 12) ? Integer.parseInt(asDateTime.substring(12,14))
					: 0);
			gc.add(Calendar.MONTH,-1); /* Default */
			if("YEAR".equals(asWhen)){
				liWhen = Calendar.YEAR;
			}else if("MONTH".equals(asWhen)){
				liWhen = Calendar.MONTH;
			}else if("DAY".equals(asWhen)){
				liWhen = Calendar.DAY_OF_MONTH;
			}else if("HOUR".equals(asWhen)){
				liWhen = Calendar.HOUR;
			}else if("MINUTE".equals(asWhen)){
				liWhen = Calendar.MINUTE;
			}else if("SECOND".equals(asWhen)){
				liWhen = Calendar.SECOND;
			}
			gc.add(liWhen,aiTerm);
			return (new SimpleDateFormat("yyyyMMddHHmmss")).format(gc.getTime());
		}catch(Exception err){
			prtLog("오류발생(calcDateTime) : " + err);
			return asDateTime;
		}
	}

	/*--------------------------------------------------------------------------*/
	/* 날짜 계산 함수 : 현재날짜 - 날짜 */
	/* asAfter=날짜(yyyyMMddHHmmss) */
	/* asLogTime=현재상대(yyyyMMddHHmmss) */
	/* 두날짜의 차이를 초단위 반환 */
	/*--------------------------------------------------------------------------*/


	/*--------------------------------------------------------------------------*/
	/* TUnionLog Class의 Log */
	/*--------------------------------------------------------------------------*/
	private void prtLog(String psStr){
		if("true".equals(isELogThisLogInd.toLowerCase()))
			logger.debug("@@ TLO:>" + psStr);
	}

	/*--------------------------------------------------------------------------*/
	/* TUnionLog Class의 오류발생시 오류로그를 생성. */
	/*--------------------------------------------------------------------------*/
	private void prtErr(String psStr) /* 오류발생 */
	{
		FileOutputStream lFOErr =  null;
		
		try{
			/*------------------------------------------------------------------*/
			/* LogInd에 따른 Service Type */
			/*------------------------------------------------------------------*/
			String lsSvcType = lsLogInd;
			if(lsSvcType.length() == 0)
				lsSvcType = "-1";
			switch(Integer.parseInt(lsSvcType)){
				case -1:
					lsSvcType = "초기화중_오류";
					break;
				case 1:
					lsSvcType = "startTran_오류";
					break;
				case 2:
					lsSvcType = "setElement_오류";
					break;
				case 3:
					lsSvcType = "endTran_오류";
					break;
			}
			/*------------------------------------------------------------------*/
			/* 오류 내용 설정. */
			/*------------------------------------------------------------------*/
			String lsYYYYMMDDHHSS = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
			String lsErr = "[" + lsYYYYMMDDHHSS + "," + lsSvcType + "]" + psStr
					+ "\n";
			/*------------------------------------------------------------------*/
			/* Error Log 생성 */
			/*------------------------------------------------------------------*/
			lFOErr = new FileOutputStream(isELogErrLogPath
					+ lsSeparator + isELogServiceName + "." + isELogServerCode
					+ "." + lsYYYYMMDDHHSS.substring(0,8) + ".log",true);
			/*------------------------------------------------------------------*/
			/* 파일 Make */
			/*------------------------------------------------------------------*/
			lFOErr.write(lsErr.getBytes());
		}catch(Exception err){
			prtLog("오류로그 작성중 오류:" + err);
		}finally{
			
			try {
				if(lFOErr != null){
					lFOErr.close();
				}
			} catch (IOException e) {}
		}

		if("true".equals(isELogThisLogInd.toLowerCase()))
			logger.debug("@@ TLO오류:>" + psStr);
	}

}