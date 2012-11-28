package es.usc.citius.openet.mg.tab.net;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.net.http.AndroidHttpClient;

public class HttpRequestFactory {

	private static HttpClient httpClient = null;

    public static HttpRequest getGetRequest(String url) {
        return new HttpClientRequest(url, HttpClientRequest.METHOD_GET);
    }

    public static HttpRequest getPostRequest(String url) {
        return new HttpClientRequest(url, HttpClientRequest.METHOD_POST);
    }

    public static HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = AndroidHttpClient.newInstance("openet4mg_tab v/1.0");
            HttpParams params = httpClient.getParams();
            HttpClientParams.setRedirecting(params, false);
            HttpConnectionParams.setConnectionTimeout(params, 6000);
            HttpConnectionParams.setSoTimeout(params, 20000);
            params.setParameter(ConnRoutePNames.DEFAULT_PROXY, null);
        }
        return httpClient;
    }
	
}
