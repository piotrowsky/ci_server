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
	 * Project relative path to main POM file
	 */
	private String pomPath;
	
	/**
	 * Minimum number of seconds between repository check
	 */
	private Long scanInterval;
	
	/**
	 * Private constructor
	 * @param builder
	 */
	private Configuration(ConfigurationBuilder builder) {
		
		this.scanInterval = builder.scanInterval;
		this.localProjectCopyPath = builder.localProjectCopyPath;
		this.pomPath = builder.pomPath;
		this.repositoryProjectUrl = builder.repositoryProjectUrl;
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

	/**
	 * @return the pomPath
	 */
	public String getPomPath() {
		return pomPath;
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
		private String pomPath;
		
		public Configuration build() {
			return new Configuration(this);
		}

		/**
		 * @return the scanInterval
		 */
		public Long getScanInterval() {
			return scanInterval;
		}

		/**
		 * @param scanInterval the scanInterval to set
		 */
		public void setScanInterval(Long scanInterval) {
			this.scanInterval = scanInterval;
		}

		/**
		 * @return the repositoryProjectUrl
		 */
		public String getRepositoryProjectUrl() {
			return repositoryProjectUrl;
		}

		/**
		 * @param repositoryProjectUrl the repositoryProjectUrl to set
		 */
		public void setRepositoryProjectUrl(String repositoryProjectUrl) {
			this.repositoryProjectUrl = repositoryProjectUrl;
		}

		/**
		 * @return the localProjectCopyPath
		 */
		public String getLocalProjectCopyPath() {
			return localProjectCopyPath;
		}

		/**
		 * @param localProjectCopyPath the localProjectCopyPath to set
		 */
		public void setLocalProjectCopyPath(String localProjectCopyPath) {
			this.localProjectCopyPath = localProjectCopyPath;
		}

		/**
		 * @return the pomPath
		 */
		public String getPomPath() {
			return pomPath;
		}

		/**
		 * @param pomPath the pomPath to set
		 */
		public void setPomPath(String pomPath) {
			this.pomPath = pomPath;
		}
	}
}
