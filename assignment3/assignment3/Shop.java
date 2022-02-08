package assignment3;

public interface Shop {
	public void buy(Product product) throws InterruptedException;
	public Product sell() throws InterruptedException;
}
