/**
 * The RegularMember class represents a standard gym member with a basic membership plan.
 * It extends the abstract GymMember class and includes additional attributes and methods
 * specific to regular members such as plan upgrades, referral source tracking, and 
 * eligibility for upgrading based on attendance.
 * @Author(Nipekshya Shakya)  
 * @Version: (12 April 2025)
 * */
 
public class RegularMember extends GymMember {
    private final int attendanceLimit = 30;
    private boolean isEligibleForUpgrade = false;
    private String removalReason = "";
    private String referralSource;
    private String plan = "basic";
    private double price = 6500.0;

        /**
     * Constructs a RegularMember with personal and membership details.
     *
     * @param id The unique ID of the member
     * @param name The full name of the member
     * @param location The residential location of the member
     * @param phone The contact number of the member
     * @param email The email address of the member
     * @param gender The gender of the member
     * @param DOB The date of birth of the member
     * @param membershipStartDate The date when the membership started
     * @param referralSource The source from which the member was referred
     */

    public RegularMember(int id, String name, String location, String phone, String email,
                        String gender, String DOB, String membershipStartDate, String referralSource) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.referralSource = referralSource;
    }

    // Accessor methods
    public int getAttendanceLimit() {
        return attendanceLimit;
    }

    public boolean isEligibleForUpgrade() {
        return isEligibleForUpgrade;
    }

    public String getRemovalReason() {
        return removalReason;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public String getPlan() {
        return plan;
    }

    public double getPrice() {
        return price;
    }

        /**
     * Increases attendance by one and adds loyalty points.
     * If attendance reaches the limit, marks the member as eligible for an upgrade.
     */

    @Override
    public void markAttendance() {
        attendance++;
        loyaltyPoints += 5;
        if (attendance >= attendanceLimit) {
            isEligibleForUpgrade = true;
        }
    }

        /**
     * Returns the price of the specified plan.
     *
     * @param plan The name of the plan to check
     * @return Price of the plan, or -1 if the plan is invalid
     */

    public double getPlanPrice(String plan) {
        switch (plan.toLowerCase()) {
            case "basic":
                return 6500;
            case "standard":
                return 12500;
            case "deluxe":
                return 18500;
            default:
                return -1; // Invalid plan
        }
    }

        /**
     * Attempts to upgrade the member's plan to a new plan.
     * Checks eligibility, validity of the new plan, and ensures it's not the current plan.
     *
     * @param newPlan The new plan to upgrade to
     * @return A message indicating success or failure of the upgrade
     */

    public String upgradePlan(String newPlan) {
        if (newPlan.equalsIgnoreCase(plan)) {
            return "You are already subscribed to this plan.";
        }
        
        double newPrice = getPlanPrice(newPlan);
        if (newPrice == -1) {
            return "Invalid plan selected.";
        }

        plan = newPlan;
        price = newPrice;
        return "Plan upgraded to " + plan + " at price " + price + ".";
    }

        /**
     * Displays all details of the regular member including plan,
     * price, and removal reason (if available).
     */

    public void revertRegularMember(String removalReason) {
        super.resetMember();
        this.isEligibleForUpgrade = false;
        this.plan = "basic";
        this.price = 6500.0;
        this.removalReason = removalReason;
    }

    // Display method
    @Override
    public void display() {
        super.display();
        System.out.println("Plan: " + plan);
        System.out.println("Price: " + price);
        if (!removalReason.isEmpty()) {
            System.out.println("Removal Reason: " + removalReason);
        }
    }
}