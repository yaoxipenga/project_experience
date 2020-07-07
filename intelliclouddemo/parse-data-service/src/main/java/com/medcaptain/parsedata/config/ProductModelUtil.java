package com.medcaptain.parsedata.config;


import com.alibaba.fastjson.JSON;
import com.medcaptain.parsedata.entity.ProductTemplate;
import com.medcaptain.parsedata.entity.Profile;
import com.medcaptain.utils.JSONUtil;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public class ProductModelUtil {
    public final static byte[] getJson(String productKey){
        try{
            ClassPathResource resource = new ClassPathResource("static/md66-model.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String data = "";
            String readData;
            while((readData = br.readLine())!=null){
                data = data+readData;
            }
            ProductTemplate productTemplate = JSON.parseObject(data, ProductTemplate.class);
            Profile profile = new Profile();
            profile.setProductKey(productKey);
            productTemplate.setProfile(profile);
            return JSON.toJSON(productTemplate).toString().getBytes();
        }catch (Exception e){
            return e.getMessage().getBytes();
        }

    }
}
