package stdio.kiteDream.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class ImageUtil {
	public static void createThumbnail(File localFile,String targetImg) throws IOException {
		BufferedImage img = ImageIO.read(localFile);  
		if(img.getWidth()>=img.getHeight()){
			Thumbnails.of(localFile).sourceRegion((img.getWidth()-img.getHeight())/2, 0, img.getHeight(), img.getHeight())
			.size(100, 100).keepAspectRatio(false)
			.toFile(targetImg);
		}else{
			Thumbnails.of(localFile).sourceRegion(0,(img.getHeight()-img.getWidth())/2, img.getWidth(), img.getWidth())
			.size(100, 100).keepAspectRatio(false)
			.toFile(targetImg);
		}
	}

}
