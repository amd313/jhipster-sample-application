package com.rahamsolutions.booking.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ServicePrice.
 */
@Entity
@Table(name = "service_price")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServicePrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

    @OneToOne
    @JoinColumn(unique = true)
    private Service service;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public ServicePrice amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Employee getEmployee() {
        return employee;
    }

    public ServicePrice employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Service getService() {
        return service;
    }

    public ServicePrice service(Service service) {
        this.service = service;
        return this;
    }

    public void setService(Service service) {
        this.service = service;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicePrice)) {
            return false;
        }
        return id != null && id.equals(((ServicePrice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicePrice{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            "}";
    }
}
