package edu.utexas.ee382v;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ClientWindow {

    private JFrame frame;
    private JTextField textFieldName;
    private JTextField textFieldTime;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientWindow window = new ClientWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ClientWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 703, 786);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        
        JPanel panelPolls = new JPanel();
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
        
        JLabel lblParticipants = new JLabel("Participants");
        
        JPanel panelParticipants = new JPanel();
        
        JLabel lblAvailableSlots = new JLabel("Available Slots");
        
        textFieldTime = new JTextField();
        textFieldTime.setColumns(10);
        
        JButton button = new JButton("New button");
        
        JButton btnAdd = new JButton("Add");
        
        JPanel panelTimes = new JPanel();
        
        JButton btnSubmitPoll = new JButton("Submit Poll");
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                            .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblEventName)
                                .addComponent(lblParticipants)
                                .addComponent(lblAvailableSlots)
                                .addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
                                    .addComponent(textFieldTime, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                    .addComponent(btnAdd))
                                .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
                                    .addComponent(textFieldName, Alignment.LEADING)
                                    .addComponent(panelParticipants, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(button))
                        .addGroup(gl_panel.createSequentialGroup()
                            .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
                                .addComponent(btnSubmitPoll)
                                .addComponent(panelTimes, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(129, Short.MAX_VALUE))))
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblEventName)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblParticipants)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(panelParticipants, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(lblAvailableSlots)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                            .addComponent(textFieldTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(button))
                        .addComponent(btnAdd))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(panelTimes, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnSubmitPoll)
                    .addContainerGap(8, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        frame.getContentPane().setLayout(groupLayout);
    }
}
