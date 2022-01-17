package pac;

import entites.Address;
import entites.People;

import javax.persistence.*;
import java.util.Scanner;

public class ResidentManager {
    private static Scanner sc = new Scanner(System.in);
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
    private static ResidentManager residentManager = new ResidentManager();

    public void createPerson() {
        EntityManager em = emf.createEntityManager();

        // creation
        System.out.print("FIRST NAME: ");
        String firstName = sc.nextLine();

        System.out.print("\nLAST NAME: ");
        String lastName = sc.nextLine();

        System.out.print("\nAGE: ");
        int age = sc.nextInt();
        sc.nextLine();

        People people = new People(firstName, lastName, age);
        em.getTransaction().begin();
        em.persist(people);
        em.getTransaction().commit();
        em.close();
        System.out.println(people.getFirstName() + " " + people.getLastName() + ", have been added to the system");
    }


    // Modification
    public void idInput() {
        System.out.println("1. Update Person");
        System.out.println("2. Delete Person");
        System.out.println("3. Connect Person to address");
        System.out.println("4. Disconnect person to address");
        System.out.println("5. Remove Address");
        System.out.println("6. Search Address by ID");
        System.out.println("7. Find out who lives here by addressId");
        System.out.println("8. Search person by personId");
        System.out.println("0. Return to main menu");
        System.out.print("\nCHOICE: ");
        int choice = sc.nextInt();
        System.out.println("\n ");

        if (choice == 1) {
            System.out.print("PERSON ID: ");
            int personId = sc.nextInt();
            sc.nextLine();
            residentManager.editPerson(personId);
        } else if (choice == 2) {
            System.out.println("PERSON ID: ");
            int personId = sc.nextInt();
            residentManager.removePerson(personId);
        } else if (choice == 3) {
            System.out.print("PERSON ID: ");
            int personId = sc.nextInt();
            System.out.println("ADDRESS ID: ");
            int addressId = sc.nextInt();
            residentManager.connectPersonToAddress(personId, addressId);
        } else if (choice == 4) {
            residentManager.disconnectPersonFromAddress();
        } else if (choice == 5) {
            System.out.print("ADDRESS ID: ");
            int addressId = sc.nextInt();
            System.out.print("\nPERSON ID: ");
            int personId = sc.nextInt();
            residentManager.removeAddress(addressId, personId);
        } else if (choice == 6) {
            System.out.print("\nADDRESS ID: ");
            int addressId = sc.nextInt();
            residentManager.searchAddressById(addressId);
        } else if (choice == 7) {
            System.out.println("ADDRESS ID: ");
            int addressId = sc.nextInt();
            residentManager.findOutWhoLivesHereByAddressId(addressId);
        } else if (choice == 8) {
            System.out.print("ENTER PERSON ID: ");
            int personId = sc.nextInt();
            residentManager.findPersonById(personId);
        }
    }


    private void editPerson(int personId) {
        EntityManager em = emf.createEntityManager();
        People people = em.find(People.class, personId);
        Scanner sc = new Scanner(System.in);
        em.getTransaction().begin();
        System.out.print("New First name: ");
        String newFirstName = sc.nextLine();
        System.out.print("New Last name: ");
        String newLastName = sc.nextLine();
        people.setFirstName(newFirstName);
        people.setLastName(newLastName);
        em.getTransaction().commit();
        System.out.println(people.getFirstName() + " " + people.getLastName());
    }



    private void removePerson(int personId) {
        EntityManager em = emf.createEntityManager();
        People people = em.find(People.class, personId);
        people.setPersonId(personId);
        em.getTransaction().begin();
        em.remove(people);
        System.out.println(people + " have been deleted");
        em.getTransaction().commit();
    }


    private void findPersonById(int personId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<People> personQuery = em.createQuery(" SELECT p from People  p where p.personId=:personId", People.class);
        personQuery.setParameter("personId", personId);
        System.out.println();
        try {
            People people = personQuery.getSingleResult();
            people.getPersonId();
            System.out.println(people.getFirstName() + " " + people.getLastName());
        } catch (NoResultException notFoundException) { // if false catch an NoResultException!
            System.out.println("No Person could be found with that id, please read all Persons\n and check the ID:s");
            System.out.println("Exception: " + notFoundException);
        }
    }


    public void createAddress(String streetName, int postCode, String city) {
        EntityManager em = emf.createEntityManager();

        Address address = new Address(streetName, postCode, city);

        em.getTransaction().begin();

        em.persist(address);
        em.getTransaction().commit();
        em.close();
    }


    /**
     * @param personId  takes personId in the parameters
     * @param addressId takes personId in the parameters
     *                  This method tries to find an address and a person which has the id that the user
     *                  have entered in thru the idInput method
     */
    private void connectPersonToAddress(int personId, int addressId) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        People people = em.find(People.class, personId);
        Address address = em.find(Address.class, addressId);

        people.setAddress(address);

        em.getTransaction().commit();
        em.close();
    }

    /**
     * this method disconnects a person from an address, the address will not be removed
     * it will only disconnect this two entites from each other!
     */
    private void disconnectPersonFromAddress() {
        System.out.print("PERSON ID: ");
        int personId = sc.nextInt();
        System.out.print("\nADDRESS ID: ");
        int addressId = sc.nextInt();
        residentManager.removeAddress(personId, addressId);
    }

    /**
     * this method removes an address, but it makes sure that it does not remove
     * the person that is connected to this address, that is why we set people to null
     *
     * @param addressId
     */
    private void removeAddress(int personId, int addressId) {
        EntityManager em = emf.createEntityManager();

        Address address = em.find(Address.class, addressId);
        People people = em.find(People.class, personId);

        em.getTransaction().begin();

        people.setAddress(null);
        address.setPeople(null);
        em.getTransaction().commit();
        em.close();
    }


    private void searchAddressById(int addressId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Address> addressTypedQuery = em.createQuery(" SELECT a from Address  a where a.addressId=:addressId", Address.class);
        addressTypedQuery.setParameter("addressId", addressId);
        System.out.println();
        try {
            Address address = addressTypedQuery.getSingleResult();
            address.getAddressId();
            System.out.println(address.getStreet() + " " + address.getPostCode() + " " + address.getCity() + " there are " +
                    address.getPeople() + " residents who lives here");
        } catch (NoResultException notFoundException) { // if false catch an NoResultException!
            System.out.println("No Person could be found with that id, please read all Persons\n and check the ID:s");
            System.out.println("Exception: " + notFoundException);
        }
    }


    private void findOutWhoLivesHereByAddressId(int addressId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Address> addressTypedQuery = em.createQuery(" SELECT a from Address  a where a.addressId=:addressId", Address.class);
        addressTypedQuery.setParameter("addressId", addressId);
        System.out.println();
        try {
            Address address = addressTypedQuery.getSingleResult();
            address.getAddressId();
            System.out.println(address.getPeople() + " lives on, " + address);
        } catch (NoResultException notFoundException) { // if false catch an NoResultException!
            System.out.println("No Person could be found with that id, please read all Persons\n and check the ID:s");
            System.out.println("Exception: " + notFoundException);
        }
    }
}
