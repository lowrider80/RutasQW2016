package pe.gob.qw.rutas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pe.gob.qw.rutas.R;
import pe.gob.qw.rutas.model.DrawerModel;

public class DrawerAdapter extends BaseAdapter {

	private List<DrawerModel> mDrawerItems;
	private LayoutInflater mInflater;

	public DrawerAdapter(Context context, List<DrawerModel> list) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDrawerItems = list;
	}

	@Override
	public int getCount() {
		return mDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mDrawerItems.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_view_item_drawer, parent, false);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.icon_social_navigation_item);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		DrawerModel item = mDrawerItems.get(position);

		holder.icon.setImageResource(item.getIconRes());
		holder.title.setText(item.getText());

		return convertView;
	}

	private static class ViewHolder {
		public ImageView icon;
		public TextView title;
	}
}
