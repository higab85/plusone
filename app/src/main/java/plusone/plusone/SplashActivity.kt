package plusone.plusone

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import com.amazonaws.mobile.auth.core.DefaultSignInResultHandler
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.auth.core.IdentityProvider
import com.amazonaws.mobile.auth.core.StartupAuthErrorDetails
import com.amazonaws.mobile.auth.core.StartupAuthResult
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler
import com.amazonaws.mobile.auth.core.signin.AuthException
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration
import com.amazonaws.mobile.auth.ui.SignInActivity

import com.amazonaws.mobile.config.AWSConfiguration

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val identityManager = IdentityManager.getDefaultIdentityManager()
        identityManager.doStartupAuth(this,
            StartupAuthResultHandler { authResults ->
// TODO: uncomment this to have automatic sign-in. Disabled for development
//                if (authResults.isUserSignedIn) {
//                    val provider = identityManager.currentIdentityProvider
//
//                    // If the user was  signed in previously with a provider,
//                    // indicate that to them with a toast.
//                    Toast.makeText(
//                            this@SplashActivity, String.format("Signed in with %s",
//                            provider.displayName), Toast.LENGTH_LONG).show()
//                    goMain(this@SplashActivity)
//                    return@StartupAuthResultHandler
//
//                } else {
                    // Either the user has never signed in with a provider before
                    // or refresh failed with a previously signed in provider.

                    // Optionally, you may want to check if refresh
                    // failed for the previously signed in provider.

//                    val errors = authResults.errorDetails
//
//                    if (errors.didErrorOccurRefreshingProvider()) {
//                        val providerAuthException = errors.providerRefreshException

                        // Credentials for previously signed-in provider could not be refreshed
                        // The identity provider name is available here using:
                        //     providerAuthException.getProvider().getDisplayName()

//                    }

                    doSignIn(IdentityManager.getDefaultIdentityManager())
                    return@StartupAuthResultHandler
//                }
            }, 2000)
    }


    fun doSignIn(identityManager: IdentityManager) {
        identityManager.setUpToAuthenticate(
            this@SplashActivity, object : DefaultSignInResultHandler() {

                override fun onSuccess(activity: Activity, identityProvider: IdentityProvider?) {
                    if (identityProvider != null) {

                        // Sign-in succeeded
                        // The identity provider name is available here using:
                        //     identityProvider.getDisplayName()

                    }

                    // On Success of SignIn go to your startup activity
                    activity.startActivity(Intent(activity, MainActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }

                override fun onCancel(activity: Activity): Boolean {
                    // Return false to prevent the user from dismissing
                    // the sign in screen by pressing back button.
                    // Return true to allow this.

                    return false
                }
        })

        val config = AuthUIConfiguration.Builder()
                .userPools(true)
                // .signInButton(FacebookButton.class)
                // .signInButton(GoogleButton.class)
                .logoResId(R.drawable.plusone)
                .build()

        val context = this@SplashActivity
        SignInActivity.startSignInActivity(context, config)
        this@SplashActivity.finish()
    }

    /** Go to the main activity.  */
    fun goMain(callingActivity: Activity) {
        callingActivity.startActivity(Intent(callingActivity, SuccessActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        callingActivity.finish()
    }

}

