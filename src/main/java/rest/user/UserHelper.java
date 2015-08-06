package rest.user;

public class UserHelper {
    private String login;
    private String firstName;
    private String lastName;
    private String oldPassword;
    private String newPassword;

    public UserHelper(){

    }

    public UserHelper(String login, String lastName, String firstName, String oldPassword, String newPassword) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UserHelper{" +
                "login='" + login + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
