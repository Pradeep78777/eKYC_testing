package com.codespine.util;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDCIDFontType0;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDType3Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.imgscalr.Scalr;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.codespine.data.eKYCDAO;
import com.codespine.dto.PdfCoordinationsDTO;
import com.codespine.dto.eKYCDTO;

public class FinalPDFGenerator {
	static String sourceFilePath =  eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH)+eKYCDAO.getInstance().getFileLocation(eKYCConstant.CONSTANT_PDF_NAME);
	static String destinationFilePath =  eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH);
//	static String sourceFilePath = "C:\\Users\\user\\Downloads\\"+"2.pdf";
//	static String destinationFilePath =  "C:\\Users\\user\\Downloads\\"+ "1.pdf";
	public static void main(String[] args) throws Exception {
//		File file = new File(sourceFilePath);
//		String application_id = "1";
//		PDDocument document = PDDocument.load(file);
//		String val  ="AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA AAAAAAAAAA";//153 - 
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
//		PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, false);
//		contentStream.setFont(PDType1Font.SYMBOL, 10);
//		contentStream.beginText();
//		File f = new File("C:\\Users\\user\\Downloads\\LiberationSans-Regular.ttf");
//		PDFont font1 = PDTrueTypeFont.loadTTF(document, f);

//		PDFont font1 = PDType1Font.COURIER;
//		char aa = 'U+2714'; 
//		U+2714
//		String value = val;
//		System.out.println(value.length());
//		 char[] ch = new char[value.length()];
//		 System.out.println(ch.length);
//		 if(ch.length > 41) {
//			 contentStream.setFont(font1, 9);
//		 }else {
//		 }
//		contentStream.setFont(font1,4);
//		contentStream.showText("\u2714");
//		 contentStream.setFont(PDType1Font.HELVETICA, 11);
//		contentStream.setCharacterSpacing(5);
//		contentStream.newLineAtOffset(105,751);
//		char aa1 = '\u2714';
//		String a = "✔";
//		String text = a;
//		contentStream.showText(text);
//		pdfimageInserter(document, 0,105,751,"tick-3.png",application_id,"C:\\Users\\user\\Downloads\\","TICK");
//		contentStream.endText();
//		contentStream.close();
//		System.out.println("Content added");
//		document.save(destinationFilePath);
//		System.out.println("pdf Generated");
//		document.close();
	}
	public static String pdfInserterRequiredValues(eKYCDTO eKYCdto) throws Exception {
		
		File file = new File(sourceFilePath);
		String application_id = eKYCdto.getForPDFKeyValue().get("application_id");
		String finalSestinationFilePath = destinationFilePath+eKYCConstant.WINDOWS_FORMAT_SLASH+application_id;
		File dir = new File(finalSestinationFilePath);
		String finalPDFName = application_id +eKYCConstant.PDF_FILE_EXTENSION;
		if (!dir.exists()) {
			dir.mkdirs();
		}
		PDDocument document = PDDocument.load(file);
		List<PdfCoordinationsDTO> pdfCoordinationsDTOs = eKYCDAO.getInstance().getPdfCoordinations();
		for(PdfCoordinationsDTO DTO:pdfCoordinationsDTOs) {
			System.out.println(DTO.getColumn_name());
			String coordinates = DTO.getCoordinates();
			if(DTO.getIs_default() < 1) {
				String[] orgs = StringUtil.split(coordinates,eKYCConstant.COMMA_SEPERATOR);
				int xValue = Integer.parseInt(orgs[0]);
				int yValue = Integer.parseInt(orgs[1]);
				if(StringUtil.isNotNullOrEmpty(eKYCdto.getForPDFKeyValue().get(DTO.getColumn_name()))
						&& !StringUtil.isEqual(DTO.getColumn_name(),eKYCConstant.IMAGE)) {
					pdfInserter(document,DTO.getPage_number(),eKYCdto.getForPDFKeyValue().get(DTO.getColumn_name()),xValue,yValue);
				}
				else if(StringUtil.isEqual(DTO.getColumn_name(),eKYCConstant.IMAGE)){
					pdfimageInserter(document, DTO.getPage_number(),xValue,yValue,"13.jpg",application_id,finalSestinationFilePath+eKYCConstant.WINDOWS_FORMAT_SLASH,"");
//				if(eKYCdto.getForPDFKeyValue().get(columnsNames.get(i)) != null) {
//					pdfimageInserter(document, pageNumber,xValue,yValue, eKYCdto.getForPDFKeyValue().get(columnsNames.get(i)),application_id,finalSestinationFilePath+eKYCConstant.WINDOWS_FORMAT_SLASH);
				}
			}else {
				if(StringUtil.isEqual(DTO.getColumn_name(), "TICK")) {
					String[] orgs = StringUtil.split(coordinates,eKYCConstant.COMMA_SEPERATOR);
					int xValue = Integer.parseInt(orgs[0]);
					int yValue = Integer.parseInt(orgs[1]);
					pdfimageInserter(document,DTO.getPage_number(),xValue,yValue,"tick-1.png",application_id,eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH),"TICK");
				}else if(StringUtil.isStrContainsWithEqIgnoreCase(DTO.getColumn_name(),"DEFAULT")){
					String[] orgs = StringUtil.split(coordinates,eKYCConstant.COMMA_SEPERATOR);
					int xValue = Integer.parseInt(orgs[0]);
					int yValue = Integer.parseInt(orgs[1]);
					String columnValue = StringUtil.replace(DTO.getColumn_name(),"DEFAULT_","");
					pdfInserter(document,DTO.getPage_number(),columnValue,xValue,yValue);
				}else {
					 JSONParser parser = new JSONParser();
				     JSONObject json = (JSONObject) parser.parse(coordinates);
				     if(StringUtil.isNotNullOrEmpty(eKYCdto.getForPDFKeyValue().get(DTO.getColumn_name()))) {
				    	 String A = (String) json.get(eKYCdto.getForPDFKeyValue().get(DTO.getColumn_name()).toLowerCase());
				    	 String[] orgs1 = StringUtil.split(A,eKYCConstant.COMMA_SEPERATOR);
				    	 int xValue1 = Integer.parseInt(orgs1[0]);
				    	 int yValue1 = Integer.parseInt(orgs1[1]);
				    	 System.out.println(A); 
				    	 pdfimageInserter(document,DTO.getPage_number(),xValue1,yValue1,"tick-1.png",application_id,eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH),"TICK");
				     }
				}
			}
		}
		// Saving the document
		document.save(new File(finalSestinationFilePath+eKYCConstant.WINDOWS_FORMAT_SLASH+finalPDFName));
		System.out.println("pdf Generated");
		// Closing the document
		document.close();
		return eKYCConstant.SITE_URL_FILE+eKYCConstant.UPLOADS_DIR+application_id+eKYCConstant.WINDOWS_FORMAT_SLASH+finalPDFName;
	}
	
	public static void pdfInserter(PDDocument document ,int pageNumber,String insertValue,int xValue, int yValue) throws Exception {
		PDPage page = document.getPage(pageNumber);
		PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, false);
		// Begin the Content stream
		contentStream.beginText();
		// Or whatever font you want.
		// Setting the font to the Content stream
//		Font nameFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
//		PDFontFactory factory = new PDFontFactory()
		contentStream.setFont(PDType1Font.HELVETICA, 11);
		contentStream.setNonStrokingColor(0, 0, 0);
		contentStream.setCharacterSpacing(5);
		// Setting the position for the line
		contentStream.newLineAtOffset(xValue,yValue);
		String text = insertValue;
		// Adding text in the form of string
		contentStream.showText(text);
		// Ending the content stream
		contentStream.endText();
		System.out.println("Content added");
		// Closing the content stream
		contentStream.close();
	}
	private static void pdfimageInserter(PDDocument document, int pageNumber,int xValue, int yValue, String image, String application_id, String finalSestinationFilePath, String tick) throws IOException {
		String File_path = eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH);
		File file1 = new File(File_path+image);
		String resizeImageName =	image;
		if(StringUtil.isNotEqual(tick, "TICK")) {
			BufferedImage img = ImageIO.read(file1);
			BufferedImage scaledImg = Scalr.resize(img, 100, 100);
			resizeImageName =StringUtil.isImageReSizeExist(finalSestinationFilePath,application_id); 
			File file = new File(finalSestinationFilePath,resizeImageName);
			ImageIO.write(scaledImg, "JPG", file);
		}
		PDPage page = document.getPage(pageNumber);
		PDPageContentStream contentStream = new PDPageContentStream(document, page,AppendMode.APPEND, true);
		PDImageXObject pdImage = PDImageXObject.createFromFile(finalSestinationFilePath+resizeImageName, document);
		contentStream.drawImage(pdImage, xValue, yValue);
		contentStream.close();


	}
}
