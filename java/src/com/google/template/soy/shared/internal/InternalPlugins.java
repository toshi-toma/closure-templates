/*
 * Copyright 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.template.soy.shared.internal;


import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.template.soy.basicdirectives.BasicDirectives;
import com.google.template.soy.basicfunctions.BasicFunctions;
import com.google.template.soy.bididirectives.BidiDirectives;
import com.google.template.soy.bidifunctions.BidiFunctions;
import com.google.template.soy.coredirectives.CoreDirectives;
import com.google.template.soy.i18ndirectives.I18nFunctions;
import com.google.template.soy.internal.i18n.BidiGlobalDir;
import com.google.template.soy.plugin.restricted.SoySourceFunction;
import com.google.template.soy.shared.restricted.SoyFunction;
import com.google.template.soy.shared.restricted.SoyFunctionSignature;
import com.google.template.soy.shared.restricted.SoyPrintDirective;
import java.util.function.Function;

/** Lists all functions & directives shipped with Soy. */
public final class InternalPlugins {
  private InternalPlugins() {}

  public static ImmutableList<SoyFunction> internalLegacyFunctions() {
    return ImmutableList.copyOf(BuiltinFunction.values());
  }

  /** Returns a map (whose key is the name of the function) of the functions shipped with Soy. */
  public static ImmutableList<SoySourceFunction> internalFunctions() {
    // TODO(b/19252021): Include BuiltInFunctions
    return ImmutableList.copyOf(
        Iterables.concat(
            BasicFunctions.functions(), BidiFunctions.functions(), I18nFunctions.functions()));
  }

  /**
   * Returns a map (whose key is the name of the directive) of the functions that can be called as
   * print directives with Soy.
   */
  public static ImmutableMap<String, SoySourceFunction> internalAliasedDirectivesMap() {
    return internalFunctions().stream()
        .filter(
            e ->
                e.getClass()
                    .getAnnotation(SoyFunctionSignature.class)
                    .callableAsDeprecatedPrintDirective())
        .collect(
            ImmutableMap.toImmutableMap(
                e -> "|" + e.getClass().getAnnotation(SoyFunctionSignature.class).name(),
                Function.identity()));
  }

  public static ImmutableList<SoyPrintDirective> internalDirectives(
      final SoyScopedData soyScopedData) {
    Supplier<BidiGlobalDir> bidiProvider = soyScopedData::getBidiGlobalDir;
    return ImmutableList.copyOf(
        Iterables.concat(
            CoreDirectives.directives(),
            BasicDirectives.directives(),
            BidiDirectives.directives(bidiProvider)));
  }
}
