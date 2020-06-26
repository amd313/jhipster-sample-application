package com.rahamsolutions.booking.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A BookedSlots.
 */
@Entity
@Table(name = "booked_slots")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BookedSlots implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slot")
    private Instant slot;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "status")
    private Integer status;

    @Column(name = "payment_status")
    private Integer paymentStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private Customer customer;

    @OneToOne
    @JoinColumn(unique = true)
    private Service service;

    @OneToOne
    @JoinColumn(unique = true)
    private ServicePrice servicePrice;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

    @OneToOne
    @JoinColumn(unique = true)
    private CustomerPayment payment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSlot() {
        return slot;
    }

    public BookedSlots slot(Instant slot) {
        this.slot = slot;
        return this;
    }

    public void setSlot(Instant slot) {
        this.slot = slot;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BookedSlots customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public BookedSlots employeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public BookedSlots serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public BookedSlots categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getStatus() {
        return status;
    }

    public BookedSlots status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public BookedSlots paymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BookedSlots customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public BookedSlots service(Service service) {
        this.service = service;
        return this;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public ServicePrice getServicePrice() {
        return servicePrice;
    }

    public BookedSlots servicePrice(ServicePrice servicePrice) {
        this.servicePrice = servicePrice;
        return this;
    }

    public void setServicePrice(ServicePrice servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Employee getEmployee() {
        return employee;
    }

    public BookedSlots employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public CustomerPayment getPayment() {
        return payment;
    }

    public BookedSlots payment(CustomerPayment customerPayment) {
        this.payment = customerPayment;
        return this;
    }

    public void setPayment(CustomerPayment customerPayment) {
        this.payment = customerPayment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookedSlots)) {
            return false;
        }
        return id != null && id.equals(((BookedSlots) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookedSlots{" +
            "id=" + getId() +
            ", slot='" + getSlot() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", employeeName='" + getEmployeeName() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", categoryName='" + getCategoryName() + "'" +
            ", status=" + getStatus() +
            ", paymentStatus=" + getPaymentStatus() +
            "}";
    }
}
