package cool.cfapps.vaadinentity.service;

import cool.cfapps.vaadinentity.entity.Address;
import cool.cfapps.vaadinentity.entity.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    private final List<Person> persons = new ArrayList<>();
    private final List<Address> addresses = new ArrayList<>();

    public DataService() {
        persons.addAll(List.of(
                new Person(1L, "John", "Doe", "john.doe@example.com", true,
                        LocalDate.of(1971, 3, 4)),
                new Person(2L, "Jane", "Smith", "jane.smith@example.com", false
                        , LocalDate.of(1975, 6, 12)),
                new Person(3L, "Bob", "Johnson", "bob.johnson@example.com", true,
                        LocalDate.of(1990, 1, 24)),
                new Person(4L, "Alice", "Williams", "alice.williams@example.com", true,
                        LocalDate.of(1985, 11, 23))));

        addresses.addAll(List.of(
                new Address(1L, "123 Main St", "New York", "USA", "10001"),
                new Address(2L, "456 Elm St", "Los Angeles", "USA", "90001"),
                new Address(3L, "789 Oak St", "Chicago", "USA", "60601"),
                new Address(4L, "101 Park Ave", "Boston", "USA", "02111")));
    }


    public List<Person> findAllPersons() {
        return persons;
    }

    public List<Address> findAllAddresses() {
        return addresses;
    }

    public void savePerson(Person person) {
        if (person.getPersonId() == null) {
            Long newId = persons.stream().mapToLong(Person::getPersonId).max().orElse(0L) + 1;
            person.setPersonId(newId);
            persons.add(person);
        } else {
            persons.replaceAll(p -> p.getPersonId().equals(person.getPersonId()) ? person : p);
        }
    }

    public void deletePerson(Person person) {
        persons.removeIf(p -> p.getPersonId().equals(person.getPersonId()));
    }

    public void saveAddress(Address address) {
        if (address.getAddressId() == null) {
            Long newId = addresses.stream().mapToLong(Address::getAddressId).max().orElse(0L) + 1;
            address.setAddressId(newId);
            addresses.add(address);
        } else {
            addresses.replaceAll(a -> a.getAddressId().equals(address.getAddressId()) ? address : a);
        }
    }

    public void deleteAddress(Address address) {
        addresses.removeIf(a -> a.getAddressId().equals(address.getAddressId()));
    }
}
