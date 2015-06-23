package pl.edu.agh.kiro.buildsystem;

import java.io.File;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

/**
 * Repository manager.
 * Simplifies using repository.
 * 
 * @author Lukasz.Gruba
 */
public class RepoManager {
	
	/**
	 * Configuration instance set on RepoManager creation.
	 */
	private Configuration config;
	
	/**
	 * Local project copy
	 */
	private File localProjectCopyLocation;
	
	/**
	 * Repository
	 */
	private SVNRepository repository;
	
	/**
	 * SVN client manager
	 */
	private SVNClientManager svnClientManager;
	
	/**
	 * Update client. Used for performing updates and checkouts on local project copy.
	 */
	private SVNUpdateClient updateClient;
	
	/**
	 * Constructor which configures RepoManager based on provided {@link Configuration} instance.
	 * @param config
	 * @throws SVNException
	 */
	public RepoManager(Configuration config) throws SVNException {
		
		this.config = config;
		
		localProjectCopyLocation = new File(getLocalProjectCopyPath());
		repository = getRepository();
		svnClientManager = SVNClientManager.newInstance(null, repository.getAuthenticationManager());
		updateClient = svnClientManager.getUpdateClient();
		updateClient.setIgnoreExternals(false);
	}
	
	/**
	 * Performs checkout using predefined {@link SVNUpdateClient}
	 * @return
	 * @throws SVNException
	 */
	public long doCheckout() throws SVNException {
		return updateClient.doCheckout(repository.getLocation(), localProjectCopyLocation, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, true);
	}
	
	/**
	 * Returns repository URL
	 * @return
	 * @throws SVNException 
	 */
	private SVNRepository getRepository() throws SVNException {
		return SVNRepositoryFactory.create(SVNURL.parseURIEncoded(this.config.getRepositoryProjectUrl()));
	}
	
	/**
	 * Returns path to local project copy
	 * @return
	 */
	private String getLocalProjectCopyPath() {
		return this.config.getLocalProjectCopyPath();
	}
	
	/**
	 * Returns repository's latest revision
	 * @return
	 * @throws SVNException
	 */
	public long getLatestRevision() throws SVNException {
		return repository.getLatestRevision();
	}
}
