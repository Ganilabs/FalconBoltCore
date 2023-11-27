package com.ganilabs.falconbolt.core.constant;

import java.util.HashSet;
import java.util.Set;

public class InterProcessMessages {
	private final Set<String> messages = new HashSet<>();
	public InterProcessMessages() {
		//adding messages directly as they will be constant
	}
	public Set<String> getMessages() {
		return this.messages;
	}
}
