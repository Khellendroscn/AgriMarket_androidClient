package com.lyj.agriculture.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class HttpClientUtils {
	private static final String DEBUG_TAG = "HttpClientUtils";
	private static final String CHARSET_UTF8 = "UTF-8";

	public String getUrlContext(Context context, String strUrl) {
		String responseStr = null;// 发送请求，得到响应
		DefaultHttpClient httpClient = null;
		HttpGet httpGet = null;
		try {
			strUrl = urlEncode(strUrl.trim(), CHARSET_UTF8);
			httpClient = getDefaultHttpClient(null);
			httpGet = new HttpGet(strUrl);
			responseStr = httpClient.execute(httpGet, strResponseHandler);
		} catch (Exception e) {
			Toast.makeText(context, "连接服务器异常...", Toast.LENGTH_SHORT).show();
			Log.e(DEBUG_TAG, e.getMessage());
		} finally {
			abortConnection(httpGet, httpClient);
		}
		return responseStr;
	}

	public boolean sendPOSTRequestboolean(String path, String xmlStr) {
		try {
			HttpClient httpclient = getDefaultHttpClient(null);
			HttpPost request = new HttpPost(path);
			StringEntity s = new StringEntity(xmlStr, HTTP.UTF_8);
			s.setContentType("text/xml");
			s.setContentEncoding("UTF-8");
			request.setEntity(s);
			HttpResponse response = httpclient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 204) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}
	
	public boolean sendPOSTNoRequestboolean(String path) {
		try {
			HttpClient httpclient = getDefaultHttpClient(null);
			HttpPost request = new HttpPost(path);
			StringEntity s = new StringEntity(HTTP.UTF_8);
			s.setContentType("text/xml");
			s.setContentEncoding("UTF-8");
			request.setEntity(s);
			HttpResponse response = httpclient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 204) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	public String sendPOSTRequesString(String path, String xmlStr) {
		try {
			HttpClient httpclient = getDefaultHttpClient(null);
			HttpPost request = new HttpPost(path);
			StringEntity s = new StringEntity(xmlStr, HTTP.UTF_8);
			s.setContentType("text/xml");
			s.setContentEncoding("UTF-8");
			request.setEntity(s);
			HttpResponse response = httpclient.execute(request);
			InputStream in = response.getEntity().getContent();
			return readString(in);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	protected String readString(InputStream in) throws Exception {
		byte[] data = new byte[1024];
		int length = 0;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		while ((length = in.read(data)) != -1) {
			bout.write(data, 0, length);
		}
		return new String(bout.toByteArray(), "UTF-8");

	}

	/**
	 * 转码http的网址，只对中文进行转码
	 * 
	 * @param str
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String urlEncode(String str, String charset) throws UnsupportedEncodingException {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
		Matcher m = p.matcher(str);
		StringBuffer b = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(b, URLEncoder.encode(m.group(0), charset));
		}
		m.appendTail(b);
		return b.toString();
	}

	/**
	 * 设置重连机制和异常自动恢复处理
	 */
	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		// 自定义的恢复策略
		public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
			// 设置恢复策略，在Http请求发生异常时候将自动重试3次
			if (executionCount >= 3) {
				// Do not retry if over max retry count
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// Retry if the server dropped connection on us
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// Do not retry on SSL handshake exception
				return false;
			}
			HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if (!idempotent) {
				// Retry if the request is considered idempotent
				return true;
			}
			return false;
		}
	};

	// 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，
	// 解决对连接的释放管理
	private static ResponseHandler<String> strResponseHandler = new ResponseHandler<String>() {
		// 自定义响应处理
		public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String charset = EntityUtils.getContentCharSet(entity) == null ? CHARSET_UTF8 : EntityUtils
						.getContentCharSet(entity);
				return new String(EntityUtils.toByteArray(entity), charset);
			} else {
				return null;
			}
		}
	};

	/**
	 * 获取DefaultHttpClient实例
	 * 
	 * @param charset
	 * @return
	 */
	private static DefaultHttpClient getDefaultHttpClient(final String charset) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
		// HttpVersion.HTTP_1_1);
		// 模拟浏览器(有些服务器只支持浏览器访问，这个可以模拟下~~~)
		// httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		// httpclient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,Boolean.FALSE);

		// 请求超时
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		// 读取超时
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
		httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				charset == null ? CHARSET_UTF8 : charset);
		httpclient.setHttpRequestRetryHandler(requestRetryHandler);
		return httpclient;
	}

	/**
	 * 释放HttpClient连接
	 * 
	 * @param hrb
	 * @param httpclient
	 */
	private static void abortConnection(final HttpRequestBase httpRequestBase, final HttpClient httpclient) {
		if (httpRequestBase != null) {
			httpRequestBase.abort();
		}
		if (httpclient != null) {
			httpclient.getConnectionManager().shutdown();
		}
	}
}
