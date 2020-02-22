package com.study.boot1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.study.boot1.bean.House;
import com.study.boot1.bean.HouseVO;

public interface  HouseMapper  {

	@Select("SELECT * FROM house ")
    List<House> listHouse();

	@Select("SELECT COUNT(*) num ,r.`name` rname ,r.lal, r.id  FROM house h,region r WHERE h.region3 = r.id  GROUP BY h.region3  ORDER BY num DESC")
    List<HouseVO> listHouseAboutxq();

	@Select("SELECT h.region1 id,COUNT(h.id) num ,r.`name` rname, r.lal lal FROM house h,region r WHERE h.region1 = r.id GROUP BY h.region1 ORDER BY num DESC")
    List<HouseVO> listHouseAboutqx();

	@Select("SELECT h.region1 id,COUNT(h.id) num ,r.`name` rname, r.lal lal FROM house h,region r WHERE h.region1 = r.id GROUP BY h.region1 ORDER BY num DESC")
    List<HouseVO> listHouseAboutsq();

	List<House> listHouseByRfgin(Integer r1, Integer r2, Integer r3, Integer page, Integer num);

	int insertHouse(House oHouse);

	boolean delectHouseByID(long hid);

	String maxHouseClosingdate();
	// SELECT h.region1,COUNT(h.id) n ,r.`name` FROM house h,region r WHERE h.region1 = r.id GROUP BY h.region1 ORDER BY n DESC
    // SELECT h.region2,COUNT(*) n ,r.`name` r1name FROM house h,region r WHERE h.region2 = r.id  GROUP BY h.region2  ORDER BY n DESC
	// SELECT h.region3,COUNT(*) n ,r.`name` r1name FROM house h,region r WHERE h.region3 = r.id  GROUP BY h.region3  ORDER BY n DESC
}
