/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.zhdata.android.zhapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class SuperAwesomeCardFragment extends Fragment {

	private static final String ARG_POSITION = "position";
	private ListView lv,lv1,lv2;
	EditText editText;
	SearchBar parentActivity;

	private String Sid;
	private int position;

	public static SuperAwesomeCardFragment newInstance(int position) {
		SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//问题搜索
		View rootView = inflater.inflate(R.layout.fragment_card,container,false);
		ViewCompat.setElevation(rootView, 50);
			parentActivity =(SearchBar) getActivity();
		    Sid=parentActivity.Sid;

				lv=(ListView)rootView.findViewById(R.id.search_list);
		switch (position){
			case 0:
				lv.setAdapter(parentActivity.arrayAdapter);
				break;
			case 1:
				lv.setAdapter(parentActivity.arrayAdapter1);
				break;
			case 2:
				lv.setAdapter(parentActivity.arrayAdapter2);
				break;
		}
				//lv.setAdapter(parentActivity.arrayAdapter);
		switch (position){
			case 0:

				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
						Intent intent=new Intent(getActivity(),Question_page_Activity.class);
						intent.putExtra("Qid",parentActivity.getdatalist1.get(i));
						intent.putExtra("Sid",Sid);
						startActivity(intent);
					}
				});
				break;
			case 1:

				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
						Intent intent=new Intent(getActivity(),TopicActivity.class);
						intent.putExtra("Tid",parentActivity.getdatalist.get(i)+"");
						intent.putExtra("Tname",parentActivity.datalist1.get(i)+"");
						intent.putExtra("Sid",Sid);
						startActivity(intent);
					}
				});
				break;
			case 2:
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
						Intent intent=new Intent(getActivity(),MoreActivity.class);
						if (parentActivity.Sid.equals(parentActivity.getdatalist1.get(i))){
							intent.putExtra("Isme",true);
						}else {
							intent.putExtra("Isme",false);
						}
						intent.putExtra("user_id",parentActivity.getdatalist1.get(i));
						startActivity(intent);
					}
				});
				break;

		}

		return rootView;
	}
}