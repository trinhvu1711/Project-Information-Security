package controller;

import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

//        // Thêm sự kiện cho checkBoxAlgorithm1
//        view.getCheckBoxAlgorithm1().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Xử lý sự kiện cho checkBoxAlgorithm1 ở đây
//                System.out.println("getCheckBoxAlgorithm1");
//            }
//        });
//
//        // Thêm sự kiện cho checkBoxAlgorithm2
//        view.getCheckBoxAlgorithm2().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Xử lý sự kiện cho checkBoxAlgorithm2 ở đây
//                System.out.println("getCheckBoxAlgorithm2");
//            }
//        });
//
//        // Thêm sự kiện cho checkBoxAlgorithm3
//        view.getCheckBoxAlgorithm3().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Xử lý sự kiện cho checkBoxAlgorithm3 ở đây
//                System.out.println("getCheckBoxAlgorithm3");
//            }
//        });

        // Thêm sự kiện cho comboBox
        view.getComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện cho comboBox ở đây
                System.out.println("getComboBox");
            }
        });


    }
}
