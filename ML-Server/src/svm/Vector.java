package svm;

import java.io.BufferedReader;
import java.io.FileReader;

import net.sf.classifier4J.ClassifierException;
import net.sf.classifier4J.vector.HashMapTermVectorStorage;
import net.sf.classifier4J.vector.TermVectorStorage;
import net.sf.classifier4J.vector.VectorClassifier;

public class Vector {
	private static TermVectorStorage storage;
	private static VectorClassifier vc;

	public Vector() throws Exception {

		storage = new HashMapTermVectorStorage();
		vc = new VectorClassifier(storage);

		trainSpam();
		trainHam();
		System.out.println("--- Vector Trained ---");
	}

	public String getResult(String msg) throws ClassifierException {

		double spam = vc.classify("Spam", msg);
		double ham = vc.classify("Ham", msg);
		System.out.println("Spam: " + spam + "    Ham: " + ham);

		if (spam > ham)
			return "true";
		else
			return "ham";
	}

	public void trainHam() throws ClassifierException {
		String str = "";
		try (BufferedReader reader = new BufferedReader(new FileReader(
				"Ham.txt"))) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				if (line.length() > 0) {
					str += line;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		vc.teachMatch("Ham", str);
	}

	public void trainSpam() throws ClassifierException {
		String str = "";
		try (BufferedReader reader = new BufferedReader(new FileReader(
				"Spam.txt"))) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				if (line.length() > 0) {
					str += line;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		vc.teachMatch("Spam", str);
	}

}
