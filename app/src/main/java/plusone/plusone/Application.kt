package plusone.plusone

import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobile.auth.core.IdentityManager
import android.support.multidex.MultiDexApplication
import com.amazonaws.mobile.auth.userpools.CognitoUserPoolsSignInProvider;

/**
 * plusone.plusone.Application class responsible for initializing singletons and other common components.
 */
class Application : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        initializeApplication()

    }

    private fun initializeApplication() {

        val awsConfiguration = AWSConfiguration(applicationContext)

        // If IdentityManager is not created, create it
        if (IdentityManager.getDefaultIdentityManager() == null) {
            val identityManager = IdentityManager(applicationContext, awsConfiguration)
            IdentityManager.setDefaultIdentityManager(identityManager)
        }
        // Add Amazon Cognito User Pools as Identity Provider.
        IdentityManager.getDefaultIdentityManager().addSignInProvider(
                CognitoUserPoolsSignInProvider::class.java)

    }

    companion object {
        private val LOG_TAG = Application::class.java.simpleName
    }
}