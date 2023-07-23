package com.ardev.idroid.ui.main.fragment.preview;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import com.ardev.idroid.ext.ColorUtils;
import com.google.android.material.card.MaterialCardView;
import java.util.List;
import java.io.File;
import android.content.Intent;
import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView;
import com.ardev.idroid.ui.main.fragment.preview.model.Item;
import com.ardev.idroid.R;



public class ValuesAdapter extends RecyclerView.Adapter<ValuesAdapter.ItemHolder<Item>> {

    private OnClickListener onClickListener = null;
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    private OnColorClickListener onColorClickListener = null;
    public void setOnColorClickListener(OnColorClickListener onColorClickListener) {
        this.onColorClickListener = onColorClickListener;
    }

    List<Item> mList = new ArrayList<>();

        public void setList(List<Item> list){ 
         
         this.mList = list;
        notifyDataSetChanged();
        
        }
        
    @NonNull   
	@Override
	public ItemHolder<Item> onCreateViewHolder(ViewGroup parent, int viewType) {
	LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		if (viewType == R.layout.item_string) {
		return new StringHolder( inflater.inflate(R.layout.item_string, parent, false));
		} else if (viewType == R.layout.item_color) {
		return new ColorHolder( inflater.inflate(R.layout.item_color, parent, false));
	    } else if (viewType == R.layout.item_empty) {
			return new EmptyHolder( inflater.inflate(R.layout.item_empty, parent, false));
			
		
		} else {
	    throw new IllegalArgumentException("Unknown view type:" + viewType);
	    }
	}
    @NonNull  
	@Override
	public void onBindViewHolder(ItemHolder viewHolder, final int position) {
	viewHolder.setItem(mList.get(position), position);
	
	 
	}
	
	
	@Override
	public int getItemViewType(int position) {
	if (mList.isEmpty()) return  R.layout.item_empty;
	
	return mList.get(position).getType().equals("color") ? R.layout.item_color : mList.get(position).getType().equals("string") ? R.layout.item_string: R.layout.item_string; 
	}

	@Override
	public int getItemCount() {
	    return mList.size();
	}
	
	
	public class StringHolder extends ItemHolder<Item> { 
    TextView sName, sValue;
    View itemView;

    public StringHolder(@NonNull View itemView) { 
        super(itemView); 
        this.itemView = itemView;
        sName = itemView.findViewById(R.id.nameS); 
        sValue = itemView.findViewById(R.id.value); 
    }
    @Override
    public void setItem(@NonNull Item item,  int position) {
   
    
    sName.setText(item.getName() + " (" +item.getType() +")");
    sValue.setText(item.getValue());
    
    itemView.setOnClickListener( view -> {
                    if (onClickListener == null) return;
					onClickListener.onItemClick(view, position, item);
});
    
    }
    
}


    public class ColorHolder extends ItemHolder<Item> {
		TextView name, value;
		View itemView;
		MaterialCardView color;

		public ColorHolder(@NonNull View itemView) {
			super(itemView);
			this.itemView = itemView;
			name = itemView.findViewById(R.id.nameC);
			value = itemView.findViewById(R.id.value);
			color = itemView.findViewById(R.id.color);
		}
		
		@Override
    public void setItem(@NonNull Item item,  int position) {
    
    name.setText(item.getName() + " (" +item.getType() +")");
    value.setText(item.getValue());
		color.setCardBackgroundColor(Color.parseColor(ColorUtils.getColor(item.getValue())));
     
    itemView.setOnClickListener( view -> {
                    if (onClickListener == null) return;
					onClickListener.onItemClick(view, position, item);
});
	color.setOnClickListener( view -> {
                    if (onColorClickListener == null) return;
					onColorClickListener.onColorClick(view, position, item);
});
    
    
    }
		
	}

public class EmptyHolder extends ItemHolder<Item> {
	
	public EmptyHolder(@NonNull View itemView) {
		super(itemView);
		
		}
	}



class ItemHolder<T> extends RecyclerView.ViewHolder {

		T item;
        int position;
         
		ItemHolder(View itemView) {
			super(itemView);
			
			
			
		}

		public void setItem(@NonNull T item,  int position) {
			this.item = item;
			this.position = position;
		}

		Context getContext() {
			return itemView.getContext();
		}
		
		
		}

		 

	





 public interface OnClickListener {
        void onItemClick(View view, int position,  Item item);
        
        }
		public interface OnColorClickListener {
        void onColorClick(View view, int position,  Item item);
        
        }
	
	 
}