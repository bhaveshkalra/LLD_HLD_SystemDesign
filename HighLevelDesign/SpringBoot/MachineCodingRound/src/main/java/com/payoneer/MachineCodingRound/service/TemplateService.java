package com.payoneer.MachineCodingRound.service;

import com.payoneer.MachineCodingRound.model.Template;
import com.payoneer.MachineCodingRound.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TemplateService {
    @Autowired
    TemplateRepository repository;

    public Template registerTemplate(Template template) {
        template.setId(UUID.randomUUID().toString());
        repository.save(template);
        return template;
    }

    public Template getByName(String name) {
        return repository.findByName(name);
    }
}
