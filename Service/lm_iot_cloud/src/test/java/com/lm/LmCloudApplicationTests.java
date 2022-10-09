package com.lm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(classes = LmCloudApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
class LmCloudApplicationTests {
    @Test
    public void contextLoads() {
        // 模拟标识符
        Map<String, String> identifierMap = new HashMap<>();
        identifierMap.put("tag1",null);
        identifierMap.put("tag2",null);
        identifierMap.put("tag3",null);
        List<String> identifierList = new ArrayList<>();
        identifierList.add("tag1");
        identifierList.add("tag2");
        identifierList.add("tag3");
        // 标识符 字符串
        String identifierStr= "";
        for (int i = 0; i <identifierList.size() ; i++) {
            if(i<identifierList.size()-1){
                identifierStr += "'" + identifierList.get(i) + "'," ;
            }else{
                identifierStr += "'" + identifierList.get(i) + "'" ;
            }
        }
        log.info(identifierStr);
    }

}
