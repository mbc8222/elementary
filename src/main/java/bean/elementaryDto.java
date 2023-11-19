package bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class elementaryDto {
    private String NAME; // 이름
    private String CODE; //표준학교코드
    private String SCHOOL_KIND; //학교 종류
    private String ADDRESS; //도로명주소
    private String DETAIL_ADDRESS; //상세주소
    private String CR_DATE; //설립일
    private String FLAG; //기관구분
}
