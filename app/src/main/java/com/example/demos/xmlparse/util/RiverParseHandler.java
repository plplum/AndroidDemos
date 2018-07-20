package com.example.demos.xmlparse.util;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.example.demos.xmlparse.bean.River;

public class RiverParseHandler extends DefaultHandler{

	private static final String NODE_RIVER = "river";
	private static final String ATTR_NAME = "name";
	private static final String ATTR_LENGTH = "length";
	private static final String NODE_INTRODUCTION = "introduction";
	private static final String NODE_IMAGEURL = "imageurl";

	private boolean isRiver;
	private boolean isIntroduction;
	private boolean isImageUrl;

	private River river;
	private List<River> rivers = new ArrayList<River>();

	/** 导航到开始标签触发 **/
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) {

		String tagName = localName.length() != 0 ? localName : qName;
		tagName = tagName.toLowerCase().trim();
		// 如果读取的是river标签开始，则实例化River
		if (tagName.equals(NODE_RIVER)) {
			isRiver = true;
			river = new River();
			/** 导航到river开始节点后 **/
			river.setName(attributes.getValue(ATTR_NAME));
			river.setLength(Integer.parseInt(attributes.getValue(ATTR_LENGTH)));
		}
		// 然后读取其他节点
		if (isRiver) {
			if (tagName.equals(NODE_INTRODUCTION)) {
				isIntroduction = true;
			} else if (tagName.equals(NODE_IMAGEURL)) {
				isImageUrl = true;
			}
		}

	}

	/** 导航到结束标签触发 **/

	public void endElement(String uri, String localName, String qName) {
		String tagName = localName.length() != 0 ? localName : qName;
		tagName = tagName.toLowerCase().trim();
		// 如果读取的是river标签结束，则把River添加进集合中
		if (tagName.equals(NODE_RIVER)) {
			isRiver = true;
			rivers.add(river);
		}
		// 然后读取其他节点
		if (isRiver) {
			if (tagName.equals(NODE_INTRODUCTION)) {
				isIntroduction = false;
			} else if (tagName.equals(NODE_IMAGEURL)) {
				isImageUrl = false;
			}
		}
	}

	// 这里是读取到节点内容时候回调

	public void characters(char[] ch, int start, int length) {
		// 设置属性值
		if (isIntroduction) {
			river.setIntroduction(river.getIntroduction() == null ? "" : river
					.getIntroduction() + new String(ch, start, length));
		} else if (isImageUrl) {
			river.setImageurl(river.getImageurl() == null ? "" : river
					.getImageurl() + new String(ch, start, length));
		}

	}

	public List<River> getRivers() {
		return rivers;
	}
	
}
