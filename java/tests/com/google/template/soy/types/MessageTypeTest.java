/*
 * Copyright 2020 Google Inc.
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

package com.google.template.soy.types;

import static com.google.common.truth.Truth.assertThat;

import com.google.template.soy.testing.Foo;
import com.google.template.soy.testing.SharedTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class MessageTypeTest {
  SoyTypeRegistry typeRegistry;

  @Before
  public void setUp() {
    typeRegistry = SharedTestUtils.importing(Foo.getDescriptor());
  }

  SoyTypesTest.SoyTypeSubject assertThatType(String type) {
    return SoyTypesTest.assertThatSoyType(type, typeRegistry);
  }

  @Test
  public void testBaseProto() throws Exception {
    assertThat(typeRegistry.getType("Message")).isSameInstanceAs(MessageType.getInstance());
  }

  @Test
  public void testBaseProtoAssignability() throws Exception {
    assertThatType("Message").isAssignableFrom("Message");
    assertThatType("Message").isAssignableFrom("Foo");
    assertThatType("Foo").isNotAssignableFrom("Message");
  }
}
