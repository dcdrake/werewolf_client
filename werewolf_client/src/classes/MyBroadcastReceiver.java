package classes;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationLibraryConstants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class MyBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("LocationBroadcastReceiver", "onReceive: received location update");
		
		final LocationInfo locationInfo = (LocationInfo) intent.getSerializableExtra(LocationLibraryConstants.LOCATION_BROADCAST_EXTRA_LOCATIONINFO);
		Log.v(TAG, "Lat: " + Float.toString(locationInfo.lastLat) + "\n" + "Long: " + Float.toString(locationInfo.lastLong));
	}
}

