package edu.tp.paw.webapp.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class RouteMethod {

	private String route;
	private HttpMethod method;
	
	private AntPathRequestMatcher matcher;
	
	public RouteMethod(String route, HttpMethod method) {
		super();
		this.route = route;
		this.method = method;
		this.matcher = new AntPathRequestMatcher(route, method.toString());
	}
	
	public AntPathRequestMatcher getMatcher() {
		return matcher;
	}

	public void setMatcher(AntPathRequestMatcher matcher) {
		this.matcher = matcher;
	}

	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public HttpMethod getMethod() {
		return method;
	}
	public void setMethod(HttpMethod method) {
		this.method = method;
	}
	@Override
	public String toString() {
		return "RouteMethod [route=" + route + ", method=" + method + "]";
	}
	
}
