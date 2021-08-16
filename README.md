# GoogleAPIConnector
Small Java library to easily connect to *Google API*, whether your *authenticating* with a *user account using OAuth2 or with a service account*.

### Java Quickstart
This library basically authenticates with the Google API using the method described on the Java Quickstart for Google Developers .
(whether you're connecting to Google Drive, Google Spreadsheets or other API's ; ex. [Google Drive](https://developers.google.com/drive/api/v3/quickstart/java)).

However, these quickstart guides always use user based OAuth2 authentication. Although well documented, I found it very cumbersome to do the same 
thing using a Google Cloud Service Account. There's a [guide specifically for that](https://cloud.google.com/docs/authentication/production), but it does 
not contain any practical examples like the other guides do. It actually took me almost a day to get the same thing up & running with a service account 
and without using deprecated stuff.

### GoogleAPIConnector
So if you run into the same problem, feel free to use this library. Basically, you create a `GoogleAPISocket` with given authentication 
type (user based auth / service account), some parameters that tell the system where to find your keyfile and a list of scopes that identify the 
specific API you want to use (e.g. Google Spreadsheets).

This library will presume you have a keyfile present in `<USER HOME>\.googleauth\<PROJECT FOLDER>\`

### Usage
Let's say we have a service account and downloaded the keyfile from [https://console.cloud.google.com/apis/credentials](https://console.cloud.google.com/apis/credentials) 
to a file called `thermal-springs-1540545.json`. We first need to set some parameters before constructing the `GoogleAPISocket`:

* Connection Type: `Service account`
* Project Name: `My Google Sheets Interface`
* Project Folder: `gsheetsconnection`
* Auth file: `thermal-springs-1540545.json`

On a Windows machine, the library will look for the keyfile with following path: 
`C:\Users\username\.googleauth\gsheetsconnection\thermal-springs-1540545.json`
Or on Linux: `/home/username/.googleauth/gsheetsconnection/thermal-springs-1540545.json`

Be sure to put your keyfile in the right place before using the library. To authenticate with Google API, call:


```
GoogleAPISocket socket = GoogleAPISocketFactory.createSocket(GoogleAPISocketFactory.CONNECTION_TYPE_SERVICE_ACCOUNT, "My Google Sheets Interface", "gsheetsconnection", "thermal-springs-1540545.json");

```

Next, we want to connect to a specific API, e.g. Google Spreadsheets, to read some data from a sheet. This means we first have to load the right scope:

```
import com.google.api.services.sheets.v4.SheetsScopes;
...
socket.setScopes(SheetsScopes.SPREADSHEETS_READONLY)
```

If you need to use more scopes at once, `setScopes(...)` takes up to four parameters. Finally, call:

```
socket.connect();
```

The `socket` will now provide the necessary parameters to connect to a specific API. In our example, call

```
new Sheets.Builder(socket.HTTP_TRANSPORT, socket.JSON_FACTORY, socket.getCredentials())
                .setApplicationName(socket.getApplicationName())
                .build();
```

to get a Sheets object.
