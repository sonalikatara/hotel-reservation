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
        return this.checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString(){
        return "Reservation for " +
                customer.toString() + " \n" +
                room.toString() + "\n" +
                "Checkin date : " + checkInDate + "\n" +
                "Checkout date : " + checkOutDate;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null || obj.getClass() != this.getClass()) { return false; }
        Reservation reservation = (Reservation) obj;
        return customer == reservation.customer && (room == reservation.room || (room != null && room.equals(reservation.getRoom()))) && (checkInDate == reservation.getCheckInDate() || (checkInDate != null && checkInDate.equals(reservation.getCheckInDate()))) && (checkOutDate == reservation.getCheckOutDate() || (checkOutDate != null && checkOutDate.equals(reservation.getCheckOutDate())));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customer == null) ? 0 : customer.hashCode());
        result = prime * result + ((room == null) ? 0 : room.hashCode());
        result = prime * result + ((checkInDate == null) ? 0 : checkInDate.hashCode());
        result = prime * result + ((checkOutDate == null) ? 0 : checkOutDate.hashCode());
        return result;
    }


}
