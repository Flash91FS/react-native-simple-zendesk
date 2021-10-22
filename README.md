# react-native-simple-zendesk

## Getting started

With npm:

`$ npm install react-native-simple-zendesk --save`

or with yarn:

`$ yarn add react-native-simple-zendesk`

## Android

To be able to build for android, make sure to add the following in your root build.gradle file under allProjects -> repositories file of the project.

`maven { url 'https://zendesk.jfrog.io/zendesk/repo' }`

### Note:

If the aove step is not done it may not build for android

## iOS

### Install Pods:

run pod install: `(cd ios; pod install)`

## Usage

```javascript
import SimpleZendesk from "react-native-simple-zendesk";

// to test if its propely integrated the following method
SimpleZendesk.displayMessage("test message");

// Init Zendesk once inside your application:
SimpleZendesk.init("YOUR_ZENDESK_ACCOUNT_KEY", "APP_ID_PROVIDED_BY_ZENDESK");

// Open zendesk chat using this method:
SimpleZendesk.openZendesk(name, email, phone);
```
