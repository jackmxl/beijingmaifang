package com.study.boot1.service;

import java.util.List;

import com.study.boot1.bean.House;
import com.study.boot1.bean.HouseVO;

public interface  HouseService {
    List<House> listHouse();

	int insertHouse();

	boolean delectHouseByID(long hid);

	List<House> listHouseByRfgin(Integer r1, Integer r2, Integer r3, Integer page, Integer num);

	String maxHouseClosingdate();

	public void dayInsertHouse();
	List<HouseVO> listHouseAboutxq();
}
