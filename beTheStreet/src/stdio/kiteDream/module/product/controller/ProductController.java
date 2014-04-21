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
import stdio.kiteDream.module.comic.bean.Comic;
import stdio.kiteDream.module.product.bean.Product;
import stdio.kiteDream.module.product.bean.ProductCategory;
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
	public List<List<Product>> list(HttpServletRequest request) {
		if (BasePathJsonParser.basePath == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			BasePathJsonParser.basePath = basePath;
		}
		List<List<Product>> result = new ArrayList<List<Product>>();
		List<Integer> levels = new ArrayList<Integer>();
		try {
			List<Product> comics = productService.getProducts();
			List<Product> currentComics = null;
			for (Product comic : comics) {
				if (levels.contains(comic.getCategory().getId())) {
					currentComics.add(comic);
				} else {
					if (currentComics != null) {
						result.add(currentComics);
					}
					currentComics = new ArrayList<Product>();
					currentComics.add(comic);
					levels.add(comic.getCategory().getId());
				}
			}
			result.add(currentComics);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/list/{categoryId}", method = RequestMethod.GET)
	public JsonVO listByCategory(HttpServletRequest request,@PathVariable("categoryId") String categoryId) {
		if (BasePathJsonParser.basePath == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			BasePathJsonParser.basePath = basePath;
		}
		JsonVO json = new JsonVO();
		try {
			json.setResult(productService.getProductsByCategory(categoryId));
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/listcategory", method = RequestMethod.GET)
	public JsonVO listcategory(HttpServletRequest request) {
		JsonVO json = new JsonVO();
		try {
			json.setResult(productService.getProductCategorys());
			json.setErrorcode(Constant.OK);
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addcategory", method = RequestMethod.GET)
	public JsonVO addcategory(ProductCategory category) {
		JsonVO json = new JsonVO();
		try {
			if(productService.saveProductCategory(category)){
				json.setErrorcode(Constant.OK);
			}else{
				json.setErrorcode(Constant.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delCategory", method = RequestMethod.GET)
	public JsonVO delCategory(@RequestParam("id") String id) {
		JsonVO json = new JsonVO();
		try {
			if(productService.delProductCategory(id)){
				json.setErrorcode(Constant.OK);
			}else{
				json.setErrorcode(Constant.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setErrorcode(Constant.FAIL);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/upload", method = { RequestMethod.POST },produces="text/plain;charset=UTF-8")
	public String add(HttpServletRequest request, HttpSession session, @RequestParam(value = "id", required = false) String id, @RequestParam("name") String name,
			@RequestParam("categoryid") String categoryid, @RequestParam("price") float price, @RequestParam("num") int num, @RequestParam("info") String info, @RequestParam("type") Product.Type type) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		ServletContext context = session.getServletContext();
//		String realContextPath = context.getRealPath("/");
		String realContextPath = Constant.REAL_PATH_PRE;
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

						if (!"fileUpload".equals(file.getName())) {
							pics.add(imgPre + fileName);
						} else {
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
				product.setCategory(productService.getProductCategory(categoryid));
				product.setName(name);
				product.setInfo(info);
				product.setPrice(price);
				product.setNum(num);
				product.setType(type);
				productService.saveProduct(product);
			}
			return "{\"result\":\"success\",\"info\":\"none\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"fail\",\"info\":\"" + e.getMessage() + "\"}";
		}
	}

}