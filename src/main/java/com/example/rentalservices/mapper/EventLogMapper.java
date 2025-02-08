package com.example.rentalservices.mapper;

import com.example.rentalservices.model.EventLog;
import com.example.rentalservices.payload.EventLogDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface EventLogMapper {

    EventLogMapper INSTANCE = Mappers.getMapper(EventLogMapper.class);

    EventLogDto mapToDto(EventLog eventLog);
}
