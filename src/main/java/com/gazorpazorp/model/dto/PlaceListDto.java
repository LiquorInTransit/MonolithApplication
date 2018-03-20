package com.gazorpazorp.model.dto;

import java.util.List;

import com.gazorpazorp.model.Place;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlaceListDto {
	private List<Place> places;
}
