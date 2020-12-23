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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.imgscalr.Scalr;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.codespine.data.eKYCDAO;
import com.codespine.dto.FileUploadDTO;
import com.codespine.dto.PdfCoordinationsDTO;
import com.codespine.dto.eKYCDTO;

public class FinalPDFGenerator {
	static String filePath = eKYCDAO.getInstance().getFileLocation(eKYCConstant.FILE_PATH);
	static String sourceFilePath = filePath + eKYCDAO.getInstance().getFileLocation(eKYCConstant.CONSTANT_PDF_NAME);
	static String destinationFilePath = filePath;

	public static void main(String[] args) throws Exception {
//		PDDocument pdDoc = PDDocument.load(new File("C:\\apache-tomcat-8.5.35\\webapps\\e_sign\\file\\uploads\\KYC_V6.pdf"));
//		int i = pdDoc.getNumberOfPages();
//		System.out.println(i);

	}

	/**
	 * Mrthod to construct pdf for ESIGN
	 * 
	 * @param eKYCdto
	 * @param folderName
	 * @return
	 * @throws Exception
	 */
	public static String pdfInserterRequiredValues(eKYCDTO eKYCdto, String folderName) throws Exception {
		File file = new File(sourceFilePath + eKYCConstant.PDF_FILE_EXTENSION);
		String application_id = eKYCdto.getForPDFKeyValue().get("application_id");
		if (StringUtil.isNullOrEmpty(folderName)) {
			long timeInmillsecods = System.currentTimeMillis();
			folderName = String.valueOf(timeInmillsecods);
		}
		String finalSestinationFilePath = destinationFilePath + eKYCConstant.WINDOWS_FORMAT_SLASH + application_id
				+ eKYCConstant.WINDOWS_FORMAT_SLASH + folderName;
		File dir = new File(finalSestinationFilePath);
		String finalPDFName = eKYCDAO.getInstance().getFileLocation(eKYCConstant.CONSTANT_PDF_NAME)
				+ eKYCConstant.PDF_FILE_EXTENSION;
		if (StringUtil.isNotNullOrEmpty(eKYCdto.getForPDFKeyValue().get("pan_card"))) {
			finalPDFName = eKYCdto.getForPDFKeyValue().get("pan_card") + eKYCConstant.PDF_FILE_EXTENSION;
		}
		if (!dir.exists()) {
			dir.mkdirs();
		}
		PDDocument document = PDDocument.load(file);
		PDDocumentInformation info = document.getDocumentInformation();
		info.setTitle(eKYCdto.getForPDFKeyValue().get("pan_card"));
		List<PdfCoordinationsDTO> pdfCoordinationsDTOs = eKYCDAO.getInstance().getPdfCoordinations();
		File tickFontFile = new File(filePath + "ARIALUNI.ttf");
		PDType0Font font = PDType0Font.load(document, tickFontFile);
		for (PdfCoordinationsDTO DTO : pdfCoordinationsDTOs) {
			String coordinates = DTO.getCoordinates();
			if (DTO.getIs_default() < 1) {
				String[] orgs = StringUtil.split(coordinates, eKYCConstant.COMMA_SEPERATOR);
				int xValue = Integer.parseInt(orgs[0]);
				int yValue = Integer.parseInt(orgs[1]);
				if (StringUtil.isNotNullOrEmpty(eKYCdto.getForPDFKeyValue().get(DTO.getColumn_name()))
						&& !StringUtil.isEqual(DTO.getColumn_name(), eKYCConstant.IMAGE)) {
					pdfTextInserter(DTO.getPage_number(), document,
							eKYCdto.getForPDFKeyValue().get(DTO.getColumn_name()), xValue, yValue,
							DTO.getIs_value_reduced(), DTO.getColumn_name());
				} else if (StringUtil.isEqual(DTO.getColumn_name(), eKYCConstant.IMAGE)) {
					String URL = eKYCDAO.getInstance().getDocumentLink(Integer.parseInt(application_id),
							eKYCConstant.EKYC_PHOTO);
					if (StringUtil.isNotNullOrEmpty(URL)) {
						String replacedURL = StringUtil.replace(URL, " ", "%20");
						pdfimageInserter(DTO.getPage_number(), document, xValue, yValue, replacedURL, application_id,
								finalSestinationFilePath + eKYCConstant.WINDOWS_FORMAT_SLASH);
//					}else {
//						pdfimageInserter(DTO.getPage_number(),document,xValue, yValue, "13.jpg", application_id,finalSestinationFilePath + eKYCConstant.WINDOWS_FORMAT_SLASH);
					}
				}
			} else {
				if (StringUtil.isEqual(DTO.getColumn_name(), "TICK")) {
					String[] orgs = StringUtil.split(coordinates, eKYCConstant.COMMA_SEPERATOR);
					int xValue = Integer.parseInt(orgs[0]);
					int yValue = Integer.parseInt(orgs[1]);
					pdfTickInserter(font, DTO.getPage_number(), document, xValue, yValue);
				} else if (StringUtil.isStrContainsWithEqIgnoreCase(DTO.getColumn_name(), "DEFAULT")) {
					String[] orgs = StringUtil.split(coordinates, eKYCConstant.COMMA_SEPERATOR);
					int xValue = Integer.parseInt(orgs[0]);
					int yValue = Integer.parseInt(orgs[1]);
					String columnValue = "";
					if (DTO.getIs_value_reduced() != 0 && DTO.getIs_value_reduced() > 0) {
						Map<String, String> annualIncomeMap = Utility.annualIncomeMap();
						columnValue = annualIncomeMap.get((eKYCdto.getForPDFKeyValue()
								.get((StringUtil.replace(DTO.getColumn_name(), "DEFAULT_", "")).toLowerCase()))
										.toLowerCase());
					} else {
						columnValue = StringUtil.replace(DTO.getColumn_name(), "DEFAULT_", "");
					}
					pdfTextInserter(DTO.getPage_number(), document, columnValue, xValue, yValue,
							DTO.getIs_value_reduced(), DTO.getColumn_name());
				} else {
					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(coordinates);
					if (StringUtil.isNotNullOrEmpty(eKYCdto.getForPDFKeyValue().get(DTO.getColumn_name()))) {
						String coordinate = (String) json
								.get(eKYCdto.getForPDFKeyValue().get(DTO.getColumn_name()).toLowerCase());
						if (StringUtil.isNotNullOrEmpty(coordinate)) {
							String[] orgs1 = StringUtil.split(coordinate, eKYCConstant.COMMA_SEPERATOR);
							int xValue1 = Integer.parseInt(orgs1[0]);
							int yValue1 = Integer.parseInt(orgs1[1]);
							pdfTickInserter(font, DTO.getPage_number(), document, xValue1, yValue1);
						}
					}
				}
			}
		}
		// attaching external required documents
		List<String> urls = getAttachedDocumentURLs(Integer.parseInt(application_id));
		if (urls != null) {
			int i = 1;
			for (String url : urls) {
				String replacedURL = StringUtil.replace(url, " ", "%20");
				if (StringUtil.isStrContainsWithEqIgnoreCase(url, ".pdf")) {
//					float pdfConstantHeight = 859.89f;
//					float pdfConstantWidth = 613.28f;
					InputStream inputStream = new URL(replacedURL).openStream();
					PDPageTree mergePD = document.getPages();
					PDDocument pddDocument2 = PDDocument.load(inputStream);
					PDPageTree mergePD1 = pddDocument2.getPages();
					int x = 16;
					if (mergePD1.getCount() > 1) {
						for (PDPage page : mergePD1) {
							mergePD.insertAfter(page, document.getPage(x));
							x++;
						}
					}else {
						PDPage page = mergePD1.get(0);
						mergePD.insertAfter(page, document.getPage(x));
						x++;
					}
//					pddDocument2.close();
//					inputStream.close();
				} else {
					InputStream in = new URL(replacedURL).openStream();
					BufferedImage bimg = ImageIO.read(in);
					PDPage page = new PDPage(new PDRectangle(612, 858));
					document.addPage(page);
					float ratio = 0f;
					if (bimg.getWidth() < bimg.getHeight()) {
						ratio = 562f / (float) (bimg.getHeight());
					} else {
						ratio = 562f / (float) (bimg.getWidth());
					}
					int width = (int) (bimg.getWidth() * ratio);
					int height = (int) (bimg.getHeight() * ratio);
					BufferedImage scaledImg = Scalr.resize(bimg, width, height);
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					String imagePath = "";
					if (StringUtil.isStrContainsWithEqIgnoreCase(url, "png")) {
						ImageIO.write(scaledImg, "png", os);
						imagePath = finalSestinationFilePath + eKYCConstant.WINDOWS_FORMAT_SLASH + application_id + i
								+ "-1.png";
					} else {
						ImageIO.write(scaledImg, "jpeg", os);
						imagePath = finalSestinationFilePath + eKYCConstant.WINDOWS_FORMAT_SLASH + application_id + i
								+ "-1.jpg";
					}
					InputStream is = new ByteArrayInputStream(os.toByteArray());
					int read = 0;
					System.out.println(imagePath);
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
					if (StringUtil.isStrContainsWithEqIgnoreCase(url, "png")) {
						File f = new File(imagePath);
						BufferedImage bimg1 = ImageIO.read(f);
						pdImage = LosslessFactory.createFromImage(document, bimg1);
					} else {
						pdImage = PDImageXObject.createFromFile(imagePath, document);
					}
					PDPageContentStream contentStream = new PDPageContentStream(document, page);
					contentStream.drawImage(pdImage, 25, (833 - height));
					contentStream.close();
					in.close();
				}
				i++;
			}
		}
		document.save(new File(finalSestinationFilePath + eKYCConstant.WINDOWS_FORMAT_SLASH + finalPDFName));
		System.out.println("pdf Generated");
		document.close();
		return application_id + eKYCConstant.WINDOWS_FORMAT_SLASH + folderName + eKYCConstant.WINDOWS_FORMAT_SLASH
				+ finalPDFName;
	}

	/**
	 * Method to insert text value in PDF
	 * 
	 * @param pageNumber
	 * @param document
	 * @param insertValue
	 * @param xValue
	 * @param yValue
	 * @param resizeRequired
	 * @param ColumnName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	public static void pdfTextInserter(int pageNumber, PDDocument document, String insertValue, int xValue, int yValue,
			int resizeRequired, String ColumnName) throws Exception {
		PDPage page = document.getPage(pageNumber);
		PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true);
		contentStream.beginText();
		contentStream.setNonStrokingColor(0, 0, 0);
		File f = new File(filePath + "MonospaceTypewriter.ttf");
		PDFont font1 = PDTrueTypeFont.loadTTF(document, f);
		if (resizeRequired > 0) {
			contentStream.setCharacterSpacing(0.4f);
			if (pageNumber == 2 && StringUtil.isEqual(ColumnName, "reduced_city")) {
//				contentStream.setFont(font1, 2);
				contentStream = changeInputTextSizesForCity(contentStream, font1, insertValue, pageNumber, ColumnName);
			} else {
				contentStream = changeInputTextSizes(contentStream, font1, insertValue, pageNumber, ColumnName);
			}
		} else {
			contentStream.setFont(font1, 8);
			contentStream.setCharacterSpacing(0.4f);
		}
		contentStream.newLineAtOffset(xValue, yValue);
		String text = insertValue;
		contentStream.showText(text);
		contentStream.endText();
		contentStream.close();
	}

	private static PDPageContentStream changeInputTextSizesForCity(PDPageContentStream contentStream, PDFont font1,
			String insertValue, int pageNumber, String columnName) throws IOException {
		if (pageNumber == 2) {
			if (!insertValue.isEmpty() && insertValue.length() > 6 && insertValue.length() < 16) {
				contentStream.setFont(font1, 7);
			} else if (!insertValue.isEmpty() && insertValue.length() > 16 && insertValue.length() < 26) {
				contentStream.setFont(font1, 6);
			} else if (!insertValue.isEmpty() && insertValue.length() > 26 && insertValue.length() < 36) {
				contentStream.setFont(font1, 5);
			} else if (!insertValue.isEmpty() && insertValue.length() > 36) {
				contentStream.setFont(font1, 4);
			} else {
				contentStream.setFont(font1, 8);
			}
		}
		return contentStream;
	}

	/**
	 * Method to insert images value in PDF
	 * 
	 * @param pageNumber
	 * @param document
	 * @param xValue
	 * @param yValue
	 * @param image
	 * @param application_id
	 * @param finalSestinationFilePath
	 * @return
	 * @throws Exception
	 */
	private static void pdfimageInserter(int pageNumber, PDDocument document, int xValue, int yValue, String image,
			String application_id, String finalSestinationFilePath) throws IOException {
		InputStream in = new URL(image).openStream();
		BufferedImage bimg = ImageIO.read(in);
		int height = bimg.getHeight(); // 310
		int width = bimg.getWidth(); // 500
		int scaledHeight = 100;
		int scaledWidth = 100;
		int changedXValue = 0;
		int changedYValue = 0;
		if (bimg.getWidth() <= 100) {
			scaledWidth = bimg.getWidth();
		}
		if (bimg.getHeight() <= 100) {
			scaledHeight = bimg.getHeight();
		}
		if (bimg.getWidth() > bimg.getHeight()) {
			if(bimg.getWidth() > 100) {
			int ratio = bimg.getWidth() / 100;
				scaledHeight = bimg.getHeight() / ratio;
				if(scaledHeight >= 100) {
					scaledHeight = 100;
				}
			}
		} else {
			if(bimg.getHeight() > 100) {
				int ratio = bimg.getHeight() / 100;
				scaledWidth = bimg.getWidth() / ratio;
				if(scaledWidth >= 100) {
					scaledHeight = 100;
				}
			}
		}
//		if (bimg.getWidth() <= 100) {
//			scaledWidth = bimg.getWidth();
//		}
//		if (bimg.getHeight() <= 100) {
//			scaledHeight = bimg.getHeight();
//		}
		if(height > width) {
			changedXValue = ((scaledHeight - scaledWidth) / 2);
		} else {
			changedYValue = ((scaledWidth - scaledHeight) / 2);
		}
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
		System.out.println(imagePath);
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
		contentStream.drawImage(pdImage, xValue + changedXValue, yValue + changedYValue);
		contentStream.close();
		in.close();

	}

	/**
	 * Method to insert TICK MARK in PDF
	 * 
	 * @param font
	 * @param pageNumber
	 * @param document
	 * @param xValue
	 * @param yValue
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private static void pdfTickInserter(PDType0Font font, int pageNumber, PDDocument document, int xValue, int yValue)
			throws IOException {
		PDPage page = document.getPage(pageNumber);
		PDPageContentStream stream = new PDPageContentStream(document, page, AppendMode.APPEND, true);
		String ascii4 = "✓";
		stream.beginText();
		stream.setFont(font, 12);
		stream.setNonStrokingColor(0, 0, 0);
		stream.moveTextPositionByAmount(xValue, yValue);
		stream.drawString(ascii4);
		stream.endText();
		stream.close();
	}

	/**
	 * Method to RE-Size value of text in pdf
	 * 
	 * @param contentStream
	 * @param font1
	 * @param insertValue
	 * @param pageNumber
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	private static PDPageContentStream changeInputTextSizes(PDPageContentStream contentStream, PDFont font1,
			String insertValue, int pageNumber, String columnName) throws Exception {
		if (pageNumber == 2) {
			if (!insertValue.isEmpty() && insertValue.length() > 70 && insertValue.length() < 80) {
				contentStream.setFont(font1, 7);
			} else if (!insertValue.isEmpty() && insertValue.length() > 80 && insertValue.length() < 90) {
				contentStream.setFont(font1, 6);
			} else if (!insertValue.isEmpty() && insertValue.length() > 90 && insertValue.length() < 110) {
				contentStream.setFont(font1, 5);
			} else if (!insertValue.isEmpty() && insertValue.length() > 110) {
				contentStream.setFont(font1, 4);
			} else {
				contentStream.setFont(font1, 8);
			}
		} else if (pageNumber == 3) {
			if (!insertValue.isEmpty() && insertValue.length() > 30 && insertValue.length() < 40) {
				contentStream.setFont(font1, 7);
			} else if (!insertValue.isEmpty() && insertValue.length() > 40 && insertValue.length() < 50) {
				contentStream.setFont(font1, 6);
			} else if (!insertValue.isEmpty() && insertValue.length() > 50 && insertValue.length() < 60) {
				contentStream.setFont(font1, 5);
			} else if (!insertValue.isEmpty() && insertValue.length() > 60 && insertValue.length() < 70) {
				contentStream.setFont(font1, 4);
			} else if (!insertValue.isEmpty() && insertValue.length() > 70 && insertValue.length() < 80) {
				contentStream.setFont(font1, 3);
			} else if (!insertValue.isEmpty() && insertValue.length() > 80) {
				contentStream.setFont(font1, 2);
			} else {
				contentStream.setFont(font1, 8);
			}
		} else if (pageNumber == 4) {
			if (!insertValue.isEmpty() && insertValue.length() > 50 && insertValue.length() < 60) {
				contentStream.setFont(font1, 7);
			} else if (!insertValue.isEmpty() && insertValue.length() > 60 && insertValue.length() < 70) {
				contentStream.setFont(font1, 6);
			} else if (!insertValue.isEmpty() && insertValue.length() > 70 && insertValue.length() < 80) {
				contentStream.setFont(font1, 5);
			} else if (!insertValue.isEmpty() && insertValue.length() > 80 && insertValue.length() < 90) {
				contentStream.setFont(font1, 4);
			} else if (!insertValue.isEmpty() && insertValue.length() > 90 && insertValue.length() < 110) {
				contentStream.setFont(font1, 3);
			} else if (!insertValue.isEmpty() && insertValue.length() > 110) {
				contentStream.setFont(font1, 2);
			} else {
				contentStream.setFont(font1, 8);
			}
		} else if (pageNumber == 7) {
			if (!insertValue.isEmpty() && insertValue.length() > 10 && insertValue.length() < 20) {
				contentStream.setFont(font1, 7);
			} else if (!insertValue.isEmpty() && insertValue.length() > 20 && insertValue.length() < 30) {
				contentStream.setFont(font1, 6);
			} else if (!insertValue.isEmpty() && insertValue.length() > 30 && insertValue.length() < 40) {
				contentStream.setFont(font1, 5);
			} else if (!insertValue.isEmpty() && insertValue.length() > 40 && insertValue.length() < 50) {
				contentStream.setFont(font1, 4);
			} else if (!insertValue.isEmpty() && insertValue.length() > 50 && insertValue.length() < 60) {
				contentStream.setFont(font1, 3);
			} else if (!insertValue.isEmpty() && insertValue.length() > 60) {
				contentStream.setFont(font1, 2);
			} else {
				contentStream.setFont(font1, 8);
			}
		} else if (pageNumber == 9) {
			if (!insertValue.isEmpty() && insertValue.length() > 50 && insertValue.length() < 60) {
				contentStream.setFont(font1, 7);
			} else if (!insertValue.isEmpty() && insertValue.length() > 60 && insertValue.length() < 70) {
				contentStream.setFont(font1, 6);
			} else if (!insertValue.isEmpty() && insertValue.length() > 70 && insertValue.length() < 80) {
				contentStream.setFont(font1, 5);
			} else if (!insertValue.isEmpty() && insertValue.length() > 80 && insertValue.length() < 90) {
				contentStream.setFont(font1, 4);
			} else if (!insertValue.isEmpty() && insertValue.length() > 90 && insertValue.length() < 110) {
				contentStream.setFont(font1, 5);
			} else if (!insertValue.isEmpty() && insertValue.length() > 110) {
				contentStream.setFont(font1, 4);
			} else {
				contentStream.setFont(font1, 8);
			}
		} else if (pageNumber == 12) {
			if (!insertValue.isEmpty() && insertValue.length() > 50 && insertValue.length() < 60) {
				contentStream.setFont(font1, 7);
			} else if (!insertValue.isEmpty() && insertValue.length() > 60 && insertValue.length() < 70) {
				contentStream.setFont(font1, 6);
			} else if (!insertValue.isEmpty() && insertValue.length() > 70 && insertValue.length() < 80) {
				contentStream.setFont(font1, 5);
			} else if (!insertValue.isEmpty() && insertValue.length() > 80 && insertValue.length() < 90) {
				contentStream.setFont(font1, 4);
			} else if (!insertValue.isEmpty() && insertValue.length() > 90 && insertValue.length() < 110) {
				contentStream.setFont(font1, 3);
			} else if (!insertValue.isEmpty() && insertValue.length() > 110) {
				contentStream.setFont(font1, 2);
			} else {
				contentStream.setFont(font1, 8);
			}
		} else if (pageNumber == 14) {
			if (!insertValue.isEmpty() && insertValue.length() > 28 && insertValue.length() < 38) {
				contentStream.setFont(font1, 7);
			} else if (!insertValue.isEmpty() && insertValue.length() > 38 && insertValue.length() < 48) {
				contentStream.setFont(font1, 6);
			} else if (!insertValue.isEmpty() && insertValue.length() > 48 && insertValue.length() < 58) {
				contentStream.setFont(font1, 5);
			} else if (!insertValue.isEmpty() && insertValue.length() > 58 && insertValue.length() < 68) {
				contentStream.setFont(font1, 4);
			} else if (!insertValue.isEmpty() && insertValue.length() > 68 && insertValue.length() < 78) {
				contentStream.setFont(font1, 3);
			} else if (!insertValue.isEmpty() && insertValue.length() > 78) {
				contentStream.setFont(font1, 2);
			} else {
				contentStream.setFont(font1, 8);
			}
		} else if (pageNumber == 16) {
			if (!insertValue.isEmpty() && insertValue.length() > 25 && insertValue.length() < 35) {
				contentStream.setFont(font1, 7);
			} else if (!insertValue.isEmpty() && insertValue.length() > 35 && insertValue.length() < 45) {
				contentStream.setFont(font1, 6);
			} else if (!insertValue.isEmpty() && insertValue.length() > 45 && insertValue.length() < 55) {
				contentStream.setFont(font1, 5);
			} else if (!insertValue.isEmpty() && insertValue.length() > 55 && insertValue.length() < 65) {
				contentStream.setFont(font1, 4);
			} else if (!insertValue.isEmpty() && insertValue.length() > 65 && insertValue.length() < 75) {
				contentStream.setFont(font1, 3);
			} else if (!insertValue.isEmpty() && insertValue.length() > 75) {
				contentStream.setFont(font1, 2);
			} else {
				contentStream.setFont(font1, 8);
			}
		} else {
			contentStream.setFont(font1, 8);
		}

		return contentStream;
	}

	/**
	 * Method to get url of attached documents
	 * 
	 * @param application_id
	 * @return
	 * @throws Exception
	 */
	private static List<String> getAttachedDocumentURLs(int application_id) {
		List<String> urls = null;
		List<FileUploadDTO> fileUploadDTOs = eKYCDAO.getInstance().getUploadedFile(application_id);
		for (FileUploadDTO fileUploadDTO : fileUploadDTOs) {
			if (StringUtil.isNotEqual(fileUploadDTO.getProofType(), eKYCConstant.EKYC_DOCUMENT)
					&& StringUtil.isNotEqual(fileUploadDTO.getProofType(), eKYCConstant.EKYC_PHOTO)
					&& StringUtil.isNotEqual(fileUploadDTO.getProofType(), eKYCConstant.SIGNED_EKYC_DOCUMENT)) {
				if (urls == null) {
					urls = new ArrayList<String>();
				}
				if (StringUtil.isNotNullOrEmpty(fileUploadDTO.getProof())) {
					urls.add(fileUploadDTO.getProof());
				}
			}
		}
		return urls;
	}
}
