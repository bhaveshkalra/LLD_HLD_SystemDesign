package com.payoneer.MachineCodingRound.controller;

import com.payoneer.MachineCodingRound.model.Template;
import com.payoneer.MachineCodingRound.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/templates")
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @PostMapping("/createTemplate") //register templates
    public Template create(@RequestBody Template template){
        if (template.getName() == null ||
                template.getChannel() == null ||
                template.getSubject() == null ||
                template.getBody() == null){
            throw new RuntimeException("Invalid template");
        }

        return templateService.registerTemplate(template);
    }
}
