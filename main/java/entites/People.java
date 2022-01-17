package entites;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class People {
    @Id
    @GeneratedValue
    private int personId;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    // person attributes
    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private int personAge;

    public int getPersonAge() {
        return personAge;
    }

    public void setPersonAge(int personAge) {
        this.personAge = personAge;
    }



    @ManyToOne
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public People() {
    }

    public People(String firstName, String lastName, int personAge) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personAge = personAge;
    }

    @Override
    public String toString() {
        return "ID: "+personId+"| "+firstName+" "+lastName+", age: "+personAge;
    }
}
