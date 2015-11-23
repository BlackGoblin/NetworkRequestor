import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import network.NetworkHelper;
import network.NetworkHelper.NetworkResponseListener;
import network.Request;
import view.RequestFrame;
import view.RequestFrame.Method;
import view.RequestFrame.OnSendClickListener;

public class NetworkRequestor {

	// layout objects
	private static RequestFrame requestFrame;
	
	// helper objects
	private static NetworkHelper networkHelper;

	public static void main(String[] args) {

		// initialize the views
		requestFrame = new RequestFrame();
		
		// initialize helper objects
		networkHelper = new NetworkHelper();

		// set listener
		requestFrame.setOnSendClickListener(new OnSendClickListener() {

			@Override
			public void onClick(Method method, String url, HashMap<String, String> headers, HashMap<String, String> params) {
				Request.Method requestMethod;
				switch (method) {
				case POST:
					requestMethod = Request.Method.POST;
					break;
				case GET:
					requestMethod = Request.Method.GET;
					break;

				default:
					requestMethod = Request.Method.GET;
				}
				try {
					networkHelper.send(new Request(requestMethod,  url, headers, params), new NetworkResponseListener() {
						
						@Override
						public void onResponse(String response, int statusCode) {
							// the request was successful
							String formatedRessult = "Success\nstatus code: " + statusCode + "\nresponse:\n" + response; 
							requestFrame.setResult(formatedRessult);
						}
						
						@Override
						public void onError(String body, int statusCode) {
							// the request was not successful
							String formatedRessult = "Error\nstatus code: " + statusCode + "\nresponse:\n" + body;
							requestFrame.setResult(formatedRessult);
						}
					});
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}				
			}
		});

	}

}