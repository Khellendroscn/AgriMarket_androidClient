package com.lyj.agriculture.xmlparse;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.lyj.agriculture.model.CustomerConfirm;
import com.lyj.agriculture.model.CustomerRequest;
import com.lyj.agriculture.util.LogUtil;

public class SAXParserContentHandler extends DefaultHandler {
	private Object javaBean = null;
	private String tag; // 标签名称
	List<Object> list = null;

	/**
	 * 用 SAX 解析xml文件
	 * 
	 * @param strUrl
	 *            网络xml地址
	 * @param javaBean
	 *            实体类对象
	 * @return List 返回实体类的一个list集合
	 * @throws Exception
	 */
	public List<Object> parseReadXml(Object javaBean, String xmlString) throws Exception {
		this.javaBean = javaBean;
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		// 取得SAXParser 实例
		SAXParser parser = parserFactory.newSAXParser();
		parser.parse(new InputSource(new StringReader(xmlString)), this);
		return list;
	}

	public Object parseReadXmlObject(Object javaBean, String xmlString) throws Exception {

		this.javaBean = javaBean;
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		// 取得SAXParser 实例
		SAXParser parser = parserFactory.newSAXParser();
		parser.parse(new InputSource(new StringReader(xmlString)), this);
		return this.javaBean;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (null != tag && !"".equals(tag)) {
			if (javaBean != null) {
				String data = new String(ch, start, length);
				Field[] fields = javaBean.getClass().getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					if (tag.equalsIgnoreCase(fields[i].getName())) {
						setAttribute(javaBean, fields[i].getName(), data, new Class[] { fields[i].getType() });
						break;
					}

				}

			}

		}

	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (null != localName && !"".equals(localName)) {
			String bean = updateSmall(javaBean.getClass().getSimpleName());

			if (bean.equalsIgnoreCase(localName) && bean != null) {
				list.add(javaBean);
			}
		}
		tag = null;
	}

	@Override
	public void startDocument() throws SAXException {

		// TODO Auto-generated method stub

		list = new ArrayList<Object>();

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		tag = qName;
		// 取得实体类简单名称，把首字母变为小 写
		String bean = updateSmall(javaBean.getClass().getSimpleName());
		// 取得实体类的 全限 名称
		String clazz = javaBean.getClass().getName();
		if (bean.equalsIgnoreCase(qName)) {
			try {
				javaBean = Class.forName(clazz).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Field[] fields = javaBean.getClass().getDeclaredFields();
			for (int i = 0; i < attributes.getLength(); i++) {
				for (int j = 0; j < fields.length; j++) {
					if (attributes.getQName(i).equalsIgnoreCase(fields[j].getName())) {
						setAttribute(javaBean, attributes.getQName(i), attributes.getValue(i),
								new Class[] { fields[j].getType() });
					}
				}
			}
		}
	}

	/**
	 * @param object
	 *            类
	 * @param setName
	 *            方法名
	 * @param setValue方法设置
	 * @param obj
	 *            属性类型
	 * @throws Exception
	 */
	public void setAttribute(Object object, String setName, String setValue, Class[] obj) {
		Method method;
		try {
			method = object.getClass().getMethod("set" + /* setName */updateStr(setName), obj[0]);

			if (obj[0].equals(Integer.class) || obj[0].equals(int.class)) {
				method.invoke(object, new Integer(setValue));
			}
			if (obj[0].equals(Float.class) || obj[0].equals(float.class)) {
				method.invoke(object, new Float(setValue));
			}
			if (obj[0].equals(Short.class) || obj[0].equals(short.class)) {
				method.invoke(object, new Short(setValue));
			}
			if (obj[0].equals(Byte.class) || obj[0].equals(byte.class)) {
				method.invoke(object, new Byte(setValue));
			}
			if (obj[0].equals(Double.class) || obj[0].equals(double.class)) {
				method.invoke(object, new Double(setValue));
			}
			if (obj[0].equals(Date.class)) {
				method.invoke(object, new Date(new Long(setValue)));
			}
			if (obj[0].equals(java.util.Date.class)) {
				method.invoke(object, new java.util.Date(setValue));
			}
			if (obj[0].equals(Long.class) || obj[0].equals(long.class)) {
				method.invoke(object, new Long(setValue));
			}
			if (obj[0].equals(Boolean.class) || obj[0].equals(boolean.class)) {
				method.invoke(object, new Boolean(setValue));
			}
			if (obj[0].equals(String.class)) {
				method.invoke(object, setValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 把字符串的首字母改为大写
	 * 
	 * @param str
	 * @return String
	 */
	public String updateStr(String str) {
		char c = (char) (str.charAt(0) - 32);
		String s = str.substring(1, str.length());
		return c + s;
	}

	/**
	 * 把字符串的首字母改为小写
	 * 
	 * @param str
	 * @return String
	 */

	public String updateSmall(String str) {
		char c = (char) (str.charAt(0) + 32);
		String s = str.substring(1, str.length());
		return c + s;
	}
}
