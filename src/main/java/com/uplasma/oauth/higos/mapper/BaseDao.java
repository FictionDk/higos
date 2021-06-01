package com.uplasma.oauth.higos.mapper;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
    public int insert(T t);
    public int update(T t);
    public int delete(T t);
    public T queryObject(String id);
    public List<T> queryList(Map<String,Object> queryMap);
}
