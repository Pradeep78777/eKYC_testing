package com.codespine.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.imgscalr.Scalr;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.codespine.data.eKYCDAO;
import com.codespine.dto.PdfCoordinationsDTO;
import com.codespine.dto.eKYCDTO;

public class FinalPDFGenerator {
	static String filePath = eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH);
	static String sourceFilePath = filePath +eKYCDAO.getInstance().getFileLocation(eKYCConstant.CONSTANT_PDF_NAME);
	static String destinationFilePath =  filePath;
//	static String sourceFilePath = "C:\\Users\\user\\Downloads\\" + "2.pdf";
//	static String destinationFilePath = "C:\\Users\\user\\Downloads\\" + "1.pdf";

	public static void main(String[] args) throws Exception {
//		File file = new File(sourceFilePath);
//		PDDocument document = PDDocument.load(file);
//		String val = "AAAAAIIIII WWWWWWWWWW AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";// AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";// 153
//		String val1 ="AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";//142 - 
//		String val2 ="AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";//131 - 
//		String val3 ="AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";//120 - 
//		String val4 ="AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";//109 - 
//		String val5 ="AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";//98 - 
//		String val6 ="AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";//87 - 
//		String val7 ="AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";//76 - 
//		String val8 ="AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";//65 - 
//		String val9 ="AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";//54 - 
//		String val10 ="AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";//43 - size 10
//		PDPage page = document.getPage(0);
//		PDRectangle mediaBox = page.getMediaBox();
//		System.out.println("Width:" + mediaBox.getWidth());
//		System.out.println("Height:" + mediaBox.getHeight());
//		File f = new File("C:\\Users\\user\\Downloads\\MonospaceTypewriter.ttf");
//		PDFont font1 = PDTrueTypeFont.loadTTF(document, f);
//		if(!val.isEmpty() && val.length() > 70 && val.length() < 80 ) {
//			contentStream.setFont(font1, 5);	
//		}else if(!val.isEmpty() && val.length() > 80 && val.length() < 90 ) {
//			contentStream.setFont(font1, 4);	
//		}else if(!val.isEmpty() && val.length() > 90 && val.length() < 110 ) {
//			contentStream.setFont(font1, 6);	
//		}else if(!val.isEmpty() && val.length() > 100 && val.length() < 110 ) {
//			contentStream.setFont(font1, 2);	
//		}
//		else {
//			contentStream.setFont(font1, 10);
//		}
//		contentStream.newLineAtOffset(105, 752);
//		contentStream.setCharacterSpacing(1);
//		contentStream.showText(val);
//		contentStream.endText();
//		contentStream.close();
//		
//		document.save(destinationFilePath);
//		System.out.println("pdf Generated");
//		document.close();
	}

	public static String pdfInserterRequiredValues(eKYCDTO eKYCdto, String folderName) throws Exception {
		File file = new File(sourceFilePath+ eKYCConstant.PDF_FILE_EXTENSION);
		String application_id = eKYCdto.getForPDFKeyValue().get("application_id");
		if(StringUtil.isNullOrEmpty(folderName)) {
			long timeInmillsecods = System.currentTimeMillis();
			folderName = String.valueOf(timeInmillsecods);
		}
		String finalSestinationFilePath = destinationFilePath + eKYCConstant.WINDOWS_FORMAT_SLASH + application_id+ eKYCConstant.WINDOWS_FORMAT_SLASH+folderName;
		File dir = new File(finalSestinationFilePath);
		String finalPDFName = eKYCDAO.getInstance().getFileLocation(eKYCConstant.CONSTANT_PDF_NAME) + eKYCConstant.PDF_FILE_EXTENSION;
		if (!dir.exists()) {
			dir.mkdirs();
		}
		PDDocument document = PDDocument.load(file);
		List<PdfCoordinationsDTO> pdfCoordinationsDTOs = eKYCDAO.getInstance().getPdfCoordinations();
		File tickFontFile = new File(filePath+"ARIALUNI.ttf");
		PDType0Font font = PDType0Font.load(document, tickFontFile);
		for (PdfCoordinationsDTO DTO : pdfCoordinationsDTOs) {
			String coordinates = DTO.getCoordinates();
			if (DTO.getIs_default() < 1) {
				String[] orgs = StringUtil.split(coordinates, eKYCConstant.COMMA_SEPERATOR);
				int xValue = Integer.parseInt(orgs[0]);
				int yValue = Integer.parseInt(orgs[1]);
				if (StringUtil.isNotNullOrEmpty(eKYCdto.getForPDFKeyValue().get(DTO.getColumn_name()))
						&& !StringUtil.isEqual(DTO.getColumn_name(), eKYCConstant.IMAGE)) {
					pdfInserter(DTO.getPage_number(),document,eKYCdto.getForPDFKeyValue().get(DTO.getColumn_name()),
							xValue, yValue, DTO.getIs_value_reduced());
				} else if (StringUtil.isEqual(DTO.getColumn_name(), eKYCConstant.IMAGE)) {
					pdfimageInserter(DTO.getPage_number(),document,xValue, yValue, "13.jpg", application_id,
							finalSestinationFilePath + eKYCConstant.WINDOWS_FORMAT_SLASH);
//				if(eKYCdto.getForPDFKeyValue().get(columnsNames.get(i)) != null) {
//					pdfimageInserter(document, pageNumber,xValue,yValue, eKYCdto.getForPDFKeyValue().get(columnsNames.get(i)),application_id,finalSestinationFilePath+eKYCConstant.WINDOWS_FORMAT_SLASH);
				}
			} else {
				if (StringUtil.isEqual(DTO.getColumn_name(), "TICK")) {
					String[] orgs = StringUtil.split(coordinates, eKYCConstant.COMMA_SEPERATOR);
					int xValue = Integer.parseInt(orgs[0]);
					int yValue = Integer.parseInt(orgs[1]);
					pdfTickInserter(font,DTO.getPage_number(),document,xValue, yValue);
				} else if (StringUtil.isStrContainsWithEqIgnoreCase(DTO.getColumn_name(), "DEFAULT")) {
					String[] orgs = StringUtil.split(coordinates, eKYCConstant.COMMA_SEPERATOR);
					int xValue = Integer.parseInt(orgs[0]);
					int yValue = Integer.parseInt(orgs[1]);
					String columnValue = StringUtil.replace(DTO.getColumn_name(), "DEFAULT_", "");
					pdfInserter(DTO.getPage_number(),document,columnValue, xValue, yValue, DTO.getIs_value_reduced());
				} else {
					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(coordinates);
					if (StringUtil.isNotNullOrEmpty(eKYCdto.getForPDFKeyValue().get(DTO.getColumn_name()))) {
						String A = (String) json
								.get(eKYCdto.getForPDFKeyValue().get(DTO.getColumn_name()).toLowerCase());
						String[] orgs1 = StringUtil.split(A, eKYCConstant.COMMA_SEPERATOR);
						int xValue1 = Integer.parseInt(orgs1[0]);
						int yValue1 = Integer.parseInt(orgs1[1]);
						pdfTickInserter(font,DTO.getPage_number(),document, xValue1, yValue1);
					}
				}
			}
		}
		// Saving the document
		document.save(new File(finalSestinationFilePath + eKYCConstant.WINDOWS_FORMAT_SLASH + finalPDFName));
		System.out.println("pdf Generated");
		// Closing the document
		document.close();
		return  application_id+ eKYCConstant.WINDOWS_FORMAT_SLASH+folderName
				+ eKYCConstant.WINDOWS_FORMAT_SLASH + finalPDFName;
	}

	@SuppressWarnings({ "deprecation", "resource" })
	public static void pdfInserter(int pageNumber,PDDocument document,String insertValue, int xValue, int yValue,int resizeRequired) throws Exception {
		PDPage page = document.getPage(pageNumber);
	   	PDPageContentStream contentStream = new PDPageContentStream(document, page,AppendMode.APPEND, true);
		contentStream.beginText();
		contentStream.setNonStrokingColor(0, 0, 0);
		File f = new File(filePath+"MonospaceTypewriter.ttf");
		PDFont font1 = PDTrueTypeFont.loadTTF(document, f);
		if (resizeRequired > 0) {
			contentStream = changeInputTextSizes(contentStream,font1,insertValue,pageNumber);
		} else {
			contentStream.setFont(font1, 11);
			contentStream.setCharacterSpacing(5);
		}
		contentStream.newLineAtOffset(xValue, yValue);
		String text = insertValue;
		contentStream.showText(text);
		contentStream.endText();
		contentStream.close();
	}

	private static PDPageContentStream changeInputTextSizes(PDPageContentStream contentStream, PDFont font1,String insertValue, int pageNumber) throws Exception {
//		if(pageNumber == 2) {
//			
//		}
		contentStream.setCharacterSpacing(1);
		if(!insertValue.isEmpty() && insertValue.length() > 70 && insertValue.length() < 80 ) {
			contentStream.setFont(font1, 8);	
		}else if(!insertValue.isEmpty() && insertValue.length() > 80 && insertValue.length() < 90 ) {
			contentStream.setFont(font1, 7);	
		}else if(!insertValue.isEmpty() && insertValue.length() > 90 && insertValue.length() < 110 ) {
			contentStream.setFont(font1, 6);	
		}else {
			contentStream.setFont(font1, 11);
		}
		return contentStream;
	}

	private static void pdfimageInserter(int pageNumber,PDDocument document,int xValue, int yValue, String image,String application_id, String finalSestinationFilePath) throws IOException {
		PDPage page = document.getPage(pageNumber);
	   	PDPageContentStream stream = new PDPageContentStream(document, page,AppendMode.APPEND, true);
		String File_path = eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH);
		File file1 = new File(File_path + image);
		String resizeImageName = image;
		BufferedImage img = ImageIO.read(file1);
		BufferedImage scaledImg = Scalr.resize(img, 100, 100);
		resizeImageName = StringUtil.isImageReSizeExist(finalSestinationFilePath, application_id);
		File file = new File(finalSestinationFilePath, resizeImageName);
		ImageIO.write(scaledImg, "JPG", file);
		PDImageXObject pdImage = PDImageXObject.createFromFile(finalSestinationFilePath + resizeImageName, document);
		stream.drawImage(pdImage, xValue, yValue);
		stream.close();

	}
	@SuppressWarnings("deprecation")
	private static void pdfTickInserter(PDType0Font font,int pageNumber,PDDocument document, int xValue, int yValue) throws IOException {
		PDPage page = document.getPage(pageNumber);
	   	PDPageContentStream stream = new PDPageContentStream(document, page,AppendMode.APPEND, true);
	   	String ascii4 ="✓";
	    stream.beginText();
	    stream.setFont(font, 12);
	    stream.moveTextPositionByAmount(xValue,yValue);
	    stream.drawString(ascii4);
	    stream.endText();
	    stream.close();
	}
}
