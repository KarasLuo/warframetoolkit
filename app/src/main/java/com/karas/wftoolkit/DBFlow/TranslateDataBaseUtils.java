package com.karas.wftoolkit.DBFlow;

import android.support.annotation.NonNull;
import android.util.Log;

import com.karas.wftoolkit.CommonUtils.ToastUtils;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Hongliang Luo on 2019/6/12.
 **/
public  class TranslateDataBaseUtils {
    final static private String TAG="TranslateDataBaseUtils";

    private final static String REG_CN="(.*[\u4e00-\u9fa5]+.*)";//包含至少一个汉字
    final static public String REG_RES_WORDS="(.*[0-9][X]?[ ])";

    public static void clearTable(){
        Delete.table(TranslateTable.class);
    }

    public static boolean add(TranslateTable data){
        return data.save();
    }

    //初始化时批量保存
    public static void saveAll(final ArrayList<TranslateTable> datas){
        FlowManager.getDatabase(JKDataBase.class)
                .beginTransactionAsync(new ProcessModelTransaction
                        .Builder<>(new ProcessModelTransaction
                        .ProcessModel<TranslateTable>() {
                    @Override
                    public void processModel(TranslateTable translateTable,
                                             DatabaseWrapper wrapper) {
                        translateTable.save();
                    }
                }).addAll(datas).build())
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(@NonNull Transaction transaction) {
                        Log.e(TAG,"数据库初始化成功");
                    }
                })
                .error(new Transaction.Error() {
                    @Override
                    public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                        ToastUtils.showShortToast("数据库创建失败，文本翻译将会异常！");
                        Log.e(TAG,"数据库初始化失败：");
                        error.printStackTrace();
                    }
                }).build().execute();
    }

    /**
     * 异步回调方式查询
     * @param enString
     * @param callback
     */
    public static void searchCnByEn(@NonNull String enString,
                                    @NonNull QueryTransaction
                                            .QueryResultSingleCallback<TranslateTable> callback){
        SQLite.select().from(TranslateTable.class)
                .where(TranslateTable_Table.enString.is(enString))
                .async()
                .querySingleResultCallback(callback)
                .execute();
    }

    /**
     * 响应式查询
     * @param enString
     * @return
     */
    public static Observable<List<TranslateTable>> searchCnByEn(@NonNull String enString){
        return RXSQLite.rx(SQLite.select().from(TranslateTable.class).
                where(TranslateTable_Table.enString.eq(enString)))
                .queryList()
                .toObservable();
    }

    /**
     * 单条字符串整体查询一次
     * @param enString
     * @return
     */
    public static Observable<String> translate(final String enString){
        return searchCnByEn(enString)
                .map(new Function<List<TranslateTable>, String>() {
                    @Override
                    public String apply(List<TranslateTable> tts) throws Exception {
                        if(tts.size()==0){
                            Log.i(TAG,"searched:null");//未查询到
                            return enString;
                        }else {//已查询到,取第一个[一般只有一个]
                            Log.i(TAG,"searched size="+tts.size());
                            Log.i(TAG,"searched: id="+tts.get(0).id+",en="+tts.get(0).enString+",cn="+tts.get(0).cnString);
                            return tts.get(0).cnString;
                        }
                    }
                });
    }

    /**
     * 单条字符串查询，先整体查询，再分割后单独查询
     * @param enString
     * @return
     */
    public static Observable<String> translateSingle(String enString){
        Log.i(TAG,"start first search:"+enString);
        String s=enString.toLowerCase()
                .replace("("," ( ")
                .replace(")"," ) ")
                .replace(":"," : ");
        Log.i(TAG,"searchCnByEn input string:"+s);
        return translate(s)
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        String[] subStrings=s.split(" ");
                        Log.i(TAG,"string 分发:"+Arrays.toString(subStrings));
                        return Observable.fromArray(subStrings);
                    }
                }).concatMap(new Function<String, Observable<String>>() {
                    @Override
                    public Observable<String> apply(String s) throws Exception {
                        if (s.matches(REG_CN)){//包含汉字
                            Log.i(TAG,"substring contain 汉字："+s);
                            return Observable.just(s);
                        }else {
                            Log.i(TAG,"start second search:"+s);
                            return translate(s);
                        }
                    }
                }).toList().toObservable()
                .map(new Function<List<String>, String>() {
                    @Override
                    public String apply(List<String> strings) throws Exception {
                        StringBuilder sb=new StringBuilder();
                        String temp="$%&*";
                        for (String str:strings){
                            if(!(temp.matches(REG_CN)&&
                                    str.matches(REG_CN))&&
                                    !temp.equals("$%&*")&&
                                    !temp.equals("(")&&
                                    !temp.equals(")")&&
                                    !str.equals(")")&&
                                    !str.equals("(")&&
                                    !temp.equals(":")&&
                                    !str.equals(":")){
                                sb.append(" ");
                            }
                            if(str.length()>0){
                                String firstChar=String.valueOf(str.charAt(0));
                                if(firstChar.matches("([a-z])")){
                                    str=str.replaceFirst(firstChar,firstChar.toUpperCase());
                                }
                            }
                            sb.append(str);
                            temp=str;
                        }
                        Log.i(TAG,"result="+sb.toString()+"<<<"+Arrays.toString(strings.toArray()));
                        return sb.toString();
                    }
                });

    }

    /**
     * 多条字符串查询，先整体查询，再分割后单独查询
     * @param enStrings
     * @return
     */
    public static Observable<List<String>> translateList(List<String> enStrings){
        return Observable.fromIterable(enStrings)
                .concatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return translateSingle(s);
                    }
                }).toList().toObservable();
    }
}
