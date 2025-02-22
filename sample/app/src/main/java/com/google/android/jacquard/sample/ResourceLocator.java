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

package com.google.android.jacquard.sample;

import static com.google.android.jacquard.sdk.log.LogLevel.ASSERT;
import static com.google.android.jacquard.sdk.log.LogLevel.DEBUG;
import static com.google.android.jacquard.sdk.log.LogLevel.ERROR;
import static com.google.android.jacquard.sdk.log.LogLevel.INFO;
import static com.google.android.jacquard.sdk.log.LogLevel.WARNING;

import android.content.Context;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import com.google.android.jacquard.sample.musicalthreads.audio.Fader;
import com.google.android.jacquard.sample.musicalthreads.audio.SoundPlayer.Note;
import com.google.android.jacquard.sample.musicalthreads.audio.SoundPoolPlayer;
import com.google.android.jacquard.sample.musicalthreads.player.PluckTreadsPlayerImpl;
import com.google.android.jacquard.sample.musicalthreads.player.ThreadsPlayer;
import com.google.android.jacquard.sdk.JacquardManager;
import com.google.android.jacquard.sdk.log.LogLevel;
import com.google.android.jacquard.sdk.log.PrintLogger;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Very simple implementation of the Service Locator IoC pattern - central registry for obtaining
 * various "services" used by the app.
 */
public class ResourceLocator {

  private static final String TAG = ResourceLocator.class.getSimpleName();
  private static final long NOTE_FADE_DURATION_IN_MILLIS = 500;
  private final Context context;
  private Preferences preferences;
  private Gson gson;
  private ConnectivityManager connectivityManager;

  ResourceLocator(Context context) {
    this.context = context.getApplicationContext();
    configurePrintLogger();
  }

  private static SoundPool getSoundPool() {
    final AudioAttributes audioAttributes =
        new AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build();

    return new SoundPool.Builder()
        .setMaxStreams(Note.values().length)
        .setAudioAttributes(audioAttributes)
        .build();
  }

  public Preferences getPreferences() {
    if (preferences == null) {
      preferences = new Preferences(context, getGson());
    }
    return preferences;
  }

  public ConnectivityManager getConnectivityManager() {
    if (connectivityManager == null) {
      connectivityManager = new ConnectivityManager(JacquardManager.getInstance());
    }
    return connectivityManager;
  }

  public ThreadsPlayer getThreadsPlayer() {
    SoundPool soundPool = getSoundPool();
    Fader fader = new Fader(soundPool, NOTE_FADE_DURATION_IN_MILLIS);
    SoundPoolPlayer player = new SoundPoolPlayer(context, soundPool, fader);
    return new PluckTreadsPlayerImpl(player);
  }

  public Resources getResources() {
    return context.getResources();
  }

  private Gson getGson() {
    if (gson == null) {
      gson =
          new GsonBuilder().registerTypeAdapterFactory(SampleTypeAdapterFactory.create()).create();
    }
    return gson;
  }

  /**
   * Configures {@link PrintLogger} with log levels {@link LogLevel}.
   */
  private void configurePrintLogger() {
    ImmutableList<LogLevel> logLevels = ImmutableList.of(DEBUG, INFO, WARNING, ERROR, ASSERT);
    PrintLogger.initialize(logLevels);
    PrintLogger.d(TAG, "PrintLogger is configured with " + logLevels);
  }
}
