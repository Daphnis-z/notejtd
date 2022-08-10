package com.daphnis.mybatis.mapper;

import com.daphnis.mybatis.entity.Account;

public interface AccountMapper {

  Account selectOneAccount(String userName);

}
