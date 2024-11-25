package com.alogo.floyd;

import java.util.*;

class AccountTransferGraph {
    private final Map<String, List<String>> adjacencyMap=new HashMap<>();
    //add a transfer from one account to another
    public void addAccountTransfer(String source,String destination){
        adjacencyMap.computeIfAbsent(source, k -> new ArrayList<>()).add(destination);
    }
    //retrieve all transfers from a given account
    public List<String> getTransfers(String account){
        return adjacencyMap.getOrDefault(account,new ArrayList<>());
    }
    //retrieve all accounts
    public Set<String> getAllAccounts(){
        return adjacencyMap.keySet();
    }
}
class FloydCycleDetector{
   public static boolean isCyclePresent(AccountTransferGraph graph,String start){
       String slow=start,fast=start;
       while(true){
           // Move slow pointer one step
           slow = next(graph, slow);

           // Move fast pointer two steps
           fast = next(graph, next(graph, fast));

           // If pointers meet, a cycle is detected
           if (slow != null && slow.equals(fast)) {
               return true;
           }

           // If fast pointer reaches null, no cycle exists
           if (fast == null || slow == null) {
               return false;
           }

       }
   }
    // get next account
    private static String next(AccountTransferGraph graph, String account) {
        List<String> transfers = graph.getTransfers(account);
        return transfers.isEmpty() ? null : transfers.get(0);
    }

}
// Main class to test the algorithm
public class FraudDetectionSystem {
    public static void main(String[] args) {
        //STEP 1: Build the account transfer graph object
        AccountTransferGraph graph = new AccountTransferGraph();

        //STEP 2: add test account transfer data
        graph.addAccountTransfer("a", "b");
        graph.addAccountTransfer("b", "c");
        graph.addAccountTransfer("c", "d");
        graph.addAccountTransfer("d", "b");

        //STEP 3: check for cycles starting from each account
        for (String account : graph.getAllAccounts()) {
            if (FloydCycleDetector.isCyclePresent(graph, account)) {
                System.out.println("Cycle detected starting from account: " + account);
                return;
            }
        }
        System.out.println("No cycles detected.");
    }
}

