package com.examen.alumnogym;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AlumnoListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mAlumnoRecyclerView;
    private AlumnoAdapter mAdapter;
    private boolean mSubtitleVisible;
    private Callbacks mCallbacks;

    /**
     * Required interface for hosting activities.
     */
    public interface Callbacks {
        void onAlumnoSelected(Alumno alumno);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mAlumnoRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mAlumnoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Alumno alumno = new Alumno();
                AlumnoLab.get(getActivity()).addAlumno(alumno);
                updateUI();
                mCallbacks.onAlumnoSelected(alumno);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        AlumnoLab alumnoLab = AlumnoLab.get(getActivity());
        int alumnoCount = alumnoLab.getAlumno().size();
        @SuppressLint("StringFormatMatches") String subtitle = getString(R.string.subtitle_format, alumnoCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    public void updateUI() {
        AlumnoLab alumnoLab = AlumnoLab.get(getActivity());
        List<Alumno> alumno = alumnoLab.getAlumno();

        if (mAdapter == null) {
            mAdapter = new AlumnoAdapter(alumno);
            mAlumnoRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setAlumno(alumno);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class AlumnoHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Alumno mAlumno;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        public AlumnoHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }

        public void bind(Alumno alumno) {
            mAlumno = alumno;
            mTitleTextView.setText(mAlumno.getTitle());
            mDateTextView.setText(mAlumno.getDate().toString());
            mSolvedImageView.setVisibility(alumno.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            mCallbacks.onAlumnoSelected(mAlumno);
        }
    }

    private class AlumnoAdapter extends RecyclerView.Adapter<AlumnoHolder> {

        private List<Alumno> mAlumno;

        public AlumnoAdapter(List<Alumno> alumnos) {
            mAlumno = alumnos;
        }

        @Override
        public AlumnoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new AlumnoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(AlumnoHolder holder, int position) {
            Alumno alumno = mAlumno.get(position);
            holder.bind(alumno);
        }

        @Override
        public int getItemCount() {
            return mAlumno.size();
        }

        public void setAlumno(List<Alumno> alumnos) {
            mAlumno = alumnos;
        }
    }
}
