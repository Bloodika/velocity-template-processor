package com.ibm.hu.spring.batch.velocity.test;

import com.ibm.hu.spring.batch.velocity.VelocityTemplateProcessor;
import org.apache.velocity.VelocityContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class VelocityTemplateProcessorTest {

    private VelocityTemplateProcessor vtp;
    private String templateName;
    private Map<String,Object> fields;
    private VelocityContext vc;

    @Before
    public void initVelocityTemplateProcessor(){
      vtp =  new VelocityTemplateProcessor("src/main/template");
      templateName = "v.vm";
      fields = new HashMap<>();
      fields.put("id",5);
      fields.put("folder_id","SRC100012");
      fields.put("requester_name","Me Me");
      vc = vtp.getVelocityContext();
      vtp.setFields(fields);
      vtp.setTemplate(templateName);
    }

    @Test
    public void testInit(){

        Assert.assertNotNull("It needs to be not null",vtp.getVelocityContext());
        Assert.assertNotNull("It needs to be not null",vtp.getVelocityEngine());
    }

    @Test
    public void testSetTemplate(){
        Assert.assertNotNull("Template needs to be initialized",vtp.getTemplate());
        Assert.assertEquals(templateName,vtp.getTemplate().getName());
        Assert.assertEquals("UTF-8",vtp.getTemplate().getEncoding());

        vtp.setTemplate(templateName,"UTF-16");
        Assert.assertEquals(templateName,vtp.getTemplate().getName());
        Assert.assertEquals("UTF-16",vtp.getTemplate().getEncoding());
    }

    @Test
    public void testSetFields(){


        Assert.assertTrue(vc.containsKey("id"));
        Assert.assertEquals(5,vc.get("id"));

        Assert.assertTrue(vc.containsKey("folder_id"));
        Assert.assertEquals("SRC100012",vc.get("folder_id"));

        Assert.assertTrue(vc.containsKey("requester_name"));
        Assert.assertEquals("Me Me",vc.get("requester_name"));

        Assert.assertFalse(vc.containsKey("folder_fk"));
    }

    @Test
    public void testWriter(){
        Object result = vtp.process(fields);
        Assert.assertEquals("5 SRC100012 Me Me",result);

    }

}
