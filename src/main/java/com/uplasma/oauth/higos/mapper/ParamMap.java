package com.uplasma.oauth.higos.mapper;

import java.util.HashMap;

public class ParamMap extends HashMap<String,Object> {
    private static final long serialVersionUID = -5874613844910293251L;
    public static ParamMap buildParam(String key, Object value) {
        ParamMap param = new ParamMap();
        param.put(key, value);
        return param;
    }
    @Override
    public ParamMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
