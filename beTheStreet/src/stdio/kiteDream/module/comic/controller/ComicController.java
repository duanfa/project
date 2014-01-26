package stdio.kiteDream.module.comic.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import stdio.kiteDream.module.comic.bean.Comic;
import stdio.kiteDream.module.comic.bean.ComicJsonPathParser;
import stdio.kiteDream.module.comic.service.ComicService;
import stdio.kiteDream.module.image.bean.Image;
import stdio.kiteDream.module.userEvent.service.UserEventService;
import stdio.kiteDream.module.vo.ComicVO;
import stdio.kiteDream.module.vo.JsonVO;
import stdio.kiteDream.util.Constant;
import stdio.kiteDream.util.ImageUtil;

@Controller
@RequestMapping("/api/comic")
public class ComicController {
	@Autowired
	UserEventService userEventService;
	@Autowired
	ComicService comicService;
	
	Comic goComic ;

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addComic(HttpServletRequest request, HttpSession session, @RequestParam("name") String name, @RequestParam("level") int level, @RequestParam("order") int order,
			@RequestParam("info") String info,@RequestParam("type") String type,@RequestParam(value="id",required=false) String id) throws IllegalStateException, IOException {
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
							if(StringUtils.isBlank(id)){
								return "{\"result\":\"fail\",\"info\":\"must need upload the image\"}";
							}
							break;
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
				Comic comic = null;
				if(StringUtils.isNotBlank(id)){
					comic = comicService.getComic(id);
					if(StringUtils.isNotBlank(fileName)){
						comic.setPath(imgPre + fileName);
						comic.setThumbnail_path(imgPre + "thumbnail_" + fileName);
					}
				}else{
					comic = new Comic();
					comic.setPath(imgPre + fileName);
					comic.setThumbnail_path(imgPre + "thumbnail_" + fileName);
				}
				if(type!=null&&Image.Type.BONUS.toString().equals(type.toUpperCase())){
					comic.setType(Image.Type.BONUS);
				}else if(type!=null&&Image.Type.STREET.toString().equals(type.toUpperCase())){
					comic.setType(Image.Type.STREET);
				}else if(type!=null&&Image.Type.CHALLENGE.toString().equals(type.toUpperCase())){
					comic.setType(Image.Type.CHALLENGE);
				}
				comic.setName(name);
				comic.setInfo(info);
				comic.setLevel(level);
				comic.setOrderNum(order);
				comicService.saveComic(comic);
			}
			return "{\"result\":\"success\",\"info\":\"none\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"fail\",\"info\":\"" + e.getMessage() + "\"}";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/list/{level}", method = RequestMethod.GET)
	public JsonVO listLevel(HttpServletRequest request, @PathVariable("level") int level, @RequestParam(value = "userid", required = false) int userid) {
		JsonVO jsonVO = new JsonVO();
		try {
			if (ComicJsonPathParser.basePath == null) {
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
				ComicJsonPathParser.basePath = basePath;
				if(goComic==null){
					goComic = new Comic("go comic","comicDir/bethestreet_go.jpg","comicDir/thumbnail_bethestreet_go.jpg");	
				}
			}
			jsonVO.setUser_events(userEventService.checkEvent(userid));
			ComicVO comicvo = new ComicVO();
			List<Comic> bonusComic = comicService.getComics(level,Image.Type.BONUS);
			bonusComic.add(goComic);
			List<Comic> streetComic = comicService.getComics(level,Image.Type.STREET);
			comicvo.setBonusComic(bonusComic);
			streetComic.add(goComic);
			comicvo.setStreetComic(streetComic);
			List<ComicVO> comicvos = new ArrayList<ComicVO>();
			comicvos.add(comicvo);
			jsonVO.setResult(comicvos);
			jsonVO.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			jsonVO.setErrorcode(Constant.FAIL);
		}
		return jsonVO;
	}

	@ResponseBody
	@RequestMapping(value = "/list/all", method = RequestMethod.GET)
	public List<List<Comic>> listAll(HttpServletRequest request) {
		if (ComicJsonPathParser.basePath == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			ComicJsonPathParser.basePath = basePath;
		}
		List<List<Comic>> result = new ArrayList<List<Comic>>();
		List<Integer> levels = new ArrayList<Integer>();
		try {
			List<Comic> comics = comicService.getComics();
			List<Comic> currentComics = null;
			for (Comic comic : comics) {
				if (levels.contains(comic.getLevel())) {
					currentComics.add(comic);
				} else {
					if (currentComics != null) {
						/*
						 * List<Comic> tmp = new ArrayList<Comic>();
						 * tmp.addAll(currentComics);
						 */
						result.add(currentComics);
					}
					currentComics = new ArrayList<Comic>();
					currentComics.add(comic);
					levels.add(comic.getLevel());
				}
			}
			result.add(currentComics);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/delete/{comicId}", method = RequestMethod.GET)
	public String del(ModelMap model, @PathVariable("comicId") String comicId) {
		try {
			if (comicService.deleteComic(comicId)) {
				return "{\"result\":\"success\",\"info\":\"none\"}";
			} else {
				return "{\"result\":\"fail\",\"info\":\"none\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"fail\",\"info\":\"" + e.getMessage() + "\"}";
		}
	}
	
	
}