package com.gazorpazorp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gazorpazorp.model.dto.StoreDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreListResult {

	@JsonProperty("result")
	public List<Store> result;
}
