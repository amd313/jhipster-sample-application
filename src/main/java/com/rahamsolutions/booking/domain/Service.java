package com.rahamsolutions.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Service entity.
 */
@ApiModel(description = "The Service entity.")
@Entity
@Table(name = "service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "services")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany(mappedBy = "services")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Service name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Service description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Service employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Service addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getServices().add(this);
        return this;
    }

    public Service removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getServices().remove(this);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Service categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Service addCategory(Category category) {
        this.categories.add(category);
        category.getServices().add(this);
        return this;
    }

    public Service removeCategory(Category category) {
        this.categories.remove(category);
        category.getServices().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Service)) {
            return false;
        }
        return id != null && id.equals(((Service) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Service{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
