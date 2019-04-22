/*
 * Created by JFormDesigner on Mon Apr 08 17:24:59 CST 2019
 */

package view;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Date;

/**
 * @author Chief
 */
public class MailConnectionView extends JFrame {

    public MailConnectionView() {
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
        popTxtField = new JTextField();
        label5 = new JLabel();
        label6 = new JLabel();
        smtpTxtField = new JTextField();
        label7 = new JLabel();
        mailTxtField = new JTextField();
        pwdTxtField = new JTextField();
        label8 = new JLabel();
        popPortTxtField = new JTextField();
        label9 = new JLabel();
        label10 = new JLabel();
        smtpPortTxtField = new JTextField();

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
                            {"aa", "cc", null},
                            {"bb", "dd", null},
                        },
                        new String[] {
                            "From", "Subject", "time"
                        }
                    ) {
                        Class<?>[] columnTypes = new Class<?>[] {
                            String.class, String.class, Date.class
                        };
                        boolean[] columnEditable = new boolean[] {
                            false, false, false
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
                        cm.getColumn(0).setResizable(false);
                        cm.getColumn(0).setPreferredWidth(200);
                        cm.getColumn(1).setResizable(false);
                        cm.getColumn(1).setPreferredWidth(650);
                        cm.getColumn(2).setResizable(false);
                        cm.getColumn(2).setPreferredWidth(137);
                    }
                    mailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    mailTable.setAutoCreateRowSorter(true);
                    mailTable.setFocusable(false);
                    mailTable.setFillsViewportHeight(true);
                    mailTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    mailScrollPane.setViewportView(mailTable);
                }

                GroupLayout mailBoxPanelLayout = new GroupLayout(mailBoxPanel);
                mailBoxPanel.setLayout(mailBoxPanelLayout);
                mailBoxPanelLayout.setHorizontalGroup(
                    mailBoxPanelLayout.createParallelGroup()
                        .addComponent(mailScrollPane, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 1018, GroupLayout.PREFERRED_SIZE)
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

                //---- pwdTxtField ----
                pwdTxtField.setBorder(new EtchedBorder());
                pwdTxtField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

                //---- label8 ----
                label8.setText("Password");
                label8.setHorizontalAlignment(SwingConstants.RIGHT);
                label8.setLabelFor(pwdTxtField);
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

                GroupLayout settingPanelLayout = new GroupLayout(settingPanel);
                settingPanel.setLayout(settingPanelLayout);
                settingPanelLayout.setHorizontalGroup(
                    settingPanelLayout.createParallelGroup()
                        .addGroup(settingPanelLayout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addGroup(settingPanelLayout.createParallelGroup()
                                .addGroup(settingPanelLayout.createSequentialGroup()
                                    .addGroup(settingPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(label5, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label6, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(settingPanelLayout.createParallelGroup()
                                        .addComponent(smtpTxtField, GroupLayout.PREFERRED_SIZE, 358, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(popTxtField))
                                    .addGap(12, 12, 12)
                                    .addGroup(settingPanelLayout.createParallelGroup()
                                        .addComponent(label10, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label9, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)))
                                .addGroup(settingPanelLayout.createSequentialGroup()
                                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(mailTxtField, GroupLayout.PREFERRED_SIZE, 358, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(label8, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addGroup(settingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(pwdTxtField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(smtpPortTxtField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(popPortTxtField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                            .addContainerGap(64, Short.MAX_VALUE))
                );
                settingPanelLayout.setVerticalGroup(
                    settingPanelLayout.createParallelGroup()
                        .addGroup(settingPanelLayout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(settingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label5)
                                .addComponent(label9)
                                .addComponent(popTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(popPortTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(settingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label6)
                                .addComponent(smtpTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label10)
                                .addComponent(smtpPortTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(settingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label7)
                                .addComponent(label8)
                                .addComponent(pwdTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(mailTxtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(406, Short.MAX_VALUE))
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
    private JTextField popTxtField;
    private JLabel label5;
    private JLabel label6;
    private JTextField smtpTxtField;
    private JLabel label7;
    private JTextField mailTxtField;
    private JTextField pwdTxtField;
    private JLabel label8;
    private JTextField popPortTxtField;
    private JLabel label9;
    private JLabel label10;
    private JTextField smtpPortTxtField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}