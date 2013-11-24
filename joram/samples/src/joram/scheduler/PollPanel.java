package scheduler;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class PollPanel extends JPanel {

    private Poll poll;
    private JLabel lblPollName;
    private JLabel lblByUser;
    private JPanel panelParticipants;
    private JPanel panelTimes;
    private JComboBox comboBoxTime;
    private JButton btnSubmit;
    private JLabel lblTime;
    private JScrollPane scrollPaneParticipants;
    private JScrollPane scrollPaneTimes;
    private JTable tableParticipants;
    private String[] colsParticipants = {"Name", "Vote"};
    private DefaultTableModel tableModelParticipants;
    private JTable tableTimes;
    private String[] colsTimes = {"Time", "Votes"};
    private DefaultTableModel tableModelTimes;

    /**
     * Create the panel.
     */
    public PollPanel() {
        init();
    }

    private void init() {

        lblPollName = new JLabel("Poll Name");
        
        lblByUser = new JLabel("by user");
        
        panelParticipants = new JPanel();
        
        panelTimes = new JPanel();
        
        comboBoxTime = new JComboBox();
        
        btnSubmit = new JButton("Submit");
        
        lblTime = new JLabel("Choose a meeting time: ");
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
        
        scrollPaneTimes = new JScrollPane();
        GroupLayout gl_panelTimes = new GroupLayout(panelTimes);
        gl_panelTimes.setHorizontalGroup(
            gl_panelTimes.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelTimes.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPaneTimes, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_panelTimes.setVerticalGroup(
            gl_panelTimes.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, gl_panelTimes.createSequentialGroup()
                    .addContainerGap(7, Short.MAX_VALUE)
                    .addComponent(scrollPaneTimes, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
        );
        panelTimes.setLayout(gl_panelTimes);
        
        scrollPaneParticipants = new JScrollPane();
        GroupLayout gl_panelParticipants = new GroupLayout(panelParticipants);
        gl_panelParticipants.setHorizontalGroup(
            gl_panelParticipants.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelParticipants.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPaneParticipants, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_panelParticipants.setVerticalGroup(
            gl_panelParticipants.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, gl_panelParticipants.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPaneParticipants, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        panelParticipants.setLayout(gl_panelParticipants);
        setLayout(groupLayout);
        
        tableTimes = new JTable();
        scrollPaneTimes.setViewportView(tableTimes);
        tableParticipants = new JTable();
        scrollPaneParticipants.setViewportView(tableParticipants);
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
        lblPollName.setText(poll.getName());
        lblByUser.setText("by " + poll.getByUser());
        comboBoxTime.setModel(new DefaultComboBoxModel(poll.getTimes().keySet().toArray()));
        comboBoxTime.repaint();
        setParticipants();
        setTimes();
    }
    
    public void setParticipants() {
        tableModelParticipants = new DefaultTableModel();
        tableModelParticipants.setColumnIdentifiers(colsParticipants);
        HashMap<String, String> participants = poll.getParticipants();
        for (Entry<String, String> entry : participants.entrySet()) {
            String[] row = {entry.getKey(), entry.getValue()};
            tableModelParticipants.addRow(row);
        }
        tableParticipants.setModel(tableModelParticipants);
        tableModelParticipants.fireTableDataChanged();
    }
    
    public void setTimes() {
        tableModelTimes = new DefaultTableModel();
        tableModelTimes.setColumnIdentifiers(colsTimes);
        HashMap<String, Integer> times = poll.getTimes();
        for (Entry<String, Integer> entry : times.entrySet()) {
            String[] row = {entry.getKey(), entry.getValue().toString()};
            tableModelTimes.addRow(row);
        }
        tableTimes.setModel(tableModelTimes);
        tableModelTimes.fireTableDataChanged();
    }
}
