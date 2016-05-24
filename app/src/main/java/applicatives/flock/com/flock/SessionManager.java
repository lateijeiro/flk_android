package applicatives.flock.com.flock;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SessionManager {
//check if it'slogged in to facebook
    public static boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }



//to use is put it on OnCreate() on some Activity class of printhashKey();
  /*  public void printhashKey(){

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

    }*/

}
