package de.meziane.services.customer.persistence.entity;

import de.meziane.common.model.CustomerGender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @Column(name = "given_name", nullable = false, length = 100)
    @ToString.Include
    private String givenName;

    @Column(name = "family_name", nullable = false, length = 100)
    @ToString.Include
    private String familyName;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Enumerated(EnumType. STRING)
    @Column (name = "gender", length = 20)
    private CustomerGender gender;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OrderBy("id ASC")
    private List<CustomerContact> contacts = new ArrayList<>();

    public Customer(String givenName, String familyName, LocalDate dob, CustomerGender gender) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.dob = dob;
        this.gender = gender;
    }

    public void changeGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void changeFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void changeDob(LocalDate dob) {
        this.dob = dob;
    }

    public void changeGender(CustomerGender gender) {
        this.gender = gender;
    }

    public void addContact(CustomerContact contact) {
        contacts.add(contact);
        contact.assignTo(this);
    }

    public void removeContact(CustomerContact contact) {
        contacts.remove(contact);
        contact.unassign();
    }
}