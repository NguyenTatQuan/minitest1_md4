package org.example.minitest1_md4.formatter;


import org.example.minitest1_md4.model.Type;
import org.example.minitest1_md4.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class TypeFormatter implements Formatter<Type> {
    private ITypeService typeService;

    @Autowired
    public TypeFormatter(ITypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public Type parse(String text, Locale locale) throws ParseException {
        Optional<Type> optionalType = typeService.findById(Long.parseLong(text));
        return optionalType.orElse(null);
    }

    @Override
    public String print(Type object, Locale locale) {
        return "[" + object.getId() + ", " +object.getName() + "]";
    }
}
