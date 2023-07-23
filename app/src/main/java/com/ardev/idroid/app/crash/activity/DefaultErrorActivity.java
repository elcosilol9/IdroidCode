package com.ardev.idroid.app.crash.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import com.ardev.idroid.app.AppActivity;
import com.ardev.idroid.R;
import com.ardev.idroid.app.crash.config.CrashConfig;
 
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public final class DefaultErrorActivity extends AppActivity {

	@SuppressLint("PrivateResource")
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crash);
		
		final CrashConfig config = CrashActivity.getConfigFromIntent(getIntent());
		if (config == null) {
			finish();
			return;
		}
		Button restartButton = findViewById(R.id.b_restart);
		if (config.isShowRestartButton() && config.getRestartActivityClass() != null) {
			restartButton.setOnClickListener(v -> CrashActivity.restartApplication(DefaultErrorActivity.this, config));
		} else {
			restartButton.setOnClickListener(v -> CrashActivity.closeApplication(DefaultErrorActivity.this, config));
		}
		Button moreInfoButton = findViewById(R.id.b_detail);
		if (config.isShowErrorDetails()) {
			moreInfoButton.setOnClickListener(v -> {
				MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(DefaultErrorActivity.this).setTitle("Detalles")
						.setMessage(CrashActivity.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent()))
						.setPositiveButton("Cerrar", null)
						.setNeutralButton("Copiar", (dialog1, which) -> copyErrorToClipboard());
						AlertDialog dialog = builder.create();
					
				TextView textView = dialog.findViewById(android.R.id.message);
				if (textView != null) {
					textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
							getResources().getDimension(R.dimen.error_detail_text_size));
				}
					dialog.show();
				
			});
		} else {
			moreInfoButton.setVisibility(View.GONE);
		}
		Integer defaultErrorActivityDrawableId = config.getErrorDrawable();
		ImageView errorImageView = findViewById(R.id.iv_error);
		if (defaultErrorActivityDrawableId != null) {
			errorImageView.setImageDrawable(
					ResourcesCompat.getDrawable(getResources(), defaultErrorActivityDrawableId, getTheme()));
		}
	}

	private void copyErrorToClipboard() {
		String errorInformation = CrashActivity.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent());
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		if (clipboard != null) {
			ClipData clip = ClipData.newPlainText("Error Information", errorInformation);
			clipboard.setPrimaryClip(clip);
			//AppUtils.showToast(this, "Copiado");
		}
	}

 
}