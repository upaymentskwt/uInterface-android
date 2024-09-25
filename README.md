
# uInterfaceSDK - PaymentSDK Integration Guide

uInterfaceSDK is a versatile SDK designed to simplify the integration of user interface components and payment processing features into your Android applications. It includes a comprehensive PaymentSDK module that facilitates seamless payment processing across multiple projects. This guide will walk you through the steps to integrate and use the SDK in your project.



## Table of Contents

- [Requirements](#requirements)
- [Installation Guide](#installation-guide)
- [Integration Guide](#integration-guide)
- [Usage](#usage)
- [Example](#example)
- [Troubleshooting](#troubleshooting)
- [FAQs](#faqs)



## Requirements

- Android Studio Koala Feature Drop - 2024.1.2 and Later



## Installation Guide

Follow the steps below to integrate the `uinterfacesdk.aar` library into your Android project.

#### Step 1: Download the Sample Project
- Download the sample project provided above and locate the `uinterfacesdk.aar` file.
  - Path: `app/libs/uinterfacesdk.aar`

#### Step 2: Add the `.aar` File to Your Project
- Copy the `uinterfacesdk.aar` file to your project:
  - Path: `your_project/app/libs/`
  - **Note**: If the `libs` folder does not exist in your project, you will need to create it manually.

#### Step 3: Update `build.gradle` (App Level)
- Open your `app/build.gradle` file and add the following dependencies:

    ```gradle
    dependencies {
        // Include the uinterfacesdk.aar library
        implementation files("libs/uinterfacesdk.aar")
        
        // Add additional required libraries
        implementation "androidx.core:core-ktx:1.7.0"
        implementation "com.google.code.gson:gson:2.10.1"
        implementation "com.squareup.okhttp3:okhttp:4.9.1"
        implementation "androidx.webkit:webkit:1.6.0"
    }
    ```

- **Note**: Ensure that you have already implemented the following libraries:

    ```gradle
    dependencies {
        implementation 'androidx.appcompat:appcompat:1.6.1'
        implementation 'com.google.android.material:material:1.9.0'
    }
    ```

#### Step 4: Sync the Project
- After placing the `uinterfacesdk.aar` file and updating the `build.gradle`, sync the project with Gradle files to complete the installation.




## Integration Guide

Follow these steps to integrate and initialize the UInterface SDK into your Android project.

#### Step 1: Create an `Application` Class (if not already created)
- If your project does not have an `Application` class, create one by extending the `Application` class.
- Update your `AndroidManifest.xml` file to define the `Application` class. Add the `name` attribute inside the `<application>` tag:

    ```xml
    <application
        android:name=".YourApplicationClass"
        ... >
        ...
    </application>
    ```

#### Step 2: Import Required Classes
- Import the following classes into your `Application` class:

    ```kotlin
    import com.upayment.upaymentsdk.UInterfaceSDK
    import com.upayment.upaymentsdk.constants.Environment
    ```

#### Step 3: Initialize the SDK in `onCreate` Method
- In the `onCreate()` method of your `Application` class, initialize the SDK with the following code:

    ```kotlin
    @Override
    public void onCreate() {
        super.onCreate()
        
        // Initialize the UInterface SDK
        UInterfaceSDK.initialize(this, API_KEY, Environment.SANDBOX, ENABLE_LOGGING)
    }
    ```

- Replace the following parameters:
  - **API_KEY**: Your provided API key.
  - **Environment**: Use `Environment.SANDBOX` for the sandbox environment or `Environment.PRODUCTION` for the production environment.
  - **ENABLE_LOGGING**: Set to `true` to enable logging or `false` to disable it.

You're all set! The uInterface SDK is now ready for use in your application.




## Usage

Below is a detailed breakdown of the payment processing flow using the uInterface SDK.

- The following code block sets up the parameters for a payment request using the `paymentPayload` builder function:

    ```kotlin
    val eventCharge = paymentPayload {
        productList {
            product {
                description = "Product 1"  // Description of the product
                price = 50.100f             // Product price in float
                name = "KFS"                // Name of the product
                qty = 1                     // Quantity of the product
            }
        }
        order {
            id = "123"                      // Order ID
            reference = "REF-456"            // Reference for the order
            description = "Order Description" // Order description
            currency = "KWD"                 // Currency code
            amount = 50.100f                 // Total amount of the order
        }
        paymentGateway {
            src = "knet"                     // Payment gateway source
        }
        language = "en"                       // Language of the transaction
        isSaveCard = false                    // Save card option
        isWhiteLabeled = isWhiteLabeledStatus // White-labeling status flag (if sandbox then false else true)
        tokens {
            kFast = ""                        // Token for KFast payment method
            creditCard = ""                   // Token for credit card payment
            customerUniqueToken = "889867836" // Unique token for the customer
        }
        reference {
            id = "123459865234889"            // Reference ID for the transaction
        }
        customer {
            uniqueId = "ABCDer22126433"       // Unique ID for the customer
            name = "John Smith"                   // Customer's name
            email = "john.smith@gmail.com"        // Customer's email
            mobile = "6992319220"               // Customer's mobile number
        }
        plugin {
            src = "magento"                   // Plugin source (e.g., Magento)
        }
        notificationUrl = "https://webhook.site/92eb6888-362b-4874-840f-3fff620f7cf4" // URL for payment notifications
        returnUrl = "https://upayments.com/en/"   // URL to return to after successful payment
        cancelUrl = "https://developers.upayments.com/" // URL to redirect if payment is cancelled
        notificationType = "all"                    // Notification type (all, success, etc.)
        // Set other properties as needed
    }
    ```

- To process a payment, call the following method, passing the above `eventCharge` object and a callback to handle the success or error response, Implement the `UInterfaceCallBack` interface in your `Activity` or `Fragment`:

    ```kotlin
    UInterfaceSDK.processPayment(eventCharge, this)
    ```

- The payment result will be received in one of the following two callback methods:

    ```kotlin
    class PaymentActivity : AppCompatActivity(), UInterfaceCallBack, OnClickListener {

    //..... 

    override fun onPaymentProcessed(paymentData: UPaymentData) {
        runOnUiThread(Runnable {
            Toast.makeText(
                this@TestKotlin,
                Gson().toJson(paymentData),   // Displays the payment data in a Toast message
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    override fun onPaymentError(data: String?) {
        runOnUiThread(Runnable {
            Toast.makeText(this@TestKotlin, data.toString(), Toast.LENGTH_SHORT).show()  // Displays the error message in a Toast
        })
    }

    //..... 

    }
    ```

- **Congratulations!** You have successfully set up the charge payment flow using the UInterface SDK.




## Example

For a complete example, check out our sample projects:

- [Android Sample Project](https://github.com/upaymentskwt/uInterface-android)





## Troubleshooting

- **Issue: Unable to find the `uinterfacesdk.aar` file after download.**  
  **Solution:** Ensure you have downloaded the sample project correctly and check the path `app/libs/uinterfacesdk.aar`. If the `libs` folder does not exist, create it manually and place the `.aar` file inside.

- **Issue: The SDK initialization fails with a null pointer exception.**  
  **Solution:** Verify that you have correctly initialized the SDK in the `onCreate()` method of your `Application` class. Ensure that `API_KEY`, `Environment`, and `ENABLE_LOGGING` parameters are set correctly.

- **Issue: Payment processing callback methods are not being called.**  
  **Solution:** Ensure that your class implements the `UInterfaceCallBack` interface and that you are passing the correct context when calling `UInterfaceSDK.processPayment(eventCharge, this)`.

- **Issue: The app crashes when attempting to show a Toast message.**  
  **Solution:** Make sure that the `runOnUiThread` method is being called from a valid UI thread context. Double-check that you are in an `Activity` or `Fragment` when trying to display the Toast.




## FAQs

- **Q: Is this SDK compatible with Java/Kotlin?**  
  **A:** Yes, this SDK is compatible with both languages.

- **Q: Is it possible to handle multiple products in a single payment request?**  
  **A:** Yes, you can add multiple products to the payment payload by adding multiple `product` objects under `productList` when building the payment request.
