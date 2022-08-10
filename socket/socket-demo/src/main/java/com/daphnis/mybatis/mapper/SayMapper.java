package com.daphnis.mybatis.mapper;

import com.daphnis.mybatis.entity.Say;

import java.util.List;

public interface SayMapper {

    List<Say> selectSays();

}
