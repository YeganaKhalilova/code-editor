package com.kafka.example.codeeditor;

import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.kafka.example.codeeditor.ui.EditorView;
import com.kafka.example.codeeditor.ui.ProjectView;
import com.kafka.example.codeeditor.ui.OpeningView;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@Slf4j
public class App extends JFrame {

  public OpeningView openingView;
  public JSplitPane rootPanel;
  public ProjectView projectView;
  public EditorView editorView;

  public JPanel rightSplitPanel;
  public JPanel toolPanel;

  public JButton openTerminalButton;
  public JButton saveFileButton;

  public String os = System.getProperty("os.name").toLowerCase();
  public String currentFileParentPath;
  public ProcessBuilder pb;

  public JMenuBar menuBar;
  public JMenu settingsMenu, themeItem, colorSchemeItem, languageItem;
  public JMenuItem closeProjectItem, newProjectItem,
      darkThemeItem, lightThemeItem,
      monokaiItem, eclipseItem, nightItem, redItem, blueItem, purpleItem,
      javaItem, pythonItem, cItem, jsItem,
      autoSaveItem, projectViewItem,
      exitItem;

  public boolean autoSave = false;
  public boolean projectViewEnabled = true;
  public Timer autoSaveTimer;

  public boolean darkTheme = true;
  public Font editorFont;

  public App() {
    setSize(800, 500);
    setTitle("CodeLite");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setLocationRelativeTo(null);
  }

  public void init() {
    editorFont = new Font(FlatJetBrainsMonoFont.FAMILY, Font.PLAIN, 18);
    openingView = new OpeningView(this);
    projectView = new ProjectView(this);
    projectView.setMinimumSize(new Dimension(200, 0));
    projectView.init();
    projectView.initActionListeners();
    editorView = new EditorView(this);
    rightSplitPanel = new JPanel();

    toolPanel = new JPanel();
    rootPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, projectView, rightSplitPanel);

    rightSplitPanel.setLayout(new BorderLayout());
    toolPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    rightSplitPanel.add(editorView.getContentPanel(), BorderLayout.CENTER);
    rightSplitPanel.add(toolPanel, BorderLayout.NORTH);

    saveFileButton = new JButton("Save");
    saveFileButton.setEnabled(false);
    saveFileButton.setFont(new Font(FlatJetBrainsMonoFont.FAMILY, Font.PLAIN, 14));
    saveFileButton.setBackground(new Color(245, 228, 248));
    saveFileButton.addActionListener(e -> projectView.saveFile());

    currentFileParentPath = projectView.projectPath;

    openTerminalButton = new JButton("Open Terminal");
    openTerminalButton.setFont(new Font(FlatJetBrainsMonoFont.FAMILY, Font.PLAIN, 14));
    openTerminalButton.setBackground(new Color(30, 126, 248));
    openTerminalButton.addActionListener(e -> {
      try {
        if (os.contains("win")) {
          pb = new ProcessBuilder("cmd", "/c", "start", "powershell.exe");
        } else if (os.contains("mac")) {
          pb = new ProcessBuilder("open", "-a", "Terminal");
        } else if (os.contains("nix") || os.contains("nux") || os.contains("bsd")) {
          pb = new ProcessBuilder("x-terminal-emulator");
        } else
          JOptionPane.showMessageDialog(null, "Unsupported Operating System", "Error", JOptionPane.ERROR_MESSAGE);

        if (!projectView.getSelectedCustomNode().getParent().isLeaf()) {
          pb.directory(new File(projectView.projectPath));
        } else {
          pb.directory(new File(currentFileParentPath));
        }
        pb.start();

      } catch (IOException ex) {
        log.error("Failed to open terminal: " + ex.getMessage());
      } catch (UnsupportedOperationException ex) {
        log.error(ex.getMessage());
      } catch (NullPointerException ex) {
        JOptionPane.showMessageDialog(null, "Select a file to open the terminal", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    menuBar = new JMenuBar();
    settingsMenu = new JMenu("Settings", true);

    newProjectItem = new JMenuItem("Open new Project");
    closeProjectItem = new JMenuItem("Close project");

    themeItem = new JMenu("Theme");
    darkThemeItem = new JMenuItem("Dark");
    lightThemeItem = new JMenuItem("Light");

    colorSchemeItem = new JMenu("Color scheme");
    monokaiItem = new JMenuItem("Monokai");
    eclipseItem = new JMenuItem("Eclipse");
    nightItem = new JMenuItem("Night");
    redItem = new JMenuItem("Reversal Red");
    blueItem = new JMenuItem("Amplified Blue");
    purpleItem = new JMenuItem("Hollow Purple");

    languageItem = new JMenu("Language support");
    javaItem = new JMenuItem("Java");
    pythonItem = new JMenuItem("Python");
    cItem = new JMenuItem("C/C++");
    jsItem = new JMenuItem("Javascript");

    autoSaveItem = new JMenuItem("Auto save : Off");
    projectViewItem = new JMenuItem("Project view : Enabled");
    exitItem = new JMenuItem("Exit CodeLite");

    newProjectItem.addActionListener(e -> {
      projectView.getProjectTree().removeAll();
      projectView.root.removeAllChildren();
      projectView.openProject();
    });

    closeProjectItem.addActionListener(e -> {
      projectView.getProjectTree().removeAll();
      projectView.root.removeAllChildren();
      projectView.projectFiles.clear();

      setContentPane(openingView);
      this.setSize(800, 500);
      this.setLocationRelativeTo(null);
    });

    darkThemeItem.addActionListener(e -> setDarkTheme(true));
    lightThemeItem.addActionListener(e -> setDarkTheme(false));

    monokaiItem.addActionListener(e -> editorView.setColorScheme("Monokai"));
    eclipseItem.addActionListener(e -> editorView.setColorScheme("Eclipse"));
    nightItem.addActionListener(e -> editorView.setColorScheme("Night"));
    redItem.addActionListener(e -> editorView.setColorScheme("Red"));
    blueItem.addActionListener(e -> editorView.setColorScheme("Blue"));
    purpleItem.addActionListener(e -> editorView.setColorScheme("Purple"));
    javaItem.addActionListener(e -> editorView.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA));
    pythonItem.addActionListener(e -> editorView.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON));
    cItem.addActionListener(e -> editorView.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C));
    jsItem.addActionListener(e -> editorView.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT));

    autoSaveItem.addActionListener(e -> toggleAutoSave());
    projectViewItem.addActionListener(e -> toggleProjectView());
    exitItem.addActionListener(e -> System.exit(0));
  }

  public void setDarkTheme(boolean isDark) {
    try {
      darkTheme = isDark;
      if (isDark) {
        UIManager.setLookAndFeel(new FlatMacDarkLaf());
        openingView.openProjectButton.setBackground(new Color(255, 151, 238)); // Set your desired button color for dark theme
      } else {
        UIManager.setLookAndFeel(new FlatMacLightLaf());
        openingView.openProjectButton.setBackground(new Color(12, 182, 41)); // Set your desired button color for light theme
      }

      SwingUtilities.updateComponentTreeUI(this);
      editorView.setFont(editorFont);
      projectView.refreshTree();
    } catch (UnsupportedLookAndFeelException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void setFontSize(int size) {
    editorFont = new Font(FlatJetBrainsMonoFont.FAMILY, Font.PLAIN, size);
    editorView.setFont(editorFont);
  }

  public void toggleAutoSave() {
    if (autoSave) {
      autoSave = false;
      autoSaveItem.setText("Auto save : Off");
      saveFileButton.setVisible(true);
      autoSaveTimer.stop();
    } else {
      autoSave = true;
      autoSaveItem.setText("Auto save : On");
      saveFileButton.setVisible(false);
      autoSaveTimer.start();
    }
  }

  public void toggleProjectView() {
    if (projectViewEnabled) {
      projectViewEnabled = false;
      projectView.setVisible(false);
      rootPanel.setDividerLocation(0);
      projectViewItem.setText("Project view : Disabled");
    } else {
      projectViewEnabled = true;
      projectView.setVisible(true);
      rootPanel.setDividerLocation(200);
      projectViewItem.setText("Project view : Enabled");
      revalidate();
      repaint();
    }
  }

  public void addComponent() {
    projectView.addComponent();

    menuBar.add(settingsMenu);
    settingsMenu.add(newProjectItem);
    settingsMenu.add(closeProjectItem);

    settingsMenu.addSeparator();
    settingsMenu.add(themeItem);
    settingsMenu.add(colorSchemeItem);
    settingsMenu.addSeparator();
    settingsMenu.add(languageItem);
    settingsMenu.add(autoSaveItem);
    settingsMenu.add(projectViewItem);
    settingsMenu.addSeparator();

    themeItem.add(darkThemeItem);
    themeItem.add(lightThemeItem);

    colorSchemeItem.add(monokaiItem);
    colorSchemeItem.add(eclipseItem);
    colorSchemeItem.add(nightItem);
    colorSchemeItem.add(redItem);
    colorSchemeItem.add(blueItem);
    colorSchemeItem.add(purpleItem);

    languageItem.add(javaItem);
    languageItem.add(pythonItem);
    languageItem.add(cItem);
    languageItem.add(jsItem);

    settingsMenu.add(exitItem);

    toolPanel.add(openTerminalButton);
    toolPanel.add(saveFileButton);

    this.add(rootPanel, BorderLayout.CENTER);
    this.add(openingView);
    setJMenuBar(menuBar);

    revalidate();
    repaint();

    setVisible(true);
  }

  public void launch() {
    setContentPane(rootPanel);

    this.setExtendedState(MAXIMIZED_BOTH);
    editorView.setColorScheme("Monokai");
  }

  public static void main(String[] args) {
    FlatMacDarkLaf.setup();
    FlatJetBrainsMonoFont.install();
    FlatInterFont.install();

    UIManager.put("defaultFont", new Font(FlatInterFont.FAMILY, Font.PLAIN, 13));

    SwingUtilities.invokeLater(() -> {

      App app = new App();
      app.init();
      app.addComponent();
    });
  }


}
