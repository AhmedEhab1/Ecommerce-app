package com.macaria.app.ui.homeScreen.profile.savedAddresses.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.macaria.app.R;
import com.macaria.app.databinding.AddAddressFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.authorization.forgetPassword.model.ChangePasswordRequest;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddAddressRequest;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.AddressModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.models.CitiesModel;
import com.macaria.app.ui.homeScreen.profile.savedAddresses.vm.AddAddressViewModel;
import com.macaria.app.utilities.MyHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddAddressFragment extends Fragment {
    private AddAddressFragmentBinding binding ;
    private AddAddressViewModel viewModel;
    private int cityId = 0;

    public AddAddressFragment() {
        // Required empty public constructor
    }

    @Inject
    MyHelper helper ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AddAddressFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        viewModel = new ViewModelProvider(this).get(AddAddressViewModel.class);
        getCities();
    }

    private void getCities(){
        helper.showLoading(getActivity());
        viewModel.getCities();
        citiesResponse();
    }


    private void citiesResponse() {
        viewModel.getCitiesResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel<List<CitiesModel>>>() {
            @Override
            public void onChanged(BaseModel<List<CitiesModel>> listBaseModel) {
                helper.dismissLoading();
                setCountrySpinnerAdapter(binding.spinner, listBaseModel.getItem().getData());
            }
        });
    }

    private void addAddress() {
        String address = binding.address.getText().toString();
        String zip_code = binding.postalCode.getText().toString();
        String building_number = binding.buildingNumber.getText().toString();
        String apartment_no = binding.apartmentNo.getText().toString();
        String flatNo = binding.flatNo.getText().toString();
        String first_name = binding.firstName.getText().toString();
        String last_name = binding.lastName.getText().toString();
        String phone = binding.phoneNumber.getText().toString();
    }


    public void setCountrySpinnerAdapter(Spinner spinner, List<CitiesModel> models) {
        List<String> listSpinner = new ArrayList<>();
        List<Integer> countriesIds = new ArrayList<>();
        listSpinner.add(getString(R.string.select_city));
        for (int i = 0; i < models.size(); i++) {
            listSpinner.add(models.get(i).getName());
            countriesIds.add(models.get(i).getId());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, listSpinner) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = null;
                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                } else {
                    v = super.getDropDownView(position, null, parent);
                }
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    cityId = countriesIds.get(i - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}