/*
  * Copyright 2013-2017 Amazon.com, Inc. or its affiliates.
  * All Rights Reserved.
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

package com.amazonaws.mobile.auth.ui

import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.auth.core.IdentityProvider
import com.amazonaws.mobile.auth.core.SignInResultHandler
import com.amazonaws.mobile.auth.core.signin.SignInManager
import com.amazonaws.mobile.auth.core.signin.SignInProviderResultHandler

import java.util.HashMap
import java.util.UUID


/**
 * Activity for handling Sign-in with an Identity Provider.
 */
class SignInActivity : AppCompatActivity() {

    /** Reference to the singleton instance of SignInManager.  */
    private var signInManager: SignInManager? = null

    /**
     * SignInProviderResultHandlerImpl handles the final result from sign in.
     */
    private inner class SignInProviderResultHandlerImpl : SignInProviderResultHandler {
        /**
         * Receives the successful sign-in result and starts the main activity.
         *
         * @param provider the identity provider used for sign-in.
         */
        override fun onSuccess(provider: IdentityProvider) {
            Log.i(LOG_TAG, String.format(getString(R.string.sign_in_succeeded_message_format),
                    provider.displayName))

            // The sign-in manager is no longer needed once signed in.
            SignInManager.dispose()
            val signInResultsHandler = signInManager!!.resultHandler

            // Call back the results handler.
            signInResultsHandler.onSuccess(this@SignInActivity, provider)
            finish()
        }

        /**
         * Receives the sign-in result indicating the user canceled and shows a toast.
         *
         * @param provider the identity provider with which the user attempted sign-in.
         */
        override fun onCancel(provider: IdentityProvider) {
            Log.i(LOG_TAG, String.format(getString(R.string.sign_in_canceled_message_format),
                    provider.displayName))
            signInManager!!.resultHandler
                    .onIntermediateProviderCancel(this@SignInActivity, provider)
        }

        /**
         * Receives the sign-in result that an error occurred signing in and shows a toast.
         *
         * @param provider the identity provider with which the user attempted sign-in.
         * @param ex       the exception that occurred.
         */
        override fun onError(provider: IdentityProvider, ex: Exception) {
            Log.e(LOG_TAG, String.format("Sign-in with %s caused an error.", provider.displayName), ex)
            signInManager!!.resultHandler
                    .onIntermediateProviderError(this@SignInActivity, provider, ex)
        }
    }

    /**
     * This method is called when SignInActivity is created.
     * Get the instance of SignInManager and set the callback
     * to be received from SignInManager on signin.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signInManager = SignInManager.getInstance()
        if (signInManager == null) {
            Log.e(LOG_TAG, "Invoke SignInActivity.startSignInActivity() method to create the SignInManager.")
            return
        }
        signInManager!!.setProviderResultsHandler(this, SignInProviderResultHandlerImpl())
        setContentView(R.layout.activity_sign_in)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        signInManager!!.handleRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        signInManager!!.handleActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (signInManager!!.resultHandler.onCancel(this)) {
            super.onBackPressed()
            // Since we are leaving sign-in via back, we can dispose the sign-in manager, since sign-in was cancelled.
            SignInManager.dispose()
        }
    }

    companion object {

        /** Log Tag.  */
        private val LOG_TAG = SignInActivity::class.java.simpleName

        /** Stores the UI Configuration object passed into SignInActivity.  */
        internal var configurationStore = HashMap<String, AuthUIConfiguration>()

        /**
         * Start the SignInActivity that kicks off the authentication flow
         * by initializing the SignInManager.
         *
         * @param context The context from which the SignInActivity will be started
         * @param config  Reference to AuthUIConfiguration object
         */
        fun startSignInActivity(context: Context,
                                config: AuthUIConfiguration) {
            try {
                val uuid = UUID.randomUUID().toString()
                synchronized(configurationStore) {
                    configurationStore.put(uuid, config)
                }
                val intent = Intent(context, SignInActivity::class.java)
                intent.putExtra(SignInView.CONFIGURATION_KEY, uuid)
                intent.putExtra(SignInView.BACKGROUND_COLOR_KEY,
                        config.getSignInBackgroundColor(SignInView.DEFAULT_BACKGROUND_COLOR))
                context.startActivity(intent)
            } catch (exception: Exception) {
                Log.e(LOG_TAG, "Cannot start the SignInActivity. " + "Check the context and the configuration object passed in.", exception)
            }

        }

        /**
         * Start the SignInActivity that kicks off the authentication flow
         * by initializing the SignInManager.
         *
         * @param context The context from which the SignInActivity will be started
         */
        fun startSignInActivity(context: Context) {
            try {
                val intent = Intent(context, SignInActivity::class.java)
                context.startActivity(intent)
            } catch (exception: Exception) {
                Log.e(LOG_TAG, "Cannot start the SignInActivity. " + "Check the context and the configuration object passed in.", exception)
            }

        }
    }
}
