package com.example.balance.service.controller.validation;

import com.example.balance.service.model.Extra;
import com.example.balance.service.model.Request;
import com.example.balance.service.model.RequestType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class RequestValidatorSignUp implements RequestTypeValidator {
    private static final Set<String> SIGN_UP_EXTRAS = Set.of("login", "password");

    @Override
    public RequestType willValidate() {
        return RequestType.CREATE_AGT;
    }

    @Override
    public boolean isValid(Request request) {
        if (request.getRequestType() != RequestType.CREATE_AGT) {
            return false;
        }

        List<Extra> extras = request.getExtras();
        if (extras == null || extras.size() != 2) {
            return false;
        }

        //Map<String, String> extMap = extras.stream()
        //        .collect(Collectors.toMap(Extra::getName, Extra::getValue));
        //https://bugs.openjdk.java.net/browse/JDK-8148463 Collectors.toMap fails on null values
        //это поведение случайно совпадает с желаемым, но в общем случае null значения
        //могут быть допустимыми. Поэтому используем альтернативный способ, а фильтрацию пустых значений
        //выполним отдельно
        Map<String, String> extMap = extras.stream().collect(
                HashMap::new, (m,v)->m.put(v.getName(), v.getValue()), HashMap::putAll);

        //filter out null values and compare remaining keys with model
        return extMap.entrySet().stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .keySet()
                .equals(SIGN_UP_EXTRAS);
    }
}
