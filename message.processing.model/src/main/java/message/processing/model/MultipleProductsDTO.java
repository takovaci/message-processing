package message.processing.model;

import java.io.Serializable;

public class MultipleProductsDTO implements Serializable {

	private static final long serialVersionUID = 4276643985840262445L;
	private int numberOfProducts = 1; //default value if we use it just for one product
	private ProductDTO product; 
	
	public MultipleProductsDTO() {
		super();
	}
	public MultipleProductsDTO(ProductDTO product) {
		super();
		this.product = product;
	}
	public int getNumberOfProducts() {
		return numberOfProducts;
	}
	public void setNumberOfProducts(int numberOfProducts) {
		this.numberOfProducts = numberOfProducts;
	}
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
}
