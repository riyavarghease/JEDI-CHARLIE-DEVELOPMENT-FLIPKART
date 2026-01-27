package com.flipkart.business;

import com.flipkart.bean.Customer;
import com.flipkart.bean.Role;
import com.flipkart.bean.User;
import com.flipkart.dao.CustomerDaoImpl;
import com.flipkart.dao.CustomerDaoInterface;
import com.flipkart.dao.UserDaoImpl;
import com.flipkart.dao.UserDaoInterface;

import java.util.UUID;

public class CustomerService implements CustomerInterface {

    // Injecting the DAO dependency
    private CustomerDaoInterface customerDao = new CustomerDaoImpl();
    @Override
    public void registerCustomer(String name, String email, String phoneNumber, String password) {
        // 1. Business Logic: Check if customer already exists
        if (customerDao.getCustomerByEmail(email) != null) {
            System.out.println("Registration Failed: Email " + email + " is already registered.");
            return;
        }
        Role customerRole = new Role();
        customerRole.setRoleName("Customer");
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setPasswordHash(password);

        User user = new User();// Ensure this matches your bean's setter (setContact or setPhoneNumber)
        user.setUserId(UUID.randomUUID().toString());
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPasswordHash(password);
        user.setRole(customerRole);
        // 3. DAO Call: Save to MySQL database

        UserDaoInterface userDao = new UserDaoImpl();
        userDao.addUser(user);

        customerDao.addCustomer(customer);
        System.out.println("Business: Customer registration processed for " + customer.getName());
    }

    @Override
    public boolean login(String email, String password) {
        // Business Logic: Retrieve from DB and verify credentials
        Customer customer = customerDao.getCustomerByEmail(email);

        if (customer != null && customer.getPasswordHash().equals(password)) {
            System.out.println("Login successful for: " + email);
            return true;
        }

        System.out.println("Invalid email or password.");
        return false;
    }

    @Override
    public void editProfile(Customer customer) {
        // Business Logic: You could add a check here to see if the customer exists first
        System.out.println("Updating profile for ID: " + customer.getCustomerId());

        // Note: You would need to add an updateCustomer method to your DAO interface
        // similar to the updateCustomerContact method in your demo file.
    }
}