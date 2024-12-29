package com.example.codeeditor.ui;

import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.example.codeeditor.App;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CustomizeView extends JPanel {

  private final App app;
  private JTree themeTree;
  private JScrollPane themeScrollPane;
  private JPopupMenu popupMenu;

  public CustomizeView(App app) {
    this.app = app;
    this.setPreferredSize(new Dimension(300, 1200));
    setLayout(new BorderLayout());
    init();
  }

  public void init() {
    themeTree = new JTree(new CustomNode("Customization", null, ""));
    themeTree.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 16));
    themeScrollPane = new JScrollPane(themeTree);
    createPopupMenu();
    addComponent();
  }

  public void initActionListeners() {
    themeTree.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        try {
          CustomNode node = (CustomNode) themeTree.getLastSelectedPathComponent();
          if (node != null) {
            if ("Dark Theme".equals(node.getNodeName())) {
              app.setDarkTheme(true);
            } else if ("Light Theme".equals(node.getNodeName())) {
              app.setDarkTheme(false);
            } else if (node.getNodeName().contains("Font Size")) {
              String fontSizeStr = JOptionPane.showInputDialog(null, "Enter font size:");
              try {
                int fontSize = Integer.parseInt(fontSizeStr);
                app.setFontSize(fontSize);
              } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid font size.", "Error", JOptionPane.ERROR_MESSAGE);
              }
            }
          }
        } catch (NullPointerException pointerException) {
          System.out.println("No selection.");
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
          int row = themeTree.getClosestRowForLocation(e.getX(), e.getY());
          themeTree.setSelectionRow(row);
          popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
      }
    });
  }

  public void addComponent() {
    this.add(themeScrollPane, BorderLayout.CENTER);
  }

  private void createPopupMenu() {
    popupMenu = new JPopupMenu();

    JMenuItem darkThemeItem = new JMenuItem("Dark Theme");
    JMenuItem lightThemeItem = new JMenuItem("Light Theme");
    JMenuItem fontSizeItem = new JMenuItem("Font Size");

    darkThemeItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        app.setDarkTheme(true);
      }
    });

    lightThemeItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        app.setDarkTheme(false);
      }
    });

    fontSizeItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String fontSizeStr = JOptionPane.showInputDialog(null, "Enter font size:");
        try {
          int fontSize = Integer.parseInt(fontSizeStr);
          app.setFontSize(fontSize);
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(null, "Invalid font size.", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    popupMenu.add(darkThemeItem);
    popupMenu.add(lightThemeItem);
    popupMenu.add(fontSizeItem);
  }

  public void refreshTree() {
    DefaultTreeModel model = (DefaultTreeModel) themeTree.getModel();
    model.reload();
  }

  public JTree getThemeTree() {
    return themeTree;
  }

  public CustomNode getSelectedCustomNode() {
    return (CustomNode) themeTree.getLastSelectedPathComponent();
  }
}
