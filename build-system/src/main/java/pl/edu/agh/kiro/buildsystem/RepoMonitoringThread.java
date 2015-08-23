package pl.edu.agh.kiro.buildsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.maven.shared.invoker.*;
import org.tmatesoft.svn.core.SVNException;


/**
 * Thread monitoring specific repository.
 * 
 * @author Lukasz.Gruba
 */
public class RepoMonitoringThread implements Runnable {
	
	/**
	 * Configuration
	 */
	private Configuration config;
	private Mailer mailer;
	
	/**
	 * Main constructor
	 * @param config
	 */
	public RepoMonitoringThread(Configuration config) {
		this.config = config;
		this.mailer = new Mailer(config);
	}

	/**
	 * Monitoring repository.
	 * If commit is detected then build process is triggered.
	 */
	@Override
	public void run() {
		
		try {
			//maven invoker init
			StringBuilder logs = new StringBuilder();
			String logFilePath;
			String logFileName;
			Invoker mvnInvoker = getInvoker(logs);
			InvocationRequest mvnRequest = getInvocationRequest();
			
			//repo init
			RepoManager svnRepoManager = new RepoManager(config);
			long currentProjectCopyRevision = svnRepoManager.doCheckout();
			
			//main loop
			while(true) {

				long timeBeforeBuild = System.currentTimeMillis();

				//if commit was performed then update local copy, run maven build and handle eventual errors
				if(currentProjectCopyRevision < svnRepoManager.getLatestRevision()) {
					LocalDateTime now = LocalDateTime.now();
					logToConsole("Automatic build has been triggered!");
					logToConsole("Old revision: " + currentProjectCopyRevision);
					logToConsole("New revision: " + svnRepoManager.getLatestRevision());
					logToConsole("Performing SVN checkout...");

					currentProjectCopyRevision = svnRepoManager.doCheckout();

					logToConsole("Checkout performed successfully!");
					logToConsole("Executing maven build...");

					InvocationResult result = mvnInvoker.execute(mvnRequest);
					if(result.getExitCode() != 0) {
						logToConsole("Build executed with failures");
					} else {
						logToConsole("Build executed successfully!");
					}
					logFilePath = config.getLogPath() + "/" + now + ".log";
					logFileName = now + ".log";
					logToConsole("Build log saved to file: " + now + ".log");
					try {
						saveLogsToFile(logs, logFilePath);
						mailer.send("Continous integration server message", "Build information", logFilePath,
								logFileName);
					} catch(Exception ex) {}
					logs.setLength(0); // clear logs
				}
				
				
				long timeAfterBuild = System.currentTimeMillis();
				long buildTimeInSeconds = TimeUnit.SECONDS.convert(timeAfterBuild - timeBeforeBuild, TimeUnit.MILLISECONDS);
				
				//if build wasn't triggered then wait configured amount of time till next try
				TimeUnit.SECONDS.sleep(config.getScanInterval() - buildTimeInSeconds);
			}
			
		}
		catch(SVNException | InterruptedException | MavenInvocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns invocation request
	 * @return
	 */
	private InvocationRequest getInvocationRequest() {
		
		InvocationRequest request = new DefaultInvocationRequest();
		request.setDebug(true);
		request.setBaseDirectory(new File(config.getLocalProjectCopyPath()));
		request.setGoals(Arrays.asList("clean", "install -e -U"));
		
		return request;
	}
	
	/**
	 * Returns invoker
	 * @return
	 */
	private Invoker getInvoker(StringBuilder logs) {
		
		Invoker invoker = new DefaultInvoker();
		invoker.setOutputHandler((line) -> logs.append(line + "\n"));
		if(!config.getMavenHome().isEmpty()) {
			invoker.setMavenHome(new File(config.getMavenHome()));
		}
		return invoker;
	}

	private void saveLogsToFile(StringBuilder logs, String fileName) throws Exception {
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		writer.append(logs);
		writer.close();
	}

	private void logToConsole(String log) {
		System.out.println(log);
	}

}
