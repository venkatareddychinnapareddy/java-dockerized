package es.joseoc.java.dockerized;

import static java.lang.String.format;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		LOGGER.debug("The process starts");
		process();
		LOGGER.debug("The process ends");
	}

	private static void process() {
		try {
			PropertiesConfiguration config = readConfig("config.properties");
			Worker.transformAndGenerateResult(config.getString("input_file"),config.getString("output_folder"));
		} catch (Exception e) {
			LOGGER.error("Error processing", e);
			throw e;
		}
	}

	private static PropertiesConfiguration readConfig(String filename) {
		Configurations configs = new Configurations();
		PropertiesConfiguration config = null;
		try {
			config = configs.properties(filename);
		} catch (ConfigurationException e) {
			throw new RuntimeException(format("Error reading properties file %s", filename), e);
		}
		return config;
	}

}
