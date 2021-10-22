// SimpleZendeskModule.java

package com.reactlibrary.simplezendesk;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class SimpleZendeskModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public SimpleZendeskModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "SimpleZendesk";
    }

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }


    @ReactMethod
    public void displayMessage(String message) {
        Log.e(TAG, "displayMessage: "+message);
        Toast.makeText(reactContext, "Message: "+message, Toast.LENGTH_SHORT).show();
    }

}
