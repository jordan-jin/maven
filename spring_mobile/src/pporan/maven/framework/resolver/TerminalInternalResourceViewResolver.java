package pporan.maven.framework.resolver;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class TerminalInternalResourceViewResolver extends InternalResourceViewResolver{

	private Logger logger = Logger.getLogger(TerminalInternalResourceViewResolver.class);
	
	private String mobile = "m/";
	private String tablet = "t/";
//	private String mobile = ".mobile";
//	private String tablet = ".tablet";
	
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception{
		Device device = DeviceUtils.getCurrentDevice(RequestContextHolder.currentRequestAttributes());
		if(device.isMobile()) {
			viewName = getChangeViewName(viewName, mobile);
		}
		if(device.isTablet()){
			viewName = getChangeViewName(viewName, tablet);
		}
		logger.debug("ViewName : "+ viewName);
		return (InternalResourceView) super.buildView(viewName);
	}
	
	private String getChangeViewName(String viewName, String target) {
		logger.debug("@@@@@ original viewname = "+viewName);
		return target + viewName;
//		return viewName + target;
	}

	@Override
	protected Object getCacheKey(String viewName, Locale locale) {
		System.out.println(DeviceUtils.getCurrentDevice(RequestContextHolder.currentRequestAttributes()));
		Device device = DeviceUtils.getCurrentDevice(RequestContextHolder.currentRequestAttributes());
		if (device.isMobile()) {
			return super.getCacheKey( getChangeViewName(viewName, mobile), locale);
		}else if (device.isTablet()) {
				return super.getCacheKey( getChangeViewName(viewName, tablet), locale);
		} else {
			return super.getCacheKey(viewName, locale);
		}
	}
}
