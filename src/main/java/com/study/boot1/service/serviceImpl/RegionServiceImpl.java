package com.study.boot1.service.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.boot1.bean.House;
import com.study.boot1.bean.Region;
import com.study.boot1.datahelper.HouseDataHelper;
import com.study.boot1.mapper.RegionMapper;
import com.study.boot1.service.RegionService;
import com.study.boot1.util.RedisUtil;

@Service(value = "regionService")
public class RegionServiceImpl implements RegionService {

	Logger log = LoggerFactory.getLogger(RegionServiceImpl.class);

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private RegionMapper regionmapper;

	@Autowired
	HouseDataHelper housedatahelper;

	@Override
	public List<Region> listRegion() {
		return regionmapper.listRegion();
	}


	@Override
	public void updataLAL()
	{
		List<Region> listR = regionmapper.listRegionNull();
		for(Region r : listR)
		{
            if(r.getUpid() != 0)
            {
            	Region s = regionmapper.getById(r.getUpid());
            	if(s != null && s.getUpid() != 0)
            	{
            		Region q = regionmapper.getById(s.getUpid());
            		String lal = housedatahelper.getLAL(q.getName(), s.getName(), r.getName());
            		if(lal != null)
            		{
            			regionmapper.updataRegion(lal, r.getId());
            		}
            	}else if(s != null){
            		String lal = housedatahelper.getLAL(s.getName(), r.getName(), null);
            		if(lal != null)
            		{
            			regionmapper.updataRegion(lal, r.getId());
            		}
            	}
            }else {
            	String lal = housedatahelper.getLAL(r.getName(), null, null);
        		if(lal != null)
        		{
        			regionmapper.updataRegion(lal, r.getId());
        		}
            }
		}
	}

	@Override
	public Region getByName(String name) {
		return regionmapper.getByName(name);
	}

	@Override
	public House insertRegion( House oHouse, String name1, String name2, String name3) {
		if(name1 != null)
		{
			Region region1 = getByName(name1);
			if(region1 == null  || region1.getId() == null)
			{
				region1 = new Region();
				region1.setName(name1);
				region1.setUpid(0);
				regionmapper.insertRegion(region1);
			}
			oHouse.setRegion1(region1.getId());
			if(name2 != null )
			{
				Region region2 = getByName(name2);
				if(region2 == null || region2.getId() == null)
				{
					region2 = new Region();
					region2.setName(name2);
					region2.setUpid(region1.getId());
					regionmapper.insertRegion(region2);
				}
				oHouse.setRegion2(region2.getId());
				if(name3 != null)
				{
					Region region3 = getByName(name3);
					if(region3 == null || region3.getId() == null)
					{
						region3 = new Region();
						region3.setName(name3);
						region3.setUpid(region2.getId());
						regionmapper.insertRegion(region3);
					}
					oHouse.setRegion3(region3.getId());
				}
			}
		}
		return oHouse;
	}

	@Override
	public boolean delectRegionByID(long rid) {
		return regionmapper.delectRegionByID(rid);
	}

	@Override
	public List<Region> getRegionListByUpid(Integer upid) {
		return regionmapper.getRegionListByUpid(upid);
	}



}
