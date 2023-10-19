import javax.xml.namespace.QName;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class contacts extends contactsmethods{
    public String name;
    public String phonenumber;
    public contacts(String name,String phonenumber){
        this.name = name;
        this.phonenumber = phonenumber;
    }
    public static void main(String[] args) throws IOException {
        String message = "1. Show All Contacts\n2. Add new contact\n3. Search contacts by name\n4. Delete Contact\n5. Exit\nEnter Choice >";
        String contactFile = "contacts.txt";
        String contactsDirectory = "contacts";
        String phoneNumber = "";
        String contactName = "";
        boolean flag = true;
        Path directory = Paths.get(contactsDirectory);
        Path file = Paths.get(contactsDirectory, contactFile);


        if (Files.notExists(directory)) {
            Files.createDirectory(directory);
        }
        if (Files.notExists(file)) {
            Files.createFile(file);
        }
        List<String> contactList = Files.readAllLines(file);
        while (flag) {
            System.out.print(message);
            Scanner input = new Scanner(System.in);
            String userSelection = input.nextLine();
            boolean check1 = true;
            while(check1){
                switch (userSelection) {
                    case "1" -> {
                        contactList = Files.readAllLines(file);
                        showContacts(contactList);
                        check1 = false;
                    }
                    case "2" -> {
                        contactList = Files.readAllLines(file);
                        addContacts(contactList, file, input, phoneNumber);
                        check1 = false;
                    }
                    case "3" -> {
                        contactList = Files.readAllLines(file);
                        searchContact(contactList, contactName, input);
                        check1 = false;
                    }
                    case "4" -> {
                        contactList = Files.readAllLines(file);
                        deleteContact(contactList, input, contactName, file);
                        check1 = false;
                    }
                    case "5" -> {
                        System.out.println("Goodbye!");
                        flag = false;
                        check1 = false;
                    }
                    default -> {
                        System.out.println("Invalid choice, try again");
                        System.out.println(message);
                        userSelection =input.nextLine();
                    }
                }
            }
        }
    }
}
