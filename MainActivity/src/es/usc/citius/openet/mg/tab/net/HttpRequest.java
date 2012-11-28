package es.usc.citius.openet.mg.tab.net;

import java.io.IOException;
import java.io.InputStream;

public interface HttpRequest {

	public void addParameterRequest(String key, String value);
	public void addHeader(String key, String value);
	public void execute() throws IOException;
	
	public String getResponseContent() throws IOException;
	public InputStream getResponseInputStream() throws IOException;
	public String[] getResponseHeader(String headerName);
	public int getResponseStatus();
	public boolean isStatusOK();
	
	public void disconnect();
	
}
