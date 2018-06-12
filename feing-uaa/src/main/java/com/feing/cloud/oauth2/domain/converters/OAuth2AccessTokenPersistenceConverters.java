package com.feing.cloud.oauth2.domain.converters;

import com.feing.cloud.core.json.JSON;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.persistence.AttributeConverter;

/**
 * OAuth2AccessToken 持久层转换
 *
 * @author longfei
 */
public class OAuth2AccessTokenPersistenceConverters implements AttributeConverter<OAuth2AccessToken, String> {
    @Override
    public String convertToDatabaseColumn(OAuth2AccessToken attribute) {
        return JSON.build().obj2json(attribute);
    }

    @Override
    public OAuth2AccessToken convertToEntityAttribute(String dbData) {
        return JSON.build().json2pojo(dbData, OAuth2AccessToken.class);
    }
}
