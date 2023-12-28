package com.example.unipaths.Models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PersonalityViewModel extends ViewModel {
    private MutableLiveData<String> trait1 = new MutableLiveData<>();
    private MutableLiveData<String> trait2 = new MutableLiveData<>();
    private MutableLiveData<String> trait3 = new MutableLiveData<>();
    private MutableLiveData<String> trait4 = new MutableLiveData<>();

    public String getTrait1() {

        if(trait1.getValue() == "E"){
            return "Extroverted";
        }
        else {
            return "Introverted";
        }
    }

    public void setTrait1(int value) {
        if(value >= 0){
            trait1.setValue("E");
        }
        else trait1.setValue("I");
    }

    public String getTrait2() {
        if(trait2.getValue() == "S"){
            return "Sensing";
        }
        else {
            return "Intuitive";
        }
    }

    public void setTrait2(int value) {
        if(value >= 0){
            trait2.setValue("S");
        }
        else trait2.setValue("N");
    }

    public String getTrait3() {
        if(trait3.getValue() == "T"){
            return "Thinking";
        }
        else {
            return "Feeling";
        }
    }

    public void setTrait3(int value) {
        if(value >= 0){
            trait3.setValue("T");
        }
        else trait3.setValue("F");
    }

    public String getTrait4() {
        if(trait4.getValue() == "J"){
            return "Judging";
        }
        else {
            return "Perceiving";
        }
    }

    public void setTrait4(int value) {
        if(value >= 0){
            trait4.setValue("J");
        }
        else trait4.setValue("P");
    }

    public String getPersonality(){
        return trait1.getValue()+trait2.getValue()+trait3.getValue()+trait4.getValue();
    }
}
