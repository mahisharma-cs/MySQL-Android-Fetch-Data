package com.mahi.dkmart;

//Please carefully read README file
//Read all the comments carefully...
///////////////////////////////////////////////////////////////////////////////////////////

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
///////////////////////////////////////////////////////////////////////////////////////////	
	//If you are using a username which do not flush even if the activity finishes...!
    SaveSharedPreference saveSharedPreference = new SaveSharedPreference();

///////////////////////////////////////////////////////////////////////////////////////////
	//This is static because I am fetching from another application Cat_ID / category ID
    private static int Cat_ID;
	private static String Cat_Name;
    private static Context context;
	
///////////////////////////////////////////////////////////////////////////////////////////	
	//This view is important to update with your MySQL table value.
    private RecyclerView recyclerView;
	private CategoryAdapter categoryAdapter;
	
///////////////////////////////////////////////////////////////////////////////////////////	
	//Here is ArrayList of type CategoryItem... means this Array only contain the items which is in CategoryItem class or any java class created by you...!!
    private ArrayList<CategoryItem> categoryItemArrayList;
    private FloatingActionButton Home;
    private FloatingActionButton Cart;
	private FrameLayout CartNotify;
	private TextView CartItemsNotify;
	private TextView CategoryName;
	private Button AddToCart;
	
	public static void setCatId(int category_id)
	{
		Cat_ID = category_id;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////	
	//When user back pressed... then this action is performed by the Android / Your Application
	@Override
    public void onBackPressed() {
        setCatId(0);
        startActivity(new Intent(this,WelcomeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
///////////////////////////////////////////////////////////////////////////////////////////		
		//This line hides your title bar (Only for UI change // Not necessary for data fetching :) )
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_category);
		context = this;
		
		Home = findViewById(R.id.home_category);
		Cart = findViewById(R.id.btn_cart_category);
		AddToCart = findViewById(R.id.btn_addtocart_category_product);
		CartNotify = findViewById(R.id.frame_cart_category_notify);
		CartItemsNotify = findViewById(R.id.tv_cart_notify_cat_product);
		CategoryName = findViewById(R.id.tv_category_name_cat_product);

		recyclerView = findViewById(R.id.category_product_list);
        recyclerView.setHasFixedSize(true);
		
///////////////////////////////////////////////////////////////////////////////////////////		
		//This is really important --- here the GridLayout may be converted as Linear layout having 1 parameter this...
		//The grid layout is containing the number of grid you want in your application --- here 2 means the grid of 2.
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        categoryItemArrayList = new ArrayList<>();
		
///////////////////////////////////////////////////////////////////////////////////////////
		//Calling this function directly
		categoryItemsJSON();

		Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCatId(0);
                startActivity(new Intent(CategoryActivity.this,WelcomeActivity.class));
            }
        });

		Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this,CartActivity.class));
            }
        });

		

    }
	
	public void categoryItemsJSON()
	{
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Opening Products");

///////////////////////////////////////////////////////////////////////////////////////////
		//here in the URL section... Your site must contain https:// (SSL Certificate)
		String url1 = "https://yoursitename.com/application/category.php?id=";
		
///////////////////////////////////////////////////////////////////////////////////////////
		//this are the values we are passing into our .php files as shows also in php file
		String url2 = url1.concat(String.valueOf(Cat_ID)+"&username=");
		
		String url = url2.concat(saveSharedPreference.getUserName(this));
		progressDialog.show();
		
///////////////////////////////////////////////////////////////////////////////////////////
		//Those all we were print as JSON objects so...
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
       try {
           JSONObject jsonObject = new JSONObject(String.valueOf(response));
           Cat_Name = jsonObject.getString("category_name");
           CategoryName.setText(Cat_Name + " Products");

           if (jsonObject.getString("cart_item_count").equalsIgnoreCase("0")) {
               CartNotify.setVisibility(View.INVISIBLE);
           } else {
               CartItemsNotify.setText(jsonObject.getString("cart_item_count"));
           }
           String success = jsonObject.getString("success");
           if (success.equalsIgnoreCase("1")) {
               progressDialog.dismiss();
               JSONArray jsonArray = response.getJSONArray("data");
			   
///////////////////////////////////////////////////////////////////////////////////////////
			   //Here we are creating array because the values are written as position 1,2,3,.....,n
			   //So, If we want to perform an action on any block of grid like to click so which id is click is identified by array position.
			   // arr_pid[2] contain the product id which is displayed on the position 2.
			   final int[] arr_pid = new int[jsonArray.length()];

               for (int i = 0; i < jsonArray.length(); i++) {
                   JSONObject hit = jsonArray.getJSONObject(i);
                   String image_url_1 = hit.getString("image");
                   String image_url_2 = "https://martdk.com/image_product/";
                   String image = image_url_2.concat(image_url_1);
                   int id = hit.getInt("id");

///////////////////////////////////////////////////////////////////////////////////////////
				   //As we are entering the value of PRODUCT ID at that postion of displayed grid.
                   arr_pid[i] = id;
				   
                   String name = hit.getString("name");
                   String quantity = hit.getString("quantity");
                   String oldprice = "₹ " + hit.getString("oldprice") + "/-";
                   String price = "₹ " + hit.getString("price") + "/-";
				   double old = hit.getDouble("oldprice");
				   double newprice = hit.getDouble("price");
                   double off1 = (1 - (newprice/old));
                   String off = " " + (int)(off1*100) + "% off ";
				   
///////////////////////////////////////////////////////////////////////////////////////////
				   //First we are store that into CategoryItem class then it will automatically update from that class to recyclerView
                   categoryItemArrayList.add(new CategoryItem(image, name, quantity, oldprice, off, price));
                   categoryAdapter = new CategoryAdapter(CategoryActivity.this, categoryItemArrayList);
                   recyclerView.setAdapter(categoryAdapter);

///////////////////////////////////////////////////////////////////////////////////////////
					//The below interface is avaliable into the CategoryAdapter.java class... we are implement that methods here!!!
                   categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener(){
                       @Override
                       public void onAddToCart(int position) {
						   
///////////////////////////////////////////////////////////////////////////////////////////						   
						   //What happen when someone click Add To Cart Button
                           addToCart(String.valueOf(arr_pid[position]));
						   
                       }
					   
					   @Override
					   public void onProductClick(int position){
						   ProductActivity.Product_Id = arr_pid[position];
						   
///////////////////////////////////////////////////////////////////////////////////////////
						   //Here, open a new activity with that product which is just now clicked...!!
						   startActivity(new Intent(CategoryActivity.this,ProductActivity.class));
						   
					   }
                   });
               }
           }
       }
       catch(JSONException e)
       {
		   progressDialog.dismiss();
           e.printStackTrace();
       }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
			   progressDialog.dismiss();
               error.printStackTrace();
           }
       });

       RequestQueue requestQueue = Volley.newRequestQueue(CategoryActivity.this);
       requestQueue.add(request);
	}

///////////////////////////////////////////////////////////////////////////////////////////
	//This is the method which is call at the time when user pressed add to cart button

    private void addToCart(String id) {
	    String url1 = "https://yoursitename.com/application/addtocart.php?id=";
	    String url2 = url1.concat(id+"&username=");
	    String url = url2.concat(saveSharedPreference.getUserName(this));

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Adding item");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                gotoCategory(context);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                gotoCategory(context);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }

    private static void gotoCategory(Context context) {
        Intent cart = new Intent(context, CategoryActivity.class);
        context.startActivity(cart);
    }

}