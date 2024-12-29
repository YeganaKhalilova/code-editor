package com.kafka.example.codeeditor.sidebar;

import com.kafka.example.codeeditor.App;
import com.kafka.example.codeeditor.utils.RoundedButton;
import java.awt.*;
import javax.swing.*;

public class CustomizePanel extends JPanel {

  private App app;

  public CustomizePanel(App app) {
    this.app = app;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setOpaque(false);

    add(Box.createVerticalStrut(20));

    RoundedButton projectsButton = new RoundedButton("Customize", 15);
    projectsButton.setBackground(new Color(75, 0, 108));
    projectsButton.setForeground(Color.WHITE);
    projectsButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
    projectsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    projectsButton.addActionListener(e -> {
      app.projectView.openProject();
      app.launch();
    });

    add(projectsButton);
  }
}
