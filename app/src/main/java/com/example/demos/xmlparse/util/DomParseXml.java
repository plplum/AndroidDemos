package com.example.demos.xmlparse.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;

import com.example.demos.xmlparse.bean.River;

/**
 * 采用DOM解析时具体处理步骤是：
 * 
 * 1 利用DocumentBuilderFactory创建一个DocumentBuilderFactory实例 
 * 2 利用DocumentBuilderFactory创建DocumentBuilder
 * 3 加载XML文档（Document）, 
 * 4 获取文档的根结点(Element)， 
 * 5 获取根结点中所有子节点的列表（NodeList）， 
 * 6 使用再获取子节点列表中的需要读取的结点。
 * 
 * @author Administrator
 * 
 */
public class DomParseXml {

	private static final String NODE_RIVER = "river";
	private static final String ATTR_NAME = "name";
	private static final String ATTR_LENGTH = "length";
	private static final String NODE_INTRODUCTION = "introduction";
	private static final String NODE_IMAGEURL = "imageurl";

	/**
	 * 参数fileName：为xml文档路径
	 */
	public static List<River> getRiversFromXml(Context context, String fileName) {
		List<River> rivers = new ArrayList<River>();
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document document = null;
		InputStream inputStream = null;
		factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
			inputStream = context.getResources().getAssets().open(fileName);
			document = builder.parse(inputStream);
			Element root = document.getDocumentElement();
			NodeList nodes = root.getElementsByTagName(NODE_RIVER);
			River river = null;
			for (int i = 0; i < nodes.getLength(); i++) {
				river = new River();
				Element riverElement = (Element) (nodes.item(i));
				river.setName(riverElement.getAttribute(ATTR_NAME));
				river.setLength(Integer.parseInt(riverElement.getAttribute(ATTR_LENGTH)));
				Element introduction = (Element) riverElement.getElementsByTagName(NODE_INTRODUCTION).item(0);
				river.setIntroduction(introduction.getFirstChild().getNodeValue());
				Element imageUrl = (Element) riverElement.getElementsByTagName(NODE_IMAGEURL).item(0);
				river.setImageurl(imageUrl.getFirstChild().getNodeValue());
				rivers.add(river);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rivers;
	}
}
