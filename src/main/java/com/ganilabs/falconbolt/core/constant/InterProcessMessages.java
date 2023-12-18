package com.ganilabs.falconbolt.core.constant;

import org.apache.derby.impl.store.access.sort.MergeScanRowSource;

import java.util.HashSet;
import java.util.Set;

public class InterProcessMessages {
	private final Set<String> messages = new HashSet<>();
	public InterProcessMessages() {
		//adding messages directly as they will be constant
		messages.add("RESULT_GENERATED");
		messages.add("OPEN_RESULT");
	}
	public Set<String> getMessages() {
		return this.messages;
	}
}
