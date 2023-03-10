package settings;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

    private final JPanel myMainPanel;
    private final JBTextField myPathText = new JBTextField();

    public AppSettingsComponent() {
        myPathText.setEnabled(false);
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Currently not supported!",new JTextPane(), 1, true )
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return myPathText;
    }

    @NotNull
    public String getPath() {
        return myPathText.getText();
    }

    public void setPath(@NotNull String newText) {
        myPathText.setText(newText);
    }


}
