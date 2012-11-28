package es.usc.citius.openet.mg.tab.t4;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;

import es.usc.citius.openet.mg.tab.events.Event;
import es.usc.citius.openet.mg.tab.events.EventBus;
import es.usc.citius.openet.mg.tab.events.EventFactory;
import es.usc.citius.openet.mg.tab.net.HttpRequest;
import es.usc.citius.openet.mg.tab.net.HttpRequestFactory;
import es.usc.citius.openet.mg.tab.openet_api.OpenetAuthentication;
import es.usc.citius.openet.mg.tab.openet_api.OpenetWebServices;
import android.os.AsyncTask;
import android.os.Message;

public class TareasMedico extends AsyncTask<Object, Integer, String>{

	private Message errorEvent = null;
	
	@Override
	protected void onPreExecute() {
		Message event = EventFactory.createEvent(Event.EVENT_UPDATE_ON);
		EventBus.getInstance().sendEvent(event);
	}
	
	@Override
	protected String doInBackground(Object... params) {
		String query=OpenetWebServices.QUERY_PREFIXES+"SELECT ?te ?t ?n ?d ?ft WHERE { "
													+"?te rdf:type <http://citius.usc.es/hmb/systemonto.owl#TaskExecution> . "
													+"?te <http://citius.usc.es/hmb/systemonto.owl#hasTask> ?t . "
													+"?t <http://citius.usc.es/hmb/hlpnonto.owl#hasOperator> ?o . "
													+"?o rdf:type <http://citius.usc.es/hmb/ontgm.owl#InputMedicalOperator> . "
													+"?o <http://citius.usc.es/hmb/hlpnonto.owl#hasName> ?n . "
													+"?o <http://citius.usc.es/hmb/hlpnonto.owl#hasDescription> ?d . "
													+"?te <http://citius.usc.es/hmb/systemonto.owl#hasFinishTime> ?ft "
													+"} "
													+"ORDER BY DESC(?ft) LIMIT 20";
		String url=null;
		try {
			url= OpenetWebServices.OPENET_ONTO_WS_BASE_URI+"query/generic?query="+URLEncoder.encode(query,"UTF-8")+"&"+OpenetWebServices.OPENET_WS_AUTH_TOKEN+"="+OpenetAuthentication.getAuthToken();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(url);
		HttpRequest req = HttpRequestFactory.getGetRequest(url);
		String jsonResponse = null;
		try {
			req.execute();
			jsonResponse = req.getResponseContent();
			System.out.println(jsonResponse);
		} catch (SocketTimeoutException e) {
			Message event = EventFactory.createEvent(Event.EVENT_ERROR_NET_SERVER_NOT_RESPONDING);
			errorEvent = event;
			e.printStackTrace();
		} catch (IOException e) {
			Message event = EventFactory.createEvent(Event.EVENT_ERROR_NET_COULD_NOT_CONNECT);
			errorEvent = event;
			e.printStackTrace();
		} finally {
			req.disconnect();
		}
		return jsonResponse;
	}
	
	@Override
	protected void onPostExecute(String result) {
		Message event = EventFactory.createEvent(Event.EVENT_UPDATE_OFF);
		EventBus.getInstance().sendEvent(event);
		
		if (errorEvent!=null) {
			EventBus.getInstance().sendEvent(errorEvent);
		}
	}
}
