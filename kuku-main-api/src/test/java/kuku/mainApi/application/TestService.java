package kuku.mainApi.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    public enum ImgEnum  {
        GOOGLE,
        NAVER
        ;
    }

    public List<String> testPlaceImages(String imageId) {

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add(ImgEnum.GOOGLE.name(), imageId);
        System.out.println(multiValueMap);
        return null;
    }
}
