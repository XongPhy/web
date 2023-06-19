package com.example.web.Controller;


import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.example.web.Model.Product;

import com.example.web.Services.ProductServices;
import com.example.web.Services.SizeService;

import com.example.web.Utils.FileUploadUtil;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;


@Configuration
@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductServices services;
	
//	@Autowired
//	private CartService cartService;
//	
//	@Autowired
//	private UserService userService;
	
	@Autowired
	private SizeService sizeService;
	
	@GetMapping
    public String viewProduct(Model model, Principal principal) {
//    	String username = principal.getName();
//        User user = userService.findByUsername(username);
//        List<CartItem> cartItems = cartService.getCartItems(user);
//        List<Product> lisProduct = services.listAll();
//        model.addAttribute("products", lisProduct);
//        model.addAttribute("sizes",sizeService.listAll());
//		  model.addAttribute("cartItemCount", cartItems.size());	
//        return "products/index";
		return viewAllProduct(model, 1, "id", "asc", " ");
    }
	
	@GetMapping("/page/{pageNum}")
	public String viewAllProduct(Model model, @PathVariable(name = "pageNum") int pageNum,
			@Param("sortField") String sortField, @Param("sortType") String sortType,
			@Param("keyword") String keyword) {
		sortField = sortField==null?"id":sortField;
		sortType = sortType==null?"asc":sortType;
		Page<Product> page = services.listAllWithOutDelete(pageNum, sortField, sortType, keyword);
		List<Product> listProduct = page.getContent();
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortType", sortType);
		model.addAttribute("reverseSortType", sortType.equals("asc") ? "desc" : "asc");
		model.addAttribute("keyword", keyword);
		model.addAttribute("products", listProduct);
		return "products/index";
	}
	
	
	@GetMapping("/create")
	public String Create(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		model.addAttribute("sizes",sizeService.listAll());
		return "products/create";
	}
	
	@PostMapping("/save")
	public String saveProduct(@ModelAttribute("product") Product product, @RequestParam("image") MultipartFile imageFile) throws IOException {	
		String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
		product.setImageUrl(fileName);
		Product saveProduct = services.save(product);
		if (!imageFile.getOriginalFilename().isBlank())
        {
            String uploadDir = "photos/" + saveProduct.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, imageFile);
        }
	    
	    return "redirect:/products";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditProductPage(@PathVariable("id") Long id, Model model) {
		Product product = services.get(id);
		
		if(product==null) {
			return "not-found";
		}else {
			model.addAttribute("sizes",sizeService.listAll());
			model.addAttribute("product",product);
			return "products/edit";
		}
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id) {
		Product product = services.get(id);
		
		if(product==null) {
			return "not-found";
		}else {
			services.delete(id);
			return "redirect:/products";
		}
	}
	
	@GetMapping("/export/{pageNum}")
	public void exportToCSV(HttpServletResponse response, @PathVariable(name = "pageNum") int pageNum)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		response.setContentType("text/csv");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=products_" + currentDateTime + ".csv";
		response.setHeader(headerKey, headerValue);

		List<Product> products = services.listAll(pageNum).getContent();

		StatefulBeanToCsvBuilder<Product> builder = new StatefulBeanToCsvBuilder<Product>(response.getWriter())
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR)
				.withOrderedResults(false);

		Arrays.stream(Product.class.getDeclaredFields())
				.filter(field -> !("id".equals(field.getName()) || "name".equals(field.getName())
						|| "brand".equals(field.getName()) || "price".equals(field.getName())))
				.forEach(field -> builder.withIgnoreField(Product.class, field));

		StatefulBeanToCsv<Product> writer = builder.build();

		// write all users to csv file
		writer.write(products);
	}
}
