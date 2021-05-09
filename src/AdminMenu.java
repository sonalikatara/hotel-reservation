import api.AdminResource;
import model.*;

import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

public class AdminMenu {
    static final Scanner input = new Scanner(System.in);
    static AdminResource adminResource =  AdminResource.getInstance();

    public AdminMenu(){
        super();
    }
    private static void displayMenu(){
        System.out.println("Admin Menu");
        System.out.println("____________________________________________");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Add Test Data");
        System.out.println("6. Back to Main Menu");
        System.out.println("____________________________________________");
        System.out.println("Please select a number for the menu option");
    }

    public static void seeAllCustomers(){
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (!customers.isEmpty()) {
            for (Customer customer : customers) {
                System.out.println(customer.toString());
            }
        } else {
            System.out.println("There is no customers yet");
        }
    }

    public static void seeAllRooms(){
        Map<String, IRoom> rooms;
        rooms = adminResource.getAllRooms();
        if (!rooms.isEmpty()) rooms.forEach((k, room) -> System.out.println(room.toString()));
        else System.out.println("There are no rooms yet");
    }

    public static void seeAllReservations(){
        Collection<Reservation> reservations;
        reservations = adminResource.getAllReservations();
        if (!reservations.isEmpty()) {
            for (Reservation reservation: reservations) {
                System.out.println(reservation.toString());
            }
        } else System.out.println("There are no reservations yet");
    }

    public static void addARoom(){
        IRoom room;
        RoomType roomType = null;
       // Boolean isFree = true;

        input.nextLine();
        System.out.println("Enter room number:");
        String roomNumber = input.nextLine();
        System.out.println("Enter price per night:");
        Double roomPrice = input.nextDouble();
        int type;
        do {
            System.out.println("Enter room type: 1 - Single bed, 2 - Double bed");
            type = input.nextInt();
            if (type == 1) {
                roomType = RoomType.SINGLE;
            } else if (type == 2) {
                roomType = RoomType.DOUBLE;
            } else {
                System.out.println("Invalid input");
            }
        } while (type != 1 && type != 2);

        room = new Room(roomNumber, roomPrice, roomType, true);
        adminResource.addRoom(room);
    }

    public static void addATestData(){
      System.out.println("Test data");
    }

    public static void start() {
        boolean quit = false;
        String choice;
        do{
            displayMenu();
            choice = input.next().trim();
            switch(choice){
                case "1":
                    seeAllCustomers();
                    break;
                case "2":
                    seeAllRooms();
                    break;
                case "3":
                    seeAllReservations();
                    break;
                case "4":
                    addARoom();
                    break;
                case "5":
                    addATestData();
                    break;
                case "6":
                    quit=true;
                    break;
            }
        }
        while(!quit);
    }

}
