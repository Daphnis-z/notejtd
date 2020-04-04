package com.daphnis.mybatis.mapper;

import com.daphnis.mybatis.entity.Goods;
import java.util.List;

public interface GoodsMapper {

  List<Goods> selectAllGoods();

  Goods selectOneGoods(String goodsId);

  int insertOneGoods(Goods goods);

  int deleteOneGoods(String goodsId);

  int updateOneGoods(Goods goods);
}
