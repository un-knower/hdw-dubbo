package com.hdw.upms.shiro;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import com.google.code.kaptcha.Producer;
//import com.google.code.kaptcha.util.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author TuMinglong
 * @Description Kaptcha验证码配置
 * @date 2018/7/9 14:06
 */
@Component
public class KaptchaConfig {

    @Bean
    public ServletRegistrationBean kaptchaServlet() {

        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new KaptchaServlet(), "/kaptcha.jpg");

        registrationBean.addInitParameter(Constants.KAPTCHA_SESSION_CONFIG_KEY,
                Constants.KAPTCHA_SESSION_KEY);
        //宽度
        registrationBean.addInitParameter(Constants.KAPTCHA_IMAGE_WIDTH, "125");
        //高度
        registrationBean.addInitParameter(Constants.KAPTCHA_IMAGE_HEIGHT, "60");
        //字体大小
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "50");

//        // 边框
//        registrationBean.addInitParameter(Constants.KAPTCHA_BORDER_THICKNESS,"1");

        //无边框
        registrationBean.addInitParameter(Constants.KAPTCHA_BORDER, "no");
        //文字颜色
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");
        //长度
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        //字符间距
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");

        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "宋体,楷体,微软雅黑");

        registrationBean.addInitParameter(Constants.KAPTCHA_BORDER_COLOR, "105,179,90");

        //可以设置很多属性,具体看com.google.code.kaptcha.Constants
//      kaptcha.border  是否有边框  默认为true  我们可以自己设置yes，no
//      kaptcha.border.color   边框颜色   默认为Color.BLACK
//      kaptcha.border.thickness  边框粗细度  默认为1
//      kaptcha.producer.impl   验证码生成器  默认为DefaultKaptcha
//      kaptcha.textproducer.impl   验证码文本生成器  默认为DefaultTextCreator
//      kaptcha.textproducer.char.string   验证码文本字符内容范围  默认为abcde2345678gfynmnpwx
//      kaptcha.textproducer.char.length   验证码文本字符长度  默认为5
//      kaptcha.textproducer.font.names    验证码文本字体样式  默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
//      kaptcha.textproducer.font.size   验证码文本字符大小  默认为40
//      kaptcha.textproducer.font.color  验证码文本字符颜色  默认为Color.BLACK
//      kaptcha.textproducer.char.space  验证码文本字符间距  默认为2
//      kaptcha.noise.impl    验证码噪点生成对象  默认为DefaultNoise
//      kaptcha.noise.color   验证码噪点颜色   默认为Color.BLACK
//      kaptcha.obscurificator.impl   验证码样式引擎  默认为WaterRipple
//      kaptcha.word.impl   验证码文本字符渲染   默认为DefaultWordRenderer
//      kaptcha.background.impl   验证码背景生成器   默认为DefaultBackground
//      kaptcha.background.clear.from   验证码背景颜色渐进   默认为Color.LIGHT_GRAY
//      kaptcha.background.clear.to   验证码背景颜色渐进   默认为Color.WHITE
//      kaptcha.image.width   验证码图片宽度  默认为200
//      kaptcha.image.height  验证码图片高度  默认为50
        return registrationBean;
    }


//    @Bean
//    public Producer KaptchaProducer() {
//        Properties kaptchaProperties = new Properties();
//        kaptchaProperties.put("kaptcha.border", "no");
//        kaptchaProperties.put("kaptcha.textproducer.char.length","4");
//        kaptchaProperties.put("kaptcha.textproducer.char.space","6");
//        kaptchaProperties.put("kaptcha.image.height","80");
//       // kaptchaProperties.put("kaptcha.image.width","125");
//        kaptchaProperties.put("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");
//        kaptchaProperties.put("kaptcha.textproducer.font.color","blue");
//        kaptchaProperties.put("kaptcha.textproducer.font.size","55");
//        kaptchaProperties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
//        //kaptchaProperties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.DefaultNoise");
//        kaptchaProperties.put("kaptcha.textproducer.char.string","acdefhkmnprtwxy2345678");
//
//        Config config = new Config(kaptchaProperties);
//        return config.getProducerImpl();
//    }
}
