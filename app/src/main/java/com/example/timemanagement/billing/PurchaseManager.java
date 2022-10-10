package com.example.timemanagement.billing;

import android.app.Activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseManager {

	/** the helper communicate with Google Play */
//	private static IabHelper mHelper;

	/** the activity which is source of buy event */
	private static Activity activity;

	public static final Map<String, String> productIds = new HashMap<>();
	//private static final String LICENSE_KEY = BuildConfig.licenceKey; // PUT YOUR MERCHANT KEY HERE;
//	public static IapConnector iapConnector;


	static {
		productIds.put("Red", "red");
		productIds.put("Orange", "orange");
		productIds.put("Yellow", "yellow");
		productIds.put("Green", "green");
		productIds.put("Teal", "teal");
		productIds.put("Blue","blue");
		productIds.put("Purple","purple");
	}


	/** (arbitrary) request code for the purchase flow */
    public static final int RC_REQUEST = 10001;

    static final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAohX0RVrngXOXa1fYY5dOerZGf4U3WBg1H8teg/JNfTTI4nOhZnEVS2sGtZvOvWX7Sl+KExzAC3SldME7aaUtoUseqfldLoXrg0e4obwWmwLtJXSrGWHKTbXDDPpHf6Ej+28GeCSFA8cVk/F7jaQckUemkaEKIOCegjsyzdTP/5kothBSPSrpx1oPwbuFW8VsqdCNLUhGLdRgM8HNZseXkdSu4iTXTsRrI+OSho04mbMEv7zVkD2jzqGcm5H36LKtOi3CJ3xL9fI0g/ZXQ4pKZQVWO5AZQROz3FUygzFTqWoylhq5G7VJAUrbK/67YCTMNHoIkpppzqLv9DIqMSDnNQIDAQAB";

	public static void buildConnectionToGooglePlay(Activity activity){

		PurchaseManager.activity = activity;
//		PurchaseManager.mHelper = new IabHelper(PurchaseManager.activity, base64EncodedPublicKey);
		List<String> list = new ArrayList<>(productIds.keySet());
//		iapConnector = new IapConnector(
//				activity,
//				list,
//				list,
//				list,
//				LICENSE_KEY,
//				true
//		);


//		// create connection
//		PurchaseManager.mHelper.startSetup(result -> {
//		   if (!result.isSuccess()) {
//			  // there was a problem.
//			   Log.d("PurchaseManager", "Problem setting up In-app Billing: " + result);
//			   Toast.makeText(PurchaseManager.activity, "Cannot connect to Google Play, may be due to your internet connection. Please verify your connection is work and try again.", Toast.LENGTH_SHORT).show();
//			   System.exit(0);
//		   }
//		   // IAB is fully set up!
//		   Log.d("PurchaseManager", "Connect to Google Play is success");
//		});
	}

//	public static void buildConnectionToGooglePlay(final Context context) {
//		PurchaseManager.mHelper = new IabHelper(context, base64EncodedPublicKey);
//		// create connection
//		PurchaseManager.mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
//			   public void onIabSetupFinished(IabResult result) {
//			      if (!result.isSuccess()) {
//			         // there was a problem.
//			    	  Log.d("PurchaseManager", "Problem setting up In-app Billing: " + result);
//			    	  Toast.makeText(context, "Cannot connect to Google Play, may be due to your internet connection. Please verify your connection is work and try again.", Toast.LENGTH_SHORT).show();
//			    	  System.exit(0);
//			      }
//			      // IAB is fully set up!
//			      Log.d("PurchaseManager", "Connect to Google Play is success");
//			   }
//			});
//	}

//	public static void processPurchase(String signatureWorkoutId){
//		// create a listener for handling purchase events
//		IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
//		   = new IabHelper.OnIabPurchaseFinishedListener() {
//
//		   public void onIabPurchaseFinished(IabResult result, Purchase purchase)
//		   {
//		      if (result.isFailure()) {
//		    	  Log.d("PurchaseManager", "Error purchasing: " + result);
//		    	  if(result.mResponse == IabHelper.IABHELPER_USER_CANCELLED) {
//		    		  Toast.makeText(activity, activity.getResources().getString(R.string.purchase_cancelled), Toast.LENGTH_SHORT).show();
//		    	  } else {
//		    		  Toast.makeText(activity, activity.getResources().getString(R.string.purchase_failed), Toast.LENGTH_SHORT).show();
//		    	  }
//		    	  return;
//		      }
//		      else if (result.isSuccess()) {
//		    	  Log.d("PurchaseManager", "The purchase is successful: " + result);
//		    	  if(GorillaApp.currentWorkout.equals("All Workouts")) {
//			    	  CommonUtils.saveData(activity, Params.SIGNATURE_WORKOUT_ALL_UNLOCK, "true");
//		    	  } else {
//		    		  String signatureWorkouts = CommonUtils.loadData(activity, Params.SIGNATURE_WORKOUT_BOUGHT);
//			    	  signatureWorkouts = signatureWorkouts.equals("") ? signatureWorkouts : signatureWorkouts + ";";
//			    	  signatureWorkouts += GorillaApp.currentWorkout;
//			    	  CommonUtils.saveData(activity, Params.SIGNATURE_WORKOUT_BOUGHT, signatureWorkouts);
//		    	  }
//
//		    	  Toast.makeText(activity, activity.getResources().getString(R.string.purchase_successful), Toast.LENGTH_SHORT).show();
//
//		    	  FragmentManager fragmentManager = ((FragmentActivity)activity).getSupportFragmentManager();
//		    	  ActivityBuySignatureWorkout activityBuySignatureWorkout = (ActivityBuySignatureWorkout) fragmentManager.findFragmentById(R.id.fragment);
//		    	  activityBuySignatureWorkout.getBuy().setVisibility(View.GONE);
//		    	  if(!GorillaApp.currentWorkout.equals("All Workouts")) {
//		    		  activityBuySignatureWorkout.getStartWorkout().setVisibility(View.VISIBLE);
//				  }
//		    	  return;
//		      }
//		   }
//		};
//
//		// launch purchase
//		mHelper.launchPurchaseFlow(activity, signatureWorkoutId, RC_REQUEST, mPurchaseFinishedListener);
//	}

//	public static ArrayList<String> getOwnedProductIds(final Context context) {
//		ArrayList<String> ownedProductIds = new ArrayList<String>();
//		try {
//			if (PurchaseManager.mHelper == null || PurchaseManager.mHelper.getService() == null) {
//				Log.d("PurchaseManager", "An exception occurs when get owned productIds");
//				return null;
//			}
//
//			Bundle ownedItems = PurchaseManager.mHelper.getService().getPurchases(3, context.getPackageName(), "inapp", null);
//
//			int response = ownedItems.getInt("RESPONSE_CODE");
//
//			if (response == 0) {
//				ownedProductIds = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
////				ownedproductIds.put("gw_signature_100_pushups");
//			} else {
//				Log.d("PurchaseManager", "An error occurs when get owned productIds");
//				return null;
//			}
//		} catch (RemoteException e) {
//			Log.d("PurchaseManager", "An exception occurs when get owned productIds");
//			return null;
//		}
//		return ownedProductIds;
//	}

//	public static void dispose() {
//		// release all resource of connection
//		if (mHelper != null) mHelper.dispose();
//		mHelper = null;
//		activity = null;
//	}
//
//	public static IabHelper getHelper(){
//		return mHelper;
//	}

}
