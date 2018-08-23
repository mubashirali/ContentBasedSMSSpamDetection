/* Mubashir Ali          */
/* Bayesian SMS Spam Filter*/

package serverCall;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import svm.Vector;

import bayes.Bayesian;

public class TCPServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		String clientMsg, reply = null;

		ServerSocket serverSocket = new ServerSocket(8081);
		Socket socket = null;

		DataInputStream dataInputStream = null;
		DataOutputStream dataOutputStream = null;
		Bayesian bayesian = new Bayesian();
		Vector svm = new Vector();
		ArrayList<String> list = new ArrayList<String>();

		while (true) {
			try {
				socket = serverSocket.accept();
				System.out.println("welcome client!" + socket.getInetAddress());

				dataInputStream = new DataInputStream(socket.getInputStream());

				dataOutputStream = new DataOutputStream(
						socket.getOutputStream());

				clientMsg = dataInputStream.readUTF();

				list.add(bayesian.classifiedMsg(clientMsg));
				list.add(svm.getResult(clientMsg));

				if (list.get(0) == "true" && list.get(1) == "true")
					reply = "true";

				else if (list.get(0) == "false" && list.get(1) == "true")
					reply = "true";

				else
					reply = "false";

				dataOutputStream.writeBytes(reply
						+ System.getProperty("line.separator"));

				System.out.println("done " + clientMsg);

				list.clear();
				dataOutputStream.close();

				dataInputStream.close();

				socket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
