package com.newsmap.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MapNewsItemMapper {
    MapNewsItemMapper INSTANCE = Mappers.getMapper(MapNewsItemMapper.class);
}
