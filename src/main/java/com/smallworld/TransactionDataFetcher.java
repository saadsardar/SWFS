package com.smallworld;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.domain.Transaction;
import com.smallworld.config.Constants;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;

public class TransactionDataFetcher {
  private List < Transaction > transactions;

  public TransactionDataFetcher() {

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      File file = new File(Constants.TRANSACTIONS_FILE_PATH);
      this.transactions = objectMapper.readValue(file, new TypeReference < List < Transaction >> () {});
    } catch (IOException e) {
      System.err.println("Error loading data from JSON file: " + e.getMessage());
    } catch (SecurityException e) {
      System.err.println("Security exception: " + e.getMessage());
    }
  }

  private Stream < Transaction > removeDuplicates() {
    Set < Integer > uniqueTransactionMts = new HashSet < > ();
    return transactions.stream().filter(transaction -> uniqueTransactionMts.add(transaction.getMtn()));
  }

  /**
   * Returns the sum of the amounts of all transactions
   */
  public double getTotalTransactionAmount() {
    return this.removeDuplicates().mapToDouble(Transaction::getAmount).sum();
  }

  /**
   * Returns the sum of the amounts of all transactions sent by the specified client
   */

  public double getTotalTransactionAmountSentBy(String senderFullName) {
    return removeDuplicates()
      .filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
      .mapToDouble(Transaction::getAmount)
      .sum();
  }

  /**
   * Returns the highest transaction amount
   */
  public double getMaxTransactionAmount() {
    return removeDuplicates().mapToDouble(Transaction::getAmount).max().orElse(0.0);
  }

  /**
   * Counts the number of unique clients that sent or received a transaction
   */
  public long countUniqueClients() {
    return removeDuplicates()
      .flatMap(transaction -> Stream.of(transaction.getSenderFullName(), transaction.getBeneficiaryFullName()))
      .distinct()
      .count();
  }

  /**
   * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
   * issue that has not been solved
   */
  public boolean hasOpenComplianceIssues(String clientFullName) {
    return transactions.stream()
      .filter(transaction -> transaction.getSenderFullName().equals(clientFullName) || transaction.getBeneficiaryFullName().equals(clientFullName))
      .anyMatch(transaction -> transaction.getIssueId() != null && !transaction.isIssueSolved());
  }

  /**
   * Returns all transactions indexed by beneficiary name
   */
  public Map < String, List < Transaction >> getTransactionsByBeneficiaryName() {
    return removeDuplicates()
      .collect(Collectors.groupingBy(Transaction::getBeneficiaryFullName));
  }

  /**
   * Returns the identifiers of all open compliance issues
   */
  public Set < Integer > getUnsolvedIssueIds() {
    return transactions.stream()
      .filter(transaction -> transaction.getIssueId() != null && !transaction.isIssueSolved())
      .map(Transaction::getIssueId)
      .collect(Collectors.toSet());
  }

  /**
   * Returns a list of all solved issue messages
   */
  public List < String > getAllSolvedIssueMessages() {
    return transactions.stream()
      .filter(transaction -> transaction.isIssueSolved() && transaction.getIssueId() != null)
      .map(Transaction::getIssueMessage)
      .collect(Collectors.toList());
  }

  /**
   * Returns the 3 transactions with highest amount sorted by amount descending
   */
  public List < Transaction > getTop3TransactionsByAmount() {
    return removeDuplicates()
      .sorted((t1, t2) -> Double.compare(t2.getAmount(), t1.getAmount()))
      .limit(3)
      .collect(Collectors.toList());
  }

  /**
   * Returns the sender with the most total sent amount
   */
  public Optional < Transaction > getTopSender() {
    return removeDuplicates()
      .collect(Collectors.groupingBy(Transaction::getSenderFullName, Collectors.summingDouble(Transaction::getAmount)))
      .entrySet()
      .stream()
      .max(Map.Entry.comparingByValue())
      .flatMap(entry -> transactions.stream()
        .filter(transaction -> transaction.getSenderFullName().equals(entry.getKey()))
        .findFirst()
      );
  }

}