package com.lyj.agriculture.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lyj.agriculture.R;
import com.lyj.agriculture.activity.FragmentChangeActivity;
import com.lyj.agriculture.util.Const;

public class MenuFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] item = getResources().getStringArray(R.array.item_names);

		SampleAdapter adapter = new SampleAdapter(getActivity());
		adapter.add(new SampleItem(item[0], R.drawable.agr_news));
		adapter.add(new SampleItem(item[1], R.drawable.agr_sell));
		adapter.add(new SampleItem(item[2], R.drawable.agr_feature));
		adapter.add(new SampleItem(item[3], R.drawable.agr_shoppingcart));
		adapter.add(new SampleItem(item[4], R.drawable.agr_me));
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
			newContent = new NewsFragment();
			break;
		case 1:
			newContent = new AgriSellFragment();
			break;
		case 2:
			newContent = new charactAgriFragment();
			break;
		case 3:
			if (Const.customerID == -1) {
				Toast.makeText(getActivity(), "你还没有登录，不能查看购物车哦", Toast.LENGTH_SHORT).show();
				newContent = new ColorFragment(android.R.color.white);
			} else {
				newContent = new ShoppingCardFragment();
			}
			break;
		case 4:
			newContent = new AboutMeFragment();
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		if (getActivity() instanceof FragmentChangeActivity) {
			FragmentChangeActivity fca = (FragmentChangeActivity) getActivity();
			fca.switchContent(fragment);
		}
	}

	private class SampleItem {
		public String tag;
		public int iconRes;

		public SampleItem(String tag, int iconRes) {
			this.tag = tag;
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_item, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}

}
