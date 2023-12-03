package com.ganilabs.falconbolt.core.View;

public class ViewMessage {
	private String msgType;
	private Object msgData;
	public ViewMessage(String msgType , Object msg) {
		this.msgData = msg;
		this.msgType = msgType;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public Object getMsgData() {
		return msgData;
	}
	public void setMsgData(Object msgData) {
		this.msgData = msgData;
	}
	
}
