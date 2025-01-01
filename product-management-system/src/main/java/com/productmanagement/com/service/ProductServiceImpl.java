package com.productmanagement.com.service;

import java.util.List;

import com.productmanagement.com.entity.Product;
import com.productmanagement.com.exception.ProductNotFoundException;
import com.productmanagement.com.repository.ProductRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Inject
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.listAll();
	}

	@Override
	public Product getProductById(final Long id) throws ProductNotFoundException {
		return productRepository.findByIdOptional(id)
				.orElseThrow(() -> new ProductNotFoundException("The product doesn't exist"));
	}

	@Override
	@Transactional
	public Product saveProduct(final Product product) {
		productRepository.persistAndFlush(product);
		return product;
	}

	@Override
	@Transactional
	public Product updateProduct(final Long id, final Product product) throws ProductNotFoundException {
		Product existingProduct = getProductById(id);
		existingProduct.setDescription(product.getDescription());
		existingProduct.setName(product.getName());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setQuantity(product.getQuantity());
		productRepository.persist(existingProduct);
		return existingProduct;
	}

	@Override
	@Transactional
	public void deleteProduct(final Long id) throws ProductNotFoundException {
		productRepository.delete(getProductById(id));
	}

	@Override
	public boolean checkProductStockAvailable(final Long id, final int count) throws ProductNotFoundException {
		Product existingProduct = getProductById(id);
		return existingProduct.getQuantity() >= count ? true : false;
	}

}
