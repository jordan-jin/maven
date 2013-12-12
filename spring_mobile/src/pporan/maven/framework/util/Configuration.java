package pporan.maven.framework.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Node;
import org.springframework.core.io.ClassPathResource;

public class Configuration {
	private static Logger g_logger = Logger.getLogger(Configuration.class);

	private static Configuration g_instance = null;
	private static long g_lastModifiedTime = 0L;
	private File m_configFile = null;

	private Map<String, String> m_properties = null;

	private Configuration() {
		
		try {
			m_configFile = new ClassPathResource("conf.xml").getFile();

			if(!m_configFile.exists()) throw new RuntimeException(m_configFile.getAbsolutePath() + " is not exist in path!!!");
			g_logger.debug("CONFIG_FILE_PATH:" + m_configFile.getAbsolutePath());

			_init();
		} catch (Throwable ex) {
			g_logger.error(ex.getMessage(), ex);
		}
	}

	private void _init() {
		g_lastModifiedTime = _lastModified();

		if (null != m_properties) {
			m_properties.clear();
			m_properties = null;
		}
		m_properties = new HashMap<String, String>();		
		DomForXPath configDom = new DomForXPath(m_configFile.getAbsolutePath(), true);
		g_logger.debug(configDom.getText("//configuration"));
		g_logger.debug("configDom.getNodeList('//configuration/module') = "+configDom.getNodeList("//configuration/module"));
		List<Node> modules = configDom.getNodeList("//configuration/module");
		for (Node moduleNode : modules) {
			List<Node> properies = DomForXPath.getNodeList(moduleNode, "property");
			String moduleId = DomForXPath.getAttribute(moduleNode, "id");
			for (Node propertyNode : properies) {
				String propertyId = DomForXPath.getAttribute(propertyNode, "id");
				String value = propertyNode.getText();
				m_properties.put(moduleId + "." + propertyId, value);
			}
		}
	}

	private String _getProperty(String module, String propety) {
		return m_properties.get(module + "." + propety);
	}

	private static Configuration _getInstance(){
		if(g_instance == null || (g_lastModifiedTime != g_instance._lastModified())) g_instance = new Configuration();
		return g_instance;
	}

	private long _lastModified() {
        return m_configFile.lastModified();
    }

	public static String getString(String module, String propety) {
		Configuration config = Configuration._getInstance();
		return config._getProperty(module, propety);
	}

	public static String getString(String propety) {
		Configuration config = Configuration._getInstance();
		return config._getProperty("base", propety);
	}

	public static Integer getInteger(String module, String propety) {
		Configuration config = Configuration._getInstance();
		return new Integer(config._getProperty(module, propety));
	}

	public static Long getLong(String module, String propety) {
		Configuration config = Configuration._getInstance();
		return new Long(config._getProperty(module, propety));
	}

	public static boolean getBoolean(String module, String propety) {
		String strval = getString(module, propety);
		if (null == strval) return false;
		return Boolean.parseBoolean(strval);
	}
}
