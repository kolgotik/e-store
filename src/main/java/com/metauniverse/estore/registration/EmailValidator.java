package com.metauniverse.estore.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    @Override
    public boolean test(String email) {
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }
}
