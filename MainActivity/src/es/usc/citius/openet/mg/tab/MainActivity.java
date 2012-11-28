package es.usc.citius.openet.mg.tab;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.Toast;
import es.usc.citius.openet.mg.tab.events.Event;
import es.usc.citius.openet.mg.tab.events.EventBus;
import es.usc.citius.openet.mg.tab.events.EventBusListener;
import es.usc.citius.openet.mg.tab.helpers.StatusHelper;
import es.usc.citius.openet.mg.tab.uiadapters.SectionsPagerAdapter;

public class MainActivity extends FragmentActivity implements EventBusListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private StatusHelper statusHelper;
	private ViewPager mViewPager;
	private int currentBackgroundWorks = 0;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				this.getApplicationContext(), getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		statusHelper = new StatusHelper(findViewById(R.id.status_container));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// No menu
		return true;
	}

	/*
	 * Registrarse para recibir eventos una vez se inició la Activity
	 */
	@Override
	public void onStart() {
		super.onStart();
		EventBus.getInstance().register(this);
		currentBackgroundWorks = 0;
		statusHelper.setReadyStatus();
	}

	/*
	 * Des-registrarse para recibir eventos una vez que la activity está
	 * preparada para destruirse (por falta de memoria o porque el usuario)
	 */
	@Override
	public void onStop() {
		super.onStop();
		EventBus.getInstance().unRegister(this);
	}

	@Override
	public void onReceive(Message event) {
		final int eventType = Event.getEventType(event);
		switch (eventType) {
		case Event.EVENT_UPDATE_ON:
			currentBackgroundWorks++;
			if (currentBackgroundWorks == 1) {
				statusHelper.setLoadingStatus();
			}
			break;
		case Event.EVENT_UPDATE_OFF:
			if (currentBackgroundWorks > 0) {
				currentBackgroundWorks--;
				if (currentBackgroundWorks <= 0) {
					statusHelper.setReadyStatus();
				}
			}
			break;
		default:
			// If event is and error, inform with Toast
			if (eventType>Event.EVENT_ERROR_BASE && eventType<Event.EVENT_ERROR_TOP) {
				Toast.makeText(this.getApplicationContext(), Event.toString(event), Toast.LENGTH_LONG).show();
			}
			break;
		}

	}

}
