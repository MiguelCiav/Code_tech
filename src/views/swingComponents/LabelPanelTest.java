package views.swingComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import utils.ViewsStyles;
import views.listeners.GoToTestView;

public class LabelPanelTest extends LabelPanel{

    private JLabel textLabel;
    private JLabel arrowLabel;
    private String testName;

    public LabelPanelTest(String testName){

        constraints = new GridBagConstraints();
        this.testName = testName;

        setLayout(new GridBagLayout());
        setBackground(ViewsStyles.LIGHT_GRAY);
        setLabel();
        addLabel();

    }

    @Override
    protected void setLabel() {

        textLabel = new JLabel();

<<<<<<< HEAD:src/views/swingComponents/LabelPanelTest.java
        textLabel.setBackground(new Color(216,233,241));
        textLabel.setOpaque(true);
        textLabel.setFont(new Font("Futura", Font.PLAIN, 12));
        textLabel.setFocusable(false);
        textLabel.setText(testName);
=======
        testLabel.setBackground(ViewsStyles.LIGHT_GRAY);
        testLabel.setOpaque(true);
        testLabel.setFont(new Font("Futura", Font.PLAIN, 12));
        testLabel.setFocusable(false);
        testLabel.setText(testName);
>>>>>>> b747c1632adf908c7c8978b2e3017ea78b79bda8:src/views/swingComponents/LabelPanelWithIcon.java

        arrowLabel = new JLabel(new ImageIcon(getClass().getResource(new PathManager().setFileLink("../img/testListView/siguiente.png"))));

    }

    @Override protected void addLabel() {

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(16, 16, 16, 16);

        add(textLabel, constraints);

        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.EAST;

        add(arrowLabel, constraints);

        arrowLabel.addMouseListener(new GoToTestView());
        
    }
    
}
