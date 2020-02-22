package com.study.boot1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.study.boot1.bean.Region;

public interface  RegionMapper  {

	@Select("SELECT * FROM region WHERE lal IS NULL")
    List<Region> listRegionNull();

	@Select("SELECT * FROM region ")
    List<Region> listRegion();

	Region getByName(String name);

	@Select("SELECT * FROM region WHERE id=#{rid}")
	Region getById(long rid);

	List<Region> getRegionListByUpid(Integer upid);

	int insertRegion(Region oRegion);


    @Update("update region set lal =#{lal} where id=#{rid}")
	int updataRegion(String lal, Integer rid);

	boolean delectRegionByID(long rid);
}
