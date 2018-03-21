package com.gazorpazorp.model.dtoMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.gazorpazorp.model.Quote;
import com.gazorpazorp.model.dto.QuoteDto;


@Mapper(componentModel = "spring")
public interface QuoteMapper {
	
	QuoteMapper INSTANCE = Mappers.getMapper(QuoteMapper.class);
	
	
	@Mapping(target="quoteId", source="id")
	@Mapping(target="estimatedAt")
	@Mapping(target="fee")
	@Mapping(target="dropoffETA")
	QuoteDto quoteToQuoteDto(Quote quote);
}
