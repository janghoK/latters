package com.jangho.latters.user.adapter.out.persistence.user.converter;

import com.jangho.latters.user.domain.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EmailConverter implements AttributeConverter<Email, String> {
    @Override
    public String convertToDatabaseColumn(Email attribute) {
        return attribute.getEmail();
    }

    @Override
    public Email convertToEntityAttribute(String dbData) {
        return Email.from(dbData);
    }
}
