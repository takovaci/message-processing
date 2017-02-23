package message.processing.sender;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageSender {

	public final static String JMS_CONNECTION_FACTORY_JNDI = "jms/RemoteConnectionFactory";
	public final static String JMS_QUEUE_JNDI = "jms/queue/MessageProcessingQueue";
	public final static String WILDFLY_REMOTING_URL = "http-remoting://localhost:8080";
	public final static String JMS_USERNAME = "test";
	public final static String JMS_PASSWORD = "test123";
	
	private QueueConnectionFactory qconFactory;
	private QueueConnection qcon;
	private QueueSession qsession;
	private QueueSender qsender;
	private Queue queue;
/**
 * 
 * @param serList list of objects which sender will send to jms queue
 * @throws Exception
 */
	public void sender(List<Serializable> serList) throws Exception {

		try {
			InitialContext ic = getInitialContext();
			qconFactory = (QueueConnectionFactory) ic.lookup(JMS_CONNECTION_FACTORY_JNDI);
			qcon = qconFactory.createQueueConnection(JMS_USERNAME, JMS_PASSWORD);
			qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queue = (Queue) ic.lookup(JMS_QUEUE_JNDI);
			qsender = qsession.createSender(queue);
			qcon.start();
			for (Serializable s : serList) {
				Message msg = qsession.createObjectMessage(s); // object
																// message
				qsender.send(msg);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		finally {
			qcon.close(); // free all resources (more)
		}
	}

	private static InitialContext getInitialContext() throws NamingException {
		InitialContext context = null;
		try {
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
			props.put(Context.PROVIDER_URL, WILDFLY_REMOTING_URL); // NOTICE:
																	// "http-remoting"
																	// and port
																	// "8080"
			 props.put(Context.SECURITY_PRINCIPAL, JMS_USERNAME);
			 props.put(Context.SECURITY_CREDENTIALS, JMS_PASSWORD);
			context = new InitialContext(props);
			System.out.println("\n\tGot initial Context: " + context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
	}
}
