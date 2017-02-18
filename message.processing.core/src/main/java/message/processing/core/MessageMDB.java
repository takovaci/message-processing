package message.processing.core;

import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.ejb.ActivationConfigProperty;

import message.processing.model.MultipleProductsDTO;
import message.processing.model.ProductAdjustmentDTO;
import message.processing.model.ProductDTO;

@MessageDriven(mappedName = "jms/queue/MessageProcessingQueue", activationConfig = {
		@ActivationConfigProperty(propertyName = "messagingType", propertyValue = "javax.jms.MessageListener"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jboss/exported/jms/queue/MessageProcessingQueue"),
		@ActivationConfigProperty(propertyName = "ConnectionFactoryName", propertyValue = "ConnectionFactory"),
		@ActivationConfigProperty(propertyName = "MaxPoolSize", propertyValue = "1"),
		@ActivationConfigProperty(propertyName = "MaxMessages", propertyValue = "1"),
		@ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true") })
public class MessageMDB implements MessageListener {

	public void onMessage(Message msg) {
		try {
			ObjectMessage objMsg = (ObjectMessage) msg;
			if (objMsg.getObject() instanceof ProductDTO) {
				System.out.println("New ProductDTO received");
			} else if (objMsg.getObject() instanceof MultipleProductsDTO) {
				System.out.println("New MultipleProductsDTO received");
			} else if (objMsg.getObject() instanceof ProductAdjustmentDTO) {
				System.out.println("New ProductAdjustmentDTO received");
			} else {
				System.out.println("New error message received");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
