package message.processing.model;

import java.io.Serializable;

public class MultipleProductsDTO implements Serializable {

	private static final long serialVersionUID = 4276643985840262445L;
	private int numberOfProducts;
	private ProductDTO product;
	
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
