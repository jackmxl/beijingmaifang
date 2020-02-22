package com.study.boot1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.study.boot1.bean.User;

public interface  UserMapper  {

	@Select("SELECT * FROM user ")
    List<User> listUser();

	User getByName(String name);

	int insertUser(User oUser);

	boolean delectUserByID(long uid);
}
