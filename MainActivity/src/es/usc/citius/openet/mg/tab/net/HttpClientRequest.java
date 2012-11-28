package es.usc.citius.openet.mg.tab.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientRequest implements HttpRequest {

	public static final int METHOD_GET = 1;
	public static final int METHOD_POST = 2;
	private HttpUriRequest request = null;
	private List<NameValuePair> nameValuePairs = null;
	private int resultStatus = 0;
	private HttpResponse response = null;
	private boolean executedOK = false;

	public HttpClientRequest(String url) {
		request = new HttpGet(url);
	}

	public HttpClientRequest(String url, int method) {
		if (method == METHOD_POST) {
			request = new HttpPost(url);
			nameValuePairs = new ArrayList<NameValuePair>(1);
		} else {
			request = new HttpGet(url);
		}
	}

	@Override
	public void addParameterRequest(String key, String value) {
		if (request instanceof HttpGet) {
			throw new IllegalStateException();
		}
		nameValuePairs.add(new BasicNameValuePair(key, value));
	}

	@Override
	public void addHeader(String key, String value) {
		request.addHeader(key, value);
	}

	@Override
	public void execute() throws IOException {
		if (executedOK || response != null) {
			throw new IllegalStateException();
		}
		try {
			if (request instanceof HttpPost) {
				((HttpPost) request).setEntity(new UrlEncodedFormEntity(
						nameValuePairs));
			}
			response = HttpRequestFactory.getHttpClient().execute(request);
			resultStatus = response.getStatusLine().getStatusCode();
			executedOK = true;
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IOException(ex);
		}

	}

	@Override
	public String getResponseContent() throws IOException {
		if (!executedOK || response == null) {
			throw new IllegalStateException();
		}
		return EntityUtils.toString(response.getEntity());
	}

	@Override
	public InputStream getResponseInputStream() throws IOException {
		if (!executedOK || response == null) {
			throw new IllegalStateException();
		}
		return response.getEntity().getContent();
	}

	@Override
	public String[] getResponseHeader(String headerName) {
		if (!executedOK || response == null) {
			throw new IllegalStateException();
		}
		Header[] hs = response.getHeaders(headerName);
		String[] ss = new String[hs.length];
		for (int i = 0; i < ss.length; i++) {
			ss[i] = hs[i].toString();
		}
		return ss;
	}

	@Override
	public int getResponseStatus() {
		if (!executedOK || response == null) {
			throw new IllegalStateException();
		}
		return response.getStatusLine().getStatusCode();
	}

	@Override
	public boolean isStatusOK() {
		if (!executedOK || response == null) {
			throw new IllegalStateException();
		}
		return resultStatus == 200;
	}

	@Override
	public void disconnect() {
		try {
			response.getEntity().getContent().close();
		} catch (Exception e1) {
			try {
				request.abort();
			} catch (Exception e2) {
			}
		}

	}

}
