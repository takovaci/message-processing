package message.processing.model;

import java.io.Serializable;

public class ProductAdjustmentDTO implements Serializable {

	private static final long serialVersionUID = -4729785641514350612L;
	
	private Operation operation; //we choose whitch operation we will do on
	private ProductDTO product; // which product
	
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
}
