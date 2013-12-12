package pporan.maven.framework.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import pporan.maven.framework.data.EData;

public class FileUtil {
	
	private static Logger logger = Logger.getLogger(FileUtil.class);
	
	// 파일 업로드 위치   conf/file_upload.properties 파일에 세팅되어 있음
	private static final ResourceBundle FILE_RESOURCE_BUNDLE = ResourceBundle.getBundle("conf.file_upload");
	
	public static final String SUCCESS = "success";
	
	public static final String NOFILE = "nofile";
	
	public static final String FAIL = "fail";
	
	public static final String FILE_UPLOAD_PATH_PREFIX = FILE_RESOURCE_BUNDLE.getString("root_path") + FILE_RESOURCE_BUNDLE.getString("default_path");
	
	public static final String DOWNLOAD_URL_PREFIX = FILE_RESOURCE_BUNDLE.getString("download_url_prefix");
	
	/**
	 * 파일 업로드
	 * @param uploadFolder
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 * @see {@link #fileUpload(EData, MultipartFile)}
	 */
	public static EData fileUpload(String uploadFolder, MultipartFile uploadFile) throws Exception {
		EData returnMap = new EData();
		
		String file_real_path = "";
		// 타입에 따라 업로드 경로 받아옴
		if(StringUtil.nvl(uploadFolder).length() > 0){
			file_real_path = uploadFolder + "/" + StringUtil.nowTime("yyyyMMdd") + "/";
		}
		else{																		
			// 미지정시 기본 업로드 경로
			file_real_path = StringUtil.nowTime("yyyyMMdd")+"/";
		}
		
		// 파일 등록을 위한
		
		String originFileName = uploadFile.getOriginalFilename().trim();	// 사용자가 올리는 원본 파일명
		
		String ext		  = "";
		String upFileName = "";
		String finalFnm	  = "";
		
		// 업로드 파일이 존재할 시
		if(originFileName != "")
		{
			String filePath = FILE_UPLOAD_PATH_PREFIX + file_real_path;
			
			ext = originFileName.substring(originFileName.lastIndexOf("."));	// 파일 확장자
			
			upFileName		= Long.toString(System.currentTimeMillis()) + ext;	// 새로운 파일명 + 파일 확장자
			
			// 파일을 지정한 위치에 upload
			File f = new File(filePath);
			
			if(!f.exists())
			{
				f.mkdirs();		// 디렉토리 생성
			}
			
			changeChmodPermPerFolder(FILE_UPLOAD_PATH_PREFIX, file_real_path);
			
			// filePath + / + upFileName
			finalFnm = filePath  + upFileName;
			
			returnMap.put("file_size", uploadFile.getSize());
			returnMap.put("file_org_name", originFileName);
			returnMap.put("file_save_name", upFileName);
			returnMap.put("file_path", file_real_path);
			returnMap.put("FILE_YN", "Y");
			
			File upFile = new File(finalFnm);
					
			uploadFile.transferTo(upFile);
			
			changeChmodPerm(finalFnm);

		}
		else
		{
			returnMap.put("FILE_YN", "N");
		}
		return returnMap;
	}
	
	/**
	 * 폴더 읽기, 쓰기 권한 부여
	 * @param rootPath
	 * @param uploadPath
	 */
	private static void changeChmodPermPerFolder(String rootPath, String uploadPath){
		rootPath = StringUtil.nvl(rootPath);
		uploadPath = StringUtil.nvl(uploadPath);
		
		String pathSep = "/";
		try {
			if(rootPath.length() > 0 && uploadPath.length() > 0){
				
				if(rootPath.endsWith(pathSep)){
					rootPath = rootPath.substring(0, rootPath.length()-1);
				}
				
				if(logger.isDebugEnabled()){
					logger.debug("@>>>>>>>>>> file rootPath : " + rootPath);
				}
				
				String [] pathArr = uploadPath.split(pathSep);
				
				if(pathArr != null && pathArr.length > 0){
					
					File folder = null;
					String tmpUpPath = "";
					
					for(int i=0; i<pathArr.length; i++){
						tmpUpPath += pathSep + pathArr[i];
						folder = new File(rootPath + tmpUpPath);
						
						if(folder.exists()){
							
							changeChmodPerm(rootPath + tmpUpPath);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
	
	/**
	 * 해당 path에 폴더 or 파일 읽기, 쓰기, 권한 부여
	 * @param path
	 */
	public static void changeChmodPerm(String path){
		
		path = StringUtil.nvl(path);
		
		if(path.length() > 0){
			
			String cmd = "chmod 774 " + path; 
			
			try {
				if(logger.isDebugEnabled()){
					logger.debug("@>>>>>>>>>> file or folder permission cmd start..... cmd is : " + cmd);
				}
				
				Runtime rt = Runtime.getRuntime(); 
				Process p = rt.exec(cmd); 
				p.waitFor();
				
				if(logger.isDebugEnabled()){
					logger.debug("@>>>>>>>>>> file or folder permission cmd success. cmd is : " + cmd);
				}
			} catch (Exception e) {
				//logger.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 파일 업로드
	 * @param uploadFolder
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 * @see {@link #fileUpload(String, MultipartFile)}
	 */
	public static EData fileUpload(EData map, MultipartFile uploadFile) throws Exception {
		
		return fileUpload(StringUtil.nvl(map.get("upload_folder")), uploadFile);
	}
	
	/**
	 * 물리 파일 삭제
	 * @param filePath 파일 경로
	 * @return 성공 = true, 실패 = false
	 * @throws Exception
	 */
	public static boolean deleteFile(String filePath) {
		
		filePath = FILE_UPLOAD_PATH_PREFIX + filePath;
		
		logger.debug("@>> file_real_path : " + filePath);
		
		File delFile = new File(filePath);
		
		// 파일삭제
		if(delFile.exists()){
			delFile.delete();
			
			if(logger.isDebugEnabled()){
				logger.debug("@>> delete file path : [" + filePath + "]");
			}
			
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 물리파일 삭제
	 * @param delList 삭제할 파일경로 list
	 * @return 삭제 성공 카운트
	 */
	public static int deleteFileList(List<String> delList){
		int delCnt = 0;
		
		if(delList != null){
			for(String path : delList){
				
				if(deleteFile(path)){
					delCnt++;
				}
			}
		}
		
		return delCnt;
	}
	
	// 기본 파일 삭제
	public static int deleteFile(EData map) throws Exception {
		
		int result = 0;
		
		try {			
			// 경로 세팅
			String filePath = StringUtil.nvl(map.get("file_path")) + map.getString("filename");
			
			deleteFile(filePath);
			
			/*File delFile = new File(file_real_path);
			
			// 파일삭제
			if(delFile.exists())delFile.delete();*/
		
		} catch (Exception e) {
			result = -1;
		}
		
		return result;
	}	
	
	
	/**
	 * 파일 업로드(request로 넘어온 모든 파일 업로드)
	 * @param mhsr MultipartHttpServletRequest
	 * @param extParam upload_folder, file_limit_size 키로 각각 업로드 경로 <code>extParam.put("upload_folder", "upload");</code>, 
	 * 			업로드 파일 최대크기<code>extParam.put("file_limit_size", Long.valueOf(5000000));</code>를 넣어준다. 
	 * 			추가로 업로드 후 db에 insert할 meta정보들을 넣어준다.
	 * @return 파일 메타 정보의 list
	 * @throws FileSizeLimitExceededException 최대 파일 업로드 크기를 넘을 경우 
	 * @throws Exception 최대 파일 업로드 크기를 넘을 경우 Exception
	 * @author cmj
	 */
	public static List<EData> fileUpload(MultipartHttpServletRequest mhsr, Map extParam) throws FileSizeLimitExceededException, Exception{
		List<EData> list = new ArrayList<EData>();
		
		String uploadFolderName = StringUtil.nvl((String)extParam.get("upload_folder"));
		Long file_limit_size = (Long)extParam.get("file_limit_size");
		int file_order_cnt = Integer.parseInt(StringUtil.nvl(mhsr.getParameter("file_order_max"),"0"));
		
		// 파일 제한 크기 값이 없으면 property에 설정된 upload.default.limit.size 값을 넣어준다. 
		if(file_limit_size == null){
			file_limit_size = Long.valueOf(FILE_RESOURCE_BUNDLE.getString("upload.default.limit.size"));
		}
		
		Map<String, MultipartFile> fileMap = mhsr.getFileMap();
		
		Iterator<String> fileKey = fileMap.keySet().iterator();
		
		while(fileKey.hasNext()){
			MultipartFile multipartFile = fileMap.get(fileKey.next());
			boolean fileLimitSizeCheck = FileUtil.fileLimitSizeCheck(multipartFile, file_limit_size);
			
			if(!fileLimitSizeCheck){
				throw new FileSizeLimitExceededException("fileLimitSizeCheck check is fail.", multipartFile.getSize(), file_limit_size);
			}
		}
		
		/*boolean fileCheckResult = FileUtil.fileLimitSizeCheck(fileMap, file_limit_size);
		
		if(!fileCheckResult){
			throw new Exception("file size check fail. file limit size is : " + file_limit_size);
		}*/
		
		Iterator<String> fileNames = mhsr.getFileNames();
		
		//파일 meta 정보
		EData fileMeta = null;
		String fileName = null;
		MultipartFile file = null;
		while(fileNames.hasNext()){
			
			fileName = (String)fileNames.next();

			file = fileMap.get(fileName);
			
			if(file != null && file.getSize() > 0){
				fileMeta = FileUtil.fileUpload(uploadFolderName, fileMap.get(fileName));
				
				String fileIdx = "";
				//업로드파일일경우 순서 및 설명
				if(fileName.indexOf("filenm") > -1){
					file_order_cnt++;
					fileIdx = StringUtil.getDigit(fileName);
					fileMeta.put("file_order", file_order_cnt);
					fileMeta.put("file_alt", StringUtil.getSpecialCharacters(StringUtil.nvl(mhsr.getParameter("fileAlt"+fileIdx))));
					
				//썸네일이미지일경우	
				}else if(fileName.indexOf("thumFile") > -1){
					fileMeta.put("thum_alt", StringUtil.getSpecialCharacters(StringUtil.nvl(mhsr.getParameter("thumFileAlt"))));
				}
				
				//파일 html form input tag name
				fileMeta.put("file_formTag_name", fileName);
				
				fileMeta.putAll(extParam);
				
				list.add(fileMeta);
			}
		}
		
		return list;
	}
	
	
	/**
	 * 파일 크기 체크
	 * @param multipartFile file
	 * @param fileLimitSize 제한 크기
	 * @return 성공 true, 제한 크기 초과 false
	 * @author cmj
	 */
	public static boolean fileLimitSizeCheck(MultipartFile multipartFile, long fileLimitSize){
		
		boolean result = false;
		
		if(multipartFile != null){
			
			if(fileLimitSize > multipartFile.getSize()){
				result = true;
			}else{
				result = false;
			}
		}else{
			result =  false;
		}
		
		return result;
	}
	
	/**
	 * 파일 크기 체크
	 * @param fileMap map of file
	 * @param fileLimitSize 제한 크기
	 * @return 성공 true, 제한 크기 초과 false
	 * @see {@link #fileLimitSizeCheck(MultipartFile, long)}
	 * @author cmj
	 */
	public static boolean fileLimitSizeCheck(Map<String, MultipartFile> fileMap, long fileLimitSize){
		boolean result = true;
		
		if(fileMap != null && !fileMap.isEmpty()){
			
			Collection<MultipartFile> values = fileMap.values();
			
			for(MultipartFile file : values){
				result = fileLimitSizeCheck(file, fileLimitSize);
				
				if(logger.isDebugEnabled()){
					logger.debug("@>> fileResult : " + result + " , fileName : " + file.getName() + " , fileSize : " + file.getSize());
				}
				
				// 하나라도 실패 할 경우 false return
				if(!result){
					return false;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 파일 사이즈 체크
	 * @deprecated 다음의 메소드를 사용 추천함. {@link #fileLimitSizeCheck(Map, long)}
	 * @param map
	 * @param it
	 * @param multipartRequest
	 * @param fileLimitSize
	 * @return 성공 : {@link #SUCCESS}, 빈 파일일 경우 : {@link #NOFILE}, 크기 초과할 경우 : {@link #FAIL} 
	 * @throws IOException
	 */
	@Deprecated
	public static String fileLimitSizeCheck2(HashMap map, Iterator it, MultipartHttpServletRequest multipartRequest, long fileLimitSize) throws IOException{
		String returnStr = "";
		int sCnt = 0;
		int fCnt = 0;
		int nCnt = 0;
		int cnt = 0;
		while(it.hasNext()){
			cnt++;
			String key = (String)it.next();
			
			if(multipartRequest.getFile(key).getSize() > 0 && fileLimitSize > multipartRequest.getFile(key).getSize()){
				sCnt++;
			}else if(multipartRequest.getFile(key).getSize() == 0){
				nCnt++;
			}else{
				fCnt++;
			}
		}
		
		if(cnt == sCnt){
			returnStr  = SUCCESS;
		}else if(nCnt > 0){
			returnStr = NOFILE;
		}else if(fCnt > 0){
			returnStr = FAIL;
		}
		
		return returnStr;
	}
	
	public static String fileLimitSizeCheck3(MultipartFile file, long fileLimitSize) throws IOException{
		
		String returnStr = "fail";
		
		if(file.getSize() > 0 && fileLimitSize > file.getSize()){
			returnStr  = "success";
		}else if(file.getSize() == 0 && file.getOriginalFilename().equals("")){
			returnStr = "nofile";
		}
		
		return returnStr;
	}
	
	/*public static HashMap fileUpload(HashMap map, MultipartFile uploadFile, String file_type) throws IOException {
			HashMap returnMap = new HashMap();
			returnMap.put("FILE_PATH", FILE_RESOURCE_BUNDLE.getString(file_type));
			
			// 파일 등록을 위한
			String file_date = DateUtil.nowTime("yyyyMMdd");
			
			String originFileName = uploadFile.getOriginalFilename().trim();	// 사용자가 올리는 원본 파일명
			
			String ext		  = "";
			String upFileName = "";
			String finalFnm	  = "";
			
			// 업로드 파일이 존재할 시
			if(originFileName != "")
			{
				ext = originFileName.substring(originFileName.lastIndexOf("."));	// 파일 확장자
				
				upFileName		= Long.toString(System.currentTimeMillis()) + ext;	// 새로운 파일명 + 파일 확장자
				String filePath = returnMap.get("FILE_PATH").toString() + file_date;
				
				logger.debug("@@@@@@@@@@@@@@@  filePath : " + filePath);
				
				// 확장자 체크
				// 파일을 지정한 위치에 upload
				File f = new File(filePath);
				logger.debug("@@@@@@@@@@@  f.length() == " + f.length());
				
				if(!f.exists())
				{
					f.mkdirs();		// 디렉토리 생성
				}
				
				// filePath + / + upFileName
				finalFnm = filePath  + File.separator + upFileName;
				
				returnMap.put("OLD_NAME", originFileName);
				returnMap.put("FILE_NAME", upFileName);
				returnMap.put("PATH_URL", filePath);
				returnMap.put("FILE_YN", "Y");
				
				uploadFile.transferTo(new File(finalFnm));
			}
			else
			{
				returnMap.put("FILE_YN", "N");
			}
			return returnMap;
		}*/

	/**
	 * 지정된 썸네일 사이즈가 있을경우
	 * @param map
	 * @param uploadFile
	 * @return
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	/*public static HashMap thumFileUpload(HashMap map, MultipartFile uploadFile) throws IOException, InterruptedException {
		HashMap returnMap = new HashMap();
		returnMap.put("FILE_PATH", FILE_RESOURCE_BUNDLE.getString(map.get("BOARD_ID").toString()));
		
		// 파일 등록을 위한
		String file_date = DateUtil.nowTime("yyyyMMdd");
		
		String originFileName = uploadFile.getOriginalFilename().trim();	// 사용자가 올리는 원본 파일명
		
		String ext		  = "";
		String upFileName = "";
		String finalFnm	  = "";
		
		FileOutputStream fos = null;
		
		try {
			// 업로드 파일이 존재할 시
			if(originFileName != "")
			{
				ext = originFileName.substring(originFileName.lastIndexOf("."));	// 파일 확장자
				
				upFileName		= Long.toString(System.currentTimeMillis()) + ext;	// 새로운 파일명 + 파일 확장자
				String filePath = returnMap.get("FILE_PATH").toString() + file_date;
				
				logger.debug("@@@@@@@@@@@@@@@  filePath : " + filePath);
				
				// 확장자 체크
				// 파일을 지정한 위치에 upload
				File f = new File(filePath);
				logger.debug("@@@@@@@@@@@  f.length() == " + f.length());
				
				if(!f.exists())
				{
					f.mkdirs();		// 디렉토리 생성
				}
				
				// filePath + / + upFileName
				finalFnm = filePath  + File.separator + upFileName;
				
				int widthSize =  Integer.parseInt(ResourceBundle.getBundle("com.etribe.common.conf.upload").getString("gal_width"));
				int heightSize =  Integer.parseInt(ResourceBundle.getBundle("com.etribe.common.conf.upload").getString("gal_height"));
				
				
				// 원본 썸네일의 path 값을 받아서 해당 위치로 업로드 
				Image imgSource = new ImageIcon(finalFnm).getImage(); 
				int newW = widthSize; 
				int newH = widthSize;
				
				Image imgTarget = imgSource.getScaledInstance(newW, newH, Image.SCALE_SMOOTH); 
				int pixels[] = new int[newW * newH]; 
				
				PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, newW, newH, pixels, 0, newW); 
				pg.grabPixels(); 
				
				BufferedImage bi = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB); 
				bi.setRGB(0, 0, newW, newH, pixels, 0, newW); 
				
				fos = new FileOutputStream(finalFnm); 
				JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(fos); 
				JPEGEncodeParam jep = jpeg.getDefaultJPEGEncodeParam(bi); 
				
				jep.setQuality(1, false); 
				jpeg.encode(bi, jep); 
				
				returnMap.put("ORIGIN_FILE", originFileName);
				returnMap.put("UP_FILE", upFileName);
				returnMap.put("FILE_PATH", filePath);
				returnMap.put("FILE_YN", "Y");
				
				uploadFile.transferTo(new File(finalFnm));
			}
			else
			{
				returnMap.put("FILE_YN", "N");
			}
		} catch (NumberFormatException e) {
			throw e;
		} catch (ImageFormatException e) {
			throw e;
		} catch (IllegalStateException e) {
			throw e;
		} finally{
			if(fos != null){
				fos.close();
			}
		}
		return returnMap;
	}*/
	
	
	/**
	 * 썸네일 사이즈가 비율로 저장할 경우
	 * @param map
	 * @param uploadFile
	 * @return
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	/*public static HashMap thumFileUpload2(HashMap map, MultipartFile uploadFile) throws IOException, InterruptedException {
		HashMap returnMap = new HashMap();
		returnMap.put("FILE_PATH", FILE_RESOURCE_BUNDLE.getString(map.get("BOARD_ID").toString()));
		
		// 파일 등록을 위한
		String file_date = DateUtil.nowTime("yyyyMMdd");
		
		String originFileName = uploadFile.getOriginalFilename().trim();	// 사용자가 올리는 원본 파일명
		
		String ext		  = "";
		String upFileName = "";
		String finalFnm	  = "";
		
		FileOutputStream fos = null;
		
		try {
			// 업로드 파일이 존재할 시
			if(originFileName != "")
			{
				ext = originFileName.substring(originFileName.lastIndexOf("."));	// 파일 확장자
				
				upFileName		= Long.toString(System.currentTimeMillis()) + ext;	// 새로운 파일명 + 파일 확장자
				String filePath = returnMap.get("FILE_PATH").toString() + file_date;
				
				logger.debug("@@@@@@@@@@@@@@@  filePath : " + filePath);
				
				// 확장자 체크
				// 파일을 지정한 위치에 upload
				File f = new File(filePath);
				logger.debug("@@@@@@@@@@@  f.length() == " + f.length());
				
				if(!f.exists())
				{
					f.mkdirs();		// 디렉토리 생성
				}
				
				// filePath + / + upFileName
				finalFnm = filePath  + File.separator + upFileName;
				
				int targetSize =  Integer.parseInt(ResourceBundle.getBundle("com.etribe.common.conf.upload").getString("gal_width"));
				
				// 원본 썸네일의 path 값을 받아서 해당 위치로 업로드 
				Image imgSource = new ImageIcon(finalFnm).getImage(); 
				int oldW = imgSource.getWidth(null); 
			    int oldH = imgSource.getHeight(null); 
			    int newW = 0; 
			    int newH = 0;
				
			    //1. 가로가 긴 사진이면 targetSize를 가로에 맞춤
				if(oldW>oldH){
					newW = targetSize; 
					newH = (targetSize * oldH) / oldW; 
				}
				//2. 세로가 긴 사진이면 targetSize를 가로에 맞춤
				else if(oldW<oldH){
					newH = targetSize; 
					newW = (targetSize * oldW) / oldH; 
				}
				//3. 세로, 세로가 같다면
				else if(oldW==oldH){
					newH = targetSize; 
					newW = targetSize; 
				}
				//기타의 경우 - 위의 3가지 경우에 모두 해당되지만, 예외처리를 위해 처리 
				else{
					newW = targetSize; 
					newH = (targetSize * oldH) / oldW; 
				}
			    
				Image imgTarget = imgSource.getScaledInstance(newW, newH, Image.SCALE_SMOOTH); 
				int pixels[] = new int[newW * newH]; 
				
				PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, newW, newH, pixels, 0, newW); 
				pg.grabPixels(); 
				
				BufferedImage bi = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB); 
				bi.setRGB(0, 0, newW, newH, pixels, 0, newW); 
				
				fos = new FileOutputStream(finalFnm); 
				JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(fos); 
				JPEGEncodeParam jep = jpeg.getDefaultJPEGEncodeParam(bi); 
				
				jep.setQuality(1, false); 
				jpeg.encode(bi, jep); 
				
				returnMap.put("ORIGIN_FILE", originFileName);
				returnMap.put("UP_FILE", upFileName);
				returnMap.put("FILE_PATH", filePath);
				returnMap.put("FILE_YN", "Y");
				
				uploadFile.transferTo(new File(finalFnm));
			}
			else
			{
				returnMap.put("FILE_YN", "N");
			}
		} catch (NumberFormatException e) {
			throw e;
		} catch (ImageFormatException e) {
			throw e;
		} catch (IllegalStateException e) {
			throw e;
		} finally{
			if(fos != null){
				fos.close();
			}
		}
		
		return returnMap;
	}
	
	public static String create_xml_name(HashMap map) throws Exception{
		String file_date = DateUtil.nowTime("yyyyMMdd");
		
		File f = new File(FILE_RESOURCE_BUNDLE.getString("xml_path"));
		
		if(!f.exists())
		{
			f.mkdirs();		// 디렉토리 생성
		}
		
		
		String xml_name = FILE_RESOURCE_BUNDLE.getString("xml_path") + map.get("EVENT_SEQ").toString() +"_"+ file_date + ".xml"; 
		
		return xml_name;
	}
	
	public static String create_main_xml_name() throws Exception{
		
		File f = new File(FILE_RESOURCE_BUNDLE.getString("xml_path"));
		
		if(!f.exists())
		{
			f.mkdirs();		// 디렉토리 생성
		}
		
		String xml_name = FILE_RESOURCE_BUNDLE.getString("xml_path") +"main_view.xml"; 
		
		return xml_name;
	}*/
	
}
