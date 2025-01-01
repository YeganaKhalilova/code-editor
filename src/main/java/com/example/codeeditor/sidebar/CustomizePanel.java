package com.example.codeeditor.sidebar;

import com.example.codeeditor.App;
import com.example.codeeditor.ui.CustomizeView;
import com.example.codeeditor.utils.RoundedButton;
import java.awt.*;
import javax.swing.*;


public class CustomizePanel extends JPanel {

  private RoundedButton customizeBtn;

  public CustomizePanel(App app) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setOpaque(false);

    add(Box.createVerticalStrut(20));

    customizeBtn = new RoundedButton("Customize", 15);
    customizeBtn.setBackground(new Color(75, 0, 108));
    customizeBtn.setForeground(Color.WHITE);
    customizeBtn.setFont(new Font("SansSerif", Font.PLAIN, 16));
    customizeBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
    customizeBtn.addActionListener(e -> {
      CustomizeView customizeView = new CustomizeView(app, this);
      app.setContentPanel(customizeView);
    });

    add(customizeBtn);
  }

  public void updateButtonColor(boolean isDarkTheme) {
    if (isDarkTheme) {
      customizeBtn.setBackground(Color.BLACK);
    } else {
      customizeBtn.setBackground(new Color(75, 0, 108));
    }
  }
}
