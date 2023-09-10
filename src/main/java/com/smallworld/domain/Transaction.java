package com.smallworld.domain;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {
  @JsonProperty("mtn")
  private int mtn;

  @JsonProperty("amount")
  private double amount;
  @JsonProperty("senderFullName")
  private String senderFullName;

  @JsonProperty("senderAge")
  private int senderAge;

  @JsonProperty("beneficiaryFullName")
  private String beneficiaryFullName;

  @JsonProperty("beneficiaryAge")
  private int beneficiaryAge;

  @JsonProperty("issueId")
  private Integer issueId;

  @JsonProperty("issueSolved")
  private boolean issueSolved;

  @JsonProperty("issueMessage")
  private String issueMessage;

  public Transaction() {}

  public Transaction(
    int mtn,
    double amount,
    String senderFullName,
    int senderAge,
    String beneficiaryFullName,
    int beneficiaryAge,
    Integer issueId,
    boolean issueSolved,
    String issueMessage
  ) {
    this.mtn = mtn;
    this.amount = amount;
    this.senderFullName = senderFullName;
    this.senderAge = senderAge;
    this.beneficiaryFullName = beneficiaryFullName;
    this.beneficiaryAge = beneficiaryAge;
    this.issueId = issueId;
    this.issueSolved = issueSolved;
    this.issueMessage = issueMessage;
  }

  public int getMtn() {
    return mtn;
  }

  public double getAmount() {
    return amount;
  }

  public String getSenderFullName() {
    return senderFullName;
  }

  public int getSenderAge() {
    return senderAge;
  }

  public String getBeneficiaryFullName() {
    return beneficiaryFullName;
  }

  public int getBeneficiaryAge() {
    return beneficiaryAge;
  }

  public Integer getIssueId() {
    return issueId;
  }

  public boolean isIssueSolved() {
    return issueSolved;
  }

  public String getIssueMessage() {
    return issueMessage;
  }

  @Override
  public String toString() {
    return "Transaction{" +
      "mtn=" + mtn +
      ", amount=" + amount +
      ", senderFullName='" + senderFullName + '\'' +
      ", beneficiaryFullName='" + beneficiaryFullName + '\'' +
      ", issueId=" + issueId +
      ", issueSolved=" + issueSolved +
      ", issueMessage='" + issueMessage + '\'' +
      '}';
  }
}