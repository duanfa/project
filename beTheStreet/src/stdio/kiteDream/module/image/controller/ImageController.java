package stdio.kiteDream.module.image.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import stdio.kiteDream.module.image.bean.Image.Check;
import stdio.kiteDream.module.image.service.ImageService;
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
			Image image) throws IllegalStateException, IOException {
		JsonVO json = new JsonVO();
		// 璁剧疆涓婁笅鏂规枃
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			ServletContext context = session.getServletContext();
//			String realContextPath = context.getRealPath("/");
			String realContextPath = Constant.REAL_PATH_PRE;

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
						Calendar now = Calendar.getInstance();
						imgPre = Constant.COMIC_PATH_PRE+(now.get(Calendar.MONTH) + 1)+"/"+now.get(Calendar.DAY_OF_MONTH)+"/"+now.get(Calendar.HOUR_OF_DAY)+"/"+now.get(Calendar.MINUTE);
						File dir = new File(realContextPath+"/"+imgPre);
						if(!dir.exists()){
							dir.mkdirs();
						}
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
				image.setPath(imgPre + fileName);
				image.setThumbnail_path(imgPre + "thumbnail_" + fileName);
				image.setCreate_time(new Date());
				image.setStatu(Image.Check.UNREAD);
				image.setIp(request.getRemoteAddr());
				
				System.out.println("userid is :"+userid);
				json.setResult(imageService.saveImage(image,userid));
				
			}
			json.setErrorcode(Constant.OK);
			json.setUser_events(userEventService.checkEvent(userid));
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	 public static void main(String[] args) throws ParseException {  
	        Calendar now = Calendar.getInstance();  
	        System.out.println("年: " + now.get(Calendar.YEAR));  
	        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");  
	        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));  
	        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));  
	        System.out.println("分: " + now.get(Calendar.MINUTE));  
	        System.out.println("秒: " + now.get(Calendar.SECOND));  
	        System.out.println("当前时间毫秒数：" + now.getTimeInMillis());  
	        System.out.println(now.getTime());  
	  
	        Date d = new Date();  
	        System.out.println(d);  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String dateNowStr = sdf.format(d);  
	        System.out.println("格式化后的日期：" + dateNowStr);  
	          
	        String str = "2012-1-13 17:26:33";  //要跟上面sdf定义的格式一样  
	        Date today = sdf.parse(str);  
	        System.out.println("字符串转成日期：" + today);  
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
	public JsonVO check(HttpServletRequest request, @PathVariable("imageid") String imageid, @RequestParam("statu") Check statu) {
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