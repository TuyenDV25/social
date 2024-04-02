package com.example.social_network.mapper;

import java.util.List;
import java.util.Set;

public interface MapperI<S, D> {

	S dtoToEntity(D obj);

	D entityToDto(S obj);

	List<S> entityListtoDtoList(List<D> dtos);

	List<D> dtoListToEntityList(List<S> entities);

	Set<S> entityListtoDtoList(Set<D> dtos);

	Set<D> dtoListToEntityList(Set<S> entities);
}
