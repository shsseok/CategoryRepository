package com.example.demo.config;

import com.example.demo.util.CategoryLoadService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component
@RequiredArgsConstructor
public class foodAndCategoryCsvLoad {
        private final CategoryLoadService categoryLoadService;
        @PostConstruct
        public void init() {
            try {
                // CSV 파일을 읽어 데이터베이스에 저장하는 메서드 호출
                categoryLoadService.saveDataFromCSV(new ClassPathResource("static/foodDataVer.csv").getFile());
            } catch (IOException e) {
                e.printStackTrace();
                log.info("fileNotFound={}","파일을 찾을수 없습니다.");
            }
        }


}
