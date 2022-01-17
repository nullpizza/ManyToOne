package entites;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int addressId;

    @OneToMany(mappedBy = "address")
    private List<People> people;



    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }



    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private int postCode;

    @OneToMany
    public List<People> getPeople() {
        if (people == null) {
            people = new ArrayList<>();
        }
        return people;
    }

    public void setPeople(List<People> people) {
        this.people = people;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public Address() {

    }



    public Address(String street, int postCode, String city) {
        this.street = street;
        this.city = city;
        this.postCode = postCode;
    }




    @Override
    public String toString() {
        return street+" "+postCode+", "+city;
    }
}
