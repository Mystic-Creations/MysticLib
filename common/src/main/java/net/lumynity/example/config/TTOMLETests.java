package net.lumynity.example.config;

import net.lumynity.lib.config.toml.TomlParser;
import net.lumynity.lib.config.toml.TomlParsingException;

/**
 * Manual test harness for TomlParser — tracks parser progress as features are implemented.
 * Run main() and check console output. Each test prints PASS / FAIL / ERROR.
 */
public class TTOMLETests {

    private static int passed = 0;
    private static int failed = 0;
    private static int errored = 0;

    public static void main(String[] args) {
        // ── Basic key/value ──────────────────────────────────────────────────
        test("Empty document",           "");
        test("Whitespace only",          "   \n\t\n");
        test("Comment only",             "# this is a comment");
        test("Comment with newline",     "# comment\n");

        // ── Bare keys ────────────────────────────────────────────────────────
        test("Simple bare key",          "key = \"value\"");
        test("Underscored bare key",     "some_key = \"value\"");
        test("Dashed bare key",          "some-key = \"value\"");
        test("Numeric bare key",         "123 = \"value\"");
        test("Mixed bare key",           "a1_b-2 = \"value\"");

        // ── Quoted keys ──────────────────────────────────────────────────────
        test("Double-quoted key",        "\"quoted key\" = \"value\"");
        test("Single-quoted key",        "'quoted key' = \"value\"");
        test("Empty quoted key",         "\"\" = \"value\"");

        // ── Dotted keys ──────────────────────────────────────────────────────
        test("Dotted bare keys",         "a.b.c = \"value\"");
        test("Dotted quoted keys",       "\"a\".\"b\" = \"value\"");
        test("Mixed dotted keys",        "a.\"b\".c = \"value\"");

        // ── String values ────────────────────────────────────────────────────
        test("Basic string",             "s = \"hello world\"");
        test("Empty string",             "s = \"\"");
        test("String with escapes",      "s = \"line1\\nline2\"");
        test("String tab escape",        "s = \"col1\\tcol2\"");
        test("String quote escape",      "s = \"say \\\"hi\\\"\"");
        test("String backslash escape",  "s = \"back\\\\slash\"");
        test("String unicode \\u",       "s = \"caf\\u00E9\"");
        test("String unicode \\U",       "s = \"\\U0001F600\"");
        test("String unicode \\x",       "s = \"\\x41\"");  // 'A'
        test("Multiline basic string",   "s = \"\"\"\nhello\nworld\n\"\"\"");
        test("Literal string",           "s = 'C:\\Users\\file.txt'");
        test("Multiline literal string", "s = '''\nline1\nline2\n'''");

        // ── Integer values ───────────────────────────────────────────────────
        test("Positive integer",         "n = 42");
        test("Negative integer",         "n = -17");
        test("Zero",                     "n = 0");
        test("Underscored integer",      "n = 1_000_000");
        test("Hex integer",              "n = 0xDEADBEEF");
        test("Octal integer",            "n = 0o755");
        test("Binary integer",           "n = 0b11010110");

        // ── Float values ─────────────────────────────────────────────────────
        test("Float",                    "f = 3.14");
        test("Negative float",           "f = -0.5");
        test("Float with exponent",      "f = 5e+22");
        test("Float inf",                "f = inf");
        test("Float -inf",               "f = -inf");
        test("Float nan",                "f = nan");

        // ── Boolean values ───────────────────────────────────────────────────
        test("Boolean true",             "b = true");
        test("Boolean false",            "b = false");

        // ── Datetime values ──────────────────────────────────────────────────
        test("Offset datetime",          "dt = 1979-05-27T07:32:00Z");
        test("Local datetime",           "dt = 1979-05-27T07:32:00");
        test("Local date",               "d = 1979-05-27");
        test("Local time",               "t = 07:32:00");

        // ── Arrays ───────────────────────────────────────────────────────────
        test("Empty array",              "arr = []");
        test("Integer array",            "arr = [1, 2, 3]");
        test("String array",             "arr = [\"a\", \"b\", \"c\"]");
        test("Mixed array",              "arr = [1, \"two\", true]");
        test("Nested array",             "arr = [[1, 2], [3, 4]]");
        test("Multiline array",          "arr = [\n  1,\n  2,\n  3\n]");
        test("Trailing comma array",     "arr = [1, 2, 3,]");

        // ── Inline tables ────────────────────────────────────────────────────
        test("Empty inline table",       "t = {}");
        test("Simple inline table",      "t = {a = 1, b = 2}");
        test("Nested inline table",      "t = {a = {b = 1}}");

        // ── Standard tables ──────────────────────────────────────────────────
        test("Simple table",             "[table]\nkey = \"value\"");
        test("Dotted table",             "[a.b.c]\nkey = \"value\"");
        test("Quoted table",             "[\"my table\"]\nkey = \"value\"");
        test("Multiple tables",
                "[tableA]\nk = 1\n\n[tableB]\nk = 2");
        test("Table then key",
                "[server]\nhost = \"localhost\"\nport = 8080");

        // ── Array of tables ──────────────────────────────────────────────────
        test("Array of tables",
                "[[products]]\nname = \"Hammer\"\n\n[[products]]\nname = \"Nail\"");
        test("Nested array of tables",
                "[[fruits]]\nname = \"apple\"\n\n  [[fruits.varieties]]\n  name = \"red delicious\"");

        // ── Multi-key documents ──────────────────────────────────────────────
        test("Multiple bare keys",
                "name = \"Tom\"\nage = 25\nactive = true");
        test("Keys with comments",
                "key = \"value\" # inline comment\nother = 42");
        test("Full example",
                "# Full example\ntitle = \"TOML Example\"\n\n[owner]\nname = \"Tom\"\n\n[database]\nserver = \"192.168.1.1\"\nports = [8001, 8002]\nenabled = true");

        // ── Error cases ──────────────────────────────────────────────────────
        testExpectError("Unclosed string",          "s = \"no end");
        testExpectError("Newline in string",        "s = \"line1\nline2\"");
        testExpectError("Double dot in key",        "a..b = 1");
        testExpectError("Trailing dot in key",      "a. = 1");
        testExpectError("Unclosed table bracket",   "[table");
        testExpectError("Bad escape",               "s = \"\\q\"");
        testExpectError("Bad unicode escape",       "s = \"\\uXXXX\"");
        testExpectError("Duplicate key",            "a = 1\na = 2");
        testExpectError("Duplicate table",          "[a]\n[a]");

        // ── Summary ──────────────────────────────────────────────────────────
        System.out.println("\n========================================");
        System.out.printf("  Results: %d passed, %d failed, %d errored%n",
                passed, failed, errored);
        System.out.println("========================================");
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    /**
     * Expects the parser to succeed (no exception).
     */
    private static void test(String name, String toml) {
        System.out.printf("[START], %s%n", name);
        try {
            new TomlParser(toml);
            System.out.printf("[PASS] %s%n", name);
            passed++;
        } catch (TomlParsingException e) {
            System.out.printf("[FAIL] %s  →  TomlParsingException: %s%n", name, e.getMessage());
            failed++;
        } catch (Exception e) {
            System.out.printf("[ERR ] %s  →  %s: %s%n", name, e.getClass().getSimpleName(), e.getMessage());
            errored++;
        }
    }

    /**
     * Expects the parser to throw TomlParsingException.
     */
    private static void testExpectError(String name, String toml) {
        try {
            new TomlParser(toml);
            System.out.printf("[FAIL] %s  →  expected TomlParsingException but none was thrown%n", name);
            failed++;
        } catch (TomlParsingException e) {
            System.out.printf("[PASS] %s  (correctly rejected: %s)%n", name, e.getMessage());
            passed++;
        } catch (Exception e) {
            System.out.printf("[ERR ] %s  →  wrong exception %s: %s%n", name, e.getClass().getSimpleName(), e.getMessage());
            errored++;
        }
    }
}