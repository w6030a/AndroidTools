package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import adb.AdbExecutor;

public class AdbUtils {
	static AdbExecutor adbExecutor = new AdbExecutor();
	
	public static String root(String deviceID) {
		return adbExecutor.execute(String.format("adb -s %s root",deviceID));
	}
	
	public static String waitForDevice(String deviceID) {
		return adbExecutor.execute(String.format("adb -s %s wait-for-device",deviceID));
	}
	
	public static String disableVerity(String deviceID) {
		return adbExecutor.execute(String.format("adb -s %s disable-verity",deviceID));
	}
	
	public static String remount(String deviceID) {
		return adbExecutor.execute(String.format("adb -s %s remount",deviceID));
	}
	
	public static String setSELinux(String deviceID, int onOffFlag) {
		return adbExecutor.execute(String.format("adb -s %s shell setenforce %d",deviceID, onOffFlag));
	}
	
	public static void reboot(String deviceID, String mode) {
		adbExecutor.execute(String.format("adb -s %s reboot",deviceID) + (mode != null? " " + mode : ""));
	}
	
	public static String isDeviceBootCompleted(String deviceID) {
		return adbExecutor.execute(String.format("adb -s %s shell getprop sys.boot_completed",deviceID));
	}
	
	public static ArrayList<String> getAllDevices() {
		ArrayList<String> devices = adbExecutor.getDevices();
		if(devices.isEmpty())
			throw new RuntimeException("No device found error");
		return devices;
	}
	
	public static ArrayList<String> getFileList(String deviceID, String filePath, String fileRegex) {
		return adbExecutor.getFileList(deviceID, filePath, fileRegex);
	}

	public static void createFolder(String deviceID, String dirPath) {
		adbExecutor.execute(String.format("adb -s %s shell mkdir %s",deviceID, dirPath));
	}
	
	public static void deleteFile(String deviceID, String filePath) {
		adbExecutor.execute(String.format("adb -s %s shell rm %s",deviceID, filePath));
	}
	
	public static String readFileOnDevice(String deviceID, String filePath) {
		return adbExecutor.readFileOnDevice(deviceID, filePath);
	}

	public static void push(String device, String file, String destination) {
		adbExecutor.execute(String.format("adb -s %s push %s %s", device, file, destination));
	}

	public static void pull(String device, String source, String destination) {
		adbExecutor.execute(String.format("adb -s %s pull %s %s", device, source, destination));
	}
	
	public static void reboot(String device, int waitTime, boolean powerOff) {
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		adbExecutor.execute(String.format("adb -s %s shutdown", device) + (powerOff? " -p" : ""));
	}
	
	public static ArrayList<String> resolveRegexFilePath(ArrayList<String> devices, String fileDir, ArrayList<String> fileRegexs) {
 		Set<String> tempFilePaths = new HashSet<String>();
 		
 		for(int i = 0; i < devices.size(); i++) {
 			for(String fileRegex : fileRegexs) {
 				ArrayList<String> fileList = AdbUtils.getFileList(devices.get(i), fileDir, fileRegex);
 				for(int j = 0; j < fileList.size(); j++) {
 					tempFilePaths.add(String.format("%s%s", fileDir, fileList.get(j)));
 					System.out.println(String.format("On Device%1$s: %2$-20s found Job File%3$s: %4$s",i+1 , devices.get(i),j+1 ,fileList.get(j)));
 				}
 			}
 		}
 		
 		return new ArrayList<String>(tempFilePaths);
 	}

	public static void chmod(String device, int permission, String filePath) {
		adbExecutor.execute(String.format("adb -s %s shell chmod %d %s", device, permission, filePath));
	}

	public static void tap(String device, int xPosition, int yPosition) {
		adbExecutor.execute(String.format("adb -s %s shell input touchscreen tap %d %d", device, xPosition, yPosition));
	}

	public static String install(String device, String apkFilePath) {
		return adbExecutor.execute(String.format("adb -s %s install %s", device, apkFilePath));
	}

	public static String uninstall(String device, String packageName) {
		return adbExecutor.execute(String.format("adb -s %s uninstall %s", device, packageName));
	}
}
