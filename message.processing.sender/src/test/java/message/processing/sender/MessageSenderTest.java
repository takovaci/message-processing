package message.processing.sender;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import message.processing.model.MultipleProductsDTO;
import message.processing.model.Operation;
import message.processing.model.PriceUnit;
import message.processing.model.ProductAdjustmentDTO;
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
	
	@Test
	public void testMultipleProducts() throws Exception{
		List<Serializable> inList = new ArrayList<Serializable>();
		for(int i=0; i < 25; i++){
			MultipleProductsDTO mp = new MultipleProductsDTO();
			mp.setNumberOfProducts(3);
			ProductDTO p = new ProductDTO();
			p.setPrice(3.2f);
			p.setPriceUnit(PriceUnit.GBP);
			p.setType("BANNANA");
			mp.setProduct(p);
			inList.add(mp);
		}
		sender.sender(inList);
	}
	
	@Test
	public void testAdjustments() throws Exception{
		List<Serializable> inList = new ArrayList<Serializable>();
		for(int i=0; i<7; i++){
			ProductAdjustmentDTO pa = new ProductAdjustmentDTO();
			pa.setOperation(Operation.ADD);
			ProductDTO p = new ProductDTO();
			p.setPrice(1.1f);
			p.setType("APPLES");
			p.setPriceUnit(PriceUnit.GBP);
			pa.setProduct(p);
			inList.add(pa);
			ProductAdjustmentDTO pa2 = new ProductAdjustmentDTO();
			pa2.setOperation(Operation.MULTIPLY);
			ProductDTO p2 = new ProductDTO();
			p2.setPrice(1.5f);
			p2.setType("BANNANA");
			p2.setPriceUnit(PriceUnit.GBP);
			pa2.setProduct(p2);
			inList.add(pa2);
			ProductAdjustmentDTO pa3 = new ProductAdjustmentDTO();
			pa3.setOperation(Operation.SUBTRACT);
			ProductDTO p3 = new ProductDTO();
			p3.setPrice(1.4f);
			p3.setType("BANNANA");
			p3.setPriceUnit(PriceUnit.GBP);
			pa3.setProduct(p3);
			inList.add(pa3);
		}
		sender.sender(inList);
	}
	
}
