package com.example.crud;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListProducts extends Fragment{
    int i = 0;
    private ip object = new ip();
    private String PCip = object.getLocalip();
    private String products;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    GetProducts getProducts;
    List<GetProducts> mProducto;
    AdapterProducts adapterProducts;
    SearchView searchView;
    View v;
    AdapterProducts.RecyclerViewClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_products, container, false);
        Bundle bundle = this.getArguments();
        i = Integer.parseInt(bundle.getString("data"));
        v = view;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // Set dir
        switch (i){
            case 1:
                products = PCip + "/listProd";
                break;
            case 2:
                products = PCip + "/listProdbyQuantity";
                break;
            case 3:
                products = PCip + "/listProdbyNameAsc";
                break;
            case 4:
                products = PCip + "/listProdbyNameDesc";
                break;
            default:
                break;
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mProducto = new ArrayList<>();
        searchView = view.findViewById(R.id.searchViewListProducts);
        searchView.setVisibility(view.GONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        requestQueue = Volley.newRequestQueue(getContext());
        this.getProducts();
        this.deleteProducts();
    }

    private void getProducts(){
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                products,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int size = response.length();
                        for (int i = 0; i < size; i++) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                String prod_id = jsonObject.getString("prod_id");
                                String nombre = jsonObject.getString("prod_nombre");
                                String tipo = jsonObject.getString("prod_tipo");
                                String cantidad = jsonObject.getString("prod_cantidad");
                                getProducts = new GetProducts(prod_id, nombre, tipo, cantidad);
                                mProducto.add(getProducts);
                                setOnClickListener();
                                recyclerView.setAdapter(adapterProducts);
                                adapterProducts = new AdapterProducts(mProducto, getContext(), listener);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(arrayRequest);
    }

    void setOnClickListener() {
        listener = new AdapterProducts.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                GetProducts aux = mProducto.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("id", aux.getProducto_id());
                bundle.putString("nombre", aux.getNombre());
                bundle.putString("tipo", aux.getTipo());
                bundle.putString("cantidad", aux.getCantidad());
                Fragment main = new EditProducts();
                main.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.load_fragment, main);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        };
    }

    public void deleteProducts(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getBindingAdapterPosition();
                GetProducts a = mProducto.get(pos);
                String id = a.getProducto_id();
                eliminarProducto(id);
                mProducto.remove(viewHolder.getBindingAdapterPosition());
                recyclerView.setAdapter(adapterProducts);
                adapterProducts.notifyDataSetChanged();
            }
        }).attachToRecyclerView(recyclerView);
    }

    public void eliminarProducto(String id) {
        String deleteProduct = PCip + "/deleteProduct/" + id;
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                deleteProduct,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Datos eliminados correctamente", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(stringRequest);
    }
    
    public void ShowSearchView(){
        searchView.setVisibility(v.VISIBLE);
    }
    
    public void filterList(String text){
        List<GetProducts> aux = new ArrayList<>();
        for(GetProducts x : mProducto){
            if(x.getNombre().toLowerCase().contains(text.toLowerCase())){
                aux.add(x);
            }
        }
        if(aux.isEmpty()){
            Toast.makeText(getContext(), "No hay coincidencias", Toast.LENGTH_SHORT).show();
        }
        else{
            adapterProducts.setFilteredProducts(aux);
            recyclerView.setAdapter(adapterProducts);
            adapterProducts.notifyDataSetChanged();
        }
    }
}