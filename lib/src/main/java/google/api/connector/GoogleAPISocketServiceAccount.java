package google.api.connector;

import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import java.io.*;

class GoogleAPISocketServiceAccount extends GoogleAPISocket
{    
    private GoogleAPISocketServiceAccount()
    {
        super();
    }
    
    @Override public void connect() throws IOException
    {
        if (SCOPES == null)
            throw new IOException ("Scopes not set. Use setScopes(...) before connecting.");
        if (getCredentials() == null)
        {
            ServiceAccountCredentials scv  = ServiceAccountCredentials.fromStream(new FileInputStream(new File(System.getProperty("user.home"), GOOGLE_AUTH_DIR + getApplicationFolder() + "/" + getAuthFile())));
            GoogleCredentials googleCredentials = scv.createScoped(SCOPES);
            setCredentials(new HttpCredentialsAdapter(googleCredentials));        
        }
    }

    static GoogleAPISocket getInstance() 
    {
        if(GoogleAPISocket.instance == null)
        {
            setInstance(new GoogleAPISocketServiceAccount());
        }
        return GoogleAPISocket.instance;
    }
}
