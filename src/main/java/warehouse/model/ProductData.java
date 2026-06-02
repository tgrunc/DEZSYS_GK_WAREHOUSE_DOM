package warehouse.model;

public class ProductData {

	private String productID;
	private String productName;
	private String productCategory;
	private double productQuantity;

	public ProductData() {
	}

	public ProductData(String productID, String productName, String productCategory, double productQuantity) {
		this.productID = productID;
		this.productName = productName;
		this.productCategory = productCategory;
		this.productQuantity = productQuantity;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public double getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(double productQuantity) {
		this.productQuantity = productQuantity;
	}

	@Override
	public String toString() {
		return String.format("Product Info: ProductID = %s, ProductName = %s, ProductCategory = %s, ProductQuantity = %4.1f",
			productID, productName, productCategory, productQuantity );
	}
}
