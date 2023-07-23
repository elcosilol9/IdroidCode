package com.ardev.component.widget.breadcrumbs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.LinearLayout;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.ardev.component.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ardev.component.widget.breadcrumbs.model.IBreadcrumbItem;

class BreadcrumbsAdapter extends RecyclerView.Adapter<BreadcrumbsAdapter.ItemHolder> {


	private List<IBreadcrumbItem> items = new ArrayList<>();
	private BreadcrumbsCallback callback;

	private BreadcrumbsView parent;

	
	private Context context;
	public BreadcrumbsAdapter(BreadcrumbsView parent) {
		this(parent, new ArrayList<IBreadcrumbItem>());
	}

	public BreadcrumbsAdapter(BreadcrumbsView parent, ArrayList<IBreadcrumbItem> items) {
		this.parent = parent;
		this.items = items;
          
	}

	public @NonNull List<IBreadcrumbItem> getItems() {
		return (List<IBreadcrumbItem>) this.items;
	}

	public <E extends IBreadcrumbItem> void setItems(@NonNull List<E> items) {
		this.items = (List<IBreadcrumbItem>) items;
	}

	public void setCallback(@Nullable BreadcrumbsCallback callback) {
		this.callback = callback;
	}

	public @Nullable BreadcrumbsCallback getCallback() {
		return this.callback;
	}



	@NonNull
    @Override
	public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		if (viewType == R.layout.bcv_item_arrow) {
			return new ArrowIconHolder(inflater.inflate(viewType, parent, false));
		} else if (viewType == R.layout.bcv_item_text) {
			return new BreadcrumbItemHolder(inflater.inflate(viewType, parent, false));
		} else {
			throw new IllegalArgumentException("Unknown view type:" + viewType);
		}
	}

	@Override
	public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
		onBindViewHolder(holder, position, null);
	}

	@Override
	public void onBindViewHolder(@NonNull ItemHolder holder, int position, List<Object> payloads) {
		int viewType = getItemViewType(position);
		int truePos = BreadcrumbsUtil.getTruePosition(viewType, position);
		holder.setItem(items.get(truePos));
	}

	@Override
	public int getItemCount() {
		return BreadcrumbsUtil.getAdapterCount(items);
	}

	@Override
	public int getItemViewType(int position) {
		return BreadcrumbsUtil.getItemViewType(position);
	}

	class BreadcrumbItemHolder extends ItemHolder<IBreadcrumbItem> {

		TextView button;

		BreadcrumbItemHolder(View itemView) {
			super(itemView);
			button = (TextView) itemView;
			// enable touch feedback only for items that have a callback
			if (callback != null) button.setOnClickListener( (view) -> callback.onItemClick(parent, getAdapterPosition() / 2));
			else button.setClickable(false);
			button.setTextSize(TypedValue.COMPLEX_UNIT_PX, parent.getTextSize());
			button.setPadding(parent.getTextPadding(), parent.getTextPadding(), parent.getTextPadding(), parent.getTextPadding());
		}

		@Override
		public void setItem(@NonNull IBreadcrumbItem item) {
			super.setItem(item);
			button.setText(item.getSelectedItem().toString());
			button.setTextColor(getAdapterPosition() == getItemCount() - 1 ? parent.getSelectedTextColor()
					: parent.getTextColor());
		}
	}

	class ArrowIconHolder extends ItemHolder<IBreadcrumbItem> {

		ImageButton imageButton;
		ListPopupWindow popupWindow;

		ArrowIconHolder(View itemView) {
			super(itemView);
			Drawable normalDrawable = getContext().getResources().getDrawable(R.drawable.ic_chevron_right_black_24dp);
			Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
			DrawableCompat.setTintList(wrapDrawable, parent.getTextColor());
			imageButton = (ImageButton) itemView;
			imageButton.setImageDrawable(wrapDrawable);
            
            imageButton.setClickable(false);
			 
			
		}

		 

	}

	class ItemHolder<T> extends RecyclerView.ViewHolder {

		T item;

		ItemHolder(View itemView) {
			super(itemView);
		}

		public void setItem(@NonNull T item) {
			this.item = item;
		}

		Context getContext() {
			return itemView.getContext();
		}

	}

}
