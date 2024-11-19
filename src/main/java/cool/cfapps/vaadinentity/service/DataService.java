package cool.cfapps.vaadinentity.service;

import cool.cfapps.vaadinentity.entity.Address;
import cool.cfapps.vaadinentity.entity.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DataService {

    private List<Person> persons = List.of(
            new Person(1L, "John", "Doe", "john.doe@example.com", true,
                    LocalDate.of(1971, 3, 4)),
            new Person(2L, "Jane", "Smith", "jane.smith@example.com", false
                    , LocalDate.of(1975, 6, 12)),
            new Person(3L, "Bob", "Johnson", "bob.johnson@example.com", true,
                    LocalDate.of(1990, 1, 24)),
            new Person(4L, "Alice", "Williams", "alice.williams@example.com", true,
                    LocalDate.of(1985, 11, 23))
    );
    private List<Address> addresses = List.of(
            new Address(1L, "123 Main St", "New York", "USA", "10001"),
            new Address(2L, "456 Elm St", "Los Angeles", "USA", "90001"),
            new Address(3L, "789 Oak St", "Chicago", "USA", "60601"),
            new Address(4L, "101 Park Ave", "Boston", "USA", "02111")
    );

    public List<Person> findAllPersons() {
        return persons;
    }

    public List<Address> findAllAddresses() {
        return addresses;
    }

}
