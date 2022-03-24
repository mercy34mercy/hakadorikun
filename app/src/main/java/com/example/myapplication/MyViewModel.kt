package jp.masashi.hakadori

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel(
        var uid:MutableLiveData<String> = MutableLiveData<String>(""),
        var date:MutableLiveData<String> = MutableLiveData<String>(""),
        var day:MutableLiveData<String> = MutableLiveData<String>(""),
        var start_time:MutableLiveData<String> = MutableLiveData<String>(""),
        var end_time:MutableLiveData<String> = MutableLiveData<String>(""),
        var memo:MutableLiveData<String> = MutableLiveData<String>(""),
        var place:MutableLiveData<String> = MutableLiveData<String>(""),
        var title:MutableLiveData<String> = MutableLiveData<String>(""),
        var url:MutableLiveData<String> = MutableLiveData<String>(""),
        var result:MutableLiveData<String> = MutableLiveData<String>(""),
        var id:MutableLiveData<String> = MutableLiveData<String>(""),

        var get_uid:MutableLiveData<String> = MutableLiveData<String>(""),
        var get_date:MutableLiveData<String> = MutableLiveData<String>(""),
        var get_day:MutableLiveData<String> = MutableLiveData<String>(""),
        var get_start_time:MutableLiveData<String> = MutableLiveData<String>(""),
        var get_end_time:MutableLiveData<String> = MutableLiveData<String>(""),
        var get_memo:MutableLiveData<String> = MutableLiveData<String>(""),
        var get_place:MutableLiveData<String> = MutableLiveData<String>(""),
        var get_title:MutableLiveData<String> = MutableLiveData<String>(""),
        var get_url:MutableLiveData<String> = MutableLiveData<String>("")


) : ViewModel()