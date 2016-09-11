package edu.tp.paw.webapp.config;

public class ContextHelper {

	private String applicationName;
	
	public ContextHelper(String applicationName) {
		this.applicationName = applicationName;
	}
	
	public String url(String url) {
		return this.applicationName+url;
	}
	
}
