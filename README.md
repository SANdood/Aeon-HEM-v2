Aeon-HEM-v2
===========

My version of the ST device driver for the Aeon HEM, exended to support the V2 version, with more displays

Now reports as Aeon HEMv2+

Plus version adds a secondary display that shows kWh, Watts, Amps and Volts per each leg, instead of the High/Low values. You can toggle back and forth between the displays, and all the values are continually tracked - even while they aren't displayed.

WARNING
=======
<b>THIS DEVICE DOES NOT DISPLAY CORRECTLY/WORK ON ANDROID DEVICES!</b>

This is because SmartThings developers seemingly refuse to change the Android app to implement the same (accidental?) UI tile features of the iOS app. I've been told they won't fix it, because the iOS implementation is "incorrect."

I have devoutly refused to change my code to match ST's inconsistent implementations because to do so is to break the cool UI feature that allows for a very efficient way to show two different displays on a single Tile. I'm sorry if you disagree, and feel free to make your own device if you don't like mine - the code is Open Source.

The only work arounds are to remove the color statements in the tiles, or to edit out the text that is sent with the sendEvents for the XXXOne/XXXTwo devices. There are several who have done this already - search the SmartThings community for sources.

It is entirely possible that ST will ultimately "fix" the iOS implementation so that this device no longer works on iOS, but I hope not.

If you'd like to support my position, please keep sending emails to support asking ST to adopt the iOS Tile implementation that parses out the first number of a value string as the numeric "value" for a display widget, even if that string contains non-numeric text - this is what allows HEMv2+ to show "1123\nWatts" and "1123\n09:45AM" in the same widget, depending upon the display toggle mode.


Usage
-----

For current users, you SHOULD be able to publish this for yourself, and then change the device driver from the "Aeon HEMv2" to the "Aeon HEMv2+". Better would (of course) be to delete the old device and create this one anew/fresh.

For NEW users, after you install this code in your IDE and Publish it, you should be able to simply add your HEMv2 to ST and this driver will (should) be automatically selected. If not, you will have to use the IDE under My Devices to edit the device and change its driver to this Aeon HEMv2+ one.

There are 3 preferences associated with this device:
1) Cost per kWh - defaults to $0.16.
2) kWh update delay in seconds - defaults to 60. Note that the HEMv2 doesn't update the TOTAL kWh more frequently than 2 minutes, so you may see L1/L2 values don't add up to the Total displayed
3) Details update delay in seconds - defaults to 30

As of Nov 8 2014, the driver should initialize itself when you install it, and it SHOULD reconfigure the HEMv2 device when you update the preferences. 

Android users will likely find that NOTHING works, because of all the colors used in the tiles. Sorry about that.

Advanced Usage
--------------
If you'd like to have the kWh value, calculated cost, and all the high/low stats reset to zero, simply call the (device).reset() command (or push the reset button). I use a modified version of Pollster to do this at midnight every day; others have the device resetting weekly or even monthly.

You can also post the data to xively - I've added my SmartApp that does this for me to this repository for those interested in getting into that. No assistance promised - use AS-IS or modify.

Since I couldn't find a specification for the name of the Volts attribute, and since some seem to prefer "Voltage" instead of "Volts", the driver now reports BOTH ("Voltage" is silent - you won't see it in the Live Logs, but it's there in events and Activity log).
