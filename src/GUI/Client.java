package GUI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket clientSocket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;

	public Client() {
	}

	public void connect(String hostname, int hostport) {
		try {
			this.clientSocket = new Socket(hostname, hostport);
			this.bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		if (this.clientSocket != null) {
			try {
				this.clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void write(String data) {
		try {
			this.bufferedWriter.write(data);
			this.bufferedWriter.newLine();
			this.bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String read() {
		try {
			return this.bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}