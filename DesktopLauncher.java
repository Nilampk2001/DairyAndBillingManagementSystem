package com.example.dairy;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

// ✅ इथे import कर
import com.example.dairy.DairyApplication;

public class DesktopLauncher extends Application {

    @Override
    public void start(Stage stage) {
        // WebView तयार करा
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Spring Boot app load करा
        webEngine.load("http://localhost:8093/branch/list");

        // Scene सेट करा
        stage.setScene(new Scene(webView, 1200, 800));
        stage.setTitle("Dairy Management Desktop App");
        stage.show();
    }

    public static void main(String[] args) {
        // Spring Boot सुरू करा
        Thread springThread = new Thread(() -> {
            DairyApplication.main(args);
        });
        springThread.setDaemon(true); // background मध्ये चालेल
        springThread.start();

        // JavaFX सुरू करा
        launch(args);
    }
}
