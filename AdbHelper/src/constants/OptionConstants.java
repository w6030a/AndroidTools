package constants;

public class OptionConstants {
	public static final String COMMAND_KEY_ROOT = "root";
	public static final String COMMAND_KEY_DISABLE_VERITY = "disable-verity";
	public static final String COMMAND_KEY_REBOOT = "reboot";
	public static final String COMMAND_KEY_REMOUNT = "remount";
	public static final String COMMAND_KEY_PUSH = "push";
	public static final String COMMAND_KEY_PULL = "pull";
	public static final String COMMAND_KEY_CHMOD = "chmod";
	public static final String COMMAND_KEY_INSTALL = "install";
	public static final String COMMAND_KEY_UNINSTALL = "uninstall";
	public static final String COMMAND_KEY_SET_SELINUX_OFF = "set-selinux-off";
	public static final String COMMAND_KEY_WAIT = "wait";
	public static final String COMMAND_KEY_TAP = "tap";

	public static final String KEY_HELP = "-help";
	public static final String KEY_COMMAND = "-command";
	public static final String KEY_DEVICE = "-device";
	
	public static final String USAGE_HELP = "...";
	public static final String USAGE_COMMAND = String.format("(Required) Supported Adb commands: \n\t\t1.%s \n\t\t2.%s \n\t\t3.%s \n\t\t4.%s \n\t\t5.%s \n\t\t6.%s \n\t\t7.%s \n\t\t8.%s. \n\t\t9.%s \n\t\t10.%s \n\t\t11.%s \n\t\t12.%s \n\t\t%s", 
			COMMAND_KEY_ROOT,
			COMMAND_KEY_DISABLE_VERITY, 
			COMMAND_KEY_REBOOT + "[,bootMode]", 
			COMMAND_KEY_REMOUNT, 
			COMMAND_KEY_PUSH + ",localFile,dest",
			COMMAND_KEY_PULL + ",source,dest",
			COMMAND_KEY_CHMOD + ",permession,fileOnDevice",
			COMMAND_KEY_INSTALL + ",apkFile",
			COMMAND_KEY_UNINSTALL + ",packageName",
			COMMAND_KEY_SET_SELINUX_OFF,
			COMMAND_KEY_WAIT + ",milliSecs",
			COMMAND_KEY_TAP + ",x-position,y-position,repeats,interval",
			"EXAMPLE: -command remount push,TestFile,/sdcard/ chmod,755,/sdcard/TestFile wait,10000 reboot,bootloader");
	public static final String USAGE_DEVICE = "(Optional) Device ID(s) from adb devices, used for running adb task on specific device(s).\n\t\tDefault: all devices attached will run the tasks";
	
}
