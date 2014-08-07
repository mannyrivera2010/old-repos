package demo;

import org.rythmengine.Rythm;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Hello world sample
 */
public class HelloWorld {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        
        try {
            cfg.setDirectoryForTemplateLoading(new File("C:\\users\\riverema\\git\\mod-earasoft-site1\\build\\mods\\com.earasoft~site1~1.0.0-final\\templates\\"));
        // Load the template
        Template template = cfg.getTemplate("index.ftl");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("message", "Hello World!");

        // Language list
        List<String> language = new ArrayList<String>();
        language.add("Java");
        language.add("C++");
        language.add("Ceylon");
        language.add("Cobol");

        data.put("languages", language);

        ByteArrayOutputStream baStream = new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(baStream);
        template.process(data, out);
        System.out.println(baStream.toString());

        out.flush();
        } catch (IOException e) {
        e.printStackTrace();
        } catch (Exception e) {
        e.printStackTrace();
        }
    }
}
