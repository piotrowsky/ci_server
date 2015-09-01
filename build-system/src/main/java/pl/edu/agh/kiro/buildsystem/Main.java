package pl.edu.agh.kiro.buildsystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;

import pl.edu.agh.kiro.buildsystem.Configuration.ConfigurationBuilder;

/**
 * Main class
 * 
 * @author Lukasz.Gruba
 */
public class Main {
	
	/**
	 * Application startup
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		
		Configuration config = loadConfiguration(args[0]);
		printStartupMessage(config);
		Thread repoMonitoringThread = new Thread(new RepoMonitoringThread(config));
		repoMonitoringThread.start();
	}
	
	/**
	 * Prints configuration information
	 * @param config
	 */
	public static void printStartupMessage(Configuration config) {
		
		System.out.println(
				  "---------------------------------------------------------------------\n"
				+ "                             CI SYSTEM\n"
				+ "---------------------------------------------------------------------\n"
				+ "CONFIGURATION:\n"
				+ "- monitored location:\t\t" + config.getRepositoryProjectUrl() + "\n"
				+ "- project local copy:\t\t" + config.getLocalProjectCopyPath() + "\n"
				+ "- scan interval:\t" + config.getScanInterval() + "\n"
				+ "---------------------------------------------------------------------\n\n\n");
	}
	
	/**
	 * Loads configuration from .properties file and returns {@link Configuration} instance.
	 * @return
	 */
	public static Configuration loadConfiguration(String configFile) throws IOException {

		Properties properties = new Properties();

		if (configFile == null || configFile.isEmpty()) {
			properties.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
		} else {
			properties.load(new FileInputStream(configFile));
		}

		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

		configurationBuilder.setRepositoryProjectUrl(properties.getProperty("config.repository.project.url"));
		configurationBuilder.setLocalProjectCopyPath(properties.getProperty("config.repository.project.copy.path"));
		configurationBuilder.setScanInterval(Long.parseLong(properties.getProperty("config.minimum.scan.interval")));
		configurationBuilder.setMavenHome(properties.getProperty("maven.home"));
		configurationBuilder.setLogPath(properties.getProperty("log.path"));
		configurationBuilder.setMailerUser(properties.getProperty("mailer.user"));
		configurationBuilder.setMailerPass(properties.getProperty("mailer.pass"));
		configurationBuilder.setMailerAuth(properties.getProperty("mailer.auth"));
		configurationBuilder.setMailerHost(properties.getProperty("mailer.host"));
		configurationBuilder.setMailerPort(properties.getProperty("mailer.port"));
		configurationBuilder.setMailerRecipients(properties.getProperty("mailer.recipients"));

		return configurationBuilder.build();
	}
	
}
