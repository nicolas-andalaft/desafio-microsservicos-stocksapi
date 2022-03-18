package com.nicolas.stocksapi.core;

import java.util.Map;

public interface IModel<T> { 
    public Map<String, Object> toMap();    
    public Map<String, Object> toMap(T obj);    
}
