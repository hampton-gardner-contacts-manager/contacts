import java.io.IOException;
import java.nio.*;
import java.io.File.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class contacts {

    public static void main(String[] args) throws IOException {

    String contactFile = "contacts.txt";
    String contactsDirectory = "contacts";
    String phoneNumber = "";
    String contactName;

    Path directory = Paths.get(contactsDirectory);
    Path file = Paths.get(contactsDirectory, contactFile);


       if (Files.notExists(directory)) {
           Files.createDirectory(directory);
       }

       if (Files.notExists(file)) {
           Files.createFile(file);
       }

       boolean flag = true;
       List<String> contactList = Files.readAllLines(file);


       while (flag) {
           System.out.println("1. Show All Contacts\n2. Add new contact\n3. Search contacts by name\n4. Delete Contact\n5. Exit");
           Scanner input = new Scanner(System.in);
           String userSelection = input.nextLine();

           if (userSelection.equalsIgnoreCase("1")){
               System.out.println(contactList);
           }
           if (userSelection.equalsIgnoreCase("2")){
               List<String> tempList = new ArrayList<>();
               boolean check = false;
               System.out.println("Enter a name:");
               String newContact = input.nextLine();
               System.out.printf("Enter %s's number:%n", newContact);
               String contactNumber = input.nextLine();
               String contactInfo = newContact + " : " + contactNumber;
               if(contactList.size()==0){
                   contactList.add(contactInfo);
               }
               for (String contact : contactList) {
                   tempList.add(contact);
                   String [] contactArray = contact.split(" : ");
                   phoneNumber = contactArray[1];
                   if (!phoneNumber.equals(contactNumber)) {
                       check=true;
                   }
                   else {
                       check=false;
                       System.out.println("Existing Phone Number");
                       break;
                   }
               }
               if (check){
                   tempList.add(contactInfo);
                   contactList = tempList;
                   Files.write(file, contactList);
               }
           }
           if (userSelection.equalsIgnoreCase("3")){
               boolean check = false;
               System.out.println("Please Enter contact name: ");
               String searchChoice = input.nextLine();
               for (String contact : contactList) {
                   String [] contactArray = contact.split(" : ");
                   contactName = contactArray[0];
                   if (contactName.equalsIgnoreCase(searchChoice)) {
                       System.out.printf("Here is your contact, %s%n",contact);
                       check = true;
                       break;
                   }
               }
               if (check == false){
                   System.out.println("Contact could not be found, check spelling.");
               }
           }
           if (userSelection.equalsIgnoreCase("4")){
               List<String> tempList = new ArrayList<>();
               System.out.println("Enter a name:");
               String newContact = input.nextLine();

               for (String contact : contactList) {
                   String [] contactArray = contact.split(" : ");
                   contactName = contactArray[0];
                   if (!contactName.equals(newContact)) {
                       tempList.add(contact);
                   }
               }
               contactList = tempList;
               Files.write(file, tempList);
           }
           if (userSelection.equals("5")) {
               flag = false;
           }
       }















    }








}
