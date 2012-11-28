package es.usc.citius.openet.mg.tab.fragments;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.usc.citius.openet.mg.tab.R;
import es.usc.citius.openet.mg.tab.events.Event;
import es.usc.citius.openet.mg.tab.events.EventBus;
import es.usc.citius.openet.mg.tab.events.EventFactory;
import es.usc.citius.openet.mg.tab.tasks.DummyBackgroundTask;

public class FakeEventsFragment extends Fragment implements
		View.OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fake_events_fragment, null);

		view.findViewById(R.id.fakeB_1).setOnClickListener(this);
		view.findViewById(R.id.fakeB_2).setOnClickListener(this);
		view.findViewById(R.id.fakeB_3).setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fakeB_1: {
			Message event = EventFactory.createEvent(Event.EVENT_UPDATE_ON, "");
			EventBus.getInstance().sendEvent(event);
		}
			break;

		case R.id.fakeB_2: {
			Message event = EventFactory
					.createEvent(Event.EVENT_UPDATE_OFF, "");
			EventBus.getInstance().sendEvent(event);
		}
			break;
			
		case R.id.fakeB_3: {
			DummyBackgroundTask dbt = new DummyBackgroundTask();
			dbt.execute();
		}
			break;

		default:
			break;
		}

	}

}
