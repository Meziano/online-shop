package de.meziane.services.customer.persistence.entity;

import de.meziane.common.model.ContactCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "customer_contacts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
public class CustomerContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 20)
    @ToString.Include
    private ContactCategory category;

    @Column(name = "contact_value", nullable = false, length = 500)
    @ToString.Include
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public CustomerContact(ContactCategory category, String value) {
        this.category = category;
        this.value = value;
    }

    public void changeCategory(ContactCategory category) {
        this.category = category;
    }

    public void changeValue(String value) {
        this.value = value;
    }

    void assignTo(Customer customer) {
        this.customer = customer;
    }

    void unassign() {
        this.customer = null;
    }
}