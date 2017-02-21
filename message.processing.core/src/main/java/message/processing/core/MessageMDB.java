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

import org.apache.log4j.Logger;

import message.processing.model.MultipleProductsDTO;
import message.processing.model.PriceUnit;
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
	private List<ProductAdjustmentDTO> productAdjustmentList = new ArrayList<>();
	private int messageCounter = 0;
	private static Logger log = Logger.getLogger(MessageMDB.class);

	public void onMessage(Message msg) {
		try {
			messageCounter++;
			System.out.println("Recived [" + messageCounter + "]");
			ObjectMessage objMsg = (ObjectMessage) msg;
			if (objMsg.getObject() instanceof ProductDTO) {
				System.out.println("New ProductDTO received");
				this.addProductToMap(new MultipleProductsDTO((ProductDTO) objMsg.getObject()));
			} else if (objMsg.getObject() instanceof MultipleProductsDTO) {
				System.out.println("New MultipleProductsDTO received");
				this.addProductToMap((MultipleProductsDTO) objMsg.getObject());
			} else if (objMsg.getObject() instanceof ProductAdjustmentDTO) {
				System.out.println("New ProductAdjustmentDTO received");
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
				System.out.println("New error message received");
			}
			if (messageCounter % 10 == 0) {
				this.createShortReport();
			}
			if (messageCounter % 50 == 0) {
				this.createLongReport();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

	}

	private void createLongReport() {
		// TODO Auto-generated method stub

	}

	private void createShortReport() {
		// TODO Auto-generated method stub

	}

	private void subtractFromProducts(@NotNull final ProductDTO product) {
		// TODO Auto-generated method stub

	}

	private void multiplyProducts(@NotNull final ProductDTO product) {
		// TODO Auto-generated method stub

	}

	private void addToProducts(@NotNull final ProductDTO product) {
		List<MultipleProductsDTO> list = productMap.get(product.getType());
		if (list != null && !list.isEmpty()) {
			list.forEach(p -> {
				if (p != null && p.getProduct() != null) {
					float calculateUnitAdjustment = this.calculateUnitAdjustment(
							p.getProduct() != null ? p.getProduct().getPriceUnit() : null, product.getPriceUnit());
					p.getProduct().setPrice(p.getProduct().getPrice() + calculateUnitAdjustment * product.getPrice());
				} else {
					log.warn("Adjustment is null");
				}
			});
		}

	}

	/**
	 * 
	 * @param p1:
	 *            first product unit
	 * @param p2:
	 *            second product unit
	 * @return adjustment if we want to compare units
	 */
	private float calculateUnitAdjustment(PriceUnit p1, PriceUnit p2) {
		if (p1.compareTo(p2) == 0) {
			return 1f;
		} else if (p1.compareTo(PriceUnit.GBP) == 0) {
			return 100f;
		} else {
			return 0.01f;
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
