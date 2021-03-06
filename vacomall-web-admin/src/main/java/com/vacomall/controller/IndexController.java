package com.vacomall.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.vacomall.redis.JedisClient;
import com.vacomall.system.i.ISysMenuService;
import com.vacomall.system.model.SysMenu;

/**
 * @author GaoJun.Zhou
 * @Time：2017年9月5日 上午11:52:49
 * @version 1.0
 */
@Controller
public class IndexController {
	
	@Autowired private ISysMenuService sysMenuService;
	
	/**
	 * 首页
	 * @param model
	 * @return
	 */
	@RequestMapping({"/","/index"})
	public String main(Model model){
		List<Map<String,Object>> listMap = sysMenuService.selectMaps(new EntityWrapper<SysMenu>().eq("pid","0").orderBy("code"));
		for(Map<String, Object> map : listMap){
			map.put("items", sysMenuService.selectMaps(new EntityWrapper<SysMenu>().eq("pid",map.get("id")).orderBy("code")));
		}
		
		model.addAttribute("systemName","VACOMALL" );
		model.addAttribute("listMap", listMap);
		
		return "index";
	}
	
	/**
	 * 欢迎页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/welcome")
	public String welcome(Model model){
		return "welcome";
	}
	
	@Autowired private JedisClient jedisClient;
	
	@ResponseBody
	@RequestMapping("/test")
	public String test(){
		return jedisClient.get("test_key");
	}
}
