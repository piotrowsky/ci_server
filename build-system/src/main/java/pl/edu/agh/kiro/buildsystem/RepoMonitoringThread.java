package pl.edu.agh.kiro.buildsystem;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
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
	
	/**
	 * Main constructor
	 * @param config
	 */
	public RepoMonitoringThread(Configuration config) {
		
		this.config = config;
	}

	/**
	 * Monitoring repository.
	 * If commit is detected then build process is triggered.
	 */
	@Override
	public void run() {
		
		try {
			//maven invoker init
			StringBuilder errorOutput = new StringBuilder();
			Invoker mvnInvoker = getInvoker(errorOutput);
			InvocationRequest mvnRequest = getInvocationRequest();
			
			//repo init
			RepoManager svnRepoManager = new RepoManager(config);
			long currentProjectCopyRevision = svnRepoManager.doCheckout();
			
			//main loop
			while(true) {
				
				long timeBeforeBuild = System.currentTimeMillis();
				
				//if commit was performed then update local copy, run maven build and handle eventual errors
				if(currentProjectCopyRevision < svnRepoManager.getLatestRevision()) {
					
					currentProjectCopyRevision = svnRepoManager.doCheckout();
					
					InvocationResult result = mvnInvoker.execute(mvnRequest);
					if(result.getExitCode() != 0) {
						handleError(errorOutput);
					}
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
		
		File pomFile = new File(config.getLocalProjectCopyPath() + config.getPomPath());
		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile(pomFile);
		request.setGoals(Arrays.asList("clean", "install -e -U"));
		
		return request;
	}
	
	/**
	 * Returns invoker
	 * @return
	 */
	private Invoker getInvoker(StringBuilder errorOutput) {
		
		Invoker invoker = new DefaultInvoker();
		invoker.setOutputHandler(new CustomOtputHandler(errorOutput));
		
		return invoker;
	}
	
	/**
	 * Handles error output
	 * @param errorOutput
	 */
	private void handleError(StringBuilder errorOutput) {
		
		//TODO: handle build error e.g. send email, revert latest repository commits etc.
		
	}
}
