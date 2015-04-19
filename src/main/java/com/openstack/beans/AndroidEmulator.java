package com.openstack.beans;

import org.hibernate.validator.constraints.NotBlank;

import com.openstack.controller.DeviceConfig;

/**
 * Represents emulator configuration 
 *
 */
public class AndroidEmulator {
	
	private String emulatorName;
	
	private String emulatorTargetId;
	
	private String emulatorDeviceId;
	
	public String getEmulatorDeviceId() {
		return emulatorDeviceId;
	}
	public void setEmulatorDeviceId(String emulatorDeviceId) {
		this.emulatorDeviceId = emulatorDeviceId;
	}
	public String getEmulatorName() {
		return emulatorName;
	}
	public void setEmulatorName(String emulatorName) {
		this.emulatorName = emulatorName;
	}
	public String getEmulatorTargetId() {
		return emulatorTargetId;
	}
	public void setEmulatorTargetId(String emulatorTargetId) {
		this.emulatorTargetId = emulatorTargetId;
	}
	public static void createEmulator(String str,String str1,String str2)
	{
		
	}
	public static String addEmulator(String user_id,DeviceConfig config)
	{
		return "success";
	}
}
