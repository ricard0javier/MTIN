package es.usc.citius.openet.mg.tab.uiadapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import es.usc.citius.openet.mg.tab.R;
import es.usc.citius.openet.mg.tab.fragments.DummySectionFragment;
import es.usc.citius.openet.mg.tab.fragments.FakeEventsFragment;
import es.usc.citius.openet.mg.tab.fragments.FragmentExample1;
import es.usc.citius.openet.mg.tab.t4.Fragment_4_1_2;
import es.usc.citius.openet.mg.tab.t4.Fragment_4_3;
import es.usc.citius.openet.mg.tab.t4.Fragment_4_4;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the primary sections of the app.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	private Context context;

	public SectionsPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int i) {
		if (i == 0) {
			Fragment fragment = new FakeEventsFragment();
			return fragment;
		} else if (i == 1) {
			Fragment fragment = new FragmentExample1();
			return fragment;
		} else if(i == 2){
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
			fragment.setArguments(args);
			return fragment;
		} else if(i == 3){
			Fragment fragment = new Fragment_4_1_2();
			return fragment;
		} else if (i == 4){
			Fragment fragment = new Fragment_4_3();
			return fragment;
		} else{
			Fragment fragment = new Fragment_4_4();
			return fragment;
		}
	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return context.getString(R.string.title_section_fakeevents);
		case 1:
			return String.format(context.getString(R.string.title_section_fragment), 1);
		case 2:
			return String.format(context.getString(R.string.title_section_fragment), 2);
		case 3:
			return String.format("4.1 y 4.2");
		case 4:
			return String.format("4.3");
		case 5:
			return String.format("4.4");
		}
		return null;
	}
}
