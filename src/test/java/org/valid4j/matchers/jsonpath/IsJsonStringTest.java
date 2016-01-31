package org.valid4j.matchers.jsonpath;

import com.jayway.jsonpath.Configuration;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.StringDescription;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.valid4j.matchers.jsonpath.helpers.ResourceHelpers;
import org.valid4j.matchers.jsonpath.helpers.StrictParsingConfiguration;
import org.valid4j.matchers.jsonpath.helpers.TestingMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class IsJsonStringTest {
    private static final String BOOKS_JSON = ResourceHelpers.resource("books.json");

    @BeforeClass
    public static void setupStrictJsonParsing() {
        Configuration.setDefaults(new StrictParsingConfiguration());
    }

    @AfterClass
    public static void setupDefaultJsonParsing() {
        Configuration.setDefaults(null);
    }

    @Test
    public void shouldMatchJsonStringEvaluatedToTrue() {
        assertThat(BOOKS_JSON, JsonPathMatchers.isJsonString(TestingMatchers.withPathEvaluatedTo(true)));
    }

    @Test
    public void shouldNotMatchJsonStringEvaluatedToFalse() {
        assertThat(BOOKS_JSON, Matchers.not(JsonPathMatchers.isJsonString(TestingMatchers.withPathEvaluatedTo(false))));
    }

    @Test
    public void shouldNotMatchInvalidJsonString() {
        assertThat("invalid-json", Matchers.not(JsonPathMatchers.isJsonString(TestingMatchers.withPathEvaluatedTo(true))));
        assertThat("invalid-json", Matchers.not(JsonPathMatchers.isJsonString(TestingMatchers.withPathEvaluatedTo(false))));
    }

    @Test
    public void shouldBeDescriptive() {
        Matcher<String> matcher = JsonPathMatchers.isJsonString(TestingMatchers.withPathEvaluatedTo(true));
        Description description = new StringDescription();
        matcher.describeTo(description);
        assertThat(description.toString(), startsWith("is json"));
        assertThat(description.toString(), containsString(TestingMatchers.MATCH_TRUE_TEXT));
    }

    @Test
    public void shouldDescribeMismatchOfValidJson() {
        Matcher<String> matcher = JsonPathMatchers.isJsonString(TestingMatchers.withPathEvaluatedTo(true));
        Description description = new StringDescription();
        matcher.describeMismatch(BOOKS_JSON, description);
        assertThat(description.toString(), containsString(TestingMatchers.MISMATCHED_TEXT));
    }

    @Test
    public void shouldDescribeMismatchOfInvalidJson() {
        Matcher<String> matcher = JsonPathMatchers.isJsonString(TestingMatchers.withPathEvaluatedTo(true));
        Description description = new StringDescription();
        matcher.describeMismatch("invalid-json", description);
        assertThat(description.toString(), containsString("\"invalid-json\""));
    }
}
