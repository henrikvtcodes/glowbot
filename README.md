# RoboLED

[![](https://jitpack.io/v/henrikvtcodes/roboled.svg)](https://jitpack.io/#henrikvtcodes/roboled)

RoboLED is an LED control library for WPILib. It is based on the AddressableLED primitives in WPILib and aims to simplify the use of directly-connected addressable LEDs as well as provide a more complete foundation to build effects on. RoboLED also includes premade patterns to start from.

### Docs

Not yet published.

### How to Use

First you have to add the JitPack repository (if you don't already have it) in your `build.gradle`:

```gradle
repositories {
    maven {
        url "https://jitpack.io/"
    }
    ... // Other repositories
}
```

Next, just add the dependency!  
[![](https://jitpack.io/v/henrikvtcodes/roboled.svg)](https://jitpack.io/#henrikvtcodes/roboled)```gradle
dependecies {
// Make sure to replace "TAG" with the most recent tag, seen above in the JitPack Icon
implementation 'com.github.henrikvtcodes:RoboLED:TAG'
}

```
> ℹ️ Note: We are considering adding support for downloading sub-packages in the future.

## In this package...
- **Addressable LEDs**
    - `LEDStrip`: WPILib's `AdressableLED` but with built in patterns and more!
- **Blinkin**
    - A declarative API for the REV Robotics Blinkin Module
```
