/**
 * @FileName: EscapeTool.java
 * @Package: com.yale.spring.base.sms.archetype.commons.util
 * @author sence
 * @created 4/14/2016 7:40 PM
 * <p/>
 * Copyright 2015 asura
 */
package com.yaler.di.shopd.tool.velocity;

import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

public class EscapeTool extends Directive {


    private static final Logger LOGGER = LoggerFactory.getLogger(EscapeTool.class);

    @Override
    public String getName() {
        return "escape";
    }

    @Override
    public int getType() {
        return Directive.LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String needEscape = null;
        try {
            //params
            if (node.jjtGetChild(0) != null) {
                needEscape = String.valueOf(node.jjtGetChild(0).value(context));
            }
            Escaper escaper = HtmlEscapers.htmlEscaper();
            writer.write(escaper.escape(needEscape));
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("VELOCITY RENDER ERROR:", e);
            }
            writer.write("");
        }
        return true;
    }
}
