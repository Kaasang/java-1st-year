/**
 * The PremiumMember class represents a gym member with a premium membership plan.
 * It extends the abstract GymMember class and includes additional features 
 * such as payment tracking, personal trainer assignment, and discount calculations.
 * *Author( Nipekshya Shakya  )
 * Version( 12 April 2025)
 */

public class PremiumMember extends GymMember {
    private double premiumCharge;
    private String personalTrainer;
    private String referralSource;
    private boolean isFullPayment = false;
    private double paidAmount = 0.0;
    private double discountAmount = 0.0;
    private String plan = "Basic"; // Default plan

     /**
     * Constructor for the PremiumMember class.
     * 
     * Initializes a new PremiumMember with the provided details, including the
     * personal trainer's name, referral source, and premium charge.
     * 
     * @param id The unique ID of the gym member.
     * @param name The name of the gym member.
     * @param location The location of the gym member.
     * @param phone The phone number of the gym member.
     * @param gender The gender of the gym member.
     * @param DOB The date of birth of the gym member.
     * @param personalTrainer The name of the personal trainer assigned to the member.
     * @param referralSource The source from which the member was referred.
     * @param premiumCharge The premium charge for the membership.
     */
    
    public PremiumMember(int id, String name, String location, String phone, String gender,
                         String DOB, String personalTrainer, String referralSource, double premiumCharge) {
        super(id, name, location, phone, "", gender, DOB, "");
        this.personalTrainer = personalTrainer;
        this.referralSource = referralSource;
        this.premiumCharge = premiumCharge;
    }

    // Accessor methods
    public double getPremiumCharge() {
        return premiumCharge;
    }

    public String getPersonalTrainer() {
        return personalTrainer;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public boolean isFullPayment() {
        return isFullPayment;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public String getPlan() {
        return plan;
    }

     /**
     * Marks attendance for the Premium Member and increases loyalty points.
     * 
     * This method overrides the abstract markAttendance method from GymMember.
     * It increments the attendance and awards 5 loyalty points each time attendance
     * is marked.
     */
    @Override
    public void markAttendance() {
        attendance++;
        loyaltyPoints += 5; // Increment loyalty points
    }

    /**
     * Method for paying the due amount for the Premium Member.
     * 
     * This method allows the member to pay the remaining amount towards the membership.
     * If the payment exceeds the premium charge or if the payment is complete, 
     * it handles those cases and returns appropriate messages.
     * 
     * @param amount The amount being paid by the member.
     * @return A message indicating the result of the payment.
     */
    public String payDueAmount(double amount) {
        if (isFullPayment) {
            return "Payment is already complete.";
        }

        paidAmount += amount;

        if (paidAmount > premiumCharge) {
            return "Paid amount exceeds the premium charge.";
        }

        if (paidAmount == premiumCharge) {
            isFullPayment = true;
        }

        double remainingAmount = premiumCharge - paidAmount;
        return "Payment successful. Remaining amount to be paid: " + remainingAmount;
    }

    /**
     * Method to calculate a discount for the Premium Member.
     * 
     * If the full payment has been made, a 10% discount on the premium charge is
     * calculated and printed. If payment is not full, no discount is available.
     */
    public void calculateDiscount() {
        if (isFullPayment) {
            discountAmount = 0.10 * premiumCharge; // 10% discount
            System.out.println("Discount calculated: " + discountAmount);
        } else {
            discountAmount = 0.0;
            System.out.println("No discount available, payment not full.");
        }
    }

    /**
     * Reverts the details of a Premium Member to their initial state.
     * 
     * This method resets the member's personal trainer, payment status, 
     * paid amount, and discount amount. It also calls the resetMember method 
     * from the super class to reset common member details.
     */
    public void revertPremiumMember() {
        super.resetMember();
        this.personalTrainer = "";
        this.isFullPayment = false;
        this.paidAmount = 0.0;
        this.discountAmount = 0.0;
    }

    /**
     * Displays the details of the Premium Member.
     * 
     * This method overrides the display method from GymMember. It shows details
     * specific to Premium Member, including personal trainer, payment status, 
     * and discount information.
     */
    @Override
    public void display() {
        super.display();
        System.out.println("Personal Trainer: " + personalTrainer);
        System.out.println("Paid Amount: " + paidAmount);
        System.out.println("Full Payment Status: " + isFullPayment);
        double remainingAmount = premiumCharge - paidAmount;
        System.out.println("Remaining Amount to be Paid: " + remainingAmount);
        if (isFullPayment) {
            System.out.println("Discount Amount: " + discountAmount);
        }
    }

    /**
     * Upgrades the premium member's plan to a new plan.
     * 
     * @param newPlan The new plan to upgrade to (Basic, Standard, or Deluxe)
     * @return A message indicating the result of the upgrade
     */
    public String upgradePlan(String newPlan) {
        if (newPlan.equalsIgnoreCase(plan)) {
            return "You are already subscribed to this plan.";
        }

        double newCharge = getPlanPrice(newPlan);
        if (newCharge == -1) {
            return "Invalid plan selected.";
        }

        // Calculate the difference in charge
        double chargeDifference = newCharge - premiumCharge;
        
        // Update the plan and charge
        this.plan = newPlan;
        this.premiumCharge = newCharge;
        
        // Reset payment status if upgrading to a more expensive plan
        if (chargeDifference > 0) {
            this.isFullPayment = false;
            this.paidAmount = 0.0;
            return "Plan upgraded to " + plan + ". New charge: " + premiumCharge + 
                   ". Please make the new payment.";
        } else {
            return "Plan upgraded to " + plan + ". New charge: " + premiumCharge;
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
                return 50000.0;
            case "standard":
                return 75000.0;
            case "deluxe":
                return 100000.0;
            default:
                return -1; // Invalid plan
        }
    }
}