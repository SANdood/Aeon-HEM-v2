Aeon-HEM-v2
===========

My version of the ST device driver for the Aeon HEM, exended to support the V2 version, with more displays

Now reports as Aeon HEMv2+

Plus version adds a secondary display that shows kWh, Watts, Amps and Volts per each leg, instead of the High/Low values. You can toggle back and forth between the displays, but the L1/L2 values are only monitored while viewing that page (they aren't tracked in the background like the High/Low values are).

For current users, you SHOULD be able to publish this for yourself, and then change the device driver from the "Aeon HEMv2" to the "Aeon HEMv2+". Better would (of course) be to delete the old device and create this one anew/fresh.

WARNING
=======
During development/test of this, I found that other "Things" stopped working, including ALL of my MiMolite switches. Powering off the ST hub for a minute seemed to fix the problem, but I have no idea why this device would impact another. Until this gets tracked down, USE AT YOUR OWN RISK!

