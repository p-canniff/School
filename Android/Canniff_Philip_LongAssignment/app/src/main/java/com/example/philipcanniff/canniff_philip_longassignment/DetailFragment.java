package com.example.philipcanniff.canniff_philip_longassignment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by philipcanniff on 9/10/15.
 */
public class DetailFragment extends Fragment {

    public static final String TAG = "DetailFragment.tag";

    private PersonClass person;
    private int placement;



    public static DetailFragment newInstance(PersonClass _person, int _placement){

        DetailFragment frag = new DetailFragment();

        Bundle args = new Bundle();
        args.putSerializable("Person", _person);
        args.putInt("Placement", _placement);
        frag.setArguments(args);

        return frag;

    }

    DetailInterfacing inputView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){

            person = (PersonClass) getArguments().getSerializable("Person");
            placement = getArguments().getInt("Placement");


        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DetailInterfacing){

            inputView = (DetailInterfacing) activity;
        }
    }

    public interface DetailInterfacing{

        public void sendDataToInput(boolean editing, PersonClass _person, int _placement);
        public void recallList();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_layout, container, false);

        TextView name = (TextView) view.findViewById(R.id.nameTextField);
        final TextView field = (TextView) view.findViewById(R.id.fieldView);
        TextView years = (TextView) view.findViewById(R.id.yearsView);
        TextView skills = (TextView) view.findViewById(R.id.skillsView);
        Button back = (Button) view.findViewById(R.id.backDetail);
        Button deletePerson = (Button) view.findViewById(R.id.deleteButton);
        Button updatePerson = (Button) view.findViewById(R.id.userUpdate);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().findViewById(R.id.inputUser).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.image_layout).setVisibility(View.VISIBLE);

                inputView.recallList();



            }
        });




        deletePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataManager.getInstance(getActivity()).deletePersons(placement);

                getActivity().findViewById(R.id.inputUser).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.image_layout).setVisibility(View.VISIBLE);

                inputView.recallList();


            }
        });


        updatePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputView.sendDataToInput(true, person, placement);

            }
        });

        name.setText(person.getName());
        field.setText(person.getLast());
        years.setText(String.valueOf(person.getYears()));
        skills.setText(person.getSkill1() + "\n" + person.getSkill2());

        return view;

    }
}
