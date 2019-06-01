package com.rbank.stmtprocessor.beans;

import java.math.BigDecimal;


public class StmtRecord {

	private Long transRefNumber;
	private String acctNumber;
	private String description;
	private BigDecimal startBalance;
	private BigDecimal mutation;
	private BigDecimal endBalance;
	private boolean hasError;

	public Long getTransRefNumber() {
		return transRefNumber;
	}

	public void setTransRefNumber(Long transRefNumber) {
		this.transRefNumber = transRefNumber;
	}

	public String getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public BigDecimal getMutation() {
		return mutation;
	}

	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}

	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	@Override
	public String toString() {
		return "StmtRecord [transRefNumber=" + transRefNumber + ", acctNumber=" + acctNumber + ", description="
				+ description + ", startBalance=" + startBalance + ", mutation=" + mutation + ", endBalance="
				+ endBalance + ", hasError=" + hasError + "]";
	}

}
