package com.aviom.aviomplay.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aviom.aviomplay.Adapters.MyLeadsListAdapter;
import com.aviom.aviomplay.Helpers.DatabaseHelper;
import com.aviom.aviomplay.Models.Lead;
import com.aviom.aviomplay.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Admin on 2017-02-17.
 */

public class MyleadsFragement extends Fragment {

    private final String TAG = "ListScientistsFragment";
  //  private int[] image = {R.drawable.curie,R.drawable.edison,R.drawable.einstein,R.drawable.faraday,R.drawable.galileo,R.drawable.hawking,R.drawable.kepler,R.drawable.newton,R.drawable.tesla};
    private ArrayList<Lead> mScientists;
    private RecyclerView mScientistRecyclerView;
    private ScientistAdapter mAdapter;
    public List<Lead> leadsData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        DatabaseHelper db = new DatabaseHelper(getActivity());
        leadsData=db.getAllLeads();

        mScientists = new ArrayList<>();
        for(int i =0;i<leadsData.size();i++){
            Lead s = new Lead();

            s.setId(leadsData.get(i).getId());
            s.setCustomername(leadsData.get(i).getCustomername());
            s.setImage(leadsData.get(i).getImage());
            s.setPhone(leadsData.get(i).getPhone());
            s.setBudget(leadsData.get(i).getBudget());
            s.setStatus(leadsData.get(i).getStatus());
            s.setLocation(leadsData.get(i).getLocation());
            s.setCreated_on(leadsData.get(i).getCreated_on());
            s.setAppoimentdate(leadsData.get(i).getAppoimentdate());



            mScientists.add(s);
        }
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_myleads, container, false);
        mScientistRecyclerView = (RecyclerView) view
                .findViewById(R.id.myLeadsListView);
     //   mScientistRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        mScientistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }
    private void updateUI(){
        mAdapter = new ScientistAdapter(mScientists);
        mScientistRecyclerView.setAdapter(mAdapter);
    }
    class ScientistHolder extends RecyclerView.ViewHolder{
        private Lead mScientist;
        public TextView _tvLeadCustomerName,_tvLeadSattus,_tvphone,_tvCity,_tvbudget,_tvappointment;
        public ImageView _ivLeadImage;
        public TextView _tvLeadCreatedOn;
        public ImageButton _viewBtn;

      //  public ImageView mImageView;
     //   public TextView mNameTextView;
      //  public TextView mBirthDeathTextView;
        public ScientistHolder(View itemView){
            super(itemView);
          //  mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            _tvLeadCustomerName= (TextView) itemView.findViewById(R.id.tvLabelCustomername);
             _tvLeadCreatedOn = (TextView)itemView.findViewById(R.id.tvLeadCreatedOn);
            //  TextView _tvLeadPhoneNumber = (TextView)myView.findViewById(R.id.tvPhonNumber);
            _tvLeadSattus = (TextView)itemView.findViewById(R.id.tvLeadStatus);
             _tvphone = (TextView)itemView.findViewById(R.id.tvphone);
            _ivLeadImage=(ImageView)itemView.findViewById(R.id.user_profile_photo);
             _tvCity=(TextView) itemView.findViewById(R.id.tvcity);
             _tvbudget=(TextView) itemView.findViewById(R.id.tvbudget);
             _tvappointment=(TextView) itemView.findViewById(R.id.tvappointment);
            _viewBtn=(ImageButton)itemView.findViewById(R.id.view_btn) ;

            // mBirthDeathTextView = (TextView) itemView.findViewById(R.id.textview_birth_death);
            _viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  Toast.makeText(getActivity(),
                          //  mScientist.getId() + " clicked!", Toast.LENGTH_SHORT)
                           // .show();

                    android.support.v4.app.Fragment fr;
                    fr = new LeadDetailsFragment();
                    FragmentManager fm = getFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putInt("leadid", mScientist.getId());
                    fr.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.container, fr);
                    fragmentTransaction.commit();

                }
            });
        }
        public void bindData(Lead s){
            mScientist = s;
           // mImageView.setImageResource(s.getImageId());
            _tvLeadCustomerName.setText(s.getCustomername());
            _tvLeadCreatedOn.setText(s.getCreated_on());
            _tvCity.setText(s.getLocation());
            _tvphone.setText(s.getPhone());
            _tvLeadCustomerName.setText(s.getCustomername());
            _tvbudget.setText(s.getBudget());
            if(s.getAppoimentdate().equals(""))
                _tvappointment.setText("NA");
            else
                _tvappointment.setText(s.getAppoimentdate());
            if(s.getImage()!=null) {
                final Bitmap bmp = BitmapFactory.decodeByteArray(s.getImage(), 0, s.getImage().length);
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
            if(s.getStatus()==1)
                _tvLeadSattus.setText(" new ");
            else if(s.getStatus()==2)
                _tvLeadSattus.setText(" closed ");
            else if(s.getStatus()==3)
                _tvLeadSattus.setText(" Follow-up ");
            else if(s.getStatus()==4)
                _tvLeadSattus.setText(" Initiated ");
            else if(s.getStatus()==5)
                _tvLeadSattus.setText(" Re-followup ");
            else if(s.getStatus()==6)
                _tvLeadSattus.setText(" Discarder ");
            else if(s.getStatus()==7)
                _tvLeadSattus.setText(" Not interested ");
          //  mBirthDeathTextView.setText(s.getBirthYear()+"-"+s.getDeathYear());

        }
    }
    private class ScientistAdapter extends RecyclerView.Adapter<ScientistHolder>{
        private ArrayList<Lead> mScientists;
        public ScientistAdapter(ArrayList<Lead> Scientists){
            mScientists = Scientists;
        }
        @Override
        public ScientistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.leads_list_item,parent,false);
            return new ScientistHolder(view);
        }
        @Override
        public void onBindViewHolder(ScientistHolder holder, int position) {
            Lead s = mScientists.get(position);
            holder.bindData(s);
        }
        @Override
        public int getItemCount() {
            return mScientists.size();
        }
    }

}
