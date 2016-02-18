import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;

import javax.swing.*;

/**
 * Created by maciek on 17/02/16.
 */
public class GenerateDialog extends DialogWrapper {

    private final LabeledComponent<JPanel> myComponent;

    public GenerateDialog() {
        super(true);
        setTitle("Select Fields for ComparisonChain");
        JPanel panel = new JPanel();
        myComponent = LabeledComponent.create(panel, "Fields to include in compareTo():");
        init();
    }

    @Override
    protected JComponent createCenterPanel() {
        return myComponent;
    }

}
