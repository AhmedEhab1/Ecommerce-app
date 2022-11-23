package com.macaria.app.ui.homeScreen.profile.savedAddresses.fragments;

import static com.macaria.app.utilities.InputValidatorHelper.isEmptyString;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
    private AddAddressFragmentBinding binding;
    private AddAddressViewModel viewModel;
    private int cityId = 0;
    private boolean updateAddress = false;

    public AddAddressFragment() {
        // Required empty public constructor
    }

    @Inject
    MyHelper helper;

    @Inject
    AddAddressRequest addAddressRequest ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AddAddressFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(AddAddressViewModel.class);
        getCities();
        errorMessage();
        binding.saveAddress.setOnClickListener(view -> addAddress());
        getAddressData();
    }

    private void getAddressData(){
        if (getArguments() !=null){
            AddressModel model =(AddressModel) getArguments().getSerializable("addressModel");
            setAddressData(model);
            updateAddress = true;
        }
    }

    private void getCities() {
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
        addAddressRequest.setAddress(binding.address.getText().toString());
        addAddressRequest.setZip_code(binding.postalCode.getText().toString());
        addAddressRequest.setBuilding_number(binding.buildingNumber.getText().toString());
        addAddressRequest.setApartment_no( binding.apartmentNo.getText().toString());
        addAddressRequest.setFlat_no(binding.flatNo.getText().toString());
        addAddressRequest.setFirst_name(binding.firstName.getText().toString());
        addAddressRequest.setLast_name(binding.lastName.getText().toString());
        addAddressRequest.setPhone(binding.phoneNumber.getText().toString());
        addAddressRequest.setCity_id(String.valueOf(cityId));
        checkIsDataValid();
    }

    private void checkIsDataValid(){
        if (isEmptyString(addAddressRequest.getAddress())) {
            helper.showToast(requireActivity(), "please add address");
        } else if (isEmptyString(addAddressRequest.getZip_code())) {
            helper.showToast(requireActivity(), "please add postal code");
        } else if (isEmptyString(addAddressRequest.getBuilding_number())) {
            helper.showToast(requireActivity(), "please add building number");
        } else if (isEmptyString(addAddressRequest.getBuilding_number())) {
            helper.showToast(requireActivity(), "please add apartment number");
        } else if (isEmptyString(addAddressRequest.getFlat_no())) {
            helper.showToast(requireActivity(), "please add flat number");
        }else if (isEmptyString(addAddressRequest.getFirst_name())) {
            helper.showToast(requireActivity(), "please add first name");
        }else if (isEmptyString(addAddressRequest.getLast_name())) {
            helper.showToast(requireActivity(), "please add last name");
        }else if (isEmptyString(addAddressRequest.getPhone())) {
            helper.showToast(requireActivity(), "please add phone number");
        }else if (cityId == 0) {
            helper.showToast(requireActivity(), "please choose city");
        }else {
            AddAddressReq();
        }
    }

    private void AddAddressReq(){
        helper.showLoading(requireActivity());
        if (updateAddress) viewModel.updateAddress(addAddressRequest);
        else  viewModel.addAddress(addAddressRequest);
        addAddressResponse();
    }

    private void addAddressResponse(){
        viewModel.getAddAddressResponse().observe(getViewLifecycleOwner(), new Observer<BaseModel>() {
            @Override
            public void onChanged(BaseModel model) {
                helper.dismissLoading();
                back();
            }
        });
    }

    public void back(){
        Navigation.findNavController(requireView()).popBackStack();
    }

    private void errorMessage(){
        viewModel.getErrorMassage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                helper.showErrorDialog(getActivity() , null , s);
            }
        });
    }

    private void setAddressData(AddressModel model){
        addAddressRequest.setId(model.getId());
        cityId = model.getCityId();
        binding.address.setText(model.getAddress());
        binding.postalCode.setText(model.getZipCode());
        binding.buildingNumber.setText(model.getBuildingNumber());
        binding.apartmentNo.setText(model.getApartmentNumber());
        binding.flatNo.setText(model.getFloorNumber());
        binding.firstName.setText(model.getFirstName());
        binding.lastName.setText(model.getLastName());
        binding.phoneNumber.setText(model.getPhone());
    }

    public void setCountrySpinnerAdapter(Spinner spinner, List<CitiesModel> models) {
        List<String> listSpinner = new ArrayList<>();
        List<Integer> Ids = new ArrayList<>();
        listSpinner.add(getString(R.string.select_city));
        for (int i = 0; i < models.size(); i++) {
            listSpinner.add(models.get(i).getName());
            Ids.add(models.get(i).getId());
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
        if (updateAddress) {
            binding.spinner.setSelection(getSelectedPlace(String.valueOf(cityId), Ids));
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    cityId = Ids.get(i - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private int getSelectedPlace(String id, List<Integer> places) {
        for (int i = 0; i < places.size(); i++) {
            if (id.equals(String.valueOf(places.get(i))))
                return i + 1;
        }
        return 0;
    }
}