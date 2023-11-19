package dao;

import bean.elementaryDto;
import mapper.elementaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class elementaryDaoImpl implements elementaryDao{

    @Autowired
    elementaryMapper em;
    @Override
    public String test() {
        String result = "";
        System.out.println("dbtest dao");
        result = em.test();
        return result;

    }

    @Override
    public int elementaryinsert(List<elementaryDto> list) {
        int result = 0;
        for(elementaryDto dto2 : list){
            result = em.elementaryinsert(dto2);
        }
        return result;
    }
}
