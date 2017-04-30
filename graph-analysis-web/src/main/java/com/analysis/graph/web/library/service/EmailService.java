package com.analysis.graph.web.library.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

/**
 * Created by cwc on 2017/4/24 0024.
 */
@Service
public class EmailService {
    private static final String EMAIL_HTML_TEMPLATE_NAME = "email.vm";

    private VelocityEngine velocityEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @PostConstruct
    private void init() {
        Properties props = new Properties();
        String fileDir = EmailService.class.getResource("/templates/emails").getPath();
        props.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, fileDir);
        props.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        props.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        props.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
        props.setProperty("log4j.logger.org.apache.velocity", "ERROR");
        velocityEngine = new VelocityEngine(props);
    }

    public void sendEmail(String emailConfig) {

        JSONObject emailJsonConfig = JSONObject.parseObject(emailConfig);
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "GBK");
            messageHelper.setTo(emailJsonConfig.getString("to"));
            messageHelper.setFrom(MimeUtility.encodeText(emailJsonConfig.getString("sign")) + "<" + emailJsonConfig.getString("from") + ">");
            messageHelper.setSubject(emailJsonConfig.getString("subject"));
            StringBuilderWriter content = getHtmlContent();
            messageHelper.setText(content.toString(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> loadTemplateParams() {
        return null;
    }

    private StringBuilderWriter getHtmlContent() {
        Map<String, Object> params = loadTemplateParams();

        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        VelocityContext velocityContext = new VelocityContext(params);
        velocityEngine.mergeTemplate(EMAIL_HTML_TEMPLATE_NAME, "UTF-8", velocityContext, stringBuilderWriter);
        return stringBuilderWriter;
    }

}
