package com.vcampus.controller.store;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class emptyController {
    public Button ok;
    public void CloseThisStage(MouseEvent mouseEvent) {
        ((Stage)ok.getScene().getWindow()).close();
    }
}
