package com.lyj.agriculture.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.lyj.agriculture.R;
import com.lyj.agriculture.activity.ProductListActivity;
import com.lyj.agriculture.adapter.AgriSellAdapter;
import com.lyj.agriculture.http.AsyncTaskWithDelayDlg;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.model.FClassification;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.Util;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

public class AgriSellFragment extends SherlockFragment {

	private ListView listView = null;
	private AgriSellAdapter agriSellAdapter = null;
	private List<Object> list = null;
	private List<FClassification> list_fClassifications = null;
	private Context mContext;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		listView = (ListView) getView().findViewById(R.id.agri_sell_list);
		init();

	}

	private void init() {
		mContext = getActivity();
		list = new ArrayList<Object>();
		list_fClassifications = new ArrayList<FClassification>();

		// 下载xml,并解析
		WebAsyncTask webAsyncTask = new WebAsyncTask(getActivity());
		webAsyncTask.execute();

		// 创建适配器，设置数据
		agriSellAdapter = new AgriSellAdapter(mContext, list_fClassifications);
		listView.setAdapter(agriSellAdapter);

		listView.setOnItemClickListener(new FclassificationOnItemClickListener());
	}

	private class FclassificationOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			Const.fClassificationID = list_fClassifications.get(position).getfClassificationID();
			Util.gotoActivityNotFinish(mContext, ProductListActivity.class);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return inflater.inflate(R.layout.agri_sell_list, null);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	//	setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}




	/*@Override
	public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu,
			com.actionbarsherlock.view.MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.menu_searchview, menu);
		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search);
		searchView.setOnQueryTextListener(new ProductOnQueryTextListener());
		super.onCreateOptionsMenu(menu, inflater);
	}
*/
/*	class ProductOnQueryTextListener implements OnQueryTextListener{

		@Override
		public boolean onQueryTextSubmit(String query) {
			// TODO Auto-generated method stub
			//Const.fClassificationID = list_fClassifications.get(position).getfClassificationID();
			Util.gotoActivityNotFinish(mContext, ProductListActivity.class);

			return false;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			//pli.setSearchText(newText);
			
			return false;
		}
	}
	*/
	// 异步下载网络xml数据
	private class WebAsyncTask extends AsyncTaskWithDelayDlg<Void, Void, String> {
		public WebAsyncTask(Context context) {
			super(context);
		}

		@Override
		protected String doInBackground2(Void... params) {
			try {
				HttpClientUtils httpClientUtils = new HttpClientUtils();
				return httpClientUtils.getUrlContext(mContext, IPPort.URL_FCLASSIFICATION);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute2(String result) {
			if (result == null) {
				Toast.makeText(mContext, "连接服务器异常", Toast.LENGTH_SHORT).show();
			} else {
				try {
					SAXParserContentHandler mSaxParserContentHandler = new SAXParserContentHandler();
					list = mSaxParserContentHandler.parseReadXml(new FClassification(), result);
					FClassification fClassification = new FClassification();
					for (Object object : list) {
						fClassification = (FClassification) object;
						list_fClassifications.add(fClassification);
					}
					agriSellAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}
