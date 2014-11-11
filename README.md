Aeon-HEM-v2
===========

My version of the ST device driver for the Aeon HEM, exended to support the V2 version, with more displays

Now reports as Aeon HEMv2+

Plus version adds a secondary display that shows kWh, Watts, Amps and Volts per each leg, instead of the High/Low values. You can toggle back and forth between the displays, and all the values are continually tracked - even while they aren't displayed.

WARNING
=======
During development/test of this, I found that other "Things" stopped working, including ALL of my MiMolite switches. Powering off the ST hub for a minute seemed to fix the problem, but I have no idea why this device would impact another. Until this gets tracked down, USE AT YOUR OWN RISK!

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
