package com.yazgelsin.ordering_system.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "order", schema = "public")
public class Order {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cartdetail_id", nullable = false)
    private CartDetail cartDetail;

	@Column(nullable = false)
	@DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal price;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false, length = 100)
	private String buyerId, buyerFullname, buyerAddress;
	
	@Column(nullable=false)
	private long buyerPhone;
	
	@Column(nullable =false)
	private boolean filled;
	
	@Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime orderDateTime;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public CartDetail getCartDetail() {
		return cartDetail;
	}

	public void setCartDetail(CartDetail cartDetail) {
		this.cartDetail = cartDetail;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerFullname() {
		return buyerFullname;
	}

	public void setBuyerFullname(String buyerFullname) {
		this.buyerFullname = buyerFullname;
	}

	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	public long getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(long buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(LocalDateTime orderDateTime) {
		this.orderDateTime = orderDateTime;
	}
	
	public String toString() {
		return String.valueOf(id);
	}
}