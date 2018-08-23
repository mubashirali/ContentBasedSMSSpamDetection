/* Mubashir Ali          */
/* Bayesian SMS Spam Filter*/

// Main program

package bayes;

import java.io.*;

public class Bayesian {
	public String classifiedMsg(String stuff) {
		try {

			// Create a new SpamFilter Object
			SpamFilter filter = new SpamFilter();

			filter.trainSpam("spam.txt");
			filter.trainGood("ham.txt");
			filter.finalizeTraining();

			// Ask the filter to analyze it
			boolean spam = filter.analyze(stuff);

			if (spam) {
				System.out.println("This Message is Spam!");
				return "true";
			} else {
				System.out.println("This Message is Ham!");
				return "false";
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "false";
	}
}
