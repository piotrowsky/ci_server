package pl.edu.agh.kiro.buildsystem;

import org.apache.maven.shared.invoker.InvocationOutputHandler;

/**
 * Custom invocation output handler writing error message to provided StringBuilder instance
 * 
 * @author Lukasz.Gruba
 */
public class CustomOtputHandler implements InvocationOutputHandler {

	/**
	 * Flag indicating that error has occurred
	 */
	private boolean errorOccured;
	
	/**
	 * Error message instance
	 */
	private StringBuilder errorMessageBuilder;
	
	/**
	 * Constructor
	 */
	public CustomOtputHandler(StringBuilder errorMessageBuilder) {
		this.errorMessageBuilder = errorMessageBuilder;
	}
	
	@Override
	public void consumeLine(String line) {
		
		if(line.startsWith("[ERROR]")) {
			errorOccured = true;
		}
		
		if(errorOccured) {
			errorMessageBuilder.append(line);
		}
	}

}
