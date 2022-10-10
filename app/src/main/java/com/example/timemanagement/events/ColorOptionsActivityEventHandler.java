package com.example.timemanagement.events;

import static com.example.timemanagement.billing.PurchaseManager.productIds;

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
import com.example.timemanagement.HiitApp;
import com.example.timemanagement.R;
import com.example.timemanagement.activity.ColorOptionsActivity;
import com.example.timemanagement.adapter.ColorOptionsListAdapter;
import com.example.timemanagement.billing.PurchaseManager;
import com.example.timemanagement.commons.Params;
import com.example.timemanagement.dto.ColorOptionsItem;
import com.example.timemanagement.dto.HiiTTimer;
import com.example.timemanagement.utils.CommonUtils;

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
								//signatureWorkouts += GorillaApp.currentWorkout;
								CommonUtils.saveData(activity, Params.SIGNATURE_WORKOUT_BOUGHT, signatureWorkouts);
							}
						}
						// Query for existing user purchases
						// Query for products for sale
						Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), "Billing client successfully set up!");
					}
					Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), "billingResult: " + billingResult.getResponseCode());
				})
				.build();

		//TODO: Connect ứng dụng của bạn với Google Billing
		billingClient.startConnection(new BillingClientStateListener() {
			@Override
			public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
				//TODO: Sau khi connect thành công, thử lấy thông tin các sản phẩm
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
		// TODO: tạo list các product id (chính là product id bạn đã nhập ở bước trước) để lấy thông tin
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
				//TODO: Sử dụng INAPP với one-time product và SUBS với các gói subscriptions.
				.build();

		// TODO: Thực hiện query
		// Query for existing in app products that have been purchased. This does NOT include subscriptions.

		billingClient.querySkuDetailsAsync(skuDetailsParams, (billingResult, list) -> {
			Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), skusList.size() + " skusList");
			if (list != null && !list.isEmpty()) {
				Log.d(ColorOptionsActivityEventHandler.class.getSimpleName(), list.size() + "");
				//Đã lấy được thông tin các sản phẩm đang bán theo các product id ở trên
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
		if(purchasedAll.equals("")){
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
			}
			item.setChecked(true);
			selectedColor = item.getName();
			ColorOptionsItem lastSelectedItem = (ColorOptionsItem)parent.getItemAtPosition(selectedIndex);
			lastSelectedItem.setChecked(false);
			selectedIndex = position;
			ColorOptionsListAdapter adapter = (ColorOptionsListAdapter) ((ColorOptionsActivity)activity).getColorList().getAdapter();
			adapter.notifyDataSetChanged();
			back();
		}else {
			ColorOptionsItem item = (ColorOptionsItem)parent.getItemAtPosition(position);
			item.setChecked(true);
			selectedColor = item.getName();
			ColorOptionsItem lastSelectedItem = (ColorOptionsItem)parent.getItemAtPosition(selectedIndex);
			lastSelectedItem.setChecked(false);
			selectedIndex = position;
			ColorOptionsListAdapter adapter = (ColorOptionsListAdapter) ((ColorOptionsActivity)activity).getColorList().getAdapter();
			adapter.notifyDataSetChanged();
			back();
		}

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
