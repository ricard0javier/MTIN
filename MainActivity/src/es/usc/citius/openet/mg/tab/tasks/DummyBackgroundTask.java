package es.usc.citius.openet.mg.tab.tasks;

import java.io.IOException;
import java.net.SocketTimeoutException;

import android.os.AsyncTask;
import android.os.Message;
import es.usc.citius.openet.mg.tab.events.Event;
import es.usc.citius.openet.mg.tab.events.EventBus;
import es.usc.citius.openet.mg.tab.events.EventFactory;
import es.usc.citius.openet.mg.tab.net.HttpRequest;
import es.usc.citius.openet.mg.tab.net.HttpRequestFactory;
import es.usc.citius.openet.mg.tab.openet_api.OpenetAuthentication;
import es.usc.citius.openet.mg.tab.openet_api.OpenetWebServices;

public class DummyBackgroundTask extends AsyncTask<Object, Integer, String>{

	private Message errorEvent = null;
	
	/*
	 * Lo de aquí dentro se ejecuta en el Thread de la UI (se pueden enviar eventos)
	 */
	@Override
	protected void onPreExecute() {
		Message event = EventFactory.createEvent(Event.EVENT_UPDATE_ON);
		EventBus.getInstance().sendEvent(event);
	}
	
	/*
	 * Lo de aquí dentro se ejecuta en otro Thread (NO se pueden enviar eventos)
	 */
	@Override
	protected String doInBackground(Object... params) {
		// Hacer cosas en segundo plano. Pj: Invocar un servicio web ;)
		// En este método no se pueden enviar eventos por el motivo explicado en el onPostExecute aquí abajo.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		final String url = OpenetWebServices.OPENET_ONTO_WS_BASE_URI+"status"+"?"+OpenetWebServices.OPENET_WS_AUTH_TOKEN+"="+OpenetAuthentication.getAuthToken();
		HttpRequest req = HttpRequestFactory.getGetRequest(url);
		String jsonResponse = null;
		try {
			req.execute();
			jsonResponse = req.getResponseContent();
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
	
	/*
	 * Lo de aquí dentro se ejecuta en el Thread de la UI (se pueden enviar eventos)
	 */
	@Override
	protected void onPostExecute(String result) {
		Message event = EventFactory.createEvent(Event.EVENT_UPDATE_OFF);
		EventBus.getInstance().sendEvent(event);
		
		event = EventFactory.createEvent(Event.EVENT_PATIENT_SELECTED, result);
		EventBus.getInstance().sendEvent(event);
		
		/* El evento de error se envía en el onPostExecute y no en el doInBackground.
		 * Si se ejecutara en el doInBackground no pasaría por un Handler y no se manejaría en el Thread de la UI, 
		 * con lo cual la aplicación se cerraría si en el manejo del evento se modifica la interfaz. 
		 */
		if (errorEvent!=null) {
			EventBus.getInstance().sendEvent(errorEvent);
		}
	}
	
	protected void onCancelled() {
		Message event = EventFactory.createEvent(Event.EVENT_UPDATE_OFF);
		EventBus.getInstance().sendEvent(event);
	}

}
