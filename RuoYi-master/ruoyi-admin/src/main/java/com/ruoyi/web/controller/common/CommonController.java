package com.ruoyi.web.controller.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.oss.OssService;
import org.apache.commons.io.FilenameUtils;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/common")
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    /** 允许的图片格式 */
    private static final String[] IMAGE_EXTENSIONS = { "jpg", "jpeg", "png", "gif", "webp" };

    /** 允许的视频格式 */
    private static final String[] VIDEO_EXTENSIONS = { "mp4", "mov" };

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private OssService ossService;

    private static final String FILE_DELIMETER = ",";

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 尝试上传到OSS
            if (ossService.isOssAvailable())
            {
                return uploadToOss(file);
            }
            // 否则上传到本地
            return uploadToLocal(file);
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 图片上传（限制jpg/png/gif/webp，10MB）
     */
    @PostMapping("/upload/image")
    @ResponseBody
    public AjaxResult uploadImage(MultipartFile file) throws Exception
    {
        try
        {
            // 校验文件类型
            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);
            boolean isValidType = false;
            for (String ext : IMAGE_EXTENSIONS)
            {
                if (ext.equalsIgnoreCase(extension))
                {
                    isValidType = true;
                    break;
                }
            }
            if (!isValidType)
            {
                return AjaxResult.error("不支持的图片格式，仅允许: jpg, png, gif, webp");
            }
            // 校验文件大小（10MB）
            long maxSize = 10 * 1024 * 1024;
            if (file.getSize() > maxSize)
            {
                return AjaxResult.error("图片大小不能超过10MB");
            }
            // 尝试上传到OSS
            if (ossService.isOssAvailable())
            {
                return uploadToOss(file);
            }
            // 否则上传到本地
            return uploadToLocal(file);
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 视频上传（限制mp4/mov，500MB）
     */
    @PostMapping("/upload/video")
    @ResponseBody
    public AjaxResult uploadVideo(MultipartFile file) throws Exception
    {
        try
        {
            // 校验文件类型
            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);
            boolean isValidType = false;
            for (String ext : VIDEO_EXTENSIONS)
            {
                if (ext.equalsIgnoreCase(extension))
                {
                    isValidType = true;
                    break;
                }
            }
            if (!isValidType)
            {
                return AjaxResult.error("不支持的视频格式，仅允许: mp4, mov");
            }
            // 校验文件大小（500MB）
            long maxSize = 500L * 1024 * 1024;
            if (file.getSize() > maxSize)
            {
                return AjaxResult.error("视频大小不能超过500MB");
            }
            // 尝试上传到OSS
            if (ossService.isOssAvailable())
            {
                return uploadToOss(file);
            }
            // 否则上传到本地
            return uploadToLocal(file);
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 上传文件到OSS
     */
    private AjaxResult uploadToOss(MultipartFile file) throws Exception
    {
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        // 生成唯一文件名：年/月/日/UUID.扩展名
        String datePath = java.time.LocalDate.now().toString().replace("-", "/");
        String objectKey = "upload/" + datePath + "/" + UUID.randomUUID().toString().replace("-", "") + "." + extension;
        // 获取内容类型
        String contentType = file.getContentType();
        // 上传到OSS
        String url = ossService.uploadFile(file.getInputStream(), objectKey, contentType);
        if (url == null)
        {
            log.warn("OSS上传失败，回退到本地存储");
            return uploadToLocal(file);
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("url", url);
        ajax.put("fileName", objectKey);
        ajax.put("newFileName", FileUtils.getName(objectKey));
        ajax.put("originalFilename", originalFilename);
        ajax.put("size", file.getSize());
        ajax.put("storageType", "oss");
        return ajax;
    }

    /**
     * 上传文件到本地
     */
    private AjaxResult uploadToLocal(MultipartFile file) throws Exception
    {
        // 上传文件路径
        String filePath = RuoYiConfig.getUploadPath();
        // 上传并返回新文件名称
        String fileName = FileUploadUtils.upload(filePath, file);
        String url = serverConfig.getUrl() + fileName;
        AjaxResult ajax = AjaxResult.success();
        ajax.put("url", url);
        ajax.put("fileName", fileName);
        ajax.put("newFileName", FileUtils.getName(fileName));
        ajax.put("originalFilename", file.getOriginalFilename());
        ajax.put("size", file.getSize());
        ajax.put("storageType", "local");
        return ajax;
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    @ResponseBody
    public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files)
            {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = serverConfig.getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls", StringUtils.join(urls, FILE_DELIMETER));
            ajax.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
            ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
            ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            if (!FileUtils.checkAllowDownload(resource))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + FileUtils.stripPrefix(resource);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 视频播放器演示页面
     *
     * @param videoUrl 视频地址（可选）
     * @param coverImage 封面图（可选）
     * @return 视频播放器页面
     */
    @GetMapping("/video/player")
    public String videoPlayer(@RequestParam(required = false) String videoUrl,
                              @RequestParam(required = false) String coverImage,
                              ModelMap mmap)
    {
        if (StringUtils.isNotEmpty(videoUrl))
        {
            mmap.put("videoUrl", videoUrl);
        }
        if (StringUtils.isNotEmpty(coverImage))
        {
            mmap.put("coverImage", coverImage);
        }
        return "video/player";
    }
}
