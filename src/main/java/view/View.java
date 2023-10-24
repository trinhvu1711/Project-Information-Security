package view;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class View {
    private JFrame frame;
    private JButton buttonFromClipboard;
    private JButton buttonFromFile, buttonClose, buttonCalculate,buttonGenerateKey;
    private Map<JCheckBox, JTextField> checkboxToResultFieldMap = new HashMap<>();
//    private JCheckBox checkBoxAlgorithm1;
//    private JCheckBox checkBoxAlgorithm2;
//    private JCheckBox checkBoxAlgorithm3;
//    private JTextField resultField1;
//    private JTextField resultField2;
//    private JTextField resultField3;
    private JTextField inputField;
    private JTextField keyField;
    private JComboBox<String> comboBox;
    public View(){
        FlatLightLaf.setup();
        frame = new JFrame("Encrypt App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel tab1 = new JPanel(new MigLayout());
        JPanel tab2 = new JPanel(new MigLayout());
        JPanel tab3 = new JPanel(new MigLayout());
        tabbedPane.addTab("Encrypt", tab1);
        tabbedPane.addTab("Decrypt", tab2);
        tabbedPane.addTab("About", tab3);

        // Input value panel
        JPanel inputPanel = new JPanel(new MigLayout("fillx", "[grow][pref!][pref!]"));
        Border border = BorderFactory.createTitledBorder("Enter text or choose file");
        inputPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        inputField = new JTextField(15);
        buttonFromClipboard = new JButton("From Clipboard");
        buttonFromFile = new JButton("From File");

        inputPanel.add(inputField, "grow, height 30:30:30");
        inputPanel.add(buttonFromClipboard, "height 30:30:30");
        inputPanel.add(buttonFromFile, "wrap, height 30:30:30");

        // Enter Key panel
        JPanel keyPanel = new JPanel(new MigLayout("fillx", "[pref!][pref!][pref!][grow]"));
        Border border2 = BorderFactory.createTitledBorder("Enter key");
        keyPanel.setBorder(BorderFactory.createCompoundBorder(border2, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel label1 = new JLabel("Key Format");
        comboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
        JLabel label2 = new JLabel("Key");
        keyField = new JTextField(15);

        keyPanel.add(label1);
        keyPanel.add(comboBox, "grow, gapright 10");
        keyPanel.add(label2);
        keyPanel.add(keyField, "grow, height 30:30:30");

        // Result panel
        JPanel resultPanel = new JPanel(new MigLayout("fillx", "[pref!][grow]"));
        Border border3 = BorderFactory.createTitledBorder("Select Algorithm");
        resultPanel.setBorder(BorderFactory.createCompoundBorder(border3, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        String[] listAlgorithm = new String[] {"Ceasar", "Affine", "Vigenere"};
        JCheckBox[] checkBoxAlgorithms = new JCheckBox[listAlgorithm.length];
        JTextField[] resultFields = new JTextField[listAlgorithm.length];

        for (int i = 0; i < listAlgorithm.length; i++) {
            checkBoxAlgorithms[i] = new JCheckBox(listAlgorithm[i]);
            resultFields[i] = new JTextField(90);
            checkboxToResultFieldMap.put(checkBoxAlgorithms[i],resultFields[i]);

        }

        for (int i = 0; i < 3; i++) {
            resultPanel.add(checkBoxAlgorithms[i], "gapright 10");
            resultPanel.add(resultFields[i], "grow");
            if (i < 2) {
                resultPanel.add(resultFields[i], "grow, wrap");
            }
        }

        // Func panel
        JPanel funcPanel = new JPanel(new MigLayout("alignx right", "[pref!,pref!,pref!]"));
        buttonGenerateKey = new JButton("Generate Key");
        buttonCalculate = new JButton("Calculate");
        buttonClose = new JButton("Close");

        funcPanel.add(buttonGenerateKey);
        funcPanel.add(buttonCalculate);
        funcPanel.add(buttonClose);

        tab1.add(inputPanel, "width max(1200), wrap");
        tab1.add(keyPanel, "width max(1200), wrap");
        tab1.add(resultPanel, "width max(1200), wrap");

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(funcPanel, BorderLayout.SOUTH);

        frame.setSize(1200, 900);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public JButton getButtonFromClipboard() {
        return buttonFromClipboard;
    }

    public JButton getButtonFromFile() {
        return buttonFromFile;
    }

    public Map<JCheckBox, JTextField> getCheckboxToResultFieldMap() {
        return checkboxToResultFieldMap;
    }

    public void setCheckboxToResultFieldMap(Map<JCheckBox, JTextField> checkboxToResultFieldMap) {
        this.checkboxToResultFieldMap = checkboxToResultFieldMap;
    }

    public JTextField getKeyField() {
        return keyField;
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public JTextField getInputField() {
        return inputField;
    }

    public void setInputField(JTextField inputField) {
        this.inputField = inputField;
    }

    public JButton getButtonClose() {
        return buttonClose;
    }

    public JButton getButtonCalculate() {
        return buttonCalculate;
    }

    public JButton getButtonGenerateKey() {
        return buttonGenerateKey;
    }
}
