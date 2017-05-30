package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Expense;

/**
 * Created by cpaier on 29/05/2017.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    public static MapFragment newInstance() {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (final Expense expense : SQLite.select().from(Expense.class).queryList()) {
            Log.e("TAG", "lat" + expense.latitude + " " + expense.longitude);
            if (expense.latitude > 0 && expense.longitude > 0) {
                LatLng ll = new LatLng(expense.latitude, expense.longitude);
                googleMap.addMarker(new MarkerOptions().position(ll).title(expense.name));
                builder.include(ll);
            }
        }

        LatLngBounds bounds = builder.build();
        int padding = 0;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.animateCamera(cu);
    }
}
