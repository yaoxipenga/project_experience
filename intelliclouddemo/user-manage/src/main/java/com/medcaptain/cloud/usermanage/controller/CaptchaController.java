package com.medcaptain.cloud.usermanage.controller;

import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.annotation.Log;
import com.medcaptain.helper.HttpServletHelper;
import com.medcaptain.logging.ExceptionLog;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.utils.CaptchaUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * 验证码校验
 *
 * @author bingwen.ai
 */
@RestController
public class CaptchaController {
    private Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @Autowired
    RedisService redisService;

    /**
     * 获取新的验证码
     *
     * @param request  HTTP请求
     * @param response HTTP响应
     * @return 接口运行结果，验证码通过流写入到HTTP响应
     */
    @Log
    @AuthorizationFree
    @GetMapping(value = "/captcha")
    public RestResult getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            String clientID = HttpServletHelper.getClientID(request, response);
            if (StringUtils.isBlank(clientID)) {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
            Map<String, Object> map = CaptchaUtil.getCaptchaImage(100, 40, 5);
            String verifyCode = map.get("verifyCode").toString();
            if (redisService.saveCaptcha(clientID, verifyCode)) {
                BufferedImage verifyCodeImage = (BufferedImage) map.get("image");
                ImageIO.write(verifyCodeImage, "JPEG", response.getOutputStream());
                response.getOutputStream().flush();
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
            }
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "生成验证码");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 验证码是否与redis中的一致
     *
     * @param request HTTP请求
     * @return true = 一致 ; false = 不一致
     */
    boolean isVerifyCodeMatch(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        try {
            String clientID = HttpServletHelper.getClientIDFromCookie(request);
            if (StringUtils.isBlank(clientID)) {
                return false;
            }
            String verifyCode = request.getParameter("captcha");
            String verifyCodeInRedis = redisService.getCaptcha(clientID);
            if (StringUtils.isBlank(verifyCode) || StringUtils.isBlank(verifyCodeInRedis)) {
                return false;
            }
            return verifyCode.toLowerCase().equals(verifyCodeInRedis.toLowerCase());
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "校验验证码");
            logger.error(exceptionLog.toString());
            return true;
        }
    }
}
