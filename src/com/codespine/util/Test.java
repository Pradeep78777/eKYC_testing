package com.codespine.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.imgscalr.Scalr;

public class Test {
	static String img1 = "http://rest.irongates.in/pic/0-1.jpg";
	static String img2 = "http://rest.irongates.in/pic/1.png";

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		String date2 = "2020-12-17 12:27:30";
		String date1 = "2020-12-17 11:18:12";
//		Custom date format
	    SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

	    Date d1 = null;
	    Date d2 = null;
	    try {
	        d1 = format.parse(date1);
	        d2 = format.parse(date2);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }

	    // Get msec from each, and subtract.
	    long diff = d2.getTime() - d1.getTime();
	    long diffSeconds = diff / 1000 % 60;
	    long diffMinutes = diff / (60 * 1000) % 60;
	    long diffHours = diff / (60 * 60 * 1000);
	    long diffDays = diff / (60 * 60 *24*1000);
	    System.out.println("Time in seconds: " + diffSeconds + " seconds.");
	    System.out.println("Time in minutes: " + diffMinutes + " minutes.");
	    System.out.println("Time in hours: " + diffHours + " hours.");
	    System.out.println("Time in Days: " + diffDays + " Days.");
//		String finalPDFName = "img.pdf";
//		File file = new File("C:\\Users\\prade\\Downloads\\ekyc_pdf\\2" + eKYCConstant.PDF_FILE_EXTENSION);
//		PDDocument document = PDDocument.load(file);
//		try {
////			pdfTextInserter(document, ".", 474, 594);
////			pdfTextInserter(document, ".", 574, 594);
////			pdfTextInserter(document, ".", 474, 698);
////			pdfTextInserter(document, ".", 574, 698);
//			pdfimageInserter(0, document, 474.5f, 594, img2, "1", "C:\\Users\\prade\\Downloads\\ekyc_pdf\\");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		document.save(
//				new File("C:\\Users\\prade\\Downloads\\ekyc_pdf" + eKYCConstant.WINDOWS_FORMAT_SLASH + finalPDFName));
//		System.out.println("pdf Generated");
//		document.close();
	}

	/**
	 * 
	 * @param pageNumber
	 * @param document
	 * @param xValue
	 * @param yValue
	 * @param image
	 * @param application_id
	 * @param finalSestinationFilePath
	 * @throws IOException
	 */
	private static void pdfimageInserter(int pageNumber, PDDocument document, float xValue, int yValue, String image,
			String application_id, String finalSestinationFilePath) throws IOException {
		InputStream in = new URL(image).openStream();
		BufferedImage bimg = ImageIO.read(in);
		int height = bimg.getHeight(); // 310
		int width = bimg.getWidth(); // 500
		int scaledHeight = 100;
		int scaledWidth = 100;
		if (bimg.getWidth() > bimg.getHeight()) {
			int ratio = bimg.getWidth() / 100;
			scaledHeight = bimg.getHeight() / ratio;
			scaledWidth = 100;
		} else {
			int ratio = bimg.getHeight() / 100;
			scaledHeight = 100;
			scaledWidth = bimg.getWidth() / ratio;
		}
//		int changedXValue = 0;
//		int changedYValue = 0;
		if (bimg.getWidth() <= 100) {
			scaledWidth = bimg.getWidth();
		}
		if (bimg.getHeight() <= 100) {
			scaledHeight = bimg.getHeight();
		}
		
		int xAxis = 0;
		int yAxis = 0;
		if(height > width) {
			//then you move X axis
//			xAxis = ((width - scaledWidth) / 2);
			xAxis = ((scaledHeight - scaledWidth) / 2);
		} else {
			//Then you move Y axis
//			yAxis = ((height - scaledHeight) / 2);
			yAxis = ((scaledWidth - scaledHeight) / 2);
		}
//		if (scaledWidth <= 100) {
//			if (scaledWidth > scaledHeight) {
//				changedYValue = (scaledWidth - scaledHeight) / 2;
////			}else {
////				changedXValue = (scaledHeight - scaledWidth)/2;
//			}
//		} else {
//			if (scaledHeight > scaledWidth) {
//				changedXValue = (scaledHeight - scaledWidth) / 2;
////			}else {
////				changedYValue = (scaledWidth - scaledHeight)/2;
//			}
//		}
		BufferedImage scaledImg = Scalr.resize(bimg, scaledWidth, scaledHeight);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String imagePath = "";
		String resizeImageName = image;
		if (StringUtil.isStrContainsWithEqIgnoreCase(image, "png")) {
			ImageIO.write(scaledImg, "png", os);
			resizeImageName = StringUtil.isImageReSizeExist(finalSestinationFilePath, application_id, ".png");
			imagePath = finalSestinationFilePath + eKYCConstant.WINDOWS_FORMAT_SLASH + resizeImageName;
		} else {
			ImageIO.write(scaledImg, "jpeg", os);
			resizeImageName = StringUtil.isImageReSizeExist(finalSestinationFilePath, application_id, ".jpg");
			imagePath = finalSestinationFilePath + eKYCConstant.WINDOWS_FORMAT_SLASH + resizeImageName;
		}
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		int read = 0;
		OutputStream out = new FileOutputStream(imagePath);
		byte[] bytes = new byte[1024];
		while ((read = is.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
		is.close();
		os.close();
		PDImageXObject pdImage = null;
		if (StringUtil.isStrContainsWithEqIgnoreCase(image, "png")) {
			File f = new File(imagePath);
			BufferedImage bimg1 = ImageIO.read(f);
			pdImage = LosslessFactory.createFromImage(document, bimg1);
		} else {
			pdImage = PDImageXObject.createFromFile(imagePath, document);
		}
		PDPage page = document.getPage(pageNumber);
		PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true);
		contentStream.drawImage(pdImage, xValue + xAxis, yValue + yAxis);
		contentStream.close();
		in.close();

	}

	@SuppressWarnings("deprecation")
	public static void pdfTextInserter(PDDocument document, String insertValue, int xValue, int yValue)
			throws Exception {
		PDPage page = document.getPage(0);
		PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true);
		contentStream.beginText();
		contentStream.setNonStrokingColor(0, 0, 0);
		File f = new File("E:\\ekyc\\" + "MonospaceTypewriter.ttf");
		PDFont font1 = PDTrueTypeFont.loadTTF(document, f);
//		if (resizeRequired > 0) {
//			contentStream.setCharacterSpacing(0.4f);
//			if(pageNumber == 2 && StringUtil.isEqual(ColumnName,"reduced_city")) {
////				contentStream.setFont(font1, 2);
//				contentStream = changeInputTextSizesForCity(contentStream,font1,insertValue,pageNumber,ColumnName);
//			}else {
//				contentStream = changeInputTextSizes(contentStream,font1,insertValue,pageNumber,ColumnName);
//			}
//		} else {
//			contentStream.setFont(font1, 8);
//			contentStream.setCharacterSpacing(0.4f);
//		}
		contentStream.setFont(font1, 8);
		contentStream.setCharacterSpacing(0.4f);
		contentStream.newLineAtOffset(xValue, yValue);
		String text = insertValue;
		contentStream.showText(text);
		contentStream.endText();
		contentStream.close();
	}
}
