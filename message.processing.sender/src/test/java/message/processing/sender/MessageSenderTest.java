package message.processing.sender;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import message.processing.model.PriceUnit;
import message.processing.model.ProductDTO;

public class MessageSenderTest {

	private MessageSender sender;

	public MessageSenderTest() {
		super();
	}
	
	@Before
	public void start(){
		sender = new MessageSender();
	}
	
	@Test
	public void initalTest() throws Exception{
		ProductDTO p = new ProductDTO();
		p.setPrice(1.0f);
		p.setPriceUnit(PriceUnit.GBP);
		p.setType("APPLE");
		sender.sender(new ArrayList<Serializable>(Arrays.asList(p)));
	}
	
}
