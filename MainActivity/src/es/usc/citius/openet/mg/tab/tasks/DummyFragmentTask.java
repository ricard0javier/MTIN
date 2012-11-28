package es.usc.citius.openet.mg.tab.tasks;

import android.os.AsyncTask;
import android.os.Message;
import es.usc.citius.openet.mg.tab.events.Event;
import es.usc.citius.openet.mg.tab.events.EventBus;
import es.usc.citius.openet.mg.tab.events.EventFactory;
import es.usc.citius.openet.mg.tab.fragments.FragmentExample1;

public class DummyFragmentTask extends AsyncTask<Object, Integer, String> {

	private FragmentExample1 fragment;
	
	public DummyFragmentTask(FragmentExample1 fragment) {
		this.fragment = fragment;
	}
	
	protected void onPreExecute() {
		Message event = EventFactory.createEvent(Event.EVENT_UPDATE_ON);
		EventBus.getInstance().sendEvent(event);
		fragment.setLoadingStatus(true);
	}
	
	@Override
	protected String doInBackground(Object... params) {
		// Hacer cosas en segundo plano. Pj: Invocar un servicio web ;)
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		return "Datos de una GM a enviar tras obtenerla del servicio web";
	}
	
	protected void onPostExecute(String result) {
		Message event = EventFactory.createEvent(Event.EVENT_UPDATE_OFF);
		EventBus.getInstance().sendEvent(event);
		
		// Send event to other fragment
		event = EventFactory.createEvent(Event.EVENT_MG_SELECTED, result);
		EventBus.getInstance().sendEvent(event);
		fragment.setLoadingStatus(false);
		fragment.setAyncTaskFinished(this);
	}
	
	protected void onCancelled() {
		Message event = EventFactory.createEvent(Event.EVENT_UPDATE_OFF);
		EventBus.getInstance().sendEvent(event);
	}
	
}
