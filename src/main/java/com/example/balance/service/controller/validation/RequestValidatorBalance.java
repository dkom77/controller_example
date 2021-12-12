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
public class RequestValidatorBalance implements RequestTypeValidator {
    private final Set<String> BALANCE_EXTRAS = Set.of("login", "password");

    @Override
    public RequestType willValidate() {
        return RequestType.GET_BALANCE;
    }

    @Override
    public boolean isValid(Request request) {
        if (request.getRequestType() != RequestType.GET_BALANCE) {
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
                .equals(BALANCE_EXTRAS);
    }

}
