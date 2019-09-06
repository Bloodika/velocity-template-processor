package com.ibm.hu.springbatch.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.springframework.batch.item.ItemProcessor;

import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

public class VelocityTemplateProcessor implements ItemProcessor {

    private VelocityEngine velocityEngine;
    private VelocityContext velocityContext;
    private Template template;

    public VelocityTemplateProcessor(String templatePath){
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
        velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,templatePath);
        velocityEngine.init();
        velocityContext = new VelocityContext();
    }

    public Object process(Object o) {
        setFields(((Map<String, Object>) o));
        StringWriter writer = new StringWriter();
        template.merge(velocityContext, writer);
        return writer.toString();
    }

    public void setTemplate(String templateName,String encoding) {
        template = velocityEngine.getTemplate(templateName,encoding);
    }

    public void setTemplate(String templateName){
        setTemplate(templateName,"UTF-8");
    }

    public void setFields(Map<String, Object> fields) {
        for(String key : fields.keySet()){
            velocityContext.put(key,fields.get(key));
        }
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }


    public VelocityContext getVelocityContext() {
        return velocityContext;
    }

    public Template getTemplate() {
        return template;
    }

}
