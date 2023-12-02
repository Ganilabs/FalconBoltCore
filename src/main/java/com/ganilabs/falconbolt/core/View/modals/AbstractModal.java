package com.ganilabs.falconbolt.core.View.modals;

import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import com.ganilabs.falconbolt.core.View.AbstractWorkView;

public abstract class AbstractModal extends JDialog{
	AbstractWorkView parentView;
	protected AbstractModal(AbstractWorkView parentView , String title) {
		super(SwingUtilities.windowForComponent(parentView) , title , Dialog.ModalityType.DOCUMENT_MODAL);
		this.parentView = parentView;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public abstract void init(); 

}
