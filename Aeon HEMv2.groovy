/**
 *  Aeon HEMv2+
 *
 *  Copyright 2014 Barry A. Burke
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *
 *  Aeon Home Energy Meter v2 (US)
 *
 *  Author: Barry A. Burke
 *  Contributors: Brock Haymond: UI updates
 *
 *  Genesys: Based off of Aeon Smart Meter Code sample provided by SmartThings (2013-05-30). Built on US model
 *			 may also work on international versions (currently reports total values only)
 *
 *  History:
 * 		
 *	2014-06-13: Massive OverHaul
 *				- Fixed Configuration (original had byte order of bitstrings backwards
 *				- Increased reporting frequency to 10s - note that values won't report unless they change
 *				  (they will also report if they exceed limits defined in the settings - currently just using
 *				  the defaults).
 *				- Added support for Volts & Amps monitoring (was only Power and Energy)
 *				- Added flexible tile display. Currently only used to show High and Low values since last
 *				  reset (with time stamps). 
 *				- All tiles are attributes, so that their values are preserved when you're not 'watching' the
 *				  meter display
 *				- Values are formatted to Strings in zwaveEvent parser so that we don't lose decimal values 
 *				  in the tile label display conversion
 *				- Updated fingerprint to match Aeon Home Energy Monitor v2 deviceId & clusters
 *				- Added colors for Watts and Amps display
 * 				- Changed time format to 24 hour
 *	2014-06-17: Tile Tweaks
 *				- Reworked "decorations:" - current values are no longer "flat"
 *				- Added colors to current Watts (0-18000) & Amps (0-150)
 *				- Changed all colors to use same blue-green-orange-red as standard ST temperature guages
 *	2014-06-18: Cost calculations
 *				- Added $/kWh preference
 *	2014-09-07:	Bug fix & Cleanup
 *				- Fixed "Unexpected Error" on Refresh tile - (added Refresh Capability)
 *				- Cleaned up low values - reset to ridiculously high value instead of null
 *				- Added poll() command/capability (just does a refresh)
 * 	2014-09-19: GUI Tweaks, HEM v1 alterations (from Brock Haymond)
 *				- Reworked all tiles for look, color, text formatting, & readability
 *	2014-09-20: Added HEMv1 Battery reporting (from Brock Haymond)
 *	2014-11-06: Added alternate display of L2 and L2 values instead of Low/High, based on version by Jayant Jhamb
 * 				- 
 */
metadata {
	// Automatically generated. Make future change here.
	definition (
		name: 		"Aeon HEMv2+", 
		namespace: 	"smartthings",
		category: 	"Green Living",
		author: 	"Barry A. Burke"
	) 
	{
    	capability "Energy Meter"
		capability "Power Meter"
		capability "Configuration"
		capability "Sensor"
        capability "Refresh"
        capability "Polling"
        capability "Battery"
        
        attribute "energy", "string"
        attribute "power", "string"
        attribute "volts", "string"
        attribute "amps", "string"
        
        attribute "energyDisp", "string"
        attribute "energyOne", "string"
        attribute "energyTwo", "string"
        
        attribute "powerDisp", "string"
        attribute "powerOne", "string"
        attribute "powerTwo", "string"
        
        attribute "voltsDisp", "string"
        attribute "voltsOne", "string"
        attribute "voltsTwo", "string"
        
        attribute "ampsDisp", "string"
        attribute "ampsOne", "string"
        attribute "ampsTwo", "string"        
        
		command "reset"
        command "configure"
        command "refresh"
        command "poll"
        command "toggleDisplay"
        
// v1		fingerprint deviceId: "0x2101", inClusters: " 0x70,0x31,0x72,0x86,0x32,0x80,0x85,0x60"

		fingerprint deviceId: "0x3101", inClusters: "0x70,0x32,0x60,0x85,0x56,0x72,0x86"
	}

	// simulator metadata
	simulator {
		for (int i = 0; i <= 10000; i += 1000) {
			status "power  ${i} W": new physicalgraph.zwave.Zwave().meterV1.meterReport(
				scaledMeterValue: i, precision: 3, meterType: 33, scale: 2, size: 4).incomingMessage()
		}
		for (int i = 0; i <= 100; i += 10) {
			status "energy  ${i} kWh": new physicalgraph.zwave.Zwave().meterV1.meterReport(
				scaledMeterValue: i, precision: 3, meterType: 33, scale: 0, size: 4).incomingMessage()
		}
        // TODO: Add data feeds for Volts and Amps
	}

	// tile definitions
	tiles {
    
    // Watts row

		valueTile("powerDisp", "device.powerDisp") {
			state (
				"default", 
				label:'${currentValue}', 
            	foregroundColors:[
            		[value: 1, color: "#000000"],
            		[value: 10000, color: "#ffffff"]
            	], 
            	foregroundColor: "#000000",
                backgroundColors:[
					[value: "0 Watts", 		color: "#153591"],
					[value: "3000 Watts", 	color: "#1e9cbb"],
					[value: "6000 Watts", 	color: "#90d2a7"],
					[value: "9000 Watts", 	color: "#44b621"],
					[value: "12000 Watts", 	color: "#f1d801"],
					[value: "15000 Watts", 	color: "#d04e00"], 
					[value: "18000 Watts", 	color: "#bc2323"]
					
				/* For low-wattage homes, use these values
					[value: "0 Watts", color: "#153591"],
					[value: "500 Watts", color: "#1e9cbb"],
					[value: "1000 Watts", color: "#90d2a7"],
					[value: "1500 Watts", color: "#44b621"],
					[value: "2000 Watts", color: "#f1d801"],
					[value: "2500 Watts", color: "#d04e00"],
					[value: "3000 Watts", color: "#bc2323"]
				*/
				]
			)
		}
        valueTile("powerOne", "device.powerOne") {
        	state(
        		"default", 
        		label:'${currentValue}', 
            	foregroundColors:[
            		[value: 1, color: "#000000"],
            		[value: 10000, color: "#ffffff"]
            	], 
            	foregroundColor: "#000000",
                backgroundColors:[
					[value: "0 Watts", 		color: "#153591"],
					[value: "3000 Watts", 	color: "#1e9cbb"],
					[value: "6000 Watts", 	color: "#90d2a7"],
					[value: "9000 Watts", 	color: "#44b621"],
					[value: "12000 Watts", 	color: "#f1d801"],
					[value: "15000 Watts", 	color: "#d04e00"], 
					[value: "18000 Watts", 	color: "#bc2323"]
					
				/* For low-wattage homes, use these values
					[value: "0 Watts", color: "#153591"],
					[value: "500 Watts", color: "#1e9cbb"],
					[value: "1000 Watts", color: "#90d2a7"],
					[value: "1500 Watts", color: "#44b621"],
					[value: "2000 Watts", color: "#f1d801"],
					[value: "2500 Watts", color: "#d04e00"],
					[value: "3000 Watts", color: "#bc2323"]
				*/
				]
			)
        }
        valueTile("powerTwo", "device.powerTwo") {
        	state(
        		"default", 
        		label:'${currentValue}', 
            	foregroundColors:[
            		[value: 1, color: "#000000"],
            		[value: 10000, color: "#ffffff"]
            	], 
            	foregroundColor: "#000000",
                backgroundColors:[
					[value: "0 Watts", 		color: "#153591"],
					[value: "3000 Watts", 	color: "#1e9cbb"],
					[value: "6000 Watts", 	color: "#90d2a7"],
					[value: "9000 Watts", 	color: "#44b621"],
					[value: "12000 Watts", 	color: "#f1d801"],
					[value: "15000 Watts", 	color: "#d04e00"], 
					[value: "18000 Watts", 	color: "#bc2323"]
					
				/* For low-wattage homes, use these values
					[value: "0 Watts", color: "#153591"],
					[value: "500 Watts", color: "#1e9cbb"],
					[value: "1000 Watts", color: "#90d2a7"],
					[value: "1500 Watts", color: "#44b621"],
					[value: "2000 Watts", color: "#f1d801"],
					[value: "2500 Watts", color: "#d04e00"],
					[value: "3000 Watts", color: "#bc2323"]
				*/
				]
			)
        }

	// Power row
    
		valueTile("energyDisp", "device.energyDisp") {
			state("default", label: '${currentValue}', foregroundColor: "#000000", backgroundColor:"#ffffff")
		}
        valueTile("energyOne", "device.energyOne") {
        	state("default", label: '${currentValue}', foregroundColor: "#000000", backgroundColor:"#ffffff")
        }        
        valueTile("energyTwo", "device.energyTwo") {
        	state("default", label: '${currentValue}', foregroundColor: "#000000", backgroundColor:"#ffffff")
        }
        
    // Volts row
    
        valueTile("voltsDisp", "device.voltsDisp") {
        	state(
        		"default", 
        		label: '${currentValue}', 
        		backgroundColors:[
            		[value: "115.6 Volts", 	color: "#bc2323"],
                	[value: "117.8 Volts", 	color: "#D04E00"],
                	[value: "120.0 Volts", 	color: "#44B621"],
                	[value: "122.2 Volts", 	color: "#D04E00"],
                	[value: "124.4 Volts", 	color: "#bc2323"]
            	]
            )
        }
        valueTile("voltsOne", "device.voltsOne", decoration: "flat") {
        	state "default", label:'${currentValue}'
        }
        valueTile("voltsTwo", "device.voltsTwo", decoration: "flat") {
        	state "default", label:'${currentValue}'
        }
    
    // Amps row
    
        valueTile("ampsDisp", "device.ampsDisp") {
        	state (
        		"default", 
        		label: '${currentValue}' , 
        		foregroundColor: "#000000", 
    			color: "#000000", 
    			backgroundColors:[
					[value: "0 Amps", 	color: "#153591"],
					[value: "25 Amps", 	color: "#1e9cbb"],
					[value: "50 Amps", 	color: "#90d2a7"],
					[value: "75 Amps", 	color: "#44b621"],
					[value: "100 Amps", color: "#f1d801"],
					[value: "125 Amps", color: "#d04e00"], 
					[value: "150 Amps", color: "#bc2323"]
				]
			)
        }
        valueTile("ampsOne", "device.ampsOne") {
        	state(
        		"default",
        		label:'${currentValue}',
        		foregroundColor: "#000000", 
    			color: "#000000", 
    			backgroundColors:[
					[value: "0 Amps", 	color: "#153591"],
					[value: "25 Amps", 	color: "#1e9cbb"],
					[value: "50 Amps", 	color: "#90d2a7"],
					[value: "75 Amps", 	color: "#44b621"],
					[value: "100 Amps", color: "#f1d801"],
					[value: "125 Amps", color: "#d04e00"], 
					[value: "150 Amps", color: "#bc2323"]
				]
			)
        }
        valueTile("ampsTwo", "device.ampsTwo") {
        	state(
        		"default", 
        		label:'${currentValue}',
        		foregroundColor: "#000000", 
    			color: "#000000", 
    			backgroundColors:[
					[value: "0 Amps", 	color: "#153591"],
					[value: "25 Amps", 	color: "#1e9cbb"],
					[value: "50 Amps", 	color: "#90d2a7"],
					[value: "75 Amps", 	color: "#44b621"],
					[value: "100 Amps", color: "#f1d801"],
					[value: "125 Amps", color: "#d04e00"], 
					[value: "150 Amps", color: "#bc2323"]
				]
			)        		
        }
    
    // Controls row
    
		standardTile("reset", "device.energy", inactiveLabel: false) {
			state "default", label:'reset', action:"reset", icon: "st.Health & Wellness.health7"
		}
		standardTile("refresh", "device.power", inactiveLabel: false) {
			state "default", label:'refresh', action:"refresh.refresh", icon:"st.secondary.refresh-icon"
		}
		standardTile("configure", "device.power", inactiveLabel: false) {
			state "configure", label:'', action:"configuration.configure", icon:"st.secondary.configure"
		}
		standardTile("toggle", "device.power", inactiveLabel: false) {
			state "default", label: "toggle", action: "toggleDisplay", icon: "st.motion.motion.inactive"
		}
		/* HEMv1 has a battery; v2 is line-powered */
		 valueTile("battery", "device.battery", decoration: "flat") {
			state "battery", label:'${currentValue}% battery', unit:""
		}

// HEM Version Configuration only needs to be done here - comments to choose what gets displayed

		main (["energyDisp","energyTwo",
			"ampsDisp","voltsDisp",				// Comment out this one for HEMv1
			"powerDisp"
			])
		details([
			"energyOne","energyDisp","energyTwo",
			"powerOne","powerDisp","powerTwo",
			"ampsOne","ampsDisp","ampsTwo",			// Comment out these two lines for HEMv1
			"voltsOne","voltsDisp","voltsTwo",		// Comment out these two lines for HEMv1
			"reset","refresh","toggle",
		//	"battery",					// Include this for HEMv1	
			"configure"
		])
	}
    preferences {
    	input "kWhCost", "string", title: "\$/kWh (0.16)", defaultValue: "0.16" as String
    }
}

def parse(String description) {
//	log.debug "Parse received ${description}"
	def result = null
	def cmd = zwave.parse(description, [0x31: 1, 0x32: 1, 0x60: 3])
	if (cmd) {
		result = createEvent(zwaveEvent(cmd))
	}
	if (result) { 
		log.debug "Parse returned ${result?.descriptionText}"
		return result
	} else {
	}
}

def zwaveEvent(physicalgraph.zwave.commands.meterv1.MeterReport cmd) {
//	log.debug "zwaveEvent received ${cmd}"
    
    def dispValue
    def newValue
	def timeString = new Date().format("h:mm a", location.timeZone)
    
    if (cmd.meterType == 33) {
		if (cmd.scale == 0) {
        	newValue = Math.round(cmd.scaledMeterValue * 10) / 10
        	if (newValue != state.energyValue) {
    			dispValue = String.format("%5.2f",newValue)+"\nkWh"
                sendEvent(name: "energyDisp", value: dispValue as String, unit: "")
                state.energyValue = newValue
                BigDecimal costDecimal = newValue * ( kWhCost as BigDecimal )
                def costDisplay = String.format("%5.2f",costDecimal)
                state.costDisp = "Cost\n\$"+costDisplay
                if (state.display == 1) { sendEvent(name: "energyTwo", value: state.costDisp, unit: "") }
                [name: "energy", value: newValue, unit: "kWh"]
            }
		} 
		else if (cmd.scale == 1) {
            newValue = cmd.scaledMeterValue
            if (newValue != state.energyValue) {
    			dispValue = String.format("%5.2f",newValue)+"\nkVAh"
                sendEvent(name: "energyDisp", value: dispValue as String, unit: "")
                state.energyValue = newValue
				[name: "energy", value: newValue, unit: "kVAh"]
            }
		}
		else if (cmd.scale==2) {				
        	newValue = Math.round( cmd.scaledMeterValue )		// really not worth the hassle to show decimals for Watts
        	if (newValue != state.powerValue) {
    			dispValue = newValue+"\nWatts"
                sendEvent(name: "powerDisp", value: dispValue as String, unit: "")
                
                if (newValue < state.powerLow) {
                	dispValue = newValue+"\n"+timeString
                	if (state.display == 1) { sendEvent(name: "powerOne", value: dispValue as String, unit: "")	}
                    state.powerLow = newValue
                    state.powerLowDisp = dispValue
                }
                if (newValue > state.powerHigh) {
                	dispValue = newValue+"\n"+timeString
                	if (state.display == 1) { sendEvent(name: "powerTwo", value: dispValue as String, unit: "")	}
                    state.powerHigh = newValue
                    state.powerHighDisp = dispValue
                }
                state.powerValue = newValue
                [name: "power", value: newValue, unit: "W"]
            }
		}
 	}
    else if (cmd.meterType == 161) {
    	if (cmd.scale == 0) {
        	newValue = cmd.scaledMeterValue
        	if (newValue != state.voltsValue) {
    			dispValue = String.format("%5.2f", newValue)+"\nVolts"
                sendEvent(name: "voltsDisp", value: dispValue as String, unit: "")

                if (newValue < state.voltsLow) {
                	dispValue = String.format("%5.2f", newValue)+"\n"+timeString                	
                	if (state.display == 1) { sendEvent(name: "voltsOne", value: dispValue as String, unit: "")	}
                    state.voltsLow = newValue
                    state.voltsLowDisp = dispValue
                }
                if (newValue > state.voltsHigh) {
                    dispValue = String.format("%5.2f", newValue)+"\n"+timeString
                	if (state.display == 1) { sendEvent(name: "voltsTwo", value: dispValue as String, unit: "") }
                    state.voltsHigh = newValue
                    state.voltsHighDisp = dispValue
                }                
                state.voltsValue = newValue
				[name: "volts", value: newValue, unit: "V"]
            }
        }
        else if (cmd.scale==1) {
        	newValue = cmd.scaledMeterValue
        	if (newValue != state.ampsValue) {
    			dispValue = String.format("%5.2f", newValue)+"\nAmps"
                sendEvent(name: "ampsDisp", value: dispValue as String, unit: "")
                
                if (newValue < state.ampsLow) {
                	dispValue = String.format("%5.2f", newValue)+"\n"+timeString
                	if (state.display == 1) { sendEvent(name: "ampsOne", value: dispValue as String, unit: "") }
                    state.ampsLow = newValue
                    state.ampsLowDisp = dispValue
                }
                if (newValue > state.ampsHigh) {
                	dispValue = String.format("%5.2f", newValue)+"\n"+timeString
                	if (state.display == 1) { sendEvent(name: "ampsTwo", value: dispValue as String, unit: "") }
                    state.ampsHigh = newValue
                    state.ampsHighDisp = dispValue
                }                
                state.ampsValue = newValue
				[name: "amps", value: newValue, unit: "A"]
            }
        }
    }           
}

def zwaveEvent(physicalgraph.zwave.commands.multichannelv3.MultiChannelCmdEncap cmd) {
// 	log.debug "MultiChannelCmdEncap $cmd"
//  log.debug "cmd.commandClass == ${cmd.commandClass} // cmd.command == ${cmd.command} // cmd.parameter == ${cmd.parameter} // cmd.sourceEndPoint == ${cmd.sourceEndPoint}"

	def dispValue
	def newValue
	def formattedValue
	
	if (state.display == 2) {

    	if (cmd.commandClass == 50) {    
    		def encapsulatedCommand = cmd.encapsulatedCommand([0x30: 1, 0x31: 1]) // can specify command class versions here like in zwave.parse
			if (encapsulatedCommand) {
				if (cmd.sourceEndPoint == 1) {

            // -- Test
//            log.debug encapsulatedCommand
//            log.debug zwaveEvent(encapsulatedCommand)
            //--- Test           

					if (encapsulatedCommand.scale == 2 ) {
						newValue = Math.round(encapsulatedCommand.scaledMeterValue)
						formattedValue = newValue as String
						dispValue = "${formattedValue}\nWatts"
						[name: "powerOne", value:dispValue, unit: "", descriptionText: "L1 Power Draw ${formattedValue} W"]
					} 
					else if (encapsulatedCommand.scale == 0 ){
						newValue = Math.round(encapsulatedCommand.scaledMeterValue * 10) / 10
						formattedValue = String.format("%5.2f", newValue)
						dispValue = "${formattedValue}\nkWh"
						[name: "energyOne", value: dispValue, unit: "", descriptionText: "L1 Energy Usage ${formattedValue} kWh"]
					} 
					else if (encapsulatedCommand.scale == 5 ) {
						newValue = encapsulatedCommand.scaledMeterValue
						formattedValue = String.format("%5.2f", newValue)
						dispValue = "${formattedValue}\nAmps"
						[name: "ampsOne", value: dispValue, unit: "", descriptionText: "L1 Current Draw ${formattedValue} A"]
                	} 
                	else if (encapsulatedCommand.scale == 4 ){
                		newValue = encapsulatedCommand.scaledMeterValue
						formattedValue = String.format("%5.2f", newValue)
						dispValue = "${formattedValue}\nVolts"
						[name: "voltsOne", value:dispValue, unit: "", descriptionText: "L1 Voltage ${formattedValue} Volts"]
                	}               
				} 
				else if (cmd.sourceEndPoint == 2) {
					if (encapsulatedCommand.scale == 2 ){
						newValue = Math.round(encapsulatedCommand.scaledMeterValue)
						formattedValue = newValue as String
						dispValue = "${formattedValue}\nWatts"
						[name: "powerTwo", value:dispValue, unit: "", descriptionText: "L2 Power Draw ${formattedValue} W"]
					} 
					else if (encapsulatedCommand.scale == 0 ){
						newValue = Math.round(encapsulatedCommand.scaledMeterValue * 10) / 10
						formattedValue = String.format("%5.2f", newValue)
						dispValue = "${formattedValue}\nkWh"
						[name: "energyTwo", value: dispValue, unit: "", descriptionText: "L2 Energy Usage ${formattedValue} kWh"]
					} 
					else if (encapsulatedCommand.scale == 5 ){
                		newValue = encapsulatedCommand.scaledMeterValue
						formattedValue = String.format("%5.2f", newValue)
						dispValue = "${formattedValue}\nAmps"
						[name: "ampsTwo", value: dispValue, unit: "", descriptionText: "L2 Current Draw ${formattedValue} A"]
		    		} 
		    		else if (encapsulatedCommand.scale == 4 ){
                		newValue = encapsulatedCommand.scaledMeterValue
						formattedValue = String.format("%5.2f", newValue)
						dispValue = "${formattedValue}\nVolts"
						[name: "voltsTwo", value: dispValue as String, unit: "", descriptionText: "L2 Voltage ${formattedValue} Volts"]
                	}               			
				}
			}
		}
	}
}


def zwaveEvent(physicalgraph.zwave.commands.batteryv1.BatteryReport cmd) {
	def map = [:]
	map.name = "battery"
	map.unit = "%"
	if (cmd.batteryLevel == 0xFF) {
		map.value = 1
		map.descriptionText = "${device.displayName} battery is low"
		map.isStateChange = true
	} 
	else {
		map.value = cmd.batteryLevel
	}
	log.debug map
	return map
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
	// Handles all Z-Wave commands we aren't interested in
    log.debug "Unhandled event ${cmd}"
	[:]
}

def refresh() {
	delayBetween([
		zwave.meterV2.meterGet(scale: 0).format(),
		zwave.meterV2.meterGet(scale: 2).format()
	])
}

def poll() {
	refresh()
}

def toggleDisplay() {
	if (state.display) {
		if (state.display == 1) { state.display = 2 }
		else { state.display = 1 }
	}
	else { state.display = 1 }
	
	resetDisplay()
}

def resetDisplay() {
	if ( state.display == 1 ) {
    	sendEvent(name: "voltsOne", value: state.voltsLowDisp, unit: "")
    	sendEvent(name: "ampsOne", value: state.ampsLowDisp, unit: "")    
		sendEvent(name: "powerOne", value: state.powerLowDisp, unit: "")     
    	sendEvent(name: "energyOne", value: state.lastResetTime, unit: "")
    	sendEvent(name: "voltsTwo", value: state.voltsHighDisp, unit: "")
    	sendEvent(name: "ampsTwo", value: state.ampsHighDisp, unit: "")
    	sendEvent(name: "powerTwo", value: state.powerHighDisp, unit: "")
    	sendEvent(name: "energyTwo", value: state.costDisp, unit: "")    	
	}
	else {
    	sendEvent(name: "voltsOne", value: "", unit: "")
    	sendEvent(name: "ampsOne", value: "", unit: "")    
		sendEvent(name: "powerOne", value: "", unit: "")     
    	sendEvent(name: "energyOne", value: "", unit: "")	
		sendEvent(name: "voltsTwo", value: "", unit: "")
    	sendEvent(name: "ampsTwo", value: "", unit: "")
    	sendEvent(name: "powerTwo", value: "", unit: "")
    	sendEvent(name: "energyTwo", value: "", unit: "")
	}
}

def reset() {
	log.debug "${device.name} reset"

    state.powerHigh = 0
    state.powerHighDisp = ""
    state.powerLow = 99999
    state.powerLowDisp = ""
    state.ampsHigh = 0
    state.ampsHighDisp = ""
    state.ampsLow = 999
    state.ampsLowDisp = ""
    state.voltsHigh = 0
    state.voltsHighDisp = ""
    state.voltsLow = 999
    state.voltsLowDisp = ""
    
    if (!state.display) { state.display = 1 }

    def dateString = new Date().format("m/d/YY", location.timeZone)
    def timeString = new Date().format("h:mm a", location.timeZone)    
	state.lastResetTime = "Since\n"+dateString+"\n"+timeString
	state.costDisp = "Cost\n--"
	
    reset Display()
    sendEvent(name: "energyDisp", value: "", unit: "")
    sendEvent(name: "powerDisp", value: "", unit: "")	
    sendEvent(name: "ampsDisp", value: "", unit: "")
    sendEvent(name: "voltsDisp", value: "", unit: "")

// No V1 available
	def cmd = delayBetween( [
		zwave.meterV2.meterReset().format(),
		zwave.meterV2.meterGet(scale: 0).format()
	])
    cmd
}

def configure() {
	// TODO: Turn on reporting for each leg of power - display as alternate view (Currently those values are
    //		 returned as zwaveEvents...they probably aren't implemented in the core Meter device yet.

	def cmd = delayBetween([
		zwave.configurationV1.configurationSet(parameterNumber: 3, size: 1, scaledConfigurationValue: 0).format(),		// Enable selective reporting
		zwave.configurationV1.configurationSet(parameterNumber: 4, size: 2, scaledConfigurationValue: 30).format(),		// Don't send unless watts have increased by 30
		zwave.configurationV1.configurationSet(parameterNumber: 5, size: 2, scaledConfigurationValue: 30).format(),		// Don't send unless watts have increased by 30
		zwave.configurationV1.configurationSet(parameterNumber: 6, size: 2, scaledConfigurationValue: 30).format(),		// Don't send unless watts have increased by 30
        zwave.configurationV1.configurationSet(parameterNumber: 8, size: 1, scaledConfigurationValue: 5).format(),		// Or by 10% (these 3 are the default values
        zwave.configurationV1.configurationSet(parameterNumber: 9, size: 1, scaledConfigurationValue: 5).format(),		// Or by 10% (these 3 are the default values
        zwave.configurationV1.configurationSet(parameterNumber: 10, size: 1, scaledConfigurationValue: 5).format(),		// Or by 10% (these 3 are the default values
		zwave.configurationV1.configurationSet(parameterNumber: 101, size: 4, scaledConfigurationValue: 6145).format(),   //  Combined and Clamp power in kWh
		zwave.configurationV1.configurationSet(parameterNumber: 111, size: 4, scaledConfigurationValue: 60).format(), 	// Every 60 Seconds
		zwave.configurationV1.configurationSet(parameterNumber: 102, size: 4, scaledConfigurationValue: 14).format(),   // Total Voltage, Amps Watts
		zwave.configurationV1.configurationSet(parameterNumber: 112, size: 4, scaledConfigurationValue: 20).format(), 	// every 20 seconds
		zwave.configurationV1.configurationSet(parameterNumber: 103, size: 4, scaledConfigurationValue: 1770240).format(),	// Amps, Voltage & Power for each clamp
		zwave.configurationV1.configurationSet(parameterNumber: 113, size: 4, scaledConfigurationValue: 20).format() 	// every 20 seconds
	])
	log.debug cmd

	cmd
}
