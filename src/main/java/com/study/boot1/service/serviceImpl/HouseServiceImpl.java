package com.study.boot1.service.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.study.boot1.bean.House;
import com.study.boot1.bean.HouseVO;
import com.study.boot1.datahelper.HouseDataHelper;
import com.study.boot1.datahelper.HouseDataHelper.househelp;
import com.study.boot1.mapper.HouseMapper;
import com.study.boot1.service.HouseService;
import com.study.boot1.util.DateUtil;
import com.study.boot1.util.RedisUtil;

@Service(value = "houseServiceImpl")
public class HouseServiceImpl implements HouseService {

	Logger log = LoggerFactory.getLogger(HouseServiceImpl.class);

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	HouseDataHelper housedatahelper;

	@Autowired
	private HouseMapper housemapper;

	@Autowired
	private RegionServiceImpl regionserviceimpl;

	@Override
	public List<House> listHouse() {
		return housemapper.listHouse();
	}


	@Override
	public void dayInsertHouse() {
		StringBuilder sb= new StringBuilder("");
		sb.append("开始时间："+DateUtil.formatDate(new Date(), DateUtil.format));
		int hnum =0;
		boolean next = true;
		int i=1;
		String endst = housemapper.maxHouseClosingdate();
		while(next)
		{
			String url = "https://bj.ke.com/chengjiao/pg"+i+"/";
			CompletableFuture<List<househelp> > listhouse = housedatahelper.findHouse(url);
	        try {
	        	hnum += listhouse.get().size();
				for(househelp ohousehelp : listhouse.get())
				{
					String name1=ohousehelp.name1;
					String name2=ohousehelp.name2;
					String name3=ohousehelp.name3;
					House oHouse = ohousehelp.oHouse;
					regionserviceimpl.insertRegion(oHouse, name1, name2, name3);
					log.info(oHouse.getClosingdate()+"");
					log.info(oHouse.getName()+"");
					if(DateUtil.parseDate(oHouse.getClosingdate(), DateUtil.format6).getTime() <= DateUtil.parseDate(endst, DateUtil.format).getTime())
					{
						next = false;
						break;
					}else {
						housemapper.insertHouse(oHouse);
					}

				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        i++;
		}
		sb.append("结束时间："+DateUtil.formatDate(new Date(), DateUtil.format)+"数量："+hnum);
		log.info(sb.toString());
	}


	@Override
	public int insertHouse() {
		StringBuilder sb= new StringBuilder("");
		sb.append("开始时间："+DateUtil.formatDate(new Date(), DateUtil.format));
		int hnum =0;
		for(int i=100; i>=1; i--)
		{
			String url = "https://bj.ke.com/chengjiao/pg"+i+"p1p2p3p4/";
			CompletableFuture<List<househelp> > listhouse = housedatahelper.findHouse(url);
	        try {
	        	hnum += listhouse.get().size();
				for(househelp ohousehelp : listhouse.get())
				{
					String name1=ohousehelp.name1;
					String name2=ohousehelp.name2;
					String name3=ohousehelp.name3;
					House oHouse = ohousehelp.oHouse;
					regionserviceimpl.insertRegion(oHouse, name1, name2, name3);
					housemapper.insertHouse(oHouse);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sb.append("结束时间："+DateUtil.formatDate(new Date(), DateUtil.format)+"数量："+hnum);
		log.info(sb.toString());
		return 1;
	}

	@Override
	public boolean delectHouseByID(long hid) {
		return housemapper.delectHouseByID(hid);
	}


	@Override
	public List<House> listHouseByRfgin(Integer r1, Integer r2, Integer r3, Integer page, Integer num) {

			page=(page-1)*num;

		return housemapper.listHouseByRfgin(r1, r2, r3, page, num);
	}


	@Override
	public String maxHouseClosingdate() {
		return housemapper.maxHouseClosingdate();
	}


	@Override
	public List<HouseVO> listHouseAboutxq() {

		String key = "house_about";
		boolean hasKey = redisUtil.exists(key);
		  if (hasKey) {
	        	String listUserStr = redisUtil.get(key);
	        	JSONArray jsonObject=JSONArray.parseArray(listUserStr);
	        	List<HouseVO> listu=jsonObject.toJavaObject(List.class);
	            System.out.println("==========从缓存中获得数据=========");
	            return listu;
	        } else {
	            System.out.println("==========从数据表中获得数据=========");
	            List<HouseVO> listu =housemapper.listHouseAboutxq();
	            // 写入缓存
	            redisUtil.set(key, JSON.toJSONString(listu));
	            return listu;
	        }
	}




}
