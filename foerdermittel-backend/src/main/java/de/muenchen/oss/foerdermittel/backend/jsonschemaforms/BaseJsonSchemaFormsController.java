package de.muenchen.oss.foerdermittel.backend.jsonschemaforms;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public abstract class BaseJsonSchemaFormsController<IdType, IndexDto, CreateForm, UpdateForm> {
    @Autowired
    private SchemaGenerator schemaGenerator;

    private final Class<CreateForm> createFormClass;
    private final Class<UpdateForm> updateFormClass;

    @SuppressWarnings("unchecked")
    protected BaseJsonSchemaFormsController() {
        this.createFormClass = (Class<CreateForm>) ResolvableType
                .forClass(getClass())
                .as(BaseJsonSchemaFormsController.class)
                .getGeneric(2)
                .resolve();
        this.updateFormClass = (Class<UpdateForm>) ResolvableType
                .forClass(getClass())
                .as(BaseJsonSchemaFormsController.class)
                .getGeneric(3)
                .resolve();
    }

    @GetMapping(value = "/schema/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode createSchema() {
        return schemaGenerator.generateSchema(createFormClass);
    }

    @GetMapping(value = "/schema/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode updateSchema() {
        return schemaGenerator.generateSchema(updateFormClass);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public abstract List<IndexDto> index();

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public abstract UpdateForm get(@PathVariable("id") IdType id);

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public abstract void create(@Valid @RequestBody CreateForm createForm);

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public abstract void update(@PathVariable("id") IdType id, @Valid @RequestBody UpdateForm updateForm);

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public abstract void delete(@PathVariable("id") IdType id);
}
