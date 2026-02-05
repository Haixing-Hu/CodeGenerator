package me.lotabout.codegenerator.util;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.java.generate.element.MethodElement;
import org.junit.jupiter.api.Test;

import com.intellij.psi.PsiMethod;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MethodEntryTest {

  @Test
  void velocityNameShouldPreferMethodNameForGetter() {
    final MethodElement element = mock(MethodElement.class);
    when(element.getName()).thenReturn("username");
    when(element.getMethodName()).thenReturn("getUsername");

    final MethodEntry entry = newMethodEntry(element);
    final Map<String, Object> contextMap = new HashMap<>();
    contextMap.put("methods", List.of(entry));

    final String template = "#foreach($m in $methods)$m.name#end";
    final String result = GenerationUtil.velocityEvaluate(contextMap, null, template, emptyList()).trim();

    // 期望输出getter方法名，而不是字段名
    assertEquals("getUsername", result);
  }

  @Test
  void velocityMethodNameShouldRenderGetterName() {
    final MethodElement element = mock(MethodElement.class);
    when(element.getName()).thenReturn("username");
    when(element.getMethodName()).thenReturn("getUsername");

    final MethodEntry entry = newMethodEntry(element);
    final Map<String, Object> contextMap = new HashMap<>();
    contextMap.put("methods", List.of(entry));

    final String template = "#foreach($m in $methods)$m.methodName#end";
    final String result = GenerationUtil.velocityEvaluate(contextMap, null, template, emptyList()).trim();

    assertEquals("getUsername", result);
  }

  private static MethodEntry newMethodEntry(final MethodElement element) {
    try {
      final Constructor<MethodEntry> ctor =
          MethodEntry.class.getDeclaredConstructor(PsiMethod.class, MethodElement.class);
      ctor.setAccessible(true);
      return ctor.newInstance(mock(PsiMethod.class), element);
    } catch (final Exception e) {
      throw new IllegalStateException("Failed to construct MethodEntry for tests", e);
    }
  }
}
