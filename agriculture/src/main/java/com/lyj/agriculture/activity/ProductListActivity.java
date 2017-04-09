package com.lyj.agriculture.activity;

import android.content.Context;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.lyj.agriculture.R;
import com.lyj.agriculture.view.ProductListInfo;
import com.lyj.agriculture.view.TabView;


/**
 * 此处的分页加载可能不是最好的， 网络解析xml是所有数据都先下载下来， 只是图片进行分页加载，如果全要分页加载可能需要服务器端做处理
 * 
 * @author LiYajun
 * 
 */
public class ProductListActivity extends SherlockActivity {

	private Context mContext = this;
	private ProductListInfo pli;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("商品信息");

		pli = new ProductListInfo(mContext);
		TabView tabView = new TabView(mContext);
		tabView.addTabView("销量", pli.getCountList());
		tabView.addTabView("价格", pli.getPriceList());
		tabView.addTabView("最新", pli.getNewList());
		this.setContentView(tabView.getMainView());

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {

		SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
		searchView.setQueryHint("搜索商品");
		MenuItem searchItem = menu.add(0, 0, 0, "search");
		searchItem.setIcon(R.drawable.abs__ic_search);
		searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		searchItem.setActionView(searchView);
		searchView.setOnQueryTextListener(new ProductOnQueryTextListener());
		return super.onCreateOptionsMenu(menu);
	}

	
	
	class ProductOnQueryTextListener implements OnQueryTextListener{

		@Override
		public boolean onQueryTextSubmit(String query) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			pli.setSearchText(newText);
			return false;
		}
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}else if(item.getItemId() == 0) {
			
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}
