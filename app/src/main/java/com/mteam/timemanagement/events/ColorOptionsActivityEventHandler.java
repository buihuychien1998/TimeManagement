package com.mteam.timemanagement.events;

import static com.mteam.timemanagement.billing.PurchaseManager.productIds;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.mteam.timemanagement.R;
import com.mteam.timemanagement.HiitApp;
import com.mteam.timemanagement.activity.ColorOptionsActivity;
import com.mteam.timemanagement.adapter.ColorOptionsListAdapter;
import com.mteam.timemanagement.commons.Params;
import com.mteam.timemanagement.dto.ColorOptionsItem;
import com.mteam.timemanagement.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class ColorOptionsActivityEventHandler extends BaseEventHandler implements OnClickListener, OnItemClickListener {

	private String selectedColor;
	
	private int selectedIndex;
	
	private BillingClient billingClient;

	final List<SkuDetails> skuDetailsList = new ArrayList<>();
	
	public ColorOptionsActivityEventHandler(Activity activity) {
		super(activity);
		billingClient = BillingClient.newBuilder(activity)
				.enablePendingPurchases()
				.setListener((billingResult, list) -> {
					if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
						for (Purchase purchase : list) {
							if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
								Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), "PURCHARED");
								handlePurchase(purchase);
								String signatureWorkouts = CommonUtils.loadData(activity, Params.SIGNATURE_WORKOUT_BOUGHT);
								signatureWorkouts = signatureWorkouts.equals("") ? signatureWorkouts : signatureWorkouts + ";";
								CommonUtils.saveData(activity, Params.SIGNATURE_WORKOUT_BOUGHT, signatureWorkouts);

								///event change color
								ColorOptionsItem item = (ColorOptionsItem)((ColorOptionsActivity)activity).getColorList().getItemAtPosition(selectedIndex);
								item.setChecked(true);
								selectedColor = item.getName();
								ColorOptionsItem lastSelectedItem = (ColorOptionsItem)((ColorOptionsActivity)activity).getColorList().getItemAtPosition(selectedIndex);
								lastSelectedItem.setChecked(false);
								ColorOptionsListAdapter adapter = (ColorOptionsListAdapter) ((ColorOptionsActivity)activity).getColorList().getAdapter();
								adapter.notifyDataSetChanged();
								back();
							}
						}
						// Query for existing user purchases
						// Query for products for sale
						Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), "Billing client successfully set up!");
					}
					Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), "billingResult: " + billingResult.getResponseCode());
				})
				.build();

		//TODO: Connect ???ng d???ng c???a b???n v???i Google Billing
		billingClient.startConnection(new BillingClientStateListener() {
			@Override
			public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
				//TODO: Sau khi connect th??nh c??ng, th??? l???y th??ng tin c??c s???n ph???m
				if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
					Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), "Billing client successfully set up!");
					// Query for existing user purchases
					// Query for products for sale
				}
				queryProducts();
			}

			@Override
			public void onBillingServiceDisconnected() {
				//TODO: Connect Google Play not success
				Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), "Billing service disconnected");
			}
		});
	}

	private void queryProducts() {
		if (!billingClient.isReady()) {
			Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), "queryPurchases: BillingClient is not ready");
		}
		// TODO: t???o list c??c product id (ch??nh l?? product id b???n ???? nh???p ??? b?????c tr?????c) ????? l???y th??ng tin
//        List<String> productIds = new ArrayList<>();
//        productIds.add("thanh_hoa");
//        productIds.add("nghe_an");
//        productIds.add("ha_tinh");
//        productIds.add("quang_binh");
//        productIds.add("quang_tri");
//        productIds.add("thua_thien_hue");
		List<String> skusList = new ArrayList<>(productIds.values());
		SkuDetailsParams skuDetailsParams = SkuDetailsParams.newBuilder()
				.setSkusList(skusList)
				.setType(BillingClient.SkuType.INAPP)
				//TODO: S??? d???ng INAPP v???i one-time product v?? SUBS v???i c??c g??i subscriptions.
				.build();

		// TODO: Th???c hi???n query
		// Query for existing in app products that have been purchased. This does NOT include subscriptions.

		billingClient.querySkuDetailsAsync(skuDetailsParams, (billingResult, list) -> {
			Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), skusList.size() + " skusList");
			if (list != null && !list.isEmpty()) {
				Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), list.size() + "");
				//???? l???y ???????c th??ng tin c??c s???n ph???m ??ang b??n theo c??c product id ??? tr??n
				skuDetailsList.clear();
				skuDetailsList.addAll(list);
//                for (SkuDetails skuDetails : list) {
//                    // matches the last text surrounded by parentheses at the end of the SKU title
//                    items.get(getItemIndexByTitle(skuDetails.getSku())).setCost(skuDetails.getPrice());
//                }
//                adapter.notifyDataSetChanged();
			} else {
				Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), "No existing in app purchases found.");
			}
		});
	}

	void handlePurchase(Purchase purchase) {
		// Purchase retrieved from BillingClient#queryPurchasesAsync or your PurchasesUpdatedListener.
//        Purchase purchase = ...;

		// Verify the purchase.
		// Ensure entitlement was not already granted for this purchaseToken.
		// Grant entitlement to the user.

		ConsumeParams consumeParams =
				ConsumeParams.newBuilder()
						.setPurchaseToken(purchase.getPurchaseToken())
						.build();

		ConsumeResponseListener listener = (billingResult, purchaseToken) -> {
			if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {


				// Handle the success of the consume operation.
			}
		};

		billingClient.consumeAsync(consumeParams, listener);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnBack:
			back();
			break;
		}
	}

	private void back() {
		Intent intent=new Intent();
	    intent.putExtra(Params.COLOR, selectedColor);
	    activity.setResult(Activity.RESULT_OK, intent);
	    activity.finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// If user purchased all
		String purchasedAll = CommonUtils.loadData(activity, Params.SIGNATURE_WORKOUT_ALL_UNLOCK);
		//if (purchasedAll.equals("")){
			ColorOptionsItem item = (ColorOptionsItem)parent.getItemAtPosition(position);
			if(position >= 1){
				if (skuDetailsList.isEmpty()) {
					Toast.makeText(activity.getApplicationContext(), "This feature is coming soon", Toast.LENGTH_SHORT).show();
					return;
				}
				Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), "buy: " + HiitApp.currentWarmupColor);
				int skuIndex = -1;
				for (int index = 0; index < skuDetailsList.size(); index++) {
					if (skuDetailsList.get(index).getSku().equals(productIds.get(item.getName()))) {
						skuIndex = index;
						break;
					}
				}
				if (skuIndex == -1) {
					Toast.makeText(activity.getApplicationContext(), "Price not found for this item!", Toast.LENGTH_SHORT).show();
					return;
				}
				BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
						.setSkuDetails(skuDetailsList.get(skuIndex))
						.build();

				BillingResult result = billingClient.launchBillingFlow(activity, billingFlowParams);
			}else {
				item.setChecked(true);
				selectedColor = item.getName();
				ColorOptionsItem lastSelectedItem = (ColorOptionsItem)parent.getItemAtPosition(selectedIndex);
				lastSelectedItem.setChecked(false);
				selectedIndex = position;
				ColorOptionsListAdapter adapter = (ColorOptionsListAdapter) ((ColorOptionsActivity)activity).getColorList().getAdapter();
				adapter.notifyDataSetChanged();
				back();
			}

//		}else {
//			ColorOptionsItem item = (ColorOptionsItem)parent.getItemAtPosition(position);
//			item.setChecked(true);
//			selectedColor = item.getName();
//			ColorOptionsItem lastSelectedItem = (ColorOptionsItem)parent.getItemAtPosition(selectedIndex);
//			lastSelectedItem.setChecked(false);
//			selectedIndex = position;
//			ColorOptionsListAdapter adapter = (ColorOptionsListAdapter) ((ColorOptionsActivity)activity).getColorList().getAdapter();
//			adapter.notifyDataSetChanged();
//			back();
//		}

	}

	public String getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(String selectedColor) {
		this.selectedColor = selectedColor;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
}
