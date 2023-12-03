package com.ganilabs.falconbolt.core.Model;

public interface ModelObserver {
    public void update(String changeMessage);
    public void update (String changeMessage , String helperData);
}
