package com.bulq.bulq_commerce.payload.wallet;

import java.math.BigDecimal;
import java.util.List;

import com.bulq.bulq_commerce.payload.transaction.TransactionViewDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminWalletViewDTO {
    
    private long id;

    private String walletName;

    private BigDecimal balance;

    private List<TransactionViewDTO> actions;
}
