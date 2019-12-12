package com.how2java.tmall.pojo;

import java.util.Date;
import java.util.List;
import com.how2java.tmall.pojo.*;

public class Product {
    private Integer id;

    private String name;

    private String subTitle;

    private Float originalPrice;

    private Float promotePrice;

    private Integer stock;

    private Integer cid;

    private Date createDate;
    
    //非数据库字段
    private Category category;
    
    private ProductImage firstProductImage;
    
    private int saleCount;
    
    private int reviewCount;
    
    private List<ProductImage> productSingleImages;
    
    private List<ProductImage> productDetailImages;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public Float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(Float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public ProductImage getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(ProductImage firstProductImage) {
        this.firstProductImage = firstProductImage;
    }
    
    public int getsaleCount() {
    	return saleCount;
    }
    
    public void setsaleCount(int saleCount) {
    	this.saleCount = saleCount;
    }
    
    public int getreviewCount() {
    	return reviewCount;
    }
    
    public void setreviewCount(int reviewCount) {
    	this.reviewCount = reviewCount;
    }
    
    public Category getCategory() {
    	return category;
    }
    
    public void setCategory(Category category) {
    	this.category = category;
    }
    
    public List<ProductImage> getproductSingleImages() {
    	return productSingleImages;
    }
    
    public void setproductSingleImages(List<ProductImage> productSingleImages) {
    	this.productSingleImages = productSingleImages;
    }

    public List<ProductImage> getproductDetailImages() {
    	return productDetailImages;
    }
    
    public void setproductDetailImages(List<ProductImage> productDetailImages) {
    	this.productDetailImages = productDetailImages;
    }
}