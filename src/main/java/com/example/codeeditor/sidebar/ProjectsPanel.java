package com.example.codeeditor.sidebar;

import com.example.codeeditor.App;
import com.example.codeeditor.ui.CustomizeView;
import com.example.codeeditor.utils.RoundedButton;
import javax.swing.*;
import java.awt.*;


public class ProjectsPanel extends JPanel {

  private RoundedButton projectsButton;
   private CustomizeView customizeView;

    public ProjectsPanel(App app) {
      CustomizePanel customizePanel = new CustomizePanel(app);
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        add(Box.createVerticalStrut(20));

        projectsButton = new RoundedButton("Projects", 15);
        projectsButton.setBackground(new Color(75, 0, 108));
        projectsButton.setForeground(Color.WHITE);
        projectsButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        projectsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        projectsButton.addActionListener(e -> {
            app.projectView.openProject();
            app.launch();
            if (!customizeView.isDarkTheme) {
              updateButtonColor(true);
            }
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
