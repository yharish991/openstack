package com.openstack.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.openstack.utility.Config;

public class AndroidEmulatorAdapter {
	static Properties properties = Config.readProperties();
	public static final String ANDRIOD_SDK_ADDRESS = properties.getProperty("ANDRIOD_SDK_ADDRESS");
	public static final String ANDROID_ADDRESS = properties.getProperty("ANDROID_ADDRESS").concat(" ");
	public static final String EMULATOR_ADDRESS = properties.getProperty("EMULATOR_ADDRESS").concat(" ");
	public static final String ANDROID_ADB_PATH = properties.getProperty("ANDROID_ADB_PATH").concat(" ");
	public static final String CREATE_ANDROID = properties.getProperty("CREATE_ANDROID").concat(" ");
	public static final String DELETE_ANDROID = properties.getProperty("DELETE_ANDROID").concat(" ");
	public static final String DEVICE_DEFINITION = " ".concat(properties.getProperty("DEVICE_DEFINITION")).concat(" ");
	public static final String ANDROID_AVD = properties.getProperty("ANDROID_AVD").concat(" ");
	public static final String CREATE_ANDROID_TARGET = " ".concat(properties.getProperty("CREATE_ANDROID_TARGET")).concat(" ");
	public static final String WAIT_FOR_ADB = properties.getProperty("WAIT_FOR_ADB");
	public static final String GET_DEVICES = properties.getProperty("GET_DEVICES");
	public static final String DEVICE_STATUS = properties.getProperty("DEVICE_STATUS");
	public static final String GET_DEVICE_STATUS_COMMAND = properties.getProperty("GET_DEVICE_STATUS_COMMAND");
	public static final String INSTALL_COMMAND = properties.getProperty("INSTALL_COMMAND");

	
	/**
	 to create emulator
	 */
	public void createEmulator(String deviceName, String targetId) {
		Runtime runTime = Runtime.getRuntime();
		try {
		System.out.println(ANDRIOD_SDK_ADDRESS.concat(ANDROID_ADDRESS).concat(CREATE_ANDROID).concat(ANDROID_AVD).concat(deviceName).concat(CREATE_ANDROID_TARGET).concat(targetId).concat(DEVICE_DEFINITION));
		Process launchEmulatorProcess = runTime.exec(ANDRIOD_SDK_ADDRESS.concat(ANDROID_ADDRESS).concat(CREATE_ANDROID).concat(ANDROID_AVD).concat(deviceName).concat(CREATE_ANDROID_TARGET).concat(targetId).concat(" --abi default/armeabi-v7a"));
		//.concat(DEVICE_DEFINITION).concat(deviceId)
		System.out.println(launchEmulatorProcess.waitFor());
		} catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * start the emulator.
	 */
	public Process startEmulator(String emulatorName) {
		Runtime runTime = Runtime.getRuntime();
		try {
		Process launchEmulatorProcess = runTime.exec(ANDRIOD_SDK_ADDRESS.concat(EMULATOR_ADDRESS).concat(" -avd ").concat(emulatorName).concat(" -no-window"));
		System.out.println(ANDRIOD_SDK_ADDRESS.concat(EMULATOR_ADDRESS).concat(" -avd ").concat(emulatorName).concat(" -no-window"));
		Thread.sleep(5000);
		Process installFastDroid = runTime.exec(ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" -s "+" emulator-5554 push fastdroid-vnc /data/");
		System.out.println("install fast droid "+ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" "+WAIT_FOR_ADB+" -s "+" emulator-5554 push fastdroid-vnc /data/");
		Thread.sleep(3000);
		System.out.println(installFastDroid.waitFor());
		Process chmodFastDroid = runTime.exec(ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" -s emulator-5554 shell chmod 755 /data/fastdroid-vnc");
		System.out.println(chmodFastDroid.waitFor());
		Thread.sleep(1000);
		System.out.println("chmod fast droid "+ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" -s emulator-5554 shell chmod 755 /data/fastdroid-vnc");
		Process runFastDroid = runTime.exec(ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" -s emulator-5554 shell /data/fastdroid-vnc &");
		System.out.println("run fast droid "+ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" -s emulator-5554 shell /data/fastdroid-vnc &");
		Thread.sleep(2000);
		Process startTelnet = runTime.exec("expect telnetCmd.sh");
		
		return launchEmulatorProcess;
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void retryTelnet() {
		try {
			Runtime runTime = Runtime.getRuntime();
			Process installFastDroid = runTime.exec(ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" -s "+" emulator-5554 push fastdroid-vnc /data/");
			System.out.println("install fast droid "+ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" "+WAIT_FOR_ADB+" -s "+" emulator-5554 push fastdroid-vnc /data/");
			Thread.sleep(3000);
			System.out.println(installFastDroid.waitFor());
			Process chmodFastDroid = runTime.exec(ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" -s emulator-5554 shell chmod 755 /data/fastdroid-vnc");
			System.out.println(chmodFastDroid.waitFor());
			Thread.sleep(1000);
			System.out.println("chmod fast droid "+ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" -s emulator-5554 shell chmod 755 /data/fastdroid-vnc");
			Process runFastDroid = runTime.exec(ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" -s emulator-5554 shell /data/fastdroid-vnc &");
			System.out.println("run fast droid "+ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" -s emulator-5554 shell /data/fastdroid-vnc &");
			Thread.sleep(2000);
			Process startTelnet = runTime.exec("expect telnetCmd.sh");
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteEmulator(String emulatorName) {
		Runtime runTime = Runtime.getRuntime();
		try {
		System.out.println(ANDRIOD_SDK_ADDRESS.concat(ANDROID_ADDRESS).concat(DELETE_ANDROID).concat(ANDROID_AVD).concat(emulatorName));
		Process launchEmulatorProcess = runTime.exec(ANDRIOD_SDK_ADDRESS.concat(ANDROID_ADDRESS).concat(DELETE_ANDROID).concat(ANDROID_AVD).concat(emulatorName));
		} catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * install APK on emulator.
	*/
	
	public void installAPK(String emulatorName, String APKPath) throws IOException {
		Runtime runTime = Runtime.getRuntime();
		Process installAPKOnEmulator = runTime.exec(ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" "+INSTALL_COMMAND+" "+APKPath);
		System.out.println(ANDRIOD_SDK_ADDRESS+ANDROID_ADB_PATH+" "+INSTALL_COMMAND+" "+APKPath);
	}
}
