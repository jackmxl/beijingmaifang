package com.study.boot1.service.serviceImpl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.study.boot1.bean.User;
import com.study.boot1.mapper.UserMapper;
import com.study.boot1.service.UserService;
import com.study.boot1.util.RedisUtil;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private UserMapper usermapper;


	@Override
	@Async("taskExecutor")
    public CompletableFuture<String> findUser(int i)  {
		log.info("Looking up "+i);
        // Artificial delay of 3s for demonstration purposes
        try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return CompletableFuture.completedFuture("s");
    }

	@Override
	public List<User> listUser() {
		String key = "user_all";
		boolean hasKey = redisUtil.exists(key);
		  if (hasKey) {
	        	String listUserStr = redisUtil.get(key);
	        	JSONArray jsonObject=JSONArray.parseArray(listUserStr);
	        	List<User> listu=jsonObject.toJavaObject(List.class);
	            System.out.println("==========从缓存中获得数据=========");
	            return listu;
	        } else {
	            System.out.println("==========从数据表中获得数据=========");
	            List<User> listu = usermapper.listUser();
	            // 写入缓存
	            redisUtil.set(key, JSON.toJSONString(listu));
	            return listu;
	        }

	}

	@Override
	public User getByName(String name) {
		String key = "user_name" + name;
		boolean hasKey = redisUtil.exists(key);
		   if (hasKey) {
	        	String carStr = redisUtil.get(key);
	        	JSONObject jsonObject=JSONObject.parseObject(carStr);
	        	User user=jsonObject.toJavaObject(User.class);
	            System.out.println("==========从缓存中获得数据=========");
	            return user;
	        } else {
	            System.out.println("==========从数据表中获得数据=========");
	            User oUser = usermapper.getByName(name);
	            // 写入缓存
	            redisUtil.set(key, JSON.toJSONString(oUser));
	            return oUser;
	        }

	}

	@Override
	public long insertUser(String name, String pass) {
		User oUser = new User();
		oUser.setName(name);
		oUser.setPass(pass);
		usermapper.insertUser(oUser);
		return oUser.getId();
	}

	@Override
	public boolean delectUserByID(long uid) {
		return usermapper.delectUserByID(uid);
	}
}
