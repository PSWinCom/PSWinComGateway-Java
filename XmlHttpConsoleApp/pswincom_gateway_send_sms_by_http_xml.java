import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main {

    public static void main(String[] args) {

        String xml = "<?xml version=\"1.0\"?><SESSION><CLIENT>USERNAMEHERE</CLIENT><PW>PASSWORDHERE</PW><RCPREQ>Y</RCPREQ><MSGLST><MSG><ID>1</ID><TEXT>Test message</TEXT><SND>Bjorn</SND><RCV>4712345678</RCV></MSG></MSGLST></SESSION>";

        String gatewayResponse = sendSmsWithPSWinComGatewayXMLHTTP("http://sms3.pswin.com/sms", xml);
        System.out.println(gatewayResponse);
    }

    public static String sendSmsWithPSWinComGatewayXMLHTTP(String targetURL, String xml)
    {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/xml");

            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(xml.getBytes().length));

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream ());
            wr.writeBytes (xml);
            wr.flush ();
            wr.close ();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}