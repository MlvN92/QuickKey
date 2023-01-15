package impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class KeySearch extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        //load properties from config file
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("/config.properties"));
        } catch (IOException x) {
            x.printStackTrace();
        }

        //Show user input an safe it to a local String variable
        String bankSearchInput = JOptionPane.showInputDialog(
                null,
                "Bank/ASPSP search",
                "QuickKey",
                JOptionPane.QUESTION_MESSAGE);

        //initialise BufferedReader with given File path
        StringBuilder lines = new StringBuilder();
        String line;

        File rootDirectory = new File(System.getProperty("user.home") + prop.getProperty("ROOT_PATH"));

        if (rootDirectory.isDirectory()) {
            FileReader fileReader;
            for (File file : Objects.requireNonNull(rootDirectory.listFiles((dir, name) -> name.endsWith("md")))) {
                try {
                    fileReader = new FileReader(file.getPath());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        if (StringUtils.startsWithIgnoreCase(line, "##")
                                && StringUtils.containsIgnoreCase(line, bankSearchInput)) {
                            if (lines.length() == 0) {
                                lines.append(System.lineSeparator());
                            }
                            lines.append(line);
                            while ((line = bufferedReader.readLine()) != null) {
                                if (line.startsWith("##") && !StringUtils.containsIgnoreCase(line, bankSearchInput)) {
                                    break;
                                }
                                lines.append(line);
                                lines.append(System.lineSeparator());
                            }
                        }

                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        if (lines.length() == 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "No entry with: " + bankSearchInput + " found!",
                    "Result of your search",
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon(("META-INF/pluginIcon.png")));
        }
        //shows result in a TextArea
        else {
            JTextArea text = new JTextArea(lines.toString());
            text.setEditable(false);
            JOptionPane.showMessageDialog(
                    null,
                    text,
                    "Result of your search",
                    JOptionPane.INFORMATION_MESSAGE,
                    new ImageIcon(("META-INF/pluginIcon.png")));
        }
    }
}
