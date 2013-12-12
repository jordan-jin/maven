/**
 * @author pporan
 * @date	2013.08.16 
 * @description FTP Upload Util
 */
package com.pporan.project.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import pporan.maven.framework.data.EData;


public class FTPUtil extends FTPVariable{
	private Logger logger = Logger.getLogger(FTPUtil.class);
	
	FTPClient ftpClient;
	
	public FTPUtil(){
		ftpClient = new FTPClient();
	}

	/**
	 * FTP Connect
	 */
	protected void connect(EData eMap){
		try{
			ftpClient.connect(eMap.getString("SERVER_IP"), eMap.getInt("SERVER_PORT"));
			int reply = 0;
			reply = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)){
				ftpClient.disconnect();
				logger.info("@@@@@@@@@@@@@@@@@@@@@ FTP Server Connect Reject @@@@@@@@@@@@@@@@@@@@@@");
			}
			else{
				logger.info("@@@@@@@@@@@@@@@@@@@@@ FTP Server Connect Success @@@@@@@@@@@@@@@@@@@@@@");
			}
		}
		catch (IOException ioe){
			if(ftpClient.isConnected()){
				try{
					ftpClient.disconnect();
				}
				catch(Exception e){
					//
				}
			}
			logger.info("@@@@@@@@@@@@@@@@@@@@@ FTP Connect Fail @@@@@@@@@@@@@@@@@@@@@@");
		}
	}
	
	/**
	 * FTP DisConnect
	 */
	public void disconnect(){
		try{
			ftpClient.disconnect();
		}
		catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	/**
	 * FTP Login
	 * @return true : success / false : fail
	 */
	public boolean login(EData eMap){
		try{
			this.connect(eMap);
			
			return ftpClient.login(eMap.getString("SERVER_ID"), eMap.getString("SERVER_PASSWD"));
		}
		catch (IOException ioe){
			
		}
		return false;
	}
	
	/**
	 * FTP Logout
	 * @return true : success / false : fail
	 */
	public boolean logout(){
		try{
			return ftpClient.logout();
		}
		catch (IOException ioe){
			
		}
		return false;
	}
	
	/**
	 * FTP Directory Change
	 */
	public void changeDirectory(String path){
		try{
			ftpClient.changeWorkingDirectory(path);
		}
		catch (IOException ioe){
			logger.info("@@@@@@@@@@@@@@@@@@@@@ Change Directory Fail @@@@@@@@@@@@@@@@@@@@@@");
		}
	}
	
	/**
	 * FTP Directory Make
	 */
	public void makeDirectory(String dir){
		try{
			ftpClient.makeDirectory(dir);
		}
		catch (IOException ioe){
			logger.info("@@@@@@@@@@@@@@@@@@@@@ Change Directory Fail @@@@@@@@@@@@@@@@@@@@@@");
		}
	}
	
	/**
	 * FTP File Read
	 */
	public InputStream readFile(String fileName){
		try{
			this.setFileType(2);
			return ftpClient.retrieveFileStream(fileName);
		}
		catch (IOException ioe){
			logger.info("@@@@@@@@@@@@@@@@@@@@@ File Read Fail @@@@@@@@@@@@@@@@@@@@@@");
		}
		return null;
	}
	
	/**
	 * FTP File Down
	 */
	public void downFile(String filePath, String fileName){
		try{
			// 다운전 디렉토리 생성
			File fPath = new File(filePath);
			fPath.mkdirs();
			
			File f = new File(filePath, fileName);
			
			this.changeDirectory(filePath);
			this.setFileType(2);
			
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
			
			boolean isSuccess = ftpClient.retrieveFile(fileName, bos);
			
			if(!isSuccess){
				logger.info("@@@@@@@@@@@@@@@@@@@@@ File Down Fail @@@@@@@@@@@@@@@@@@@@@@");
			}
		}
		catch (IOException ioe){
			logger.info("@@@@@@@@@@@@@@@@@@@@@ File Down Fail @@@@@@@@@@@@@@@@@@@@@@");
		}
	}
	
	/**
	 * FTP File Upload
	 */
	public void upFile(String bFile, String fileName){
		try{
			FileInputStream fis = new FileInputStream(bFile);
			boolean upSuccess = ftpClient.storeFile(fileName, fis);
			
			if(upSuccess){
				logger.info("@@@@@@@@@@@@@@@@@@@@@ FTP Upload Success @@@@@@@@@@@@@@@@@@@@@@");
			}
			else{
				logger.info("@@@@@@@@@@@@@@@@@@@@@ File Upload Fail @@@@@@@@@@@@@@@@@@@@@@");
			}
			fis.close();
		}
		catch (IOException ioe){
			logger.info("@@@@@@@@@@@@@@@@@@@@@ File Upload Fail @@@@@@@@@@@@@@@@@@@@@@");
		}
	}
	
	/**
	 * FTP File Type
	 * 0 : ASCII_FILE_TYPE
	 * 1 : EBCDIC_FILE_TYPE
	 * 2 : BINARY_FILE_TYPE
	 * 3 : IMAGE_FILE_TYPE
	 * 4 : IMAGE_FILE_TYPE
	 */
	public void setFileType(int fileType){
		try{
			int type = FTP.ASCII_FILE_TYPE;
			
			if(fileType == 1){
				type = FTP.EBCDIC_FILE_TYPE;
			}else if(fileType == 2){
				type = FTP.BINARY_FILE_TYPE;
			}else if(fileType == 3){
				type = FTP.LOCAL_FILE_TYPE;
			}
			
			ftpClient.setFileType(type);
		}
		catch (IOException ioe){
			logger.info("@@@@@@@@@@@@@@@@@@@@@ FTP File Type Fail @@@@@@@@@@@@@@@@@@@@@@");
		}
	}
	
	/**
	 * FTP File List in Directory
	 */
	public FTPFile[] list(){
		FTPFile[] files = null;
		try{
			files = this.ftpClient.listFiles();
			return files;
		}
		catch (IOException ioe){
			logger.info("@@@@@@@@@@@@@@@@@@@@@ FTP File List in Directory Fail @@@@@@@@@@@@@@@@@@@@@@");
		}
		return null;
	}

	
} 
