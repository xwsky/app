package com.app.controller;

import com.app.common.curruser.UserSession;
import com.app.model.user.entity.User;
import com.app.model.user.service.UserService;
import com.platform.common.controller.InsBaseController;
import com.platform.core.http.Condition;
import com.platform.core.http.messager.Messager;
import com.app.common.curruser.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Map;

@Controller
@RequestMapping("/sys/user")
public class UserController extends InsBaseController {
	
	@Autowired
	@Qualifier("UserServiceImpl")
	private UserService userService;
	
	/**
	 * 加载数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loadList")
	public @ResponseBody Map<String, Object> loadList(
			HttpServletRequest request, HttpServletResponse response){
		Condition condition = new Condition(request);
		request.setAttribute("limit",100);
		System.out.println(userService.loadList(condition));
		return userService.loadList(condition);
	}
	
	
	/**
	 * 加载单条数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/load")
	public @ResponseBody Map<String, Object> load(@RequestParam("id") String id){		
		return Messager.getJsonMessager().success().data(userService.load(id));		
	}
	
	/**
	 * 新增
	 * @param user
	 * @return
	 */
	@RequestMapping("/add")	
	public @ResponseBody Map<String, Object> add(User user,HttpServletRequest request){
//		CurrentUser user = UserSession.getCurrentUser(request);
//		user.setCreateUserId(user.getId());
//		user.setIsDel(0);
//		user.setCreateTime(new Timestamp(System.currentTimeMillis()));
//		userService.save(user);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 编辑
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")	
	public @ResponseBody Map<String, Object> update(
		   @RequestParam("id") String id,
		   HttpServletRequest request){		
		User user = userService.get(id);
		bindObject(request, user);
		userService.update(user);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")	
	public @ResponseBody Map<String, Object> delete(HttpServletRequest request){
		String [] ids = getSelectedItems(request, null);	
		userService.deleteByIds(ids);
		return Messager.getJsonMessager().success();
	}

	
}
