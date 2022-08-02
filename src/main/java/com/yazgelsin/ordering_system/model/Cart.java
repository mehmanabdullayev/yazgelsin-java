package com.yazgelsin.ordering_system.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "cart", schema = "public")
public class Cart {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 100)
	private String buyerId, buyerFullname, buyerAddress;
	
	@Column(nullable=false)
	private long buyerPhone;
	
	@Column(nullable =false)
	private boolean order_made, order_filled;
	
	@Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime orderDateTime;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<CartDetail> CartDetails = new TreeSet<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public boolean isOrder_made() {
		return order_made;
	}

	public void setOrder_made(boolean order_made) {
		this.order_made = order_made;
	}

	public boolean isOrder_filled() {
		return order_filled;
	}

	public void setOrder_filled(boolean order_filled) {
		this.order_filled = order_filled;
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