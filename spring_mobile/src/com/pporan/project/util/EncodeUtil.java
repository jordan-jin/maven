package com.pporan.project.util;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class EncodeUtil {

	
	public static void allEncodeTest(String str){
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
	}
	
	public static String allEncodeString(String str){
		String data = "";
		data += makeMs9KscKOR(str);
		data += "%";
		data += makeMs9IsoKOR(str);
		data += "%";
		data += makeMs9EucKOR(str);
		data += "%";
		data += makeMs9UtfKOR(str);
		data += "%";
		data += makeIsoMs9KOR(str);
		data += "%";
		data += makeIsoKscKOR(str);
		data += "%";
		data += makeIsoEucKOR(str);
		data += "%";
		data += makeIsoUtfKOR(str);
		data += "%";
		data += makeUtfMs9KOR(str);
		data += "%";
		data += makeUtfEucKOR(str);
		data += "%";
		data += makeUtfKscKOR(str);
		data += "%";
		data += makeUtfIsoKOR(str);
		data += "%";
		data += makeEucMs9KOR(str);
		data += "%";
		data += makeEucIsoKOR(str);
		data += "%";
		data += makeEucKscKOR(str);
		data += "%";
		data += makeEucUtfKOR(str);
		data += "%";
		data += makeKscMs9KOR(str);
		data += "%";
		data += makeKscIsoKOR(str);
		data += "%";
		data += makeKscEucKOR(str);
		data += "%";
		data += makeKscUtfKOR(str);
		return data;
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

}
