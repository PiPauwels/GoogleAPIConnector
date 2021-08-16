package google.api.connector;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.util.store.FileDataStoreFactory;
import java.io.*;

class GoogleAPISocketOAuth2 extends GoogleAPISocket
{    
    private GoogleAPISocketOAuth2()
    {
        super();
    }
    
    @Override public void connect() throws IOException
    {
        if (SCOPES == null)
            throw new IOException ("Scopes not set. Use setScopes(...) before connecting.");
        if (getCredentials() == null)
        {  
            InputStream in = new FileInputStream(new File(System.getProperty("user.home"), GOOGLE_AUTH_DIR + getApplicationFolder() + "/" + getAuthFile()));
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                    .Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in)), SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(getDataDir()))
                    .build();
            setCredentials(new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver.Builder().setPort(8888).build()).authorize("user"));
        }
    }

    static GoogleAPISocket getInstance() 
    {
        if(GoogleAPISocket.instance == null)
        {
            setInstance(new GoogleAPISocketOAuth2());
        }
        return GoogleAPISocket.instance;
    }
}
