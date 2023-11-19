package dao;

import bean.elementaryDto;

import java.util.List;

public interface elementaryDao {
    public String test();

    int elementaryinsert(List<elementaryDto> list);
}
