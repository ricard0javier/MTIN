package es.usc.citius.openet.mg.tab.events;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Message;

public class EventBus {

	private static EventBus instance = null;

	public static EventBus getInstance() {
		if (instance == null) {
			instance = new EventBus();
		}
		return instance;
	}

	private ArrayList<EventBusListener> listeners = new ArrayList<EventBusListener>();

	public EventBus() {

	}
	
	public synchronized void register(EventBusListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public synchronized void unRegister(EventBusListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	public synchronized void sendEvent(Message event) {
		Iterator<EventBusListener> iterator = listeners.iterator();
		while (iterator.hasNext()) {
			iterator.next().onReceive(event);
		}
	}

}
