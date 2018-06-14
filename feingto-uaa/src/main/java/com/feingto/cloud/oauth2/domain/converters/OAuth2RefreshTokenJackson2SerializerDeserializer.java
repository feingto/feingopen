package com.feingto.cloud.oauth2.domain.converters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.io.IOException;
import java.util.Date;

/**
 * OAuth2RefreshToken 序列化、反序列化
 *
 * @author longfei
 */
public final class OAuth2RefreshTokenJackson2SerializerDeserializer {
    private static final String TOKEN_VALUE = "value";
    private static final String EXPIRATION = "expiration";

    public static class OAuth2RefreshTokenJackson2Serializer extends StdSerializer<OAuth2RefreshToken> {
        private static final long serialVersionUID = 3889984965007463118L;

        public OAuth2RefreshTokenJackson2Serializer() {
            super(OAuth2RefreshToken.class);
        }

        @Override
        public void serialize(OAuth2RefreshToken value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeStringField(TOKEN_VALUE, value.getValue());
            if (value instanceof ExpiringOAuth2RefreshToken) {
                gen.writeNumberField(EXPIRATION, ((ExpiringOAuth2RefreshToken) value).getExpiration().getTime());
            }
            gen.writeEndObject();
        }
    }

    public static class OAuth2RefreshTokenJackson2Deserializer extends StdDeserializer<OAuth2RefreshToken> {
        private static final long serialVersionUID = -8333266025512748241L;

        protected OAuth2RefreshTokenJackson2Deserializer() {
            super(OAuth2RefreshToken.class);
        }

        @Override
        public OAuth2RefreshToken deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String tokenValue = null;
            Long expiration = null;

            while (p.nextToken() != JsonToken.END_OBJECT) {
                String name = p.getCurrentName();
                p.nextToken();
                if (TOKEN_VALUE.equals(name)) {
                    tokenValue = p.getText();
                } else if (EXPIRATION.equals(name)) {
                    try {
                        expiration = p.getLongValue();
                    } catch (JsonParseException e) {
                        expiration = Long.valueOf(p.getText());
                    }
                }
            }

            if (expiration != null) {
                return new DefaultExpiringOAuth2RefreshToken(tokenValue, new Date(expiration));
            } else {
                return new DefaultOAuth2RefreshToken(tokenValue);
            }
        }
    }
}
