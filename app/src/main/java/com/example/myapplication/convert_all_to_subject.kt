package jp.masashi.hakadori


import io.realm.Realm
import io.realm.RealmResults
import java.text.FieldPosition

//Realm1...全体のデータ
//Realm2...選択した教科だけのデータ
class convert_all_to_subject(val Realm1:RealmResults<TaskDB>, val Realm2:RealmResults<TaskDB>, val positon:Int) {
    //TODO 個別の教科ごとの課題のpositionを全体のポジションにへんこうする
    val select_resutl = Realm2[positon]
    val Realm1_size = Realm1.size
    var  return_data:Int = 0
    private fun main()
    {
        for (i in 0 ..Realm1_size){
            if(select_resutl!!.task_uid == Realm1[i]!!.task_uid){
                if(select_resutl!!.task_title == Realm1[i]!!.task_title){
                    return_data = i
                }
            }
        }
    }
}