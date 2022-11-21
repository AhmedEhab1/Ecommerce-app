package com.macaria.app.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {

    public static final int LOCATION_REQUEST_CODE = 1;
    public static final int GPS_REQUEST_CODE = 2;
    public static final int PLACES_REQUEST_CODE = 3;
    public static final int CALL_REQUEST_CODE = 4;
    public static final int Video_REQUEST_CODE = 5;

    private PermissionUtil() {
    }

    public static boolean isPermissionGranted(@NonNull Activity activity) {
        final int read_permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        final int write_permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        final int camera_permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (read_permission == PackageManager.PERMISSION_GRANTED &&
                write_permission == PackageManager.PERMISSION_GRANTED ) {
            return true;
        } else {
            return false;
        }
    }

    public static void askForFileAndCameraPermission(@NonNull Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                2);
    }

    public static boolean isLocationPermissionGranted(@NonNull Activity activity) {

        final int coarse_permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        final int fine_permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (coarse_permission == PackageManager.PERMISSION_GRANTED && fine_permission == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public static void askForLocationPermission(@NonNull Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
    }

    public static boolean isPhoneCallPermissionGranted(@NonNull Activity activity) {
        final int call_permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
        if (call_permission == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    public static void askForPhoneCallPermission(@NonNull Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},CALL_REQUEST_CODE);
    }

    public static boolean isVideoPermissionGranted(@NonNull Activity activity) {

        final int camera_permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        final int audio_permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);

        if (camera_permission == PackageManager.PERMISSION_GRANTED && audio_permission == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public static void askForVideoPermission(@NonNull Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO}, Video_REQUEST_CODE);
    }

}
