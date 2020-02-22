package com.study.boot1.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.study.boot1.bean.User;

public interface  UserService {
	List<User> listUser();

	User getByName(String name);

	long insertUser(String name, String pass);

	boolean delectUserByID(long uid);

	CompletableFuture<String> findUser(int i);
}
