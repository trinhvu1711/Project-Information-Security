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
    private JButton buttonFromClipboard, buttonFromClipboardDecrypt;
    private JButton buttonFromFile, buttonFromFileDecrypt, buttonClose, buttonCalculate,buttonGenerateKey;
    private Map<JCheckBox, JTextField> checkboxToResultFieldMap = new HashMap<>();
    private Map<JCheckBox, JTextField> checkBoxResultDecryptMap = new HashMap<>();
    private JTextField inputField, decrypInputField;
    private JTextField keyField, decryptKeyField;
    private JComboBox<String> comboBox, comboBoxDecrypt;
    public View(){
        FlatLightLaf.setup();
        frame = new JFrame("Encrypt App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel tab1 = new JPanel(new MigLayout());
        JPanel tab2 = new JPanel(new MigLayout());
        JPanel tab3 = new JPanel(new MigLayout());
        JPanel tab4 = new JPanel(new MigLayout());
        tabbedPane.addTab("Encrypt", tab1);
        tabbedPane.addTab("Decrypt", tab2);
        tabbedPane.addTab("Digital Signature", tab3);
        tabbedPane.addTab("About", tab4);
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

//        start tab 1
        // Result panel 1
        JPanel resultPanel1 = new JPanel(new MigLayout("fillx", "[pref!][grow]"));
        Border border3 = BorderFactory.createTitledBorder("Symmetric Encryption");
        resultPanel1.setBorder(BorderFactory.createCompoundBorder(border3, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        String[] SymmetricEncryption = new String[] {"Ceasar", "Vigenere", "Twofish", "Serpent", "DES", "AES"};
        JCheckBox[] checkBoxAlgorithms1 = new JCheckBox[SymmetricEncryption.length];
        JTextField[] resultFields1 = new JTextField[SymmetricEncryption.length];

        for (int i = 0; i < SymmetricEncryption.length; i++) {
            checkBoxAlgorithms1[i] = new JCheckBox(SymmetricEncryption[i]);
            resultFields1[i] = new JTextField(90);
            checkboxToResultFieldMap.put(checkBoxAlgorithms1[i],resultFields1[i]);
        }

        for (int i = 0; i < SymmetricEncryption.length; i++) {
            resultPanel1.add(checkBoxAlgorithms1[i], "gapright 10");
            resultPanel1.add(resultFields1[i], "grow");
            if (i < SymmetricEncryption.length -1 ) {
                resultPanel1.add(resultFields1[i], "grow, wrap");
            }
        }

//      Result panel 2
        JPanel resultPanel2 = new JPanel(new MigLayout("fillx", "[pref!][grow]"));
        Border border4 = BorderFactory.createTitledBorder("Asymmetric Encryption");
        resultPanel2.setBorder(BorderFactory.createCompoundBorder(border4, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        String[] AsymmetricEncryption = new String[] {"RSA"};
        JCheckBox[] checkBoxAlgorithms2 = new JCheckBox[AsymmetricEncryption.length];
        JTextField[] resultFields2 = new JTextField[AsymmetricEncryption.length];

        for (int i = 0; i < AsymmetricEncryption.length; i++) {
            checkBoxAlgorithms2[i] = new JCheckBox(AsymmetricEncryption[i]);
            resultFields2[i] = new JTextField(90);
            checkboxToResultFieldMap.put(checkBoxAlgorithms2[i],resultFields2[i]);
        }

        for (int i = 0; i < AsymmetricEncryption.length; i++) {
            resultPanel2.add(checkBoxAlgorithms2[i], "gapright 10");
            resultPanel2.add(resultFields2[i], "grow");
            if (i < AsymmetricEncryption.length -1 ) {
                resultPanel2.add(resultFields2[i], "grow, wrap");
            }
        }

//           Result panel 3
        JPanel resultPanel3 = new JPanel(new MigLayout("fillx", "[pref!][grow]"));
        Border border5 = BorderFactory.createTitledBorder("Hash Function");
        resultPanel3.setBorder(BorderFactory.createCompoundBorder(border5, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        String[] HashFunction = new String[] {"MD5", "SHA256"};
        JCheckBox[] checkBoxAlgorithms3 = new JCheckBox[HashFunction.length];
        JTextField[] resultFields3 = new JTextField[HashFunction.length];

        for (int i = 0; i < HashFunction.length; i++) {
            checkBoxAlgorithms3[i] = new JCheckBox(HashFunction[i]);
            resultFields3[i] = new JTextField(90);
            checkboxToResultFieldMap.put(checkBoxAlgorithms3[i],resultFields3[i]);
        }

        for (int i = 0; i < HashFunction.length; i++) {
            resultPanel3.add(checkBoxAlgorithms3[i], "gapright 10");
            resultPanel3.add(resultFields3[i], "grow");
            if (i < HashFunction.length -1 ) {
                resultPanel3.add(resultFields3[i], "grow, wrap");
            }
        }

        int maxWidth = 70;

        for (Map.Entry<JCheckBox, JTextField> entry : checkboxToResultFieldMap.entrySet()) {
            JCheckBox checkBox = entry.getKey();
            checkBox.setMinimumSize(new Dimension(maxWidth, checkBox.getPreferredSize().height));
        }
//        done tab 1


        // Input value panel
        JPanel inputDecryptPanel = new JPanel(new MigLayout("fillx", "[grow][pref!][pref!]"));
        Border decryptBorder = BorderFactory.createTitledBorder("Enter text or choose file");
        inputDecryptPanel.setBorder(BorderFactory.createCompoundBorder(decryptBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        decrypInputField = new JTextField(15);
        buttonFromClipboardDecrypt = new JButton("From Clipboard");
        buttonFromFileDecrypt = new JButton("From File");

        inputDecryptPanel.add(decrypInputField, "grow, height 30:30:30");
        inputDecryptPanel.add(buttonFromClipboardDecrypt, "height 30:30:30");
        inputDecryptPanel.add(buttonFromFileDecrypt, "wrap, height 30:30:30");

        // Enter Key panel
        JPanel keyPanelDecrypt = new JPanel(new MigLayout("fillx", "[pref!][pref!][pref!][grow]"));
        Border keyDecryptBorder = BorderFactory.createTitledBorder("Enter key");
        keyPanelDecrypt.setBorder(BorderFactory.createCompoundBorder(keyDecryptBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        comboBoxDecrypt = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
        decryptKeyField = new JTextField(15);

        keyPanelDecrypt.add( new JLabel("Key Format"));
        keyPanelDecrypt.add(comboBoxDecrypt, "grow, gapright 10");
        keyPanelDecrypt.add(new JLabel("Key"));
        keyPanelDecrypt.add(decryptKeyField, "grow, height 30:30:30");

//        start tab 2
        JPanel resultPanel4 = new JPanel(new MigLayout("fillx", "[pref!][grow]"));
        Border border6 = BorderFactory.createTitledBorder("Symmetric Encryption");
        resultPanel4.setBorder(BorderFactory.createCompoundBorder(border6, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        String[] SymmetricDecryption = new String[] {"Ceasar", "Vigenere", "Twofish", "Serpent", "DES", "AES"};
        JCheckBox[] checkBoxAlgorithms4 = new JCheckBox[SymmetricDecryption.length];
        JTextField[] resultFields4 = new JTextField[SymmetricDecryption.length];

        for (int i = 0; i < SymmetricDecryption.length; i++) {
            checkBoxAlgorithms4[i] = new JCheckBox(SymmetricDecryption[i]);
            resultFields4[i] = new JTextField(90);
            checkBoxResultDecryptMap.put(checkBoxAlgorithms4[i],resultFields4[i]);
        }

        for (int i = 0; i < SymmetricDecryption.length; i++) {
            resultPanel4.add(checkBoxAlgorithms4[i], "gapright 10");
            resultPanel4.add(resultFields4[i], "grow");
            if (i < SymmetricDecryption.length -1 ) {
                resultPanel4.add(resultFields4[i], "grow, wrap");
            }
        }

//        done tab 2

//        start tab 3

//        done tab 3


        // Func panel
        JPanel funcPanel = new JPanel(new MigLayout("alignx right", "[pref!,pref!,pref!]"));
        buttonGenerateKey = new JButton("Generate Key");
        buttonCalculate = new JButton("Calculate");
        buttonClose = new JButton("Close");

        funcPanel.add(buttonCalculate);
        funcPanel.add(buttonGenerateKey);
        funcPanel.add(buttonClose);

        tab1.add(inputPanel, "width max(1200), wrap");
        tab1.add(keyPanel, "width max(1200), wrap");
        tab1.add(resultPanel1, "width max(1200), wrap");
        tab1.add(resultPanel2, "width max(1200), wrap");
        tab1.add(resultPanel3, "width max(1200), wrap");

        tab2.add(inputDecryptPanel, "width max(1200), wrap");
        tab2.add(keyPanelDecrypt, "width max(1200), wrap");
        tab2.add(resultPanel4, "width max(1200), wrap");

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

    public JButton getButtonFromClipboardDecrypt() {
        return buttonFromClipboardDecrypt;
    }

    public JButton getButtonFromFileDecrypt() {
        return buttonFromFileDecrypt;
    }

    public Map<JCheckBox, JTextField> getCheckBoxResultDecryptMap() {
        return checkBoxResultDecryptMap;
    }

    public JTextField getDecrypInputField() {
        return decrypInputField;
    }

    public JTextField getDecryptKeyField() {
        return decryptKeyField;
    }

    public JComboBox<String> getComboBoxDecrypt() {
        return comboBoxDecrypt;
    }
}
