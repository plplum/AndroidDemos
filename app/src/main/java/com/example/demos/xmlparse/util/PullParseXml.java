package com.example.demos.xmlparse.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

import com.example.demos.xmlparse.bean.River;

/**
 * 采用PULL解析基本处理方式：
 * 
 * 当PULL解析器导航到文档开始标签时就开始实例化list集合用来存贮数据对象。导航到元素开始标签时回判断元素标签类型，如果是river标签，
 * 则需要实例化River对象了，
 * 
 * 如果是其他类型，则取得该标签内容并赋予River对象。当然它也会导航到文本标签，不过在这里，我们可以不用。
 * 
 * 根据以上的解释，我们可以得出以下处理xml文档逻辑：
 * 
 * 1：当导航到XmlPullParser.START_DOCUMENT，可以不做处理，当然你可以实例化集合对象等等。
 * 
 * 2：当导航到XmlPullParser.START_TAG，则判断是否是river标签，如果是，则实例化river对象，
 * 并调用getAttributeValue方法获取标签中属性值。
 * 
 * 3：当导航到其他标签，比如Introduction时候，则判断river对象是否为空，如不为空，则取出Introduction中的内容，
 * nextText方法来获取文本节点内容
 * 
 * 4：当然啦，它一定会导航到XmlPullParser.END_TAG的，有开始就要有结束嘛。在这里我们就需要判读是否是river结束标签，如果是，
 * 则把river对象存进list集合中了，并设置river对象为null.
 * 
 * @author Administrator
 * 
 */
public class PullParseXml {

	private static final String NODE_RIVER = "river";
	private static final String ATTR_NAME = "name";
	private static final String ATTR_LENGTH = "length";
	private static final String NODE_INTRODUCTION = "introduction";
	private static final String NODE_IMAGEURL = "imageurl";

	public static List<River> parse(Context context, String xmlPath) {
		List<River> rivers = new ArrayList<River>();
		River river = null;
		InputStream inputStream = null;
		// 获得XmlPullParser解析器
		XmlPullParser xmlParser = Xml.newPullParser();
		try {
			// 得到文件流，并设置编码方式
			inputStream = context.getResources().getAssets().open(xmlPath);
			xmlParser.setInput(inputStream, "utf-8");
			// 获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
			int evtType = xmlParser.getEventType();
			// 一直循环，直到文档结束
			while (evtType != XmlPullParser.END_DOCUMENT) {
				switch (evtType) {
				case XmlPullParser.START_TAG:
					String tag = xmlParser.getName();
					// 如果是river标签开始，则说明需要实例化对象了
					if (tag.equalsIgnoreCase(NODE_RIVER)) {
						river = new River();
						// 取出river标签中的一些属性值
						river.setName(xmlParser.getAttributeValue(null, ATTR_NAME));
						river.setLength(Integer.parseInt(xmlParser.getAttributeValue(null, ATTR_LENGTH)));
					} else if (river != null) {
						// 如果遇到introduction标签，则读取它内容
						if (tag.equalsIgnoreCase(NODE_INTRODUCTION)) {
							String attrDate = xmlParser.getAttributeValue(null, "date");
							System.out.println("attrDate = " + attrDate);
							river.setIntroduction(xmlParser.nextText());
						} else if (tag.equalsIgnoreCase(NODE_IMAGEURL)) {
							river.setImageurl(xmlParser.nextText());
						} else if (tag.equalsIgnoreCase("test_node")) {
							System.out.println("test_node = " + xmlParser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					// 如果遇到river标签结束，则把river对象添加进集合中
					if (xmlParser.getName().equalsIgnoreCase(NODE_RIVER)
							&& river != null) {
						rivers.add(river);
						river = null;
					}
					break;
				default:
					break;
				}
				// 如果xml没有结束，则导航到下一个river节点
				evtType = xmlParser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return rivers;
	}
}
