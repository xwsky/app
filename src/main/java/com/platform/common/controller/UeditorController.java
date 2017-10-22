package com.platform.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.platform.common.context.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.platform.component.service.file.BaseUploadService;
import com.platform.component.service.file.bean.FileBean;
import com.platform.core.utils.FileUtil;
import com.platform.core.utils.JsonUtil;


/**
 * ueditor控制器
 * @author xw
 *
 */
@Controller
@RequestMapping("/ueditor")
public class UeditorController{
	
	@Autowired
	private BaseUploadService baseUploadService;
	
	@RequestMapping("/getConfig")
	public void getConfig(HttpServletRequest request, HttpServletResponse response) throws Exception{		
		 request.setCharacterEncoding( "utf-8" );
		 response.setHeader("Content-Type" , "text/html");
		 //初始化返回的配置项
		 String json = 
				 "{\"snapscreenInsertAlign\":\"none\",\"videoPathFormat\":\"/ueditor/jsp/upload/video/{yyyy}{mm}{dd}/{time}{rand:6}\",\"videoFieldName\":\"upfile\",\"fileManagerActionName\":\"listfile\",\"fileUrlPrefix\":\"\",\"imageUrlPrefix\":\"\",\"imageAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\"],\"videoAllowFiles\":[\".flv\",\".swf\",\".mkv\",\".avi\",\".rm\",\".rmvb\",\".mpeg\",\".mpg\",\".ogg\",\".ogv\",\".mov\",\".wmv\",\".mp4\",\".webm\",\".mp3\",\".wav\",\".mid\"],\"filePathFormat\":\"/ueditor/jsp/upload/file/{yyyy}{mm}{dd}/{time}{rand:6}\",\"fileMaxSize\":51200000,\"fileManagerListPath\":\"/ueditor/jsp/upload/file/\",\"catcherUrlPrefix\":\"\",\"videoActionName\":\"uploadvideo\",\"scrawlInsertAlign\":\"none\",\"videoUrlPrefix\":\"\",\"imageManagerUrlPrefix\":\"\",\"scrawlUrlPrefix\":\"\",\"imageFieldName\":\"upfile\",\"fileManagerAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\",\".flv\",\".swf\",\".mkv\",\".avi\",\".rm\",\".rmvb\",\".mpeg\",\".mpg\",\".ogg\",\".ogv\",\".mov\",\".wmv\",\".mp4\",\".webm\",\".mp3\",\".wav\",\".mid\",\".rar\",\".zip\",\".tar\",\".gz\",\".7z\",\".bz2\",\".cab\",\".iso\",\".doc\",\".docx\",\".xls\",\".xlsx\",\".ppt\",\".pptx\",\".pdf\",\".txt\",\".md\",\".xml\"],\"imageMaxSize\":2048000,\"catcherPathFormat\":\"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\"imageManagerInsertAlign\":\"none\",\"scrawlFieldName\":\"upfile\",\"imagePathFormat\":\"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\"scrawlActionName\":\"uploadscrawl\",\"imageManagerActionName\":\"listimage\",\"imageActionName\":\"uploadimage\",\"imageManagerListSize\":20,\"imageManagerAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\"],\"fileAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\",\".flv\",\".swf\",\".mkv\",\".avi\",\".rm\",\".rmvb\",\".mpeg\",\".mpg\",\".ogg\",\".ogv\",\".mov\",\".wmv\",\".mp4\",\".webm\",\".mp3\",\".wav\",\".mid\",\".rar\",\".zip\",\".tar\",\".gz\",\".7z\",\".bz2\",\".cab\",\".iso\",\".doc\",\".docx\",\".xls\",\".xlsx\",\".ppt\",\".pptx\",\".pdf\",\".txt\",\".md\",\".xml\"],\"snapscreenActionName\":\"uploadimage\",\"fileFieldName\":\"upfile\",\"fileActionName\":\"uploadfile\",\"catcherActionName\":\"catchimage\",\"fileManagerListSize\":20,\"catcherAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\"],\"snapscreenPathFormat\":\"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\"imageCompressBorder\":1600,\"snapscreenUrlPrefix\":\"\",\"imageCompressEnable\":true,\"catcherLocalDomain\":[\"127.0.0.1\",\"localhost\",\"img.baidu.com\"],\"imageManagerListPath\":\"/ueditor/jsp/upload/image/\",\"imageInsertAlign\":\"none\",\"catcherMaxSize\":2048000,\"videoMaxSize\":102400000,\"fileManagerUrlPrefix\":\"\",\"scrawlPathFormat\":\"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\",\"scrawlMaxSize\":2048000,\"catcherFieldName\":\"source\"}";
		 @SuppressWarnings("unchecked")
		 //自定义参数
		 Map<String,Object> map = JsonUtil.toObject(json, Map.class);
		 
		 map.put("imageActionName", request.getContextPath() + "/ueditor/upload");    //图片上传路径		
		 map.put("imageMaxSize", 2048000); 		//图片大小限制2M
		 map.put("imageFieldName", "uploadfile");		//图片字段名
		 
		 
		 map.put("fileActionName", request.getContextPath() + "/ueditor/upload");    //附件上传路径		
		 map.put("fileMaxSize", 51200000); 		//附件大小限制50M
		 map.put("fileFieldName", "uploadfile");		//附件字段名
		 
		 /*
		 map.put("videoActionName", request.getContextPath() + "/ueditor/upload");    //视频上传路径		
		 map.put("videoMaxSize", 512000000); 		//视频大小限制500M
		 map.put("videoFieldName", "uploadfile");		//视频字段名
		 */
		 
		 response.getWriter().write(JsonUtil.toJson(map));
	}
	
	@RequestMapping("/upload")
	public void upload
		(HttpServletRequest request,HttpServletResponse response){
		
		MultipartFile file = 
				baseUploadService.getMultipartFile(request, "uploadfile");	
		
		String url = "";			
		String state = "SUCCESS";
		
		if(file != null){
			try{
				FileBean fileBean = baseUploadService.uploadFile(file, App.FILE_REALPATH, App.FILE_PATH, "ueditor");
				if(fileBean == null){
					state = "Upload failure";
				}else{
					url = fileBean.getUrlPath();
				}
			}catch(Exception ex){
				state = "Upload failure";
				ex.printStackTrace();			
			}
		}else{
			state = "Upload failure";
		}

	    String callback = request.getParameter("callback");

	    String result = 
	    		"{\"title\":\""+ FileUtil.getName(file.getOriginalFilename()) +"\", " +
	    		"\"original\": \""+ file.getOriginalFilename() +"\", " +
	    		"\"size\": "+ file.getSize() +", \"state\": \""+ state +"\", " +
	    		"\"type\": \""+ FileUtil.getFileExtName(file.getOriginalFilename()) +"\", " +
	    		"\"url\": \""+ url +"\"}";

	    result = result.replaceAll( "\\\\", "\\\\" );
	    
	    response.setCharacterEncoding("utf-8");
	    
	    try{
	    	if( callback == null ){
	    		response.getWriter().print( result );
	    	}else{
	    		response.getWriter().print("<script>"+ callback +"(" + result + ")</script>");
	    	}
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    }
	    
	}	
		
}

