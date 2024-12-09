package com.nixie.sisuratmob.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nixie.sisuratmob.Helpers.Helpers;
import com.nixie.sisuratmob.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView nik = view.findViewById(R.id.textnikf);
        TextView nama = view.findViewById(R.id.txt_namaprofilef);
        ImageView iv = view.findViewById(R.id.txt_homefotoprofil);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        String namalengkap = sharedPreferences.getString("namalengkap", "");
        String niks = sharedPreferences.getString("nik", "");
        String foto = sharedPreferences.getString("foto", "");
        Glide.with(getContext())
                .load(Helpers.BASE_URL+"admin/assetsprofile/"+foto)
                .placeholder(R.drawable.baground_rtrw)
                .error(R.drawable.baground_rtrw)
                .into(iv);
        nik.setText(niks);
        nama.setText(namalengkap);
        view.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("isLoggedIn");
                editor.remove("nik");
                editor.remove("role");
                editor.remove("namalengkap");
                editor.remove("nokk");
                editor.remove("email");
                editor.remove("no_hp");
                editor.apply();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        view.findViewById(R.id.informasiAkun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InformasiAkunActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.iubahemal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UbahEmailActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.UbahNoTelepon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UbahNoTeleponActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.UbahPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UbahPasswordActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.DaftarKeluarga).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListKeluargaActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.KelurahanBadean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = -7.9181419;
                double longitude = 113.8151781;  // Ganti dengan longitude yang diinginkan
                String label = "KANTOR KELURAHAN BADEAN";  // Ganti dengan nama lokasi yang diinginkan
                
                String geoUri = "geo:0,0?q=" + latitude + "," + longitude + "(" + label + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));

                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Google Maps tidak ditemukan!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.SuratDibatalkan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DibatalkanActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}