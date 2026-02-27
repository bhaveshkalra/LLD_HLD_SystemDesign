package LowLevelDesign.DesignSplitwise.Expense.Split;

import LowLevelDesign.DesignSplitwise.User.User;

public class Split {

    User user;
    double amountSplit;

    public Split(User user, double amountSplit){
        this.user = user;
        this.amountSplit = amountSplit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmountSplit() {
        return amountSplit;
    }

    public void setAmountSplit(double amountSplit) {
        this.amountSplit = amountSplit;
    }

}
