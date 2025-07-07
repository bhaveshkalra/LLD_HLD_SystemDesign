package LowLevelDesign.DesignSplitwise;

import LowLevelDesign.DesignSplitwise.Expense.Split.Split;
import LowLevelDesign.DesignSplitwise.User.User;

import java.util.List;
import java.util.Map;

public class BalanceSheetController {

    public void updateUserExpenseBalanceSheet(User expensePaidBy, List<Split> splits, double totalExpenseAmount){

        //update the total amount paid of the expense paid by user
        UserExpenseBalanceSheet paidByUserExpenseSheet = expensePaidBy.getUserExpenseBalanceSheet();
        paidByUserExpenseSheet.setTotalPayment(paidByUserExpenseSheet.getTotalPayment() + totalExpenseAmount);

        for(Split split : splits) {

            User splitUser = split.getUser();
            UserExpenseBalanceSheet splitUserExpenseSheet = splitUser.getUserExpenseBalanceSheet();
            double splitAmount = split.getAmountSplit();

            if(expensePaidBy.getUserId().equals(splitUser.getUserId())){
                //user 1 who  paid the exepense
                paidByUserExpenseSheet.setTotalYourExpense(paidByUserExpenseSheet.getTotalYourExpense() + splitAmount);
            }
            else {
                //update the balance of paid user
                paidByUserExpenseSheet.setTotalYouGetBack(paidByUserExpenseSheet.getTotalYouGetBack() + splitAmount);
                Balance userSplitBalance;
                if(paidByUserExpenseSheet.getUserVsBalance().containsKey(splitUser.getUserId())) {
                    userSplitBalance = paidByUserExpenseSheet.getUserVsBalance().get(splitUser.getUserId());
                }
                else {
                    userSplitBalance = new Balance();
                    paidByUserExpenseSheet.getUserVsBalance().put(splitUser.getUserId(), userSplitBalance);
                }
                //this needs to paid for each user who owes paid user
                userSplitBalance.setAmountGetBack(userSplitBalance.getAmountGetBack() + splitAmount);
            
                
                //update the balance sheet of owe user
                splitUserExpenseSheet.setTotalYourExpense(splitUserExpenseSheet.getTotalYourExpense() + splitAmount);
                splitUserExpenseSheet.setTotalYouOwe(splitUserExpenseSheet.getTotalYouOwe() + splitAmount);
                Balance userPaidBalance;
                if(splitUserExpenseSheet.getUserVsBalance().containsKey(expensePaidBy.getUserId())){
                    userPaidBalance = splitUserExpenseSheet.getUserVsBalance().get(expensePaidBy.getUserId());
                }
                else{
                    userPaidBalance = new Balance();
                    splitUserExpenseSheet.getUserVsBalance().put(expensePaidBy.getUserId(), userPaidBalance);
                }
                userPaidBalance.setAmountOwe(userPaidBalance.getAmountOwe() + splitAmount);
            }
        }
    }

    public void showBalanceSheetOfUser(User user){

        System.out.println("---------------------------------------");

        System.out.println("Balance sheet of user : " + user.getUserId());

        UserExpenseBalanceSheet userExpenseBalanceSheet =  user.getUserExpenseBalanceSheet();

        System.out.println("TotalYourExpense: " + userExpenseBalanceSheet.getTotalYourExpense());
        System.out.println("TotalGetBack: " + userExpenseBalanceSheet.getTotalYouGetBack());
        System.out.println("TotalYourOwe: " + userExpenseBalanceSheet.getTotalYouOwe());
        System.out.println("TotalPaymnetMade: " + userExpenseBalanceSheet.getTotalPayment());
        for(Map.Entry<String, Balance> entry : userExpenseBalanceSheet.getUserVsBalance().entrySet()){

            String userID = entry.getKey();
            Balance balance = entry.getValue();

            System.out.println("userID:" + userID + " YouGetBack:" + balance.getAmountGetBack() + " YouOwe:" + balance.getAmountOwe());
        }

        System.out.println("---------------------------------------");

    }
}
