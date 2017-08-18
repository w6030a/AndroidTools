package main;

import java.util.ArrayList;

import cli.Options;
import constants.OptionConstants;
import utils.AdbUtils;

public class AdbHelper {
	static Options options = new Options();
	static {
		options
		.add(OptionConstants.KEY_COMMAND, OptionConstants.USAGE_COMMAND)
		.add(OptionConstants.KEY_DEVICE, OptionConstants.USAGE_DEVICE);
	}
	public static void main(String[] args) {
		parseUserArguments(args);
		
		ArrayList<String> devices = options.getOptionValues(OptionConstants.KEY_DEVICE).size() != 0? options.getOptionValues(OptionConstants.KEY_DEVICE) : AdbUtils.getAllDevices(); // get all devices
		final ArrayList<String> commands = options.getOptionValues(OptionConstants.KEY_COMMAND);
		
		for(final String device : devices) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					for(final String command : commands) {
						System.out.println(String.format("Executing %s on device %s", command, device));
						executeOn(device, command);
					}
				}
			});
			thread.start();
		}
	}

	private static void executeOn(String device, String command) {
		String[] commandFragments = command.split(",");
		String supportedCommand = commandFragments[0];
		switch(supportedCommand) {
			case OptionConstants.COMMAND_KEY_ROOT:
				root(device);
				break;
			case OptionConstants.COMMAND_KEY_DISABLE_VERITY:
				disableVerity(device);
				break;
			case OptionConstants.COMMAND_KEY_REBOOT:
				if(commandFragments.length == 1)
					reboot(device);
				if(commandFragments.length == 2)
					AdbUtils.reboot(device, commandFragments[1]);
				break;
			case OptionConstants.COMMAND_KEY_REMOUNT:
				root(device);
				disableVerity(device);
				reboot(device);
				root(device);
				remount(device);
				break;
			case OptionConstants.COMMAND_KEY_SET_SELINUX_OFF:
				selinuxOff(device);
				break;
			case OptionConstants.COMMAND_KEY_WAIT:
				if(commandFragments.length != 2)
					throw new RuntimeException("Parameters do not match the usage");
				try {
					Thread.sleep(Integer.parseInt(commandFragments[1]));
				} catch (NumberFormatException | InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case OptionConstants.COMMAND_KEY_PUSH:
				if(commandFragments.length != 3)
					throw new RuntimeException("Parameters do not match the usage");
				AdbUtils.push(device, commandFragments[1], commandFragments[2]);
				break;
			case OptionConstants.COMMAND_KEY_PULL:
				AdbUtils.pull(device, commandFragments[1], commandFragments[2]);
				break;
			case OptionConstants.COMMAND_KEY_CHMOD:
				if(commandFragments.length != 3)
					throw new RuntimeException("Parameters do not match the usage");
				AdbUtils.chmod(device, Integer.parseInt(commandFragments[1]), commandFragments[2]);
				break;
			case OptionConstants.COMMAND_KEY_TAP:
				if(commandFragments.length != 5)
					throw new RuntimeException("Parameters do not match the usage");
				int repeats = Integer.parseInt(commandFragments[3]);
				int interval = Integer.parseInt(commandFragments[4]);
				for(int i = 0; i < repeats; i++) {
					try {
						Thread.sleep(interval);
						AdbUtils.tap(device, Integer.parseInt(commandFragments[1]), Integer.parseInt(commandFragments[2]));
					} catch (NumberFormatException | InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			case OptionConstants.COMMAND_KEY_INSTALL:
				if(commandFragments.length != 2)
					throw new RuntimeException("Parameters do not match the usage");
				install(device, commandFragments[1]);
				break;
			case OptionConstants.COMMAND_KEY_UNINSTALL:
				if(commandFragments.length != 2)
					throw new RuntimeException("Parameters do not match the usage");
				uninstall(device, commandFragments[1]);
				break;
			default:
				System.out.println(String.format("Unsupported command entered: %s", command));
		}
	}

	private static void install(String device, String apkFilePath) {
		System.out.println(AdbUtils.install(device, apkFilePath));
	}
	
	private static void uninstall(String device, String packageName) {
		System.out.println(AdbUtils.uninstall(device, packageName));
	}
	
	private static void selinuxOff(String device) {
		System.out.println(AdbUtils.setSELinux(device, 0));
	}

	private static void remount(String device) {
		System.out.println(AdbUtils.remount(device));
	}

	private static void disableVerity(String device) {
		System.out.println(AdbUtils.disableVerity(device));
	}

	private static void root(String device) {
		System.out.println(AdbUtils.root(device));
		System.out.println(AdbUtils.waitForDevice(device));
	}

	private static void reboot(String device) {
		AdbUtils.reboot(device, null);
		int waitSec = 300;
		while(waitSec > 0) {
			try {
				//System.out.println(String.format("Boot Completed Flag: %s", AdbUtils.isDeviceBootCompleted(device)));
				if(AdbUtils.isDeviceBootCompleted(device).replaceAll("\n", "").replaceAll("\r", "").equals("1")) {
					System.out.println("Reboot completed for device: " + device);
					break;
				}
			} catch(NumberFormatException e) {
			}
			
			waitSec -= 5;
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void parseUserArguments(String[] args) {
		options.parse(args);
		if(options.containHelpOption() || !options.validOptions())
			printHelpMenuAndExit();
	}
	
	private static void printHelpMenuAndExit() {
		System.out.println(options.getHelpMenu());
		System.exit(1);
	}
}
