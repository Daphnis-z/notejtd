package com.daphnis.mybatis.mapper;

import com.daphnis.mybatis.entity.Goods;
import com.daphnis.mybatis.entity.PageInfo;
import java.util.List;

public interface GoodsMapper {

  List<Goods> selectAllGoods();

  Goods selectOneGoods(int goodsId);

  List<Goods> selectGoodsListPage(PageInfo page);
  

  int insertOneGoods(Goods goods);

  int deleteOneGoods(int goodsId);

  int updateOneGoods(Goods goods);
}
