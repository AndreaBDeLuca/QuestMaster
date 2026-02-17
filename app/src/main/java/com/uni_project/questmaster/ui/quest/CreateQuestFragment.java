package com.uni_project.questmaster.ui.quest;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uni_project.questmaster.R;
import com.uni_project.questmaster.adapter.QuestMediaAdapter;
import com.uni_project.questmaster.model.QuestLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateQuestFragment extends Fragment implements OnMapReadyCallback, QuestMediaAdapter.OnImageDeleteListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private EditText editTextQuestName;
    private EditText editTextQuestDescription;
    private EditText editTextPpq;
    private Button buttonSaveQuest, buttonUploadImage;

    private MapView mapView;
    private GoogleMap googleMap;
    private RadioGroup mapOptionsRadioGroup;
    private View locationFieldsContainer, mapCard;
    private EditText startingPoint, endPoint, singleLocation;
    private Button search_single_location, search_start_point, search_end_point;

    private QuestLocation startLocation, endLocation, singleQuestLocation;
    private final List<Uri> imageUris = new ArrayList<>();
    private RecyclerView recyclerViewImages;
    private QuestMediaAdapter questMediaAdapter;

    private CreateQuestViewModel viewModel;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    if (result.getData().getClipData() != null) {
                        ClipData clipData = result.getData().getClipData();
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            imageUris.add(clipData.getItemAt(i).getUri());
                        }
                    } else if (result.getData().getData() != null) {
                        imageUris.add(result.getData().getData());
                    }
                    questMediaAdapter.notifyDataSetChanged();
                    recyclerViewImages.setVisibility(View.VISIBLE);
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quest_create, container, false);

        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            viewModel = new ViewModelProvider(this).get(CreateQuestViewModel.class);

            // VIEWS
            editTextQuestName = view.findViewById(R.id.edit_text_quest_name);
            editTextQuestDescription = view.findViewById(R.id.edit_text_quest_description);
            editTextPpq = view.findViewById(R.id.edit_text_ppq);
            buttonSaveQuest = view.findViewById(R.id.button_save_quest);
            buttonUploadImage = view.findViewById(R.id.button_upload_image);
            recyclerViewImages = view.findViewById(R.id.recycler_view_images);
            mapOptionsRadioGroup = view.findViewById(R.id.map_options_radiogroup);
            locationFieldsContainer = view.findViewById(R.id.location_fields_container);
            mapCard = view.findViewById(R.id.map_card);
            startingPoint = view.findViewById(R.id.startingPoint);
            endPoint = view.findViewById(R.id.endPoint);
            singleLocation = view.findViewById(R.id.singleLocation);
            search_single_location = view.findViewById(R.id.search_single_location);
            search_start_point = view.findViewById(R.id.search_start_point);
            search_end_point = view.findViewById(R.id.search_end_point);

            questMediaAdapter = new QuestMediaAdapter(getContext(), imageUris, this, true, null, getString(R.string.google_maps_key));
            recyclerViewImages.setAdapter(questMediaAdapter);
            recyclerViewImages.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            buttonSaveQuest.setOnClickListener(v -> saveQuest());
            buttonUploadImage.setOnClickListener(v -> openImagePicker());

            viewModel.questCreationResult.observe(getViewLifecycleOwner(), successful -> {
                if (successful) {
                    Toast.makeText(getContext(), "Quest created!", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(CreateQuestFragment.this).popBackStack();
                } else {
                    Toast.makeText(getContext(), "Error creating quest", Toast.LENGTH_SHORT).show();
                }
            });

            mapOptionsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId == R.id.radio_no_map) {
                    mapCard.setVisibility(View.GONE);
                    locationFieldsContainer.setVisibility(View.GONE);
                } else if (checkedId == R.id.radio_single_location) {
                    mapCard.setVisibility(View.VISIBLE);
                    locationFieldsContainer.setVisibility(View.VISIBLE);
                    singleLocation.setVisibility(View.VISIBLE);
                    startingPoint.setVisibility(View.GONE);
                    endPoint.setVisibility(View.GONE);
                    search_single_location.setVisibility(View.VISIBLE);
                    search_start_point.setVisibility(View.GONE);
                    search_end_point.setVisibility(View.GONE);
                } else if (checkedId == R.id.radio_start_end) {
                    mapCard.setVisibility(View.VISIBLE);
                    locationFieldsContainer.setVisibility(View.VISIBLE);
                    singleLocation.setVisibility(View.GONE);
                    startingPoint.setVisibility(View.VISIBLE);
                    endPoint.setVisibility(View.VISIBLE);
                    search_single_location.setVisibility(View.GONE);
                    search_start_point.setVisibility(View.VISIBLE);
                    search_end_point.setVisibility(View.VISIBLE);
                }
            });

            search_single_location.setOnClickListener(v -> searchLocation(singleLocation.getText().toString(), "single"));
            search_start_point.setOnClickListener(v -> searchLocation(startingPoint.getText().toString(), "start"));
            search_end_point.setOnClickListener(v -> searchLocation(endPoint.getText().toString(), "end"));
    }
    // LOCATION
    private void searchLocation(String locationName, String type) {
        if (locationName.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a location name", Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                QuestLocation questLocation = new QuestLocation(address.getLatitude(), address.getLongitude());
                if (type.equals("single")) {
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(questLocation.getLatitude(), questLocation.getLongitude())).title("Quest Location"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(questLocation.getLatitude(), questLocation.getLongitude()), 15));
                    singleQuestLocation = questLocation;
                    singleLocation.setText(address.getAddressLine(0));
                } else if (type.equals("start")) {
                    startLocation = questLocation;
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(questLocation.getLatitude(), questLocation.getLongitude())).title("Start Point"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(questLocation.getLatitude(), questLocation.getLongitude()), 15));
                    startingPoint.setText(address.getAddressLine(0));
                } else if (type.equals("end")) {
                    endLocation = questLocation;
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(questLocation.getLatitude(), questLocation.getLongitude())).title("End Point"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(questLocation.getLatitude(), questLocation.getLongitude()), 15));
                    endPoint.setText(address.getAddressLine(0));
                }
                updateMapPreview();
            } else {
                Toast.makeText(getContext(), "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Geocoder service not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMapPreview() {
        questMediaAdapter = new QuestMediaAdapter(getContext(), imageUris, this, true, singleQuestLocation, getString(R.string.google_maps_key));
        recyclerViewImages.setAdapter(questMediaAdapter);
    }


    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imagePickerLauncher.launch(intent);
    }

    @Override
    public void onImageDelete(int position) {
        imageUris.remove(position);
        questMediaAdapter.notifyItemRemoved(position);
        questMediaAdapter.notifyItemRangeChanged(position, imageUris.size());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        googleMap.setOnMapClickListener(latLng -> {
            int checkedId = mapOptionsRadioGroup.getCheckedRadioButtonId();
            QuestLocation questLocation = new QuestLocation(latLng.latitude, latLng.longitude);
            if (checkedId == R.id.radio_single_location) {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Quest Location"));
                singleQuestLocation = questLocation;
                singleLocation.setText(questLocation.getLatitude() + ", " + questLocation.getLongitude());
            } else if (checkedId == R.id.radio_start_end) {
                if (startLocation == null) {
                    startLocation = questLocation;
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("Start Point"));
                    startingPoint.setText(questLocation.getLatitude() + ", " + questLocation.getLongitude());
                } else if (endLocation == null) {
                    endLocation = questLocation;
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("End Point"));
                    endPoint.setText(questLocation.getLatitude() + ", " + questLocation.getLongitude());
                }
            }
            updateMapPreview();
        });
    }

    private void saveQuest() {
        String questName = editTextQuestName.getText().toString();
        String questDescription = editTextQuestDescription.getText().toString();
        int ppq = Integer.parseInt(editTextPpq.getText().toString());

        int checkedId = mapOptionsRadioGroup.getCheckedRadioButtonId();
        QuestLocation questLocation = null;
        if (checkedId == R.id.radio_single_location) {
            questLocation = singleQuestLocation;
        } else if (checkedId == R.id.radio_start_end) {
            // For now, let's just use the start location.
            // You might want to handle start and end locations differently.
            questLocation = startLocation;
        }

        viewModel.createQuest(questName, questDescription, ppq, imageUris, questLocation);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
