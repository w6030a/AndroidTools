package commandline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandLineExecutor {
	long elapsedTime;
	
	public String execute(String command) {
		StringBuilder commandResult = new StringBuilder();
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		long startTime;
		
		try {
			startTime = System.currentTimeMillis();
			process = runtime.exec(command);
			
			commandResult.append(getResult(process.getInputStream()));
			if(process.getErrorStream().available() > 0)
				commandResult.append("[Error]\n" + getResult(process.getErrorStream()));
			
			elapsedTime = System.currentTimeMillis() - startTime;
		} catch (IOException e) {
			throw new RuntimeException(String.format("Fail to execute command: %s", command));
		}
		
		return commandResult.toString();
	}
	
	private String getResult(InputStream inputStream) {
		StringBuilder result = new StringBuilder();
		try {
			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			while((line = reader.readLine()) != null)
				result.append(line).append("\n");
		} catch (IOException ios) {
			throw new RuntimeException("Fail to read command output");
		}
		return result.toString();
	}
	
	public long getElapsedTime() {
		return elapsedTime;
	}
}
