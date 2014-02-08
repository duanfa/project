package stdio.kiteDream.module.product.controller;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import stdio.kiteDream.module.comic.bean.BasePathJsonParser;
import stdio.kiteDream.module.product.bean.Product;
import stdio.kiteDream.module.product.service.ProductService;
import stdio.kiteDream.module.vo.JsonVO;
import stdio.kiteDream.util.Constant;
import stdio.kiteDream.util.ImageUtil;

@Controller
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@ResponseBody
	@RequestMapping(value = "/delete/{productId}", method = RequestMethod.GET)
	public String del(ModelMap model, @PathVariable("productId") String productId) {
		try {
			if (productService.delProduct(productId)) {
				return "{\"result\":\"success\",\"info\":\"none\"}";
			} else {
				return "{\"result\":\"fail\",\"info\":\"none\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"fail\",\"info\":\"" + e.getMessage() + "\"}";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JsonVO list(HttpServletRequest request) {
		if (BasePathJsonParser.basePath == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			BasePathJsonParser.basePath = basePath;
		}
		JsonVO json = new JsonVO();
		try {
			json.setResult(productService.getProducts());
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	public String add(HttpServletRequest request, HttpSession session, @RequestParam(value = "id", required = false) String id, @RequestParam("name") String name,
			@RequestParam("price") float price, @RequestParam("num") int num, @RequestParam("info") String info) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		ServletContext context = session.getServletContext();
		String realContextPath = context.getRealPath("/");
		try {
			String imgPre_path = "";
			String fileName_path = "";
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

				Iterator<String> iter = multiRequest.getFileNames();
				List<String> pics = new ArrayList<String>();
				while (iter.hasNext()) {

					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String fileName = file.getOriginalFilename();
						if (StringUtils.isBlank(fileName)) {
							if (StringUtils.isBlank(id)) {
								return "{\"result\":\"fail\",\"info\":\"must need upload the image\"}";
							}
							break;
						}
						String imgPre = Constant.COMIC_PATH_PRE;
						File localFile = new File(realContextPath + "/" + imgPre + fileName);
						while (localFile.exists()) {
							imgPre = Constant.COMIC_PATH_PRE + new Date().getTime() + "_";
							localFile = new File(realContextPath + "/" + imgPre + fileName);
						}
						file.transferTo(localFile);

						if(!"fileUpload".equals(file.getName())){
							pics.add(imgPre + fileName);
						}else{
							fileName_path = fileName;
							imgPre_path = imgPre;
							ImageUtil.createThumbnail(localFile, realContextPath + "/" + imgPre + "thumbnail_" + fileName);
						}
						System.out.println(localFile.getAbsolutePath());

					}

				}
				Product product = null;
				if (StringUtils.isNotBlank(id)) {
					product = productService.getProduct(id);
					if (StringUtils.isNotBlank(fileName_path)) {
						product.setPath(imgPre_path + fileName_path);
						product.setThumbnail_path(imgPre_path + "thumbnail_" + fileName_path);
						product.setPics(pics);
					}
				} else {
					product = new Product();
					product.setPath(imgPre_path + fileName_path);
					product.setThumbnail_path(imgPre_path + "thumbnail_" + fileName_path);
					product.setPics(pics);
				}
				product.setName(name);
				product.setInfo(info);
				product.setPrice(price);
				product.setNum(num);
				productService.saveProduct(product);
			}
			return "{\"result\":\"success\",\"info\":\"none\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"fail\",\"info\":\"" + e.getMessage() + "\"}";
		}
	}

}