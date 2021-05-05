package service;
import model.Customer;
import model.IRoom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    // create a static variable of type CustomerService
    private static CustomerService customerService;
    public static Collection<Customer> customers;

    // make the constructor private so that this class cannot be instantiated
    private CustomerService(){
        this.customers =  new ArrayList<Customer>();
    };

    //Get the only object available
    public static CustomerService getInstance(){
        if(customerService == null){
            customerService = new CustomerService();
        }
        return customerService;
    }


    public void addCustomer(String email, String firstName, String lastName){
            customers.add( new Customer(firstName,lastName,email));
    }

    public Customer getCustomer(String customerEmail){
        Customer found = customers.stream()
                .filter(customer ->customerEmail.equals(customer.getEmail()))
                .findAny()
                .orElse(null);
        return found;
    }

    public Collection<Customer> getAllCustomers(){
        return customers;
    }
}
