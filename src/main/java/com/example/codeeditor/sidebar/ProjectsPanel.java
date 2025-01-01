package com.example.codeeditor.sidebar;

import com.example.codeeditor.App;
import com.example.codeeditor.utils.RoundedButton;

import javax.swing.*;
import java.awt.*;

public class ProjectsPanel extends JPanel {

  private RoundedButton projectsButton;

    public ProjectsPanel(App app) {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        add(Box.createVerticalStrut(20));

        RoundedButton projectsButton = new RoundedButton("Projects", 15);
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

    public void updateButtonColor(boolean isDarkTheme) {
        if (isDarkTheme) {
            projectsButton.setBackground(Color.BLACK);
        } else {
            projectsButton.setBackground(new Color(75, 0, 108));
        }
    }
}
