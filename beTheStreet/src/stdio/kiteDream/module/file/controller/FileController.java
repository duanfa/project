package stdio.kiteDream.module.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping("/api/file")
public class FileController {

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String fileUpload2(HttpServletRequest request)
			throws IllegalStateException, IOException {
		// 设置上下方文
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());

			// 检查form是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {

					// 由CommonsMultipartFile继承而来,拥有上面的方法.
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String fileName = "demoUpload" + file.getOriginalFilename();
						String path = "/home/duanfa/tmp/" + fileName;
						System.out.println(path);
						File localFile = new File(path);
						file.transferTo(localFile);
					}

				}
			}
			return "{result:'success',info:'none'}";
		} catch (Exception e) {
			e.printStackTrace();		
			return "{result:'fail',info:'"+e.getMessage()+"'}";
		}
	}
}