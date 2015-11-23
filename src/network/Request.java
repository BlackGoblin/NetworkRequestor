package network;

import java.util.HashMap;

/**
 * This class represents a network request
 */
public class Request {

	private Method method;
	private String url;
	private String userAgent = "AndroidApp";
	private int timeout = 10000;
	private HashMap<String, String> headers;
	private HashMap<String, String> params;

	/**
	 * creates a network request
	 * 
	 * @param method
	 *            the method of the request
	 * @param url
	 *            the URL to send the request to
	 * @param headers
	 *            the headers of the request
	 * @param params
	 *            the parameters of the request
	 */
	public Request(Method method, String url, HashMap<String, String> headers, HashMap<String, String> params) {
		this.method = method;
		this.url = url;
		this.headers = headers;
		this.params = params;
	}

	public Method getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * the request methods
	 */
	public enum Method {
		GET, POST
	}

}