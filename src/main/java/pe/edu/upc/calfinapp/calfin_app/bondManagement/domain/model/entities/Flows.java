package pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.entities;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.CashFlows;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer period;

    private LocalDate date;

    private Double beginningBalance;

    private Double interestPayment;

    private Double principalPayment;

    private Double totalPayment;

    private Double endingBalance;

    @ManyToOne
    //@JoinColumn(name = "cashflows_id")
    @JsonBackReference
    private CashFlows cashFlows;

    public Flows(Integer period, LocalDate date, Double beginningBalance, Double interestPayment, Double principalPayment, Double totalPayment, Double endingBalance) {
        this.period = period;
        this.date = date;
        this.beginningBalance = beginningBalance;
        this.interestPayment = interestPayment;
        this.principalPayment = principalPayment;
        this.totalPayment = totalPayment;
        this.endingBalance = endingBalance;
    }

}
