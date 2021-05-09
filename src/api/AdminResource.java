package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AdminResource {
    private static AdminResource adminResource;
    static CustomerService customerService = CustomerService.getInstance();
    static ReservationService reservationService = ReservationService.getInstance();


    private AdminResource() { }

    public static AdminResource getInstance(){
        if(adminResource == null){
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(IRoom room){
        reservationService.addRoom(room);
    }

    public Map<String, IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public Collection<Reservation> getAllReservations(){
        Collection<Reservation> allReservations;
        allReservations = reservationService.getAllReservation();
        return allReservations;
    }

}
