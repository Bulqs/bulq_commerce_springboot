package com.bulq.bulq_commerce.controller;

import java.time.LocalDateTime;
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
import com.bulq.bulq_commerce.models.Item;
import com.bulq.bulq_commerce.models.Order;
import com.bulq.bulq_commerce.models.Product;
import com.bulq.bulq_commerce.payload.order.AddItemPayloadDTO;
import com.bulq.bulq_commerce.payload.order.AddOrderPayloadDTO;
import com.bulq.bulq_commerce.payload.order.FilterOrderViewDT;
import com.bulq.bulq_commerce.payload.order.ItemViewDTO;
import com.bulq.bulq_commerce.payload.order.OrderViewDTO;
import com.bulq.bulq_commerce.payload.order.UpdateOrderPayloadDTO;
import com.bulq.bulq_commerce.services.AccountService;
import com.bulq.bulq_commerce.services.BusinessService;
import com.bulq.bulq_commerce.services.EmailService;
import com.bulq.bulq_commerce.services.ItemService;
import com.bulq.bulq_commerce.services.OrderService;
import com.bulq.bulq_commerce.services.ProductService;
import com.bulq.bulq_commerce.util.AppUtil;
import com.bulq.bulq_commerce.util.constants.EmaiLError;
import com.bulq.bulq_commerce.util.constants.Status;
import com.bulq.bulq_commerce.util.email.EmailDetailsWelcome;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "Controller for Orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @Autowired
    private final AccountService accountService;

    @Autowired
    private final ItemService itemService;

    @Autowired
    private final EmailService emailService;

    @Autowired
    private final BusinessService businessService;

    @Autowired
    private final ProductService productService;

    @PostMapping("/create-order")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Please pass the correct payload")
    @ApiResponse(responseCode = "201", description = "Order added")
    @ApiResponse(responseCode = "200", description = "success")
    @Operation(summary = "Add a new order")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<OrderViewDTO> addOrder(@Valid @RequestBody AddOrderPayloadDTO payloadDTO,
            Authentication authentication) {
        String email = "";
        email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Optional<Product> optionalProduct = productService.findById(payloadDTO.getProductId());
        Account account;
        Product product;
        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
            product = optionalProduct.get();
        } else {
            return ResponseEntity.badRequest().body(null);
        }
        // deduct the customers wallet
        // credit the store owners wallet
        try {
            AppUtil appUtil = new AppUtil();
            String receiptNumber = appUtil.stringGenerator(account.getId());
            List<AddItemPayloadDTO> items = new ArrayList<>();
            Order order = new Order();
            order.setTotalItems(payloadDTO.getTotalItems());
            order.setSubtotal(payloadDTO.getSubtotal());
            order.setShippingFee(payloadDTO.getShippingFee());
            order.setCreatedAt(LocalDateTime.now());
            order.setAccount(account);
            order.setStatus(Status.NEW);
            order.setBusiness(product.getCollection().getBusiness());
            order.setCustomerName(account.getFullName());
            order.setReceiptNumber(receiptNumber);
            order.setCountry(payloadDTO.getCountry());
            order.setState(payloadDTO.getState());
            order.setAddress(payloadDTO.getAddress());
            order.setDeliveryType(payloadDTO.getDeliveryType());
            orderService.save(order);

            for (AddItemPayloadDTO orderItem : payloadDTO.getItems()) {
                items.add(new AddItemPayloadDTO(orderItem.getImage(), orderItem.getColor(),
                        orderItem.getSize(), orderItem.getFrequency(), orderItem.getTotal(), orderItem.getDiscount(),
                        orderItem.getVendor()));
                Item item = new Item();
                item.setColor(orderItem.getColor());
                item.setDiscount(Integer.parseInt(orderItem.getDiscount()));
                item.setFrequency(Integer.parseInt(orderItem.getFrequency()));
                item.setImage(orderItem.getImage());
                item.setOrder(order);
                item.setSize(orderItem.getSize());
                item.setTotal(Integer.parseInt(orderItem.getTotal()));
                item.setVendor(orderItem.getVendor());
                itemService.save(item);
            }

            String subject = "Business created successfully";
            EmailDetailsWelcome orderDetails = new EmailDetailsWelcome(account.getEmail(),
                    receiptNumber,
                    subject, account.getFirstName());
            System.out.println(orderDetails);

            if (emailService.sendOrderSuccessfulEmail(orderDetails) == false) {
                log.debug(EmaiLError.Email_Error.toString() + " " + "Error sending mail");
                return ResponseEntity.badRequest().body(null);
            }
            ;

            // Also send a mail to the Store owner
            String storeSubject = "Order placed awaiting delivery";
            EmailDetailsWelcome storeDetails = new EmailDetailsWelcome(product.getCollection().getBusiness().getEmail(),
                    receiptNumber,
                    storeSubject, account.getFirstName());
            System.out.println(orderDetails);

            if (emailService.sendOrderPendingDeliveryEmail(storeDetails) == false) {
                log.debug(EmaiLError.Email_Error.toString() + " " + "Error sending mail");
                return ResponseEntity.badRequest().body(null);
            }
            ;

            OrderViewDTO orderViewDTO = new OrderViewDTO(order.getId(), order.getTotalItems(), order.getSubtotal(),
                    order.getShippingFee(), order.getCustomerName(), order.getCountry(), order.getState(),
                    order.getAddress(), order.getDeliveryType(), null);

            return ResponseEntity.ok(orderViewDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all")
    @ApiResponse(responseCode = "200", description = "List of complaints pagination")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token error")
    @Operation(summary = "List complaints in paginated format")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<List<FilterOrderViewDT>> allUsers(
            @RequestParam(required = false, name = "createdAt", defaultValue = "createdAt") String sort_by,
            @RequestParam(required = false, name = "per_page", defaultValue = "2") String per_page,
            @RequestParam(required = false, name = "page", defaultValue = "1") String page,
            @RequestParam(required = false, name = "id", defaultValue = "1") Long id,
            @RequestParam(required = false, name = "status", defaultValue = "") String status,
            @RequestParam(required = false, name = "customerName", defaultValue = "") String customerName,
            @RequestParam(required = false, name = "receiptNumber", defaultValue = "") String receiptNumber) {
        Page<Order> ordersOnPage = orderService.findByOrderInfo(Integer.parseInt(page) - 1,
                Integer.parseInt(per_page), sort_by, id, status, customerName, receiptNumber);
        List<Order> orderList = ordersOnPage.getContent();
        int totalPages = ordersOnPage.getTotalPages();
        List<Integer> pages = new ArrayList<>();
        if (totalPages > 0) {
            pages = IntStream.rangeClosed(0, totalPages -
                    1).boxed().collect(Collectors.toList());
        }
        for (int link : pages) {
            // String active = "";
            if (link == ordersOnPage.getNumber()) {
                // active = "active";
            }
            if (pages != null) {
                List<FilterOrderViewDT> orders = orderList.stream()
                        .map(order -> new FilterOrderViewDT(order.getId(), order.getTotalItems(), order.getSubtotal(),
                                order.getShippingFee(), order.getCustomerName(), order.getCountry(), order.getState(),
                                order.getAddress(), order.getDeliveryType()))
                        .collect(Collectors.toList());
                return ResponseEntity.ok(orders);
            }
        }
        return null;
    }

    @GetMapping(value = "/{orderId}/view-order", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "View single order")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token error")
    @Operation(summary = "View single complaint")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<OrderViewDTO> viewComplaint(@PathVariable("orderId") Long orderId,
            Authentication authentication) {
        Optional<Order> optionalOrder = orderService.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            orderService.findById(orderId);
            List<ItemViewDTO> items = new ArrayList<>();
            // ItemViewDTO itemOrdered = new ItemViewDTO();
            for (Item item : itemService.findByOrder_id(orderId)) {
                items.add(new ItemViewDTO(item.getId(), item.getImage(), item.getColor(), item.getSize(),
                        item.getFrequency(), item.getTotal(), item.getDiscount(), item.getVendor()));
            }

            OrderViewDTO singleOrder = new OrderViewDTO(order.getId(), order.getTotalItems(), order.getSubtotal(),
                    order.getShippingFee(), order.getCustomerName(), order.getCountry(), order.getState(),
                    order.getAddress(), order.getDeliveryType(), items);
            return ResponseEntity.ok(singleOrder);

        }
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping(value = "/{orderId}/update-order", produces = "application/json", consumes = "application/json")
    @ApiResponse(responseCode = "200", description = "List of catalogs")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token error")
    @Operation(summary = "update complaint")
    @SecurityRequirement(name = "bulq-demo-api")
    public ResponseEntity<String> updateCatalog(@Valid @RequestBody UpdateOrderPayloadDTO payloadDTO,
            @PathVariable("orderId") Long orderId, Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        Account account = optionalAccount.get();
        Optional<Business> optionalBusiness = businessService.findById(account.getBusiness().getId());
        Optional<Order> optionalOrder = orderService.findById(orderId);
        Business business;
        Order order;
        if (optionalOrder.isPresent()) {
            business = optionalBusiness.get();
            account = optionalAccount.get();
            order = optionalOrder.get();
        } else {
            return ResponseEntity.badRequest().body(null);
        }
        if ((order.getId() != business.getAccount().getId()) ||
                (account.getAuthorities() != "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        Order updatedOrder = optionalOrder.get();
        updatedOrder.setStatus(payloadDTO.getStatus());
        orderService.save(updatedOrder);
        return ResponseEntity.ok("Order has been updated successfully");

    }

}
