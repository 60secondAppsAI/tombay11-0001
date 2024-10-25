package com.tombay11.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.tombay11.dao.GenericDAO;
import com.tombay11.service.GenericService;
import com.tombay11.service.impl.GenericServiceImpl;
import com.tombay11.dao.ProductDAO;
import com.tombay11.domain.Product;
import com.tombay11.dto.ProductDTO;
import com.tombay11.dto.ProductSearchDTO;
import com.tombay11.dto.ProductPageDTO;
import com.tombay11.dto.ProductConvertCriteriaDTO;
import com.tombay11.dto.common.RequestDTO;
import com.tombay11.dto.common.ResultDTO;
import com.tombay11.service.ProductService;
import com.tombay11.util.ControllerUtils;





@Service
public class ProductServiceImpl extends GenericServiceImpl<Product, Integer> implements ProductService {

    private final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductDAO productDao;

	


	@Override
	public GenericDAO<Product, Integer> getDAO() {
		return (GenericDAO<Product, Integer>) productDao;
	}
	
	public List<Product> findAll () {
		List<Product> products = productDao.findAll();
		
		return products;	
		
	}

	public ResultDTO addProduct(ProductDTO productDTO, RequestDTO requestDTO) {

		Product product = new Product();

		product.setProductId(productDTO.getProductId());


		product.setName(productDTO.getName());


		product.setDescription(productDTO.getDescription());


		product.setPrice(productDTO.getPrice());


		product.setQuantity(productDTO.getQuantity());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		product = productDao.save(product);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Product> getAllProducts(Pageable pageable) {
		return productDao.findAll(pageable);
	}

	public Page<Product> getAllProducts(Specification<Product> spec, Pageable pageable) {
		return productDao.findAll(spec, pageable);
	}

	public ResponseEntity<ProductPageDTO> getProducts(ProductSearchDTO productSearchDTO) {
	
			Integer productId = productSearchDTO.getProductId(); 
 			String name = productSearchDTO.getName(); 
 			String description = productSearchDTO.getDescription(); 
  			Integer quantity = productSearchDTO.getQuantity(); 
 			String sortBy = productSearchDTO.getSortBy();
			String sortOrder = productSearchDTO.getSortOrder();
			String searchQuery = productSearchDTO.getSearchQuery();
			Integer page = productSearchDTO.getPage();
			Integer size = productSearchDTO.getSize();

	        Specification<Product> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, productId, "productId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, quantity, "quantity"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Product> products = this.getAllProducts(spec, pageable);
		
		//System.out.println(String.valueOf(products.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(products.getTotalPages()));
		
		List<Product> productsList = products.getContent();
		
		ProductConvertCriteriaDTO convertCriteria = new ProductConvertCriteriaDTO();
		List<ProductDTO> productDTOs = this.convertProductsToProductDTOs(productsList,convertCriteria);
		
		ProductPageDTO productPageDTO = new ProductPageDTO();
		productPageDTO.setProducts(productDTOs);
		productPageDTO.setTotalElements(products.getTotalElements());
		return ResponseEntity.ok(productPageDTO);
	}

	public List<ProductDTO> convertProductsToProductDTOs(List<Product> products, ProductConvertCriteriaDTO convertCriteria) {
		
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		
		for (Product product : products) {
			productDTOs.add(convertProductToProductDTO(product,convertCriteria));
		}
		
		return productDTOs;

	}
	
	public ProductDTO convertProductToProductDTO(Product product, ProductConvertCriteriaDTO convertCriteria) {
		
		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setProductId(product.getProductId());

	
		productDTO.setName(product.getName());

	
		productDTO.setDescription(product.getDescription());

	
		productDTO.setPrice(product.getPrice());

	
		productDTO.setQuantity(product.getQuantity());

	

		
		return productDTO;
	}

	public ResultDTO updateProduct(ProductDTO productDTO, RequestDTO requestDTO) {
		
		Product product = productDao.getById(productDTO.getProductId());

		product.setProductId(ControllerUtils.setValue(product.getProductId(), productDTO.getProductId()));

		product.setName(ControllerUtils.setValue(product.getName(), productDTO.getName()));

		product.setDescription(ControllerUtils.setValue(product.getDescription(), productDTO.getDescription()));

		product.setPrice(ControllerUtils.setValue(product.getPrice(), productDTO.getPrice()));

		product.setQuantity(ControllerUtils.setValue(product.getQuantity(), productDTO.getQuantity()));



        product = productDao.save(product);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ProductDTO getProductDTOById(Integer productId) {
	
		Product product = productDao.getById(productId);
			
		
		ProductConvertCriteriaDTO convertCriteria = new ProductConvertCriteriaDTO();
		return(this.convertProductToProductDTO(product,convertCriteria));
	}







}
