package com.bk.mapper;

import com.bk.dto.AuthLog;
import com.bk.entity.AuthLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthLogMapper {
    AuthLogMapper INSTANCE = Mappers.getMapper(AuthLogMapper.class);
    AuthLogEntity toAuthLogEntity(AuthLog authLog);
}
