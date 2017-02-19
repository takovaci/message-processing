package message.processing.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.validation.constraints.NotNull;

import message.processing.model.MultipleProductsDTO;
import message.processing.model.ProductAdjustmentDTO;
import message.processing.model.ProductDTO;

@MessageDriven(mappedName = "jms/queue/MessageProcessingQueue", activationConfig = {
		@ActivationConfigProperty(propertyName = "messagingType", propertyValue = "javax.jms.MessageListener"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jboss/exported/jms/queue/MessageProcessingQueue"),
		@ActivationConfigProperty(propertyName = "ConnectionFactoryName", propertyValue = "ConnectionFactory"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1"),
		@ActivationConfigProperty(propertyName = "MaxMessages", propertyValue = "1"),
		@ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true") })
public class MessageMDB implements MessageListener {

	private Map<String, List<MultipleProductsDTO>> productMap = new HashMap<>();

	public void onMessage(Message msg) {
		try {
			System.out.println("ProductMap size: [" + productMap.values()== null?0:productMap.values().size() + "]");
			ObjectMessage objMsg = (ObjectMessage) msg;
			if (objMsg.getObject() instanceof ProductDTO) {
				System.out.println("New ProductDTO received");
				this.addProductToMap(new MultipleProductsDTO((ProductDTO) objMsg.getObject()));
			} else if (objMsg.getObject() instanceof MultipleProductsDTO) {
				System.out.println("New MultipleProductsDTO received");
				this.addProductToMap((MultipleProductsDTO) objMsg.getObject());
			} else if (objMsg.getObject() instanceof ProductAdjustmentDTO) {
				System.out.println("New ProductAdjustmentDTO received");
			} else {
				System.out.println("New error message received");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addProductToMap(@NotNull MultipleProductsDTO p) {
		if (p.getProduct() != null && p.getProduct().getType() != null) {
			if (productMap.containsKey(p.getProduct().getType().trim().toUpperCase())) {
				productMap.get(p.getProduct().getType().trim().toUpperCase()).add(p);
			} else {
				productMap.put(p.getProduct().getType().trim().toUpperCase(), new ArrayList<>(Arrays.asList(p)));
			}
		}

	}

}
