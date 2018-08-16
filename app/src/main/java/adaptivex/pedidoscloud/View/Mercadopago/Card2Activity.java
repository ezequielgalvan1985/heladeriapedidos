package adaptivex.pedidoscloud.View.Mercadopago;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Response;
import com.mercadopago.adapters.IdentificationTypesAdapter;
import com.mercadopago.core.MercadoPago;
import com.mercadopago.dialogs.CustomDatePickerDialog;
import com.mercadopago.model.CardToken;
import com.mercadopago.model.IdentificationType;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.Token;
import com.mercadopago.util.ApiUtil;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.util.LayoutUtil;
import com.mercadopago.util.MercadoPagoUtil;

import java.util.Calendar;
import java.util.List;

import adaptivex.pedidoscloud.R;
import retrofit.Callback;
import retrofit.RetrofitError;

public class Card2Activity extends AppCompatActivity implements CustomDatePickerDialog.DatePickerDialogListener  {
    private PaymentMethod paymentMethod;
    private EditText cardNumber;
    private EditText securityCode;
    private Button expiryDate;
    private EditText cardHolderName;
    private EditText identificationNumber;
    private Spinner identificationType;
    private RelativeLayout identificationLayout;
    private CardToken cardToken;
    private Calendar selectedExpiryDate;
    private int expiryMonth;
    private int expiryYear;
    private Activity activity;
    private String exceptionOnMethod;
    private MercadoPago mercadoPago;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_card2);
            activity = this;
            String mMerchantPublicKey = this.getIntent().getStringExtra("TEST-924835fe-e6df-4199-baa0-26ba59811a31");
            cardNumber                = (EditText) findViewById(R.id.cardNumber);
            securityCode              = (EditText) findViewById(R.id.securityCode);
            cardHolderName            = (EditText) findViewById(R.id.cardholderName);
            identificationNumber      = (EditText) findViewById(R.id.identificationNumber);
            identificationType        = (Spinner) findViewById(R.id.identificationType);
            identificationLayout      = (RelativeLayout) findViewById(R.id.identificationLayout);
            expiryDate                = (Button) findViewById(R.id.expiryDateButton);

            mercadoPago               = new MercadoPago.Builder()
                    .setContext(activity)
                    .setPublicKey(mMerchantPublicKey)
                    .build();

            setIdentificationNumberKeyboardBehavior();
            setErrorTextCleaner(cardHolderName);
            paymentMethod = JsonUtil.getInstance().fromJson(this.getIntent().getStringExtra("paymentMethod"), PaymentMethod.class);
            if (paymentMethod.getId() != null) {
                ImageView pmImage = (ImageView) findViewById(com.mercadopago.R.id.pmImage);
                pmImage.setImageResource(MercadoPagoUtil.getPaymentMethodIcon(this, paymentMethod.getId()));
            }
            getIdentificationTypesAsync();
        }catch(Exception e){
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("backButtonPressed", true);
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public void onDateSet(DialogFragment dialog, int month, int year) {
        expiryMonth = month;
        expiryYear = year;
        selectedExpiryDate = Calendar.getInstance();
        selectedExpiryDate.set(year, month - 1, Calendar.DAY_OF_WEEK);
        String monthString = month < 10 ? "0" + month : Integer.toString(month);
        String yearString = Integer.toString(year).substring(2);
        expiryDate.setText(new StringBuilder().append(monthString).append(" / ").append(yearString));
        if (CardToken.validateExpiryDate(expiryMonth, expiryYear)) {
            expiryDate.setError(null);
        } else {
            expiryDate.setError(getString(com.mercadopago.R.string.invalid_field));
        }
    }

    public void refreshLayout(View view) {
        if (exceptionOnMethod.equals("getIdentificationTypesAsync")) {
            getIdentificationTypesAsync();
        } else if (exceptionOnMethod.equals("createTokenAsync")) {
            createTokenAsync();
        }
    }

    public void popExpiryDate(View view) {
        DialogFragment newFragment = new CustomDatePickerDialog();
        Bundle args = new Bundle();
        args.putSerializable(CustomDatePickerDialog.CALENDAR, selectedExpiryDate);
        newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), null);
    }

    public void submitForm(View view) {
        LayoutUtil.hideKeyboard(activity);
        cardToken = new CardToken(getCardNumber(), getMonth(), getYear(), getSecurityCode(), getCardHolderName(),
                getIdentificationTypeId(getIdentificationType()), getIdentificationNumber());
        if (validateForm(cardToken)) {
            createTokenAsync();
        }
    }

    private boolean validateForm(CardToken cardToken) {
        boolean result = true;
        boolean focusSet = false;

        try {
            validateCardNumber(cardToken);
            cardNumber.setError(null);
        } catch (Exception ex) {
            cardNumber.setError(ex.getMessage());
            cardNumber.requestFocus();
            result = false;
            focusSet = true;
        }

        try {
            validateSecurityCode(cardToken);
            securityCode.setError(null);
        } catch (Exception ex) {
            securityCode.setError(ex.getMessage());
            if (!focusSet) {
                securityCode.requestFocus();
                focusSet = true;
            }
            result = false;
        }

        if (!cardToken.validateExpiryDate()) {
            expiryDate.setError(getString(com.mercadopago.R.string.invalid_field));
            result = false;
        } else {
            expiryDate.setError(null);
        }

        if (!cardToken.validateCardholderName()) {
            cardHolderName.setError(getString(com.mercadopago.R.string.invalid_field));
            if (!focusSet) {
                cardHolderName.requestFocus();
                focusSet = true;
            }
            result = false;
        } else {
            cardHolderName.setError(null);
        }

        if (getIdentificationType() != null) {
            if (!cardToken.validateIdentificationNumber()) {
                identificationNumber.setError(getString(com.mercadopago.R.string.invalid_field));
                if (!focusSet) {
                    identificationNumber.requestFocus();
                }
                result = false;
            } else {
                identificationNumber.setError(null);
            }
        }
        return result;
    }

    protected void validateCardNumber(CardToken cardToken) throws Exception {
        cardToken.validateCardNumber(this, paymentMethod);
    }

    protected void validateSecurityCode(CardToken cardToken) throws Exception {
        cardToken.validateSecurityCode(this, paymentMethod);
    }

    private void getIdentificationTypesAsync() {
        LayoutUtil.showProgressLayout(activity);
        mercadoPago.getIdentificationTypes(new Callback<List<IdentificationType>>() {

            public void success(List<IdentificationType> identificationTypes, Response response) {
                identificationType.setAdapter(new IdentificationTypesAdapter(activity, identificationTypes));
                setFormGoButton(identificationNumber);
                LayoutUtil.showRegularLayout(activity);
            }


            @Override
            public void success(List<IdentificationType> identificationTypes, retrofit.client.Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
                if ((error.getResponse() != null) && (error.getResponse().getStatus() == 404)) {
                    identificationLayout.setVisibility(View.GONE);
                    setFormGoButton(cardHolderName);
                    LayoutUtil.showRegularLayout(activity);
                } else {
                    exceptionOnMethod = "getIdentificationTypesAsync";
                    ApiUtil.finishWithApiException(activity, error);
                }
            }
        });
    }

    private void createTokenAsync() {
        LayoutUtil.showProgressLayout(activity);
        mercadoPago.createToken(cardToken, new Callback<Token>() {

            public void success(Token token, Response response) {
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                returnIntent.putExtra("token", token.getId());
                returnIntent.putExtra("paymentMethod", JsonUtil.getInstance().toJson(paymentMethod));
                finish();
            }


            @Override
            public void success(Token token, retrofit.client.Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
                exceptionOnMethod = "createTokenAsync";
                ApiUtil.finishWithApiException(activity, error);
            }
        });
    }

    private void setFormGoButton(final EditText editText) {
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    submitForm(v);
                }
                return false;
            }
        });
    }

    private void setIdentificationNumberKeyboardBehavior() {
        identificationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IdentificationType identificationType = getIdentificationType();
                if (identificationType != null) {
                    if (identificationType.getType().equals("number")) {
                        identificationNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                    } else {
                        identificationNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setErrorTextCleaner(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable edt) {
                if (editText.getText().length() > 0) {
                    editText.setError(null);
                }
            }
        });
    }

    private String getCardNumber() {
        return this.cardNumber.getText().toString();
    }

    private String getSecurityCode() {
        return this.securityCode.getText().toString();
    }

    private Integer getMonth() {
        return expiryMonth;
    }

    private Integer getYear() {
        return expiryYear;
    }

    private String getCardHolderName() {
        return this.cardHolderName.getText().toString();
    }

    private IdentificationType getIdentificationType() {
        return (IdentificationType) identificationType.getSelectedItem();
    }

    private String getIdentificationTypeId(IdentificationType identificationType) {
        if (identificationType != null) {
            return identificationType.getId();
        } else {
            return null;
        }
    }

    private String getIdentificationNumber() {
        if (!this.identificationNumber.getText().toString().equals("")) {
            return this.identificationNumber.getText().toString();
        } else {
            return null;
        }
    }



}
