/*
 * Created by JFormDesigner on Mon Apr 08 17:24:59 CST 2019
 */

package view;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Chief
 */
public class MailSocketView extends JFrame {

    public MailSocketView() {
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        tabbedPane2 = new JTabbedPane();
        composePanel = new JPanel();
        toTxtField = new JTextField();
        label1 = new JLabel();
        subjectTxtField = new JTextField();
        label2 = new JLabel();
        contentScrollPane = new JScrollPane();
        contentTxtArea = new JTextArea();
        label3 = new JLabel();
        fromTxtField = new JTextField();
        label4 = new JLabel();
        sendBtn = new JButton();
        mailBoxPanel = new JPanel();
        mailScrollPane = new JScrollPane();
        mailTable = new JTable();
        settingPanel = new JPanel();

        //======== this ========
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout());

        //======== tabbedPane2 ========
        {
            tabbedPane2.setFocusable(false);
            tabbedPane2.setAlignmentX(0.0F);
            tabbedPane2.setAlignmentY(0.0F);
            tabbedPane2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

            //======== composePanel ========
            {
                composePanel.setAlignmentY(0.0F);
                composePanel.setAlignmentX(0.0F);

                //---- toTxtField ----
                toTxtField.setBorder(new EtchedBorder());
                toTxtField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));

                //---- label1 ----
                label1.setText("To");
                label1.setHorizontalAlignment(SwingConstants.RIGHT);
                label1.setLabelFor(toTxtField);
                label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));

                //---- subjectTxtField ----
                subjectTxtField.setBorder(new EtchedBorder());
                subjectTxtField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));

                //---- label2 ----
                label2.setText("Subject");
                label2.setHorizontalAlignment(SwingConstants.RIGHT);
                label2.setLabelFor(subjectTxtField);
                label2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));

                //======== contentScrollPane ========
                {
                    contentScrollPane.setBorder(null);

                    //---- contentTxtArea ----
                    contentTxtArea.setBorder(new EtchedBorder());
                    contentTxtArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
                    contentScrollPane.setViewportView(contentTxtArea);
                }

                //---- label3 ----
                label3.setText("Content");
                label3.setHorizontalAlignment(SwingConstants.RIGHT);
                label3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
                label3.setLabelFor(contentTxtArea);

                //---- fromTxtField ----
                fromTxtField.setBorder(new EtchedBorder());
                fromTxtField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));

                //---- label4 ----
                label4.setText("From");
                label4.setHorizontalAlignment(SwingConstants.RIGHT);
                label4.setLabelFor(toTxtField);
                label4.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));

                //---- sendBtn ----
                sendBtn.setText("Send");
                sendBtn.setFocusPainted(false);
                sendBtn.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));

                GroupLayout composePanelLayout = new GroupLayout(composePanel);
                composePanel.setLayout(composePanelLayout);
                composePanelLayout.setHorizontalGroup(
                    composePanelLayout.createParallelGroup()
                        .addGroup(composePanelLayout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(composePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label3)
                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(composePanelLayout.createParallelGroup()
                                .addComponent(sendBtn, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                                .addGroup(composePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(contentScrollPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
                                    .addComponent(toTxtField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
                                    .addComponent(subjectTxtField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
                                    .addComponent(fromTxtField, GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)))
                            .addContainerGap(14, Short.MAX_VALUE))
                );
                composePanelLayout.setVerticalGroup(
                    composePanelLayout.createParallelGroup()
                        .addGroup(composePanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(composePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(toTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label1))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(composePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(subjectTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label2))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(composePanelLayout.createParallelGroup()
                                .addComponent(contentScrollPane, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                                .addGroup(composePanelLayout.createSequentialGroup()
                                    .addComponent(label3)
                                    .addGap(0, 339, Short.MAX_VALUE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(composePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(fromTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label4))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sendBtn)
                            .addContainerGap())
                );
            }
            tabbedPane2.addTab("Compose", composePanel);

            //======== mailBoxPanel ========
            {
                mailBoxPanel.setAlignmentX(0.0F);
                mailBoxPanel.setAlignmentY(0.0F);

                //======== mailScrollPane ========
                {
                    mailScrollPane.setAlignmentX(0.0F);
                    mailScrollPane.setAlignmentY(0.0F);

                    //---- mailTable ----
                    mailTable.setAlignmentY(0.0F);
                    mailTable.setAlignmentX(0.0F);
                    mailScrollPane.setViewportView(mailTable);
                }

                GroupLayout mailBoxPanelLayout = new GroupLayout(mailBoxPanel);
                mailBoxPanel.setLayout(mailBoxPanelLayout);
                mailBoxPanelLayout.setHorizontalGroup(
                    mailBoxPanelLayout.createParallelGroup()
                        .addComponent(mailScrollPane, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE)
                );
                mailBoxPanelLayout.setVerticalGroup(
                    mailBoxPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, mailBoxPanelLayout.createSequentialGroup()
                            .addGap(0, 20, Short.MAX_VALUE)
                            .addComponent(mailScrollPane, GroupLayout.PREFERRED_SIZE, 491, GroupLayout.PREFERRED_SIZE))
                );
            }
            tabbedPane2.addTab("Mail Box", mailBoxPanel);

            //======== settingPanel ========
            {
                settingPanel.setAlignmentY(0.0F);
                settingPanel.setAlignmentX(0.0F);

                GroupLayout settingPanelLayout = new GroupLayout(settingPanel);
                settingPanel.setLayout(settingPanelLayout);
                settingPanelLayout.setHorizontalGroup(
                    settingPanelLayout.createParallelGroup()
                        .addGap(0, 1018, Short.MAX_VALUE)
                );
                settingPanelLayout.setVerticalGroup(
                    settingPanelLayout.createParallelGroup()
                        .addGap(0, 511, Short.MAX_VALUE)
                );
            }
            tabbedPane2.addTab("Setting", settingPanel);
        }
        contentPane.add(tabbedPane2);
        setSize(1020, 575);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTabbedPane tabbedPane2;
    private JPanel composePanel;
    private JTextField toTxtField;
    private JLabel label1;
    private JTextField subjectTxtField;
    private JLabel label2;
    private JScrollPane contentScrollPane;
    private JTextArea contentTxtArea;
    private JLabel label3;
    private JTextField fromTxtField;
    private JLabel label4;
    private JButton sendBtn;
    private JPanel mailBoxPanel;
    private JScrollPane mailScrollPane;
    private JTable mailTable;
    private JPanel settingPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
