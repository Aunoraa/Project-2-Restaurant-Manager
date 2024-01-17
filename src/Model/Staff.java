package Model;

import java.util.Date;

public class Staff {
    private int staffID;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String position;
    private double salary;
    private Date startDate;

    public Staff(int staffID, String fullName, String phoneNumber, String email, String address, String position, double salary, Date startDate) {
        this.staffID = staffID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.position = position;
        this.salary = salary;
        this.startDate = startDate;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Staff [staffID=" + staffID + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", email=" + email + ", address=" + address + ", position=" + position + ", salary=" + salary + ", startDate=" + startDate + "]";
    }
}