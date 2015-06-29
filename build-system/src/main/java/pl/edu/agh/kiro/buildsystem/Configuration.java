package pl.edu.agh.kiro.buildsystem;

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
	
	/**
	 * Private constructor
	 * @param builder
	 */
	private Configuration(ConfigurationBuilder builder) {
		
		this.scanInterval = builder.scanInterval;
		this.localProjectCopyPath = builder.localProjectCopyPath;
		this.repositoryProjectUrl = builder.repositoryProjectUrl;
		this.mavenHome = builder.mavenHome;
		this.logPath = builder.logPath;
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

	}
}
