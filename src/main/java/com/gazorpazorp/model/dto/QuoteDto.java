package com.gazorpazorp.model.dto;

import java.sql.Timestamp;

public class QuoteDto {
	private Long quoteId;
	private Timestamp estimatedAt;
	private double fee;
	private int dropoffETA;
	
	
	public Long getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}
	public Timestamp getEstimatedAt() {
		return estimatedAt;
	}
	public void setEstimatedAt(Timestamp estimatedAt) {
		this.estimatedAt = estimatedAt;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public int getDropoffETA() {
		return dropoffETA;
	}
	public void setDropoffETA(int dropoffETA) {
		this.dropoffETA = dropoffETA;
	}
	@Override
	public String toString() {
		return "QuoteDto [quoteId=" + quoteId + ", fee=" + fee + ", dropoffETA=" + dropoffETA + "]";
	}
	
	
}
