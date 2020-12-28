package com.codespine.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import sun.misc.BASE64Encoder;

public class TestNSDL2 {
	public static void main(String[] args) {
		try {
			String url = "https://oa1.zebull.in//e_sign/file//uploads//1//IVR_IMAGE//1_ivrImage.png";
			String replacedURL = StringUtil.replace(url, " ", "%20");
			InputStream in = new URL(replacedURL).openStream();
			BufferedImage bimg = ImageIO.read(in);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

//			ImageIO.write(bimg, "PNG", bos);S
//			byte[] imageBytes = bos.toByteArray();
//
//			BASE64Encoder encoder = new BASE64Encoder();
//			imageString = encoder.encode(imageBytes);
//			System.out.println(imageString);

			ImageIO.write(bimg, "png", bos);
			BASE64Encoder encoder = new BASE64Encoder();
			String result = encoder.encode(bos.toByteArray());
			System.out.print(result);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
