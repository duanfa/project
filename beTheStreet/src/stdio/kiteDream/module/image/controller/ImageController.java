package stdio.kiteDream.module.image.controller;

import java.io.File;
import java.io.IOException;
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

import stdio.kiteDream.module.comic.bean.BasePathJsonParser;
import stdio.kiteDream.module.image.bean.Image;
import stdio.kiteDream.module.image.bean.Image.Type;
import stdio.kiteDream.module.image.service.ImageService;
import stdio.kiteDream.module.user.bean.User;
import stdio.kiteDream.module.user.service.UserService;
import stdio.kiteDream.module.userEvent.service.UserEventService;
import stdio.kiteDream.module.vo.JsonVO;
import stdio.kiteDream.module.vo.PageVO;
import stdio.kiteDream.util.Constant;
import stdio.kiteDream.util.ImageUtil;

@Controller
@RequestMapping("/api/image")
public class ImageController {
	@Autowired
	ImageService imageService;
	@Autowired
	UserService userService;
	@Autowired
	UserEventService userEventService;

	@ResponseBody
	@RequestMapping(value = "/user/upload", method = RequestMethod.POST)
	public JsonVO uploadImage(HttpServletRequest request, HttpSession session, @RequestParam("userid") int userid,
			@RequestParam(value = "imgname", required = false) String imgname, @RequestParam(value = "desc", required = false) String desc,
			@RequestParam(value = "level", required = false) int level,
			@RequestParam(value = "gps", required = false) String gps,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "levelType", required = false) Type levelType
			) throws IllegalStateException, IOException {
		JsonVO json = new JsonVO();
		// 设置上下方文
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
							json.setErrorcode(Constant.FAIL);
							return json;
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
					}

				}
				Image image = new Image();
				image.setPath(imgPre + fileName);
				image.setThumbnail_path(imgPre + "thumbnail_" + fileName);
				image.setCreate_time(new Date());
				image.setDescrib(desc);
				image.setLevel(level);
				image.setName(fileName);
				image.setStatu(Image.Check.UNREAD.toString());
				image.setGps(gps);
				image.setIp(request.getRemoteAddr());
				image.setAddress(address);
				image.setLevelType(levelType);
				if(Image.Type.STREET.equals(image.getLevelType())){
					User user = userService.getUser(userid+"");
					user.setHigh_level(image.getLevel());
					user.setHigh_level_stage(image.getLevel_stage());
					userService.saveUser(user);
				}
				System.out.println("userid is :"+userid);
				imageService.saveImage(image,userid);
			}
			json.setErrorcode(Constant.OK);
			json.setUser_events(userEventService.checkEvent(userid));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/list/{userid}", method = RequestMethod.GET)
	public PageVO listUesrImage(HttpServletRequest request, @PathVariable("userid") int userid,@RequestParam("page") int page,@RequestParam("size") int size) {
		if (BasePathJsonParser.basePath == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			BasePathJsonParser.basePath = basePath;
		}
		PageVO jsonVO = new PageVO();
		try {
			List<Image> images = imageService.getUserImages(userid,page,size);
			jsonVO.setCount(imageService.getUserImageCount(userid));
			jsonVO.setResult(images);
			jsonVO.setErrorcode("ok");
		} catch (Exception e) {
			e.printStackTrace();
			jsonVO.setErrorcode("fail");
		}
		return jsonVO;
	}
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public PageVO list(HttpServletRequest request,@RequestParam("page") int page,@RequestParam("size") int size) {
		if (BasePathJsonParser.basePath == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			BasePathJsonParser.basePath = basePath;
		}
		PageVO jsonVO = new PageVO();
		try {
			List<Image> images = imageService.getImages(page, size);
			jsonVO.setCount(imageService.getImageCount());
			jsonVO.setResult(images);
			jsonVO.setErrorcode("ok");
		} catch (Exception e) {
			e.printStackTrace();
			jsonVO.setErrorcode("fail");
		}
		return jsonVO;
	}

	@ResponseBody
	@RequestMapping(value = "/check/{imageid}", method = RequestMethod.GET)
	public JsonVO check(HttpServletRequest request, @PathVariable("imageid") String imageid, @RequestParam("statu") String statu) {
		JsonVO json = new JsonVO();
		try {
			if (imageService.updateImageStatu(imageid, statu)) {
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
	@RequestMapping(value = "/delete/{imageid}", method = RequestMethod.GET)
	public boolean del(HttpServletRequest request, @PathVariable("imageid") String imageid) {
		try {
			return imageService.deleteImage(imageid);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}