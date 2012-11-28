package es.usc.citius.openet.mg.tab.events;

import android.os.Message;

public class Event {

	/*
	 * Enviado al iniciar una tarea en segundo plano
	 * Recibido por la MainActivity para feedback visual
	 */
	public static final int EVENT_UPDATE_ON = 1;
	/*
	 * Enviado al terminar una tarea en segundo plano
	 * Recibido por la MainActivity para feedback visual
	 */
	public static final int EVENT_UPDATE_OFF = 2;
	/*
	 * Enviado cuando el usuario quiere que los elementos de la interfaz se actualicen
	 * Recibido por todos los componentes de la interfaz.
	 * Al recibir este evento (y para simplificar), estos componentes pueden ejecutar el código de recepción del último evento de cambio de contenido.
	 */
	public static final int EVENT_DO_UPDATE = 3;
	/*
	 * Enviado al seleccionar un paciente por parte de la lista de pacientes y la agenda.
	 * Recibido por el fragment de datos de paciente, de guías médicas del paciente, de la propia lista de pacientes y de ultimas tareas del paciente.
	 */
	public static final int EVENT_PATIENT_SELECTED = 10;
	/*
	 * Enviado al seleccionar una guía médica de un paciente desde la lista de guías médicas o desde la agenda.
	 * Recibido por la lista de guías médicas, por el panel de opciones de guías médicas, la lista de tareas de la guía médica
	 */
	public static final int EVENT_MG_SELECTED = 11;
	/*
	 * Enviado al seleccionar una tarea en la lista de tareas o en la agenda.
	 * Recibido por el componente de formularios (5)
	 */
	public static final int EVENT_TASK_SELECTED = 12;
	/*
	 * Enviado al realizar una tarea sobre una guía médica (opciones de gm (2))
	 * Recibido por la lista de tareas, el visor de formularios (5) y la agenda.
	 */
	public static final int EVENT_MG_MODIFIED = 13;
	/*
	 * Enviado al realizar una tarea sobre una tarea (opciones de tarea (2) o al enviar un formulario (5))
	 * Recibido por la lista de tareas, el visor de formularios (5) y la agenda.
	 */
	public static final int EVENT_TASK_MODIFIED = 14;
	
	
	public static final int EVENT_ERROR_BASE = 30;
	/*
	 * Enviado cuando ocurre algún error de conexión de red.
	 * Recibido por gestores de errores en la activity (no aplicable a ningún componente a realizar en la práctica).
	 */
	public static final int EVENT_ERROR_NET_COULD_NOT_CONNECT = 31;
	/*
	 * Enviado cuando ocurre algún error de conexión de red.
	 * Recibido por gestores de errores en la activity (no aplicable a ningún componente a realizar en la práctica).
	 */
	public static final int EVENT_ERROR_NET_SERVER_NOT_RESPONDING = 32;
	/*
	 * Enviado si el servidor de red al que se trata de conectar no responde en el timeout establecido.s
	 * Recibido por gestores de errores en la activity (no aplicable a ningún componente a realizar en la práctica).
	 */
	public static final int EVENT_ERROR_API_FORMAT = 41;
	
	public static final int EVENT_ERROR_TOP = 50;

	public static Message createEvent(int eventType, String payload) {
		Message m = new Message();
		m.what = eventType;
		if (payload!=null) {
			m.getData().putString("payload", payload);
		}
		return m;
	}
	
	public static Message createEvent(int eventType) {
		return createEvent(eventType, null);
	}

	public static String getEventPayload(Message event) {
		return event.getData().getString("payload");
	}

	public static int getEventType(Message event) {
		return event.what;
	}

	public static String toString(Message event) {
		switch (event.what) {
		    // Update events
		case EVENT_UPDATE_ON:
			return "EVENT_UPDATE_ON";
		case EVENT_UPDATE_OFF:
			return "EVENT_UPDATE_OFF";
		case EVENT_DO_UPDATE:
			return "EVENT_DO_UPDATE";
			// Data events
		case EVENT_PATIENT_SELECTED:
			return "EVENT_PATIENT_SELECTED";
		case EVENT_MG_SELECTED:
			return "EVENT_MG_SELECTED";
		case EVENT_MG_MODIFIED:
			return "EVENT_MG_MODIFIED";
		case EVENT_TASK_SELECTED:
			return "EVENT_TASK_SELECTED";
		case EVENT_TASK_MODIFIED:
			return "EVENT_TASK_MODIFIED";
			// Errors
		case EVENT_ERROR_NET_COULD_NOT_CONNECT:
			return "EVENT_ERROR_NET_COULD_NOT_CONNECT";
		case EVENT_ERROR_NET_SERVER_NOT_RESPONDING:
			return "EVENT_ERROR_NET_SERVER_NOT_RESPONDING";
		case EVENT_ERROR_API_FORMAT:
			return "EVENT_ERROR_API_FORMAT";
		default:
			return "EVENT_UNKNOWN";
		}
	}

}
