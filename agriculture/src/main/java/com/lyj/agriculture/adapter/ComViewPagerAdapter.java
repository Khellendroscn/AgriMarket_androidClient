package com.lyj.agriculture.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * View pager 切换页面 使用参考 ViewPagerDemoActivity
 * 
 *
 * **/
public class ComViewPagerAdapter extends PagerAdapter {
	/**
	 * 适配器数据更新； PagerAdapter支持数据集的变化。
	 * 数据设置的改变必须发生在主线程必须停止向的呼叫notifyDataSetChanged（）
	 * 从派生的AdapterView适配器BaseAdapter的类似。 一个数据集的变化可能涉及到的网页被添加，删除，或改变位置
	 * 
	 * 特殊机制； ViewPager联营公司与关键对象，而不是直接点击每一页。 此键是用来跟踪和唯一标识一个给定页面的独立适配器的地位。
	 * 调用的PagerAdapter的方法startUpdate（ViewGroup的）表示的ViewPager内容是关于改变。
	 * 一个或多个调用到instantiateItem（ViewGroup的，INT）和/或destroyItem（ViewGroup的诠释，对象），
	 * 年底将更新由调用到finishUpdate（ViewGroup的）信号。
	 * 通过时间finishUpdate返回与返回由instantiateItem的主要对象相关联的意见，
	 * 应增加应被删除传递到destroyItem的钥匙这些方法和相关的意见传递给父ViewGroup的。
	 * 的的方法isViewFromObject（查看对象）标识是否给定的键对象相关联的页面视图。
	 * 
	 * 生命周期 调用顺序 startUpdate 开始更新 getCount 获取view的个数 instantiateItem 初始化当前view
	 * finshUpdate 完成更新 isViewFromObject getCount savaState 保存状态
	 * **/

	// 界面列表
	private List < View > views;
	Boolean display = false;

	public ComViewPagerAdapter( List < View > views ) {
		this.views = views;
	}

	@Override
	public void startUpdate ( View arg0 ) {
		if ( display ) {
		}

	}

	// 获得当前界面数
	@Override
	public int getCount () {
		if ( views != null ) {
			return views.size ();
		}
		return 0;
	}

	/**
	 * 从指定的position创建page
	 * 
	 * @param container
	 *            ViewPager容器
	 * @param position
	 *            The page position to be instantiated.
	 * @return 返回指定position的page，这里不需要是一个view，也可以是其他的视图容器.
	 */
	@Override
	public Object instantiateItem ( View arg0 , int arg1 ) {
		if ( display ) {
		}
		( ( ViewPager ) arg0 ).addView ( views.get ( arg1 ) , 0 );
		return views.get ( arg1 );
	}

	/** 检查关联的视图 */
	@Override
	public boolean isViewFromObject ( View arg0 , Object arg1 ) {
		if ( display ) {
		}
		return ( arg0 == arg1 );
	}

	@Override
	public Parcelable saveState () {
		if ( display ) {
		}
		return null;
	}

	@Override
	public void restoreState ( Parcelable arg0 , ClassLoader arg1 ) {
		if ( display ) {
		}
	}

	/**
	 * 销毁arg1位置的界面 销毁是 第一次：初始化01 第二次初始2 ，销毁1， 第三次 初始3销毁1；
	 **/
	@Override
	public void destroyItem ( View arg0 , int arg1 , Object arg2 ) {
		if ( display ) {
		}
		( ( ViewPager ) arg0 ).removeView ( views.get ( arg1 ) );
	}

	@Override
	public void finishUpdate ( View arg0 ) {
		if ( display ) {
		}
	}

}
