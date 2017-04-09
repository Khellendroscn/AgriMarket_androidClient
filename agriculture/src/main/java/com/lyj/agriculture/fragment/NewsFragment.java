package com.lyj.agriculture.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.lyj.agriculture.R;
import com.lyj.agriculture.activity.NewsDetailActivity;
import com.lyj.agriculture.adapter.NewsAdapter;
import com.lyj.agriculture.http.AsyncTaskWithDelayDlg;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.model.NewOutline;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.LogUtil;
import com.lyj.agriculture.util.Util;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

public class NewsFragment extends SherlockFragment {

	private ListView listView = null;
	private NewsAdapter newsAdapter = null;
	private List<Object> list = null;
	private List<NewOutline> list_newOutline = null;
	private Context mContext;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		listView = (ListView) getView().findViewById(R.id.news_list);
		init();

	}

	private void init() {
		mContext = getActivity();
		list = new ArrayList<Object>();
		list_newOutline = new ArrayList<NewOutline>();

		// 下载xml,并解析
		WebAsyncTask webAsyncTask = new WebAsyncTask(getActivity());
		webAsyncTask.execute();

	}

	private class NewsOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			Const.newsID = list_newOutline.get(position).getNewID();
			Util.gotoActivityNotFinish(mContext, NewsDetailActivity.class);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.news_list, null);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	// 异步下载网络xml数据
	private class WebAsyncTask extends AsyncTaskWithDelayDlg<Void, Void, String> {
		public WebAsyncTask(Context context) {
			super(context);
		}

		@Override
		protected String doInBackground2(Void... params) {
			try {
				HttpClientUtils httpClientUtils = new HttpClientUtils();
				return httpClientUtils.getUrlContext(mContext, IPPort.URL_NEWSOUTLINE);
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
					list = mSaxParserContentHandler.parseReadXml(new NewOutline(), result);
					NewOutline newOutline = new NewOutline();
					for (Object object : list) {
						newOutline = (NewOutline) object;
						list_newOutline.add(newOutline);
					}
					// 创建适配器，设置数据
					newsAdapter = new NewsAdapter(mContext, list_newOutline);
					listView.setAdapter(newsAdapter);

					listView.setOnItemClickListener(new NewsOnItemClickListener());

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}
