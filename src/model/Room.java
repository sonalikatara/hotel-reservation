package model;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType roomType;

    public void setRoomNumber(String rmNbr){
        this.roomNumber =rmNbr;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public Double getPrice(){ return this.price; }

    public void setRoomType(RoomType rmTyp) {
        this.roomType = rmTyp;
    }

    public Room(String roomNumber, Double price, RoomType roomType){
        super();
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber(){
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.getPrice();
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString(){
        return getRoomNumber() + getRoomPrice() + getRoomType();
    }
}
