/*
 * Created by JFormDesigner on Mon Apr 22 20:57:54 CST 2019
 */

package view;

import model.MailModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author alex
 */
public class MailContentView extends JFrame {
    public MailContentView() {
        initComponents();
    }

    public MailContentView(MailModel mail) {
        initComponents();
        lblSubject.setText(mail.getSubject());
        lblFrom.setText(mail.getFrom());
        lblDate.setText(mail.getDate().toString());
        txtAreaContent.append(mail.getContent());


    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - alex
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label6 = new JLabel();
        lblSubject = new JLabel();
        lblFrom = new JLabel();
        lblDate = new JLabel();
        scrollPane1 = new JScrollPane();
        txtAreaContent = new JTextArea();
        buttonBar = new JPanel();
        btnDelete = new JButton();
        btnClose = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {

                //---- label1 ----
                label1.setText("Subject: ");

                //---- label2 ----
                label2.setText("From: ");

                //---- label3 ----
                label3.setText("Date: ");

                //---- label6 ----
                label6.setText("Content:");

                //======== scrollPane1 ========
                {

                    //---- txtAreaContent ----
                    txtAreaContent.setLineWrap(true);
                    scrollPane1.setViewportView(txtAreaContent);
                }

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(label3)
                                        .addComponent(label1)
                                        .addComponent(label2))
                                    .addGap(18, 18, 18)
                                    .addGroup(contentPanelLayout.createParallelGroup()
                                        .addComponent(lblSubject, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblFrom, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblDate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addComponent(label6)
                                    .addGap(18, 18, 18)
                                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)))
                            .addContainerGap())
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1)
                                .addComponent(lblSubject))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(lblFrom))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label3)
                                .addComponent(lblDate))
                            .addGap(18, 18, 18)
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addComponent(label6)
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
                            .addContainerGap())
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- btnDelete ----
                btnDelete.setText("Delete");
                buttonBar.add(btnDelete, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- btnClose ----
                btnClose.setText("Close");
                buttonBar.add(btnClose, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - alex
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label6;
    private JLabel lblSubject;
    private JLabel lblFrom;
    private JLabel lblDate;
    private JScrollPane scrollPane1;
    private JTextArea txtAreaContent;
    private JPanel buttonBar;
    private JButton btnDelete;
    private JButton btnClose;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
