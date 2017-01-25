package com.example.juancacosta.simpletodo;

/**
 * Created by Juan C. Acosta on 1/25/2017.
 *
 */

class Todo {
    int _id;
    String _name;
    String _date;
    String _notes;
    int _priority;
    int _status;

    Todo() {
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
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    String getDate() {
        return _date;
    }

    void setDate(String _date) {
        this._date = _date;
    }

    String getNotes() {
        return _notes;
    }

    void setNotes(String _notes) {
        this._notes = _notes;
    }

    int getPriority() {
        return _priority;
    }

    void setPriority(int _priority) {
        this._priority = _priority;
    }

    int getStatus() {
        return _status;
    }

    void setStatus(int _status) {
        this._status = _status;
    }
}
