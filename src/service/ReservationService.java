package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Validator;

import java.util.*;

public class ReservationService {
    private static ReservationService reservationService;
    static ArrayList<Reservation> reservations ;
    static Map<String, IRoom> rooms;

    private ReservationService(){
        reservations = new ArrayList<>();
        rooms = new HashMap<>();
    }

    public static ReservationService getInstance(){
        if (reservationService == null){
            reservationService = new ReservationService();
        }
        return reservationService;
    }


    public void addRoom(IRoom room){
        rooms.put(room.getRoomNumber(),room);
    }

    public IRoom getARoom(String roomNumber){
        return rooms.get(roomNumber);
    }

    public static Map<String, IRoom> getAllRooms() {
        return rooms;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(newReservation);
        return newReservation;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer){
        ArrayList<Reservation> customerReservations = new ArrayList<>();
        String customerEmail = customer.getEmail();
        reservations.forEach((reservation) -> {
            if(reservation.getCustomer().getEmail().equals(customerEmail)){
                customerReservations.add(reservation);
            }
        });
        return customerReservations;
    }

    //  search for available rooms based on provided checkIn and checkout dates.
    public Map<String, IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Map<String, IRoom> availableRooms = new HashMap<>();
        ArrayList<String> bookedRoomNumbers = new ArrayList<>();

        reservations.forEach(reservation ->{
            if(Validator.dateIsWithinRange(reservation.getCheckInDate(),checkInDate,checkOutDate) || Validator.dateIsWithinRange(reservation.getCheckOutDate(),checkInDate,checkOutDate) || ( Validator.dateIsWithinRange(checkInDate,reservation.getCheckInDate(),reservation.getCheckOutDate()) && Validator.dateIsWithinRange(checkOutDate,reservation.getCheckInDate(),reservation.getCheckOutDate())) ){
              //  System.out.println("booked  room"+ reservation.getRoom().getRoomNumber());
                bookedRoomNumbers.add(reservation.getRoom().getRoomNumber());
            }
        });

        rooms.forEach( (key,val)->{
            if(!bookedRoomNumbers.contains(key)){
                availableRooms.put(key,val);
            }
        });
        return availableRooms;
    }

    public Collection<Reservation> getAllReservation(){
        return reservations;
    }

    public void printAllReservation(){
        reservations.forEach(reservation-> System.out.println(reservation.toString()));
    }

}

