package tvz.mc2.codeit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import tvz.mc2.codeit.RazinaDva;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Boki on 26.4.2015..
 */

public class SvojstvaLabeleDialog extends DialogFragment {

    Spinner spinner;
    String boja;
    int velicina;

    @Override
    public Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_svojstva_labele, null);

        spinner = (Spinner) view.findViewById(R.id.spinnerSvojstvaLabeleR2);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.labela_boje, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText tekstLabele = (EditText) view.findViewById(R.id.editTextSvojstvaLabeleR2);
                        EditText velicinaLabele = (EditText) view.findViewById(R.id.editTextVelicinaLabeleR2);

                        if (velicinaLabele.getText().toString().equals(""))
                        {
                            velicina = 0;
                        }

                        else
                        {
                            velicina = Integer.valueOf(velicinaLabele.getText().toString());
                        }

                        boja = (String) spinner.getSelectedItem();
                        RazinaDva.promjeniSvojstva(tekstLabele.getText().toString(), boja, velicina);

                    }
                })
                .setNegativeButton("Odustani", null);
        return builder.create();

    }

}
