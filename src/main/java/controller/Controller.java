package controller;

import models.*;
import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {
    private final View view;
    Serpent serpent;
    RSA rsa;
    DESEncryption des;
    AESEncryption aes;
    Twofish twofish;
    boolean isFileEncrypt = false;
    boolean isFileDecrypt = false;
    String lastSelectedDirectory = "";
    String selectedFilePath = "";
    String outputPath = "";
    String selectedFileDecryptPath = "";
    String outputDecryptPath = "";
    String fileName, fileNameDecrypt;

    public Controller(View view) {
        this.view = view;
        attachEventListeners();
    }

    private void attachEventListeners() {
        // Thêm sự kiện cho buttonFromClipboard
        view.getButtonFromClipboard().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện cho buttonFromClipboard ở đây
                System.out.println("getButtonFromClipboard");
            }
        });

        // Thêm sự kiện cho buttonFromFile encrypt
        view.getButtonFromFile().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (lastSelectedDirectory != null) {
                    fileChooser.setCurrentDirectory(new File(lastSelectedDirectory));
                }
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    view.getInputField().setText(selectedFilePath);
                    lastSelectedDirectory = fileChooser.getSelectedFile().getParent();
                    isFileEncrypt = true;
                    fileName = fileChooser.getSelectedFile().getName();
                }
            }
        });

        view.getButtonFromFileDecrypt().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (lastSelectedDirectory != null) {
                    fileChooser.setCurrentDirectory(new File(lastSelectedDirectory));
                }
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFileDecryptPath = fileChooser.getSelectedFile().getAbsolutePath();
                    view.getDecrypInputField().setText(selectedFileDecryptPath);
                    lastSelectedDirectory = fileChooser.getSelectedFile().getParent();
                    isFileDecrypt= true;
                    fileNameDecrypt = fileChooser.getSelectedFile().getName();
                }
            }
        });

        // Thêm sự kiện cho comboBox
        view.getComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện cho comboBox ở đây
                System.out.println("getComboBox");
            }
        });

//        add event listener for calculate button
        view.getButtonCalculate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedTabIndex = view.getTabbedPane().getSelectedIndex();
                if (selectedTabIndex == 0){
                    //                encrypt define
                    String inputText = view.getInputField().getText();
                    String key = view.getKeyField().getText();
                    List<JCheckBox> listCheckBox = getSelectedCheckboxes();

                    if (listCheckBox != null) {
                        for (JCheckBox jCheckBox:listCheckBox) {
                            String result = calculateAlgorithm(jCheckBox, inputText, key);
                            JTextField resultField = view.getCheckboxToResultFieldMap().get(jCheckBox);
                            if (result != null) {
                                resultField.setText(result);
                            }
                        }
                    }
                }
                else if(selectedTabIndex == 1){
//                    decrypt define
                    String inputTextDes = view.getDecrypInputField().getText();
                    String decryptKey = view.getDecryptKeyField().getText();
                    List<JCheckBox> listDecryptCheckBox = getSelectedDecryptCheckboxes();
//                System.out.println(listDecryptCheckBox);
                    if (listDecryptCheckBox != null) {
                        for (JCheckBox jCheckBox:listDecryptCheckBox) {
                            String result = decryptAlgorithm(jCheckBox, inputTextDes, decryptKey);
                            System.out.println(result);
                            JTextField resultField = view.getCheckBoxResultDecryptMap().get(jCheckBox);
                            if (result != null) {
                                resultField.setText(result);
                            }
                        }
                    }
                }

            }
        });

//        add even listener for generate key
        view.getButtonGenerateKey().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<JCheckBox> listCheckBox = getSelectedCheckboxes();
                System.out.println(listCheckBox.get(0).getText());
                String key = null;
                if (listCheckBox != null){
                    try {
                        key = generateKey(listCheckBox.get(0));
                    } catch (NoSuchAlgorithmException ex) {
                        throw new RuntimeException(ex);
                    }
                    JTextField resultField = view.getKeyField();
                    if (key != null) {
                        resultField.setText(key);
                    }
                }
            }
        });
    }

//    add Algorithm Encrypt event
    private String calculateAlgorithm(JCheckBox checkbox, String inputText, String key) {
        String algorithmResult = "";
        if(key == null || key.isEmpty()) {
            showMessageDialog("Key null", "Key error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        if (checkbox.getText().equals("Ceasar")) {
            algorithmResult = new CaesarCipher(Integer.valueOf(key)).calculate(inputText);
        }
        if (checkbox.getText().equals("Vigenere")) {
            algorithmResult = new VigenereCipher(key).calculate(inputText);
        }
        if (checkbox.getText().equals("Twofish")) {
            algorithmResult = twofish.calculate(inputText);
        }
        if (checkbox.getText().equals("Serpent")) {
            algorithmResult =serpent.calculate(inputText);
        }
        if (checkbox.getText().equals("DES")) {
            if (checkIsFile()){
                getOutputPath();
                try {
                    des.encryptFile(selectedFilePath, outputPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                algorithmResult = des.calculate(inputText);
            }

        }
        if (checkbox.getText().equals("AES")) {
            // add encrypt algorithm
            algorithmResult = aes.calculate(inputText);
        }
        if (checkbox.getText().equals("MD5")) {
            // add encrypt algorithm
            algorithmResult = new MD5().calculate(inputText);
        }
        if (checkbox.getText().equals("SHA256")) {
            // add encrypt algorithm
            algorithmResult = new SHA256().calculate(inputText);
        }

        if (checkbox.getText().equals("RSA")){
            algorithmResult = new RSA().calculate(inputText);
        }
        return algorithmResult;
    }

    //    add Algorithm Decrypt event
    private String decryptAlgorithm(JCheckBox checkbox, String inputText, String key) {
        String algorithmResult = "";

        if (checkbox.getText().equals("Ceasar")) {
            algorithmResult = new CaesarCipher(Integer.valueOf(key)).decrypt(inputText);
        }
        if (checkbox.getText().equals("Vigenere")) {
            algorithmResult = new VigenereCipher(key).decrypt(inputText);
        }
        if (checkbox.getText().equals("Twofish")) {
            algorithmResult = twofish.decrypt(inputText);
        }
        if (checkbox.getText().equals("Serpent")) {
            algorithmResult =serpent.decrypt(inputText);
        }
        if (checkbox.getText().equals("DES")) {
            if (checkIsFileDecrypt()){
                getOutputDecryptPath();
                try {
                    System.out.println(outputDecryptPath);
                    System.out.println(selectedFileDecryptPath);
                    des.decryptFile(selectedFileDecryptPath, outputDecryptPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else if(checkKeyDecrypt()) {
                des.setKeyFromText(view.getDecryptKeyField().getText());
                algorithmResult = des.decrypt(inputText);
            }
        }
        if (checkbox.getText().equals("AES")) {
            // add encrypt algorithm
            algorithmResult = aes.decrypt(inputText);
        }

        return algorithmResult;
    }

//    add generate key encrypt
    private String generateKey(JCheckBox checkbox) throws NoSuchAlgorithmException {
        // add generate key algorithm
        String key = "";
        if (checkbox.getText().equals("AES")) {
            aes = new AESEncryption();
            aes.generateKey();
            key = VietnameseTextHelper.bytesToHex(aes.getKey().getEncoded());
        }
        if (checkbox.getText().equals("DES")) {
            des = new DESEncryption();
            des.generateKey();
            key = VietnameseTextHelper.bytesToHex(des.getKey().getEncoded());
        }
        if (checkbox.getText().equals("Twofish")) {
            twofish = new Twofish();
            twofish.generateKey();
            key = VietnameseTextHelper.bytesToHex(twofish.getKey().getEncoded());
        }

        if (checkbox.getText().equals("Serpent")) {
            serpent= new Serpent();
            serpent.generateKey();
            key = VietnameseTextHelper.bytesToHex(serpent.getKey().getEncoded());
        }
        if (checkbox.getText().equals("MD5")) {
            System.out.println("MD5");
        }
        if (checkbox.getText().equals("SHA256")) {
            System.out.println("SHA256");
        }

        if (checkbox.getText().equals("RSA")){
            rsa = new RSA();
            try {
                rsa.generateKey();
//                key
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return key;
    }


    public List<JCheckBox> getSelectedCheckboxes() {
        ArrayList<JCheckBox> selectedCheckboxes = new ArrayList<>();

        for (Map.Entry<JCheckBox, JTextField> entry : view.getCheckboxToResultFieldMap().entrySet()) {
            JCheckBox checkbox = entry.getKey();
            if (checkbox.isSelected()) {
                selectedCheckboxes.add(checkbox);
            }
        }

        return selectedCheckboxes;
    }

    public List<JCheckBox> getSelectedDecryptCheckboxes() {
        ArrayList<JCheckBox> selectedCheckboxes = new ArrayList<>();

        for (Map.Entry<JCheckBox, JTextField> entry : view.getCheckBoxResultDecryptMap().entrySet()) {
            JCheckBox checkbox = entry.getKey();
            if (checkbox.isSelected()) {
                selectedCheckboxes.add(checkbox);
            }
        }
        return selectedCheckboxes;
    }

    public boolean checkIsFile() {
        return isFileEncrypt && new File(selectedFilePath).isFile();
    }

    public boolean checkIsFileDecrypt() {
        return isFileDecrypt;
    }

    public boolean checkKey(){
        return view.getKeyField().getText().isEmpty() && view.getKeyField().getText() != null && !new File(view.getKeyField().getText()).isFile();
    }

    public boolean checkKeyDecrypt(){
        return !view.getDecryptKeyField().getText().isEmpty() && view.getDecryptKeyField().getText() != null && !new File(view.getDecryptKeyField().getText()).isFile();
    }

    //    file path for encrypt output
    public void getOutputPath(){
        JFileChooser fileChooser = new JFileChooser();
        if (lastSelectedDirectory != null) {
            fileChooser.setCurrentDirectory(new File(lastSelectedDirectory));
        }
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            outputPath = fileChooser.getSelectedFile().getAbsolutePath();
        }
        if (fileName != null && !fileName.isEmpty()) {
            outputPath = outputPath + File.separator + fileName;
        }
    }

//    file path for decrypt output
    public void getOutputDecryptPath(){
        JFileChooser fileChooser = new JFileChooser();
        if (lastSelectedDirectory != null) {
            fileChooser.setCurrentDirectory(new File(lastSelectedDirectory));
        }
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showOpenDialog(null);
        System.out.println(fileNameDecrypt);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            outputDecryptPath = fileChooser.getSelectedFile().getAbsolutePath();
            if (fileNameDecrypt != null && !fileNameDecrypt.isEmpty()) {
                fileNameDecrypt = fileNameDecrypt.replaceFirst("\\.zip$", "");
                if (!fileNameDecrypt.endsWith("_decrypt.zip")) {
                    fileNameDecrypt += "_decrypt.zip";
                }
                outputDecryptPath = outputDecryptPath + File.separator + fileNameDecrypt;
            }
        }
        System.out.println(fileNameDecrypt);
    }

//    dialog
    public static void showMessageDialog(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
}
