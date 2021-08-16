package google.api.connector;

import java.io.*;
import java.util.*;
import java.security.GeneralSecurityException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public abstract class GoogleAPISocket 
{
    public static HttpTransport HTTP_TRANSPORT;
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    protected static List<String> SCOPES = null;
    protected static final String GOOGLE_AUTH_DIR = ".googleauth/";
      
    protected GoogleAPISocket() 
    {
        try
        {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } 
        catch (IOException | GeneralSecurityException t)
        {
            System.exit(1);
        }
    }

    /**
     * Handles authorisation with the Google API. This requires that the scopes have been defined already using setScopes(...) 
     * and that a valid keyfile is present in <USER HOME>/.googleauth/<APPLICATION FOLDER>/
     * 
     * @throws IOException No valid keyfile was found or no scopes are defined.
     */
    public abstract void connect() throws IOException;

    /**
     * 
     * @return The name of your application.
     */
    public String getApplicationName() 
    {
        return applicationName;
    }

    void setApplicationName(String applicationName) 
    {
        this.applicationName = applicationName;
    }

    /**
     * 
     * @return The name of the application's folder. This folder is located in <USER HOME>/.googleauth/
     */
    public String getApplicationFolder() 
    {
        return applicationFolder;
    }

    void setApplicationFolder(String applicationFolder) 
    {
        this.applicationFolder = applicationFolder;
        setDataDir(new java.io.File(System.getProperty("user.home"), GOOGLE_AUTH_DIR + getApplicationFolder()));
    }

    protected File getDataDir() 
    {
        return dataDir;
    }

    private void setDataDir(File dataDir) 
    {
        this.dataDir = dataDir;
    }

    /**
     * 
     * @return The name of the keyfile.
     */
    public String getAuthFile() 
    {
        return authFile;
    }

    void setAuthFile(String authFile) 
    {
        this.authFile = authFile;
    }

    /**
     * 
     * @return The credentials necessary to identify with different Google API services. Will return null if connect() has not yet been called.
     */
    public HttpRequestInitializer getCredentials() 
    {
        return credentials;
    }
    
    /**
     * 
     * @return The HttpRequestInitializer necessary to identify with different Google API services. Will return null if connect() has not yet been called.
     */
    public HttpRequestInitializer getHttpRequestInitializer() 
    {
        return credentials;
    }

    protected void setCredentials(HttpRequestInitializer credentials) 
    {
        this.credentials = credentials;
    }
    
    /**
     * 
     * @param scope The scope for connecting to the Google API.
     */
    public void setScopes(String scope)
    {
        SCOPES = Arrays.asList(scope);
    }
    
    /**
     * 
     * @param scope1 The first scope for connecting to the Google API.
     * @param scope2 The second scope for connecting to the Google API.
     */
    public void setScopes(String scope1, String scope2)
    {
        SCOPES = Arrays.asList(scope1, scope2);
    }
    
    /**
     * 
     * @param scope1 The first scope for connecting to the Google API.
     * @param scope2 The second scope for connecting to the Google API.
     * @param scope3 The third scope for connecting to the Google API.
     */
    public void setScopes(String scope1, String scope2, String scope3)
    {
        SCOPES = Arrays.asList(scope1, scope2, scope3);
    }
    
    /**
     * 
     * @param scope1 The first scope for connecting to the Google API.
     * @param scope2 The second scope for connecting to the Google API.
     * @param scope3 The third scope for connecting to the Google API.
     * @param scope4 The fourth scope for connecting to the Google API.
     */
    public void setScopes(String scope1, String scope2, String scope3, String scope4)
    {
        SCOPES = Arrays.asList(scope1, scope2, scope3, scope4);
    }

    protected static void setInstance(GoogleAPISocket instance)
    {
        GoogleAPISocket.instance = instance;
    } 
    
    
    // Data members
    private String applicationName;
    private String applicationFolder;
    private File dataDir;
    private String authFile;
    private HttpRequestInitializer credentials;
    protected static GoogleAPISocket instance;
}
