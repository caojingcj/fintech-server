package generatorConfigTest;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * @Title: GenMain.java
 * @Package generatorConfigTest
 * @author qierkang xyqierkang@163.com
 * @date 2017年7月24日 下午3:17:02
 * @Description: TODO[ 用一句话描述该文件做什么 ]
 */
public class GenMain {
	/**
	 * @Title: GenMain.java @param @param args 设定文件 @Description: TODO[
	 * 这里用一句话描述这个方法的作用 ] @throws
	 */
	public static void main(String[] args) {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		String genCfg = "/generatorConfig.xml";
		File configFile = new File(GenMain.class.getResource(genCfg).getFile());
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = null;
		try {
			config = cp.parseConfiguration(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLParserException e) {
			e.printStackTrace();
		}
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = null;
		try {
			myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try {
			myBatisGenerator.generate(null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
