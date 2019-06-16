package com.examen.alumnogym;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class AlumnoPagerActivity extends AppCompatActivity
        implements AlumnoFragment.Callbacks {

    private static final String EXTRA_ALUMNO_ID =
            "com.examen.alumnogym.alumno_id";

    private ViewPager mViewPager;
    private List<Alumno> mAlumno;

    public static Intent newIntent(Context packageContext, UUID alumnoId) {
        Intent intent = new Intent(packageContext, AlumnoPagerActivity.class);
        intent.putExtra(EXTRA_ALUMNO_ID, alumnoId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID alumnoId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_ALUMNO_ID);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);

        mAlumno = AlumnoLab.get(this).getAlumno();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Alumno alumno = mAlumno.get(position);
                return AlumnoFragment.newInstance(alumno.getId());
            }

            @Override
            public int getCount() {
                return mAlumno.size();
            }
        });

        for (int i = 0; i < mAlumno.size(); i++) {
            if (mAlumno.get(i).getId().equals(alumnoId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public void onAlumnoUpdated(Alumno alumno) {

    }
}
