package es.usc.citius.openet.mg.tab.fragments;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import es.usc.citius.openet.mg.tab.R;
import es.usc.citius.openet.mg.tab.events.Event;
import es.usc.citius.openet.mg.tab.events.EventBus;
import es.usc.citius.openet.mg.tab.events.EventBusListener;
import es.usc.citius.openet.mg.tab.events.EventFactory;
import es.usc.citius.openet.mg.tab.tasks.DummyFragmentTask;

public class FragmentExample1 extends Fragment implements EventBusListener {

	private TextView textTV;
	private View progressBar = null;
	private DummyFragmentTask runningTask = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.example_fragment, null);

		textTV = (TextView) view.findViewById(R.id.fakeTV_1);
		progressBar = view.findViewById(R.id.fragment_title_pb);

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		EventBus.getInstance().register(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EventBus.getInstance().unRegister(this);
		if (this.runningTask != null) {
			this.runningTask.cancel(true);
		}
	}

	@Override
	public void onReceive(Message event) {
		final int eventType = Event.getEventType(event);
		if (eventType == Event.EVENT_PATIENT_SELECTED) {
			String patient = Event.getEventPayload(event);
			
			if (patient == null) {
				return;
			}
			
			manageEventPatientSelected(patient);

			Toast.makeText(this.getActivity(),
					"Recibido: " + Event.toString(event), Toast.LENGTH_LONG)
					.show();

			// Enviar otra tarea en segundo plano
			DummyFragmentTask dft = new DummyFragmentTask(this);
			runningTask = dft;
			dft.execute();
		}
	}

	private void manageEventPatientSelected(String patient) {
		if (patient == null) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		try {
			JSONObject jsonObject = new JSONObject(patient);
			sb.append(jsonObject.getString("name"));
			sb.append(" (");
			sb.append(jsonObject.getInt("number_of_statements"));
			sb.append(")");

		} catch (JSONException e) {
			Message event = EventFactory.createEvent(Event.EVENT_ERROR_API_FORMAT);
			EventBus.getInstance().sendEvent(event);
		}
		textTV.setText("Status: \n" + sb.toString());

	}

	public void setLoadingStatus(boolean isLoading) {
		progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
	}

	public void setAyncTaskFinished(DummyFragmentTask task) {
		runningTask = null;
	}
}
