import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileUploader {
    public static void main(String[] args) {
        // Create a sample CSV file
        String csvContent = "Name, Age, City\nJohn, 30, New York\nJane, 25, Los Angeles";
        File csvFile = new File("sample.csv");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(csvContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // FTP settings
        String server = "ftp.example.com";
        int port = 21;
        String user = "username";
        String pass = "password";
        String remoteFilePath = "/upload/sample.csv";

        // Upload file via FTP
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File("sample.csv");

            ftpClient.storeFile(remoteFilePath, localFile);
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
