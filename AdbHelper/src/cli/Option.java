package cli;

import java.util.ArrayList;

public class Option {
	String key;
	String usage;
	ArrayList<String> values = new ArrayList<String>();;
	
	public Option(String key) {
		this.key = key;
	}
	
	public Option(String key, String usage) {
		this(key);
		this.usage = usage;
	}
	
	public Option(String key, String usage, String[] values) {
		this(key, usage);
		for(String value : values) {
			this.values.add(value);
		}
	}
	
	public String getKey() {
		return this.key;
	}
	
	public Option addValue(String value) {
		this.values.add(value);
		return this;
	}
	
	public ArrayList<String> getValues() {
		return this.values;
	}
	
	public String getUsage() {
		return this.usage;
	}
}
