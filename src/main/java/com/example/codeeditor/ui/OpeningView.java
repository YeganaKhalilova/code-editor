package com.example.codeeditor.ui;

import com.example.codeeditor.App;
import com.example.codeeditor.sidebar.CustomizePanel;
import com.example.codeeditor.sidebar.ProjectsPanel;
import com.example.codeeditor.utils.RoundedButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class OpeningView extends JPanel implements ComponentListener {

  private final App app;
  private final JPanel sidebarPanel;
  private final JPanel contentPanel;

  private final JLabel appNameLabel;
  private final JLabel titleLabel;
  private final JLabel mottoLabel;
  public JButton openProjectButton;

  public OpeningView(App app) {
    this.app = app;
    this.setLayout(new BorderLayout());
    this.setOpaque(false);

    sidebarPanel = new JPanel();
    sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
    sidebarPanel.setPreferredSize(new Dimension(200, 0));
    sidebarPanel.setBackground(new Color(255, 254, 254));
    sidebarPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

    JPanel sidebarContainer = new JPanel();
    sidebarContainer.setLayout(new BoxLayout(sidebarContainer, BoxLayout.Y_AXIS));
    sidebarContainer.setOpaque(false);

    sidebarContainer.add(Box.createVerticalStrut(20));

    appNameLabel = new JLabel("Codie");
    appNameLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    appNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    appNameLabel.setForeground(new Color(50, 50, 50));

    sidebarContainer.add(appNameLabel);
    sidebarContainer.add(Box.createVerticalStrut(20));

    ProjectsPanel projectsPanel = new ProjectsPanel(app);
    sidebarContainer.add(projectsPanel);

    CustomizePanel customizePanel = new CustomizePanel(app);
    sidebarContainer.add(customizePanel);

    sidebarPanel.add(sidebarContainer);

    contentPanel = new JPanel();
    contentPanel.setLayout(null);
    contentPanel.setOpaque(false);

    titleLabel = new JLabel("");
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 52));
    titleLabel.setForeground(new Color(50, 50, 50));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

    mottoLabel = new JLabel("Edit your code with ease");
    mottoLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
    mottoLabel.setForeground(new Color(0, 0, 0));
    mottoLabel.setHorizontalAlignment(SwingConstants.CENTER);

    openProjectButton = new RoundedButton("Open Project", 15);
    openProjectButton.setBackground(new Color(75, 0, 108));
    openProjectButton.setForeground(Color.WHITE);
    openProjectButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
    openProjectButton.addActionListener(e -> {
    app.projectView.openProject();
    app.launch();
    });

    contentPanel.add(titleLabel);
    contentPanel.add(mottoLabel);
    contentPanel.add(openProjectButton);

    this.add(sidebarPanel, BorderLayout.WEST);
    this.add(contentPanel, BorderLayout.CENTER);

    this.addComponentListener(this);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;

    GradientPaint gradient = new GradientPaint(0, 0, new Color(245, 228, 248), getWidth(), getHeight(), new Color(114, 2, 102));
    g2D.setPaint(gradient);
    g2D.fillRect(0, 0, getWidth(), getHeight());
  }

  @Override
  public void componentResized(ComponentEvent e) {
    if (titleLabel != null && mottoLabel != null && openProjectButton != null) {
      int contentWidth = getWidth() - 200;
      titleLabel.setBounds(contentWidth / 2 - 200, getHeight() / 2 - 150, 400, 50);
      mottoLabel.setBounds(contentWidth / 2 - 200, titleLabel.getY() + 60, 400, 30);
      openProjectButton.setBounds(contentWidth / 2 - 100, mottoLabel.getY() + 50, 200, 40);
    }
  }

  @Override
  public void componentMoved(ComponentEvent e) {

  }

  @Override
  public void componentShown(ComponentEvent e) {

  }

  @Override
  public void componentHidden(ComponentEvent e) {

  }
}
