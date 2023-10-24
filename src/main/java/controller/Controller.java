package controller;

import models.CaesarCipher;
import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {
    private View view;
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

        view.getButtonCalculate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý tính toán và hiển thị kết quả ở đây
                // Lấy giá trị từ input và key field
                String inputText = view.getInputField().getText();
                String key = view.getKeyField().getText();
                // Lấy checkbox đã chọn
                List<JCheckBox> listCheckBox = getSelectedCheckboxes();
                System.out.println(listCheckBox.get(0).getText());
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

    }

    private String calculateAlgorithm(JCheckBox checkbox, String inputText, String key) {
        // Determine which algorithm to use based on the checkbox
        String algorithmResult = "";

        if (checkbox.getText().equals("Ceasar")) {
            // Use the first algorithm (replace with your specific implementation)
            algorithmResult = new CaesarCipher(Integer.valueOf(key)).calculate(inputText);
        }

        return algorithmResult;
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
