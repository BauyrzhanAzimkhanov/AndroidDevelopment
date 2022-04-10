package com.example.lab_4_todo_app

import android.os.Parcel
import android.os.Parcelable

data class TodoTask(var id: Int, var title: String?, var description: String?, var status: Boolean, var category: Category?, var duration: String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Category::class.java.classLoader),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeByte(if (status) 1 else 0)
        parcel.writeParcelable(category, flags)
        parcel.writeString(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TodoTask> {
        override fun createFromParcel(parcel: Parcel): TodoTask {
            return TodoTask(parcel)
        }

        override fun newArray(size: Int): Array<TodoTask?> {
            return arrayOfNulls(size)
        }
    }

}
