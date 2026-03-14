package com.payoneer.MachineCodingRound.repository;

import com.payoneer.MachineCodingRound.model.Template;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TemplateRepository {

    private Map<String, Template> templates = new ConcurrentHashMap<>();

    public Template save(Template template){
        templates.put(template.getName(), template);
        return template;
    }

    public Template findByName(String name){
        return templates.get(name);
    }
}