package pl.edu.agh.kiro.buildsystem;

import java.util.List;

/**
 * Configuration object
 * 
 * @author Lukasz.Gruba
 */
public class Configuration {
	
	/**
	 * Full url to project in repository 
	 */
	private String repositoryProjectUrl;
	
	/**
	 * Path where local project copy should be stored
	 */
	private String localProjectCopyPath;
	
	/**
	 * Minimum number of seconds between repository check
	 */
	private Long scanInterval;

	private String mavenHome;
	private String logPath;
	private String mailerUser;
	private String mailerPass;
	private String mailerAuth;
	private String mailerHost;
	private String mailerPort;
	private String mailerRecipients;

	/**
	 * Private constructor
	 * @param builder
	 */
	private Configuration(ConfigurationBuilder builder) {
		this.mailerUser = builder.mailerUser;
		this.mailerPass = builder.mailerPass;
		this.scanInterval = builder.scanInterval;
		this.localProjectCopyPath = builder.localProjectCopyPath;
		this.repositoryProjectUrl = builder.repositoryProjectUrl;
		this.mavenHome = builder.mavenHome;
		this.logPath = builder.logPath;
		this.mailerAuth = builder.mailerAuth;
		this.mailerHost = builder.mailerHost;
		this.mailerPort = builder.mailerPort;
		this.mailerRecipients = builder.mailerRecipients;
	}
	
	/**
	 * @return the scanInterval
	 */
	public Long getScanInterval() {
		return scanInterval;
	}
	
	/**
	 * @return the repositoryProjectUrl
	 */
	public String getRepositoryProjectUrl() {
		return repositoryProjectUrl;
	}

	/**
	 * @return the localProjectCopyPath
	 */
	public String getLocalProjectCopyPath() {
		return localProjectCopyPath;
	}


	public String getMavenHome() {
		return mavenHome;
	}

	public String getLogPath() {
		return logPath;
	}

	public String getMailerAuth() {
		return mailerAuth;
	}

	public String getMailerHost() {
		return mailerHost;
	}

	public String getMailerPort() {
		return mailerPort;
	}

	public String getMailerUser() {
		return mailerUser;
	}

	public String getMailerPass() {
		return mailerPass;
	}

	public String getMailerRecipients() {
		return mailerRecipients;
	}

	/**
	 * Configuration builder
	 * 
	 * @author Lukasz.Gruba
	 */
	public static class ConfigurationBuilder {
		
		private Long scanInterval;
		private String repositoryProjectUrl;
		private String localProjectCopyPath;
		private String mavenHome;
		private String logPath;
		private String mailerUser;
		private String mailerPass;
		private String mailerAuth;
		private String mailerHost;
		private String mailerPort;
		private String mailerRecipients;


		public Configuration build() {
			return new Configuration(this);
		}

		public void setScanInterval(Long scanInterval) {
			this.scanInterval = scanInterval;
		}

		public void setRepositoryProjectUrl(String repositoryProjectUrl) {
			this.repositoryProjectUrl = repositoryProjectUrl;
		}

		public void setLocalProjectCopyPath(String localProjectCopyPath) {
			this.localProjectCopyPath = localProjectCopyPath;
		}

		public void setMavenHome(String mavenHome) {
			this.mavenHome = mavenHome;
		}

		public void setLogPath(String logPath) {
			this.logPath = logPath;
		}

		public void setMailerUser(String mailerUser) {
			this.mailerUser = mailerUser;
		}

		public void setMailerPass(String mailerPass) {
			this.mailerPass = mailerPass;
		}

		public void setMailerAuth(String mailerAuth) {
			this.mailerAuth = mailerAuth;
		}

		public void setMailerHost(String mailerHost) {
			this.mailerHost = mailerHost;
		}

		public void setMailerPort(String mailerPort) {
			this.mailerPort = mailerPort;
		}

		public void setMailerRecipients(String mailerRecipients) {
			this.mailerRecipients = mailerRecipients;
		}
	}
}
