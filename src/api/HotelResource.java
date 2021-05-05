package api;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource hotelResource;

    private HotelResource() {}

    public static HotelResource getInstance(){
        if(hotelResource == null){
            hotelResource = new HotelResource();
        }
        return  hotelResource;
    }

    public Customer getCustomer(String email){
        return null;
    }

    public void createACustomer(String email, String firstName, String lastName){

    }

    public IRoom getRoom(String roomNumber){
        return null;
    }

    public Reservation bookARoom(String custom, IRoom room, Date checkInDate, Date CheckOutDate){

        return null;
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail){
        return null;
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
        return null;
    }

}
