/*
 * Copyright (C) 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.quickstep.util;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import android.view.Display;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;

public final class DisplayDensityUtils {

    private static final String TAG = "DisplayDensityUtils";

    private DisplayDensityUtils() {
    }

    public static void toggleDisplayDensity(Context context) {
        final IWindowManager windowManager = WindowManagerGlobal.getWindowManagerService();
        try {
            final int regularDensity = windowManager.getInitialDisplayDensity(
                    Display.DEFAULT_DISPLAY);
            final int compactDensity = Math.max(1, Math.round(regularDensity * 2f / 3f));
            final int currentDensity = windowManager.getBaseDisplayDensity(Display.DEFAULT_DISPLAY);

            if (currentDensity == compactDensity) {
                windowManager.clearForcedDisplayDensityForUser(
                        Display.DEFAULT_DISPLAY, context.getUserId());
            } else {
                windowManager.setForcedDisplayDensityForUser(
                        Display.DEFAULT_DISPLAY, compactDensity, context.getUserId());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to toggle display density", e);
        }
    }
}
