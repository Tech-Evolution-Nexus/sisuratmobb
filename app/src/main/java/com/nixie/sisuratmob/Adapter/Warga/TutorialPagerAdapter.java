package com.nixie.sisuratmob.Adapter.Warga;

// TutorialPagerAdapter.java // Sesuaikan dengan package Anda

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.nixie.sisuratmob.R;

public class TutorialPagerAdapter extends PagerAdapter {
    private Context context;
    private int[] images = {
            R.drawable.login, // Ganti dengan nama gambar Anda
            R.drawable.register,
            R.drawable.actv
    };

    private String[] titles = {
            "Login",
            "Register",
            "Aktivasi"
    };

    private String[] descriptions = {
            "Kami akan memandu warga yang sudah terdaftar untuk melakukan login ke aplikasi. Cukup masukkan NIK dan kata sandi yang telah Anda daftarkan. Jika data anda sudah terdaftar sebelumnya bisa langsung Login Menuju Aplikasi E-surat.",
            "Bagi warga baru diharapkan Register terlebih dahulu dan Pastikan Anda mengisi semua informasi yang diperlukan dengan benar, termasuk Nik, nomor telepon, dan kata sandi. ",
            "Bagi warga yang sudah Terdaftar akan melanjutkan Aktifasi akun, Proses aktifasi akun sangat penting untuk memastikan keamanan dan keaslian pengguna.Pastikan untuk memeriksa notifikasi dan menyelesaikan aktifasi agar dapat mengakses semua fitur aplikasi."
    };

    public TutorialPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImage = view.findViewById(R.id.slideImage);
        TextView slideTitle = view.findViewById(R.id.slideTitle);
        TextView slideDescription = view.findViewById(R.id.slideDescription);

        slideImage.setImageResource(images[position]);
        slideTitle.setText(titles[position]);
        slideDescription.setText(descriptions[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object obj) {
        container.removeView((View) obj);
    }
}

