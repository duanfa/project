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

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import stdio.kiteDream.module.comic.bean.Comic;
import stdio.kiteDream.module.comic.service.ComicService;
import stdio.kiteDream.util.Constant;

@Controller
@RequestMapping("/api/comic")
public class ComicController {
	@Autowired
	ComicService comicService;

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addComic(HttpServletRequest request, HttpSession session,
			@RequestParam("name") String name,
			@RequestParam("level") int level, @RequestParam("order") int order,
			@RequestParam("info") String info) throws IllegalStateException,
			IOException {
		// 设置上下方文
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			ServletContext context = session.getServletContext();
			String realContextPath = context.getRealPath("/");

			String path = "";
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {

					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String fileName = file.getOriginalFilename();
						path = Constant.COMIC_PATH_PRE + fileName;
						File localFile = new File(realContextPath + "/" + path);
						if (localFile.exists()) {
							path = Constant.COMIC_PATH_PRE
									+ new Date().getTime() + "_" + fileName;
							localFile = new File(realContextPath + "/" + path);
						}
						file.transferTo(localFile);
						System.out.println(localFile.getAbsolutePath());
					}

				}
				Comic comic = new Comic();
				comic.setName(name);
				comic.setInfo(info);
				comic.setLevel(level);
				comic.setOrderNum(order);
				comic.setPath(path);
				comicService.saveComic(comic);
			}
			return "{result:'success',info:'none'}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{result:'fail',info:'" + e.getMessage() + "'}";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/list/{level}", method = RequestMethod.GET)
	public List<Comic> listLevel(ModelMap model,
			@PathVariable("level") int level) {
		try {
			return comicService.getComics(level);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Comic>();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/list/all", method = RequestMethod.GET)
	public List<List<Comic>> listAll(ModelMap model) {
		List<List<Comic>> result = new ArrayList<List<Comic>>();
		List<Integer> levels = new ArrayList<Integer>();
		try {
			List<Comic> comics = comicService.getComics();
			List<Comic> currentComics = null;
			for (Comic comic : comics) {
				if (levels.contains(comic.getLevel())) {
					currentComics.add(comic);
				} else {
					if(currentComics!=null){
						/*List<Comic> tmp = new ArrayList<Comic>();
						tmp.addAll(currentComics);*/
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
			if (comicService.delComic(comicId)) {
				return "{result:'success',info:'none'}";
			} else {
				return "{result:'fail',info:'none'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{result:'fail',info:'" + e.getMessage() + "'}";
		}
	}
}