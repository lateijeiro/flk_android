package applicatives.flock.com.flock;


import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private TextView textDetails;

   private CallbackManager callbackManager;

    private AccessTokenTracker tokenTracker;
    private ProfileTracker profileTracker;

    FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.d("Facebook-Callback", "onSuccess");
        AccessToken accessToken = loginResult.getAccessToken();
        Profile profile = Profile.getCurrentProfile();
        displayWelcomeMessage(profile);



    }

    @Override
    public void onCancel() {
        Log.d("Facebook-Cancel", "onCancel");
    }

    @Override
    public void onError(FacebookException error) {
        Log.d("Facebook-Error", "onCancel");
    }
};

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager=CallbackManager.Factory.create();

        setupTokenTracker();
        setupProfileTracker();

        tokenTracker.startTracking();
        profileTracker.startTracking();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTextDetails(view);
        setupLoginButton(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile=Profile.getCurrentProfile();
        displayWelcomeMessage(profile);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onStop() {
        super.onStop();
        tokenTracker.stopTracking();
        profileTracker.stopTracking();
    }


    //funciones de usuario

    private void displayWelcomeMessage(Profile profile){

        if(SessionManager.isLoggedIn()){
            textDetails.setText("Logged "+profile.getName());
            
        }else{
            textDetails.setText("Logged out");
        }

    }



    private void setupTextDetails(View view) {
        textDetails = (TextView) view.findViewById(R.id.text_details);
    }


    private void setupLoginButton(View view) {
        LoginButton buttonLogin = (LoginButton) view.findViewById(R.id.login_button);
        buttonLogin.setFragment(this);
        //I set up the permissions I want to ask for
        buttonLogin.setReadPermissions("user_friends");
        buttonLogin.registerCallback(callbackManager, callback);
    }

    //it's the callback function that listens everytime the profile of the user changes(change profile pic or whatever)
    private void setupProfileTracker() {

        profileTracker= new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                Log.d("New Profile ", "" + newProfile);
                displayWelcomeMessage(newProfile);

            }

        };
    }

    //it's the callback function that listens everytime the token changes(When I lost connection I receive another token for example and the nthis function executes)
    private void setupTokenTracker() {

        tokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                //log in the console the new token
                Log.d("New token for User ", "" + newToken);
            }
        };

    }


}
