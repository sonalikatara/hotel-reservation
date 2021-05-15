package model;

public class Tester {
    public static void main(String[] args){
        Customer customer = new Customer("first", "last","j@domain.com");
        System.out.println(customer);
       /* Customer customerError = new Customer("first","second","email");
        System.out.println(customerError);*/
    }
}
