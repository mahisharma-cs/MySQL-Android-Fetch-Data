package com.mahi.dkmart;

//Please carefully read README file
//Read all the comments carefully...
///////////////////////////////////////////////////////////////////////////////////////////

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

///////////////////////////////////////////////////////////////////////////////////////////
	//here we are Extending recyclerview...

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>  {
    private Context mcontext;
    private ArrayList<CategoryItem> mCategoryList;

    public CategoryAdapter(Context context, ArrayList<CategoryItem> orderlist)
    {
        mcontext = context;
        mCategoryList = orderlist;
    }

    private OnItemClickListener mListener;

///////////////////////////////////////////////////////////////////////////////////////////
	//What happen when item is clicked....!
    public interface OnItemClickListener
    {
		
		//this method is implement in Category Activity...!!
        void onAddToCart(int position);
		
		//this method is also implement in Category Activity...!!
		void onProductClick(int id);
		
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.category_product_item,parent, false);
        return new CategoryViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        CategoryItem currentitem = mCategoryList.get(position);

        String imageUrl = currentitem.getmImage();
		String name = currentitem.getmName();
		String quantity = currentitem.getmQuantity();
		String oldprice = currentitem.getmOldPrice();
		String off = currentitem.getmOff();
		String price = currentitem.getmPrice();

		holder.Name.setText(name);
		holder.Quantity.setText(quantity);
		holder.OldPrice.setText(oldprice);
		holder.Off.setText(off);
		holder.Price.setText(price);
		Glide.with(mcontext).load(imageUrl).into(holder.Image);
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
		public ImageView Image;
		public TextView Name;
		public TextView Quantity;
		public TextView Price;
        public TextView OldPrice;
        public TextView Off;
		public Button AddToCart;

        public CategoryViewHolder(View itemView,final CategoryAdapter.OnItemClickListener listener)
        {
            super(itemView);
			Image = itemView.findViewById(R.id.image_category_product);
			Name = itemView.findViewById(R.id.tv_product_name_category_product);
			Quantity = itemView.findViewById(R.id.tv_quantity_category_product);
			OldPrice = itemView.findViewById(R.id.tv_oldprice_category_product);
			Off = itemView.findViewById(R.id.tv_off_category_product);
			Price = itemView.findViewById(R.id.tv_price_category_product);
			AddToCart = itemView.findViewById(R.id.btn_addtocart_category_product);

////////////////////////////////////////////////////////////////	
		// When add to cart button is clicked ...
            AddToCart.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(listener != null)
                   {
                       int position = getAdapterPosition();
                       if(position != RecyclerView.NO_POSITION)
                       {
////////////////////////////////////////////////////////////////
						//here we are just passing the position... because in the Activity we are created an array containing the product id with that particular position.
                           listener.onAddToCart(position);
                       }
                   }
               }
            });

////////////////////////////////////////////////////////////////
			//When the whole item is clicked... this called
			itemView.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					if(listener != null)
                   {
                       int position = getAdapterPosition();
                       if(position != RecyclerView.NO_POSITION)
                       {
                           listener.onProductClick(position);
                       }
                   }
				}
			});

        }


    }
}