package rest.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private final Integer id;
    private final String firstName;
    private final String lastName;

    private User(UserBuilder builder){
        this(
                builder.id,
                builder.firstname,
                builder.lastname
        );
    }

    public User(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public static class UserBuilder{
        private Integer id;
        private String firstname;
        private String lastname;

        public UserBuilder(){

        };

        public UserBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder setFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserBuilder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }
    }
}
