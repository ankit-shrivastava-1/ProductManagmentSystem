package com.productmanagement.resource;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.productmanagement.entity.Product;
import com.productmanagement.exception.ProductNotFoundException;
import com.productmanagement.service.ProductService;

import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path(ProductResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

	public static final String RESOURCE_PATH = "/api/v1";

	private final ProductService productService;

	@Inject
	public ProductResource(ProductService productService) {
		this.productService = productService;
	}

	@POST
	@Path("/products")
	@Operation(summary = "Adds a product", description = "Creates a product and persists into database")
	@Transactional
	public Product createProduct(@Valid Product product) {
		return productService.saveProduct(product);
	}

	@GET
	@Path("/products")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Gets all products", description = "Lists all available products")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GET
	@Path("/products/{id}")
	@Operation(summary = "Gets a product", description = "Retrieves a product by id")
	public Product getProduct(@PathParam("id") Long id) throws ProductNotFoundException {
		return productService.getProductById(id);
	}

	@PUT
	@Path("/products/{id}")
	@Operation(summary = "Updates a product", description = "Updates an existing product by id")
	public Product updateProduct(@PathParam("id") Long id, @Valid Product product) throws ProductNotFoundException {
		return productService.updateProduct(id, product);
	}

	@DELETE
	@Path("/products/{id}")
	@Operation(summary = "Deletes a product", description = "Deletes a product by id")
	public Response deleteProduct(@PathParam("id") Long id) throws ProductNotFoundException {
		productService.deleteProduct(id);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@GET
	@Path("/checkProductStock/{id}/{count}")
	@Operation(summary = "Check Product Quantity", description = "Check the requested number of product available in the stock")
	public Response getProductStockAvailabilty(@PathParam("id") Long id, @PathParam("count") int count)
			throws ProductNotFoundException {
		return Response.ok("Product Stock Available : " + productService.checkProductStockAvailable(id, count),
				MediaType.APPLICATION_JSON_TYPE).build();

	}

	@GET
	@Path("/ascProductsByPrice")
	@Operation(summary = "Gets products by increasing price", description = "Retrieves products by increasing price")
	public List<Product> getProductAscendingByPrice() {
		return Product.findAll(Sort.ascending("price")).list();
	}

}
