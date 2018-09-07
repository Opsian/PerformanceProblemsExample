package com.opsian.performance_problems_example.views;

import com.opsian.performance_problems_example.core.Person;
import io.dropwizard.views.View;

public class PersonView extends View {
    private final Person person;

    public enum Template {
        FREEMARKER("freemarker/person.ftl"),
        MUSTACHE("mustache/person.mustache");

        private String templateName;

        Template(String templateName) {
            this.templateName = templateName;
        }

        public String getTemplateName() {
            return templateName;
        }
    }

    public PersonView(PersonView.Template template, Person person) {
        super(template.getTemplateName());
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
