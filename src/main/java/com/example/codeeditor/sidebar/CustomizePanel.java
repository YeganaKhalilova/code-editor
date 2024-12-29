package com.example.codeeditor.sidebar;

import com.example.codeeditor.App;
import com.example.codeeditor.utils.RoundedButton;
import java.awt.*;
import javax.swing.*;

public class CustomizePanel extends JPanel {

  private App app;

  public CustomizePanel(App app) {
    this.app = app;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setOpaque(false);

    add(Box.createVerticalStrut(20));

    RoundedButton customizeBtn = new RoundedButton("Customize", 15);
    customizeBtn.setBackground(new Color(75, 0, 108));
    customizeBtn.setForeground(Color.WHITE);
    customizeBtn.setFont(new Font("SansSerif", Font.PLAIN, 16));
    customizeBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
    customizeBtn.addActionListener(e -> {
      app.showCustomizationView();
    });

    add(customizeBtn);
  }
}
