Aeon-HEM-v2
===========

My version of the ST device driver for the Aeon HEM, exended to support the V2 version, with more displays

Now reports as Aeon HEMv2+

Plus version adds a secondary display that shows kWh, Watts, Amps and Volts per each leg, instead of the High/Low values. You can toggle back and forth between the displays, but the L1/L2 values are only monitored while viewing that page (they aren't tracked in the background like the High/Low values are).

WARNING
=======
During development/test of this, I found that other "Things" stopped working, including ALL of my MiMolite switches. Powering off the ST hub for a minute seemed to fix the problem, but I have no idea why this device would impact another. Until this gets tracked down, USE AT YOUR OWN RISK!

Usage
-----

For current users, you SHOULD be able to publish this for yourself, and then change the device driver from the "Aeon HEMv2" to the "Aeon HEMv2+". Better would (of course) be to delete the old device and create this one anew/fresh.

For NEW users, after you install this code in your IDE and Publish it, you should be able to simply add your HEMv2 to ST and this driver will (should) be automatically selected. If not, you will have to use the IDE under My Devices to edit the device and change its driver to this Aeon HEMv2+ one.

IMPORTANT!
----------
After installation, you need to do TWO things:

1) Push the Configuration button - this configures the reporting of your HEMv2 to send all the details we need an a frequent basis (1 minute updates on the kWh displays, and 20 second updates on everything else).

2) Push the RESET button - this will reset all the values necessary for the display to be updated properly. If you don't do this, you'll never see data; if you do, you should start seeing updates within 60 seconds.

Android users will likely find that NOTHING works, because of all the colors used in the tiles. Sorry about that.

Advanced Usage
--------------
If you'd like to have the kWh value and cost reset to zero, simply call the (device).reset() command. I use a modified version of Pollster to do this at midnight every day; others have the device resetting weekly or even monthly.

You can also post the data to xively - I've added my SmartApp that does this for me to this repository for those interested in getting into that. No assistance promised - use AS-IS or modify.
