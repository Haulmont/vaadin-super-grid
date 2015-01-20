/*
 * Copyright 2013 Haulmont Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.haulmont.vaadin.sample

import com.vaadin.server.VaadinRequest
import com.vaadin.shared.ui.MarginInfo
import com.vaadin.ui.*

public class GridExampleUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        def layout = new VerticalLayout()

        def container = new Panel(width: "400px", height: "-1px")

        def grid = new SuperGridLayout(rows: 4, columns: 2, width: "100%", height: "-1px",
                spacing: true, margin: new MarginInfo(true, true, true, true))

        grid.with {
            addComponent(new Label(value: 'Field 1', width: "-1px"), 0, 0)
            addComponent(new Label(value: 'Field 2', width: "-1px"), 0, 1)
            addComponent(new Label(value: 'Field 3', width: "-1px"), 0, 2)
            addComponent(new Label(value: 'Field 4', width: "-1px"), 0, 3)

            addComponent(new ComboBox(required: true, width: "100%"), 1, 0)
            addComponent(new TextField(width: "100%"), 1, 1)
            addComponent(new ComboBox(required: true, width: "100%"), 1, 2)
            addComponent(new TextField(required: true, width: "100%"), 1, 3)
        }

        grid.setColumnExpandRatio(0, 0)
        grid.setColumnExpandRatio(1, 1f)

        container.setContent(grid)

        layout.addComponent(container)

        def buttons = new HorizontalLayout(spacing: true)
        def showCell = { int x, int y ->
            grid.getComponent(x, y).visible = true
        }

        buttons.addComponent(new Button('Show', new Button.ClickListener() {
            @Override
            void buttonClick(Button.ClickEvent event) {
                showCell(0, 1)
                showCell(1, 1)
                showCell(0, 2)
                showCell(1, 2)
            }
        }))

        def hideCell = { int x, int y ->
            grid.getComponent(x, y).visible = false
        }
        buttons.addComponent(new Button('Hide', new Button.ClickListener() {
            @Override
            void buttonClick(Button.ClickEvent event) {
                hideCell(0, 1)
                hideCell(1, 1)
                hideCell(0, 2)
                hideCell(1, 2)
            }
        }))

        layout.addComponent(buttons)

        setContent(layout)
    }
}
