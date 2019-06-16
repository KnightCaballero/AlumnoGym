package com.examen.alumnogym;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class AlumnoListActivity extends SingleFragmentActivity
        implements AlumnoListFragment.Callbacks, AlumnoFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new AlumnoListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onAlumnoSelected(Alumno alumno) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = AlumnoPagerActivity.newIntent(this, alumno.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = AlumnoFragment.newInstance(alumno.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    public void onAlumnoUpdated(Alumno alumno) {
        AlumnoListFragment listFragment = (AlumnoListFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
