package com.example.servercomputer.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.servercomputer.entity.Category;
import com.example.servercomputer.entity.Product;
import com.example.servercomputer.repository.CategoryRepository;
import com.example.servercomputer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.servercomputer.dto.ProductDTO;
import com.example.servercomputer.service.ProductService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/product")
public class ProductAPI {
	@Autowired
	private ProductService productService;
	private final int PAGE_SIZE = 6;
	private final int RELATED_SIZE = 4;

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepo;
	@GetMapping
	public Page<ProductDTO> getPageProduct(@RequestParam(value = "page", required = false) Integer pageNumber){
		if(pageNumber == null || pageNumber==0) pageNumber=1;
		return productService.findAllPagination(pageNumber, PAGE_SIZE);
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/page")
	public Page<ProductDTO> getPageProduct2(@RequestParam(value = "page", required = false) Integer pageNumber){
		if(pageNumber == null || pageNumber==0) pageNumber=1;
		return productService.findAllPagination(pageNumber, PAGE_SIZE);
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/all")
	public List<ProductDTO> getAllList(){
		return productService.findAllProduct();
	}
	
	@GetMapping("/{id}")
	public ProductDTO getById(@PathVariable Long id) {
		return productService.findOneById(id);
	}
	
	@GetMapping("/")
	public Page<ProductDTO> getByCategoryId(
			@RequestParam("categoryId") Long id,
			@RequestParam(value = "page", required = false) Integer page) {
		if(page ==null) page=1;
		return productService.findByCategory(id, page, PAGE_SIZE);
	}
	@GetMapping("/getbycategory")
	public ResponseEntity<Map<String, Object>> getAllTutorials(
			@RequestParam("categoryId") Long categoryId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size
	) {

		try {
			Category category = categoryRepo.findById(categoryId).orElseThrow(()->new IllegalStateException("Not found category"));
			List<Product> tutorials = new ArrayList<Product>();
			Pageable paging = PageRequest.of(page, size);

			Page<Product> pageTuts;
			if (categoryId == null)
				pageTuts = productRepository.findAll(paging);
			else
				pageTuts = productRepository.findByCategory(category, paging);

			tutorials = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("data", tutorials);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/relate")
	public List<ProductDTO> getRelateProduct(
			@RequestParam("categoryId") Long id) {
		return productService.findByCategory(id, 1, RELATED_SIZE).getContent();
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ProductDTO save(@Valid @RequestBody ProductDTO product){
		return productService.save(product);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ProductDTO update(@RequestBody ProductDTO product){
		return productService.save(product);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(@PathVariable Long id){
		productService.delete(id);
	}
	
	@PostMapping("/craw")
	public void crawData(@Valid @RequestBody List<ProductDTO> productDTOs) {
		productService.saveAll(productDTOs);
	}
	
	@GetMapping("/search")
	public List<ProductDTO> searchProduct(@RequestParam String keyword,
										@RequestParam(required = false) Long categoryId){
		return productService.findByKeyword(keyword, categoryId);
	}
	
	@GetMapping("category{categoryId}")
	public List<ProductDTO> getAllByCategory(@PathVariable Long categoryId){
		return productService.findAllByCategory(categoryId);
	}
}
