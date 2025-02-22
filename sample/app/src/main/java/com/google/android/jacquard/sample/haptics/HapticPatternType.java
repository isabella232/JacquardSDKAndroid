/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.jacquard.sample.haptics;

import com.google.android.jacquard.sdk.command.HapticCommand.Frame;
import com.google.android.jacquard.sdk.command.HapticCommand.Pattern;

/** A set of predefined haptic patterns with plays on the gear component. */
public enum HapticPatternType {
  INSERT_PATTERN(
      /* description= */ "Tag Insertion Pattern",
      /* onMs= */ 200,
      /* offMs= */ 0,
      Pattern.HAPTIC_SYMBOL_SINE_INCREASE,
      /* maxAmplitudePercent= */ 60,
      /* repeatNminusOne= */ 0),
  GESTURE_PATTERN(
      /* description= */ "Gesture Pattern",
      /* onMs= */ 170,
      /* offMs= */ 0,
      Pattern.HAPTIC_SYMBOL_SINE_INCREASE,
      /* maxAmplitudePercent= */ 60,
      /* repeatNminusOne= */ 0),
  NOTIFICATION_PATTERN(
      /* description= */ "Notification Pattern",
      /* onMs= */ 170,
      /* offMs= */ 30,
      Pattern.HAPTIC_SYMBOL_SINE_INCREASE,
      /* maxAmplitudePercent= */ 60,
      /* repeatNminusOne= */ 1),
  ERROR_PATTERN(
      /* description= */ "Error Pattern",
      /* onMs= */ 170,
      /* offMs= */ 50,
      Pattern.HAPTIC_SYMBOL_SINE_INCREASE,
      /* maxAmplitudePercent= */ 60,
      /* repeatNminusOne= */ 3),
  ALERT_PATTERN(
      /* description= */ "Alert Pattern",
      /* onMs= */ 170,
      700,
      Pattern.HAPTIC_SYMBOL_SINE_INCREASE,
      /* maxAmplitudePercent= */ 60,
      /* repeatNminusOne= */ -1);

  private final Frame frame;
  private final String description;

  HapticPatternType(
      String description,
      int onMs,
      int offMs,
      Pattern pattern,
      int maxAmplitudePercent,
      int repeatNminusOne) {
    this.description = description;
    frame =
        Frame.builder()
            .setOnMs(onMs)
            .setOffMs(offMs)
            .setPattern(pattern)
            .setMaxAmplitudePercent(maxAmplitudePercent)
            .setRepeatNminusOne(repeatNminusOne)
            .build();
  }

  /** Returns frame {@link Frame} which play on gear component. */
  public Frame getFrame() {
    return frame;
  }

  /** Returns haptics pattern type description. */
  public String getDescription() {
    return description;
  }
}
