package com.example.projetseg2505;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class InformationList extends ArrayAdapter<Requirement<String>> {
    private Activity context;
    List<Requirement<String>> requirements;
    List<String> infoBits;

    public InformationList(Activity context, List<Requirement<String>> requirements){
        //constructor
        super(context, R.layout.activity_create_request3, requirements);
        this.context = context;
        this.requirements = requirements;
        this.infoBits = new ArrayList<>();
        for(int i = 0; i < requirements.size(); i++){
            infoBits.add("");
        }
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_requirement_list, null, true);

        TextView textViewRequirementName = (TextView) listViewItem.findViewById(R.id.textViewReqName);
        EditText editTextInfo = (EditText) listViewItem.findViewById(R.id.editTextInfoForReq);

        Requirement requirement = requirements.get(position);
        editTextInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                infoBits.set(position, s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                infoBits.set(position, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                infoBits.set(position, s.toString());
            }
        });
        textViewRequirementName.setText(requirement.getName());
        return listViewItem;
    }

    public List<String> getInfoBits(){
        return this.infoBits;
    }

}
