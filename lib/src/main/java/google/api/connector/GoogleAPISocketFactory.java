package google.api.connector;

/**
 * Serves as a factory to create a GoogleAPISocket which can then be used to handle authorisation with a Google API.
 * 
 * @version 1.0
 * @since 16/08/2021
 * @author PiPauwels https://github.com/PiPauwels
 */
public class GoogleAPISocketFactory 
{
    /**
     * Use this connection type in createSocket() to create GoogleAPISocket that can connect with normal user credentials using OAuth2.
     * 
     */
    public static final int CONNECTION_TYPE_USER_ACCOUNT_OAUTH2 = 0;
    
    /**
     * Use this connection type in createSocket() to create GoogleAPISocket that can connect with service account credentials.
     * 
     */
    public static final int CONNECTION_TYPE_SERVICE_ACCOUNT = 1;
    
    private GoogleAPISocketFactory(){}
    
    /**
     * Creates a GoogleAPISocket which can be used to handle authorisation with a Google API.
     * 
     * @param type The type of the GoogleAPISocket to be created. This should be CONNECTION_TYPE_USER_ACCOUNT_OAUTH2 if you want
     *             to connect with normal user credentials using OAuth2. Use CONNECTION_TYPE_SERVICE_ACCOUNT to connect using
     *             a service account. <br/><br/>
     * 
     *             The parameters applicationFolder and authFile tell the system where to locate the keyfile which is needed for authorisation.
     *             this keyfile needs to be located in <USER HOME>/.googleauth/<applicationFolder>/<keyfile.json>.<br/><br/>
     * 
     *             e.g. If you're working on a Windows Machine, your username is "James", you can name the application folder "MyApp" and put your
     *             key in a file called "mycredentials.json". You should then place this keyfile in C:\Users\James\.googleauth\MyApp\" and call 
     *             this method as follows: <br/><br/>
     *             GoogleAPISocketFactory.createSocket(GoogleAPISocketFactory.CONNECTION_TYPE_USER_ACCOUNT_OAUTH2, "My Application", "MyApp", "mycredentials.json");
     * <br/><br/><br/><br/>
     * @param applicationName The name of your application.<br/><br/>
     * @param applicationFolder The application folder containing the keyfile. This folder is located in <USER HOME>/.googleauth/<br/><br/>
     * @param authFile The keyfile used to authenticate with a Google API. This file should be located at <USER HOME>/.googleauth/<applicationFolder>/<br/><br/>
     * @return The GoogleAPISocket to handle authorisation with a Google API.
     */
    public static GoogleAPISocket createSocket(int type, String applicationName, String applicationFolder, String authFile)
    {
        GoogleAPISocket socket;
        switch(type)
        {
            case CONNECTION_TYPE_USER_ACCOUNT_OAUTH2: socket = GoogleAPISocketOAuth2.getInstance(); break;
            case CONNECTION_TYPE_SERVICE_ACCOUNT: socket = GoogleAPISocketServiceAccount.getInstance(); break;
            default: throw new IllegalArgumentException("No valid socket type given. Use createSocket(.. ) with "
                                                      + "CONNECTION_TYPE_USER_ACCOUNT_OAUTH2 or CONNECTION_TYPE_SERVICE_ACCOUNT.");
        }
        socket.setApplicationName(applicationName);
        socket.setApplicationFolder(applicationFolder);
        socket.setAuthFile(authFile);
        return socket;
    }
}
