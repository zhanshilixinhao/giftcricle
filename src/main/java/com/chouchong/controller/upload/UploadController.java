package com.chouchong.controller.upload;

import com.chouchong.utils.upload.UploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yy
 * @date 2017/12/15
 **/
@Controller
@RequestMapping("manage/upload")
public class UploadController {

    @PostMapping("image")
    public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws Exception{
        UploadUtils.upload(request, response,1);
    }
}
