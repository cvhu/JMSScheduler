package scheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.Message;
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
    
    public Client(Integer clientId) throws IOException, NamingException, JMSException {
        this.clientName = names.get(clientId);
        this.queueMap = new HashMap<String, Queue>();
        this.senderMap = new HashMap<String, QueueSender>();
        connect();
    }
    
    public void connect() throws NamingException, JMSException{
        qcf = (QueueConnectionFactory) ictx.lookup("qcf");
        cnx = qcf.createQueueConnection();
        session = cnx.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
    public void init() throws JMSException, NamingException {
        running = true;
        cnx.start();
        broadcast(String.format("Hello world, '%s' is online!", this.clientName));
        receive();
    }
    
    public void receive() throws JMSException, NamingException {
        if (receiver == null) {
            receiver = session.createReceiver(getQueue(this.clientName));
        }
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                while (running) {
                    System.out.println(clientName + " receiving...");
                    try {
                        Message msg = receiver.receive();
                        System.out.printf("Msg received by [%s]: %s\n", clientName, ((TextMessage) msg).getText());
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
    public void broadcast(String message) throws JMSException, NamingException {
        for (String name : names) {
            sendMessageTo(name, message);
        }
    }
    
    public void sendMessageTo(String name, String message) throws JMSException, NamingException {
        System.out.printf("Sending message to '%s': %s\n", name, message);
        TextMessage msg = session.createTextMessage();
        msg.setText(message);
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
