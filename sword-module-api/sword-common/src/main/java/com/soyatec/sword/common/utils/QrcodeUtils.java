package com.soyatec.sword.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.soyatec.sword.common.utils.file.FileUploadUtils;

public class QrcodeUtils {

	private static final int DEFAULT_WDITH = 350;
	private static final int DEFAULT_HEIGHT = 350;

	private QrcodeUtils() {
	}

	public static BufferedImage generateQrcode(String content) {
		if (content == null) {
			return null;
		}
		QRCodeWriter qrCodeWriter = new QRCodeWriter();

		try {
			BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, DEFAULT_WDITH, DEFAULT_HEIGHT);

			return MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (WriterException e) {
			return null;
		}
	}

//	public static String generate(String dir, String content) {
//		if (StringUtils.isEmpty(dir) || StringUtils.isEmpty(content)) {
//			return null;
//		}
//		try {
//			String fileName = IdWorker.getIdStr() + ".png";
//			File file = new File(dir, fileName);
//			if (!file.getParentFile().exists()) {
//				file.getParentFile().mkdirs();
//			}
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			QRCodeWriter qrCodeWriter = new QRCodeWriter();
//
//			BitMatrix matrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, DEFAULT_WDITH, DEFAULT_HEIGHT);
//
//			MatrixToImageWriter.writeToPath(matrix, "PNG", file.toPath());
//			return getPathFileName(dir, fileName);
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
	public static String generate(String content) {
		BufferedImage image = generateQrcode(content);
		if (image != null) {
			InputStream is = null;
			try {
				String fileName = "qrcode/" + IdWorker.getIdStr() + ".png";
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(image, "PNG", os);
				is = new ByteArrayInputStream(os.toByteArray());
				return FileUploadUtils.upload(fileName, is);
			} catch (Exception e) {
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return null;
	}
//
//	private static final String getPathFileName(String uploadDir, String fileName) throws IOException {
//		int dirLastIndex = XPlan30Config.getProfile().length() + 1;
//		String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
//		String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
//		return pathFileName;
//	}
//
//	public static void main(String[] args) {
//		System.out.println(generate(XPlan30Config.getQrcodePath(), "HELLLO"));
//	}
}
