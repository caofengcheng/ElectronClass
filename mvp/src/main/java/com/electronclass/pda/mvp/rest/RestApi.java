package com.electronclass.pda.mvp.rest;


import com.electronclass.pda.mvp.entity.Attendance;
import com.electronclass.pda.mvp.entity.ClassItem;
import com.electronclass.pda.mvp.entity.ClassMessage;
import com.electronclass.pda.mvp.entity.ClassMien;
import com.electronclass.pda.mvp.entity.ClassMienPage;
import com.electronclass.pda.mvp.entity.Coures;
import com.electronclass.pda.mvp.entity.CouresNode;
import com.electronclass.pda.mvp.entity.Duty;
import com.electronclass.pda.mvp.entity.ECardDetail;
import com.electronclass.pda.mvp.entity.Inform;
import com.electronclass.pda.mvp.entity.InformPage;
import com.electronclass.pda.mvp.entity.Jurisdiction;
import com.electronclass.pda.mvp.entity.ServiceResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 描述:接口信息.
 */


public interface RestApi {

    /**
     * 获取学校班级信息
     */
    @POST("/e-card/card/get")
    Single<ServiceResponse<ClassMessage>> getClassAndSchool(@Query("eCardNo") String eCardNo);


    /**
     * 获取通知（校园/班级）
     */
    @POST("/e-card/notice/get")
    Single<ServiceResponse<List<Inform>>> getInform(@Query("eCardNo") String eCardNo, @Query("userId") String userId,
                                                    @Query("departId") String departId, @Query("type") int type,
                                                    @Query("isAvaliable") int isAvaliable);


    /**
     * 获取班级风采
     */
    @POST("/e-card/catch/get")
    Single<ServiceResponse<ClassMien>> getClassMien(@Query("eCardNo") String eCardNo, @Query("userId") String userId,
                                                    @Query("classId") String classId, @Query("pageStart") int pageStart,
                                                    @Query("pageSize") int pageSize);

    /**
     * 获取班级课表
     */
    @POST("/e-card/table/get")
    Single<ServiceResponse<List<Coures>>> getCoures(@Query("eCardNo") String eCardNo, @Query("userId") String userId,
                                                    @Query("classId") String classId, @Query("requestDate") String eventDate,
                                                    @Query("type") int type);

    /**
     * 获取班级值日表
     */
    @POST("/e-card/clean/get")
    Single<ServiceResponse<List<Duty>>> getDuty(@Query("eCardNo") String eCardNo, @Query("userId") String userId,
                                                @Query("classId") String classId, @Query("eventDate") String eventDate,
                                                @Query("type") int type);

    /**
     * 获取班级考勤
     */
    @POST("/e-card/attendance/student/get")
    Single<ServiceResponse<Attendance>> getAttendance(@Query("eCardNo") String eCardNo, @Query("userId") String userId,
                                                      @Query("classId") String classId, @Query("eventDate") String eventDate);

    /**
     * 获取班级列表
     */
    @POST("/e-card/depart/get")
    Single<ServiceResponse<List<ClassItem>>> getClassList(@Query("departId") String departId, @Query("userId") String userId);

    /**
     * 考勤打卡
     */
    @POST("/e-card/attendance/student/send")
    Single<ServiceResponse> getCardAttendance(@Query("eCardNo") String eCardNo, @Query("studentCardNo") String studentCardNo,
                                              @Query("eventTime") String eventTime, @Query("isLate") int isLate);

    /**
     * 获取验证码
     */
    @POST("/e-card/wxLogin/sms/code/get")
    Single<ServiceResponse> sendSms(@Query("phoneNum") String phoneNum);

    /**
     * 通过验证码登录
     */
    @POST("/e-card/wxLogin/sms/code/check")
    Single<ServiceResponse<List<Jurisdiction>>> login(@Query("phoneNum") String phoneNum, @Query("smsCode") String smsCode);


    /**
     * 绑定班级和班牌
     */
    @POST("/e-card/card/add/class")
    Single<ServiceResponse> bound(@Query("eCardNo") String eCardNo, @Query("departId") String departId);


    /**
     * 添加值日信息
     */
    @POST("/e-card/clean/set")
    Single<ServiceResponse> addOrUpdateDuty(@Query("id") String id, @Query("eCardNo") String eCardNo,
                                            @Query("password") String password,
                                            @Query("task") String task,
                                            @Query("name") String name,
                                            @Query("eventDate") String eventDate);

    /**
     * 删除值日
     */
    @POST("/e-card/clean/delete")
    Single<ServiceResponse> deleteDuty(@Query("id") String id, @Query("eCardNo") String eCardNo, @Query("password") String password);


    /**************************班牌新接口***************************/
    /**
     * 获取学校班级信息
     */
    @POST("ecard/app/ecard/getDetail")
    Single<ServiceResponse<ECardDetail>> getECardDetail(@Query("eCardNo") String eCardNo);

    /**
     * 获取通知（校园/班级）
     * classId *
     * string
     * 班级id
     * <p>
     * pageNo *
     * string
     * 页码
     * <p>
     * pageSize *
     * string
     * 一页数量
     * <p>
     * type *
     * string
     * 0-班级通知 1-校园通知
     */
    @POST("ecard/app/notice/page")
    Single<ServiceResponse<InformPage>> getInform(@Query("classId") String classId, @Query("pageNo") String pageNo,
                                                  @Query("pageSize") String pageSize, @Query("type") int type);


    /**
     * 获取班级风采
     */
    @POST("ecard/app/catch/classPage")
    Single<ServiceResponse<ClassMienPage>> getClassMien(@Query("departId") String departId, @Query("pageNo") int pageNo,
                                                        @Query("pageSize") int pageSize);

    /**
     * 获取班级课表
     */
    @POST("ecard/app/table/get")
    Single<ServiceResponse<List<CouresNode>>> getCoures(@Query("classId") String classId);

    /**
     * 获取班级值日表
     */
    @POST("ecard/app/clean/list")
    Single<ServiceResponse<List<Duty>>> getDuty(@Query("classId") String classId);

    /**
     * 获取班级考勤
     */
    @POST("ecard/app/attendance/info")
    Single<ServiceResponse<Attendance>> getAttendance(@Query("classId") String classId);

    /**
     * 考勤打卡
     */
    @POST("ecard/app/attendance/save")
    Single<ServiceResponse> getCardAttendance(@Query("eCardNo") String eCardNo, @Query("studentCardNo") String studentCardNo,
                                              @Query("eventTime") String eventTime, @Query("isLate") int isLate, @Query("schoolId") String schoolId);



}
