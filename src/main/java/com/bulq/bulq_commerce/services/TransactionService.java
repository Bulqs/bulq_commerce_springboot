package com.bulq.bulq_commerce.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulq.bulq_commerce.models.Order;
import com.bulq.bulq_commerce.models.Transaction;
import com.bulq.bulq_commerce.models.Wallet;
import com.bulq.bulq_commerce.repositories.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction save(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findAllByWalletId(Long id) {
        return transactionRepository.findAllByWalletId(id);
    }

    public Optional<Transaction> findById(Long id){
        return transactionRepository.findById(id);
    }

    public Transaction createTransaction(Wallet wallet, Order order, BigDecimal amount){
        Transaction transaction2 = new Transaction();
        transaction2.setWallet(wallet);
        transaction2.setAmount(amount);
        transaction2.setOrder(order);
        return transactionRepository.save(transaction2);
    }
    
}
