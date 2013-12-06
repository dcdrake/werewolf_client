package classes;

import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibrary;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
	@Override
	public void onCreate()
	{
		super.onCreate();

		try{
			LocationLibrary.initialiseLibrary(getBaseContext(), 60 * 1000, 2 * 60 * 1000, "com.example.werewolf_client");
		}
		catch (UnsupportedOperationException ex) {
			Log.d("TestApplication", "UnsupportedOperationException thrown - the device doesn't have any location providers");
		}
	}

}
