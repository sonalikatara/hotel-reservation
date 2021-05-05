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
        this.reservations = new ArrayList<Reservation>();
        this.rooms = new HashMap<String, IRoom>();
    };

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

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(newReservation);
        return newReservation;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer){
        ArrayList<Reservation> customerReservations = new ArrayList<Reservation>();
        String customerEmail = customer.getEmail();
        reservations.forEach((reservation) -> {
            if(reservation.getCustomer().getEmail() == customerEmail){
                customerReservations.add(reservation);
            }
        });
        return null;
    }

    //  search for available rooms based on provided checkin and checkout dates.
    public Map<String, IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Map<String, IRoom> availableRooms = new HashMap<String, IRoom>();
        ArrayList<String> bookedRoomNumbers = new ArrayList<String>();

        reservations.forEach(reservation ->{
            if(Validator.dateIsWithinRange(checkInDate,reservation.getCheckInDate(),reservation.getCheckOutDate()) || Validator.dateIsWithinRange(checkOutDate,reservation.getCheckInDate(),reservation.getCheckOutDate())){
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

    public void printAllReservation(){
        reservations.forEach(reservation ->{
            System.out.println(reservation.toString());
        });
    }

}

