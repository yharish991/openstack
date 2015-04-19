package com.openstack.controller;

import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.telemetry.Meter;
import org.openstack4j.model.telemetry.Statistics;
import org.openstack4j.openstack.OSFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openstack.adapter.AndroidEmulatorAdapter;
import com.openstack.beans.AndroidEmulator;


@RestController
public class GreetingController {
	AndroidEmulatorAdapter androidEmulatorAdapter = new AndroidEmulatorAdapter();

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/createEmulator", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void createUserAndroidEmulator(@PathVariable String userId, @RequestBody DeviceConfig newDeviceConfig, BindingResult validationResult) {
 			System.out.println("New device config type : "+newDeviceConfig.getDeviceType());
 			AndroidEmulatorAdapter aea=new AndroidEmulatorAdapter();
 			aea.createEmulator(newDeviceConfig.getDeviceName(),newDeviceConfig.getDeviceType());
 			/*AndroidEmulator newAndroidEmulator = AndroidEmulator.getDevice(newDeviceConfig.getDeviceType());
 			System.out.println("New device target id : "+newAndroidEmulator);
 			AndroidEmulator.createEmulator(newDeviceConfig.getDeviceName(),newAndroidEmulator.getEmulatorTargetId(),newAndroidEmulator.getEmulatorDeviceId());
 			String opStatus = AndroidEmulator.addEmulator(userId, newDeviceConfig);*/
 			System.out.println("successfully created");
 			//TODO check status of operation
 			
 	}
    
    @RequestMapping(value = "/emulators/{emulatorName}", method = RequestMethod.GET)
 	public ResponseEntity<Map<String,String>> startUserAndroidEmulator(@PathVariable String emulatorName) {
 		//TODO Need to solve emulator naming issue in case of multiple users giving same name. One-way: use emualtorName as userId + emulatorName
 		Map<String, String> responseBody = new HashMap<String, String>();
 		Process emulatorProcess = androidEmulatorAdapter.startEmulator(emulatorName);
 		if (emulatorProcess != null) {
 			Application.runningAndroidEmulator.put(emulatorName, emulatorProcess);
 			responseBody.put("success", "true");
 			try {
 			Field processField = emulatorProcess.getClass().getDeclaredField("pid");
 			processField.setAccessible(true);
 			
 			
				responseBody.put("processId",String.valueOf((int)processField.get(emulatorProcess)));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("***Emulator not launched!!!");
	 			responseBody.put("success", "false");
	 			responseBody.put("errors", "emulator is not launched");
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("***Emulator not launched!!!");
	 			responseBody.put("success", "false");
	 			responseBody.put("errors", "emulator is not launched");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("***Emulator not launched!!!");
	 			responseBody.put("success", "false");
	 			responseBody.put("errors", "emulator is not launched");
			}
 		} else {
 			System.out.println("***Emulator not launched!!!");
 			responseBody.put("success", "false");
 			responseBody.put("errors", "emulator is not launched");
 		}
 		return new ResponseEntity<Map<String,String>>(responseBody, new HttpHeaders(), HttpStatus.OK);
 	}
	
    
    /*
     * to stop the emulator
     * 
     */
    @RequestMapping(value = "/emulators/{emulatorName}", method = RequestMethod.PUT)
 	public ResponseEntity<Map<String,String>> stopUserAndroidEmulator(@PathVariable String emulatorName) {
 		Map<String, String> responseBody = new HashMap<String, String>();
 		//TODO Follow startUserAndroidEmulator approach for addressing emulators
 		//TODO Stop multiple instance of same emulator
 		Process emulatorProcess = Application.runningAndroidEmulator.get(emulatorName);
 		if (emulatorProcess != null) {
 			emulatorProcess.destroy();
 			Application.runningAndroidEmulator.remove(emulatorName);
 			responseBody.put("success", "true");
 			System.out.println("***Emulator "+emulatorName+" stopped!!!");
 		} else {
 			responseBody.put("success", "false");
 			responseBody.put("errors", "emulator is not running");
 			System.out.println("***Emulator not running!!!");
 		}
 		return new ResponseEntity<Map<String,String>>(responseBody, new HttpHeaders(), HttpStatus.OK);
 	}
    
    /*/*
     * 
     * to delete the emulator
     */
    /*@RequestMapping(value = "/emulators/{emulatorName}", method = RequestMethod.DELETE)
 	public ResponseEntity<Map<String,String>> removeUserAndroidEmulator(@PathVariable String userId, @PathVariable String emulatorName){
 		System.out.println("userId"+userId+"emulatorName:"+emulatorName);
 		HttpStatus deleteStatus;
 		HashMap<String,String> responseBody = new HashMap<String, String>();
 		String opStatus = AndroidEmulator.removeEmulator(userId, emulatorName);
 		//TODO check status of operation
 		if (opStatus.equals("success")) {
 			androidEmulatorAdapter.deleteEmulator(emulatorName);
 			deleteStatus = HttpStatus.NO_CONTENT;
 			responseBody.put("success", "true");
 		} else {
 			deleteStatus = HttpStatus.OK;
 			responseBody.put("success", "false");
 			responseBody.put("errors", opStatus);
 		}
 		//TODO check status of command
 		return new ResponseEntity<Map<String,String>>(responseBody, new HttpHeaders(), deleteStatus);
 	}*/

    /*
     * install apk
     * 
     */
    
    /*@RequestMapping(value = "/emulators/{emulatorName}", method = RequestMethod.DELETE)
 	public ResponseEntity<Map<String, String>> installApk(@PathVariable String userId, @PathVariable String emulatorName){
 		System.out.println("userId"+userId+"emulatorName:"+emulatorName);
 		HttpStatus deleteStatus;
 		HashMap<String,String> responseBody = new HashMap<String, String>();
 		String opStatus = AndroidEmulator.removeEmulator(userId, emulatorName);
 		//TODO check status of operation
 		if (opStatus.equals("success")) {
 			androidEmulatorAdapter.deleteEmulator(emulatorName);
 			deleteStatus = HttpStatus.NO_CONTENT;
 			responseBody.put("success", "true");
 		} else {
 			deleteStatus = HttpStatus.OK;
 			responseBody.put("success", "false");
 			responseBody.put("errors", opStatus);
 		}
 		//TODO check status of command
 		return new ResponseEntity<Map<String,String>>(responseBody, new HttpHeaders(), deleteStatus);
 	}*/

    
}
