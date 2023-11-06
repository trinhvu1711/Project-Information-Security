package controller;

import models.*;
import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {
    private View view;
    Serpent serpent = new Serpent();
    RSA rsa = new RSA();
    DESEncryption des = new DESEncryption();
    AESEncryption aes = new AESEncryption();
    Twofish twofish = new Twofish();

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

        // Thêm sự kiện cho buttonFromFile
        view.getButtonFromFile().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện cho buttonFromFile ở đây
                System.out.println("getButtonFromFile");
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
                // Xử lý tính toán và hiển thị kết quả ở đây
                // Lấy giá trị từ input và key field
                String inputText = view.getInputField().getText();
                String key = view.getKeyField().getText();
                // Lấy checkbox đã chọn
                List<JCheckBox> listCheckBox = getSelectedCheckboxes();
//                System.out.println(listCheckBox.get(0).getText());
                // Kiểm tra xem checkbox có được chọn không
                if (listCheckBox != null) {
                    // Thực hiện tính toán dựa trên checkbox, input và key
                    for (JCheckBox jCheckBox:listCheckBox) {
                        String result = calculateAlgorithm(jCheckBox, inputText, key);
                        System.out.println(result);
                        // Hiển thị kết quả lên result field tương ứng
                        JTextField resultField = view.getCheckboxToResultFieldMap().get(jCheckBox);
                        if (result != null) {
                            resultField.setText(result);
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

//    add Algorithm
    private String calculateAlgorithm(JCheckBox checkbox, String inputText, String key) {
        // Determine which algorithm to use based on the checkbox
        String algorithmResult = "";

        if (checkbox.getText().equals("Ceasar")) {
            // Use the first algorithm (replace with your specific implementation)
            algorithmResult = new CaesarCipher(Integer.valueOf(key)).calculate(inputText);
        }
        if (checkbox.getText().equals("Vigenere")) {
            // Use the first algorithm (replace with your specific implementation)
            algorithmResult = new VigenereCipher(key).calculate(inputText);
        }
        if (checkbox.getText().equals("Twofish")) {
            // Use the first algorithm (replace with your specific implementation)
            algorithmResult = twofish.calculate(inputText);
        }
        if (checkbox.getText().equals("Serpent")) {
            // Use the first algorithm (replace with your specific implementation)

            algorithmResult =serpent.calculate(inputText);
        }
        if (checkbox.getText().equals("DES")) {
            // Use the first algorithm (replace with your specific implementation)
            algorithmResult = des.calculate(inputText);
        }
        if (checkbox.getText().equals("AES")) {
            // Use the first algorithm (replace with your specific implementation)
            algorithmResult = aes.calculate(inputText);
        }
        if (checkbox.getText().equals("MD5")) {
            // Use the first algorithm (replace with your specific implementation)
            algorithmResult = new MD5().calculate(inputText);
        }
        if (checkbox.getText().equals("SHA256")) {
            // Use the first algorithm (replace with your specific implementation)
            algorithmResult = new SHA256().calculate(inputText);
        }
        return algorithmResult;
    }

    private String generateKey(JCheckBox checkbox) throws NoSuchAlgorithmException {
        // Determine which algorithm to use based on the checkbox
        String key = "";
        if (checkbox.getText().equals("AES")) {
            // Use the first algorithm (replace with your specific implementation)
            aes = new AESEncryption();
            aes.generateKey();
            key = VietnameseTextHelper.bytesToHex(aes.getKey().getEncoded());
        }
        if (checkbox.getText().equals("DES")) {
            // Use the first algorithm (replace with your specific implementation)
            des = new DESEncryption();
            des.generateKey();
            key = VietnameseTextHelper.bytesToHex(des.getKey().getEncoded());
        }
        if (checkbox.getText().equals("Twofish")) {
            // Use the first algorithm (replace with your specific implementation)
            twofish = new Twofish();
            twofish.generateKey();
            key = VietnameseTextHelper.bytesToHex(twofish.getKey().getEncoded());
        }

        if (checkbox.getText().equals("Serpent")) {
            // Use the first algorithm (replace with your specific implementation)
            serpent= new Serpent();
            serpent.generateKey();
            key = VietnameseTextHelper.bytesToHex(serpent.getKey().getEncoded());
        }
        return key;
    }


    public List<JCheckBox> getSelectedCheckboxes() {
        List<JCheckBox> selectedCheckboxes = new ArrayList<>();

        for (Map.Entry<JCheckBox, JTextField> entry : view.getCheckboxToResultFieldMap().entrySet()) {
            JCheckBox checkbox = entry.getKey();
            if (checkbox.isSelected()) {
                selectedCheckboxes.add(checkbox);
            }
        }

        return selectedCheckboxes;
    }
}
