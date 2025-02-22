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

import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.jacquard.sdk.log.PrintLogger;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();
  private static final BluetoothStateChangeReceiver bluetoothReceiver = new BluetoothStateChangeReceiver();

  @SuppressWarnings("FieldCanBeLocal")
  private MainActivityViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    PrintLogger.d(TAG,
        "onCreate # " + getString(R.string.app_version, BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE));
    setContentView(R.layout.activity_main);
    registerBluetoothReceiver();
    viewModel =
        new ViewModelProvider(this, new ViewModelFactory(getApplication(), getNavController()))
            .get(MainActivityViewModel.class);
  }

  @Override
  protected void onDestroy() {
    unregisterReceiver(bluetoothReceiver);
    super.onDestroy();
  }

  private NavController getNavController() {
    NavHostFragment navHostFragment =
        (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    if (navHostFragment == null) {
      throw new IllegalStateException("Failed to find NavHostFragment");
    }
    return navHostFragment.getNavController();
  }

  private void registerBluetoothReceiver() {
    IntentFilter filter = new IntentFilter();
    filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
    registerReceiver(bluetoothReceiver, filter);
  }
}
