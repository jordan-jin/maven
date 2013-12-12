/**
 * @date 2012-11-21
 * @author jinbos
 * @description Header(User-Agent) value Check
 */
package pporan.maven.framework.util;

import java.util.ResourceBundle;

public class MobileUtil {

	/**
	 * @description Mobile Check
	 * @param userAgent
	 * @return true:mobile false:not mobile
	 */
	public static boolean _mobileChk(String userAgent){
		boolean result = false;
		
		if(userAgent != null && !"".equals(userAgent)){
			userAgent = userAgent.toLowerCase();
			String[] vals = ResourceBundle.getBundle("properties.conf").getString("mobileAgent").split(",");
			for(int i=0, len=vals.length; i < len; i++){
				if(userAgent.indexOf(vals[i]) > -1){ result = true; break; }
			}
		}
		
		return result;
	}
	
	/**
	 * Web/Tablet/Mobile Check
	 * @param userAgent
	 * @return 1:Web 2:tablet 3:mobile
	 */
	public static int _agentChk(String userAgent){
		int result = 1;
		
		if(userAgent != null && !"".equals(userAgent)){
			userAgent = userAgent.toLowerCase();
			String[] vals = ResourceBundle.getBundle("properties.conf").getString("tabletAgent").split(",");
			for(int i=0, len=vals.length; i < len; i++){
				if(userAgent.indexOf(vals[i]) > -1){ result = 2; break; }
			}

			if(result == 1){
				vals = ResourceBundle.getBundle("properties.conf").getString("mobileAgent").split(",");
				for(int i=0, len=vals.length; i < len; i++){
					if(userAgent.indexOf(vals[i]) > -1){ result = 3; break; }
				}
			}
		}
		
		return result;
	}
}
