package com.lyj.agriculture.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

public abstract class AsyncTaskWithDelayDlg<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

	// 300 ms
	private static final int PROGRESS_DIALOG_DISPLAY_DELAY_MILLS = 300;
	private ProgressDialog progressDialog;
	private static Handler handler;

	private Context context;
	private volatile boolean isFinished = false;

	public AsyncTaskWithDelayDlg(Context context) {
		this.context = context;
		if (handler == null) {
			handler = new Handler();
		}
	}

	@Override
	protected final void onPreExecute() {
		super.onPreExecute();
		onPreExecute2();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (!isFinished) {
					// 如果还没有完成，则显示等待对话框
					showLoading(context, "正在拼了命的加载...");
				}
			}
		}, PROGRESS_DIALOG_DISPLAY_DELAY_MILLS);
	}

	@Override
	protected final void onPostExecute(Result result) {
		super.onPostExecute(result);
		dismissLoading();
		onPostExecute2(result);
	}

	@Override
	protected final void onCancelled() {
		super.onCancelled();
		dismissLoading();
		onCancelled2();
	}

	@Override
	protected final Result doInBackground(Params... params) {
		Result result = doInBackground2(params);
		isFinished = true;// 标识doInBackground已经做完
		return result;
	}

	// --------------------------- 以下方法可有子类继承
	protected abstract Result doInBackground2(Params... params);

	protected void onPreExecute2() {
	}

	protected void onPostExecute2(Result result) {
	}

	protected void onCancelled2() {
	}
	
	
	public void showLoading ( Context context , String title ) {
		if ( progressDialog == null || !progressDialog.isShowing () ) {
			progressDialog = new ProgressDialog ( context );
			progressDialog.setMessage ( title );
			progressDialog.show ();
		}
	}

	/**
	 * 关闭等待框
	 */
	public void dismissLoading () {
		if ( null != progressDialog ) {
			progressDialog.dismiss ();
			progressDialog = null;
		}
	}
}