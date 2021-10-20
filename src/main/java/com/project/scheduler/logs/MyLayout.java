package com.project.scheduler.logs;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

@Plugin(name = "MyLayout", category = "Core", elementType = Layout.ELEMENT_TYPE, printObject = true)
public class MyLayout extends AbstractStringLayout {

    private final StringBuilder builder = new StringBuilder();

    protected MyLayout(Charset charset) {
        super(charset);
    }



    @Override
    public String toSerializable(LogEvent event) {
        builder.setLength(0);
        builder.append("<tr><td>").append("   Kyiv-Mohyla Academy   ").append(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:S").format(event.getTimeMillis())).append(" ")
                .append(event.getLevel().toString()).append(" ").append(event.getLoggerName()).append(" ").append(event.getMessage().getFormattedMessage()).append(" ");
        if (event.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            event.getThrown().printStackTrace(pw);
            pw.close();
            builder.append(sw);
        }

        builder.append("</tr></td>\n");
        return builder.toString();
    }

    @PluginFactory
    public static MyLayout createLayout(@PluginAttribute(value = "charset") Charset charset) {
        if(charset == null) {
            charset = Charset.defaultCharset();
        }
        return new MyLayout(charset);
    }

}
