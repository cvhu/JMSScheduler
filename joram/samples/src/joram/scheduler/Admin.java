package scheduler;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;

import org.objectweb.joram.client.jms.Queue;
import org.objectweb.joram.client.jms.admin.AdminModule;
import org.objectweb.joram.client.jms.admin.User;
import org.objectweb.joram.client.jms.tcp.TcpConnectionFactory;

public class Admin {

    public static void main(String[] args) throws Exception {
        System.out.println(args[0]);
        
        javax.naming.Context jndiCtx = new javax.naming.InitialContext();
        ConnectionFactory cf = TcpConnectionFactory.create("localhost", 16010);
        QueueConnectionFactory qcf = TcpConnectionFactory.create("localhost", 16010);
        jndiCtx.bind("cf", cf);
        jndiCtx.bind("qcf", qcf);
        
        AdminModule.connect(cf, "root", "root");
        User.create("anonymous", "anonymous");
        
        
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String line = reader.readLine();
        while (line != null) {
            System.out.println(line);
            Queue queue = Queue.create(line);
            queue.setFreeReading();
            queue.setFreeWriting();
            jndiCtx.bind(line, queue);
            line = reader.readLine();
        }
        jndiCtx.close();
        AdminModule.disconnect();
    }

}
