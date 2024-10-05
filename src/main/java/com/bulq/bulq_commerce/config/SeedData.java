package com.bulq.bulq_commerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bulq.bulq_commerce.models.Account;
import com.bulq.bulq_commerce.services.AccountService;
import com.bulq.bulq_commerce.util.constants.Authority;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        Account account01 = new Account();
        Account account02 = new Account();
        Account account03 = new Account();

        account01.setEmail("user@user.com");
        account01.setPassword("password");
        account01.setAuthorities(Authority.USER.toString());
        accountService.save(account01);

        account02.setEmail("admin@admin.com");
        account02.setPassword("password");
        account02.setAuthorities(Authority.ADMIN.toString());
        accountService.save(account02);

        account03.setEmail("driver@driver.com");
        account03.setPassword("password");
        account03.setAuthorities(Authority.BUSINESS.toString());
        accountService.save(account03);
    }

    
}

