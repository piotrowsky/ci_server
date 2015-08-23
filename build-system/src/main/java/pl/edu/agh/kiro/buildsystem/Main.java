package pl.edu.agh.kiro.buildsystem;

import java.util.ArrayList;
import java.util.Arrays;
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
	public static void main(String[] args) {
		
		Configuration config = loadConfiguration();
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
	public static Configuration loadConfiguration() {
		
		ResourceBundle properties = ResourceBundle.getBundle("config");
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

		configurationBuilder.setRepositoryProjectUrl(properties.getString("config.repository.project.url"));
		configurationBuilder.setLocalProjectCopyPath(properties.getString("config.repository.project.copy.path"));
		configurationBuilder.setScanInterval(Long.parseLong(properties.getString("config.minimum.scan.interval")));
		configurationBuilder.setMavenHome(properties.getString("maven.home"));
		configurationBuilder.setLogPath(properties.getString("log.path"));
		configurationBuilder.setMailerUser(properties.getString("mailer.user"));
		configurationBuilder.setMailerPass(properties.getString("mailer.pass"));
		configurationBuilder.setMailerAuth(properties.getString("mailer.auth"));
		configurationBuilder.setMailerHost(properties.getString("mailer.host"));
		configurationBuilder.setMailerPort(properties.getString("mailer.port"));
		configurationBuilder.setMailerRecipients(properties.getString("mailer.recipients"));

		return configurationBuilder.build();
	}
	
}
