package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * A wrapper class which takes a logger as constructor argument and offers a
 * PrintStream whose flush method writes the written content to the supplied
 * logger (debug level).
 * <p>
 * Usage:<br>
 * initializing in @BeforeClass of the unit test:
 * 
 * <pre>
 * ToLoggerPrintStream loggerPrintStream = new ToLoggerPrintStream(myLog);
 * RestAssured.config = RestAssured.config().logConfig(new LogConfig(loggerPrintStream.getPrintStream(), true));
 * </pre>
 * 
 * will redirect all log outputs of a ValidatableResponse to the supplied
 * logger:
 * 
 * <pre>
 * resp.then().log().all(true);
 * </pre>
 *
 * @version 1.0 (28.10.2015)
 * @author Heri Bender
 */
public class ToLoggerPrintStream {
	private PrintStream myPrintStream;
	private String str;
	int i;

	/**
	 * @return printStream
	 */
	public PrintStream getPrintStream() {

		if (myPrintStream == null) {
			OutputStream output = new OutputStream() {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				@Override
				public void write(int b) throws IOException {
					baos.write(b);
				}

				@Override
				public void flush() {
					if (i < 1) {
						// myLog.info(this.baos.toString());
						str = this.baos.toString();
						baos = new ByteArrayOutputStream();
					}
					i = i + 1;
				}
			};

			myPrintStream = new PrintStream(output, true); // true: autoflush
															// must be set!
		}
		return myPrintStream;
	}

	/**
	 * Constructor
	 *
	 * @param aLogger
	 */
	public ToLoggerPrintStream(Log aLogger) {
		super();
	}

	public void set() {
		this.i = 0;
		this.str = "";

	}

	public String log(boolean f) {
		if (f) {
			this.str = "";
		}
		return str;

	}
}