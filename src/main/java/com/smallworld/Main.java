package com.smallworld;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.smallworld.domain.Transaction;

public class Main {
  public static void main(String[] args) {
    TransactionDataFetcher dataFetcher = new TransactionDataFetcher();

    double totalAmount = dataFetcher.getTotalTransactionAmount();
    System.out.println("Total Transaction Amount: " + totalAmount);

    String senderFullName = "Tom Shelby";
    double senderTotalAmount = dataFetcher.getTotalTransactionAmountSentBy(senderFullName);
    System.out.println("Total Transaction Amount Sent by " + senderFullName + ": " + senderTotalAmount);

    double maxAmount = dataFetcher.getMaxTransactionAmount();
    System.out.println("Max Transaction Amount: " + maxAmount);

    long uniqueClientsCount = dataFetcher.countUniqueClients();
    System.out.println("Count of Unique Clients: " + uniqueClientsCount);

    String clientFullName = "Tom Shelby";
    boolean hasComplianceIssues = dataFetcher.hasOpenComplianceIssues(clientFullName);
    System.out.println("Has Open Compliance Issues for " + clientFullName + ": " + hasComplianceIssues);

    Set < Integer > unsolvedIssueIds = dataFetcher.getUnsolvedIssueIds();
    System.out.println("Unsolved Issue IDs: " + unsolvedIssueIds);

    List < String > solvedIssueMessages = dataFetcher.getAllSolvedIssueMessages();
    System.out.println("Solved Issue Messages: " + solvedIssueMessages);

    System.out.println("Top 3 Transactions By Amount: ");
    dataFetcher.getTop3TransactionsByAmount().forEach(System.out::println);

    Optional < Transaction > topSender = dataFetcher.getTopSender();
    topSender.ifPresent(sender -> {
      Transaction transaction = (Transaction) sender;
      System.out.println("Top Sender: " + transaction.getSenderFullName());
    });

    Map < String, List < Transaction >> transactionsByBeneficiary = dataFetcher.getTransactionsByBeneficiaryName();
    System.out.println("Transactions by Beneficiary Name: " + transactionsByBeneficiary);
  }
}