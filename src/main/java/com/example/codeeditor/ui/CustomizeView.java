package com.example.codeeditor.ui;

import com.example.codeeditor.App;
import java.awt.*;
import javax.swing.*;

public class CustomizeView extends JPanel {

  public CustomizeView(App app) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBackground(new Color(240, 240, 240));

    JLabel titleLabel = new JLabel("Customize Settings");
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    titleLabel.setForeground(new Color(50, 50, 50));
    add(Box.createVerticalStrut(20));
    add(titleLabel);

    JLabel themeLabel = new JLabel("Select Theme:");
    themeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
    themeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(Box.createVerticalStrut(10));
    add(themeLabel);

    JButton darkThemeButton = new JButton("Dark Theme");
    darkThemeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    darkThemeButton.addActionListener(e -> app.setDarkTheme(true));
    add(Box.createVerticalStrut(5));
    add(darkThemeButton);

    JButton lightThemeButton = new JButton("Light Theme");
    lightThemeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    lightThemeButton.addActionListener(e -> app.setDarkTheme(false));
    add(Box.createVerticalStrut(5));
    add(lightThemeButton);

    JLabel fontLabel = new JLabel("Select Font Size:");
    fontLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
    fontLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(Box.createVerticalStrut(20));
    add(fontLabel);

    JSlider fontSizeSlider = new JSlider(12, 36, 18);
    fontSizeSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
    fontSizeSlider.addChangeListener(e -> {
      int fontSize = fontSizeSlider.getValue();
      app.setFontSize(fontSize);
    });
    add(Box.createVerticalStrut(10));
    add(fontSizeSlider);
  }
}

