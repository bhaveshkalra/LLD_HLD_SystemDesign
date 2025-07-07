package LowLevelDesign.DesignSplitwise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Time complexity - >0(n!) for n transactions
public class SplitwiseSimplifyDebtAlgo {

    public int minTransactions(int [][] transactions) { 
        Map<Integer, Integer> memberVsBalanceMap = new HashMap<>();
        //compute the overall balance (incoming - outgoing) for each member
        for (int[] txn : transactions) {
            int from = txn[0];
            int to = txn[1];
            int amount = txn[2];
            memberVsBalanceMap.put(from, memberVsBalanceMap.getOrDefault(from, 0) - amount);//giving money
            memberVsBalanceMap.put(from, memberVsBalanceMap.getOrDefault(to, 0) + amount); //receiving money
        }

        //put into the balance list
        List<Integer> balances = new ArrayList<>();
        for(int amount : memberVsBalanceMap.values()) {
            if (amount != 0) {
                balances.add(amount);
            }
        }
        return dfs(balances, 0);
    }

    private int dfs(List<Integer> balanceList, int currentIndex) {
        if (balanceList.size() == 0 || currentIndex >= balanceList.size()) return 0;

        if (balanceList.get(currentIndex) == 0) {
            return dfs(balanceList, currentIndex + 1);
        }

        int currentVal = balanceList.get(currentIndex);
        int minTxnCount = Integer.MAX_VALUE;

        for(int txnIndex = currentIndex + 1; txnIndex < balanceList.size(); txnIndex++) {
            int nextVal = balanceList.get(txnIndex);

            if (currentVal * nextVal < 0) { //find opppsite values +ve and -ve
                balanceList.set(txnIndex, currentVal + nextVal);
                minTxnCount = Math.min(minTxnCount, 1 + dfs(balanceList, currentIndex+1));
                balanceList.set(txnIndex, nextVal);

                if (currentVal + nextVal == 0) {
                    break;
                }
            }
        }
        return minTxnCount;
    }
  
}
