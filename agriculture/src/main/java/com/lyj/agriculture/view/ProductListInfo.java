package com.lyj.agriculture.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.lyj.agriculture.R;
import com.lyj.agriculture.activity.ProductDetailActivity;
import com.lyj.agriculture.adapter.ProductListAdapter;
import com.lyj.agriculture.http.AsyncTaskWithDelayDlg;
import com.lyj.agriculture.http.HttpClientUtils;
import com.lyj.agriculture.http.IPPort;
import com.lyj.agriculture.model.ProductOutline;
import com.lyj.agriculture.util.Const;
import com.lyj.agriculture.util.LogUtil;
import com.lyj.agriculture.util.Util;
import com.lyj.agriculture.xmlparse.SAXParserContentHandler;

public class ProductListInfo {
	private Context mContext;
	private ListView listView1, listView2, listView3;
	private List<Object> list1, list2, list3;
	private List<ProductOutline> list_ProductOutline1, list_ProductOutline2, list_ProductOutline3;
	private ProductListAdapter productListAdapter1, productListAdapter2, productListAdapter3;
	private View moreView1, moreView2, moreView3;
	private int currentCount1, currentCount2, currentCount3;
	private ProductOutline productOutline;
	private int lastItem1, lastItem2, lastItem3;
	private boolean isLast1, isLast2, isLast3;

	public ProductListInfo(Context context) {
		mContext = context;

	}

	public void setSearchText(String searchString) {
		// 下载xml,并解析
		for (int i = 1; i < 4; i++) {
			WebAsyncTask webAsyncTask = new WebAsyncTask(mContext, i, searchString);
			webAsyncTask.execute();
		}
	}

	public View getNewList() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.product_list, null);
		listView3 = (ListView) view.findViewById(R.id.product_list);
		moreView3 = LayoutInflater.from(mContext).inflate(R.layout.load_more, null);
		list3 = new ArrayList<Object>();
		list_ProductOutline3 = new ArrayList<ProductOutline>();
		// 下载xml,并解析
		WebAsyncTask webAsyncTask = new WebAsyncTask(mContext, 3, "");
		webAsyncTask.execute();

		productListAdapter3 = new ProductListAdapter(mContext, list_ProductOutline3);

		listView3.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (!isLast3 && lastItem3 == currentCount3 && scrollState == this.SCROLL_STATE_IDLE) {
					loadMore3();
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				lastItem3 = firstVisibleItem + visibleItemCount - 1;
			}
		});
		listView3.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Const.productID = list_ProductOutline3.get(position).getProductID();
				Util.gotoActivityNotFinish(mContext, ProductDetailActivity.class);
			}
		});
		return view;
	}

	public View getPriceList() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.product_list, null);
		listView2 = (ListView) view.findViewById(R.id.product_list);
		moreView2 = LayoutInflater.from(mContext).inflate(R.layout.load_more, null);
		list2 = new ArrayList<Object>();
		list_ProductOutline2 = new ArrayList<ProductOutline>();
		// 下载xml,并解析
		WebAsyncTask webAsyncTask = new WebAsyncTask(mContext, 2, "");
		webAsyncTask.execute();

		productListAdapter2 = new ProductListAdapter(mContext, list_ProductOutline2);

		listView2.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (!isLast2 && lastItem2 == currentCount2 && scrollState == this.SCROLL_STATE_IDLE) {
					loadMore2();
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				lastItem2 = firstVisibleItem + visibleItemCount - 1;
			}
		});
		listView2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Const.productID = list_ProductOutline2.get(position).getProductID();
				Util.gotoActivityNotFinish(mContext, ProductDetailActivity.class);
			}
		});
		return view;
	}

	public View getCountList() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.product_list, null);
		listView1 = (ListView) view.findViewById(R.id.product_list);
		moreView1 = LayoutInflater.from(mContext).inflate(R.layout.load_more, null);
		list1 = new ArrayList<Object>();
		list_ProductOutline1 = new ArrayList<ProductOutline>();
		// 下载xml,并解析
		WebAsyncTask webAsyncTask = new WebAsyncTask(mContext, 1, "");
		webAsyncTask.execute();

		productListAdapter1 = new ProductListAdapter(mContext, list_ProductOutline1);

		listView1.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (!isLast1 && lastItem1 == currentCount1 && scrollState == this.SCROLL_STATE_IDLE) {
					loadMore1();
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				lastItem1 = firstVisibleItem + visibleItemCount - 1;
			}
		});
		listView1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Const.productID = list_ProductOutline1.get(position).getProductID();
				Util.gotoActivityNotFinish(mContext, ProductDetailActivity.class);
			}
		});
		return view;
	}

	private void loadMore1() {
		moreView1.setVisibility(View.VISIBLE);
		
		if (Const.pageItemCount + currentCount1 < list1.size()) {
			LogUtil.i("lyj", "size = "+list1.size());
//			LogUtil.i("lyj", "currentCount1 = "+currentCount1);
			for (int i = currentCount1; i < currentCount1 + Const.pageItemCount; i++) {
				LogUtil.i("lyj", "currentCount1 = "+currentCount1+" , i = "+i);
				productOutline = (ProductOutline) list1.get(i);
				list_ProductOutline1.add(productOutline);
				
			}
			currentCount1 = currentCount1 + Const.pageItemCount;
			isLast1 = false;
		} else {
			for (int i = currentCount1; i < list1.size(); i++) {
				productOutline = (ProductOutline) list1.get(i);
				list_ProductOutline1.add(productOutline);
				currentCount1 = list1.size();
			}
			isLast1 = true;
			listView1.removeFooterView(moreView1);
		}
		productListAdapter1.notifyDataSetChanged();
		moreView1.setVisibility(View.GONE);
	}

	private void loadMore2() {
		moreView2.setVisibility(View.VISIBLE);
		if (Const.pageItemCount + currentCount2 < list2.size()) {
			for (int i = currentCount2; i < currentCount2 + Const.pageItemCount; i++) {
				productOutline = (ProductOutline) list2.get(i);
				list_ProductOutline2.add(productOutline);
				
			}
			currentCount2 = currentCount2 + Const.pageItemCount;
			isLast2 = false;
		} else {
			for (int i = currentCount2; i < list2.size(); i++) {
				productOutline = (ProductOutline) list2.get(i);
				list_ProductOutline2.add(productOutline);
				currentCount2 = list2.size();
			}
			isLast2 = true;
			listView2.removeFooterView(moreView2);
		}
		productListAdapter2.notifyDataSetChanged();
		moreView2.setVisibility(View.GONE);
	}

	private void loadMore3() {
		moreView3.setVisibility(View.VISIBLE);
		if (Const.pageItemCount + currentCount3 < list3.size()) {
			for (int i = currentCount3; i < currentCount3 + Const.pageItemCount; i++) {
				productOutline = (ProductOutline) list3.get(i);
				list_ProductOutline3.add(productOutline);
				
			}currentCount3 = currentCount3 + Const.pageItemCount;
			isLast3 = false;
		} else {
			for (int i = currentCount3; i < list3.size(); i++) {
				productOutline = (ProductOutline) list3.get(i);
				list_ProductOutline3.add(productOutline);
				currentCount3 = list3.size();
			}
			isLast3 = true;
			listView3.removeFooterView(moreView3);
		}
		productListAdapter3.notifyDataSetChanged();
		moreView3.setVisibility(View.GONE);
	}

	private class SeachOnQueryTextListener implements OnQueryTextListener {

		@Override
		public boolean onQueryTextSubmit(String query) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	// 异步下载网络xml数据
	private class WebAsyncTask extends AsyncTaskWithDelayDlg<Void, Void, String> {
		private int tag = 0;

		private String searchString;

		public WebAsyncTask(Context context, int tag, String s) {
			super(context);
			this.tag = tag;
			searchString = s;
		}

		@Override
		protected String doInBackground2(Void... params) {
			try {
				HttpClientUtils httpClientUtils = new HttpClientUtils();
				if (tag == 1) {
					if (searchString == null || searchString == "") {
						return httpClientUtils.getUrlContext(mContext, IPPort.URL_PRODUCTOUTLINE
								+ Const.fClassificationID + "&orderkey=" + "recentSellCount%20DESC");
					} else {
						return httpClientUtils.getUrlContext(mContext, IPPort.URL_PRODUCTOUTLINESEARCH
								+ Const.fClassificationID + "&orderkey=" + "recentSellCount%20DESC" + "&searchBarText="
								+ searchString);
					}
				} else if (tag == 2) {
					if (searchString == null || searchString == "") {

						return httpClientUtils.getUrlContext(mContext, IPPort.URL_PRODUCTOUTLINE
								+ Const.fClassificationID + "&orderkey=" + "price");
					} else {
						return httpClientUtils.getUrlContext(mContext, IPPort.URL_PRODUCTOUTLINESEARCH
								+ Const.fClassificationID + "&orderkey=" + "price" + "&searchBarText="
								+ searchString);
					}
				} else if (tag == 3) {
					if (searchString == null || searchString == "") {
						return httpClientUtils.getUrlContext(mContext, IPPort.URL_PRODUCTOUTLINE
								+ Const.fClassificationID + "&orderkey=" + "createTime");
					} else {
						return httpClientUtils.getUrlContext(mContext, IPPort.URL_PRODUCTOUTLINESEARCH
								+ Const.fClassificationID + "&orderkey=" + "createTime" + "&searchBarText="
								+ searchString);
					}
				}
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
					if (tag == 1) {
						list1.clear();
						list1 = mSaxParserContentHandler.parseReadXml(new ProductOutline(), result);
						productOutline = new ProductOutline();
						listView1.addFooterView(moreView1);
						list_ProductOutline1.clear();
						if (Const.pageItemCount < list1.size()) {
							for (int i = 0; i < Const.pageItemCount; i++) {
								productOutline = (ProductOutline) list1.get(i);
								list_ProductOutline1.add(productOutline);
								currentCount1 = Const.pageItemCount;
							}
							isLast1 = false;

						} else {
							for (int i = 0; i < list1.size(); i++) {
								productOutline = (ProductOutline) list1.get(i);
								list_ProductOutline1.add(productOutline);
								currentCount1 = list1.size();
							}
							isLast1 = true;
							listView1.removeFooterView(moreView1);
						}

						listView1.setAdapter(productListAdapter1);
					} else if (tag == 2) {
						list2.clear();
						list2 = mSaxParserContentHandler.parseReadXml(new ProductOutline(), result);
						productOutline = new ProductOutline();
						listView2.addFooterView(moreView2);
						list_ProductOutline2.clear();
						if (Const.pageItemCount < list2.size()) {
							for (int i = 0; i < Const.pageItemCount; i++) {
								productOutline = (ProductOutline) list2.get(i);
								list_ProductOutline2.add(productOutline);
								currentCount2 = Const.pageItemCount;
							}
							isLast2 = false;

						} else {
							for (int i = 0; i < list2.size(); i++) {
								productOutline = (ProductOutline) list2.get(i);
								list_ProductOutline2.add(productOutline);
								currentCount2 = list2.size();
							}
							isLast2 = true;
							listView2.removeFooterView(moreView2);
						}

						listView2.setAdapter(productListAdapter2);
					} else if (tag == 3) {
						list3.clear();
						list3 = mSaxParserContentHandler.parseReadXml(new ProductOutline(), result);
						productOutline = new ProductOutline();
						listView3.addFooterView(moreView3);
						list_ProductOutline3.clear();
						if (Const.pageItemCount < list3.size()) {
							for (int i = 0; i < Const.pageItemCount; i++) {
								productOutline = (ProductOutline) list3.get(i);
								list_ProductOutline3.add(productOutline);
								currentCount3 = Const.pageItemCount;
							}
							isLast3 = false;

						} else {
							for (int i = 0; i < list3.size(); i++) {
								productOutline = (ProductOutline) list3.get(i);
								list_ProductOutline3.add(productOutline);
								currentCount3 = list3.size();
							}
							isLast3 = true;
							listView3.removeFooterView(moreView3);
						}

						listView3.setAdapter(productListAdapter3);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
