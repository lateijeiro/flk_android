package applicatives.flock.com.flock;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        printhashKey();
    }

    public void printhashKey(){

        // Add code to print out the key hash, it prints on the log down the window when you ran the app
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "applicatives.flock.com.flock",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Flock:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }
}
