package Model;

public class EmployeeAccount {

    private int accountId;
    private String username;
    private String phoneNumber;
    private String email;

    public EmployeeAccount(int accountId, String username, String phoneNumber, String email) {
        this.accountId = accountId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
