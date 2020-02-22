package com.study.boot1.conteroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.boot1.bean.House;
import com.study.boot1.bean.HouseVO;
import com.study.boot1.bean.Region;
import com.study.boot1.service.HouseService;
import com.study.boot1.service.RegionService;

@Controller
@RequestMapping(value = "/house")
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class HouseController {

	@Autowired
    private HouseService houseService;

	@Autowired
    private RegionService regionservice;

	Logger log = LoggerFactory.getLogger(HouseController.class);

	 @Scheduled(cron="0 03 04 ? * *")  //每天4点3分执行
	 public void addLAL() throws InterruptedException {
		 regionservice.updataLAL();
	 }

	@ResponseBody
	@GetMapping("/addlal")
	public void addLALs()
	{
		regionservice.updataLAL();
	}

    @Scheduled(cron="0 03 03 ? * *")  //每天3点3分执行
    public void first() throws InterruptedException {
    	houseService.dayInsertHouse();
    }

	@ResponseBody
	@GetMapping("/addhou")
    public void addhou() throws InterruptedException {
    	houseService.dayInsertHouse();
    }

	@ResponseBody
	@GetMapping("/insert")
	public int insertUser()
	{
		return houseService.insertHouse();
	}

	@RequestMapping("/info{p1}_{p2}_{p3}_{page}_{num}")
	public String getInfo(@PathVariable("p1") Integer p1,
			@PathVariable("p2") Integer p2, @PathVariable("p3") Integer p3,
			@PathVariable("page") Integer page,@PathVariable("num") Integer num,
			HttpServletRequest request, Model model) throws IOException {
		List<Region> r1 = regionservice.getRegionListByUpid(0);
		model.addAttribute("r1", r1);
		if(p1 != null && p1 != 0)
		{
			List<Region> r2 = regionservice.getRegionListByUpid(p1);
			model.addAttribute("r2", r2);
		}
		if(p2 != null && p2 != 0)
		{
			List<Region> r3 = regionservice.getRegionListByUpid(p2);
			model.addAttribute("r3", r3);
		}

		List<House> listhouse = houseService.listHouseByRfgin(p1, p2, p3, page, num);

		model.addAttribute("t1", p1);
		model.addAttribute("t2", p2);
		model.addAttribute("t3", p3);
		model.addAttribute("pages", page);
		model.addAttribute("lh", listhouse);
		return "house/info.html";
	}

	@RequestMapping("/info")
	public String getInfo( Model model) throws IOException {
		List<Region> r1 = regionservice.getRegionListByUpid(0);
		model.addAttribute("r1", r1);
		List<House> listhouse = houseService.listHouseByRfgin(0, 0, 0, 1, 20);
		model.addAttribute("pages", 1);
		model.addAttribute("lh", listhouse);
		return "house/info.html";
	}

	@RequestMapping("/gdmap")
	public String getGDMap( Model model) throws IOException {
		List<HouseVO> listhouse = houseService.listHouseAboutxq();
		model.addAttribute("lh", listhouse);
		return "house/gdmap.html";
	}

}
