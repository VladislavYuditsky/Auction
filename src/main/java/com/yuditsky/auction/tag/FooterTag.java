package com.yuditsky.auction.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class FooterTag extends TagSupport {
    @Override
    public int doStartTag() {
        try {
            pageContext.getOut().print("Yuditsky");
        } catch (IOException e) {
           ///
        }


        return SKIP_BODY;
    }
}
