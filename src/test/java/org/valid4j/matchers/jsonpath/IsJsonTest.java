package org.valid4j.matchers.jsonpath;

import com.jayway.jsonpath.Configuration;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.valid4j.matchers.jsonpath.helpers.StrictParsingConfiguration;
import org.valid4j.matchers.jsonpath.helpers.TestingMatchers;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.jsonpath.JsonPathMatchers.isJson;
import static org.valid4j.matchers.jsonpath.helpers.ResourceHelpers.resource;
import static org.valid4j.matchers.jsonpath.helpers.ResourceHelpers.resourceAsFile;

public class IsJsonTest {
    private static final String BOOKS_JSON_STRING = resource("books.json");
    private static final File BOOKS_JSON_FILE = resourceAsFile("books.json");

    @BeforeClass
    public static void setupStrictJsonParsing() {
        Configuration.setDefaults(new StrictParsingConfiguration());
    }

    @AfterClass
    public static void setupDefaultJsonParsing() {
        Configuration.setDefaults(null);
    }

    @Test
    public void shouldMatchJsonObjectEvaluatedToTrue() {
        Object parsedJson = parseJson(BOOKS_JSON_STRING);
        assertThat(parsedJson, isJson(TestingMatchers.withPathEvaluatedTo(true)));
    }

    @Test
    public void shouldNotMatchJsonObjectEvaluatedToFalse() {
        Object parsedJson = parseJson(BOOKS_JSON_STRING);
        assertThat(parsedJson, not(isJson(TestingMatchers.withPathEvaluatedTo(false))));
    }

    @Test
    public void shouldMatchJsonStringEvaluatedToTrue() {
        assertThat(BOOKS_JSON_STRING, isJson(TestingMatchers.withPathEvaluatedTo(true)));
    }

    @Test
    public void shouldNotMatchJsonStringEvaluatedToFalse() {
        assertThat(BOOKS_JSON_STRING, not(isJson(TestingMatchers.withPathEvaluatedTo(false))));
    }

    @Test
    public void shouldMatchJsonFileEvaluatedToTrue() {
        assertThat(BOOKS_JSON_FILE, isJson(TestingMatchers.withPathEvaluatedTo(true)));
    }

    @Test
    public void shouldNotMatchJsonFileEvaluatedToFalse() {
        assertThat(BOOKS_JSON_FILE, not(isJson(TestingMatchers.withPathEvaluatedTo(false))));
    }

    @Test
    public void shouldBeDescriptive() {
        Matcher<Object> matcher = isJson(TestingMatchers.withPathEvaluatedTo(true));
        Description description = new StringDescription();
        matcher.describeTo(description);
        assertThat(description.toString(), startsWith("is json"));
        assertThat(description.toString(), containsString(TestingMatchers.MATCH_TRUE_TEXT));
    }

    @Test
    public void shouldDescribeMismatchOfValidJson() {
        Matcher<Object> matcher = isJson(TestingMatchers.withPathEvaluatedTo(true));
        Description description = new StringDescription();
        matcher.describeMismatch(BOOKS_JSON_STRING, description);
        assertThat(description.toString(), containsString(TestingMatchers.MISMATCHED_TEXT));
    }

    @Test
    public void shouldDescribeMismatchOfInvalidJson() {
        Matcher<Object> matcher = isJson(TestingMatchers.withPathEvaluatedTo(true));
        Description description = new StringDescription();
        matcher.describeMismatch("invalid-json", description);
        assertThat(description.toString(), containsString("\"invalid-json\""));
    }

    private static Object parseJson(String json) {
        return Configuration.defaultConfiguration().jsonProvider().parse(json);
    }

}
