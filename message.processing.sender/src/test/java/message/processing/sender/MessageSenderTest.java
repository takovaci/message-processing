package message.processing.sender;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		List<Serializable> inList = new ArrayList<Serializable>();
		for(int i=0; i < 15; i++){
		ProductDTO p = new ProductDTO();
		p.setPrice(1.0f);
		p.setPriceUnit(PriceUnit.GBP);
		p.setType("APPLE");
		inList.add(p);
		}
		sender.sender(inList);
	}
	
}
