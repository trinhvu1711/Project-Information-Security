package controller;

import models.*;
import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class Controller {
    private final View view;
    Serpent serpent = new Serpent();
    RSA rsa = new RSA();
    DESEncryption des = new DESEncryption();
    AESEncryption aes = new AESEncryption();
    Twofish twofish = new Twofish();
    HashFunction hashFunction = new HashFunction();
    boolean isFileEncrypt = false;
    boolean isFileDecrypt = false;
    boolean isHexKeyEncrypt, isHexKeyDecrypt = false;

    String lastSelectedDirectory = "D:\\VuxBaox\\University Document\\Semester 7\\test_attt";
    String selectedFilePath = "";
    String outputPath = "";
    String selectedFileDecryptPath, selectedFileDSPath = "";
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
                    fileName = fileChooser.getSelectedFile().getName();
                }
            }
        });

        view.getButtonFromFileDigitalSignature().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (lastSelectedDirectory != null) {
                    fileChooser.setCurrentDirectory(new File(lastSelectedDirectory));
                }
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFileDSPath = fileChooser.getSelectedFile().getAbsolutePath();
                    view.getDigitalSignatureInputField().setText(selectedFileDSPath);
                    lastSelectedDirectory = fileChooser.getSelectedFile().getParent();
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
        view.getComboBoxInputEncrypt().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) view.getComboBoxInputEncrypt().getSelectedItem();
                if (selectedValue.equals("File")){
                    isFileEncrypt = true;
                }
                else {
                    isFileEncrypt = false;
                }
            }
        });
        view.getComboBoxKeyEncrypt().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) view.getComboBoxKeyEncrypt().getSelectedItem();
                if (selectedValue.equals("String hex")){
                    isHexKeyEncrypt = true;
                }
                else {
                    isHexKeyEncrypt = false;
                }
            }
        });
        view.getComboBoxKeyDecrypt().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) view.getComboBoxKeyDecrypt().getSelectedItem();
                if (selectedValue.equals("String hex")){
                    isHexKeyDecrypt = true;
                }
                else {
                    isHexKeyDecrypt = false;
                }
            }
        });
        view.getComboBoxInputDecrypt().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) view.getComboBoxInputDecrypt().getSelectedItem();
                if (selectedValue.equals("File")) {
                    isFileDecrypt = true;
                } else {
                    isFileDecrypt = false;
                }
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
                else if(selectedTabIndex == 2){
                    List<JCheckBox> dsCheckboxes = getSelectedDSCheckboxes();
                    if (dsCheckboxes != null) {
                        for (JCheckBox jCheckBox:dsCheckboxes) {
                            String result = null;
                            try {
                                result = calculateDSAlgorithm(jCheckBox);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            System.out.println(result);
                            JTextField resultField = view.getCheckBoxResultDigitalSignatureMap().get(jCheckBox);
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

        //        add even listener for generate key
        view.getButtonCompareDS().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<JCheckBox> dsCheckboxes = getSelectedDSCheckboxes();

                boolean result = false;
                if (dsCheckboxes != null) {
                    for (JCheckBox jCheckBox:dsCheckboxes) {
                        try {
                            String input = view.getDigitalSignatureOutputField().getText();
                            result = compareDSAlgorithm(jCheckBox, input);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        System.out.println(result);
                    }

                }
                if (result) {
                    showMessageDialog("File is verify", "Checksum result", JOptionPane.PLAIN_MESSAGE);

                }
                else {
                    showMessageDialog("File not verify", "Checksum result", JOptionPane.ERROR_MESSAGE);
                }
                result =false;
            }

        });
    }

//    add Algorithm Encrypt event
    private String calculateAlgorithm(JCheckBox checkbox, String inputText, String key) {
        String algorithmResult = "";
//        if(key == null || key.isEmpty()) {
//            showMessageDialog("Key null", "Key error", JOptionPane.ERROR_MESSAGE);
//            return "";
//        }
        if (checkbox.getText().equals("Ceasar")) {
            algorithmResult = new CaesarCipher(Integer.valueOf(key)).calculate(inputText);
        }
        if (checkbox.getText().equals("Vigenere")) {
            algorithmResult = new VigenereCipher(key).calculate(inputText);
        }
        if (checkbox.getText().equals("Hill")) {
            algorithmResult = new Hill(Hill.getKeyMatrix(key)).calculate(inputText);
        }
        if (checkbox.getText().equals("Twofish")) {
            if (isFileEncrypt){
                getOutputPath();
                try {
                    twofish.encryptFile(selectedFilePath, outputPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                algorithmResult = twofish.calculate(inputText);
            }
        }
        if (checkbox.getText().equals("Serpent")) {
            if (isFileEncrypt){
                getOutputPath();
                try {
                    serpent.encryptFile(selectedFilePath, outputPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                algorithmResult =serpent.calculate(inputText);
            }

        }
        if (checkbox.getText().equals("DES")) {
            if (isFileEncrypt){
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
            if (isFileEncrypt){
                getOutputPath();
                try {
                    aes.encryptFile(selectedFilePath, outputPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                algorithmResult = aes.calculate(inputText);
            }

        }
        if (checkbox.getText().equals("RSA")){
            if (isFileEncrypt){
                getOutputPath();
                try {
                    rsa.fileEncrypt(selectedFilePath, outputPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                algorithmResult = rsa.calculate(inputText);
            }

        }
        if (checkbox.getText().equals("MD5")){
            algorithmResult = hashFunction.calculate(inputText, HashFunction.ALGORITHM_MD5);
        }
        if (checkbox.getText().equals("SHA1")){
            algorithmResult = hashFunction.calculate(inputText, HashFunction.ALGORITHM_SHA1);
        }
        if (checkbox.getText().equals("SHA224")){
            algorithmResult = hashFunction.calculate(inputText, HashFunction.ALGORITHM_SHA224);
        }
        if (checkbox.getText().equals("SHA256")){
            algorithmResult = hashFunction.calculate(inputText, HashFunction.ALGORITHM_SHA256);
        }
        if (checkbox.getText().equals("SHA384")){
            algorithmResult = hashFunction.calculate(inputText, HashFunction.ALGORITHM_SHA384);
        }
        if (checkbox.getText().equals("SHA512")){
            algorithmResult = hashFunction.calculate(inputText, HashFunction.ALGORITHM_SHA512);
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
        if (checkbox.getText().equals("Hill")) {
            algorithmResult = new Hill(Hill.getKeyMatrix(key)).decrypt(inputText).replaceAll("/", "");
        }
        if (checkbox.getText().equals("Twofish")) {
            twofish.setTwoFishKeyFromString(view.getDecryptKeyField().getText());
            if (isFileDecrypt){
                getOutputDecryptPath();
                try {
                    System.out.println(outputDecryptPath);
                    System.out.println(selectedFileDecryptPath);
                    twofish.decryptFile(selectedFileDecryptPath, outputDecryptPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else if(checkKeyDecrypt()) {
                algorithmResult = twofish.decrypt(inputText);
            }

        }
        if (checkbox.getText().equals("Serpent")) {
            serpent.setSerpentKeyFromString(view.getDecryptKeyField().getText());
            if (isFileDecrypt){
                getOutputDecryptPath();
                try {
                    System.out.println(outputDecryptPath);
                    System.out.println(selectedFileDecryptPath);
                    serpent.decryptFile(selectedFileDecryptPath, outputDecryptPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else if(checkKeyDecrypt()) {
                algorithmResult =serpent.decrypt(inputText);
            }

        }
        if (checkbox.getText().equals("DES")) {
            des.setDESKeyFromString(view.getDecryptKeyField().getText());
            if (isFileDecrypt){
                getOutputDecryptPath();
                try {
                    System.out.println(outputDecryptPath);
                    System.out.println(selectedFileDecryptPath);
                    des.decryptFile(selectedFileDecryptPath, outputDecryptPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else if(checkKeyDecrypt()) {
                algorithmResult = des.decrypt(inputText);
            }
        }
        if (checkbox.getText().equals("AES")) {
            aes.setAESKeyFromString(view.getDecryptKeyField().getText());
            if (isFileDecrypt){
                getOutputDecryptPath();
                try {
                    System.out.println(outputDecryptPath);
                    System.out.println(selectedFileDecryptPath);
                    aes.decryptFile(selectedFileDecryptPath, outputDecryptPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else if(checkKeyDecrypt()) {
                algorithmResult = aes.decrypt(inputText);
            }

        }

        if (checkbox.getText().equals("RSA")){
            if (isFileDecrypt) {
                getOutputDecryptPath();
                try {
                    rsa.fileDecrypt(selectedFileDecryptPath, outputDecryptPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else {
                try {
                    rsa.setPrivateKeyFromString(view.getDecryptKeyField().getText());
                    algorithmResult = rsa.decrypt(inputText);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return algorithmResult;
    }

    private String calculateDSAlgorithm(JCheckBox checkbox) throws IOException {
        String algorithmResult = "";
        if (checkbox.getText().equals("MD5")){
            algorithmResult = hashFunction.calculateFromFile(selectedFileDSPath, HashFunction.ALGORITHM_MD5);
        }
        if (checkbox.getText().equals("SHA1")){
            algorithmResult = hashFunction.calculateFromFile(selectedFileDSPath, HashFunction.ALGORITHM_SHA1);
        }
        if (checkbox.getText().equals("SHA224")){
            algorithmResult = hashFunction.calculateFromFile(selectedFileDSPath, HashFunction.ALGORITHM_SHA224);
        }
        if (checkbox.getText().equals("SHA256")){
            algorithmResult = hashFunction.calculateFromFile(selectedFileDSPath, HashFunction.ALGORITHM_SHA256);
        }
        if (checkbox.getText().equals("SHA384")){
            algorithmResult = hashFunction.calculateFromFile(selectedFileDSPath, HashFunction.ALGORITHM_SHA384);
        }
        if (checkbox.getText().equals("SHA512")){
            algorithmResult = hashFunction.calculateFromFile(selectedFileDSPath, HashFunction.ALGORITHM_SHA512);
        }

        return algorithmResult;
    }

    private boolean compareDSAlgorithm(JCheckBox checkbox, String input) throws IOException {
        boolean algorithmResult = false;
        if (checkbox.getText().equals("MD5")){
            algorithmResult = hashFunction.calculateFromFile(selectedFileDSPath, HashFunction.ALGORITHM_MD5).equals(input);
            if (algorithmResult) return true;
        }
        if (checkbox.getText().equals("SHA1")){
            algorithmResult = hashFunction.calculateFromFile(selectedFileDSPath, HashFunction.ALGORITHM_SHA1).equals(input);
            if (algorithmResult) return true;
        }
        if (checkbox.getText().equals("SHA224")){
            algorithmResult = hashFunction.calculateFromFile(selectedFileDSPath, HashFunction.ALGORITHM_SHA224).equals(input);
            if (algorithmResult) return true;
        }
        if (checkbox.getText().equals("SHA256")){
            algorithmResult = hashFunction.calculateFromFile(selectedFileDSPath, HashFunction.ALGORITHM_SHA256).equals(input);
            if (algorithmResult) return true;
        }
        if (checkbox.getText().equals("SHA384")){
            algorithmResult = hashFunction.calculateFromFile(selectedFileDSPath, HashFunction.ALGORITHM_SHA384).equals(input);
            if (algorithmResult) return true;
        }
        if (checkbox.getText().equals("SHA512")){
            algorithmResult = hashFunction.calculateFromFile(selectedFileDSPath, HashFunction.ALGORITHM_SHA512).equals(input);
            if (algorithmResult) return true;
        }
        return false;
    }

//    add generate key encrypt
    private String generateKey(JCheckBox checkbox) throws NoSuchAlgorithmException {
        // add generate key algorithm
        String key = "";
        if (checkbox.getText().equals("AES")) {
            aes = new AESEncryption();
            aes.generateKey();
            key = Base64.getEncoder().encodeToString(aes.getKey().getEncoded());
        }
        if (checkbox.getText().equals("DES")) {
            des = new DESEncryption();
            des.generateKey();
            key = Base64.getEncoder().encodeToString(des.getKey().getEncoded());
        }
        if (checkbox.getText().equals("Twofish")) {
            twofish = new Twofish();
            twofish.generateKey();
            key = Base64.getEncoder().encodeToString(twofish.getKey().getEncoded());
        }

        if (checkbox.getText().equals("Serpent")) {
            serpent= new Serpent();
            serpent.generateKey();
            key = Base64.getEncoder().encodeToString(serpent.getKey().getEncoded());
        }

        if (checkbox.getText().equals("RSA")){
            rsa = new RSA();
            try {
                rsa.generateKey();
                key = Base64.getEncoder().encodeToString(rsa.getPrivateKey().getEncoded());
                view.showPublicPrivateKey(rsa.getPublicKey(), rsa.getPrivateKey());
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

    public List<JCheckBox> getSelectedDSCheckboxes() {
        ArrayList<JCheckBox> selectedCheckboxes = new ArrayList<>();

        for (Map.Entry<JCheckBox, JTextField> entry : view.getCheckBoxResultDigitalSignatureMap().entrySet()) {
            JCheckBox checkbox = entry.getKey();
            if (checkbox.isSelected()) {
                selectedCheckboxes.add(checkbox);
            }
        }
        return selectedCheckboxes;
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
        int returnValue = fileChooser.showSaveDialog(null);
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
        int returnValue = fileChooser.showSaveDialog(null);
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
