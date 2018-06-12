package com.feing.cloud.oauth2.domain.converters;

import com.feing.cloud.core.json.JSON;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import javax.persistence.AttributeConverter;

/**
 * OAuth2RefreshToken 持久层转换
 *
 * @author longfei
 */
public class OAuth2RefreshTokenPersistenceConverters implements AttributeConverter<OAuth2RefreshToken, String> {
    @Override
    public String convertToDatabaseColumn(OAuth2RefreshToken attribute) {
        return JSON.build().obj2json(attribute);
    }

    @Override
    public OAuth2RefreshToken convertToEntityAttribute(String dbData) {
        return JSON.build().json2pojo(dbData, OAuth2RefreshToken.class);
    }
}
