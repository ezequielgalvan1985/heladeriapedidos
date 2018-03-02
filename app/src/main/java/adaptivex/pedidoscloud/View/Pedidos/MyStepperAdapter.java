package adaptivex.pedidoscloud.View.Pedidos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import adaptivex.pedidoscloud.R;

/**
 * Created by egalvan on 9/2/2018.
 */
public class MyStepperAdapter extends AbstractFragmentStepAdapter {
    private String CURRENT_STEP_POSITION_KEY = "0";
    public MyStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        final CargarDireccionFragment step = new CargarDireccionFragment();
        Bundle b = new Bundle();

        b.putInt(CURRENT_STEP_POSITION_KEY, position);
        step.setArguments(b);
        return step;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return new StepViewModel.Builder(context)
                .setTitle("Carga Direccion") //can be a CharSequence instead
                .create();
    }
}