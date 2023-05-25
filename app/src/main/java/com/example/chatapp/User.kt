package com.example.chatapp

import android.provider.ContactsContract.CommonDataKinds.Email
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL
import org.jetbrains.annotations.Nullable

class User {

    var name: String?=null
    var email: String?=null
    var uid:String?=null

    constructor(){}

    constructor(name:String?,email: String?,uid:String?){

        this.name=name
        this.email=email
        this.uid=uid

    }



}