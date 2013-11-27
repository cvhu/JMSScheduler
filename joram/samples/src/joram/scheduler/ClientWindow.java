package scheduler;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ClientWindow {
    
    private Client client;
    private HashMap<String, PollPanelOld> pollPanelMap;
    private ArrayList<String> comboBoxItems;
    private JFrame frame;
    private JTextField textFieldName;
    private JTextField textFieldTime;
    private JPanel panelPolls;
    private JPanel panelParticipants;
    private JPanel panelTimes;
    private JComboBox comboBoxParticipants;
    private JScrollPane scrollPaneParticipants;
    private JScrollPane scrollPaneTimes;
    private JTable tableParticipants;
    private String[] colsParticipants = {"Client Name"};
    private DefaultTableModel tableModelParticipants;
    private JTable tableTimes;
    private String[] colsTimes = {"Time Slots"};
    private DefaultTableModel tableModelTimes;
    private JPanel polls;
    
    private ArrayList<String> participants;
    private ArrayList<String> times;

    /**
     * Create the application.
     */
    public ClientWindow(Client client) {
        this.client = client;
        this.pollPanelMap = new HashMap<String, PollPanelOld>();
        initialize();
        frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    @SuppressWarnings("unchecked")
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 703, 786);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(client.getName() + " - Scheduler Client");
        comboBoxItems = (ArrayList<String>) Client.getNames().clone();
        
        JPanel panel = new JPanel();
        
        panelPolls = new JPanel();
        panelPolls.setBorder(new TitledBorder("Polls"));
        
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(panel, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(panelPolls, GroupLayout.PREFERRED_SIZE, 495, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(7, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(panelPolls, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
                        .addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE))
                    .addContainerGap())
        );
        
        JLabel lblEventName = new JLabel("Event Name");
        
        textFieldName = new JTextField();
        textFieldName.setColumns(10);
        
        panelParticipants = new JPanel();
        panelParticipants.setBorder(new TitledBorder("Participants"));
        
        panelTimes = new JPanel();
        panelTimes.setBorder(new TitledBorder("Available Times"));
        
        JButton btnSubmitPoll = new JButton("Submit Poll");
        btnSubmitPoll.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                submitForm();
            }
        });
        
        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                resetForm();
            }
        });
        
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
                                .addComponent(lblEventName, Alignment.LEADING)
                                .addComponent(textFieldName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                            .addComponent(panelParticipants, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl_panel.createSequentialGroup()
                            .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
                                .addGroup(gl_panel.createSequentialGroup()
                                    .addComponent(btnReset)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(btnSubmitPoll, 0, 0, Short.MAX_VALUE))
                                .addComponent(panelTimes, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGap(116))
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblEventName)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(panelParticipants, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(panelTimes, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnReset)
                        .addComponent(btnSubmitPoll))
                    .addContainerGap(9, Short.MAX_VALUE))
        );
        
        comboBoxParticipants = new JComboBox();
        
        JButton btnAddParticipant = new JButton("Add");
        btnAddParticipant.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                addParticipant(comboBoxParticipants.getSelectedItem(), comboBoxParticipants.getSelectedIndex());
            }
        });
        
        scrollPaneParticipants = new JScrollPane();
        GroupLayout gl_panelParticipants = new GroupLayout(panelParticipants);
        gl_panelParticipants.setHorizontalGroup(
            gl_panelParticipants.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelParticipants.createSequentialGroup()
                    .addComponent(comboBoxParticipants, 0, 101, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(btnAddParticipant, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .addGroup(Alignment.TRAILING, gl_panelParticipants.createSequentialGroup()
                    .addContainerGap(14, Short.MAX_VALUE)
                    .addComponent(scrollPaneParticipants, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
        );
        gl_panelParticipants.setVerticalGroup(
            gl_panelParticipants.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelParticipants.createSequentialGroup()
                    .addGroup(gl_panelParticipants.createParallelGroup(Alignment.BASELINE)
                        .addComponent(comboBoxParticipants, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddParticipant))
                    .addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                    .addComponent(scrollPaneParticipants, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        panelParticipants.setLayout(gl_panelParticipants);
        
        textFieldTime = new JTextField();
        textFieldTime.setColumns(10);
        
        JButton btnAddTime = new JButton("Add");
        btnAddTime.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                addTime(textFieldTime.getText());
            }
        });
        
        scrollPaneTimes = new JScrollPane();
        GroupLayout gl_panelTimes = new GroupLayout(panelTimes);
        gl_panelTimes.setHorizontalGroup(
            gl_panelTimes.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelTimes.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panelTimes.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPaneTimes, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_panelTimes.createSequentialGroup()
                            .addComponent(textFieldTime, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(btnAddTime, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
        );
        gl_panelTimes.setVerticalGroup(
            gl_panelTimes.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelTimes.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panelTimes.createParallelGroup(Alignment.BASELINE)
                        .addComponent(textFieldTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddTime))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(scrollPaneTimes, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(21, Short.MAX_VALUE))
        );
        panelTimes.setLayout(gl_panelTimes);
        panel.setLayout(gl_panel);
        frame.getContentPane().setLayout(groupLayout);
        
        tableTimes = new JTable();
        scrollPaneTimes.setViewportView(tableTimes);
        tableParticipants = new JTable();
        scrollPaneParticipants.setViewportView(tableParticipants);
        
        panelPolls.setLayout(new BorderLayout(4, 4));
        
        polls = new JPanel(new GridLayout(0, 1, 3, 3));
        panelPolls.add(new JScrollPane(polls), BorderLayout.CENTER);
        
        participants = new ArrayList<String>();
        times = new ArrayList<String>();
        
        resetForm();
    }
    
    @SuppressWarnings("unchecked")
    public void resetForm() {
        participants.clear();
        times.clear();
        
        textFieldName.setText("");
        textFieldTime.setText("");
        
        tableModelParticipants = new DefaultTableModel();
        tableModelParticipants.setColumnIdentifiers(colsParticipants);
        tableParticipants.setModel(tableModelParticipants);
        tableModelParticipants.fireTableDataChanged();
        
        tableModelTimes = new DefaultTableModel();
        tableModelTimes.setColumnIdentifiers(colsTimes);
        tableTimes.setModel(tableModelTimes);
        tableModelTimes.fireTableDataChanged();
        
        comboBoxItems = (ArrayList<String>) Client.getNames().clone();
        comboBoxItems.remove(client.getName());
        comboBoxParticipants.setModel(new DefaultComboBoxModel(comboBoxItems.toArray()));
    }
    
    @SuppressWarnings("unchecked")
    public void addParticipant(Object participant, int index) {
        comboBoxItems.remove(index);
        participants.add((String) participant);
        System.out.println(index + Arrays.asList(comboBoxItems).toString());
        comboBoxParticipants.setModel(new DefaultComboBoxModel(comboBoxItems.toArray()));
        comboBoxParticipants.repaint();
        String[] row = {participant.toString()};
        tableModelParticipants.addRow(row);
        tableParticipants.setModel(tableModelParticipants);
        tableModelParticipants.fireTableDataChanged();
    }
    
    public void addTime(String time) {
        times.add(time);
        textFieldTime.setText("");
        String[] row = {time};
        tableModelTimes.addRow(row);
        tableTimes.setModel(tableModelTimes);
        tableModelTimes.fireTableDataChanged();
    }
    
    public synchronized void addPoll(Poll poll) {
        PollPanelOld panelPoll = this.pollPanelMap.get(poll.getName());
        if (panelPoll == null) {
            boolean isOwner = client.getName().equals(poll.getByUser());
            panelPoll = new PollPanelOld(this.client, isOwner);
        }
        panelPoll.setPoll(poll);
        this.pollPanelMap.put(poll.getName(), panelPoll);
        polls.add(panelPoll);
        polls.revalidate();
        polls.repaint();
    }
    
    public synchronized void submitForm() {
        Poll poll = new Poll(textFieldName.getText(), client.getName(), participants, times);
        try {
            client.broadcastPoll(poll);
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        resetForm();
    }
}
