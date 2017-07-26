package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nick.finalyearproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Renter.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Renter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Renter extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button locationButton ;
    EditText longitude;
    EditText latitude;
    EditText location;
    CheckBox two;
    CheckBox four;
    EditText twowheeler;
    EditText fourwheeler;
    LinearLayout l1,l2;
    Button timeButton;
    LinearLayout l3,l4,l5;
    CheckBox all,mon,tue,wed,thu,fri,sat,sun;
    EditText from,to;
    EditText chargeText;
    Button submitButton;


    private OnFragmentInteractionListener mListener;

    public Renter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Renter.
     */
    // TODO: Rename and change types and number of parameters
    public static Renter newInstance(String param1, String param2) {
        Renter fragment = new Renter();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment'

        final View view  = inflater.inflate(R.layout.fragment_renter, container, false);


         locationButton=(Button)view.findViewById(R.id.location_button);
         longitude=(EditText)view.findViewById(R.id.longitude_text);
         latitude=(EditText)view.findViewById(R.id.latitude_text);
         location=(EditText)view.findViewById(R.id.location_text);
         two=(CheckBox)view.findViewById(R.id.two);
         four=(CheckBox)view.findViewById(R.id.four);
         twowheeler=(EditText)view.findViewById(R.id.twowheeler);
         fourwheeler=(EditText)view.findViewById(R.id.fourwheeler);
        l1=(LinearLayout)view.findViewById(R.id.editTextHolder1) ;
        l2=(LinearLayout)view.findViewById(R.id.editTextHolder2) ;

        timeButton=(Button)view.findViewById(R.id.timeButton);
        l3=(LinearLayout)view.findViewById(R.id.holder3) ;
        l4=(LinearLayout)view.findViewById(R.id.holder4) ;
        l5=(LinearLayout)view.findViewById(R.id.holder5) ;
        all=(CheckBox)view.findViewById(R.id.all);
        mon=(CheckBox)view.findViewById(R.id.mon);
        tue=(CheckBox)view.findViewById(R.id.tue);
        wed=(CheckBox)view.findViewById(R.id.wed);
        thu=(CheckBox)view.findViewById(R.id.thu);
        fri=(CheckBox)view.findViewById(R.id.fri);
        sat=(CheckBox)view.findViewById(R.id.sat);
        sun=(CheckBox)view.findViewById(R.id.sun);

        two.setChecked(false);
        four.setChecked(false);
        l1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);


        all.setChecked(false);
        mon.setChecked(false);
        tue.setChecked(false);
        wed.setChecked(false);
        thu.setChecked(false);
       fri.setChecked(false);
        sat.setChecked(false);
        sun.setChecked(false);

        l3.setVisibility(View.GONE);
        l4.setVisibility(View.GONE);
        l5.setVisibility(View.GONE);

        from=(EditText)view.findViewById(R.id.from);
        to=(EditText)view.findViewById(R.id.to);
        chargeText=(EditText)view.findViewById(R.id.chargeText);
        submitButton=(Button)view.findViewById(R.id.submitButton);




        /*two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(two.isChecked())
                {
                    l1.setVisibility(View.VISIBLE);
                    twowheeler.setHint("Enter Number of Two-Wheelers");
                }
                else
                {
                    l1.setVisibility(View.GONE);
                }



            }
        });

        four.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(four.isChecked())
                {
                    l2.setVisibility(View.VISIBLE);
                    fourwheeler.setHint("Enter Number of Four-Wheelers");
                }
                else
                {
                    l2.setVisibility(View.GONE);
                }



            }
        });*/

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int count=0;

                if((count%2)==0) {
                    l3.setVisibility(View.VISIBLE);
                    l4.setVisibility(View.VISIBLE);
                    l5.setVisibility(View.VISIBLE);
                    count++;
                }
                else
                {
                    l3.setVisibility(View.GONE);
                    l4.setVisibility(View.GONE);
                    l5.setVisibility(View.GONE);
                    count++;

                }
            }
        });



        submitButton.setOnClickListener(this);



        return view ;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



        @Override
    public void onClick(View v) {
            Log.d("Clicked","Submit Clicked");
            Toast.makeText(getActivity(),"Submit Clicked",Toast.LENGTH_LONG).show();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
