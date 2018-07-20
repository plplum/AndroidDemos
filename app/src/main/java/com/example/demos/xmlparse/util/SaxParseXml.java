package com.example.demos.xmlparse.util;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;

import com.example.demos.xmlparse.bean.River;

/**
 * 
 * 采用SAX解析时具体处理步骤是：
 * 
 * 1 创建SAXParserFactory对象
 * 
 * 2 根据SAXParserFactory.newSAXParser()方法返回一个SAXParser解析器
 * 
 * 3 根据SAXParser解析器获取事件源对象XMLReader
 * 
 * 4 实例化一个DefaultHandler对象
 * 
 * 5 连接事件源对象XMLReader到事件处理类DefaultHandler中
 * 
 * 6 调用XMLReader的parse方法从输入源中获取到的xml数据
 * 
 * 7 通过DefaultHandler返回我们需要的数据集合。
 * 
 * @author Administrator
 * 
 */
public class SaxParseXml {

	public static List<River> parse(Context context, String xmlPath) {
		List<River> rivers = null;
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			// 获取事件源
			XMLReader xmlReader = parser.getXMLReader();
			// 设置处理器
			RiverParseHandler handler = new RiverParseHandler();
			xmlReader.setContentHandler(handler);
			// 解析xml文档
			// xmlReader.parse(new InputSource(new URL(xmlPath).openStream()));
			xmlReader.parse(new InputSource(context.getAssets().open(xmlPath)));
			rivers = handler.getRivers();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rivers;
	}

}
