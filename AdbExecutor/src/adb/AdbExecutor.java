package adb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commandline.CommandLineExecutor;

public class AdbExecutor extends CommandLineExecutor{
	
	public ArrayList<String> getDevices() {
		String command = "adb devices";
		String result = execute(command);

		return parseDevices(result);
	}
	
	public ArrayList<String> getFileList(String deviceID, String filePath, String fileRegex) {
		String command = String.format("adb -s %s shell ls %s",deviceID, filePath);
		String result = execute(command);
		
		ArrayList<String> files = new ArrayList<String>(Arrays.asList(result.split("\n")));
		ArrayList<String> filteredFiles = new ArrayList<String>();
		for(String file : files) {
			if(file.matches(fileRegex.replaceAll("\\*", ".*"))) {
				filteredFiles.add(file);
			}
		}

		return filteredFiles;
	}
	
	public String readFileOnDevice(String deviceID, String filePath) {
		String command = String.format("adb -s %s shell cat %s",deviceID, filePath);
		return execute(command);
	}
	
	private static ArrayList<String> parseDevices(String deviceList) {
		ArrayList<String> devices = new ArrayList<String>();
		String[] deviceIDandStatus = deviceList.split("\n");
		
		if(deviceIDandStatus.length < 1)
			throw new RuntimeException("No working device attached");
		
		Pattern deviceListPattern = Pattern.compile("(?<deviceID>^[0-9a-zA-z]+).+device");
		for(int i = 1; i < deviceIDandStatus.length; i++) {
			Matcher deviceListMatcher = deviceListPattern.matcher(deviceIDandStatus[i]);
			if(deviceListMatcher.find())
				devices.add(deviceListMatcher.group("deviceID"));
		}
		
		return devices;
	}
}
