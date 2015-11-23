package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 * This class is responsible for networking functions
 */
public class NetworkHelper {

	/**
	 * sends a request
	 * 
	 * @param request
	 *            the {@link Request} to be sent
	 * @param listener
	 *            the listener that gets called when the response is received
	 * @throws UnsupportedEncodingException
	 */
	public void send(final Request request, final NetworkResponseListener listener) throws UnsupportedEncodingException {

		if (listener == null) {
			throw new RuntimeException("NetworkResponseListenr should not be null");
		}

		// check the request method
		switch (request.getMethod()) {
		case POST:
			sendPost(request, listener);
			break;

		case GET:
			sendGet(request, listener);
			break;

		default:
			throw new RuntimeException("Method not supported");
		}

	}

	/**
	 * sends a post request
	 * 
	 * @param request
	 *            the {@link Request} to be sent
	 * @param listener
	 *            the listener that gets called when the response is received
	 * @throws UnsupportedEncodingException
	 */
	private void sendPost(final Request request, final NetworkResponseListener listener) throws UnsupportedEncodingException {
		// initialize a client and its request
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(request.getUrl());

		// set the headers
		if (request.getHeaders() != null) {
			for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}

		// set the parameters
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		if (request.getParams() != null) {
			for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
				urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));

		// run a thread to send the request and get its response
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				// execute the request
				try {
					HttpResponse response = client.execute(httpPost);

					// read the response
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					StringBuffer result = new StringBuffer();
					String line = "";
					while ((line = bufferedReader.readLine()) != null) {
						result.append(line);
					}

					String networkResponse = result.toString();
					int statusCode = response.getStatusLine().getStatusCode();

					// check if the request was successful or with error
					if (statusCode < 400 || statusCode > 599) {
						// there was no error
						listener.onResponse(networkResponse, statusCode);
					} else {
						// there was an error
						listener.onError(networkResponse, statusCode);
					}

				} catch (IOException e) {
					// an error has occurred
					e.printStackTrace();
					listener.onError(null, -1);
				}
			}
		});
		thread.start();
	}

	/**
	 * sends a get request
	 * 
	 * @param request
	 *            the {@link Request} to be sent
	 * @param listener
	 *            the listener that gets called when the response is received
	 */
	private void sendGet(final Request request, final NetworkResponseListener listener) {
		// add the parameters to the URL
		String url = request.getUrl();
		if (request.getParams() != null) {
			url += "?";
			for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
				url += entry.getKey() + "=" + entry.getValue() + "&";
			}
			url = url.substring(0, url.length() - 1);
		}

		// initialize a client and its request
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);

		// set the headers
		if (request.getHeaders() != null) {
			for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				// execute the request
				try {
					HttpResponse response = client.execute(httpGet);
					
					// read the response
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					StringBuffer result = new StringBuffer();
					String line = "";
					while ((line = bufferedReader.readLine()) != null) {
						result.append(line);
					}

					String networkResponse = result.toString();
					int statusCode = response.getStatusLine().getStatusCode();

					// check if the request was successful or with error
					if (statusCode < 400 || statusCode > 599) {
						// there was no error
						listener.onResponse(networkResponse, statusCode);
					} else {
						// there was an error
						listener.onError(networkResponse, statusCode);
					}
					
				} catch (IOException e) {
					// an error has occurred
					e.printStackTrace();
					listener.onError(null, -1);
				}
			}
		});
		thread.start();

	}

	/**
	 * The listener for network responses
	 */
	public interface NetworkResponseListener {

		/**
		 * callback when the network response is received
		 * 
		 * @param response
		 *            the response of the network
		 * @param statusCode
		 *            the network status code
		 */
		void onResponse(String response, int statusCode);

		/**
		 * the callback when an error is occurred
		 * 
		 * @param body
		 *            the body of the error
		 * @param statusCode
		 *            the network status code
		 */
		void onError(String body, int statusCode);
	}
}