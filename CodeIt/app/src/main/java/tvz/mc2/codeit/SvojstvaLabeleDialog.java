package tvz.mc2.codeit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import tvz.mc2.codeit.RazinaDva;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by Boki on 26.4.2015..
 */

public class SvojstvaLabeleDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_svojstva_labele, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //View view2 = (View) findViewById(R.layout.activity_razina_dva);
                        EditText userNameEditText = (EditText) view.findViewById(R.id.editTextSvojstvaLabeleR2);
                        //TextView labela = (TextView)

                        RazinaDva.promjeniSvojstva(userNameEditText.getText().toString());
                        /*EditText passwordEditText = (EditText) view.findViewById(R.id.editText2);
                        if ( userNameEditText.getText().length() == 0 || passwordEditText.getText().length() == 0 ) {
                            Toast.makeText(getActivity(), "Korisničko ime i/ili lozinka nisu uneseni.", Toast.LENGTH_LONG).show();
                        }
                    */
                    }
                })
                .setNegativeButton("Odustani", null);
        return builder.create();

    }
}
