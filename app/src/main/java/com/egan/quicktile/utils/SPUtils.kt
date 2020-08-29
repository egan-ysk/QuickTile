package com.egan.quicktile.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * @author egan.
 */
object SPUtils {

    private final val sp_name = "com.egan.sp"

    public fun getSP(context: Context, sharedPreferencesName: String): SharedPreferences {
        // PreferencesMode
        val context = context
        return context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
    }

    fun getValue(context: Context, key: String, defValue: Any): Any {
        val sp = getSP(context, sp_name)
        var value = defValue
        if (sp.contains(key)) {
            when (defValue) {
                is String -> value = sp.getString(key, defValue as String?)
                is Float -> value = sp.getFloat(key, defValue as Float)
                is Boolean -> value = sp.getBoolean(key, defValue as Boolean)
                is Long -> value = sp.getLong(key, defValue as Long)
                is Int -> value = sp.getInt(key, defValue as Int)
                else -> {
                    // do nothing...
                }
            }
        }
        return value
    }

    fun getAllValue(context: Context): ArrayList<String> {
        val allValue = ArrayList<String>()
        val sp = getSP(context, sp_name)
        sp.all.forEach { key, value -> allValue.add(key + "、" + value.toString()) }
        return allValue
    }

    fun putValue(context: Context, key: String, value: Any): Boolean {

        val edit = getSP(context, sp_name).edit()
        when (value) {
            is String -> {
                edit.putString(key, value as String?)
            }
            is Float -> {
                edit.putFloat(key, value as Float)
            }
            is Boolean -> {
                edit.putBoolean(key, value as Boolean)
            }
            is Long -> {
                edit.putLong(key, value as Long)
            }
            is Int -> {
                edit.putInt(key, value as Int)
            }
            else -> {
                // default do.
            }
        }
//        edit.apply()
        return edit.commit()
        /**
         * 这两个方法的区别在于：
         * 1. apply没有返回值而commit返回boolean表明修改是否提交成功
         * 2. apply是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘, 而commit是同步的提交到硬件磁盘，
         * 因此，在多个并发的提交commit的时候，他们会等待正在处理的commit保存到磁盘后在操作，从而降低了效率。
         * 而apply只是原子的提交到内容，后面有调用apply的函数的将会直接覆盖前面的内存数据，
         * 这样从一定程度上提高了很多效率。
         * 3. apply方法不会提示任何失败的提示。
         * 由于在一个进程中，sharedPreference是单实例，一般不会出现并发冲突，如果对提交的结果不关心的话，
         * 建议使用apply，当然需要确保提交成功且有后续操作的话，还是需要用commit的。
         */

    }

    fun deleteValue(context: Context, key: String): Boolean {
        val edit = getSP(context, sp_name).edit()
        return edit.remove(key).commit()
    }

}
