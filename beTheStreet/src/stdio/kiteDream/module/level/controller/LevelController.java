package stdio.kiteDream.module.level.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import stdio.kiteDream.module.coins.bean.Coins;
import stdio.kiteDream.module.comic.bean.BasePathJsonParser;
import stdio.kiteDream.module.level.bean.Level;
import stdio.kiteDream.module.level.service.LevelService;
import stdio.kiteDream.module.userEvent.service.UserEventService;
import stdio.kiteDream.module.vo.JsonVO;
import stdio.kiteDream.module.vo.PageVO;
import stdio.kiteDream.util.Constant;
import stdio.kiteDream.util.ImageUtil;

@Controller
@RequestMapping("/api/level")
public class LevelController {
	@Autowired
	LevelService levelService;
	@Autowired
	UserEventService userEventService;
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonVO listrule(HttpServletRequest request, HttpSession session,@RequestParam("page")int page,@RequestParam("size")int size,@RequestParam("userid")int userid) {
		if (BasePathJsonParser.basePath == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			BasePathJsonParser.basePath = basePath;
		}
		JsonVO json = new JsonVO();
		try {
			json.setResult(levelService.getLevels(userid));
			json.setUser_events(userEventService.checkEvent(userid));
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping(value = "/listPage", method = { RequestMethod.GET, RequestMethod.POST })
	public PageVO listPage(HttpServletRequest request, HttpSession session,@RequestParam("page")int page,@RequestParam("size")int size) {
		if (BasePathJsonParser.basePath == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			BasePathJsonParser.basePath = basePath;
		}
		PageVO json = new PageVO();
		try {
			json.setResult(levelService.getLevels(-1));
			json.setCount(levelService.getCount());
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonVO check(HttpServletRequest request, HttpSession session,Level level) {
		JsonVO json = new JsonVO();
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			ServletContext context = session.getServletContext();
			String realContextPath = context.getRealPath("/");

			String imgPre = "";
			String fileName = "";
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {

					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						fileName = file.getOriginalFilename();
						if (StringUtils.isBlank(fileName)) {
							if (level.getId()>0) {
								Level oldLevel = levelService.getLevelById(level.getId());
								level.setPath(oldLevel.getPath());
								level.setThumbnail_path(oldLevel.getThumbnail_path());
							}
							break;
						}else{
							if (level.getId()>0) {
								try {
									Level oldLevel = levelService.getLevelById(level.getId());
									File oldHeadPhoto = new File(realContextPath+"/"+oldLevel.getPath());
									File oldThumbnail_path = new File(realContextPath+"/"+oldLevel.getThumbnail_path());
									if(oldHeadPhoto.isFile()){
										oldHeadPhoto.delete();
									}
									if(oldThumbnail_path.isFile()){
										oldThumbnail_path.delete();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						imgPre = Constant.COMIC_PATH_PRE;
						File localFile = new File(realContextPath + "/" + imgPre + fileName);
						while (localFile.exists()) {
							imgPre = Constant.COMIC_PATH_PRE + new Date().getTime() + "_";
							localFile = new File(realContextPath + "/" + imgPre + fileName);
						}
						file.transferTo(localFile);

						ImageUtil.createThumbnail(localFile, realContextPath + "/" + imgPre + "thumbnail_" + fileName);
						System.out.println(localFile.getAbsolutePath());

						level.setPath(imgPre + fileName);
						level.setThumbnail_path(imgPre + "thumbnail_" + fileName);
					}

				}
				levelService.saveLevel(level);
				json.setErrorcode(Constant.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	

	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public JsonVO del(HttpSession session, @PathVariable("id") String id) {
		ServletContext context = session.getServletContext();
		String realContextPath = context.getRealPath("/");
		JsonVO json = new JsonVO();
		try {
			if (levelService.deleteLevel(id,realContextPath)) {
				json.setErrorcode(Constant.OK);
			} else {
				json.setErrorcode(Constant.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/view/{userid}", method = RequestMethod.GET)
	public JsonVO view(HttpServletRequest request, @PathVariable("userid") int userid) {
		JsonVO json = new JsonVO();
		try {
			List<Coins> coines = new ArrayList<Coins>();
			coines.add(levelService.getUserCoins(userid));
			json.setResult(coines);
			json.setErrorcode(Constant.OK);
			json.setUser_events(userEventService.checkEvent(userid));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping(value = "/getChallenge/{userid}", method = RequestMethod.GET)
	public JsonVO getChallenge(HttpServletRequest request, @PathVariable("userid") int userid) {
		JsonVO json = new JsonVO();
		try {
			;
			json.setErrorcode(levelService.getChallenge(userid));
			json.setUser_events(userEventService.checkEvent(userid));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

}