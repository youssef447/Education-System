package com.example.education_system.config.sanitization;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class HtmlSanitizerService {
    private static final Safelist safelist = Safelist.basic();
    public static String sanitize(String input) {
        return Jsoup.clean(input, safelist);
    }
}


