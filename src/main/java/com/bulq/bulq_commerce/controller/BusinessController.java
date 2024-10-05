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
import com.bulq.bulq_commerce.models.Business;
import com.bulq.bulq_commerce.models.Collection;
import com.bulq.bulq_commerce.models.Color;
import com.bulq.bulq_commerce.models.Image;
import com.bulq.bulq_commerce.models.Product;
import com.bulq.bulq_commerce.models.Size;
import com.bulq.bulq_commerce.payload.business.AddBusinessPayloadDTO;
import com.bulq.bulq_commerce.payload.business.FilterBusinessViewDTO;
import com.bulq.bulq_commerce.payload.business.FilterCollectionViewDTO;
import com.bulq.bulq_commerce.payload.business.UpdateBusinessPayloadDTO;
import com.bulq.bulq_commerce.payload.business.UpdateBusinessProductPayloadDTO;
import com.bulq.bulq_commerce.payload.products.AddBusinessImagePayloadDTO;
import com.bulq.bulq_commerce.payload.products.AddBusinessProductColorPayloadDTO;
import com.bulq.bulq_commerce.payload.products.AddBusinessProductSizePayloadDTO;
import com.bulq.bulq_commerce.payload.products.ColorViewDTO;
import com.bulq.bulq_commerce.payload.products.DeleteProductPayloadDTO;
import com.bulq.bulq_commerce.payload.products.FilterProductViewDTO;
import com.bulq.bulq_commerce.payload.products.ImageViewDTO;
import com.bulq.bulq_commerce.payload.products.SizeViewDTO;
import com.bulq.bulq_commerce.services.AccountService;
import com.bulq.bulq_commerce.services.BusinessService;
import com.bulq.bulq_commerce.services.CollectionService;
import com.bulq.bulq_commerce.services.ColorService;
import com.bulq.bulq_commerce.services.EmailService;
import com.bulq.bulq_commerce.services.ImageService;
import com.bulq.bulq_commerce.services.ProductService;
import com.bulq.bulq_commerce.services.SizeService;
import com.bulq.bulq_commerce.util.constants.EmaiLError;
import com.bulq.bulq_commerce.util.email.EmailDetailsWelcome;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/business")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Business Controller", description = "Controller for Business management")
public class BusinessController {

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

    @Autowired
    private BusinessService businessService;

    @Autowired
    private CollectionService collectionService;

    @PostMapping("/create-business")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Issue with payload")
    @ApiResponse(responseCode = "201", description = "Pickup a package added")
    @ApiResponse(responseCode = "200", description = "success")
    @Operation(summary = "Add a new Product")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> createBusiness(@Valid @RequestBody AddBusinessPayloadDTO payloadDTO,
            Authentication authentication) {
        String email = "";
        email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account;
        if (!optionalAccount.isPresent()) {
            ResponseEntity.badRequest().body(null);
        }
        account = optionalAccount.get();
        Optional<Business> optionalBusiness = businessService.findById(account.getId());
        if (optionalBusiness.isPresent()) {
            return ResponseEntity.badRequest().body("Account can only have only one business account");
        }
        try {
            Business business = new Business();
            business.setAccount(account);
            business.setAgree(payloadDTO.getAgree());
            business.setBusiness_location(payloadDTO.getBusiness_location());
            business.setBusiness_name(payloadDTO.getBusiness_name());
            business.setBusiness_type(payloadDTO.getBusiness_type());
            business.setPhone_number(payloadDTO.getPhone_number());
            business.setEmail(payloadDTO.getEmail());
            businessService.save(business);

            String subject = "Business created successfully";
            EmailDetailsWelcome businessDetails = new EmailDetailsWelcome(business.getEmail(),
                    business.getBusiness_name(),
                    subject, business.getBusiness_name());
            System.out.println(businessDetails);

            if (emailService.sendBusinessCreationUnderReviewEmail(businessDetails) == false) {
                log.debug(EmaiLError.Email_Error.toString() + " " + "Error sending mail");
                return ResponseEntity.badRequest().body("Error sending mail, contact admin");
            }
            ;

            return ResponseEntity
                    .ok("Business has been created successfully, and awaiting QA review from the management. check your mail.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/")
    @ApiResponse(responseCode = "200", description = "List of businesses pagination")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token error")
    @Operation(summary = "List businesses in paginated format")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<List<FilterBusinessViewDTO>> allUsers(
            @RequestParam(required = false, name = "createdAt", defaultValue = "delivery_status") String sort_by,
            @RequestParam(required = false, name = "per_page", defaultValue = "2") String per_page,
            @RequestParam(required = false, name = "page", defaultValue = "1") String page,
            @RequestParam(required = false, name = "id", defaultValue = "") Long id,
            @RequestParam(required = false, name = "business_name", defaultValue = "") String businessName,
            @RequestParam(required = false, name = "email", defaultValue = "") String email,
            @RequestParam(required = false, name = "business_type", defaultValue = "") String businessType,
            @RequestParam(required = false, name = "phone_number,", defaultValue = "") String phoneNumber) {
        Page<Business> businessesOnPage = businessService.findByBusinessInfo(Integer.parseInt(page) - 1,
                Integer.parseInt(per_page), sort_by, id, businessName, email, businessType, phoneNumber);
        List<Business> businessList = businessesOnPage.getContent();
        int totalPages = businessesOnPage.getTotalPages();
        List<Integer> pages = new ArrayList<>();
        if (totalPages > 0) {
            pages = IntStream.rangeClosed(0, totalPages - 1).boxed().collect(Collectors.toList());
        }
        for (int link : pages) {
            // String active = "";
            if (link == businessesOnPage.getNumber()) {
                // active = "active";
            }

            List<FilterCollectionViewDTO> collections = new ArrayList<>();
            List<FilterProductViewDTO> products = new ArrayList<>();

            for (Collection collection : collectionService.findByBusiness_id(id)) {

                for (Product product : productService.findByCollectionId(id)) {
                    products.add(new FilterProductViewDTO(product.getId(), product.getProductName(),
                            product.getVendor(), product.getBrand(), product.getPrice(), product.getImage(),
                            product.getMaterial(), product.getDescription(), product.getCategory(), product.getWeight(),
                            product.getDiscount(), product.getStock()));
                }

                collections.add(new FilterCollectionViewDTO(collection.getId(), collection.getName(),
                        collection.getDsecription(), collection.getBillboard(), products));
            }

            if (pages != null) {
                List<FilterBusinessViewDTO> businesses = businessList.stream()
                        .map(business -> new FilterBusinessViewDTO(business.getId(), business.getBusiness_name(),
                                business.getBusiness_location(), business.getBusiness_type(), business.getEmail(),
                                business.getPhone_number(), business.getAgree(), business.getEarning(), collections))
                        .collect(Collectors.toList());
                return ResponseEntity.ok(businesses);
            }
        }
        return null;
    }

    @GetMapping(value = "/{businessId}/view", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "delivery code found success")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error")
    @Operation(summary = "View single business")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<FilterBusinessViewDTO> trackOrder(@PathVariable("businessId") Long businessId,
            Authentication authentication) {
        // String email = authentication.getName();
        // Optional<Account> optionalAccount = accountService.findByEmail(email);
        // Account account = optionalAccount.get();
        Optional<Business> optionalBusiness = businessService.findById(businessId);
        Business business;
        // Booking booking;
        if (optionalBusiness.isPresent()) {
            business = optionalBusiness.get();
        } else {
            return ResponseEntity.ok(null);
        }
        // if (account.getId() != booking.getAccount().getId()) {
        // return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        // }

        List<ColorViewDTO> colors = new ArrayList<>();
        List<SizeViewDTO> sizes = new ArrayList<>();
        List<ImageViewDTO> images = new ArrayList<>();

        for (Color color : colorService.findByProduct_id(businessId)) {
            colors.add(new ColorViewDTO(color.getId(), color.getColor()));
        }

        for (Size size : sizeService.findByProduct_id(businessId)) {
            sizes.add(new SizeViewDTO(size.getId(), size.getSize()));
        }

        for (Image image : imageService.findByProduct_id(businessId)) {
            images.add(new ImageViewDTO(image.getId(), image.getImage()));
        }

        List<FilterCollectionViewDTO> collections = new ArrayList<>();
        List<FilterProductViewDTO> products = new ArrayList<>();

        for (Collection collection : collectionService.findByBusiness_id(businessId)) {

            for (Product product : productService.findByCollectionId(businessId)) {
                products.add(new FilterProductViewDTO(product.getId(), product.getProductName(), product.getVendor(),
                        product.getBrand(), product.getPrice(), product.getImage(), product.getMaterial(),
                        product.getDescription(), product.getCategory(), product.getWeight(), product.getDiscount(),
                        product.getStock()));
            }

            collections.add(new FilterCollectionViewDTO(collection.getId(), collection.getName(),
                    collection.getDsecription(), collection.getBillboard(), products));
        }

        FilterBusinessViewDTO businessViewDTO = new FilterBusinessViewDTO(business.getId(), business.getBusiness_name(),
                business.getBusiness_location(), business.getBusiness_type(), business.getEmail(),
                business.getPhone_number(), business.getAgree(), business.getEarning(), collections);

        return ResponseEntity.ok(businessViewDTO);
    }

    @PutMapping(value = "/{businessId}/update", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Please add valid name a description")
    @ApiResponse(responseCode = "204", description = "Album updated")
    @Operation(summary = "Update an Album")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> updateBusiness(@Valid @RequestBody UpdateBusinessPayloadDTO payloadDTO,
            @PathVariable("businessId") Long businessId,
            Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();
        Optional<Business> optionalBusiness = businessService.findById(businessId);
        Business business;
        // Booking booking;
        if (optionalAccount.isPresent() && optionalBusiness.isPresent()) {
            business = optionalBusiness.get();
            account = optionalAccount.get();
        } else {
            return ResponseEntity.ok(null);
        }
        if ((account.getId() != business.getAccount().getId()) ||
                (account.getAuthorities() != "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        try {
            Business updatedBusiness = new Business();
            updatedBusiness.setBusiness_location(payloadDTO.getBusiness_location());
            updatedBusiness.setBusiness_name(payloadDTO.getBusiness_name());
            updatedBusiness.setBusiness_type(payloadDTO.getBusiness_type());
            updatedBusiness.setEmail(payloadDTO.getEmail());
            updatedBusiness.setPhone_number(payloadDTO.getPhone_number());
            businessService.save(updatedBusiness);

            return ResponseEntity
                    .ok("Product has been updated successfully, and awaiting QA review from the management.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(value = "/product/delete-product")
    @ApiResponse(responseCode = "200", description = "business product deleted")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token error")
    @Operation(summary = "Delete product")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> deleteCatalog(@RequestBody DeleteProductPayloadDTO payloadDTO,
            Authentication authentication) {

        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();
        Optional<Business> optionalBusiness = businessService.findById(account.getBusiness().getId());
        Optional<Product> optionalProduct = productService.findById(payloadDTO.getProductId());
        Optional<Collection> optionalCollection = collectionService.findById(payloadDTO.getCollectionId());
        Business business;
        Product product;
        Collection collection;
        // Booking booking;
        if (optionalCollection.isPresent()) {
            collection = optionalCollection.get();
        } else {
            return ResponseEntity.badRequest().body("Product does not exist!");
        }
        if (optionalProduct.isPresent()) {
            business = optionalBusiness.get();
            account = optionalAccount.get();
            product = optionalProduct.get();
        } else {
            return ResponseEntity.ok(null);
        }
        if ((account.getId() != business.getAccount().getId()) ||
                (account.getAuthorities() != "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        if ((collection.getId() != business.getId()) ||
                (account.getAuthorities() != "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        productService.deleteById(product.getId());
        return ResponseEntity.badRequest().body("Product deleted successfully!");
    }

    @PostMapping("/product/add-image")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Issue with payload")
    @ApiResponse(responseCode = "201", description = "Pickup a package added")
    @ApiResponse(responseCode = "200", description = "success")
    @Operation(summary = "Add a new image")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> addImage(@Valid @RequestBody AddBusinessImagePayloadDTO payloadDTO,
            Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();
        Optional<Business> optionalBusiness = businessService.findById(account.getBusiness().getId());
        Optional<Product> optionalProduct = productService.findById(payloadDTO.getProductId());
        Optional<Collection> optionalCollection = collectionService.findById(payloadDTO.getCollectionId());
        Business business;
        Product product;
        Collection collection;
        if (optionalCollection.isPresent()) {
            collection = optionalCollection.get();
        } else {
            return ResponseEntity.badRequest().body("Collection does not exist!");
        }
        if (optionalProduct.isPresent()) {
            business = optionalBusiness.get();
            account = optionalAccount.get();
            product = optionalProduct.get();
        } else {
            return ResponseEntity.badRequest().body("Product does not exist");
        }
        if ((account.getId() != business.getAccount().getId()) ||
                (account.getAuthorities() != "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        if ((collection.getId() != business.getId()) ||
                (account.getAuthorities() != "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        if (product.getId() != collection.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }

        try {
            Image image = new Image();
            image.setImage(payloadDTO.getImage());
            image.setProduct(product);
            image.setProduct(product);
            imageService.save(image);
            return ResponseEntity.ok("Image added to product successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Problem adding image");
        }
    }

    @PostMapping("/product/add-size")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Issue with payload")
    @ApiResponse(responseCode = "201", description = "Pickup a package added")
    @ApiResponse(responseCode = "200", description = "success")
    @Operation(summary = "Add a new Size")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> addSize(@Valid @RequestBody AddBusinessProductSizePayloadDTO payloadDTO,
            Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();
        Optional<Business> optionalBusiness = businessService.findById(account.getBusiness().getId());
        Optional<Product> optionalProduct = productService.findById(payloadDTO.getProductId());
        Optional<Collection> optionalCollection = collectionService.findById(payloadDTO.getCollectionId());
        Business business;
        Product product;
        Collection collection;
        if (optionalCollection.isPresent()) {
            collection = optionalCollection.get();
        } else {
            return ResponseEntity.badRequest().body("Collection does not exist!");
        }
        if (optionalProduct.isPresent()) {
            business = optionalBusiness.get();
            account = optionalAccount.get();
            product = optionalProduct.get();
        } else {
            return ResponseEntity.badRequest().body("Product does not exist");
        }
        if ((account.getId() != business.getAccount().getId()) ||
                (account.getAuthorities() != "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        if (collection.getId() != business.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        if (product.getId() != collection.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }

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

    @PostMapping("/product/add-color")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Issue with payload")
    @ApiResponse(responseCode = "201", description = "Pickup a package added")
    @ApiResponse(responseCode = "200", description = "success")
    @Operation(summary = "Add a new color")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> addColor(@Valid @RequestBody AddBusinessProductColorPayloadDTO payloadDTO,
            Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();
        Optional<Business> optionalBusiness = businessService.findById(account.getBusiness().getId());
        Optional<Product> optionalProduct = productService.findById(payloadDTO.getProductId());
        Optional<Collection> optionalCollection = collectionService.findById(payloadDTO.getCollectionId());
        Business business;
        Product product;
        Collection collection;
        if (optionalCollection.isPresent()) {
            collection = optionalCollection.get();
        } else {
            return ResponseEntity.badRequest().body("Collection does not exist!");
        }
        if (optionalProduct.isPresent()) {
            business = optionalBusiness.get();
            account = optionalAccount.get();
            product = optionalProduct.get();
        } else {
            return ResponseEntity.badRequest().body("Product does not exist");
        }
        if ((account.getId() != business.getAccount().getId()) ||
                (account.getAuthorities() != "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        if (collection.getId() != business.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        if (product.getId() != collection.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }

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

    @PutMapping(value = "/{businessId}/product/update", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Please add valid name a description")
    @ApiResponse(responseCode = "204", description = "Album updated")
    @Operation(summary = "Update an Album")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> updateBusinessProduct(@Valid @RequestBody UpdateBusinessProductPayloadDTO payloadDTO,
            @PathVariable("businessId") Long businessId,
            Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();
        Optional<Business> optionalBusiness = businessService.findById(businessId);
        Optional<Product> optionalProduct = productService.findById(payloadDTO.getProductId());
        Optional<Collection> optionalCollection = collectionService.findById(payloadDTO.getCollectionId());
        Business business;
        Product product;
        Collection collection;
        if (optionalCollection.isPresent()) {
            collection = optionalCollection.get();
        } else {
            return ResponseEntity.badRequest().body("Collection does not exist!");
        }
        if (optionalProduct.isPresent()) {
            business = optionalBusiness.get();
            account = optionalAccount.get();
            product = optionalProduct.get();
        } else {
            return ResponseEntity.badRequest().body("Product does not exist");
        }
        if ((account.getId() != business.getAccount().getId()) ||
                (account.getAuthorities() != "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        if (collection.getId() != business.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        if (product.getId() != collection.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        try {
            Product updatedProduct = new Product();
            updatedProduct.setProductName(payloadDTO.getProductName());
            updatedProduct.setBrand(payloadDTO.getBrand());
            updatedProduct.setCategory(payloadDTO.getCategory());
            updatedProduct.setDescription(payloadDTO.getDescription());
            updatedProduct.setImage(payloadDTO.getImage());
            updatedProduct.setPrice(payloadDTO.getPrice());
            updatedProduct.setDiscount(payloadDTO.getDiscount());
            updatedProduct.setCollection(collection);
            updatedProduct.setStock(payloadDTO.getStock());
            updatedProduct.setWeight(payloadDTO.getWeight());
            updatedProduct.setVendor(business.getBusiness_name());
            productService.save(updatedProduct);

            return ResponseEntity
                    .ok("Product has been updated successfully, and awaiting QA review from the management.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
