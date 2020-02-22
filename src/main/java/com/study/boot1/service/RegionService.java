package com.study.boot1.service;

import java.util.List;

import com.study.boot1.bean.House;
import com.study.boot1.bean.Region;

public interface  RegionService {
	  List<Region> listRegion();
      Region getByName(String name);
  	  public House insertRegion( House oHouse, String name1, String name2, String name3);
	  boolean delectRegionByID(long rid);
	  public void updataLAL();
	  List<Region> getRegionListByUpid(Integer upid);
}
