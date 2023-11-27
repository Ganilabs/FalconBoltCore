package com.ganilabs.falconbolt.core.Control;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.ganilabs.falconbolt.interfaces.plugin.MessageQueue;
import com.ganilabs.falconbolt.interfaces.plugin.PluginMessage;
import com.ganilabs.falconbolt.interfaces.plugin.PluginMessageListener;

@Component
public class MessageQueueImpl implements MessageQueue {
	private Set<PluginMessageListener> listeners = new HashSet<>();
	private String msgQueueName;
	public MessageQueueImpl() {
		this.msgQueueName = "";
	}
	public MessageQueueImpl(String name) {
		this.msgQueueName = name;
	}

	@Override
	public void addListener(PluginMessageListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void dispatchMessage(PluginMessage<?> msg) {
		for(PluginMessageListener listener : this.listeners) {
			listener.messageReceived(this.getName(), msg);
		}
	}

	@Override
	public String getName() {
		return this.msgQueueName;
	}

	@Override
	public void messageRecieved(PluginMessage<?> msg) {
		this.dispatchMessage(msg);

	}

}
