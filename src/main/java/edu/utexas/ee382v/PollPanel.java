package edu.utexas.ee382v;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class PollPanel extends JPanel {

    /**
     * Create the panel.
     */
    public PollPanel() {
        
        JLabel lblPollName = new JLabel("Poll Name");
        
        JLabel lblByUser = new JLabel("by user");
        
        JPanel panelParticipants = new JPanel();
        
        JPanel panelTimes = new JPanel();
        
        JComboBox comboBoxTime = new JComboBox();
        
        JButton btnSubmit = new JButton("Submit");
        
        JLabel lblTime = new JLabel("Choose a meeting time: ");
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(lblTime)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(comboBoxTime, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(btnSubmit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblPollName)
                                .addComponent(lblByUser))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(panelParticipants, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(panelTimes, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)))
                    .addGap(20))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(lblPollName)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblByUser))
                        .addComponent(panelParticipants, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelTimes, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblTime)
                        .addComponent(comboBoxTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(11, Short.MAX_VALUE))
        );
        setLayout(groupLayout);

    }
}
