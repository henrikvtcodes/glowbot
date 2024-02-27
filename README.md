# Glowbot
Glowbot is an LED control library for FIRST Robotics Competition. Currently, it contains two primary things:  

- A full featured SDK for controlling LEDs with WPILib's built in Addressable LED support
- A slim, declarative class and set of Enums for controlling Rev Blinkin modules. (This was more of a side project and the documentation here will focus on the main sdk)

## Installing
It has been packaged as a vendor dep, so here's the URL:
```
https://orangeunilabs.com/frc/glowbot/Glowbot.json
```
Here's how to add a vendordep from a URL: [Installing 3rd Party Libraries | WPILib Docs](https://docs.wpilib.org/en/stable/docs/software/vscode-overview/3rd-party-libraries.html#installing-libraries)

# Documentation
### Features and Motivation
I decided to create this almost 1 year ago because I felt like the LED support was a bit barebones. Why not take some ideas
from the core of WPILib and provide a more full featured experience for writing LED code. This library was made to be very 
flexible and modular, in that you can use parts of it or the whole thing. The main features include simple things like 
enhanced for loop support, a built in updater, and methods to set a range of LEDs at once. This library also supports the
creation of LED "sections"; i.e. distinct ranges of LEDs that can be individually controlled.

## Credits
Many of the builtin LED patterns and the API were either directly copied from or inspired by code from 
FRC team 5013, the Trobots.