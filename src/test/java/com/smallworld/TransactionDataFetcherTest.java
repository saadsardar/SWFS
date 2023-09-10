package com.smallworld;
import com.smallworld.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDataFetcherTest {

    private TransactionDataFetcher dataFetcher;
    private List<Transaction> mockTransactions;

    @BeforeEach
    public void setup() throws IOException {
        dataFetcher = new TransactionDataFetcher();
        mockTransactions = Arrays.asList(
            new Transaction(663458, 430.2, "Tom Shelby", 22, "Alfie Solomons", 33, 1, false, "Looks like money laundering"),
            new Transaction(1284564, 150.2, "Tom Shelby", 22, "Arthur Shelby", 60, 2, true, "Never gonna give you up"),
            new Transaction(1284564, 150.2, "Tom Shelby", 22, "Arthur Shelby", 60, 3, false, "Looks like money laundering"),
            new Transaction(96132456, 67.8, "Aunt Polly", 34, "Aberama Gold", 58, null, true, null),
            new Transaction(5465465, 985.0, "Arthur Shelby", 60, "Ben Younger", 47, 15, false, "Something's fishy"),
            new Transaction(1651665, 97.66, "Tom Shelby", 22, "Oswald Mosley", 37, 65, true, "Never gonna let you down"),
            new Transaction(6516461, 33.22, "Aunt Polly", 34, "MacTavern", 30, null, true, null),
            new Transaction(32612651, 666.0, "Grace Burgess", 31, "Michael Gray", 58, 54, false, "Something ain't right"),
            new Transaction(32612651, 666.0, "Grace Burgess", 31, "Michael Gray", 58, 78, true, "Never gonna run around and desert you"),
            new Transaction(32612651, 666.0, "Grace Burgess", 31, "Michael Gray", 58, 99, false, "Don't let this transaction happen"),
            new Transaction(36448252, 154.15, "Billy Kimber", 58, "Winston Churchill", 48, null, true, null),
            new Transaction(645645111, 215.17, "Billy Kimber", 58, "Major Campbell", 41, null, true, null),
            new Transaction(45431585, 89.77, "Billy Kimber", 58, "Luca Changretta", 46, null, true, null)
        );
    }


    @Test
    public void testGetMaxTransactionAmount() {
        double expectedMax = mockTransactions.stream().mapToDouble(Transaction::getAmount).max().orElse(0.0);
        double actualMax = dataFetcher.getMaxTransactionAmount();
        assertEquals(expectedMax, actualMax);
    }

    @Test
    public void testCountUniqueClients() {
        long expectedCount = mockTransactions.stream()
            .flatMap(transaction -> Stream.of(transaction.getSenderFullName(), transaction.getBeneficiaryFullName()))
            .distinct()
            .count();
        long actualCount = dataFetcher.countUniqueClients();
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testHasOpenComplianceIssues() {
        String clientFullName = "Tom Shelby";
        boolean expected = mockTransactions.stream()
            .filter(transaction -> transaction.getSenderFullName().equals(clientFullName) || transaction.getBeneficiaryFullName().equals(clientFullName))
            .anyMatch(transaction -> transaction.getIssueId() != null && !transaction.isIssueSolved());
        boolean actual = dataFetcher.hasOpenComplianceIssues(clientFullName);
        assertEquals(expected, actual);
    }

    
    @Test
public void testGetTotalTransactionAmountSentByNonExistentClient() {
    String senderFullName = "NonExistentClient";
    double actualTotal = dataFetcher.getTotalTransactionAmountSentBy(senderFullName);
    assertEquals(0.0, actualTotal);
}




@Test
public void testHasOpenComplianceIssuesWithOpenIssues() {
    String clientFullName = "Tom Shelby";
    boolean actual = dataFetcher.hasOpenComplianceIssues(clientFullName);
    assertTrue(actual);
}

@Test
public void testHasOpenComplianceIssuesWithSolvedIssues() {
    String clientFullName = "Aunt Polly";
    boolean actual = dataFetcher.hasOpenComplianceIssues(clientFullName);
    assertFalse(actual);
}


@Test
public void testGetAllSolvedIssueMessages() {
    List<String> expectedMessages = Arrays.asList(
        "Never gonna give you up",
        "Never gonna let you down",
        "Never gonna run around and desert you"
    );
    List<String> actualMessages = dataFetcher.getAllSolvedIssueMessages();
    assertEquals(expectedMessages, actualMessages);
}
}
