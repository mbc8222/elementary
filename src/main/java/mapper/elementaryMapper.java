package mapper;

import bean.elementaryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface elementaryMapper {

    public String test();

    public int elementaryinsert(elementaryDto dto2);
}
