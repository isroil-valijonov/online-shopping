package com.example.onlineshopping.common.mapper;

public abstract class GenericMapper <ENTITY, CREATE_DTO, RESPONSE_DTO, UPDATE_DTO>{
    public abstract ENTITY toEntity(CREATE_DTO createDto);

    public abstract RESPONSE_DTO toResponse(ENTITY entity);

    public abstract void toUpdate(UPDATE_DTO updateDto, ENTITY entity );
}
