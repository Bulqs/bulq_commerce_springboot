package com.bulq.bulq_commerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bulq.bulq_commerce.models.Account;
import com.bulq.bulq_commerce.models.Color;
import com.bulq.bulq_commerce.models.Image;
import com.bulq.bulq_commerce.models.Product;
import com.bulq.bulq_commerce.models.Size;
import com.bulq.bulq_commerce.payload.products.AddColorPayloadDTO;
import com.bulq.bulq_commerce.payload.products.AddImagePayloadDTO;
import com.bulq.bulq_commerce.payload.products.AddProductPayloadDTO;
import com.bulq.bulq_commerce.payload.products.AddSizePayloadDTO;
import com.bulq.bulq_commerce.payload.products.ColorViewDTO;
import com.bulq.bulq_commerce.payload.products.DeleteProductPayloadDTO;
import com.bulq.bulq_commerce.payload.products.FilterProductViewDTO;
import com.bulq.bulq_commerce.payload.products.ImageViewDTO;
import com.bulq.bulq_commerce.payload.products.SingleProductViewDTO;
import com.bulq.bulq_commerce.payload.products.SizeViewDTO;
import com.bulq.bulq_commerce.payload.products.UpdateProductPayloadDTO;
import com.bulq.bulq_commerce.services.AccountService;
import com.bulq.bulq_commerce.services.ColorService;
import com.bulq.bulq_commerce.services.EmailService;
import com.bulq.bulq_commerce.services.ImageService;
import com.bulq.bulq_commerce.services.ProductService;
import com.bulq.bulq_commerce.services.SizeService;
import com.bulq.bulq_commerce.util.constants.EmaiLError;
import com.bulq.bulq_commerce.util.constants.QualityAssuranceType;
import com.bulq.bulq_commerce.util.email.EmailDetailsWelcome;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "Controller for Product management")
public class ProductController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ImageService imageService;

    @PostMapping("/create-product")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Issue with payload")
    @ApiResponse(responseCode = "201", description = "product created")
    @ApiResponse(responseCode = "200", description = "success")
    @Operation(summary = "Add a new Product")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> createProduct(@Valid @RequestBody AddProductPayloadDTO payloadDTO,
            Authentication authentication) {
        String email = "";
        email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account;
        if (!optionalAccount.isPresent()) {
            ResponseEntity.badRequest().body(null);
        }
        account = optionalAccount.get();
        try {
            Product product = new Product();
            product.setProductName(payloadDTO.getProductName());
            product.setDescription(payloadDTO.getDescription());
            product.setCategory(payloadDTO.getCategory());
            product.setBrand(payloadDTO.getBrand());
            product.setMaterial(payloadDTO.getMaterial());
            product.setPrice(payloadDTO.getPrice());
            product.setImage(email);
            product.setStock(payloadDTO.getStock());
            product.setQualityAssurance(QualityAssuranceType.PENDING);
            productService.save(product);

            String subject = "Booking success";

            EmailDetailsWelcome accountDetails = new EmailDetailsWelcome(account.getEmail(),
                    product.getProductName(), subject, account.getFirstName());
            System.out.println(accountDetails);

            if (emailService.sendProductUnderReviewEmail(accountDetails) == false) {
                log.debug(EmaiLError.Email_Error.toString() + " " + "Error sending mail");
                return ResponseEntity.badRequest().body("Error sending mail, contact admin");
            }
            ;

            return ResponseEntity
                    .ok("Product has been added successfully, and awaiting QA review from the management.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/")
    @ApiResponse(responseCode = "200", description = "List of products pagination")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token error")
    @Operation(summary = "List products in paginated format")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<List<FilterProductViewDTO>> allProducts(
            @RequestParam(required = false, name = "createdAt", defaultValue = "delivery_status") String sort_by,
            @RequestParam(required = false, name = "per_page", defaultValue = "2") String per_page,
            @RequestParam(required = false, name = "page", defaultValue = "1") String page,
            @RequestParam(required = false, name = "id", defaultValue = "") Long id,
            @RequestParam(required = false, name = "productName", defaultValue = "") String productName,
            @RequestParam(required = false, name = "vendor", defaultValue = "") String vendor,
            @RequestParam(required = false, name = "brand", defaultValue = "") String brand,
            @RequestParam(required = false, name = "category,", defaultValue = "") String category) {
        Page<Product> productsOnPage = productService.findByProductInfo(Integer.parseInt(page) - 1,
                Integer.parseInt(per_page), sort_by, id, productName, vendor, brand, category);
        List<Product> productList = productsOnPage.getContent();
        int totalPages = productsOnPage.getTotalPages();
        List<Integer> pages = new ArrayList<>();
        if (totalPages > 0) {
            pages = IntStream.rangeClosed(0, totalPages - 1).boxed().collect(Collectors.toList());
        }
        for (int link : pages) {
            // String active = "";
            if (link == productsOnPage.getNumber()) {
                // active = "active";
            }
            if (pages != null) {
                List<FilterProductViewDTO> products = productList.stream()
                        .map(product -> new FilterProductViewDTO(product.getId(), product.getProductName(),
                                product.getVendor(), product.getBrand(), product.getPrice(), product.getImage(),
                                product.getMaterial(), product.getDescription(),
                                product.getCategory(), product.getWeight(), product.getDiscount(), product.getStock()))
                        .collect(Collectors.toList());
                return ResponseEntity.ok(products);
            }
        }
        return null;
    }

    @GetMapping(value = "/{productId}/view", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "single product found")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error")
    @Operation(summary = "View single product")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<SingleProductViewDTO> viewProduct(@PathVariable("productId") Long productId,
            Authentication authentication) {
        Optional<Product> optionalProduct = productService.findById(productId);
        Product product;
        // Booking booking;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            return ResponseEntity.ok(null);
        }

        List<ColorViewDTO> colors = new ArrayList<>();
        List<SizeViewDTO> sizes = new ArrayList<>();
        List<ImageViewDTO> images = new ArrayList<>();

        for (Color color : colorService.findByProduct_id(productId)) {
            colors.add(new ColorViewDTO(color.getId(), color.getColor()));
        }

        for (Size size : sizeService.findByProduct_id(productId)) {
            sizes.add(new SizeViewDTO(size.getId(), size.getSize()));
        }

        for (Image image : imageService.findByProduct_id(productId)) {
            images.add(new ImageViewDTO(image.getId(), image.getImage()));
        }

        SingleProductViewDTO singleProductViewDTO = new SingleProductViewDTO(product.getId(), product.getProductName(),
                product.getVendor(), product.getVendor(), product.getPrice(),
                product.getMaterial(), product.getDescription(), product.getCategory(), product.getWeight(),
                product.getImage(),
                product.getDiscount(), product.getStock(), colors, sizes, images);

        return ResponseEntity.ok(singleProductViewDTO);
    }

    @PutMapping(value = "/{productId}/update", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Please add valid name a description")
    @ApiResponse(responseCode = "204", description = "product updated")
    @Operation(summary = "Update a product")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> updateProduct(@Valid @RequestBody UpdateProductPayloadDTO payloadDTO,
            @PathVariable("productId") Long productId,
            Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();
        Optional<Product> optionalProduct = productService.findById(productId);
        Product product;
        // Booking booking;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            return ResponseEntity.ok(null);
        }
        // if ((product.getId() != product.getBusiness().getId()) ||
        // (account.getAuthorities() != "ADMIN")) {
        // return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        // }
        account = optionalAccount.get();
        try {
            Product updatedProduct = new Product();
            updatedProduct.setProductName(payloadDTO.getProductName());
            updatedProduct.setDescription(payloadDTO.getDescription());
            updatedProduct.setCategory(payloadDTO.getCategory());
            updatedProduct.setBrand(payloadDTO.getBrand());
            updatedProduct.setMaterial(payloadDTO.getMaterial());
            updatedProduct.setPrice(payloadDTO.getPrice());
            updatedProduct.setStock(payloadDTO.getStock());
            updatedProduct.setQualityAssurance(QualityAssuranceType.PENDING);
            updatedProduct.setVendor(account.getBusiness().getBusiness_name());
            productService.save(updatedProduct);

            return ResponseEntity
                    .ok("Product has been updated successfully, and awaiting QA review from the management.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(value = "/delete-product")
    @ApiResponse(responseCode = "200", description = "product deleted")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token error")
    @Operation(summary = "Delete product")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> deleteProduct(@RequestBody DeleteProductPayloadDTO payloadDTO,
            Authentication authentication) {

        Optional<Product> optionalProduct = productService.findById(payloadDTO.getProductId());
        Product product;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            return ResponseEntity.badRequest().body("Product does not exist!");
        }
        productService.deleteById(product.getId());
        return ResponseEntity.badRequest().body("Product deleted successfully!");
    }

    @PostMapping("/add-image")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Issue with payload")
    @ApiResponse(responseCode = "201", description = "Image added")
    @ApiResponse(responseCode = "200", description = "success")
    @Operation(summary = "Add a new image")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> addImage(@Valid @RequestBody AddImagePayloadDTO payloadDTO,
            Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();
        Optional<Product> optionalProduct = productService.findById(payloadDTO.getProductId());
        Product product;
        // Booking booking;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            return ResponseEntity.ok(null);
        }
        // if ((product.getId() != product.getBusiness().getId()) ||
        // (account.getAuthorities() != "ADMIN")) {
        // return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        // }
        account = optionalAccount.get();

        try {
            Image image = new Image();
            image.setImage(payloadDTO.getImage());
            image.setProduct(product);
            imageService.save(image);
            return ResponseEntity.ok("Image added to product successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Problem adding image");
        }
    }

    @PostMapping("/add-size")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Issue with payload")
    @ApiResponse(responseCode = "201", description = "Size added")
    @ApiResponse(responseCode = "200", description = "success")
    @Operation(summary = "Add a new Size")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> addSize(@Valid @RequestBody AddSizePayloadDTO payloadDTO,
            Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();
        Optional<Product> optionalProduct = productService.findById(payloadDTO.getProductId());
        Product product;
        // Booking booking;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            return ResponseEntity.ok(null);
        }
        // if ((product.getId() != product.getBusiness().getId()) ||
        // (account.getAuthorities() != "ADMIN")) {
        // return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        // }
        account = optionalAccount.get();

        try {
            Size size = new Size();
            size.setSize(payloadDTO.getSize());
            size.setProduct(product);
            sizeService.save(size);
            return ResponseEntity.ok("Size added to product successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Problem adding size");
        }
    }

    @PostMapping("/add-color")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Issue with payload")
    @ApiResponse(responseCode = "201", description = "Color added")
    @ApiResponse(responseCode = "200", description = "success")
    @Operation(summary = "Add a new color")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> addColor(@Valid @RequestBody AddColorPayloadDTO payloadDTO,
            Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();
        Optional<Product> optionalProduct = productService.findById(payloadDTO.getProductId());
        Product product;
        // Booking booking;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            return ResponseEntity.ok(null);
        }
        // if ((product.getId() != product.getBusiness().getId()) ||
        // (account.getAuthorities() != "ADMIN")) {
        // return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        // }
        account = optionalAccount.get();

        try {
            Color color = new Color();
            color.setColor(payloadDTO.getColor());
            color.setProduct(product);
            colorService.save(color);
            return ResponseEntity.ok("Color added to product successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Problem adding size");
        }
    }

}
