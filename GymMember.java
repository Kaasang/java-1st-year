/**
 * The GymMember class represents a general gym member with basic details
 * such as name, contact information, gender, date of birth, and membership info.
 * 
 * This abstract class serves as a parent class for specific types of gym members
 * (e.g., RegularMember, PremiumMember) and includes shared properties and methods
 * like attendance tracking, loyalty points, and membership status.
 * 
 * @author (Nipekshya Shakya)
 * @version (13 April 2025)
 */



public abstract class GymMember {
    protected int id;
    protected String name;
    protected String location;
    protected String phone;
    protected String email;
    protected String gender;
    protected String DOB;
    protected String membershipStartDate;
    protected int attendance = 0;
    protected double loyaltyPoints = 0.0;
    protected boolean activeStatus = false;

        /**
     * Constructs a GymMember object with the given personal and membership details.
     *
     * @param id The unique ID of the member
     * @param name The full name of the member
     * @param location The residential location of the member
     * @param phone The contact number of the member
     * @param email The email address of the member
     * @param gender The gender of the member
     * @param DOB The date of birth of the member
     * @param membershipStartDate The date when the membership started
     */

    public GymMember(int id, String name, String location, String phone, String email,
                     String gender, String DOB, String membershipStartDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.DOB = DOB;
        this.membershipStartDate = membershipStartDate;
    }

    // Accessor Methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getDOB() {
        return DOB;
    }

    public String getMembershipStartDate() {
        return membershipStartDate;
    }

    public int getAttendance() {
        return attendance;
    }

    public double getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

        /**
     * Abstract method to be implemented by subclasses to define
     * how attendance should be marked for different types of members.
     */

    public abstract void markAttendance();

        /**
     * Activates the member's gym membership.
     * Deactivates the member's gym membership.
     */

    public void activateMembership() {
        activeStatus = true;
    }

    public void deactivateMembership() {
        if (activeStatus) {
            activeStatus = false;
        }
    }

        /**
     * Resets the member's activity by setting attendance to 0,
     * loyalty points to 0.0, and deactivating the membership.
     */

    public void resetMember() {
        activeStatus = false;
        attendance = 0;
        loyaltyPoints = 0.0;
    }

    /**
     * Adds loyalty points to the member's account.
     * @param points The number of points to add
     */
    public void addLoyaltyPoints(int points) {
        if (points > 0) {
            this.loyaltyPoints += points;
        }
    }

        /**
     * Displays the member's full details including ID, name,
     * contact information, attendance, loyalty points, and status.
     */

    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Location: " + location);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Gender: " + gender);
        System.out.println("Date of Birth: " + DOB);
        System.out.println("Membership Start Date: " + membershipStartDate);
        System.out.println("Attendance: " + attendance);
        System.out.println("Loyalty Points: " + loyaltyPoints);
        System.out.println("Active Status: " + activeStatus);
    }
}