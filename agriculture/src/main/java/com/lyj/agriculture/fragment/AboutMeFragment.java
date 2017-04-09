package com.lyj.agriculture.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.lyj.agriculture.R;
import com.lyj.agriculture.activity.AboutMeActivity;
import com.lyj.agriculture.activity.AboutMeAddressActivity;
import com.lyj.agriculture.activity.AboutMeEvaluateActivity;
import com.lyj.agriculture.activity.AboutMeEvaluateLoginActivity;
import com.lyj.agriculture.activity.AboutMeOrderActivity;
import com.lyj.agriculture.activity.FankuiActivity;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.Util;

public class AboutMeFragment extends SherlockFragment {
	private Context mContext;
	private LinearLayout linearLogin, linearOrder, linearEvaluate, linearAddress, linearFankui, linearOur;
	private static TextView tv_login;
	public static Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (Const.customerName != null && tv_login != null) {
				tv_login.setText(Const.customerName);
			}
		};
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.personal, null);
	}

	private void init() {
		mContext = getActivity();

		linearOrder = (LinearLayout) getView().findViewById(R.id.person_order);
		linearEvaluate = (LinearLayout) getView().findViewById(R.id.person_evaluate);
		linearAddress = (LinearLayout) getView().findViewById(R.id.person_address);
		linearFankui = (LinearLayout) getView().findViewById(R.id.person_fankui);
		linearOur = (LinearLayout) getView().findViewById(R.id.person_our);
		linearLogin = (LinearLayout) getView().findViewById(R.id.personal_login);
		tv_login = (TextView) getView().findViewById(R.id.person_denglu);

		if (Const.customerName != null) {
			tv_login.setText(Const.customerName);
		}
		linearLogin.setOnClickListener(new linearLoginOnclickListener());
		linearOrder.setOnClickListener(new linearOrderOnclickListener());
		linearEvaluate.setOnClickListener(new linearEvaluateOnclickListener());
		linearAddress.setOnClickListener(new linearAddressOnclickListener());
		linearFankui.setOnClickListener(new linearFankuiOnclickListener());
		linearOur.setOnClickListener(new linearOurOnclickListener());
	}

	private class linearLoginOnclickListener implements OnClickListener {
		public void onClick(View v) {
			Util.gotoActivityNotFinish(mContext, AboutMeEvaluateLoginActivity.class);
		}
	}

	private class linearOrderOnclickListener implements OnClickListener {
		public void onClick(View v) {

			if (!LoginFirst()) {
				Util.gotoActivityNotFinish(mContext, AboutMeEvaluateLoginActivity.class);
			} else {
				Util.gotoActivityNotFinish(mContext, AboutMeOrderActivity.class);

			}
		}
	}

	private class linearEvaluateOnclickListener implements OnClickListener {
		public void onClick(View v) {

			if (!LoginFirst()) {
				Util.gotoActivityNotFinish(mContext, AboutMeEvaluateLoginActivity.class);
			} else {
				Util.gotoActivityNotFinish(mContext, AboutMeEvaluateActivity.class);

			}
		}
	}

	private class linearAddressOnclickListener implements OnClickListener {
		public void onClick(View v) {

			if (!LoginFirst()) {
				Util.gotoActivityNotFinish(mContext, AboutMeEvaluateLoginActivity.class);
			} else {
				Util.gotoActivityNotFinish(mContext, AboutMeAddressActivity.class);

			}
		}
	}

	private class linearFankuiOnclickListener implements OnClickListener {
		public void onClick(View v) {
			Util.gotoActivityNotFinish(mContext, FankuiActivity.class);
		}
	}

	private class linearOurOnclickListener implements OnClickListener {
		public void onClick(View v) {
			Util.gotoActivityNotFinish(mContext, AboutMeActivity.class);
		}
	}

	private boolean LoginFirst() {
		if (Const.customerID == -1) {
			Toast.makeText(mContext, "请先登录你的账号", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
