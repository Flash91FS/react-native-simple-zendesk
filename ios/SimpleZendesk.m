// SimpleZendesk.m

#import "SimpleZendesk.h"
#import <React/RCTLog.h>
#import <ChatSDK/ChatSDK.h>
#import <ChatProvidersSDK/ChatProvidersSDK.h>
#import <MessagingSDK/MessagingSDK.h>


@implementation SimpleZendesk

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(sampleMethod:(NSString *)stringArgument numberParameter:(nonnull NSNumber *)numberArgument callback:(RCTResponseSenderBlock)callback)
{
    // TODO: Implement some actually useful functionality
    callback(@[[NSString stringWithFormat: @"numberArgument: %@ stringArgument: %@", numberArgument, stringArgument]]);
}

RCT_EXPORT_METHOD(displayMessage:(NSString *)message )
{
 RCTLogInfo(@"displayMessage: %@ ", message);
}


RCT_EXPORT_METHOD(init:(NSString *)zenDeskKey appId:(NSString *)appId) {
  if (appId) {
    [ZDKChat initializeWithAccountKey:zenDeskKey appId:appId queue:dispatch_get_main_queue()];
  } else {
    [ZDKChat initializeWithAccountKey:zenDeskKey queue:dispatch_get_main_queue()];
  }
}


RCT_EXPORT_METHOD(openZendesk:(NSString *)name email:(NSString *)email phone:(NSString *)phone)
{
  @try
  {
    dispatch_async(dispatch_get_main_queue(), ^{
      RCTLogInfo(@"Preparing to Open Zendesk for: Name = %@ -- Email = %@ -- Phone = %@", name, email, phone);

      
      ZDKChatFormConfiguration *formConfiguration = [[ZDKChatFormConfiguration alloc] initWithName:ZDKFormFieldStatusRequired
                                                                                             email:ZDKFormFieldStatusRequired
                                                                                       phoneNumber:ZDKFormFieldStatusOptional
                                                                                        department:ZDKFormFieldStatusOptional];
      
      ZDKChatAPIConfiguration *chatAPIConfiguration = [[ZDKChatAPIConfiguration alloc] init];
      chatAPIConfiguration.visitorInfo = [[ZDKVisitorInfo alloc] initWithName:name
                                                                        email:email
                                                                  phoneNumber:phone];
      ZDKChat.instance.configuration = chatAPIConfiguration;

      ZDKChatConfiguration *chatConfiguration = [[ZDKChatConfiguration alloc] init];
      //      chatConfiguration.isPreChatFormEnabled = NO;
            chatConfiguration.preChatFormConfiguration = formConfiguration;
      NSError *error = nil;
      NSArray *engines = @[
        [ZDKChatEngine engineAndReturnError:&error]
      ];
      if (!!error) {
        NSLog(@"[RNZendeskChatModule] Internal Error loading ZDKChatEngine %@", error);
        return;
      }
      
      UIViewController *zendeskVC = [[ZDKMessaging instance] buildUIWithEngines:engines configs:@[chatConfiguration] error:&error];
      
      if (!!error) {
        NSLog(@"[RNZendeskChatModule] Internal Error building ZDKMessagingUI %@",error);
        return;
      }
      NSDictionary *options = [[NSDictionary alloc] initWithObjectsAndKeys:
                                                    @"Dismiss", @"localizedDismissButtonTitle", nil];
      zendeskVC.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithTitle: options[@"localizedDismissButtonTitle"] ?: @"Close"
                                                                                    style: UIBarButtonItemStylePlain
                                                                                   target: self
                                                                                   action: @selector(closeChatVC)];
      
      UINavigationController *chatController = [[UINavigationController alloc] initWithRootViewController: zendeskVC];
      [RCTPresentedViewController() presentViewController:chatController animated:YES completion:nil];
      
////      UIViewController *zendeskVC = [[ZDKMessaging instance] buildUIWithEngines:@[(id <ZDKEngine>) [ZDKChatEngine engineAndReturnError:nil]] configs:@[(id <ZDKConfiguration>) [[ZDKChatConfiguration alloc] init]] error:nil];
//      [[UIApplication sharedApplication].delegate.window.rootViewController presentViewController:zendeskVC animated:YES completion:nil];
      
    });
  }
  @catch(NSException *exception) {
    NSLog(@"Exception Name: %@ ",exception.name);
    NSLog(@"ExceptionReason: %@ ",exception.reason);
  }
}

- (void) closeChatVC {
  [RCTPresentedViewController() dismissViewControllerAnimated:YES completion:nil];
}

@end
