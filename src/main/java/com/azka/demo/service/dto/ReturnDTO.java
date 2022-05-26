package com.azka.demo.service.dto;

public class ReturnDTO {

    private Long id;
	private String token;
	private String sku;
	private int quantity;
	private double unitRefundAmount;
	private double totalRefundAmount;
	private String status;
	private String qcStatus;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getUnitRefundAmount() {
		return unitRefundAmount;
	}
	public void setUnitRefundAmount(double unitRefundAmount) {
		this.unitRefundAmount = unitRefundAmount;
	}
	public double getTotalRefundAmount() {
		return totalRefundAmount;
	}
	public void setTotalRefundAmount(double totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getQcStatus() {
		return qcStatus;
	}
	public void setQcStatus(String qcStatus) {
		this.qcStatus = qcStatus;
	}
}
