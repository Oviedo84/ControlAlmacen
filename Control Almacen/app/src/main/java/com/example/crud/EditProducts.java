package com.example.crud;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProducts extends Fragment {
    private String EditProd;
    private ip object = new ip();
    private String PCip = object.getLocalip();

    private Button insertButton;
    private EditText insertNombre, insertDescripcion, insertCantidad;
    RequestQueue requestQueue;
    String id, nombre, descripcion, cantidad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_products, container, false);
        Bundle bundle = this.getArguments();
        id = bundle.getString("id");
        nombre = bundle.getString("nombre");
        descripcion = bundle.getString("tipo");
        cantidad = bundle.getString("cantidad");
        EditProd = PCip + "/editProd/" + id;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestQueue = Volley.newRequestQueue(getContext());

        // Insertar Producto
        insertNombre = (EditText) view.findViewById(R.id.nombre);
        insertDescripcion = (EditText) view.findViewById(R.id.descripcion);
        insertCantidad = (EditText) view.findViewById(R.id.cantidad);

        // Inicializar los campos
        insertNombre.setText(nombre, TextView.BufferType.EDITABLE);
        insertDescripcion.setText(descripcion, TextView.BufferType.EDITABLE);
        insertCantidad.setText(cantidad, TextView.BufferType.EDITABLE);

        insertButton = (Button) view.findViewById(R.id.insert_button);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit()){
                    Bundle bundleforFragment = new Bundle();
                    bundleforFragment.putString("data", "1");
                    Fragment main = new ListProducts();
                    main.setArguments(bundleforFragment);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.load_fragment, main);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    // Mostrar el FAB
                    Main activity = (Main)getActivity();
                    activity.showUpFAB();
                }
            }
        });
    }

    boolean edit(){
        String nombre = insertNombre.getText().toString().trim();
        String tipo = insertDescripcion.getText().toString().trim();
        String cantidad = insertCantidad.getText().toString().trim();

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        if (nombre.isEmpty()) {
            insertNombre.setError("Complete el campo");
            return false;
        } else if (tipo.isEmpty()) {
            insertDescripcion.setError("Complete el campo");
            return false;
        } else if (cantidad.isEmpty()) {
            insertCantidad.setError("Complete el campo");
            return false;
        } else {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.PUT,
                    EditProd,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getContext(), "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error){
                    //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            )
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String>params=new HashMap<String, String>();
                    params.put("nombre", nombre);
                    params.put("tipo", tipo);
                    params.put("cantidad", cantidad );
                    return params;
                }
            };
            requestQueue.add(stringRequest);
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Main activity = (Main)getActivity();
        if (activity != null) {
            activity.hideFAB();
        }
    }
}