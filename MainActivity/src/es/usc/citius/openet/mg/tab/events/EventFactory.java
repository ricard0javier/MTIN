package es.usc.citius.openet.mg.tab.events;

import android.os.Message;

public class EventFactory {

	public static Message createEvent(int eventType, String payload) {
		return Event.createEvent(eventType, payload);
	}
	
	public static Message createEvent(int eventType) {
		return Event.createEvent(eventType);
	}
	
}
