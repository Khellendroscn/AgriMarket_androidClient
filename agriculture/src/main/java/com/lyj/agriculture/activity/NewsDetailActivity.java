package com.lyj.agriculture.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lyj.agriculture.R;
import com.lyj.agriculture.http.AsyncTaskWithDelayDlg;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.http.ImageLoader;
import com.lyj.agriculture.model.NewDescription;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.LogUtil;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

/**
 * 此处的分页加载可能不是最好的， 网络解析xml是所有数据都先下载下来， 只是图片进行分页加载，如果全要分页加载可能需要服务器端做处理
 * 
 * @author LiYajun
 * 
 * 
 */
public class NewsDetailActivity extends SherlockActivity {

	private Context mContext = this;
	private NewDescription newDescription;
	private ImageView imageView;
	private TextView tv;
	ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_detail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("资讯");

		imageView = (ImageView) findViewById(R.id.news_image);
		tv = (TextView) findViewById(R.id.news_text);
		imageLoader = new ImageLoader(mContext);
		
		WebAsyncTask webAsyncTask = new WebAsyncTask(mContext);
		webAsyncTask.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
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
				LogUtil.i("lyj", IPPort.URL_NEWOUTLINEDETAIL + Const.newsID);
				return httpClientUtils.getUrlContext(mContext, IPPort.URL_NEWOUTLINEDETAIL + Const.newsID);
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
					newDescription = (NewDescription) mSaxParserContentHandler.parseReadXmlObject(new NewDescription(),
							result);
					tv.setText(newDescription.getNewDetail());
					imageLoader.DisplayImage(newDescription.getNewImage(), imageView);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
