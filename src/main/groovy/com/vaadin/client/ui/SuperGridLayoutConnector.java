package com.vaadin.client.ui;

import com.vaadin.ui.SuperGridLayout;
import com.vaadin.client.ui.gridlayout.GridLayoutConnector;
import com.vaadin.shared.ui.Connect;

@Connect(SuperGridLayout.class)
public class SuperGridLayoutConnector extends GridLayoutConnector {

    @Override
    public SuperGridLayoutWidget getWidget() {
        return (SuperGridLayoutWidget) super.getWidget();
    }
}
