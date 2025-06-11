package com.calfin.calfin_app.cashFlow.domain.model.aggregates;

import com.calfin.calfin_app.cashFlow.domain.model.commands.CreateCashFlowCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class CashFlow {
    @Id
    private Long id;

    private Date date;

    private Integer beginningBalance;

    private Integer interestPayment;

    private Integer principalPayment;

    private Integer totalPayment;

    private Integer endingBalance;

    public CashFlow(CreateCashFlowCommand command){
        this.date = command.date();
        this.beginningBalance = command.beginningBalance();
        this.interestPayment = command.interestPayment();
        this.principalPayment = command.principalPayment();
        this.totalPayment = command.totalPayment();
        this.endingBalance = command.endingBalance();
    }

}
