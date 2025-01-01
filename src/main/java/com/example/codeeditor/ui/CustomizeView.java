package com.example.codeeditor.ui;

import com.example.codeeditor.App;
import com.example.codeeditor.sidebar.CustomizePanel;
import java.awt.*;
import javax.swing.*;


public class CustomizeView extends JPanel {

  public boolean isDarkTheme = false;

  public CustomizeView(App app, CustomizePanel customizePanel) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBackground(new Color(240, 240, 240));

    JLabel titleLabel = new JLabel("Customize Settings");
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    titleLabel.setForeground(new Color(255, 252, 252));
    add(Box.createVerticalStrut(20));
    add(titleLabel);

    JLabel themeLabel = new JLabel("Select Theme:");
    themeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
    themeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(Box.createVerticalStrut(20));
    add(themeLabel);

    changeTheme(app, customizePanel);

    JLabel fontLabel = new JLabel("Select Font Size:");
    fontLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
    fontLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(Box.createVerticalStrut(20));
    add(fontLabel);

    JSlider slider = new JSlider(12, 36, 18);
    slider.setMaximumSize(new Dimension(200, slider.getPreferredSize().height));
    slider.setAlignmentX(Component.LEFT_ALIGNMENT);
    slider.addChangeListener(e -> {
      if (!slider.getValueIsAdjusting()) {
        int fontSize = slider.getValue();
        app.setFontSize(fontSize);
      }
    });

    add(Box.createVerticalStrut(10));
    add(slider);
  }

  public void changeTheme(App app, CustomizePanel customizePanel) {

    String[] themes = {"Light Theme", "Dark Theme"};
    JComboBox<String> themeDropdown = new JComboBox<>(themes);
    themeDropdown.setMaximumSize(new Dimension(200, 30));
    themeDropdown.setBackground(Color.WHITE);
    themeDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
    themeDropdown.setForeground(new Color(30, 30, 30));

    themeDropdown.addActionListener(e -> {
      String selectedTheme = (String) themeDropdown.getSelectedItem();
      isDarkTheme = "Dark Theme".equals(selectedTheme);
      app.setDarkTheme(isDarkTheme);

      customizePanel.updateButtonColor(isDarkTheme);

      repaint();
    });

    add(Box.createVerticalStrut(10));
    add(themeDropdown);
  }
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;

    GradientPaint gradient;
    if (isDarkTheme) {
      gradient = new GradientPaint(0, 0, Color.WHITE, getWidth(), getHeight(), Color.BLACK);
    } else {
      gradient = new GradientPaint(0, 0, new Color(245, 228, 248), getWidth(), getHeight(), new Color(114, 2, 102));
    }

    g2D.setPaint(gradient);
    g2D.fillRect(0, 0, getWidth(), getHeight());
  }
}
