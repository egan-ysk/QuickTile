package com.egan.quicktile.service;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.service.quicksettings.TileService;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.egan.quicktile.R;
import com.egan.quicktile.utils.SPUtils;

import java.util.ArrayList;

/**
 * @author egan.
 */
public class CustomService extends TileService {

    private ListView recyclerView;
    private ArrayList<String> data = new ArrayList<>();

    @Override
    public void onClick() {
        super.onClick();

        data = SPUtils.INSTANCE.getAllValue(this);

        View layout = LayoutInflater.from(this).inflate(R.layout.layout_quick_dialog, ((ViewGroup) null));
        parseView(layout);
        Dialog dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(layout);
        showDialog(dialog);
    }

    private void parseView(final View content) {

        final View inputView = content.findViewById(R.id.input_layout);
        final EditText title = (EditText) content.findViewById(R.id.editTextTitle);
        final EditText scheme = (EditText) content.findViewById(R.id.editTextScheme);

        content.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputView.setVisibility(View.VISIBLE);
            }
        });

        final TextView delete = (TextView) content.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListAdapter adapter = (ListAdapter) recyclerView.getAdapter();
                boolean del = adapter.isDel();
                adapter.setDel(!del);
                delete.setText(del ? "删除" : "删除完成");
            }
        });

        recyclerView = ((ListView) content.findViewById(R.id.recycler_view));
        recyclerView.setAdapter(new ListAdapter());

        // 增加 scheme 的布局
        content.findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleString = title.getText().toString().trim();
                String schemeString = scheme.getText().toString().trim();
                if (!TextUtils.isEmpty(titleString) && !TextUtils.isEmpty(schemeString))
                    if (SPUtils.INSTANCE.putValue(content.getContext(), title.getText().toString(), scheme.getText().toString())) {
                        titleString = titleString.replaceAll("、", "");
                        schemeString = schemeString.replaceAll("、", "");
                        data.add(titleString + "、" + schemeString);
                        ((ListAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
                        inputView.setVisibility(View.GONE);
                        title.setText("");
                        scheme.setText("");
                    } else {
                        Toast.makeText(CustomService.this, "请填写完整数据！", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        content.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputView.setVisibility(View.GONE);
                title.setText("");
                scheme.setText("");
            }
        });

    }

    class ListAdapter extends BaseAdapter {

        private boolean del = false;

        public boolean isDel() {
            return del;
        }

        public void setDel(boolean del) {
            this.del = del;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data != null ? data.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
            }
            final TextView textView = (TextView) convertView.findViewById(R.id.text_view);
            ImageView delete = (ImageView) convertView.findViewById(R.id.image_view);

            delete.setVisibility(del ? View.VISIBLE : View.GONE);

            final String[] str = data.get(position).split("、");
            textView.setText(str[0]);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SPUtils.INSTANCE.deleteValue(parent.getContext(), textView.getText().toString())) {
                        data.remove(position);
                        notifyDataSetChanged();
                    }
                }
            });
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW).setData(Uri.parse(str[1]));
                    startActivityAndCollapse(intent);
                }
            });
            return convertView;
        }
    }
}
