package pporan.maven.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class EncodeUtil {

	public static String getAdminLogName(String str) throws Exception{
		
		ResourceBundle fileResourceBundle = ResourceBundle.getBundle("conf.admin_log");
		
//		String result = EncodeUtil.makeIsoUtfKOR(StringUtil.nvl(fileResourceBundle.getString(str),""));
		String result = StringUtil.nvl(fileResourceBundle.getString(str),"");
//		result += allEncodeString(result);
		return result;
	}
	
	public static void allEncodeTest(String str) throws Exception{
		System.out.println("makeMs9KscKOR : "+makeMs9KscKOR(str));
		System.out.println("makeMs9IsoKOR : "+makeMs9IsoKOR(str));
		System.out.println("makeMs9EucKOR : "+makeMs9EucKOR(str));
		System.out.println("makeMs9UtfKOR : "+makeMs9UtfKOR(str));
		System.out.println("makeIsoMs9KOR : "+makeIsoMs9KOR(str));
		System.out.println("makeIsoKscKOR : "+makeIsoKscKOR(str));
		System.out.println("makeIsoEucKOR : "+makeIsoEucKOR(str));
		System.out.println("makeIsoUtfKOR : "+makeIsoUtfKOR(str));
		System.out.println("makeUtfMs9KOR : "+makeUtfMs9KOR(str));
		System.out.println("makeUtfEucKOR : "+makeUtfEucKOR(str));
		System.out.println("makeUtfKscKOR : "+makeUtfKscKOR(str));
		System.out.println("makeUtfIsoKOR : "+makeUtfIsoKOR(str));
		System.out.println("makeEucMs9KOR : "+makeEucMs9KOR(str));
		System.out.println("makeEucIsoKOR : "+makeEucIsoKOR(str));
		System.out.println("makeEucKscKOR : "+makeEucKscKOR(str));
		System.out.println("makeEucUtfKOR : "+makeEucUtfKOR(str));
		System.out.println("makeKscMs9KOR : "+makeKscMs9KOR(str));
		System.out.println("makeKscIsoKOR : "+makeKscIsoKOR(str));
		System.out.println("makeKscEucKOR : "+makeKscEucKOR(str));
		System.out.println("makeKscUtfKOR : "+makeKscUtfKOR(str));
		System.out.println("URLEncoder : "+URLEncoder.encode(str,"UTF-8"));
		System.out.println("URLDecoder : "+URLDecoder.decode(str,"UTF-8"));
	}
	
	public static String allEncodeString(String str) throws Exception{
		String data = "1";
		data += makeMs9KscKOR(str);
		data += "%2";
		data += makeMs9IsoKOR(str);
		data += "%3";
		data += makeMs9EucKOR(str);
		data += "%4";
		data += makeMs9UtfKOR(str);
		data += "%5";
		data += makeIsoMs9KOR(str);
		data += "%6";
		data += makeIsoKscKOR(str);
		data += "%7";
		data += makeIsoEucKOR(str);
		data += "%8";
		data += makeIsoUtfKOR(str);
		data += "%9";
		data += makeUtfMs9KOR(str);
		data += "%10";
		data += makeUtfEucKOR(str);
		data += "%11";
		data += makeUtfKscKOR(str);
		data += "%12";
		data += makeUtfIsoKOR(str);
		data += "%13";
		data += makeEucMs9KOR(str);
		data += "%14";
		data += makeEucIsoKOR(str);
		data += "%15";
		data += makeEucKscKOR(str);
		data += "%16";
		data += makeEucUtfKOR(str);
		data += "%17";
		data += makeKscMs9KOR(str);
		data += "%18";
		data += makeKscIsoKOR(str);
		data += "%19";
		data += makeKscEucKOR(str);
		data += "%20";
		data += makeKscUtfKOR(str);
		data += "%21";
		data += URLEncoder.encode(str);
		data += "%22";
		data += URLDecoder.decode(str);
		return data;
	}
	
	public static String getEncoding(String name,String key){
		String reStr = null;
		int type = StringUtil.nvl(key,1);
		if(1 == type){
			reStr = makeMs9KscKOR(name);
		}
		if(2 == type){
			reStr = makeMs9IsoKOR(name);
		}
		if(3 == type){
			reStr = makeMs9EucKOR(name);
		}
		if(4 == type){
			reStr = makeMs9UtfKOR(name);
		}
		if(5 == type){
			reStr = makeIsoMs9KOR(name);
		}
		if(6 == type){
			reStr = makeIsoKscKOR(name);
		}
		if(7 == type){
			reStr = makeIsoEucKOR(name);
		}
		if(8 == type){
			reStr = makeIsoUtfKOR(name);
		}
		if(9 == type){
			reStr = makeUtfMs9KOR(name);
		}
		if(10 == type){
			reStr = makeUtfEucKOR(name);
		}
		if(11 == type){
			reStr = makeUtfKscKOR(name);
		}
		if(12 == type){
			reStr = makeUtfIsoKOR(name);
		}
		if(13 == type){
			reStr = makeEucMs9KOR(name);
		}
		if(14 == type){
			reStr = makeEucIsoKOR(name);
		}
		if(15 == type){
			reStr = makeEucKscKOR(name);
		}
		if(16 == type){
			reStr = makeEucUtfKOR(name);
		}
		if(17 == type){
			reStr = makeKscMs9KOR(name);
		}
		if(18 == type){
			reStr = makeKscIsoKOR(name);
		}
		if(19 == type){
			reStr = makeKscEucKOR(name);
		}
		if(20 == type){
			reStr = makeKscUtfKOR(name);
		}
		return reStr;
	}
	
	
	public static String makeMs9KscKOR(String str){
		String utf = null;
		
		if(null != str){
			try {
				utf = new String(str.getBytes("MS949"),"ksc5601");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return utf;
	}
	
	
	public static String makeMs9IsoKOR(String str){
		String utf = null;
		
		if(null != str){
			try {
				utf = new String(str.getBytes("MS949"),"8859_1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return utf;
	}
	
	
	public static String makeMs9EucKOR(String str){
		String utf = null;
		
		if(null != str){
			try {
				utf = new String(str.getBytes("MS949"),"EUC-KR");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return utf;
	}
	
	
	public static String makeMs9UtfKOR(String str){
		String utf = null;
		
		if(null != str){
			try {
				utf = new String(str.getBytes("MS949"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return utf;
	}
	
	
	public static String makeIsoMs9KOR(String str){
		String kor = null;
		
		if(null != str){
			try {
				kor = new String(str.getBytes("8859_1"),"MS949");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return kor;
	}
	
	
	public static String makeIsoKscKOR(String str){
		String kor = null;
		
		if(null != str){
			try {
				kor = new String(str.getBytes("8859_1"),"ksc5601");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return kor;
	}
	
	
	public static String makeIsoEucKOR(String str){
		String kor = null;
		
		if(null != str){
			try {
				kor = new String(str.getBytes("8859_1"),"EUC-KR");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return kor;
	}
	
	
	public static String makeIsoUtfKOR(String str){
		String utf = null;
		
		if(null != str){
			try {
				utf = new String(str.getBytes("8859_1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return utf;
	}
	
	public static String makeUtfMs9KOR(String str){
		String utf = null;
		
		if(null != str){
			try {
				utf = new String(str.getBytes("UTF-8"),"MS949");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return utf;
	}
	
	public static String makeUtfEucKOR(String str){
		String utf = null;
		
		if(null != str){
			try {
				utf = new String(str.getBytes("UTF-8"),"EUC-KR");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return utf;
	}
	
	public static String makeUtfKscKOR(String str){
		String utf = null;
		
		if(null != str){
			try {
				utf = new String(str.getBytes("UTF-8"),"ksc5601");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return utf;
	}
	
	public static String makeUtfIsoKOR(String str){
		String utf = null;
		
		if(null != str){
			try {
				utf = new String(str.getBytes("UTF-8"),"8859_1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return utf;
	}
	
	public static String makeEucMs9KOR(String str){
		String kor = null;
		
		if(null != str){
			try {
				kor = new String(str.getBytes("EUC-KR"),"MS949");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return kor;
	}
	
	public static String makeEucIsoKOR(String str){
		String kor = null;
		
		if(null != str){
			try {
				kor = new String(str.getBytes("EUC-KR"),"8859_1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return kor;
	}
	
	public static String makeEucKscKOR(String str){
		String kor = null;
		
		if(null != str){
			try {
				kor = new String(str.getBytes("EUC-KR"),"ksc5601");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return kor;
	}
	
	public static String makeEucUtfKOR(String str){
		String kor = null;
		
		if(null != str){
			try {
				kor = new String(str.getBytes("EUC-KR"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return kor;
	}
	
	public static String makeKscMs9KOR(String str){
		String kor = null;
		
		if(null != str){
			try {
				kor = new String(str.getBytes("ksc5601"),"MS949");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return kor;
	}
	
	public static String makeKscIsoKOR(String str){
		String kor = null;
		
		if(null != str){
			try {
				kor = new String(str.getBytes("ksc5601"),"8859_1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return kor;
	}
	
	public static String makeKscEucKOR(String str){
		String kor = null;
		
		if(null != str){
			try {
				kor = new String(str.getBytes("ksc5601"),"EUC-KR");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return kor;
	}
	
	public static String makeKscUtfKOR(String str){
		String kor = null;
		
		if(null != str){
			try {
				kor = new String(str.getBytes("ksc5601"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return kor;
	}

	/**
	 * 코드화 된 관리자 로그값을 프로퍼티에 저장된 값으로 변환 
	 * @param str 프로퍼티에서 가져온 값으로 변환할 문자열
	 * @return 프로퍼티에서 가져온 값으로 변환된 문자열
	 */
	public static String getMileageLogName(String str){
		
		ResourceBundle fileResourceBundle = ResourceBundle.getBundle("conf.mileage_log");
		
		Logger logger = Logger.getLogger(StringUtil.class);		
		//logger.debug("@@@@@@@@@@@@@@@@@@@@@@@ fileResourceBundle.getString(str) : "+ EncodeUtil.allEncodeString(fileResourceBundle.getString(str)) );
		
		//String result = EncodeUtil.makeIsoUtfKOR(nvl(fileResourceBundle.getString(str),""));
		String result = StringUtil.nvl(fileResourceBundle.getString(str),"");
		return result;
	}	
	
}
