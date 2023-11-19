package service;

import bean.elementaryDto;
import dao.elementaryDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class elementaryServiceImpl implements elementaryService{
    private static final Logger log = LogManager.getLogger(elementaryServiceImpl.class);

    @Autowired
    elementaryDaoImpl ed;

    @Value("${school.key}")
    private String key;

    @Override
    public String test() {
        String result = "";
        System.out.println("dbtest service");
        result = ed.test();
        return result;
    }

    @Override
    public int elementaryinsert() {
        int result = 0;
        String url = "https://open.neis.go.kr/hub/schoolInfo?";
        String type = "json"; //xml, json
        int pindex = 0; //페이지 위치
        String pSize = "10"; //페이지당 요청 숫자
        //String key = "78e9f815cc9b48619dccdb4ff4e23a31";
        String ATPT_OFCDC_SC_CODE = "B10"; //시도교육청코드
        //A00 울산광역시
        //B10 서울특별시
        //C10 부산광역시
        //D10 대구광역시
        //E10 인천광역시
        //F10
        //G10
        //H10
        //I10
        //J10
        //K10
        //M10
        //N10
        //P10
        //Q10
        //R10
        //S10
        //T10
        String fullpath ; //+"&ATPT_OFCDC_SC_CODE="+ATPT_OFCDC_SC_CODE;
        URL obj = null;
        BufferedReader br = null;
        String line;
        while (true) {
            pindex += 1;
            fullpath = url+"type="+type+"&pindex="+pindex+"&pSize="+pSize+"&key="+key;
            StringBuffer sb = new StringBuffer();
            try {
                obj = new URL(fullpath);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                JSONParser parser = new JSONParser();
                JSONObject sb_jo = new JSONObject();
                JSONArray sb_ja = new JSONArray();
                JSONObject jo = new JSONObject();
                JSONArray ja = new JSONArray();
                sb_jo = (JSONObject) parser.parse(sb.toString());
                sb_ja = (JSONArray) sb_jo.get("schoolInfo");

                jo = (JSONObject) sb_ja.get(0);
                ja = (JSONArray) jo.get("head");
                JSONObject result1 = (JSONObject) ja.get(1);
                JSONObject result_code = (JSONObject) result1.get("RESULT");
                log.info(result_code.get("CODE"));
                log.info(pindex);
                if(!"INFO-000".equals(result_code.get("CODE")) || pindex > 3){
                    break;
                }

                jo = (JSONObject) sb_ja.get(1);
                ja = (JSONArray) jo.get("row");
                System.out.println(ja);

                List<elementaryDto> list = new ArrayList<elementaryDto>();

                if (ja.size() > 0) {
                    for (int i = 0; i < ja.size(); i++) {
                        elementaryDto dto = new elementaryDto();
                        JSONObject jsonObject = (JSONObject) ja.get(i);

                        dto.setNAME((String) jsonObject.get("SCHUL_NM")); //기관이름
                        dto.setCODE((String) jsonObject.get("SD_SCHUL_CODE")); //표준학교코
                        dto.setSCHOOL_KIND((String) jsonObject.get("SCHUL_KND_SC_NM")); //학교 종류
                        dto.setADDRESS((String) jsonObject.get("ORG_RDNMA")); //도로명주소
                        dto.setDETAIL_ADDRESS((String) jsonObject.get("ORG_RDNDA")); //상세주소
                        dto.setCR_DATE((String) jsonObject.get("FOND_YMD")); //설립일
                        switch (dto.getSCHOOL_KIND()) {
                            case "초등학교":
                                dto.setFLAG("3");
                                break;
                            case "중학교":
                                dto.setFLAG("4");
                                break;
                            case "고등학교":
                                dto.setFLAG("5");
                                break;
                            default:
                                dto.setFLAG("E");
                        }
                        log.info("logger" + dto.toString());
                        list.add(dto);
                    }
                    ed.elementaryinsert(list);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
