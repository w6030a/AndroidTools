package cli;

import java.util.ArrayList;
import java.util.Arrays;

import constants.OptionConstants;

public class Options {
	ArrayList<Option> options;
	
	public Options() {
		this.options = new ArrayList<Option>();
	}
	
	public Options add(Option option) {
		this.options.add(option);
		return this;
	}

	public Options add(String key, String usage) {
		this.options.add(new Option(key, usage));
		return this;
	}
	
	public ArrayList<String> getKeys() {
		ArrayList<String> keys = new ArrayList<String>();
		for(Option option : options)
			keys.add(option.getKey());
		return keys;
	}
	
	public ArrayList<Option> getOptions() {
		return this.options;
	}
	
	public Option getOption(String key) {
		Option temp = null;
		for(Option option : options)
			if(option.getKey().equals(key))
				temp = option;
		return temp;
	}
	
	public ArrayList<String> getOptionValues(String key) {
		ArrayList<String> values = null;
		for(Option option : options)
			if(option.getKey().equals(key))
				values = option.getValues();
		
		return values;
	}
	
	public void parse(String... args) {
		if(Arrays.asList(args).contains("-help")) {
			options.add(new Option(OptionConstants.KEY_HELP, OptionConstants.USAGE_HELP));
			return;
		}
			
		for(Option option : options) {
			for(int i = 0; i < args.length; i++) {
				if(args[i].equals(option.getKey())) {
					int j = i + 1;
					while(j < args.length && !args[j].startsWith("-")) {
						option.addValue(args[j]);
						j++;
					}
					i = j + 1;
				}
			}
		}
	}
	
	public boolean validOptions() {
		for(Option option : options) {
			if(option.getKey().equals(OptionConstants.KEY_COMMAND) && option.getValues().size() < 1)
				return false;
		}
		return true;
	}

	public boolean containHelpOption() {
		for(String key : getKeys())
			if(key.equals(OptionConstants.KEY_HELP))
				return true;
		return false;
	}
	
	public String getHelpMenu() {
		StringBuilder helpMenu = new StringBuilder();
		for(Option option : getOptions())
			helpMenu.append(String.format("%1$-15s\t%2$s\n", option.getKey(), option.getUsage()));
		
		return helpMenu.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Option option : options) {
			sb.append(option.getKey() + ": ");
			for(String value : option.getValues())
				sb.append(value + ", ");
		}
		return sb.toString().substring(0, sb.toString().length());
	}
}
