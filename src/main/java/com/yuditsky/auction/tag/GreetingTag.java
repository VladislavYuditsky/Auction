package com.yuditsky.auction.tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;

public class GreetingTag extends TagSupport {
    private final Locale localeEN = new Locale("en", "US");

    @Override
    public int doStartTag() {
        try {
            String name = "guest";
            String hello = "Hello";

            HttpSession session = pageContext.getSession();

            Locale locale;
            if(session.getAttribute("locale") != null) {
                locale = (Locale) session.getAttribute("locale");
            } else {
                locale = localeEN;
            }

            if(!locale.equals(localeEN)){
                name = "гость";
                hello = "Привет";
            }

            if (session.getAttribute("login") != null) {
                name = (String) session.getAttribute("login");
            }

            pageContext.getOut().print("<h2><b>" +  hello + ", " + name + "! </b></h2>");
        } catch (IOException e) {
            /////////////////
        }

        return SKIP_BODY;
    }
}
