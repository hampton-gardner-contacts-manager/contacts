import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class contactsmethods {
    public static void showContacts (List<String> contact) {
        String name = "Name";
        String phoneNumber = "Phone Number";
        System.out.printf("%-15s | %-15s |%n-----------------------------------%n", name, phoneNumber);
        contact.sort(Comparator.naturalOrder());
        for (String person : contact) {
            String [] personArray = person.split(" : ");
            System.out.printf("%-15s | %-15s |%n", personArray[0], personArray[1]);
            contacts newcontact = new contacts(personArray[0],personArray[1]);
        }

    }
    public static void addContacts(List<String> contactList, Path file, Scanner input, String phoneNumber) throws IOException {
        List<String> tempList = new ArrayList<>();
        boolean check = false;
        System.out.print("Enter a name:\n>");
        String newContact = input.nextLine();
        System.out.printf("Enter %s's number:%n>", newContact);
        String contactNumber = input.nextLine();

        while (contactNumber.length()!=10&&contactNumber.length()!=7){
            System.out.print("Please enter a ten or seven digit number, try again\n>");
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
            contacts newcontact = new contacts(contactArray[0],contactArray[1]);
            phoneNumber = contactArray[1];
            if (!phoneNumber.equals(contactNumber)) {
                check=true;
            }

            if (newContact.equalsIgnoreCase(contactArray[0])){
                System.out.printf("There's already an existing contact with the name %s. Do you wish to overwrite it? (Y/N?)\n>",newContact);
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
            System.out.println("Contact added");
        }
    }
    public static void searchContact(List<String> contactList,String contactName,Scanner input){
        boolean check = false;
        System.out.print("Please Enter contact name: \n>");
        String searchChoice = input.nextLine();
        for (String contact : contactList) {
            String [] contactArray = contact.split(" : ");
            contacts newcontact = new contacts(contactArray[0],contactArray[1]);
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
    public static void deleteContact(List<String> contactList,Scanner input,String contactName,Path file) throws IOException {
        List<String> tempList = new ArrayList<>();
        showContacts(contactList);
        System.out.print("Enter a name:\n>");
        String newContact = input.nextLine();

        for (String contact : contactList) {
            String [] contactArray = contact.split(" : ");
            contacts newcontact = new contacts(contactArray[0],contactArray[1]);
            contactName = contactArray[0];
            if (!contactName.equalsIgnoreCase(newContact)) {
                tempList.add(contact);
            }
        }
        contactList = tempList;
        Files.write(file, tempList);
        System.out.println("Contact deleted");
    }

}
