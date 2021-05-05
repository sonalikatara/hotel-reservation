package model;
import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation( Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer(){
        return this.customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public IRoom getRoom(){
        return this.room;
    }

    public void setRoom(Room room){
        this.room = room;
    }

    public Date getCheckInDate(){
        return this.checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate(){
        return this.checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }



    @Override
    public String toString(){
        return "Reservation :: { " +
                customer.toString() + "\\n" +
                room.toString() + "\\n" +
                "check in date : " + checkInDate + "\\n" +
                "check out date : " + checkOutDate;

    }
}
