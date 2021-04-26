package com.justanalytics.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RestEnvelope implements Serializable {
    private static final long serialVersionUID = 3850945033649827507L;
    private static final DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private Object data;
    private Map<String, Object> extra = new HashMap<>();
    public RestEnvelope(Object data){
        this.data = data;
        extra.put("updatedTime", LocalDateTime.now().format(localDateTimeFormatter) + "Z");
    }

    public static RestEnvelope of(Object data) {
        return new RestEnvelope(data);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra.putAll(extra);
    }

    public void putProperty(String key, Object value){
        extra.put(key, value);
    }

    public Optional<Object> getProperty(String key){
        return Optional.ofNullable(extra.get(key));
    }
}