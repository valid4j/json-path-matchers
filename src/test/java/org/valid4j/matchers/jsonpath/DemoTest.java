package org.valid4j.matchers.jsonpath;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.jsonpath.JsonPathMatchers.*;
import static org.valid4j.matchers.jsonpath.helpers.ResourceHelpers.resource;
import static org.valid4j.matchers.jsonpath.helpers.ResourceHelpers.resourceAsFile;

@Ignore
public class DemoTest {
    @Test
    public void shouldFailOnJsonString() {
        String json = resource("books.json");
        assertThat(json, isJson(withJsonPath("$.store.name", equalTo("The Shop"))));
    }

    @Test
    public void shouldFailOnJsonFile() {
        File json = resourceAsFile("books.json");
        assertThat(json, isJson(withJsonPath("$.store.name", equalTo("The Shop"))));
    }

    @Test
    public void shouldFailOnInvalidJsonString() {
        String json = resource("invalid.json");
        assertThat(json, isJson(withJsonPath("$.store.name", equalTo("The Shop"))));
    }

    @Test
    public void shouldFailOnInvalidJsonFile() {
        File json = resourceAsFile("invalid.json");
        assertThat(json, isJson(withJsonPath("$.store.name", equalTo("The Shop"))));
    }

    @Test
    public void shouldFailOnTypedJsonString() {
        String json = resource("books.json");
        assertThat(json, isJsonString(withJsonPath("$.store.name", equalTo("The Shop"))));
    }

    @Test
    public void shouldFailOnTypedJsonFile() {
        File json = resourceAsFile("books.json");
        assertThat(json, isJsonFile(withJsonPath("$.store.name", equalTo("The Shop"))));
    }

    @Test
    public void shouldFailOnTypedInvalidJsonString() {
        String json = resource("invalid.json");
        assertThat(json, isJsonString(withJsonPath("$.store.name", equalTo("The Shop"))));
    }

    @Test
    public void shouldFailOnTypedInvalidJsonFile() {
        File json = resourceAsFile("invalid.json");
        assertThat(json, isJsonFile(withJsonPath("$.store.name", equalTo("The Shop"))));
    }

    @Test
    public void shouldFailOnNonExistingJsonPath() {
        String json = resource("books.json");
        assertThat(json, hasJsonPath("$.not-here"));
    }

    @Test
    public void shouldFailOnExistingJsonPath() {
        String json = resource("books.json");
        assertThat(json, hasNoJsonPath("$.store.name"));
    }

    @Test
    public void shouldFailOnExistingJsonPathAlternative() {
        String json = resource("books.json");
        assertThat(json, isJson(withoutJsonPath("$.store.name")));
    }
}
