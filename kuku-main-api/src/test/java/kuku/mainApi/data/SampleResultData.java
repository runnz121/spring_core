package kuku.mainApi.data;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class SampleResultData {

    private Long schedulerReviewKey;
    private Long placeKey;
    private String placeName;
    private String contents;
    private String cityCode;
    private String googlePlaceId;
    private String createBy;
    private String updateBy;
    private Date createUtc;
    private Date updateUtc;
}
