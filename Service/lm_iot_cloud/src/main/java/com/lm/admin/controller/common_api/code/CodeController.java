package com.lm.admin.controller.common_api.code;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.lm.admin.controller.common_api.CommonBaseController;
import com.lm.admin.utils.pwd.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.lm.common.redis.adminkey.RedisAndHeaderKey.REDIS_CODE_UUID_KEY;


/**
 * 验证码接口 管理员和普通用户共用
 * path : /api/common/***
 * @author Lm
 * @date: 2022/10/3 20:02
 */
@Slf4j
@RestController
public class CodeController extends CommonBaseController {
    @Autowired
    private DefaultKaptcha defaultKaptcha; // 验证码生成类
    @Autowired
    private StringRedisTemplate redisTemplate;  // 操作Redis
    /**
     * @description: 生成验证码接口
     * @path: /api/captcha
     * @author: Lm
     * @date: 2022/10/3 20:02
     * @param session
     * @return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @ResponseBody
    @RequestMapping("/captcha")
    public Map<String, Object> captcha(HttpSession session) throws IOException {
        /**
         * 前后端分离 登录验证码 方案
         * 后端生成图片 验证码字符串 uuid
         * uuid为key  验证码字符串为value
         * 传递bs64图片 和uuid给前端
         * 前端在登录的时候 传递 账号 密码 验证码 uuid
         * 通过uuid获取 验证码 验证
         */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //获取验证码 5898
        String text = defaultKaptcha.createText();
        log.info("1--------------->登录验证码：" + text);

        BufferedImage image = defaultKaptcha.createImage(text);
        ImageIO.write(image, "png", out);
        String base64bytes = Base64.encode(out.toByteArray());
        //该字符串传输至前端放入src即可显示图片，安卓可以去掉data:image/png;base64,
        String src = "data:image/png;base64," + base64bytes;
        // 生成一个key
        String db_code_uuid = UUID.randomUUID().toString();
        // 验证码信息
        Map<String, Object> map = new HashMap<>(2);
        // 这个验证码key
        map.put("codeuuid", db_code_uuid);
        // 这个验证码的图片地址
        map.put("img", src);
        // 把生成的验证码放入到session中 spring-session
        //session.setAttribute("code", text);// 自动放入到redis
        // 这里为什么要设置时间，因为如果不设置时间，验证生成很频繁，其实一直放在内存中其实没必要的事情，所有设置一个有效期，自动从redis内存中删除
        redisTemplate.opsForValue().set(REDIS_CODE_UUID_KEY + db_code_uuid, text,5, TimeUnit.MINUTES);//5分钟
        return map;
    }
}
