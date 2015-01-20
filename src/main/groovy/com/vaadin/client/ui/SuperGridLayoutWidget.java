package com.vaadin.client.ui;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Element;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.LayoutManager;
import com.vaadin.client.ui.gridlayout.GridLayoutConnector;

public class SuperGridLayoutWidget extends VGridLayout {

    private GridLayoutConnector getConnector() {
        return (GridLayoutConnector) ConnectorMap.get(client).getConnector(this);
    }

    private boolean isUndefinedHeight() {
        return getConnector().isUndefinedHeight();
    }

    private boolean isUndefinedWidth() {
        return getConnector().isUndefinedWidth();
    }

    @Override
    void layoutCellsHorizontally() {
        LayoutManager layoutManager = LayoutManager.get(client);
        Element element = getElement();
        int x = layoutManager.getPaddingLeft(element);
        int paddingRight = layoutManager.getPaddingRight(element);
        int horizontalSpacing = getHorizontalSpacing();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Cell cell = cells[i][j];
                if (cell != null) {
                    int reservedMargin;
                    // Make room for layout padding for cells reaching the
                    // right edge of the layout
                    if (i + cell.colspan >= cells.length) {
                        reservedMargin = paddingRight;
                    } else {
                        reservedMargin = 0;
                    }
                    cell.layoutHorizontally(x, reservedMargin);
                }
            }
            // Fix for GridLayout leaves an empty space for invisible components #VAADIN-12655
            // hide zero width columns
            if (columnWidths[i] > 0) {
                x += columnWidths[i] + horizontalSpacing;
            }
        }

        if (isUndefinedWidth()) {
            int outerWidth = x - horizontalSpacing
                    + layoutManager.getPaddingRight(element)
                    + layoutManager.getBorderWidth(element);
            element.getStyle().setWidth(outerWidth, Style.Unit.PX);
            getConnector().getLayoutManager().reportOuterWidth(getConnector(),
                    outerWidth);
        }
    }

    @Override
    void layoutCellsVertically() {
        int verticalSpacing = getVerticalSpacing();
        LayoutManager layoutManager = LayoutManager.get(client);
        Element element = getElement();
        int paddingTop = layoutManager.getPaddingTop(element);
        int paddingBottom = layoutManager.getPaddingBottom(element);

        int y = paddingTop;
        for (int column = 0; column < cells.length; column++) {
            y = paddingTop + 1 - 1; // Ensure IE10 does not optimize this out by
            // adding something to evaluate on the RHS
            // #11303

            for (int row = 0; row < cells[column].length; row++) {
                Cell cell = cells[column][row];
                if (cell != null) {
                    int reservedMargin;
                    if (cell.rowspan + row >= cells[column].length) {
                        // Make room for layout padding for cells reaching the
                        // bottom of the layout
                        reservedMargin = paddingBottom;
                    } else {
                        reservedMargin = 0;
                    }

                    cell.layoutVertically(y, reservedMargin);
                }
                // Fix for GridLayout leaves an empty space for invisible components #VAADIN-12655
                // hide zero height rows
                if (rowHeights[row] > 0) {
                    y += rowHeights[row] + verticalSpacing;
                }
            }
        }

        if (isUndefinedHeight()) {
            int outerHeight = y - verticalSpacing
                    + layoutManager.getPaddingBottom(element)
                    + layoutManager.getBorderHeight(element);
            element.getStyle().setHeight(outerHeight, Style.Unit.PX);

            getConnector().getLayoutManager().reportOuterHeight(getConnector(),
                    outerHeight);
        }
    }
}
