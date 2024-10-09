package kuku.mainApi.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@ToString
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SampleResponseData {

    private Long schedulerReviewKey;
    private String placeName;
    private String cityCode;
    private String contents;
    private String googlePlaceId;
    private String thumbNailUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Date createUtc;

    public static SampleResponseData of(SampleResultData review,
                                        List<String> placeImageUrls) {

        return SampleResponseData.builder()
                .schedulerReviewKey(review.getSchedulerReviewKey())
                .cityCode(review.getCityCode())
                .googlePlaceId(review.getGooglePlaceId())
                .placeName(review.getPlaceName())
                .contents(review.getContents())
                .createUtc(review.getCreateUtc())
                .build();
    }
}
