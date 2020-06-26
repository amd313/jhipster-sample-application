package com.rahamsolutions.booking.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CustomerPayment.
 */
@Entity
@Table(name = "customer_payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private Integer status;

    @Column(name = "amount")
    private Double amount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public CustomerPayment status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public CustomerPayment amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerPayment)) {
            return false;
        }
        return id != null && id.equals(((CustomerPayment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerPayment{" +
            "id=" + getId() +
            ", status=" + getStatus() +
            ", amount=" + getAmount() +
            "}";
    }
}
