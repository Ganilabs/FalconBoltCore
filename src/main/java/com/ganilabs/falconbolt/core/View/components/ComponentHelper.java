package com.ganilabs.falconbolt.core.View.components;

import com.ganilabs.falconbolt.core.View.View;

import javax.swing.*;
import java.awt.*;

public class ComponentHelper {
    public static ImageIcon resizeImageIconToSize(ImageIcon icon , Integer size){
        Image scaledNewImage = icon.getImage().getScaledInstance(size, -1, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledNewImage);
    }
}
