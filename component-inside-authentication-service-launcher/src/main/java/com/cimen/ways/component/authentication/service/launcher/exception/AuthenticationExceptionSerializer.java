package com.cimen.ways.component.authentication.service.launcher.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName AuthenticationExceptionSerializer
 * @Description 自定义异常
 * @Date 2020/5/21 15:54
 * @Author wangyong
 * @Version 1.0
 **/
public class AuthenticationExceptionSerializer extends StdSerializer<AuthenticationException> {

    public AuthenticationExceptionSerializer() {
        super(AuthenticationException.class);
    }

    @Override
    public void serialize(AuthenticationException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("code", e.getHttpErrorCode());
        jsonGenerator.writeStringField("msg",  e.getMessage());
        jsonGenerator.writeObjectField("data", e.getOAuth2ErrorCode());
        if (e.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : e.getAdditionalInformation().entrySet()) {
                jsonGenerator.writeStringField(entry.getKey(), entry.getValue());
            }
        }
        jsonGenerator.writeEndObject();
    }
}
