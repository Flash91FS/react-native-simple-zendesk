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

import zendesk.chat.Chat;
import zendesk.chat.ChatConfiguration;
import zendesk.chat.ChatEngine;
import zendesk.chat.ChatProvidersConfiguration;
import zendesk.chat.PreChatFormFieldStatus;
import zendesk.chat.VisitorInfo;
import zendesk.messaging.MessagingActivity;

public class SimpleZendeskModule extends ReactContextBaseJavaModule {

    private static final String TAG = "SimpleZendeskModule";
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

    @ReactMethod
    public void init(String key, String appId) {
        if (appId != null) {
            Chat.INSTANCE.init(reactContext, key, appId);
        } else {
            Chat.INSTANCE.init(reactContext, key);
        }
        Log.e(TAG, "init: Chat.INSTANCE was properly initialized from JS.");
    }

    @ReactMethod
    public void openZendesk(String name, String email, String phone){
        Log.e(TAG, "openZendesk: name = "+name);
        Log.e(TAG, "openZendesk: email = "+email);
        Log.e(TAG, "openZendesk: phone = "+phone);

        if (name == null) {
            name = "";
        }
        if (email == null) {
            email = "";
        }
        if (phone == null) {
            phone = "";
        }

        Toast.makeText(reactContext, "opening Zendesk Chat", Toast.LENGTH_SHORT).show();
        
        try {
//                    Chat.INSTANCE.resetIdentity();
////                    The chat can also be configured using either the ProfileProvider or the ChatProvider class.
//
//                    ProfileProvider profileProvider = Chat.INSTANCE.providers().profileProvider();
//                    ChatProvider chatProvider = Chat.INSTANCE.providers().chatProvider();
//
//                    VisitorInfo visitorInfo = VisitorInfo.builder()
//                            .withName("Bob")
//                            .withEmail("bob@example.com")
//                            .withPhoneNumber("123456") // numeric string
//                            .build();
//                    profileProvider.setVisitorInfo(visitorInfo, null);

            VisitorInfo visitorInfo = VisitorInfo.builder()
//                            .withName("Bob")
//                            .withEmail("bob@example.com")
//                            .withPhoneNumber("123456999") // numeric string
                    .withName(name)
                    .withEmail(email)
                    .withPhoneNumber(phone) // numeric string
                    .build();

            boolean showPreChatForm = false;
            if (name.contentEquals("") || email.contentEquals("")){
                showPreChatForm = true;
            }


            ChatConfiguration chatConfig = ChatConfiguration.builder()
                //    .withAgentAvailabilityEnabled(false)
//                            .withPreChatFormEnabled(false)
                    .withEmailFieldStatus(PreChatFormFieldStatus.REQUIRED)
                    .withNameFieldStatus(PreChatFormFieldStatus.REQUIRED)
                    .withPhoneFieldStatus(PreChatFormFieldStatus.OPTIONAL)
                    .build();

            ChatProvidersConfiguration chatProvidersConfiguration = ChatProvidersConfiguration.builder()
                    .withVisitorInfo(visitorInfo)
                    .withDepartment("Support")
                    .build();

            Log.e(TAG, "onCreate: name = "+visitorInfo.getName());
            Log.e(TAG, "onCreate: email = "+visitorInfo.getEmail());
            Log.e(TAG, "openZendesk: phone = "+visitorInfo.getPhoneNumber());
            Log.e(TAG, "openZendesk: Department = "+chatProvidersConfiguration.getDepartmentName());

            Chat.INSTANCE.setChatProvidersConfiguration(chatProvidersConfiguration);

            // MessagingActivity.builder()
            //         .withEngines(ChatEngine.engine())
            //         .show(context, chatConfig);

            Activity activity = getCurrentActivity();
            if (activity != null) {
                MessagingActivity.builder().withEngines(ChatEngine.engine()).show(activity, chatConfig);
            } else {
                Log.e(TAG, "openZendesk: Could not load getCurrentActivity -- no UI can be displayed without it.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "openZendesk: ERRORR..............");
        }
    }
}
