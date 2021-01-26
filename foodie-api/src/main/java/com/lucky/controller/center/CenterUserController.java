package com.lucky.controller.center;

import com.lucky.bo.CenterUserBo;
import com.lucky.config.FileUpload;
import com.lucky.controller.BaseController;
import com.lucky.core.JsonResult;
import com.lucky.pojo.Users;
import com.lucky.service.CenterUserService;
import com.lucky.utils.CookieUtils;
import com.lucky.utils.DateUtil;
import com.lucky.utils.JsonUtils;
import com.lucky.vo.UsersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 用户信息操作相关接口
 * @author: OuYangXiaoGuang
 * @Date: 2021-01-26 13:55
 **/
@Api(value = "用户信息", tags = "用户信息操作相关接口")
@Slf4j
@RestController
@RequestMapping(value = "/userInfo")
public class CenterUserController extends BaseController {

    private JsonResult jsonResult = new JsonResult();

    //定义头像上传的地址
    //File.separator 不同的系统分隔符不一样
    private static final String IMAGE_USER_FACE_LOCATION = "D:" + File.separator + "IdeaProjects" + File.separator + "foodie-shop" + File.separator + "foodie" + File.separator + "faces";

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;


    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public JsonResult updateUserInfo(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "centerUserBo", value = "用户信息bo对象", required = true)
            @RequestBody @Valid CenterUserBo centerUserBo,
            BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) {
        log.info("用户提交信息{}", centerUserBo);
        //判断bindingResult中是否存在错误验证信息，有则直接return错误
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = getErrors(bindingResult);
            log.info("验证信息:{}", errors);
            return jsonResult.error(errors);
        }
        Users users = centerUserService.updateUserInfo(userId, centerUserBo);
        UsersVo usersVo = userToken(users);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVo), true);
        return jsonResult.success();
    }


    @ApiOperation(value = "上传用户头像", notes = "上传用户头像", httpMethod = "POST")
    @PostMapping("/uploadFace")
    public JsonResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
                    MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {

        //定义头像上传的地址
        String fileSpace = fileUpload.getImageUserFaceLocation();
        //在路径上为每一个用户增加一个userId,用于区别不同的用户
        String uploadPathPrefix = File.separator + userId;
        String newFileName = "";
        //开始文件上传
        if (file != null) {
            FileOutputStream os = null;
            try {
                //获取完整的文件名
                String originalFilename = file.getOriginalFilename();
                if (originalFilename != null) {
                    String[] s = originalFilename.split("\\.");
                    //获取文件后缀
                    String suffix = s[s.length - 1];
                    //限制图片格式
                    if (!suffix.equalsIgnoreCase("jpg") &&
                            !suffix.equalsIgnoreCase("png") &&
                            !suffix.equalsIgnoreCase("jpeg")
                    ) {
                        return jsonResult.error("图片格式不正确!");
                    }
                    //覆盖式
                    newFileName = "face-" + userId + "." + suffix;
                    //上传文件最终地址
                    String finalPath = fileSpace + uploadPathPrefix + File.separator + newFileName;
                    File outFile = new File(finalPath);
                    if (outFile.getParentFile() != null) {
                        //创建文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    //
                    os = new FileOutputStream(outFile);
                    InputStream is = file.getInputStream();
                    IOUtils.copy(is, os);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.flush();
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        } else {
            return jsonResult.error("文件不能为空!");
        }

        String serverImageUrl = fileUpload.getImageServerUrl() + userId + "/" + newFileName + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        //更新头像信息到数据库
        Users users = centerUserService.updateUserFace(userId, serverImageUrl);
        //Users resultUser = setNullProperty(users);
        // 增加令牌token，会整合进redis，分布式会话
        UsersVo usersVo = userToken(users);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVo), true);
        return jsonResult.success();

    }

    public Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(
                fieldError -> {
                    //获取错误的属性
                    String errorField = fieldError.getField();
                    //获取错误对应的信息
                    String defaultMessage = fieldError.getDefaultMessage();
                    errorMap.put(errorField, defaultMessage);
                }
        );
        return errorMap;
    }

    //把关键信息置为空
    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setBirthday(null);
        return userResult;
    }

}
