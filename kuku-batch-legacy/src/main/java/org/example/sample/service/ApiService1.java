package org.example.sample.service;

import org.example.sample.batch.domain.ApiInfo;
import org.example.sample.batch.domain.ApiResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService1 extends AbstractApiService {

    @Override
    protected ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo) {

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8081/product/1", apiInfo, String.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        ApiResponseVO apiResponseVO = ApiResponseVO.builder().status(statusCodeValue).message(responseEntity.getBody()).build();
        return apiResponseVO;
    }
}
