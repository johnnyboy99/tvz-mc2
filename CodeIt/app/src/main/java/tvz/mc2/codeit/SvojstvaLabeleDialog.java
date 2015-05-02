package tvz.mc2.codeit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.widget.Spinner;


/**
 * Created by Boki on 26.4.2015..
 */

public class SvojstvaLabeleDialog extends DialogFragment {

    Spinner spinner;
    String boja;
    int velicina;
    EditText tekstLabele, velicinaLabele;
    Integer bijela;

    @Override
    public Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
        bijela = getResources().getColor(R.color.bijela);
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_svojstva_labele, null);

        spinner = (Spinner) view.findViewById(R.id.spinnerSvojstvaLabeleR2);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.bojeSpinner3, R.layout.unsimple_spinner_item);
        spinner.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setPositiveButton(R.string.poz, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tekstLabele = (EditText) view.findViewById(R.id.editTextSvojstvaLabeleR2);
                        velicinaLabele = (EditText) view.findViewById(R.id.editTextVelicinaLabeleR2);

                        if (velicinaLabele.getText().toString().equals(""))
                        {
                            velicina = 0;
                        }

                        else
                        {
                            velicina = Integer.valueOf(velicinaLabele.getText().toString());

                            if (velicina < 10) velicina = 10;
                            else if (velicina > 150) velicina = 150;
                        }

                        boja = (String) spinner.getSelectedItem();
                        RazinaDva.promjeniSvojstva(tekstLabele.getText().toString(), boja, velicina);

                    }
                })
                .setNegativeButton(R.string.neg, null);

        return builder.create();

    }

}
