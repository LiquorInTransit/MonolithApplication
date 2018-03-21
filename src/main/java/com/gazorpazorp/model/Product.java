package com.gazorpazorp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="product")
public class Product {
	///////////////////////////////////////
	//LCBO-API Stored Properties
	///////////////////////////////////////
	@Id
	@JsonProperty("id")
	private Long id;
	@JsonProperty("is_dead")
	private Boolean isDead;
	@JsonProperty("name")
	private String name;
	@Column(length=500)
	@JsonProperty("tags")
	private String tags;
	@JsonProperty("is_discontinued")
	private Boolean isDiscontinued;
	@JsonProperty("price_in_cents")
	private Long priceInCents;
	@JsonProperty("stock_type")
	private String stockType;
	@JsonProperty("primary_category")
	private String primaryCategory;
	@JsonProperty("secondary_category")
	private String secondaryCategory;
	@JsonProperty("origin")
	private String origin;
	//TODO: Readd these
	@Column(name="package")
	@JsonProperty("package")
	private String packageType;
	@JsonProperty("package_unit_type")
	private String packageUnitType;
	@JsonProperty("package_unit_volume_in_milliliters")
	private Long packageUnitVolumeInMilliliters;
	@JsonProperty("total_package_units")
	private Long totalPackageUnits;
	@JsonProperty("volume_in_milliliters")
	private Long volumeInMilliliters;
	@JsonProperty("alcohol_content")
	private Long alcoholContent;
	@JsonProperty("producer_name")
	private String producerName;
	@JsonProperty("released_on")
	private String releasedOn;
	@JsonProperty("is_seasonal")
	private Boolean isSeasonal;
	@JsonProperty("is_vqa")
	private Boolean isVqa;
	@JsonProperty("is_ocb")
	private Boolean isOcb;
	@JsonProperty("is_kosher")
	private Boolean isKosher;
	@Column(length=2000)
	@JsonProperty("description")
	private String description;
	@Column(length=2000)
	@JsonProperty("serving_suggestion")
	private String servingSuggestion;
	@Column(length=2000)
	@JsonProperty("tasting_note")
	private String tastingNote;
	@JsonProperty("updated_at")
	private String updatedAt;
	@Column(length=500)
	@JsonProperty("image_thumb_url")
	private String imageThumbUrl;
	@Column(length=500)
	@JsonProperty("image_url")
	private String imageUrl;
	@JsonProperty("varietal")
	private String varietal;
	@JsonProperty("style")
	private String style;
	@JsonProperty("tertiary_category")
	private String tertiaryCategory;
	
	@JsonIgnore
	@Transient
	private double price;
	
	public void Incorporate() {
		this.price = this.price/100.0*1.029;
	}
}
