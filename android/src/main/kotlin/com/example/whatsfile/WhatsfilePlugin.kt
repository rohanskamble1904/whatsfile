package com.example.whatsfile

import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentProviderOperation
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodChannel
import java.io.File


/** WhatsfilePlugin */
class WhatsfilePlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var activity : Activity
  private lateinit var context: Context

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "file_sharing_channel")
    channel.setMethodCallHandler(this)
    context = flutterPluginBinding.applicationContext

  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if (call.method == "shareFile") {
      val filePath = call.argument<String>("filePath")
      val mimeType = call.argument<String>("mimeType")
      val phone = call.argument<String>("phone")
      if (filePath != null) {
        if (phone != null) {
          if (mimeType != null) {
            saveContact("My Contact", phone)
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
              shareFile(filePath,phone,mimeType)// Call your method here
            }, 15000)

          }
        }
      }
      result.success(null)
    }
    else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
  override fun onDetachedFromActivityForConfigChanges() {
  }
  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    activity = binding.activity
  }


 override fun onDetachedFromActivity() {
  }
  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activity = binding.activity


  }
  private fun shareFile(filePath: String,phone: String,mime: String) {
    val intent = Intent(Intent.ACTION_SEND)
    val phoneNumber="919967754392"
    intent.type = mime
    val fileURI = FileProvider.getUriForFile(
            context!!, context!!.packageName + ".provider",
            File(filePath)
    )
    intent.putExtra(Intent.EXTRA_STREAM, fileURI)
    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    intent.putExtra("jid", phone + "@s.whatsapp.net")
    intent.setPackage("com.whatsapp.w4b")
    activity.startActivity(intent)
  }
  private fun saveContact(name: String, phone: String) {
    val ops = ArrayList<ContentProviderOperation>()

    ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
            .build())

    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
            .build())

    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
            .build())


    try {
      context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
    } catch (e: Exception) {
      Log.e(TAG, "Failed to save contact", e)
    }
  }
}
