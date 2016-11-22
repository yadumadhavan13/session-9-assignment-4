package com.example.y.intermediatealertdialogue;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> al_contactName,al_contactNumber, al_contactDOB;
    //    private TextView contactName, contactNumber, ContactDOB;
    private EditText ev_diag_contactName, ev_diag_contactNumber, ev_diag_DOB;
    protected MyAdapter adapter;
    String diag_contactName, diag_contactNumber, diag_contactDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.lv_contacts);

        al_contactName = new ArrayList<>();
        al_contactNumber = new ArrayList<>();
        al_contactDOB = new ArrayList<>();


        adapter = new MyAdapter();
        listView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMenu: {
                Toast.makeText(MainActivity.this, "New Item Added", Toast.LENGTH_LONG).show();
                addItem();
                Log.e("MainActivity", "New Item Added");
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addItem(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        alertDialog.setView(inflater.inflate(R.layout.dialog_contat, null))
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog diag = (Dialog) dialog;
                        ev_diag_contactName = (EditText) diag.findViewById(R.id.diag_contact_name);
                        ev_diag_contactNumber = (EditText) diag.findViewById(R.id.diag_phone_number);
                        ev_diag_DOB = (EditText) diag.findViewById(R.id.diag_date_of_birth);

                        diag_contactName = ev_diag_contactName.getText().toString();
                        diag_contactNumber = ev_diag_contactNumber.getText().toString();
                        diag_contactDOB = ev_diag_DOB.getText().toString();

                        Log.e("addItem ",diag_contactName);

                        al_contactName.add(diag_contactName);
                        al_contactNumber.add("+" + diag_contactNumber);
                        al_contactDOB.add(diag_contactDOB);

                        adapter.notifyDataSetChanged();

                        addItem();  //Reappear of AlertDialog to enter more contacts

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }


    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return al_contactName.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.name_and_contacts,parent,false);
                holder = new ViewHolder();
                holder.bindView(convertView);
                convertView.setTag(holder);

            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.contactName.setText(al_contactName.get(position));
            holder.contactNumber.setText(al_contactNumber.get(position));
            holder.contactDOB.setText(al_contactDOB.get(position));
            return convertView;
        }
    }
    public class ViewHolder{
        TextView contactName, contactNumber, contactDOB;
        void bindView(View convertView){
            contactName = (TextView)convertView.findViewById(R.id.contact_name);
            contactNumber = (TextView)convertView.findViewById(R.id.contact_number);
            contactDOB = (TextView)convertView.findViewById(R.id.contact_DOB);
        }
    }
}
