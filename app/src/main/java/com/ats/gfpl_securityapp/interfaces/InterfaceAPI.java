package com.ats.gfpl_securityapp.interfaces;

import com.ats.gfpl_securityapp.model.Employee;
import com.ats.gfpl_securityapp.model.Gate;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Purpose;
import com.ats.gfpl_securityapp.model.PurposeList;
import com.ats.gfpl_securityapp.model.Visitor;
import com.ats.gfpl_securityapp.model.VisitorList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceAPI {

    @GET("master/allEmployees")
    Call<ArrayList<Employee>> allEmployees();

    @POST("master/savePurpose")
    Call<Purpose> savePurpose(@Body Purpose purpose);

    @GET("master/getAllPurposes")
    Call<ArrayList<PurposeList>> allPurposes();

    @POST("master/deletePurpose")
    Call<Info> deletePurpose(@Query("purposeId") int purposeId);

    @POST("master/login")
    Call<Login> doLogin(@Query("dscNumber") String dscNumber);

    @POST("master/updateToken")
    Call<Info> updateUserToken(@Query("empId") int empId, @Query("token") String token);

    @GET("master/allGate")
    Call<ArrayList<Gate>> allGate();

    @POST("transaction/saveGatepassVisitor")
    Call<Visitor> saveGatepassVisitor(@Body Visitor visitor);

    @POST("transaction/getVisitorGatepassListInDate")
    Call<ArrayList<VisitorList>> getVisitorGatepassListInDate(@Query("fromDate") String fromDate, @Query("toDate") String toDate, @Query("gatepassType") ArrayList<Integer> gatepassType, @Query("empIds") String empIds, @Query("status") List<Integer> status);

    @POST("transaction/updateGatepassStatus")
    Call<Info> updateGatepassStatus(@Query("gatepassVisitorId") int gatepassVisitorId, @Query("empId") int empId,@Query("status") int status,@Query("gateId") int gateId);


}
