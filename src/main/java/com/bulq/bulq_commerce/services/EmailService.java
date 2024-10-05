package com.bulq.bulq_commerce.services;

import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.util.email.EmailDetails;
import com.bulq.bulq_commerce.util.email.EmailDetailsWelcome;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public Boolean sendMail(EmailDetails details){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
// String to, String firstName, String verificationLink
    public boolean sendWelcomeEmail(EmailDetailsWelcome details) throws MessagingException {
        try {
            String subject = "Welcome to Bulq";
        String content = buildEmailContent(details.getRecipient(),details.getFirstName(), details.getBody());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(details.getRecipient());
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
        return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean sendForgetPasswordEmail(EmailDetailsWelcome details) throws MessagingException {
        try {
            String subject = "Oops! Forgotten password!";
        String content = buildForgetPassword(details.getRecipient(),details.getFirstName(), details.getBody());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(details.getRecipient());
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
        return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean sendProductUnderReviewEmail(EmailDetailsWelcome details) throws MessagingException {
        try {
            String subject = "New Product Under QA Review";
        String content = buildProductUnderReview(details.getRecipient(),details.getFirstName(), details.getBody());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(details.getRecipient());
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
        return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean sendBusinessCreationUnderReviewEmail(EmailDetailsWelcome details) throws MessagingException {
        try {
            String subject = "New Business creatiom Under QA Review";
        String content = buildBusinessCreationUnderReview(details.getRecipient(),details.getFirstName(), details.getBody());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(details.getRecipient());
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
        return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean sendOrderSuccessfulEmail(EmailDetailsWelcome details) throws MessagingException {
        try {
            String subject = "New Business creatiom Under QA Review";
        String content = buildSuccessfulOrder(details.getRecipient(),details.getFirstName(), details.getBody());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(details.getRecipient());
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
        return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean sendOrderPendingDeliveryEmail(EmailDetailsWelcome details) throws MessagingException {
        try {
            String subject = "Order placed awaiting delivery";
        String content = buildOrderPendingDelivery(details.getRecipient(),details.getFirstName(), details.getBody());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(details.getRecipient());
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
        return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean sendKYCPendingEmail(EmailDetailsWelcome details) throws MessagingException {
        try {
            String subject = "Verification awaiting approval";
        String content = buildKYCVerificationPending(details.getRecipient(),details.getFirstName(), details.getBody());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(details.getRecipient());
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
        return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean sendKYCStatusEmail(EmailDetailsWelcome details) throws MessagingException {
        try {
            String subject = "Verification awaiting approval";
        String content = buildKYCApprovalStatus(details.getRecipient(),details.getFirstName(), details.getBody());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(details.getRecipient());
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
        return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    private String buildKYCApprovalStatus(String recipient, String firstName, String message){
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Order Successful</title>"
                + "<style>"
                + "body {"
                + "    font-family: Arial, sans-serif;"
                + "    background-color: #f4f4f4;"
                + "    margin: 0;"
                + "    padding: 0;"
                + "}"
                + ".header, .footer {"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    text-align: center;"
                + "    padding: 10px 0;"
                + "}"
                + ".content {"
                + "    background-color: white;"
                + "    padding: 20px;"
                + "    margin: 20px auto;"
                + "    width: 80%;"
                + "    max-width: 600px;"
                + "    border-radius: 8px;"
                + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "}"
                + ".button {"
                + "    display: inline-block;"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    padding: 10px 20px;"
                + "    text-decoration: none;"
                + "    border-radius: 5px;"
                + "    margin-top: 20px;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"header\">"
                + "        <h1>Order pending delivery</h1>"
                + "    </div>"
                + "    <div class=\"content\">"
                + "        <h2>Hello " + firstName + ",</h2>"
                + "        <p>I hope you're having a great day</p>"
                + "        <p>Kindly note that your verification was </p>"
                + "        <p>" + message + "<p>"
                + "        <p>You would receive a mail once your verification is successful.</p>"
                + "        <p>Have a nice one</p>"
                + "        <p>You can visit our app on:</p>"
                + "        <a href=\"http://localhost:8080/swagger-ui/index.html" +  "\" class=\"button\">Visit Bulq</a>"
                + "        <p>If you have any questions or need assistance, feel free to reach out to our support team.</p>"
                + "        <p>Happy travels!</p>"
                + "        <p>Best regards,</p>"
                + "        <p>The Bulq Team</p>"
                + "    </div>"
                + "    <div class=\"footer\">"
                + "        <p>&copy; 2024 Bulq. All rights reserved.</p>"
                + "        <p>Calgary, Alberta, Canada.</p>"
                + "        <p><a href=\"ecobank.com/bulq\" style=\"color: white;\">Unsubscribe</a></p>"
                + "    </div>"
                + "</body>"
                + "</html>";
    }

    private String buildKYCVerificationPending(String recipient, String firstName, String message){
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Order Successful</title>"
                + "<style>"
                + "body {"
                + "    font-family: Arial, sans-serif;"
                + "    background-color: #f4f4f4;"
                + "    margin: 0;"
                + "    padding: 0;"
                + "}"
                + ".header, .footer {"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    text-align: center;"
                + "    padding: 10px 0;"
                + "}"
                + ".content {"
                + "    background-color: white;"
                + "    padding: 20px;"
                + "    margin: 20px auto;"
                + "    width: 80%;"
                + "    max-width: 600px;"
                + "    border-radius: 8px;"
                + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "}"
                + ".button {"
                + "    display: inline-block;"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    padding: 10px 20px;"
                + "    text-decoration: none;"
                + "    border-radius: 5px;"
                + "    margin-top: 20px;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"header\">"
                + "        <h1>Order pending delivery</h1>"
                + "    </div>"
                + "    <div class=\"content\">"
                + "        <h2>Hello " + firstName + ",</h2>"
                + "        <p>I hope you're having a great day</p>"
                + "        <p>Kindly note that your verification is currently undergoing review for business </p>"
                + "        <p>" + message + "<p>"
                + "        <p>You would receive a mail once your verification is successful.</p>"
                + "        <p>Have a nice one</p>"
                + "        <p>You can visit our app on:</p>"
                + "        <a href=\"http://localhost:8080/swagger-ui/index.html" +  "\" class=\"button\">Visit Bulq</a>"
                + "        <p>If you have any questions or need assistance, feel free to reach out to our support team.</p>"
                + "        <p>Happy travels!</p>"
                + "        <p>Best regards,</p>"
                + "        <p>The Bulq Team</p>"
                + "    </div>"
                + "    <div class=\"footer\">"
                + "        <p>&copy; 2024 Bulq. All rights reserved.</p>"
                + "        <p>Calgary, Alberta, Canada.</p>"
                + "        <p><a href=\"ecobank.com/bulq\" style=\"color: white;\">Unsubscribe</a></p>"
                + "    </div>"
                + "</body>"
                + "</html>";
    }


    private String buildOrderPendingDelivery(String recipient, String firstName, String message){
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Order Successful</title>"
                + "<style>"
                + "body {"
                + "    font-family: Arial, sans-serif;"
                + "    background-color: #f4f4f4;"
                + "    margin: 0;"
                + "    padding: 0;"
                + "}"
                + ".header, .footer {"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    text-align: center;"
                + "    padding: 10px 0;"
                + "}"
                + ".content {"
                + "    background-color: white;"
                + "    padding: 20px;"
                + "    margin: 20px auto;"
                + "    width: 80%;"
                + "    max-width: 600px;"
                + "    border-radius: 8px;"
                + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "}"
                + ".button {"
                + "    display: inline-block;"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    padding: 10px 20px;"
                + "    text-decoration: none;"
                + "    border-radius: 5px;"
                + "    margin-top: 20px;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"header\">"
                + "        <h1>Order pending delivery</h1>"
                + "    </div>"
                + "    <div class=\"content\">"
                + "        <h2>Hello " + firstName + ",</h2>"
                + "        <p>I hope you're having a great day</p>"
                + "        <p>Kindly note an order has been placed for the following item with receipt: </p>"
                + "        <p>" + message + "<p>"
                + "        <p>Kindly log on to check for further details</p>"
                + "        <p>Have a nice one</p>"
                + "        <p>You can visit our app on:</p>"
                + "        <a href=\"http://localhost:8080/swagger-ui/index.html" +  "\" class=\"button\">Visit Bulq</a>"
                + "        <p>If you have any questions or need assistance, feel free to reach out to our support team.</p>"
                + "        <p>Happy travels!</p>"
                + "        <p>Best regards,</p>"
                + "        <p>The Bulq Team</p>"
                + "    </div>"
                + "    <div class=\"footer\">"
                + "        <p>&copy; 2024 Bulq. All rights reserved.</p>"
                + "        <p>Calgary, Alberta, Canada.</p>"
                + "        <p><a href=\"ecobank.com/bulq\" style=\"color: white;\">Unsubscribe</a></p>"
                + "    </div>"
                + "</body>"
                + "</html>";
    }

    private String buildSuccessfulOrder(String recipient, String firstName, String message){
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Order Successful</title>"
                + "<style>"
                + "body {"
                + "    font-family: Arial, sans-serif;"
                + "    background-color: #f4f4f4;"
                + "    margin: 0;"
                + "    padding: 0;"
                + "}"
                + ".header, .footer {"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    text-align: center;"
                + "    padding: 10px 0;"
                + "}"
                + ".content {"
                + "    background-color: white;"
                + "    padding: 20px;"
                + "    margin: 20px auto;"
                + "    width: 80%;"
                + "    max-width: 600px;"
                + "    border-radius: 8px;"
                + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "}"
                + ".button {"
                + "    display: inline-block;"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    padding: 10px 20px;"
                + "    text-decoration: none;"
                + "    border-radius: 5px;"
                + "    margin-top: 20px;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"header\">"
                + "        <h1>Thanks for your patronage</h1>"
                + "    </div>"
                + "    <div class=\"content\">"
                + "        <h2>Hi " + firstName + ",</h2>"
                + "        <p>Thanks for placing your order on the Bulq platform</p>"
                + "        <p>Kindly note that your orders are currently been processed and please this your order receipt: </p>"
                + "        <p>" + message + "<p>"
                + "        <p>Kindly provide this to our delivery person on contact</p>"
                + "        <p>Thanks for your patronage.</p>"
                + "        <p>You can visit our app on:</p>"
                + "        <a href=\"http://localhost:8080/swagger-ui/index.html" +  "\" class=\"button\">Visit Bulq</a>"
                + "        <p>If you have any questions or need assistance, feel free to reach out to our support team.</p>"
                + "        <p>Happy travels!</p>"
                + "        <p>Best regards,</p>"
                + "        <p>The Bulq Team</p>"
                + "    </div>"
                + "    <div class=\"footer\">"
                + "        <p>&copy; 2024 Bulq. All rights reserved.</p>"
                + "        <p>Calgary, Alberta, Canada.</p>"
                + "        <p><a href=\"ecobank.com/bulq\" style=\"color: white;\">Unsubscribe</a></p>"
                + "    </div>"
                + "</body>"
                + "</html>";
    }

    private String buildBusinessCreationUnderReview(String recipient, String firstName, String message){
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Welcome to Bulq</title>"
                + "<style>"
                + "body {"
                + "    font-family: Arial, sans-serif;"
                + "    background-color: #f4f4f4;"
                + "    margin: 0;"
                + "    padding: 0;"
                + "}"
                + ".header, .footer {"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    text-align: center;"
                + "    padding: 10px 0;"
                + "}"
                + ".content {"
                + "    background-color: white;"
                + "    padding: 20px;"
                + "    margin: 20px auto;"
                + "    width: 80%;"
                + "    max-width: 600px;"
                + "    border-radius: 8px;"
                + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "}"
                + ".button {"
                + "    display: inline-block;"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    padding: 10px 20px;"
                + "    text-decoration: none;"
                + "    border-radius: 5px;"
                + "    margin-top: 20px;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"header\">"
                + "        <h1>Welcome to Bulq</h1>"
                + "    </div>"
                + "    <div class=\"content\">"
                + "        <h2>Hi " + firstName + ",</h2>"
                + "        <p>Thank you for partnering with Bulq! We are thrilled to have you on board.</p>"
                + "        <p>Kindly note that your newly created business is currently under review for Quality Assurance purposes as we'd love customers to get the best out of their purchases, we'll get back to you as soon as your business with name </p>"
                + "        <p>" + message + "<p>"
                + "        <p>Is approved which will automatically display for customers who'd to purchase goods from your store</p>"
                + "        <p>Thanks for your patience.</p>"
                + "        <p>You can visit our app on:</p>"
                + "        <a href=\"http://localhost:8080/swagger-ui/index.html" +  "\" class=\"button\">Visit Bulq</a>"
                + "        <p>If you have any questions or need assistance, feel free to reach out to our support team.</p>"
                + "        <p>Happy travels!</p>"
                + "        <p>Best regards,</p>"
                + "        <p>The Bulq Team</p>"
                + "    </div>"
                + "    <div class=\"footer\">"
                + "        <p>&copy; 2024 Bulq. All rights reserved.</p>"
                + "        <p>Calgary, Alberta, Canada.</p>"
                + "        <p><a href=\"ecobank.com/bulq\" style=\"color: white;\">Unsubscribe</a></p>"
                + "    </div>"
                + "</body>"
                + "</html>";
    }

    private String buildProductUnderReview(String recipient, String firstName, String message){
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Welcome to Bulq</title>"
                + "<style>"
                + "body {"
                + "    font-family: Arial, sans-serif;"
                + "    background-color: #f4f4f4;"
                + "    margin: 0;"
                + "    padding: 0;"
                + "}"
                + ".header, .footer {"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    text-align: center;"
                + "    padding: 10px 0;"
                + "}"
                + ".content {"
                + "    background-color: white;"
                + "    padding: 20px;"
                + "    margin: 20px auto;"
                + "    width: 80%;"
                + "    max-width: 600px;"
                + "    border-radius: 8px;"
                + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "}"
                + ".button {"
                + "    display: inline-block;"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    padding: 10px 20px;"
                + "    text-decoration: none;"
                + "    border-radius: 5px;"
                + "    margin-top: 20px;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"header\">"
                + "        <h1>Welcome to Bulq</h1>"
                + "    </div>"
                + "    <div class=\"content\">"
                + "        <h2>Hi " + firstName + ",</h2>"
                + "        <p>Thank you for partnering with Bulq! We are thrilled to have you on board.</p>"
                + "        <p>Kindly note that your newly added product is currently under review for Quality Assurance purposes as we'd love customers to get the best out of their purchases, we'll get back to you as soon as your product </p>"
                + "        <p>" + message + "<p>"
                + "        <p>Is approved which will automatically display for customers who purchase goods from your store</p>"
                + "        <p>Thanks for your patience.</p>"
                + "        <p>You can visit our app on:</p>"
                + "        <a href=\"http://localhost:8080/swagger-ui/index.html" +  "\" class=\"button\">Visit Bulq</a>"
                + "        <p>If you have any questions or need assistance, feel free to reach out to our support team.</p>"
                + "        <p>Happy travels!</p>"
                + "        <p>Best regards,</p>"
                + "        <p>The Bulq Team</p>"
                + "    </div>"
                + "    <div class=\"footer\">"
                + "        <p>&copy; 2024 Bulq. All rights reserved.</p>"
                + "        <p>Calgary, Alberta, Canada.</p>"
                + "        <p><a href=\"ecobank.com/bulq\" style=\"color: white;\">Unsubscribe</a></p>"
                + "    </div>"
                + "</body>"
                + "</html>";
    }

    private String buildEmailContent(String recipient, String firstName, String message){
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Welcome to Bulq</title>"
                + "<style>"
                + "body {"
                + "    font-family: Arial, sans-serif;"
                + "    background-color: #f4f4f4;"
                + "    margin: 0;"
                + "    padding: 0;"
                + "}"
                + ".header, .footer {"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    text-align: center;"
                + "    padding: 10px 0;"
                + "}"
                + ".content {"
                + "    background-color: white;"
                + "    padding: 20px;"
                + "    margin: 20px auto;"
                + "    width: 80%;"
                + "    max-width: 600px;"
                + "    border-radius: 8px;"
                + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "}"
                + ".button {"
                + "    display: inline-block;"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    padding: 10px 20px;"
                + "    text-decoration: none;"
                + "    border-radius: 5px;"
                + "    margin-top: 20px;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"header\">"
                + "        <h1>Welcome to Bulq</h1>"
                + "    </div>"
                + "    <div class=\"content\">"
                + "        <h2>Hi " + firstName + ",</h2>"
                + "        <p>Thank you for joining Bulq! We are thrilled to have you on board.</p>"
                + "        <p>With Bulq, you have a partner come rain, come sunshine.</p>"
                + "        <p>Your verification code is:</p>"
                + "        <p>" + message + "<p>"
                + "        <p>You can visit our app on:</p>"
                + "        <a href=\"http://localhost:8080/swagger-ui/index.html" +  "\" class=\"button\">Visit Bulq</a>"
                + "        <p>If you have any questions or need assistance, feel free to reach out to our support team.</p>"
                + "        <p>Happy travels!</p>"
                + "        <p>Best regards,</p>"
                + "        <p>The Bulq Team</p>"
                + "    </div>"
                + "    <div class=\"footer\">"
                + "        <p>&copy; 2024 Bulq. All rights reserved.</p>"
                + "        <p>Calgary, Alberta, Canada.</p>"
                + "        <p><a href=\"ecobank.com/bulq\" style=\"color: white;\">Unsubscribe</a></p>"
                + "    </div>"
                + "</body>"
                + "</html>";
    }

    private String buildForgetPassword(String recipient, String firstName, String message){
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Welcome to Bulq Logistics</title>"
                + "<style>"
                + "body {"
                + "    font-family: Arial, sans-serif;"
                + "    background-color: #f4f4f4;"
                + "    margin: 0;"
                + "    padding: 0;"
                + "}"
                + ".header, .footer {"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    text-align: center;"
                + "    padding: 10px 0;"
                + "}"
                + ".content {"
                + "    background-color: white;"
                + "    padding: 20px;"
                + "    margin: 20px auto;"
                + "    width: 80%;"
                + "    max-width: 600px;"
                + "    border-radius: 8px;"
                + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "}"
                + ".button {"
                + "    display: inline-block;"
                + "    background-color: #0033cc;"
                + "    color: white;"
                + "    padding: 10px 20px;"
                + "    text-decoration: none;"
                + "    border-radius: 5px;"
                + "    margin-top: 20px;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"header\">"
                + "        <h1>Welcome to Bulq Logistics</h1>"
                + "    </div>"
                + "    <div class=\"content\">"
                + "        <h2>Hi " + firstName + ",</h2>"
                + "        <p>Forgootten password</p>"
                + "        <p>You can enter this reset password link below to reset your password</p>"
                + "        <p>" + message + "<p>"
                + "        <p>To continue on Bulq visit the link down below.</p>"
                + "        <a href=\"https://bulq-ecommerce.vercel.app/" + "\" class=\"button\">Visit Bulq</a>"
                + "        <p>Pease note that this link will expire in 10 minutes</p>"
                + "        <p>If you have any questions or need assistance, feel free to reach out to our support team.</p>"
                + "        <p>Happy Delivery!</p>"
                + "        <p>Best regards,</p>"
                + "        <p>The Bulq Team</p>"
                + "    </div>"
                + "    <div class=\"footer\">"
                + "        <p>&copy; 2024 Bulq Logistics. All rights reserved.</p>"
                + "        <p>Calgary, Alberta, Canada.</p>"
                + "        <p><a href=\"ecobank.com/bulq\" style=\"color: white;\">Unsubscribe</a></p>"
                + "    </div>"
                + "</body>"
                + "</html>";
    }
}
