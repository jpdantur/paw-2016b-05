package edu.tp.paw.webapp.config;

public class ContextHelper {

	private String applicationName;
	
	/**
	 * @param applicationName
	 */
	public ContextHelper(String applicationName) {
		this.applicationName = applicationName;
	}
	
	/**
	 * @param url
	 * @return
	 */
	public String url(String url) {
		return this.applicationName+url;
	}
	
}
