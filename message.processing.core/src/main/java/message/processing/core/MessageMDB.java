package message.processing.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;

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

	private Map<String, List<MultipleProductsDTO>> productMap = new HashMap<>(); //for storing products
	private List<ProductAdjustmentDTO> productAdjustmentList = new ArrayList<>(); // for storing incomming adjustments
	private int messageCounter = 0; // to count how many messages we have
	private static Logger log = Logger.getLogger(MessageMDB.class);

	@EJB
	ReportBl reportBl;
	
	public void onMessage(Message msg) {
		try {
			messageCounter++;
			log.trace("Received [" + messageCounter + "]");
			ObjectMessage objMsg = (ObjectMessage) msg;
			if (objMsg.getObject() instanceof ProductDTO) {
				log.trace("New ProductDTO received");
				this.addProductToMap(new MultipleProductsDTO((ProductDTO) objMsg.getObject()));
			} else if (objMsg.getObject() instanceof MultipleProductsDTO) {
				log.trace("New MultipleProductsDTO received");
				this.addProductToMap((MultipleProductsDTO) objMsg.getObject());
			} else if (objMsg.getObject() instanceof ProductAdjustmentDTO) {
				log.trace("New ProductAdjustmentDTO received");
				ProductAdjustmentDTO pa = (ProductAdjustmentDTO) objMsg.getObject();
				productAdjustmentList.add(pa);
				switch (pa.getOperation()) {
				case ADD: {
					this.addToProducts(pa.getProduct());
					break;
				}
				case MULTIPLY: {
					this.multiplyProducts(pa.getProduct());
					break;
				}
				case SUBTRACT: {
					this.subtractFromProducts(pa.getProduct());
					break;
				}
				}
			} else {
				log.trace("New error message received");
			}
			if (messageCounter % 10 == 0) {
				// if number of messages is divided with 10 we create short report 
				reportBl.createShortReport(productMap);
			}
			if (messageCounter % 50 == 0) {
				// if number of messages is divided with 50 we create adjustmens report
				reportBl.createLongReport(productAdjustmentList);
			}
		} catch (Exception e) {
			log.error(e);
		}

	}
/**
 * method for subtracting from list of products
 * @param product - contains how much from which type and which unit we calculate
 */
	private void subtractFromProducts(@NotNull final ProductDTO product) {
		List<MultipleProductsDTO> list = productMap.get(product.getType());
		if (list != null && !list.isEmpty()) {
			list.forEach(p -> {
				if (p != null && p.getProduct() != null) {
					float calculateUnitAdjustment = Util.calculateUnitAdjustment(
							p.getProduct() != null ? p.getProduct().getPriceUnit() : null, product.getPriceUnit());
					p.getProduct().setPrice(p.getProduct().getPrice() - (calculateUnitAdjustment * product.getPrice()));
				} else {
					log.warn("Adjustment is null");
				}
			});
		}

	}
/**
 * method for multiplying products price
 * @param product - contains how much from which type and which unit we calculate
 */
	private void multiplyProducts(@NotNull final ProductDTO product) {
		List<MultipleProductsDTO> list = productMap.get(product.getType());
		if (list != null && !list.isEmpty()) {
			list.forEach(p -> {
				if (p != null && p.getProduct() != null&& p.getProduct().getPrice() != 0.0f){
					p.getProduct().setPrice(p.getProduct().getPrice() * product.getPrice());
				}
				});
		}
	}
/**
 * method for adding price to list of products of some type
 * @param product - contains how much from which type and which unit we calculate
 */
	private void addToProducts(@NotNull final ProductDTO product) {
		List<MultipleProductsDTO> list = productMap.get(product.getType());
		if (list != null && !list.isEmpty()) {
			list.forEach(p -> {
				if (p != null && p.getProduct() != null) {
					float calculateUnitAdjustment = Util.calculateUnitAdjustment(
							p.getProduct() != null ? p.getProduct().getPriceUnit() : null, product.getPriceUnit());
					p.getProduct().setPrice(p.getProduct().getPrice() + (calculateUnitAdjustment * product.getPrice()));
				} else {
					log.warn("Adjustment is null");
				}
			});
		}

	}


/**
 * method for simplify adding objects to map acording to their type
 * @param p, object multiple product which we want to add to map
 */
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
