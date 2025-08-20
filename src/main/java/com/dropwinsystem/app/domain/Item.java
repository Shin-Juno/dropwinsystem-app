package com.dropwinsystem.app.domain;

import java.time.LocalDateTime;
import java.util.*;

public class Item {
	private int id;
	private String category;
	private String name;
	private int startPrice;
	private Integer buyNowPrice;
	private LocalDateTime endTime;
	private String imagePaths;
	private String transactionMethod;
	private String returnPolicy;
	private String status;
	private LocalDateTime createdAt;
	private String sellerId;
	private String sellerName;
	private int bidCount;
	private Integer currentPrice;
	private boolean isPaid;

	private String size;
	private Integer releaseYear;
	private String country;
	private String material;
	private String itemCondition;
	private String color;
	private String brand;
	private String features;

	public Integer getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Integer currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Item() {
	}

	public Item(int id, String category, String name, int startPrice, Integer buyNowPrice, LocalDateTime endTime,
			String imagePaths, String transactionMethod, String returnPolicy, String status, LocalDateTime createdAt,
			String sellerId, int bidCount, String size, Integer releaseYear, String country, String material,
			String itemCondition, String color, String brand, String features, boolean isPaid) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.startPrice = startPrice;
		this.buyNowPrice = buyNowPrice;
		this.endTime = endTime;
		this.imagePaths = imagePaths;
		this.transactionMethod = transactionMethod;
		this.returnPolicy = returnPolicy;
		this.status = status;
		this.createdAt = createdAt;
		this.sellerId = sellerId;
		this.bidCount = bidCount;
		this.size = size;
		this.releaseYear = releaseYear;
		this.country = country;
		this.material = material;
		this.itemCondition = itemCondition;
		this.color = color;
		this.brand = brand;
		this.features = features;
		this.isPaid = isPaid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}

	public Integer getBuyNowPrice() {
		return buyNowPrice;
	}

	public void setBuyNowPrice(Integer buyNowPrice) {
		this.buyNowPrice = buyNowPrice;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getImagePaths() {
		return imagePaths;
	}

	public void setImagePaths(String imagePaths) {
		this.imagePaths = imagePaths;
	}

	public String getTransactionMethod() {
		return transactionMethod;
	}

	public void setTransactionMethod(String transactionMethod) {
		this.transactionMethod = transactionMethod;
	}

	public String getReturnPolicy() {
		return returnPolicy;
	}

	public void setReturnPolicy(String returnPolicy) {
		this.returnPolicy = returnPolicy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public int getBidCount() {
		return bidCount;
	}

	public void setBidCount(int bidCount) {
		this.bidCount = bidCount;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getItemCondition() {
		return itemCondition;
	}

	public void setItemCondition(String itemCondition) {
		this.itemCondition = itemCondition;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}
	

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public List<String> getImagePathList() {
		if (this.imagePaths == null || this.imagePaths.trim().isEmpty()) {
			return Collections.emptyList();
		}
		return Arrays.asList(this.imagePaths.split("\\s*,\\s*"));
	}

	public String getMainImageUrl() {
		List<String> paths = getImagePathList();
		if (paths.isEmpty()) {
			return "/img/no_image_available.png"; 
		}
		return paths.get(0);
	}

	@Override
	public String toString() {
		return "Item{" + "id=" + id + ", category='" + category + '\'' + ", name='" + name + '\'' + ", startPrice="
				+ startPrice + ", buyNowPrice=" + buyNowPrice + ", endTime=" + endTime + ", imagePaths='" + imagePaths
				+ '\'' + ", transactionMethod='" + transactionMethod + '\'' + ", returnPolicy='" + returnPolicy + '\''
				+ ", status='" + status + '\'' + ", createdAt=" + createdAt + ", sellerId='" + sellerId + '\''
				+ ", sellerName='" + sellerName + '\'' + ", bidCount=" + bidCount + ", currentPrice=" + currentPrice
				+ "// ⭐⭐ toString에도 8가지 상세 정보 필드 추가 ⭐⭐" + ", size='" + size + '\'' + ", releaseYear=" + releaseYear
				+ ", country='" + country + '\'' + ", material='" + material + '\'' + ", itemCondition='"
				+ itemCondition + '\'' + ", color='" + color + '\'' + ", brand='" + brand + '\'' + ", features='"
				+ features + '\'' + '}';
	}
}
