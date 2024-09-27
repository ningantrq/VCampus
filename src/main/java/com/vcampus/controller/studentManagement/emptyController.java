package com.vcampus.controller.studentManagement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class emptyController {
    @FXML
    public Button ok;

    @FXML
    void CloseThisStage (MouseEvent event) {
        ((Stage)ok.getScene().getWindow()).close();
    }
}
