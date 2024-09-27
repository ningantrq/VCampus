package com.vcampus.controller.ai;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.model.completion.CompletionResponse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.application.Platform;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.IOException;
import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.model.chat.ChatResponse;
import com.baidubce.qianfan.model.completion.CompletionResponse;
import com.baidubce.qianfan.model.chat.ChatResponse;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;


public class ai {

    @FXML
    private VBox chatArea;

    @FXML
    private TextField chatInput;

    @FXML
    private TextArea inputTextArea;
    @FXML
    private TextArea inputTextArea2;
    @FXML
    private TextArea inputTextArea3;
    @FXML
    private TextArea outputTextArea2;
    @FXML
    private TextArea outputTextArea3;

    @FXML
    private TextArea outputTextArea;



//    @FXML
//    private Button exitButton;

    @FXML
    private Button write;

    @FXML
    private RadioButton simpleStyle;
    @FXML
    private RadioButton businessStyle;
    @FXML
    private RadioButton academicStyle;
    @FXML
    private RadioButton informalStyle;

    // 注入基调的 RadioButton
    @FXML
    private RadioButton enthusiasticTone;
    @FXML
    private RadioButton friendlyTone;
    @FXML
    private RadioButton confidentTone;
    @FXML
    private RadioButton diplomaticTone;
    @FXML
    private ToggleGroup writingStyleGroup;

    @FXML
    private ToggleGroup toneGroup;

    @FXML
    private void initialize() {
        // 检查 ToggleGroup 是否为 null
        assert writingStyleGroup != null : "writingStyleGroup is null";
        assert toneGroup != null : "toneGroup is null";
    }

    Qianfan qianfan = new Qianfan("ALTAK8R9Ikz6pR9b9SeNlLLwal", "36c47ed15bc64f629a9cdf193eed30b0");

    @FXML
    private void handleSendMessage() {
        String message = chatInput.getText();
        if (message.trim().isEmpty()) {
            return;
        }
        Text userMessage = new Text("You: " + message);
        chatArea.getChildren().add(userMessage);
        // Clear the input field
        chatInput.clear();

        new Thread(() -> {
            try {
                // 调用Qianfan的chatCompletion方法
                ChatResponse response = qianfan.chatCompletion()
                        .model("ERNIE-4.0-8K") // 使用指定的预置模型
                        .addMessage("user", message) // 添加用户消息
                        .temperature(0.7) // 自定义超参数（可选）
                        .execute(); // 发起请求

                Platform.runLater(() -> {
                    // Display the model's response in the chat area
                    Text modelResponse = new Text("VCampus: " + response.getResult());
                    chatArea.getChildren().add(modelResponse);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    Text errorMessage = new Text("Error: Unable to get response from the model.");
                    chatArea.getChildren().add(errorMessage);
                });
            }
        }).start();

    }


    @FXML
    private void handleRewriteText() {
        // 获取输入的文本内容
        String message = inputTextArea3.getText();
        if (message.trim().isEmpty()) {
            return; // 如果输入为空，则返回
        }

        // 获取选中的写作风格和基调
        RadioButton selectedStyle = (RadioButton) writingStyleGroup.getSelectedToggle();
        RadioButton selectedTone = (RadioButton) toneGroup.getSelectedToggle();

        // 默认的风格和基调
        String style = "简单";
        String tone = "热情";

        // 检查是否有选中的风格和基调，并获取其文本
        if (selectedStyle != null) {
            style = selectedStyle.getText(); // 获取选中的风格文本
        }

        if (selectedTone != null) {
            tone = selectedTone.getText(); // 获取选中的基调文本
        }

        // 构建提示内容
        String fullPrompt = String.format("请对以下内容进行改写，写作风格为【%s】，基调为【%s】：\n%s", style, tone, message);

        // 创建新线程调用API
        new Thread(() -> {
            try {
                // 调用Qianfan API进行文本改写
                ChatResponse response = qianfan.chatCompletion()
                        .model("ERNIE-4.0-8K") // 使用指定的预置模型
                        .addMessage("user", fullPrompt) // 添加用户消息
                        .temperature(0.7) // 设置温度超参数
                        .execute(); // 发起请求

                // 更新UI，显示改写后的文本
                Platform.runLater(() -> {
                    outputTextArea3.setText(response.getResult());
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    Text errorMessage = new Text("Error: Unable to get response from the model.");
                    chatArea.getChildren().add(errorMessage);
                });
            }
        }).start();
    }


    @FXML
    private void handleContinueWriteText() {
        String message = inputTextArea2.getText();
        if (message.trim().isEmpty()) {
            return;
        }


        new Thread(() -> {
            try {
                String fullPrompt = "请根据以下内容用中文续写，并确保回答是中文：" + message;
                ChatResponse response = qianfan.chatCompletion()
                        .model("ERNIE-4.0-8K") // 使用指定的预置模型
                        .addMessage("user", fullPrompt) // 添加用户消息
                        .temperature(0.7) // 自定义超参数（可选）
                        .execute();
                System.out.println(response.getResult());


                Platform.runLater(() -> {
                    // Display the model's response in the chat area
                    outputTextArea2.setText(response.getResult());
                });
            }

            catch (Exception e) {
                e.printStackTrace();

            }
        }).start();
    }

//    @FXML
//    private void Exit() {
//        try {
//            ((Stage)exitButton.getScene().getWindow()).close();
//
//            // 创建FXML加载器
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/vcampus/view.MainMenu/MainMenu.fxml"));
//            Parent root = loader.load();
//
//            // 创建新窗口并设置场景
//            Stage stage = new Stage();
//            stage.setTitle("主菜单");
//            stage.setScene(new Scene(root));
//            stage.setResizable(false);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

