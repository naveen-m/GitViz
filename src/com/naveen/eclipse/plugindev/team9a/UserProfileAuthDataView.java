package com.naveen.eclipse.plugindev.team9a;

import java.io.IOException;
import java.net.URL;

import org.eclipse.egit.*;

import com.naveen.eclipse.plugindev.constants.IGitHubConstants;

//import org.eclipse.egit.core.project.RepositoryFinder;
import static com.naveen.eclipse.plugindev.constants.IGitHubConstants.HOST_API;
import static com.naveen.eclipse.plugindev.constants.IGitHubConstants.HOST_DEFAULT;
import static com.naveen.eclipse.plugindev.constants.IGitHubConstants.HOST_GISTS;
import static com.naveen.eclipse.plugindev.constants.IGitHubConstants.PROTOCOL_HTTPS;
//import static org.eclipse.egit.github.core.client.IGitHubConstants..PROTOCOL_HTTPS;
//import static org.eclipse.egit.github.core.client.IGitHubConstants.HOST_API;
//import static org.eclipse.egit.github.core.client.IGitHubConstants.HOST_DEFAULT;
//import static org.eclipse.egit.github.core.client.IGitHubConstants.HOST_GISTS;

/**
 * @author naveen naveen
 * @Component: Eclipse Plugin Framework "View Component" Stub (un-integrated)
 */
public class UserProfileAuthDataView {

	//public GitHubUser retrieveUser(String name) {
	public Object retrieveUser(String name) {
		
		//Application app = new Object();
		
		String gitProjectName = "foo";

		// GitVizModelDAOService.java
		//TODO Wed 5/20: Integration with Naveen, after his class
		return null; 
	}
	

	/**
	 * INNER CLASS: GitHubClient
	 * @author dd3152
	 */
	public class GitHubClient {

		/**
		 * Base URI
		 */
		protected final String baseUri;

		/**
		 * Prefix to apply to base URI
		 */
		protected final String prefix;

		/**
		 * Create default client
		 */
		public GitHubClient() {
			this(HOST_API);
		}
		
		/**
		 * Create client for host name
		 *
		 * @param hostname
		 */
		public GitHubClient(String hostname) {
			this(hostname, -1, PROTOCOL_HTTPS);
		}
		
		/**
		 * Create client for host, port, and scheme
		 *
		 * @param hostname
		 * @param port
		 * @param scheme
		 */
		public GitHubClient(final String hostname, final int port,
				final String scheme) {
			final StringBuilder uri = new StringBuilder(scheme);
			uri.append("://"); //$NON-NLS-1$
			uri.append(hostname);
			if (port > 0)
				uri.append(':').append(port);
			baseUri = uri.toString();

			// Use URI prefix on non-standard host names
			if (HOST_API.equals(hostname))
				prefix = null;
			else
				prefix = IGitHubConstants.SEGMENT_V3_API;
		}
		
		/**
		 * Create API v3 client from URL.
		 * <p>
		 * This creates an HTTPS-based client with a host that contains the host
		 * value of the given URL prefixed with 'api' if the given URL is github.com
		 * or gist.github.com
		 *
		 * @param url
		 * @return client
		 */
//public static GitHubClient createClient(String url) {
public GitHubClient createClient(String url) {
			try {
				String host = new URL(url).getHost();
				if (HOST_DEFAULT.equals(host) || HOST_GISTS.equals(host))
					host = HOST_API;
				return new GitHubClient(host);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}	
	
	
	
	
}
