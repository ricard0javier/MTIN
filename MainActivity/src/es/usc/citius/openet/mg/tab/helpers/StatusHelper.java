package es.usc.citius.openet.mg.tab.helpers;

import es.usc.citius.openet.mg.tab.R;
import android.view.View;
import android.widget.TextView;

public class StatusHelper {

	private TextView textV;
	private View progressV;

	public StatusHelper(View container) {
		textV = (TextView) container.findViewById(R.id.status_text);
		progressV = container.findViewById(R.id.status_pb);
	}

	public void setReadyStatus() {
		textV.setText(R.string.ready);
		progressV.setVisibility(View.GONE);
	}

	public void setLoadingStatus() {
		textV.setText(R.string.loading);
		progressV.setVisibility(View.VISIBLE);
	}

}
