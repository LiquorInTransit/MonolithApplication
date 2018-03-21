package com.gazorpazorp.model.dtoMapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.LineItem;
import com.gazorpazorp.model.dto.DeliveryWithItemsDto;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

	DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);
	
	@Mapping(target="delivery", source="delivery")
	@Mapping(target="items", source="items")
	DeliveryWithItemsDto deliveryAndItemsToDeliveryWithItemsDto(Delivery delivery, List<LineItem> items);
}
