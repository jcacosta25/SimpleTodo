package com.example.juancacosta.simpletodo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by Juan C. Acosta on 1/25/2017.
 *
 */

public class Todo extends BaseObservable implements Parcelable  {

    @Bindable
    int _id;
    @Bindable
    String _name;
    @Bindable
    String _date;
    @Bindable
    String _notes;
    @Bindable
    int _priority;
    @Bindable
    int _status;

    public Todo() {
    }

    public Todo(String _name, String _date, String _notes, int _priority, int _status) {
        this._name = _name;
        this._date = _date;
        this._notes = _notes;
        this._priority = _priority;
        this._status = _status;
    }

    public Todo(int _id, String _name, String _date, String _notes, int _priority, int _status) {
        this._id = _id;
        this._name = _name;
        this._date = _date;
        this._notes = _notes;
        this._priority = _priority;
        this._status = _status;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
        notifyPropertyChanged(com.example.juancacosta.simpletodo.BR.id);
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
        notifyPropertyChanged(com.example.juancacosta.simpletodo.BR.name);
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String _date) {
        this._date = _date;
        notifyPropertyChanged(com.example.juancacosta.simpletodo.BR.date);
    }

    public String getNotes() {
        return _notes;
    }

    public void setNotes(String _notes) {
        this._notes = _notes;
        notifyPropertyChanged(com.example.juancacosta.simpletodo.BR.notes);
    }

    public int getPriority() {
        return _priority;
    }

    public void setPriority(int _priority) {
        this._priority = _priority;
        notifyPropertyChanged(com.example.juancacosta.simpletodo.BR.priority);
    }

    public int getStatus() {
        return _status;
    }

    public void setStatus(int _status) {
        this._status = _status;
        notifyPropertyChanged(com.example.juancacosta.simpletodo.BR.status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this._name);
        dest.writeString(this._date);
        dest.writeString(this._notes);
        dest.writeInt(this._priority);
        dest.writeInt(this._status);
    }

    protected Todo(Parcel in) {
        this._id = in.readInt();
        this._name = in.readString();
        this._date = in.readString();
        this._notes = in.readString();
        this._priority = in.readInt();
        this._status = in.readInt();
    }

    public static final Parcelable.Creator<Todo> CREATOR = new Parcelable.Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel source) {
            return new Todo(source);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };
}
