package com.astabits.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pinmicro.beaconplusbasesdk.BeaconPlusError;
import com.pinmicro.beaconplusbasesdk.BeaconPlusManager;
import com.pinmicro.beaconplusbasesdk.initialization.InitializationCallback;
import com.pinmicro.beaconplusbasesdk.initialization.SdkMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */
                token.continuePermissionRequest();}

        }).check();

        BeaconPlusManager managerInstance = BeaconPlusManager.getInstance(this);

        BeaconPlusManager.getInstance(this).init(SdkMode.WITHOUT_SERVER, new InitializationCallback() {
            @Override
            public void onInitializationSuccess() {

                try {
                    JSONObject configurationJson = new JSONObject();
                    BeaconPlusManager.getInstance(this).addDeploymentConfiguration(1,configurationJson);
                }
                catch (JSONException | BeaconPlusError e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onInitializationFailure(BeaconPlusError beaconPlusError) {

            }
        });

    }


}
