import java.io.IOException;
import java.nio.*;
import java.io.File.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLOutput;
import java.util.*;


public class contacts {


    public static void showContacts (List<String> contact) {
        String name = "Name";
        String phoneNumber = "Phone Number";
        System.out.printf("%-15s | %-15s |%n-----------------------------------%n", name, phoneNumber);
        contact.sort(Comparator.naturalOrder());
        for (String person : contact) {
            String [] personArray = person.split(" : ");
            System.out.printf("%-15s | %-15s |%n", personArray[0], personArray[1]);
        }

    }
    public static void addContacts(List<String> contactList,Path file,Scanner input,String phoneNumber) throws IOException {
        List<String> tempList = new ArrayList<>();
        boolean check = false;
        System.out.println("Enter a name:\n>");
        String newContact = input.nextLine();
        System.out.printf("Enter %s's number:%n>", newContact);
        String contactNumber = input.nextLine();


//        String tenDigitPattern = "\\d{3}-\\d{3}-\\d{4}";
//        String sevenDigitPattern = "\\d{3}-\\d{4}";
//        while (!contactNumber.matches(tenDigitPattern)&& !contactNumber.matches(sevenDigitPattern)){
//            System.out.println("Invalid Format, try again");
//            contactNumber= input.nextLine();
//        }
        while (contactNumber.length()!=10&&contactNumber.length()!=7){
            System.out.println("Invalid Format, try again");
            contactNumber= input.nextLine();
        }
        if(contactNumber.length()==10){
            String first = contactNumber.substring(0,3);
            String second = contactNumber.substring(3,6);
            String third = contactNumber.substring(6,10);
            contactNumber = first+"-"+second+"-"+third;
        }
        if (contactNumber.length()==7){
            String first = contactNumber.substring(0,3);
            String second = contactNumber.substring(3,7);
            contactNumber = first+"-"+second;
        }

        String contactInfo = newContact + " : " + contactNumber;
        if(contactList.size()==0){
            contactList.add(contactInfo);
        }
        for (String contact : contactList) {
            tempList.add(contact);
            String [] contactArray = contact.split(" : ");
            phoneNumber = contactArray[1];
            if (!phoneNumber.equals(contactNumber)) {
                System.out.println("Phone Number checks out");
                check=true;
            }
//                   else {
//                       check=false;
//                       System.out.println("Existing Phone Number");
//                       break;
//                   }
            if (newContact.equalsIgnoreCase(contactArray[0])){
                System.out.printf("There's already an existing contact with the name %s. Do you wish to overwrite it? (Y/N?)",newContact);
                String choice = input.nextLine();
                if (choice.contains("y")){
                    tempList.remove(contact);
                    check=true;
                }
                else{
                    addContacts(contactList,file,input,phoneNumber);
                    check=false;
                    break;
                }
            }
        }
        if (check){
            tempList.add(contactInfo);
            contactList = tempList;
            Files.write(file, contactList);
        }
    }

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
               contactList = Files.readAllLines(file);
               showContacts(contactList);
           }
           if (userSelection.equalsIgnoreCase("2")){
               addContacts(contactList,file,input,phoneNumber);
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
                   if (!contactName.equalsIgnoreCase(newContact)) {
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
