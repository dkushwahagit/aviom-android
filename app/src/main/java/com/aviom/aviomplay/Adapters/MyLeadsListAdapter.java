package com.aviom.aviomplay.Adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aviom.aviomplay.Fragments.LeadDetailsFragment;
import com.aviom.aviomplay.Fragments.MyleadsFragement;
import com.aviom.aviomplay.Models.Lead;
import com.aviom.aviomplay.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 2017-02-18.
 */

public class MyLeadsListAdapter extends BaseAdapter {
//FragmentActivity mftagment;
    List<Lead> leadList = new ArrayList<Lead>();
    public MyLeadsListAdapter(List<Lead> leadList){
        this.leadList=leadList;
      //  mftagment=activity;
    }

    @Override
    public int getCount() {
        return leadList.size();
    }

    @Override
    public Lead getItem(int position) {
        return leadList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View myView= LayoutInflater.from(parent.getContext()).inflate(R.layout.leads_list_item,parent,false);
        TextView _tvLeadCreatedOn = (TextView)myView.findViewById(R.id.tvLeadCreatedOn);
        TextView _tvLeadCustomerName = (TextView)myView.findViewById(R.id.tvLabelCustomername);
        //  TextView _tvLeadPhoneNumber = (TextView)myView.findViewById(R.id.tvPhonNumber);
        TextView _tvLeadSattus = (TextView)myView.findViewById(R.id.tvLeadStatus);
        TextView _tvphone = (TextView)myView.findViewById(R.id.tvphone);
        final ImageView _ivLeadImage=(ImageView)myView.findViewById(R.id.user_profile_photo);
         TextView _tvCity=(TextView) myView.findViewById(R.id.tvcity);
         TextView _tvbudget=(TextView) myView.findViewById(R.id.tvbudget);
         TextView _tvappointment=(TextView) myView.findViewById(R.id.tvappointment);
       // ImageButton _view_btn=(ImageButton) myView.findViewById(R.id.view_btn);
      final  Lead lead=leadList.get(position);
        _tvLeadCreatedOn.setText(lead.getCreated_on());
        _tvCity.setText(lead.getLocation());
        _tvphone.setText(lead.getPhone());
        _tvLeadCustomerName.setText(lead.getCustomername());
        _tvbudget.setText(lead.getBudget());
        if(lead.getAppoimentdate().equals(""))
        _tvappointment.setText("NA");
        else
            _tvappointment.setText(lead.getAppoimentdate());
        if(lead.getImage()!=null) {
            final   Bitmap bmp = BitmapFactory.decodeByteArray(lead.getImage(), 0, lead.getImage().length);
            _ivLeadImage.post(new Runnable() {
                @Override
                public void run() {
                    //

                     _ivLeadImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, _ivLeadImage.getWidth(),
                     _ivLeadImage.getHeight(),false));
                    //_ivLeadImage.setImageDrawable(custom_rounded_border);

                }
            });
        }
        //   _tvLeadPhoneNumber.setText(lead.getPhone());
        if(lead.getStatus()==1)
        _tvLeadSattus.setText(" new ");
       else if(lead.getStatus()==2)
            _tvLeadSattus.setText("closed ");
        else if(lead.getStatus()==3)
            _tvLeadSattus.setText(" Follow-up ");
        else if(lead.getStatus()==4)
            _tvLeadSattus.setText(" Initiated ");
        else if(lead.getStatus()==5)
            _tvLeadSattus.setText("Re-followup");
        else if(lead.getStatus()==6)
            _tvLeadSattus.setText(" Discarder ");
        else if(lead.getStatus()==7)
            _tvLeadSattus.setText(" Not interested ");

      /*  _view_btn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              int id=lead.getId();
                                              Log.d("i am ",id+"clicked");
                                             // Fragment fragment = new LeadDetailsFragment();
                                             // FragmentManager fragmentManager = ((Activity) mContext).getFragmentManager();
                                             // fragmentManager.beginTransaction().replace(R.id.main_activity, fragment).commit();

                                            // LeadDetailsFragment fragment2 = new LeadDetailsFragment();
                                              //android.app.FragmentManager fragmentManager = mftagment.getFragmentManager();
                                              //android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                              //fragmentTransaction.add(R.id.container, new LeadDetailsFragment());
                                              //fragmentTransaction.commit();
                                          }

                                      });*/

                //tv.setText(mArrayList[position]);
        return myView;
    }
}
