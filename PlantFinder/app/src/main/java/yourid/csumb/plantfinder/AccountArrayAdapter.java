package yourid.csumb.plantfinder;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import yourid.csumb.plantfinder.model.Account;

public class AccountArrayAdapter extends ArrayAdapter<Account> {
    private Context mContext;
    int mResource;

    public AccountArrayAdapter(Context context, int resource, List<Account> account) {
        super(context, resource, account);
        mResource = resource;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.account_item, parent, false);
        }

        Account account = getItem(position);

        TextView tv = (TextView) convertView.findViewById(R.id.list_user_string);
        tv.setText(account.toString());

        ImageButton db = convertView.findViewById(R.id.delete_btn);
        ImageButton eb = convertView.findViewById(R.id.edit_btn);

        db.setTag(account);
        eb.setTag(account);

        return convertView;
    }
}
