package serialize.impl;

import com.alibaba.fastjson.JSON;
import serialize.Serializer;
import serialize.SerializerAlgrithm;

public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deSerialize(Class<T> tClass, byte[] bytes) {
        return JSON.parseObject(bytes, tClass);
    }
}
