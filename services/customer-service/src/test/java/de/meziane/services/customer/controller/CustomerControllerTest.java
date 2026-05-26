package de.meziane.services.customer.controller;


import de.meziane.common.model.ContactCategory;
import de.meziane.common.model.CustomerGender;
import de.meziane.services.customer.api.dto.CustomerContactRequest;
import de.meziane.services.customer.api.dto.CustomerContactResponse;
import de.meziane.services.customer.api.dto.CustomerRequest;
import de.meziane.services.customer.api.dto.CustomerResponse;
import de.meziane.services.customer.persistence.entity.Customer;
import de.meziane.services.customer.persistence.entity.CustomerContact;
import de.meziane.services.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.List;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCustomer() throws Exception {
        var request = generateCustomerRequest();
        var response = generateCustomerResponse();

        given(customerService.createCustomer(request)).willReturn(response);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.givenName").value(response.givenName()));
    }

    @Test
    void shouldGetCustomer() throws Exception {
        var response = generateCustomerResponse();
        given(customerService.getCustomerById(1L)).willReturn(response);
        mockMvc.perform(get("/api/customers/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.givenName").value(response.givenName()))
                .andExpect(jsonPath("$.familyName").value(response.familyName()));
    }

    @Test
    void shouldUpdateCustomer() throws Exception {
        var request = generateCustomerRequest();
        var response = generateCustomerResponse();

        given(customerService.updateCustomer(eq(1L), any(CustomerRequest.class)))
                .willReturn(response);

        mockMvc.perform(put("/api/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.givenName").value(response.givenName()))
                .andExpect(jsonPath("$.familyName").value(response.familyName()));
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        willDoNothing().given(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/api/customers/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    private CustomerRequest generateCustomerRequest() {
        return new CustomerRequest(
                "Max",
                "Mustermann",
                LocalDate.of(1990, 1, 1),
                CustomerGender.MALE,
                List.of( new CustomerContactRequest(ContactCategory.EMAIL, "max.Mustermann@test.com"),
                        new CustomerContactRequest(ContactCategory.ADDRESS, "Paul Heidelbach Starsse 1, 34134 Kassel"))
        );
    }

    private CustomerResponse generateCustomerResponse() {
        return new CustomerResponse(
                1L,
                "Max",
                "Mustermann",
                LocalDate.of(1990, 1, 1),
                CustomerGender.UNKNOWN,
                List.of(new CustomerContactResponse(ContactCategory.EMAIL, "max.Mustermann@test.com"),
                        new CustomerContactResponse(ContactCategory.ADDRESS, "Paul Heidelbach Starsse 1, 34134 Kassel"))
                );
    }

}
