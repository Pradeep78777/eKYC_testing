package com.codespine.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.imgscalr.Scalr;

import com.codespine.data.eKYCDAO;
import com.codespine.dto.PdfCoordinationsDTO;
import com.codespine.dto.eKYCDTO;

public class FinalPDFGenerator {
	static String sourceFilePath =  eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH)+eKYCDAO.getInstance().getFileLocation(eKYCConstant.CONSTANT_PDF_NAME);
	static String destinationFilePath =  eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH);
	public static void main(String[] args) throws Exception {
		File file = new File(sourceFilePath);
		PDDocument document = PDDocument.load(file);
		pdfInserter(document,2,"PRADEEP KUPPUSAMY",105,752);//name
		pdfInserter(document,2,"STONEAGE",105,736);//fathers name
		pdfInserter(document,2,"STONEAGE",105,721);//mothers name
		pdfInserter(document,2,"X",104,707);//gender male
		pdfInserter(document,2,"X",139,707);//gender Female
		pdfInserter(document,2,"X",246,707);//single
		pdfInserter(document,2,"X",285,707);//married
		pdfInserter(document,2,"STONEAGE",105,689);//DOB
		pdfInserter(document,2,"STONEAGE",105,674);//PAN
		pdfInserter(document,2,"STONEAGE",311,678);//PAN letter
		pdfInserter(document,2,"STONEAGE",105,659);//AAdhar
		pdfInserter(document,2,"X",104,643);//Status -- Resident
		pdfInserter(document,2,"X",187,643);//Status -- Non Resident
		pdfInserter(document,2,"X",251,643);//Status -- Resident
		pdfInserter(document,2,"X",104,627);//Status -- Nationality
		pdfInserter(document,2,"X",187,627);//Status -- Nationality other
		pdfInserter(document,2,"STONEAGE",268,627);//Status -- Nationality other letter
		pdfInserter(document,2,"X",104,611);//Status -- PAN
		pdfInserter(document,2,"X",104,594);//Status -- other proof
		pdfInserter(document,2,"STONEAGE",157,594);// -- other proof letter 
		pdfInserter(document,2,"STONEA",379,594);// -- other proof letter
		pdfimageInserter(document,2,490,600,"13.jpg","","");
		pdfInserter(document,2,"X",123,556);//address present
		pdfInserter(document,2,"X",226,556);//office address
		pdfInserter(document,2,"STONEAGE",104,538);//residence
		pdfInserter(document,2,"STONEAGE",104,526);//residence second line
		pdfInserter(document,2,"STONEAGE",104,514);//landmark
		pdfInserter(document,2,"STONEAGE",104,502);//city
		pdfInserter(document,2,"XXX",520,502);//pin mandatory
		pdfInserter(document,2,"STONEAGE",104,491);//state
		pdfInserter(document,2,"1235",462,490);//pin
		pdfInserter(document,2,"1235",546,490);//residence
		pdfInserter(document,2,"X",103,479);//country india
		pdfInserter(document,2,"X",138,479);//country other
		pdfInserter(document,2,"STONEAGE",179,479);//country other letter
		pdfInserter(document,2,"STONEAGE",138,466);//Specify the proof of address submitted
		pdfInserter(document,2,"STONEA",521,468);//Valid till
		pdfInserter(document,2,"STON",103,441);//Contact details Country Code
		pdfInserter(document,2,"STONE",167,441);//Contact details STD/Area code 
		pdfInserter(document,2,"STONEAGE",243,441);//Contact details Number
		pdfInserter(document,2,"STON",396,441);//Contact details Extn.
		pdfInserter(document,2,"STON",103,429);//Tel.(Res)
		pdfInserter(document,2,"STONE",167,429);//Tel.(Res) STD/Area code
		pdfInserter(document,2,"STONEAGE",243,429);//Tel.(Res) Number
		pdfInserter(document,2,"STON",103,417);//FAX
		pdfInserter(document,2,"STONE",167,417);//FAX STD/Area code
		pdfInserter(document,2,"STONEAGE",243,417);//FAX Number
		pdfInserter(document,2,"STON",396,417);//FAX Mobile Country code.
		pdfInserter(document,2,"STONEAGE",471,417);//FAX Mobile Number.
		pdfInserter(document,2,"STONEAGE",103,402);//E-mail ID (Mandatory)
		pdfInserter(document,2,"X",104,386);//PERMANENET ADDRESS TICK
		pdfInserter(document,2,"STONEAGE",104,372);//Permanent Address
		pdfInserter(document,2,"STONEAGE",104,360);//Permanent Address second line
		pdfInserter(document,2,"STONEAGE",104,347);//Permanent Address Landmark (Mandatory)
		pdfInserter(document,2,"STONEAGE",104,333);//Permanent Address City/Town/Village
		pdfInserter(document,2,"STONEA",457,333);//Permanent Address City/Town/Village pin
		pdfInserter(document,2,"STONEAGE",104,320);//Permanent Address state
		pdfInserter(document,2,"X",104,307);//Permanent Address Country India
		pdfInserter(document,2,"X",138,307);//Permanent Address Country Other
		pdfInserter(document,2,"STONEAGE",180,307);//Permanent Address Country Other text
		pdfInserter(document,2,"ST",104,295);//Permanent Address Tel.(Res)
		pdfInserter(document,2,"STONE",143,295);//Permanent Address STD
		pdfInserter(document,2,"STONEAGE",219,295);//Permanent Address NUMBER
		pdfInserter(document,2,"STONEAGE",407,295);//Permanent Address Mobile No.
		pdfInserter(document,2,"X",173,274);//Part c tick
		pdfInserter(document,2,"ST",208,236);//Part c Country Code
		pdfInserter(document,2,"STONEAGE",285,236);//Part c Country name
		pdfInserter(document,2,"STONEAGE",248,224);//Part c Tax Identification Number
		pdfInserter(document,2,"STONEAGE",106,210);//Part c DOB CITY
		pdfInserter(document,2,"ST",398,208);//Part c ISO 3166 Country Code of Birth*
		pdfInserter(document,2,"STONEAGE",475,208);//Part c ISO 3166 Country Name
		pdfInserter(document,2,"X",34,168);//Part D tick 1
		pdfInserter(document,2,"X",103,168);//Part D tick 2
		pdfInserter(document,2,"X",171,168);//Part D tick 3
		pdfInserter(document,2,"X",236,168);//Part D tick 4
		pdfInserter(document,2,"X",274,168);//Part D tick 5
		pdfInserter(document,2,"X",306,168);//Part D tick 6
		pdfInserter(document,2,"X",350,168);//Part D tick 7
		pdfInserter(document,2,"X",396,168);//Part D tick 8
		pdfInserter(document,2,"X",436,168);//Part D tick 9
		pdfInserter(document,2,"X",471,168);//Part D tick 10
		pdfInserter(document,2,"STONEA",528,172);//Part D tick name
		pdfInserter(document,2,"STONEA",150,101);//offical use Name of the Person
		pdfInserter(document,2,"STONEA",150,89);//offical use Signature of the Person
		pdfInserter(document,2,"STONEA",150,77);//offical use Designation & Emp.code
		pdfInserter(document,2,"STONEA",170,55);//offical use Name and Signature of Authorised Signatory
		pdfInserter(document,2,"STONE",345,60);//offical use SIGN BELOW DATE
		pdfInserter(document,2,"STONEAGE",450,115);//offical use SGINATURE OF CLIENT
		pdfInserter(document,2,"STONEA",442,90);//offical use CLIENT SIGN DATE
		pdfInserter(document,2,"STONEA",545,91);//offical use CLIENT SIGN PLace
		//page 3
		pdfInserter(document,3,"X",23,757);//tick
		pdfInserter(document,3,"X",23,731);//second tick
		pdfInserter(document,3,"STONEAGE",207,757);//1st Holder
		pdfInserter(document,3,"STONEAGE",364,757);//1st Holder TAX Reference Number
		pdfInserter(document,3,"STONEAGE",207,745);//2st Holder
		pdfInserter(document,3,"STONEAGE",364,745);//2st Holder TAX Reference Number
		pdfInserter(document,3,"STONEAGE",207,732);//3st Holder
		pdfInserter(document,3,"STONEAGE",364,732);//3st Holder TAX Reference Number
		pdfInserter(document,3,"STONEAGE",207,719);//Guardian
		pdfInserter(document,3,"STONEAGE",364,719);//Guardian TAX Reference Number
		pdfInserter(document,3,"X",64,672);//1st Holder 
		pdfInserter(document,3,"X",121,672);//1st Holder -1
		pdfInserter(document,3,"X",201,672);//1st Holder -2
		pdfInserter(document,3,"X",236,672);//1st Holder -3
		pdfInserter(document,3,"X",283,672);//1st Holder -4
		pdfInserter(document,3,"STONEAGE",360,675);//1st Holder country 1
		pdfInserter(document,3,"STONEAGE",494,673);//2st Holder country 2
		pdfInserter(document,3,"X",64,659);//2st Holder
		pdfInserter(document,3,"X",121,659);//2st Holder -1
		pdfInserter(document,3,"X",201,659);//2st Holder -2
		pdfInserter(document,3,"X",236,659);//2st Holder -3
		pdfInserter(document,3,"X",283,659);//2st Holder -4
		pdfInserter(document,3,"STONEAGE",360,663);//2st Holder country 1
		pdfInserter(document,3,"STONEAGE",494,661);//2st Holder country 2
		pdfInserter(document,3,"X",64,647);//3st Holder
		pdfInserter(document,3,"X",121,647);//3st Holder -1
		pdfInserter(document,3,"X",201,647);//3st Holder -2
		pdfInserter(document,3,"X",236,647);//3st Holder -3
		pdfInserter(document,3,"X",283,647);//3st Holder -4
		pdfInserter(document,3,"STONEAGE",360,652);//3st Holder country 1
		pdfInserter(document,3,"STONEAGE",494,650);//3st Holder country 2
		pdfInserter(document,3,"X",64,634);//4st Holder
		pdfInserter(document,3,"X",121,634);//4st Holder -1
		pdfInserter(document,3,"X",201,634);//4st Holder -2
		pdfInserter(document,3,"X",236,634);//4st Holder -3
		pdfInserter(document,3,"X",283,634);//4st Holder -4
		pdfInserter(document,3,"STONEAGE",360,639);//4st Holder country 1
		pdfInserter(document,3,"STONEAGE",494,637);//4st Holder country 2
		pdfInserter(document,3,"X",17,534);//FINANCIAL DETAIL 1st Holder TICK Below Rs.1 lac
		pdfInserter(document,3,"X",81,534);//FINANCIAL DETAIL 1st Holder TICK Below Rs.1 to 5 lac
		pdfInserter(document,3,"X",138,534);//FINANCIAL DETAIL 1st Holder TICK Below Rs. 5 to 10 lac
		pdfInserter(document,3,"X",17,516);//FINANCIAL DETAIL 1st Holder TICK Below Rs. 10 to 25 lac
		pdfInserter(document,3,"X",81,516);//FINANCIAL DETAIL 1st Holder TICK Below More than Rs. 25 lac
		pdfInserter(document,3,"X",210,534);//FINANCIAL DETAIL 2st Holder TICK Below Rs.1 lac
		pdfInserter(document,3,"X",273,534);//FINANCIAL DETAIL 2st Holder TICK Below Rs.1 to 5 lac
		pdfInserter(document,3,"X",329,534);//FINANCIAL DETAIL 2st Holder TICK Below Rs. 5 to 10 lac
		pdfInserter(document,3,"X",210,516);//FINANCIAL DETAIL 2st Holder TICK Below Rs. 10 to 25 lac
		pdfInserter(document,3,"X",273,516);//FINANCIAL DETAIL 2st Holder TICK Below More than Rs. 25 lac
		pdfInserter(document,3,"X",410,534);//FINANCIAL DETAIL 3st Holder TICK Below Rs.1 lac
		pdfInserter(document,3,"X",472,534);//FINANCIAL DETAIL 3st Holder TICK Below Rs.1 to 5 lac
		pdfInserter(document,3,"X",530,534);//FINANCIAL DETAIL 3st Holder TICK Below Rs. 5 to 10 lac
		pdfInserter(document,3,"X",410,516);//FINANCIAL DETAIL 3st Holder TICK Below Rs. 10 to 25 lac
		pdfInserter(document,3,"X",472,516);//FINANCIAL DETAIL 3st Holder TICK Below More than Rs. 25 lac
		pdfInserter(document,3,"STONEAGE",61,498);//FINANCIAL DETAIL 1st Holder income range
		pdfInserter(document,3,"STONEAGE",254,498);//FINANCIAL DETAIL 2st Holder income range
		pdfInserter(document,3,"STONEAGE",452,498);//FINANCIAL DETAIL 3st Holder income range
		pdfInserter(document,3,"STONEAGE",50,477);//FINANCIAL DETAIL 1st Holder date
		pdfInserter(document,3,"STONEAGE",243,477);//FINANCIAL DETAIL 2st Holder date
		pdfInserter(document,3,"STONEAGE",444,477);//FINANCIAL DETAIL 3st Holder date
		pdfInserter(document,3,"X",15,446);//FINANCIAL DETAIL 1st Holder Public Sector tick
		pdfInserter(document,3,"X",57,446);//FINANCIAL DETAIL 1st Holder Private Sector tick
		pdfInserter(document,3,"X",99,446);//FINANCIAL DETAIL 1st Holder Government services tick
		pdfInserter(document,3,"X",155,446);//FINANCIAL DETAIL 1st Holder Business tick
		pdfInserter(document,3,"X",15,437);//FINANCIAL DETAIL 1st Holder Professional tick
		pdfInserter(document,3,"X",57,437);//FINANCIAL DETAIL 1st Holder Agriculturist tick
		pdfInserter(document,3,"X",99,437);//FINANCIAL DETAIL 1st Holder Retired tick
		pdfInserter(document,3,"X",155,437);//FINANCIAL DETAIL 1st Holder House wife tick
		pdfInserter(document,3,"X",15,428);//FINANCIAL DETAIL 1st Holder Student tick
		pdfInserter(document,3,"X",57,428);//FINANCIAL DETAIL 1st Holder Others (please specify) tick
		pdfInserter(document,3,"STONEAGE",110,432);//FINANCIAL DETAIL 1st Holder Others (please specify) text
		pdfInserter(document,3,"X",209,446);//FINANCIAL DETAIL 2st Holder Public Sector tick
		pdfInserter(document,3,"X",251,446);//FINANCIAL DETAIL 2st Holder Private Sector tick
		pdfInserter(document,3,"X",293,446);//FINANCIAL DETAIL 2st Holder Government services tick
		pdfInserter(document,3,"X",349,446);//FINANCIAL DETAIL 2st Holder Business tick
		pdfInserter(document,3,"X",209,437);//FINANCIAL DETAIL 2st Holder Professional tick
		pdfInserter(document,3,"X",251,437);//FINANCIAL DETAIL 2st Holder Agriculturist tick
		pdfInserter(document,3,"X",293,437);//FINANCIAL DETAIL 2st Holder Retired tick
		pdfInserter(document,3,"X",349,437);//FINANCIAL DETAIL 2st Holder House wife tick
		pdfInserter(document,3,"X",209,428);//FINANCIAL DETAIL 2st Holder Student tick
		pdfInserter(document,3,"X",251,428);//FINANCIAL DETAIL 2st Holder Others (please specify) tick
		pdfInserter(document,3,"STONEAGE",304,432);//FINANCIAL DETAIL 2st Holder Others (please specify) text
		pdfInserter(document,3,"X",407,446);//FINANCIAL DETAIL 3st Holder Public Sector tick
		pdfInserter(document,3,"X",449,446);//FINANCIAL DETAIL 3st Holder Private Sector tick
		pdfInserter(document,3,"X",491,446);//FINANCIAL DETAIL 3st Holder Government services tick
		pdfInserter(document,3,"X",547,446);//FINANCIAL DETAIL 3st Holder Business tick
		pdfInserter(document,3,"X",407,437);//FINANCIAL DETAIL 3st Holder Professional tick
		pdfInserter(document,3,"X",449,437);//FINANCIAL DETAIL 3st Holder Agriculturist tick
		pdfInserter(document,3,"X",491,437);//FINANCIAL DETAIL 3st Holder Retired tick
		pdfInserter(document,3,"X",547,437);//FINANCIAL DETAIL 3st Holder House wife tick
		pdfInserter(document,3,"X",407,428);//FINANCIAL DETAIL 3st Holder Student tick
		pdfInserter(document,3,"X",449,428);//FINANCIAL DETAIL 3st Holder Others (please specify) tick
		pdfInserter(document,3,"STONEAGE",502,432);//FINANCIAL DETAIL 3st Holder Others (please specify) text
		pdfInserter(document,3,"STONEAGE",57,418);//FINANCIAL DETAIL 1st Holder Brief Details
		pdfInserter(document,3,"STONEAGE",251,418);//FINANCIAL DETAIL 2st Holder Brief Details
		pdfInserter(document,3,"STONEAGE",449,418);//FINANCIAL DETAIL 3st Holder Brief Details
		pdfInserter(document,3,"STONEAGE",449,418);//FINANCIAL DETAIL 3st Holder Brief Details
		pdfInserter(document,3,"X",70,405);//FINANCIAL DETAIL 1st Holder Politically Exposed Person (PEP) tick
		pdfInserter(document,3,"X",70,397);//FINANCIAL DETAIL 1st Holder Related to a politically Exposed Person (PEP) tick
		pdfInserter(document,3,"X",70,390);//FINANCIAL DETAIL 1st Holder Non of above both tick
		pdfInserter(document,3,"X",263,406);//FINANCIAL DETAIL 2st Holder Politically Exposed Person (PEP) tick
		pdfInserter(document,3,"X",263,397);//FINANCIAL DETAIL 2st Holder Related to a politically Exposed Person (PEP) tick
		pdfInserter(document,3,"X",462,405);//FINANCIAL DETAIL 3st Holder Politically Exposed Person (PEP) tick
		pdfInserter(document,3,"X",462,397);//FINANCIAL DETAIL 3st Holder Related to a politically Exposed Person (PEP) tick
		pdfInserter(document,3,"STONEAGE",77,333);//FINANCIAL DETAIL 1st Holder name
		pdfInserter(document,3,"STONEAGE",77,320);//FINANCIAL DETAIL 1st Holder place
		pdfInserter(document,3,"STONEAGE",216,320);//FINANCIAL DETAIL 1st Holder date
		pdfInserter(document,3,"STONEAGE",77,305);//FINANCIAL DETAIL 2st Holder name
		pdfInserter(document,3,"STONEAGE",77,292);//FINANCIAL DETAIL 3st Holder place
		pdfInserter(document,3,"STONEAGE",216,292);//FINANCIAL DETAIL 2st Holder date
		pdfInserter(document,3,"STONEAGE",77,278);//FINANCIAL DETAIL 3st Holder name
		pdfInserter(document,3,"STONEAGE",77,265);//FINANCIAL DETAIL 3st Holder place
		pdfInserter(document,3,"STONEAGE",216,265);//FINANCIAL DETAIL 3st Holder date
		pdfInserter(document,3,"STONEAGE",480,328);//FINANCIAL DETAIL 1st Holder sign
		pdfInserter(document,3,"STONEAGE",480,300);//FINANCIAL DETAIL 2st Holder sign
		pdfInserter(document,3,"STONEAGE",480,273);//FINANCIAL DETAIL 3st Holder sign
		// Saving the document
		document.save(new File(destinationFilePath));
		// Closing the document
		document.close();
	}
	public static void pdfInserterRequiredValues(eKYCDTO eKYCdto) throws Exception {
		File file = new File(sourceFilePath);
		String application_id = eKYCdto.getForPDFKeyValue().get("application_id");
		String finalSestinationFilePath = destinationFilePath+eKYCConstant.WINDOWS_FORMAT_SLASH+application_id;
		File dir = new File(finalSestinationFilePath);
		String finalPDFName = application_id +eKYCConstant.PDF_FILE_EXTENSION;
		if (!dir.exists()) {
			dir.mkdirs();
		}
		PDDocument document = PDDocument.load(file);
		HashMap<String,String> keyAndCoordinates = constructkeyAndItsCoordinates(eKYCDAO.getInstance().getPdfCoordinations());
		List<String> columnsNames = eKYCDAO.getInstance().getPdfTotalColumns();
		for(int i=0;i<columnsNames.size();i++) {
			String coordinates = keyAndCoordinates.get(columnsNames.get(i));
			String[] orgs = StringUtil.split(coordinates,eKYCConstant.COMMA_SEPERATOR);
			int pageNumber = Integer.parseInt(orgs[0]);
			int xValue = Integer.parseInt(orgs[1]);
			int yValue = Integer.parseInt(orgs[2]);
			String dataType = orgs[3]; 
			if(StringUtil.isEqual(dataType,eKYCConstant.STRING)) {
				if(StringUtil.isNotNullOrEmpty(eKYCdto.getForPDFKeyValue().get(columnsNames.get(i)))) {
					pdfInserter(document,pageNumber,eKYCdto.getForPDFKeyValue().get(columnsNames.get(i)),xValue,yValue);	
				}
			}else if(StringUtil.isEqual(dataType,eKYCConstant.IMAGE)) {
				pdfimageInserter(document, pageNumber,xValue,yValue,"13.jpg",application_id,finalSestinationFilePath+eKYCConstant.WINDOWS_FORMAT_SLASH);
//				if(eKYCdto.getForPDFKeyValue().get(columnsNames.get(i)) != null) {
//					pdfimageInserter(document, pageNumber,xValue,yValue, eKYCdto.getForPDFKeyValue().get(columnsNames.get(i)),application_id,finalSestinationFilePath+eKYCConstant.WINDOWS_FORMAT_SLASH);
//				}
			}
			else if(StringUtil.isEqual(dataType,eKYCConstant.TICK)) {
				if(StringUtil.isNotNullOrEmpty(eKYCdto.getForPDFKeyValue().get(columnsNames.get(i)))) {
					String tickcoordinates = keyAndCoordinates.get(eKYCdto.getForPDFKeyValue().get(columnsNames.get(i)).toLowerCase());
					String[] tickorgs = StringUtil.split(tickcoordinates,eKYCConstant.COMMA_SEPERATOR);
					int tickpageNumber = Integer.parseInt(tickorgs[0]);
					int tickxValue = Integer.parseInt(tickorgs[1]);
					int tickyValue = Integer.parseInt(tickorgs[2]);
					pdfInserter(document,tickpageNumber,"X",tickxValue,tickyValue);	
				}
				
			}
			
		}
		// Saving the document
		document.save(new File(finalSestinationFilePath+eKYCConstant.WINDOWS_FORMAT_SLASH+finalPDFName));
		System.out.println("pdf Generated");
		// Closing the document
		document.close();
	}
	
	private static HashMap<String, String> constructkeyAndItsCoordinates(List<PdfCoordinationsDTO> pdfCoordinationsDTOs) {
		HashMap<String,String> keyAndCoordinates = new HashMap<String,String>();
		if(pdfCoordinationsDTOs != null) {
			for(PdfCoordinationsDTO DTO:pdfCoordinationsDTOs) {
				keyAndCoordinates.put(DTO.getColumn_name(), DTO.getCoordinates());
			}
		}
		return keyAndCoordinates;
	}
	public static void pdfInserter(PDDocument document ,int pageNumber,String insertValue,int xValue, int yValue) throws Exception {
		PDPage page = document.getPage(pageNumber);
		PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, false);
		// Begin the Content stream
		contentStream.beginText();

		// Setting the font to the Content stream
		contentStream.setFont(PDType1Font.HELVETICA, 11);
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
	private static void pdfimageInserter(PDDocument document, int pageNumber,int xValue, int yValue, String image, String application_id, String finalSestinationFilePath) throws IOException {
		String File_path = eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH);
		File file1 = new File(File_path+image);
		BufferedImage img = ImageIO.read(file1);
		BufferedImage scaledImg = Scalr.resize(img, 100, 100);
		String resizeImageName =StringUtil.isImageReSizeExist(finalSestinationFilePath,application_id); 
		File file = new File(finalSestinationFilePath,resizeImageName);
		ImageIO.write(scaledImg, "JPG", file);
		PDPage page = document.getPage(pageNumber);
		PDPageContentStream contentStream = new PDPageContentStream(document, page,AppendMode.APPEND, true);
		PDImageXObject pdImage = PDImageXObject.createFromFile(finalSestinationFilePath+resizeImageName, document);
		contentStream.drawImage(pdImage, xValue, yValue);
		contentStream.close();


	}
}
