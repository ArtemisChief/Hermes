/*
 * Created by JFormDesigner on Mon Apr 08 17:24:59 CST 2019
 */

package view;

import controller.ConnectionController;
import controller.MailController;
import model.MailModel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

/**
 * @author Chief
 */
public class MailConnectionView extends JFrame {

    private ConnectionController connectionController;
    private MailController mailController;

    private boolean isConnected = false;

    public MailConnectionView() {
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (readSetting()) {
            isConnected = true;
            mailController = new MailController(connectionController.getPOP3Connection(), connectionController.getSMTPConnection());
            fillReceivedMailTalbe();
        }

    }

    private boolean readSetting() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("setting")));

            String pop = reader.readLine();
            int popPort = Integer.parseInt(reader.readLine());
            String smtp = reader.readLine();
            int smtpPort = Integer.parseInt(reader.readLine());
            String username = reader.readLine();
            String password = reader.readLine();

            reader.close();

            connectionController = new ConnectionController(pop, popPort, smtp, smtpPort, username, password);

            popTxtField.setText(pop);
            popPortTxtField.setText(Integer.toString(popPort));
            smtpTxtField.setText(smtp);
            smtpPortTxtField.setText(Integer.toString(smtpPort));
            mailTxtField.setText(username);
            pwdTxtField.setText(password);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean writeSetting() {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("setting")));

            String pop = popTxtField.getText();
            int popPort = Integer.parseInt(popPortTxtField.getText());
            String smtp = smtpTxtField.getText();
            int smtpPort = Integer.parseInt(smtpPortTxtField.getText());
            String username = mailTxtField.getText();
            String password = pwdTxtField.getPassword().toString();

            StringBuilder stringBuilder = new StringBuilder(pop);
            stringBuilder.append("\n").append(popPort).append("\n").append(smtp).append("\n").append(smtpPort).append("\n").append(username).append("\n").append(password);

            writer.write(stringBuilder.toString());
            writer.close();

            connectionController = new ConnectionController(pop, popPort, smtp, smtpPort, username, password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void fillReceivedMailTalbe() {
        connectionController.checkConnection(connectionController.getPOP3Connection());
        mailController.receiveMailAmount();

        connectionController.checkConnection(connectionController.getPOP3Connection());
        if (mailController.mailLogin()) {
            while (mailController.getCurrentReading() != 1800) {
                mailController.receiveMail();
            }
        }
        mailController.mailLogout();

        ArrayList<MailModel> mailBox = mailController.getMailBox();
        DefaultTableModel model = (DefaultTableModel) mailTable.getModel();

        for (MailModel mail : mailBox) {
            model.addRow(mail.getHeadInfo());
        }
    }

    private void sendBtnActionPerformed(ActionEvent e) {
        if (isConnected) {
            connectionController.checkConnection(connectionController.getSMTPConnection());
            mailController.sendMail(toTxtField.getText(), subjectTxtField.getText(), fromTxtField.getText(), contentTxtArea.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Please set and save the address first", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mailTableMouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            connectionController.checkConnection(connectionController.getPOP3Connection());
            MailModel mail = mailController.readMail(Integer.parseInt(mailTable.getValueAt(mailTable.getSelectedRow(), 0).toString()));
            String content;
            if (mail == null)
                content = "读取邮件时出现错误！";
            else
                content = "Subject: " + mail.getSubject() + "<br>" +
                        "From: " + mail.getFrom() + "<br>" +
                        "To: " + mail.getTo() + "<br>" +
                        "Date: " + mail.getDate() + "<br><br>" +
                        mail.getContent();
            mailTxtPane.setText(content);
        }
    }

    private void saveBtnActionPerformed(ActionEvent e) {
        if (!writeSetting()) {
            JOptionPane.showMessageDialog(null, "Setting error", "Error", JOptionPane.ERROR_MESSAGE);
            isConnected = false;
        } else {
            isConnected = true;
            fillReceivedMailTalbe();
        }
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
        mailContentScrollPane = new JScrollPane();
        mailTxtPane = new JTextPane();
        settingPanel = new JPanel();
        popTxtField = new JTextField();
        label5 = new JLabel();
        label6 = new JLabel();
        smtpTxtField = new JTextField();
        label7 = new JLabel();
        mailTxtField = new JTextField();
        label8 = new JLabel();
        popPortTxtField = new JTextField();
        label9 = new JLabel();
        label10 = new JLabel();
        smtpPortTxtField = new JTextField();
        saveBtn = new JButton();
        pwdTxtField = new JPasswordField();

        //======== this ========
        setResizable(false);
        setTitle("Hermes - Simple Java mail client");
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
                toTxtField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- label1 ----
                label1.setText("To");
                label1.setHorizontalAlignment(SwingConstants.RIGHT);
                label1.setLabelFor(toTxtField);
                label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- subjectTxtField ----
                subjectTxtField.setBorder(new EtchedBorder());
                subjectTxtField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- label2 ----
                label2.setText("Subject");
                label2.setHorizontalAlignment(SwingConstants.RIGHT);
                label2.setLabelFor(subjectTxtField);
                label2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //======== contentScrollPane ========
                {
                    contentScrollPane.setBorder(null);

                    //---- contentTxtArea ----
                    contentTxtArea.setBorder(new EtchedBorder());
                    contentTxtArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
                    contentScrollPane.setViewportView(contentTxtArea);
                }

                //---- label3 ----
                label3.setText("Content");
                label3.setHorizontalAlignment(SwingConstants.RIGHT);
                label3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
                label3.setLabelFor(contentTxtArea);

                //---- fromTxtField ----
                fromTxtField.setBorder(new EtchedBorder());
                fromTxtField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- label4 ----
                label4.setText("From");
                label4.setHorizontalAlignment(SwingConstants.RIGHT);
                label4.setLabelFor(toTxtField);
                label4.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- sendBtn ----
                sendBtn.setText("Send");
                sendBtn.setFocusPainted(false);
                sendBtn.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
                sendBtn.addActionListener(e -> sendBtnActionPerformed(e));

                GroupLayout composePanelLayout = new GroupLayout(composePanel);
                composePanel.setLayout(composePanelLayout);
                composePanelLayout.setHorizontalGroup(
                    composePanelLayout.createParallelGroup()
                        .addGroup(composePanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(composePanelLayout.createParallelGroup()
                                .addGroup(composePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(label1, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                    .addComponent(label3, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                    .addComponent(label4, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(composePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(sendBtn, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                                .addComponent(contentScrollPane, GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
                                .addComponent(fromTxtField, GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
                                .addComponent(subjectTxtField, GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
                                .addComponent(toTxtField, GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE))
                            .addContainerGap(28, Short.MAX_VALUE))
                );
                composePanelLayout.setVerticalGroup(
                    composePanelLayout.createParallelGroup()
                        .addGroup(composePanelLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addGroup(composePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1)
                                .addComponent(toTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(composePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(subjectTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(composePanelLayout.createParallelGroup()
                                .addGroup(composePanelLayout.createSequentialGroup()
                                    .addComponent(label3)
                                    .addGap(0, 347, Short.MAX_VALUE))
                                .addComponent(contentScrollPane, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(composePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label4)
                                .addComponent(fromTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
                    mailScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    mailScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                    //---- mailTable ----
                    mailTable.setAlignmentY(0.0F);
                    mailTable.setAlignmentX(0.0F);
                    mailTable.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
                    mailTable.setModel(new DefaultTableModel(
                        new Object[][] {
                        },
                        new String[] {
                            "No", "From", "Subject", "Time"
                        }
                    ) {
                        Class<?>[] columnTypes = new Class<?>[] {
                            Integer.class, String.class, String.class, String.class
                        };
                        boolean[] columnEditable = new boolean[] {
                            true, false, false, false
                        };
                        @Override
                        public Class<?> getColumnClass(int columnIndex) {
                            return columnTypes[columnIndex];
                        }
                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return columnEditable[columnIndex];
                        }
                    });
                    {
                        TableColumnModel cm = mailTable.getColumnModel();
                        cm.getColumn(0).setPreferredWidth(45);
                        cm.getColumn(1).setResizable(false);
                        cm.getColumn(1).setPreferredWidth(215);
                        cm.getColumn(2).setResizable(false);
                        cm.getColumn(2).setPreferredWidth(530);
                        cm.getColumn(3).setResizable(false);
                        cm.getColumn(3).setPreferredWidth(207);
                    }
                    mailTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    mailTable.setAutoCreateRowSorter(true);
                    mailTable.setFocusable(false);
                    mailTable.setFillsViewportHeight(true);
                    mailTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    mailTable.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            mailTableMouseClicked(e);
                        }
                    });
                    mailScrollPane.setViewportView(mailTable);
                }

                //======== mailContentScrollPane ========
                {

                    //---- mailTxtPane ----
                    mailTxtPane.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
                    mailTxtPane.setBorder(null);
                    mailTxtPane.setContentType("text/html");
                    mailTxtPane.setFocusable(false);
                    mailTxtPane.setEditable(false);
                    mailContentScrollPane.setViewportView(mailTxtPane);
                }

                GroupLayout mailBoxPanelLayout = new GroupLayout(mailBoxPanel);
                mailBoxPanel.setLayout(mailBoxPanelLayout);
                mailBoxPanelLayout.setHorizontalGroup(
                    mailBoxPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, mailBoxPanelLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addGroup(mailBoxPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(mailScrollPane, GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
                                .addComponent(mailContentScrollPane, GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE))
                            .addContainerGap())
                );
                mailBoxPanelLayout.setVerticalGroup(
                    mailBoxPanelLayout.createParallelGroup()
                        .addGroup(mailBoxPanelLayout.createSequentialGroup()
                            .addComponent(mailScrollPane, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mailContentScrollPane, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
                );
            }
            tabbedPane2.addTab("Mail Box", mailBoxPanel);

            //======== settingPanel ========
            {
                settingPanel.setAlignmentY(0.0F);
                settingPanel.setAlignmentX(0.0F);

                //---- popTxtField ----
                popTxtField.setBorder(new EtchedBorder());
                popTxtField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- label5 ----
                label5.setText("POP3 Address");
                label5.setHorizontalAlignment(SwingConstants.RIGHT);
                label5.setLabelFor(popTxtField);
                label5.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- label6 ----
                label6.setText("SMTP Address");
                label6.setHorizontalAlignment(SwingConstants.RIGHT);
                label6.setLabelFor(smtpTxtField);
                label6.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- smtpTxtField ----
                smtpTxtField.setBorder(new EtchedBorder());
                smtpTxtField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- label7 ----
                label7.setText("Mail Address");
                label7.setHorizontalAlignment(SwingConstants.RIGHT);
                label7.setLabelFor(mailTxtField);
                label7.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- mailTxtField ----
                mailTxtField.setBorder(new EtchedBorder());
                mailTxtField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- label8 ----
                label8.setText("Password");
                label8.setHorizontalAlignment(SwingConstants.RIGHT);
                label8.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- popPortTxtField ----
                popPortTxtField.setBorder(new EtchedBorder());
                popPortTxtField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- label9 ----
                label9.setText("POP3 Port");
                label9.setHorizontalAlignment(SwingConstants.RIGHT);
                label9.setLabelFor(popPortTxtField);
                label9.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- label10 ----
                label10.setText("SMTP Port");
                label10.setHorizontalAlignment(SwingConstants.RIGHT);
                label10.setLabelFor(smtpPortTxtField);
                label10.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- smtpPortTxtField ----
                smtpPortTxtField.setBorder(new EtchedBorder());
                smtpPortTxtField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- saveBtn ----
                saveBtn.setText("Save");
                saveBtn.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
                saveBtn.addActionListener(e -> saveBtnActionPerformed(e));

                GroupLayout settingPanelLayout = new GroupLayout(settingPanel);
                settingPanel.setLayout(settingPanelLayout);
                settingPanelLayout.setHorizontalGroup(
                    settingPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, settingPanelLayout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addGroup(settingPanelLayout.createParallelGroup()
                                .addGroup(settingPanelLayout.createSequentialGroup()
                                    .addGroup(settingPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(label5, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label6, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(settingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(smtpTxtField, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                                        .addComponent(popTxtField, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)))
                                .addGroup(settingPanelLayout.createSequentialGroup()
                                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addGroup(settingPanelLayout.createParallelGroup()
                                        .addComponent(mailTxtField, GroupLayout.PREFERRED_SIZE, 358, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(saveBtn, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))))
                            .addGap(18, 18, 18)
                            .addGroup(settingPanelLayout.createParallelGroup()
                                .addComponent(label8, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                .addComponent(label10, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                .addComponent(label9, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(settingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(smtpPortTxtField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(popPortTxtField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(pwdTxtField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                            .addGap(73, 73, 73))
                );
                settingPanelLayout.setVerticalGroup(
                    settingPanelLayout.createParallelGroup()
                        .addGroup(settingPanelLayout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(settingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label5)
                                .addComponent(popPortTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(popTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label9))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(settingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label6)
                                .addComponent(smtpTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(smtpPortTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label10))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(settingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label7)
                                .addComponent(mailTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label8)
                                .addComponent(pwdTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(saveBtn)
                            .addContainerGap(363, Short.MAX_VALUE))
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
    private JScrollPane mailContentScrollPane;
    private JTextPane mailTxtPane;
    private JPanel settingPanel;
    private JTextField popTxtField;
    private JLabel label5;
    private JLabel label6;
    private JTextField smtpTxtField;
    private JLabel label7;
    private JTextField mailTxtField;
    private JLabel label8;
    private JTextField popPortTxtField;
    private JLabel label9;
    private JLabel label10;
    private JTextField smtpPortTxtField;
    private JButton saveBtn;
    private JPasswordField pwdTxtField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}