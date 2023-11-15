package com.apunao.elmirador;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorMenu extends  RecyclerView.Adapter<AdaptadorMenu.MyViewHolder> {

    Context mContext;
    List<ItemMesa> mMesa,mReserva;
    String mFecha, mHora, mCantidad, mNombre, mCorreo, mTelefono, mId;
    //private ArrayList<ItemReserva> itemReserva = new ArrayList<ItemReserva>();,hora, cantidad, nombre, id, correo,telefono
    public AdaptadorMenu(Context mContext, List<ItemMesa> mMesa, List<ItemMesa> mReserva,String mFecha,
                         String mHora,String mCantidad,String mNombre,String mId,String mCorreo,
                         String mTelefono) {
        this.mContext = mContext;
        this.mMesa = mMesa;
        this.mReserva = mReserva;
        this.mFecha = mFecha;
        this.mCantidad = mCantidad;
        this.mHora = mHora;
        this.mNombre = mNombre;
        this.mCorreo = mCorreo;
        this.mTelefono = mTelefono;
        this.mId = mId;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mesa;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mesa = (TextView) itemView.findViewById(R.id.mesa);
            cardView  = (CardView) itemView.findViewById(R.id.cardMesa);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_mesa, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(view);
        vHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int posicion = vHolder.getAdapterPosition();
                final ItemMesa objeto = mMesa.get(posicion);

                    v.setBackgroundColor(mContext.getResources().getColor(R.color.colorNoDisponible));
                    //v.setSelected(false);
                    Toast.makeText(mContext, "Mesa seleccionada: "+objeto.getId(), Toast.LENGTH_LONG).show();

                    AlertDialog.Builder alertbox = new AlertDialog.Builder(mContext);
                    alertbox.setMessage(Html.fromHtml("Mesa seleccionada: "+ objeto.getId()  + "<br> <br>Desea continuar con la RESERVA ?"));
                    alertbox.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        //Funcion llamada cuando se pulsa el boton Si
                        public void onClick(DialogInterface arg0, int arg1) {

                            Intent intent = new Intent(mContext, ReservaOk.class);
                            Bundle bundleQR = new Bundle();
                            bundleQR.putString("fecha", mFecha);
                            bundleQR.putString("hora", mHora);
                            bundleQR.putString("cantidad", mCantidad);
                            bundleQR.putString("mesa", objeto.getId());
                            bundleQR.putString("id", mId);
                            bundleQR.putString("nombre", mNombre);
                            bundleQR.putString("telefono", mTelefono);
                            bundleQR.putString("correo", mCorreo);

                            intent.putExtras(bundleQR);
                            mContext.startActivity(intent);


                            //Toast.makeText(mContext, "RESERVA OK", Toast.LENGTH_LONG).show();
                                //

                        }
                    });
                    alertbox.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        //Funcion llamada cuando se pulsa el boton No
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(mContext, "Seleccione una MESA", Toast.LENGTH_LONG).show();
                            v.setBackgroundColor(mContext.getResources().getColor(R.color.colorDisponible));
                        }
                    });

                    alertbox.show();


                /*else {
                    v.setBackgroundColor(mContext.getResources().getColor(R.color.colorDisponible));
                    v.setSelected(true);
                }*/

            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.mesa.setText(mMesa.get(position).id);
        //holder.cardView.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlanco));
        holder.cardView.setBackgroundColor(mContext.getResources().getColor(R.color.colorDisponible));
        if(mMesa.get(position).getCapacidad().equals("ocupada"))
        {
            holder.cardView.setEnabled(false);
            holder.cardView.setBackgroundColor(mContext.getResources().getColor(R.color.colorOut));
        }



    }

    @Override
    public int getItemCount() {
        return mMesa.size();
    }
}
