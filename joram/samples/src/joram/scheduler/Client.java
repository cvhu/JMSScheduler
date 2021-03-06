package scheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.objectweb.joram.client.jms.Queue;


public class Client {
    
    private static Context ictx = null;
    private static ArrayList<String> names;
   
    private boolean running;
    private String clientName;
    private HashMap<String, Queue> queueMap;
    private HashMap<String, QueueSender> senderMap;
    private QueueReceiver receiver;
    private QueueConnectionFactory qcf;
    private QueueConnection cnx;
    private QueueSession session;
    private ClientWindow clientWindow;
    
    public Client(Integer clientId) throws IOException, NamingException, JMSException {
        this.clientName = names.get(clientId);
        this.queueMap = new HashMap<String, Queue>();
        this.senderMap = new HashMap<String, QueueSender>();
        connect();
    }
    
    public String getName() {
        return this.clientName;
    }
    
    public static ArrayList<String> getNames() {
        return names;
    }
    
    public void connect() throws NamingException, JMSException{
        this.qcf = (QueueConnectionFactory) ictx.lookup("qcf");
        this.cnx = qcf.createQueueConnection();
        this.session = cnx.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
    public void init() throws JMSException, NamingException {
        this.running = true;
        this.cnx.start();
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                clientWindow = new ClientWindow(Client.this);
            }
        }).start();
        receive();
    }
    
    public void receive() throws JMSException, NamingException {
        if (receiver == null) {
            QueueSession receiverSession = cnx.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            receiver = receiverSession.createReceiver(getQueue(this.clientName));
        }
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                while (running) {
                    System.out.println(clientName + " receiving...");
                    try {
                        Message msg = receiver.receive();
                        if (msg instanceof TextMessage) {
                            System.out.printf("Msg received by [%s]: %s\n", clientName, ((TextMessage) msg).getText());
                        } else if (msg instanceof ObjectMessage) {
                            clientWindow.addPoll((Poll) ((ObjectMessage) msg).getObject());
                        } else {
                            System.out.printf("Msg received by [%s]: %s\n", clientName, msg);
                        }
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
    public void broadcastPoll(Poll poll) throws JMSException, NamingException {
        for (String participant : poll.getParticipants().keySet()) {
            if (!participant.equals(this.clientName)) {
                sendMessageTo(participant, session.createObjectMessage(poll));
            }
        }
        sendMessageTo(poll.getByUser(), session.createObjectMessage(poll));
    }
    
    public void broadcastText(String text) throws JMSException, NamingException {
        broadcast(session.createTextMessage(text));
    }
    
    public void broadcast(Message msg) throws JMSException, NamingException {
        for (String name : names) {
            if (!name.equals(this.clientName)) {
                sendMessageTo(name, msg);
            }
        }
    }
    
    public void sendMessageTo(String name, Message msg) throws JMSException, NamingException {
        System.out.printf("Sending message to '%s': %s\n", name, msg);
        getSender(name).send(msg);
    }
    
    public QueueSender getSender(String name) throws JMSException, NamingException {
        QueueSender sender = senderMap.get(name);
        if (sender == null) {
            sender = session.createSender(getQueue(name));
            senderMap.put(name, sender);
        }
        return sender;
    }
    
    public Queue getQueue(String name) throws NamingException {
        Queue queue = queueMap.get(name);
        if (queue == null) {
            queue = (Queue) ictx.lookup(name);
            queueMap.put(name, queue);
        }
        return queue;
    }
    
    public void terminate() throws JMSException {
        running = false;
        cnx.close();
    }
    
    public static void terminateAll() throws NamingException {
        ictx.close();
    }
    

    public static void main(String[] args) throws Exception{
        if (ictx == null) {
            ictx = new InitialContext();
        }
        
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String line = reader.readLine();
        names = new ArrayList<String>();
        ArrayList<Client> clients = new ArrayList<Client>();
        int clientId = 0;
        while (line != null) {
            System.out.println(line);
            names.add(line);
            clients.add(new Client(clientId));
            clientId++;
            line = reader.readLine();
        }
        reader.close();
        
        for (final Client client : clients) {
            new Thread(new Runnable() {
                
                @Override
                public void run() {
                    try {
                        client.init();
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (NamingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
