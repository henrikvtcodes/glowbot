# QELib
[![](https://jitpack.io/v/FRC9015/QELib.svg)](https://jitpack.io/#FRC9015/QELib)

QE Lib (short for Questionable Engineering Library) is a Java library for FRC Robotics that expands on the functionality of WPILib. 

### How to Use
Our code is delivered via JitPack, so first you have to add the JitPack repository (if you don't already have it) in your `build.gradle`:  
```gradle
repositories {
    maven {
        url "https://jitpack.io/"
    }
    ... // Other repositories
}
``` 
Next, just add our dependency!  
[![](https://jitpack.io/v/FRC9015/QELib.svg)](https://jitpack.io/#FRC9015/QELib)
```gradle
dependecies {
    // Make sure to replace "TAG" with the most recent tag, seen above in the JitPack Icon
        implementation 'com.github.FRC9015:QELib:TAG'
}
```
> ℹ️ Note: We are looking to add support for downloading sub-packages in the future.

## In this package...
So far, here are the main sections of our package: 
- **Drive**
    - `QEDiffDrive`: This is an enhanced differential drive class for more accurate driving; i.e. driving using units like meters/second and radians/second.
- **LED Control**
    - `LEDStrip`: WPILib's `AdressableLED` but with built in patterns and more!
    - `BlinkinModule`: A Declarative API for the REV Robotics Blinkin

*See the README in each sub-package for more information.*

## Justification
This was created to package up some pretty useful code so that it can be used across seasons. The classes and utilites in this library are here for one of two reasons: 1) it's a neat-and-tidy version of solutions to problems we had while developing robots, or 2) It simplifies repetetive tasks.