package com.ruoyi.common.utils.oss;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.ruoyi.common.config.OssConfig;

/**
 * 阿里云OSS服务类
 *
 * @author ruoyi
 */
@Component
public class OssService
{
    private static final Logger log = LoggerFactory.getLogger(OssService.class);

    @Autowired
    private OssConfig ossConfig;

    /**
     * 获取OSS客户端实例
     */
    private OSS getOssClient()
    {
        return new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
    }

    /**
     * 检查OSS是否可用
     */
    public boolean isOssAvailable()
    {
        return ossConfig.isEnabled() && ossConfig.getEndpoint() != null && ossConfig.getBucketName() != null;
    }

    /**
     * 上传文件（字节数组）
     *
     * @param data 文件字节数组
     * @param objectKey 对象键（文件路径）
     * @param contentType 文件类型
     * @return 文件完整URL，失败返回null
     */
    public String uploadFile(byte[] data, String objectKey, String contentType)
    {
        if (!isOssAvailable())
        {
            log.warn("OSS未启用或配置不完整，请使用本地存储");
            return null;
        }
        OSS ossClient = null;
        try
        {
            ossClient = getOssClient();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(data.length);
            if (contentType != null)
            {
                metadata.setContentType(contentType);
            }
            ossClient.putObject(ossConfig.getBucketName(), objectKey, new java.io.ByteArrayInputStream(data), metadata);
            return getFileUrl(objectKey);
        }
        catch (Exception e)
        {
            log.error("上传文件到OSS失败: {}", e.getMessage(), e);
            return null;
        }
        finally
        {
            if (ossClient != null)
            {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 上传文件（输入流）
     *
     * @param inputStream 文件输入流
     * @param objectKey 对象键（文件路径）
     * @param contentType 文件类型
     * @return 文件完整URL，失败返回null
     */
    public String uploadFile(InputStream inputStream, String objectKey, String contentType)
    {
        if (!isOssAvailable())
        {
            log.warn("OSS未启用或配置不完整，请使用本地存储");
            return null;
        }
        OSS ossClient = null;
        try
        {
            ossClient = getOssClient();
            ObjectMetadata metadata = new ObjectMetadata();
            if (contentType != null)
            {
                metadata.setContentType(contentType);
            }
            PutObjectRequest putRequest = new PutObjectRequest(ossConfig.getBucketName(), objectKey, inputStream, metadata);
            ossClient.putObject(putRequest);
            return getFileUrl(objectKey);
        }
        catch (Exception e)
        {
            log.error("上传文件到OSS失败: {}", e.getMessage(), e);
            return null;
        }
        finally
        {
            if (ossClient != null)
            {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param objectKey 对象键
     * @return 是否删除成功
     */
    public boolean deleteFile(String objectKey)
    {
        if (!isOssAvailable())
        {
            log.warn("OSS未启用或配置不完整");
            return false;
        }
        OSS ossClient = null;
        try
        {
            ossClient = getOssClient();
            ossClient.deleteObject(ossConfig.getBucketName(), objectKey);
            return true;
        }
        catch (Exception e)
        {
            log.error("删除OSS文件失败: {}", e.getMessage(), e);
            return false;
        }
        finally
        {
            if (ossClient != null)
            {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 生成签名URL（用于私有bucket）
     *
     * @param objectKey 对象键
     * @param expiration 过期时间（毫秒）
     * @return 签名URL
     */
    public String generatePresignedUrl(String objectKey, long expiration)
    {
        if (!isOssAvailable())
        {
            log.warn("OSS未启用或配置不完整");
            return null;
        }
        OSS ossClient = null;
        try
        {
            ossClient = getOssClient();
            Date expirationDate = new Date(System.currentTimeMillis() + expiration);
            URL url = ossClient.generatePresignedUrl(ossConfig.getBucketName(), objectKey, expirationDate);
            return url.toString();
        }
        catch (Exception e)
        {
            log.error("生成签名URL失败: {}", e.getMessage(), e);
            return null;
        }
        finally
        {
            if (ossClient != null)
            {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param objectKey 对象键
     * @return 是否存在
     */
    public boolean exists(String objectKey)
    {
        if (!isOssAvailable())
        {
            return false;
        }
        OSS ossClient = null;
        try
        {
            ossClient = getOssClient();
            return ossClient.doesObjectExist(ossConfig.getBucketName(), objectKey);
        }
        catch (Exception e)
        {
            log.error("检查OSS文件是否存在失败: {}", e.getMessage(), e);
            return false;
        }
        finally
        {
            if (ossClient != null)
            {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 获取文件的完整URL
     *
     * @param objectKey 对象键
     * @return 完整URL
     */
    private String getFileUrl(String objectKey)
    {
        // 如果配置了CDN域名，使用CDN域名
        if (ossConfig.getCdnDomain() != null && !ossConfig.getCdnDomain().isEmpty())
        {
            return ossConfig.getCdnDomain().replaceAll("/$", "") + "/" + objectKey;
        }
        // 否则使用默认的OSS域名
        return "https://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint() + "/" + objectKey;
    }
}
