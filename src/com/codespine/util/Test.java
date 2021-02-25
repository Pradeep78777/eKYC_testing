package com.codespine.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.imgscalr.Scalr;
import org.json.simple.JSONObject;

@SuppressWarnings({ "unused", "deprecation" })
public class Test {
	static String img1 = "http://rest.irongates.in/pic/0-1.jpg";
	static String img2 = "http://rest.irongates.in/pic/1.png";
	static String pdf = "https://oa1.zebull.in//e_sign/file//uploads//4//PANCARD//PAN Card.pdf";
	static float pdfConstantHeight = 859.89f;
	static float pdfConstantWidth = 613.28f;

	static String img4 = "http://rest.irongates.in/pic/photo.jpeg";
	static String img3 = "https://oa1.zebull.in//e_sign/file//uploads//2//PHOTO//WhatsApp%20Image%202019-09-19%20at%209.12.49%20AM(2).jpeg";

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		// String finalPDFName = "img.pdf";
		// changePdfSizes(pdf, pdfConstantHeight, pdfConstantWidth,
		// "E:\\ekyc\\2");
		// File file = new File("C:\\Users\\prade\\Downloads\\ekyc_pdf\\2" +
		// eKYCConstant.PDF_FILE_EXTENSION);
		// PDDocument document = PDDocument.load(file);
		// pdfimageInserter(0, document, 474.5f, 594, pdf, "1", "E:\\ekyc\\2");
//		checkPayment();
		// shrinkgOnePage();

	}

	private static void checkPayment() {
		JSONObject bankJSON = new JSONObject();
		bankJSON.put(eKYCConstant.CONST_BANK_ACCOUNT_NUMBER, "1123");
		bankJSON.put(eKYCConstant.CONST_BANK_NAME, "NAME");
		bankJSON.put(eKYCConstant.CONST_BANK_IFSC, "IFSC");
		JSONObject orderRequest = new JSONObject();
		orderRequest.put(eKYCConstant.AMOUNT, 1 * 100);
		orderRequest.put(eKYCConstant.METHOD, eKYCConstant.BANKING_VIA);
		orderRequest.put(eKYCConstant.CURRENCY, eKYCConstant.RAZORPAY_CURRENCY_INR);
		orderRequest.put(eKYCConstant.RECEIPT, 1);
		orderRequest.put(eKYCConstant.CONST_BANK_ACCOUNT, bankJSON);
		System.out.println(orderRequest);
	}

	private static void changePdfSizes(String pdfURL, float pdfConstantHeight2, float pdfConstantWidth2, String string)
			throws MalformedURLException, IOException {
		PDDocument pddDocument1 = new PDDocument();
		PDPage page = new PDPage();
		pddDocument1.addPage(page);
		// PDPageTree mergePD1 = pddDocument1.getPages();
		String replacedURL = StringUtil.replace(pdfURL, " ", "%20");
		InputStream inputStream = new URL(replacedURL).openStream();
		PDDocument pddDocument = PDDocument.load(inputStream);
		// PDPageTree mergePD = pddDocument.getPages();
		PDFRenderer pdfRenderer = new PDFRenderer(pddDocument);
		BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
		ByteArrayOutputStream os1 = new ByteArrayOutputStream();
		ImageIO.write(bim, "png", os1);
		String imagePath = string + eKYCConstant.WINDOWS_FORMAT_SLASH + "1.png";
		InputStream is1 = new ByteArrayInputStream(os1.toByteArray());
		int read = 0;
		System.out.println(imagePath);
		OutputStream out1 = new FileOutputStream(imagePath);
		byte[] bytes = new byte[1024];
		while ((read = is1.read(bytes)) != -1) {
			out1.write(bytes, 0, read);
		}
		out1.flush();
		out1.close();
		is1.close();
		os1.close();
		PDImageXObject pdImage = null;
		pdImage = LosslessFactory.createFromImage(pddDocument1, bim);
		PDPageContentStream contentStream = new PDPageContentStream(pddDocument1, pddDocument1.getPages().get(0));
		contentStream.drawImage(pdImage, 25, (0));
		// x++;
		inputStream.close();
		contentStream.close();
		pddDocument1.save(string + eKYCConstant.WINDOWS_FORMAT_SLASH + "1.pdf");
		// PDPageContentStream contentStream1 = new
		// PDPageContentStream(pddDocument1, page);
		// contentStream1.drawImage(pdImage, 0, (0));
		// contentStream1.close();
	}

	private static void shrinkgOnePage() {
		try {
			float newHeight = PDRectangle.A4.getHeight() - 10;
			float newWidth = PDRectangle.A4.getWidth() - 10;
			PDDocument pdDocument = new PDDocument();
			PDDocument oDocument = PDDocument.load(new File("E:\\ekyc\\1\\1608794266147\\BZAPG5040A.pdf"));
			PDFRenderer pdfRenderer = new PDFRenderer(oDocument);
			int numberOfPages = oDocument.getNumberOfPages();
			PDPage page = null;

			for (int i = 17; i < numberOfPages; i++) {
				page = new PDPage(PDRectangle.A4);
				BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 300, ImageType.RGB);
				PDImageXObject pdImage = JPEGFactory.createFromImage(pdDocument, bim);
				int oriHeight = pdImage.getHeight();
				int oriWidth = pdImage.getWidth();
				System.out.println("oH" + oriHeight);
				System.out.println("oW" + oriWidth);
				System.out.println("H" + newHeight);
				System.out.println("W" + newWidth);
				PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
				contentStream.drawImage(pdImage, 0, 0, newWidth, newHeight);
				contentStream.close();

				pdDocument.addPage(page);
			}

			pdDocument.save("E:/ekyc/1/1608794266147/out.pdf");
			pdDocument.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
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
		// int changedXValue = 0;
		// int changedYValue = 0;
		if (bimg.getWidth() <= 100) {
			scaledWidth = bimg.getWidth();
		}
		if (bimg.getHeight() <= 100) {
			scaledHeight = bimg.getHeight();
		}

		int xAxis = 0;
		int yAxis = 0;
		if (height > width) {
			// then you move X axis
			// xAxis = ((width - scaledWidth) / 2);
			xAxis = ((scaledHeight - scaledWidth) / 2);
		} else {
			// Then you move Y axis
			// yAxis = ((height - scaledHeight) / 2);
			yAxis = ((scaledWidth - scaledHeight) / 2);
		}
		// if (scaledWidth <= 100) {
		// if (scaledWidth > scaledHeight) {
		// changedYValue = (scaledWidth - scaledHeight) / 2;
		//// }else {
		//// changedXValue = (scaledHeight - scaledWidth)/2;
		// }
		// } else {
		// if (scaledHeight > scaledWidth) {
		// changedXValue = (scaledHeight - scaledWidth) / 2;
		//// }else {
		//// changedYValue = (scaledWidth - scaledHeight)/2;
		// }
		// }
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

	public static void pdfTextInserter(PDDocument document, String insertValue, int xValue, int yValue)
			throws Exception {
		PDPage page = document.getPage(0);
		PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true);
		contentStream.beginText();
		contentStream.setNonStrokingColor(0, 0, 0);
		File f = new File("E:\\ekyc\\" + "MonospaceTypewriter.ttf");
		PDFont font1 = PDTrueTypeFont.loadTTF(document, f);
		// if (resizeRequired > 0) {
		// contentStream.setCharacterSpacing(0.4f);
		// if(pageNumber == 2 && StringUtil.isEqual(ColumnName,"reduced_city"))
		// {
		//// contentStream.setFont(font1, 2);
		// contentStream =
		// changeInputTextSizesForCity(contentStream,font1,insertValue,pageNumber,ColumnName);
		// }else {
		// contentStream =
		// changeInputTextSizes(contentStream,font1,insertValue,pageNumber,ColumnName);
		// }
		// } else {
		// contentStream.setFont(font1, 8);
		// contentStream.setCharacterSpacing(0.4f);
		// }
		contentStream.setFont(font1, 8);
		contentStream.setCharacterSpacing(0.4f);
		contentStream.newLineAtOffset(xValue, yValue);
		String text = insertValue;
		contentStream.showText(text);
		contentStream.endText();
		contentStream.close();
	}
}
