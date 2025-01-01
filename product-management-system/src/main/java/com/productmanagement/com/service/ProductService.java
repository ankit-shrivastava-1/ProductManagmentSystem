package com.productmanagement.com.service;

import java.util.List;

import com.productmanagement.com.entity.Product;
import com.productmanagement.com.exception.ProductNotFoundException;

/**
 * Service class for managing Product entities.
 */
public interface ProductService {

	List<Product> getAllProducts();

	Product getProductById(final Long id) throws ProductNotFoundException;

	Product saveProduct(final Product product);

	Product updateProduct(final Long id, final Product product) throws ProductNotFoundException;

	void deleteProduct(final Long id) throws ProductNotFoundException;
	
	boolean checkProductStockAvailable(final Long id, final int count) throws ProductNotFoundException;

}
