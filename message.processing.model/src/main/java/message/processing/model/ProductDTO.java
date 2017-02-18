package message.processing.model;

import java.io.Serializable;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = -2562642545915842968L;

	private String type; //APPLES, ...
	private float price; //we need two variables, so we can do math operations
	private PriceUnit priceUnit; //define unit to price
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public PriceUnit getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(PriceUnit priceUnit) {
		this.priceUnit = priceUnit;
	}
	
	
	
}
