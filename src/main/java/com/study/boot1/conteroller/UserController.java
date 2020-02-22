package com.study.boot1.conteroller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.boot1.bean.User;
import com.study.boot1.service.UserService;
import com.study.boot1.util.OkHttpUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
    private UserService userService;

	Logger log = LoggerFactory.getLogger(UserController.class);

	@ResponseBody
	@GetMapping("/all")
	public Object findAllUser() {
		for(int i=0;i<10 ;i++)
		{
			userService.findUser(i);
		}

		return userService.listUser();
	}

	@ResponseBody
	@GetMapping("/setname")
	public Long insertUser(String name, String pass)
	{
		return userService.insertUser(name, pass);
	}

	@ResponseBody
	@GetMapping("/getname")
	public User  getByName(String name)
	{
		log.info(name);
		return userService.getByName(name);
	}

	@ResponseBody
	@GetMapping("/delevtid")
	public boolean delectUserByID(long uid)
	{
		return userService.delectUserByID(uid);
	}

	@ResponseBody
	@GetMapping("/show")
    public String show() {
        String url = "https://bj.ke.com/chengjiao/pg2p1p2p3p4/";
        Document document = Jsoup.parse(OkHttpUtil.doGet(url));
        Elements oElements = document.getElementsByClass("position");
        Elements sElements = oElements.get(0).getElementsByTag("dl");
        Elements aElements = sElements.select("a[href]");
        StringBuilder sb = new StringBuilder("");
        for(Element o: aElements)
        {
        	sb.append(o.text());
        }
        return sb.toString();
    }


}
