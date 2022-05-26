package com.azka.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Return extends AbstractAuditingEntity {

	private static final long serialVersionUID = -3868781094242803615L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private long id;
	private String token;
	private String sku;
	private int quantity;
	private double unitRefundAmount;
	private double totalRefundAmount;
	private String status;
	private String qcStatus;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
