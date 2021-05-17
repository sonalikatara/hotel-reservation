import api.AdminResource;
import api.HotelResource;
import model.*;
import service.ReservationService;

import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

public class AdminMenu {
    static final Scanner input = new Scanner(System.in);
    static AdminResource adminResource =  AdminResource.getInstance();
    static HotelResource hotelResource = HotelResource.getInstance();

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
        System.out.println("Please select a number for the menu option ");
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
            ReservationService.printAllReservations();
         /*   for (Reservation reservation: reservations) {
                System.out.println(reservation.toString());
            }*/
        } else System.out.println("There are no reservations yet");
    }

    public static void addARoom() throws Exception {
        String addRoom = "y";
        while(addRoom.equals("y")){
            IRoom room;
            RoomType roomType = null;
            // Boolean isFree = true;
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

            room = new Room(roomNumber, roomPrice, roomType);
            adminResource.addRoom(room);
            input.nextLine();

            System.out.println("Would you like to add another room (y/n) ? :");
            addRoom = input.nextLine().toLowerCase().trim();
            while(!addRoom.equals("y") && !addRoom.equals("n")){
                System.out.println("Please enter y (yes) or n (no) ");
                addRoom = input.nextLine().toLowerCase().trim();
            }
        }

    }

    public static void addATestData(){
        IRoom room1 =  new Room("100", 0.0, RoomType.SINGLE);
        IRoom room2 =  new Room("200", 250.0, RoomType.DOUBLE);
        try {
            adminResource.addRoom(room1);
            adminResource.addRoom(room2);
        } catch(Exception e){

        }
        hotelResource.createACustomer("john@domain.com","John","Hopkins");
        System.out.println("Test rooms 100 and 200 added to the system. test account john@domain.com created ");
    }

    public static void start() {
        boolean quit = false;
        String choice;
        do{
            displayMenu();
            choice = input.nextLine().trim();
            try {
                switch (choice) {
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
                        try {
                            addARoom();
                        } catch(Exception e){
                            System.out.println("Unable to add the room. "+ e.getMessage());
                            input.nextLine();
                        }
                        break;
                    case "5":
                        addATestData();
                        break;
                    case "6":
                        MainMenu mainMenu = new MainMenu();
                        mainMenu.start();
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid Option. Please try again!");
                }
            } catch (Exception e){
                System.out.println("Invalid Input. Please try again! ");
                input.nextLine();
            }
        }
        while(!quit);
    }

}
