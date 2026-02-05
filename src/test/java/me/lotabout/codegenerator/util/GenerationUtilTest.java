package me.lotabout.codegenerator.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenerationUtilTest {

  @Test
  public void testVelocityEvaluate() {
    final Map<String, Object> contextMap = new HashMap<>();
    contextMap.put("message", "Hello World");
    final String template = "Template: $message";
    final String result = GenerationUtil.velocityEvaluate(contextMap, null, template, emptyList());
    // Note: velocityEvaluate adds a leading newline due to include processing
    assertEquals("\nTemplate: Hello World", result);
  }
}
