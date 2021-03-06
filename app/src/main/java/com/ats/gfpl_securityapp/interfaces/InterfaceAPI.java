package com.ats.gfpl_securityapp.interfaces;

import com.ats.gfpl_securityapp.model.Company;
import com.ats.gfpl_securityapp.model.Dashboard;
import com.ats.gfpl_securityapp.model.Department;
import com.ats.gfpl_securityapp.model.EmpGatePass;
import com.ats.gfpl_securityapp.model.Employee;
import com.ats.gfpl_securityapp.model.Gate;
import com.ats.gfpl_securityapp.model.Info;
import com.ats.gfpl_securityapp.model.Item;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Material;
import com.ats.gfpl_securityapp.model.MaterialDetail;
import com.ats.gfpl_securityapp.model.Notification;
import com.ats.gfpl_securityapp.model.Outward;
import com.ats.gfpl_securityapp.model.Party;
import com.ats.gfpl_securityapp.model.Purpose;
import com.ats.gfpl_securityapp.model.PurposeList;
import com.ats.gfpl_securityapp.model.Sync;
import com.ats.gfpl_securityapp.model.VisitCard;
import com.ats.gfpl_securityapp.model.Visitor;
import com.ats.gfpl_securityapp.model.VisitorList;
import com.ats.gfpl_securityapp.model.VisitorMaster;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface InterfaceAPI {

    @GET("master/allEmployeesByDesg")
    Call<ArrayList<Employee>> allEmployees();

    @GET("master/allEmployees")
    Call<ArrayList<Employee>> allEmployee();

    @GET("master/allCompany")
    Call<ArrayList<Company>> allCompany();

//    master/allEmployees

    @POST("master/getAllPurposesByType")
    Call<ArrayList<PurposeList>> getAllPurposesByType( @Query("typeList") ArrayList<Integer> typeList);

    @POST("master/savePurpose")
    Call<Purpose> savePurpose(@Body Purpose purpose);

    @GET("master/getAllPurposes")
    Call<ArrayList<PurposeList>> allPurposes();

    @POST("master/deletePurpose")
    Call<Info> deletePurpose(@Query("purposeId") int purposeId);

    @POST("master/login")
    Call<Login> doLogin(@Query("dscNumber") String dscNumber);

    @GET("master/allSettings")
    Call<ArrayList<Sync>> allSettings();

    @POST("master/updateToken")
    Call<Info> updateUserToken(@Query("empId") int empId, @Query("token") String token);

    @GET("master/allGate")
    Call<ArrayList<Gate>> allGate();

    @POST("transaction/saveGatepassVisitor")
    Call<Visitor> saveGatepassVisitor(@Body Visitor visitor);

    @POST("saveVisitor")
    Call<VisitorMaster> saveVisitor(@Body VisitorMaster visitorMaster);

    @POST("transaction/saveGatepassVisitor")
    Call<VisitorList> saveGatepassVisitor(@Body VisitorList visitorList);

    @POST("transaction/getVisitorGatepassListInDate")
    Call<ArrayList<VisitorList>> getVisitorGatepassListInDate(@Query("fromDate") String fromDate, @Query("toDate") String toDate, @Query("gatepassType") ArrayList<Integer> gatepassType, @Query("empIds") String empIds, @Query("status") List<Integer> status);

    @GET("getVisitorList")
    Call<ArrayList<VisitorMaster>> getVisitorList();


    @POST("transaction/updateGatepassStatus")
    Call<Info> updateGatepassStatus(@Query("gatepassVisitorId") int gatepassVisitorId, @Query("empId") int empId,@Query("status") int status,@Query("gateId") int gateId);

    @GET("master/allAvailableVisitCard")
    Call<ArrayList<VisitCard>> allVisitCard();
//    master/allVisitCard
    @Multipart
    @POST("photoUpload")
    Call<JSONObject> imageUpload(@Part MultipartBody.Part[] filePath, @Part("imageName") ArrayList<String> name, @Part("type") RequestBody type);

    @POST("master/deleteVisitor")
    Call<Info> deleteVisitor(@Query("gatepassVisitorId") int gatepassVisitorId);

    @POST("deleteVistor ")
    Call<Info> deleteVistor(@Query("visitorId") int visitorId);

    @POST("transaction/updateVisitorStatus")
    Call<Info> updateVisitorStatus(@Query("gatepassVisitorId") int gatepassVisitorId,@Query("status") int status);

    @POST("master/saveEmployeeGatepass")
    Call<EmpGatePass> saveEmployeeGatepass(@Body EmpGatePass empGatePass);

    @POST("transaction/getEmpGatepassListWithDateFilter")
    Call<ArrayList<EmpGatePass>> getEmpGatepassListWithDateFilter(@Query("fromDate") String fromDate, @Query("toDate") String toDate, @Query("deptIds") ArrayList<Integer> deptIds, @Query("empIds") String empIds, @Query("status") List<Integer> status);

    @POST("master/deleteEmployeeGatepass")
    Call<Info> deleteEmployeeGatepass(@Query("gatepassEmpId") int gatepassEmpId);

    @POST("transaction/updateEmpGatepass")
    Call<Info> updateEmpGatepass(@Query("gatepassEmpId") int gatepassEmpId,@Query("securityId") int securityId,@Query("status") int status,@Query("type") int type);

    @POST("transaction/saveMaterialGatepass")
    Call<Material> saveMaterialGatepass(@Body Material material);

    @GET("master/allEmployeeDepartment")
    Call<ArrayList<Department>> allEmployeeDepartment();

    @POST("transaction/getMaterialTrackGatepassListWithFilter")
    Call<ArrayList<MaterialDetail>> getMaterialTrackGatepassListWithFilter(@Query("deptIds") ArrayList<Integer> deptIds, @Query("empIds") ArrayList<Integer> empIds, @Query("status") List<Integer> status);

    @POST("transaction/getMaterialTrackGatepassListWithDateFilter")
    Call<ArrayList<MaterialDetail>> getMaterialTrackGatepassListWithDateFilter(@Query("fromDate") String fromDate, @Query("toDate") String toDate, @Query("deptIds") ArrayList<Integer> deptIds, @Query("empIds") ArrayList<Integer> empIds, @Query("status") List<Integer> status);

    @POST("master/deleteMaterialGatepass")
    Call<Info> deleteMaterialGatepass(@Query("gatepassInwardId") int gatepassInwardId);

    @GET("getAllItems")
    Call<ArrayList<Item>> getAllItems();

    @GET("getAllVendorByIsUsed ")
    Call<ArrayList<Party>> getAllVendorByIsUsed ();

    @POST("transaction/updateMaterialGatepass")
    Call<Info> updateMaterialGatepass(@Query("headerList") ArrayList<Integer> headerList,@Query("status") int status);

    @POST("transaction/materialGatepassHandover")
    Call<Info> materialGatepassHandover(@Query("headerIdList") ArrayList<Integer> headerIdList,@Query("fromEmpId") int fromEmpId,@Query("fromDeptId") int fromDeptId,@Query("toEmpId") int toEmpId,@Query("toDeptId") int toDeptId);

    @POST("master/getNotificationByGatepassId")
    Call<ArrayList<Notification>> getNotificationByGatepassId(@Query("gatepassVisitorId") int gatepassVisitorId);

    @POST("transaction/sendNotification")
    Call<Info> sendNotification(@Query("gatepassVisitorId") int gatepassVisitorId,@Query("empId") int empId);

    @POST("transaction/dashboardCount")
    Call<Dashboard> dashboardCount(@Query("fromDate") String fromDate, @Query("toDate") String todate, @Query("empId") int empId);

    @POST("master/saveVisitCard")
    Call<VisitCard> saveVisitCard(@Body VisitCard visitCard);

    @POST("master/deleteVisitCard")
    Call<Info> deleteVisitCard(@Query("cardId") int cardId);

    @POST("master/saveOutwardGatepass")
    Call<Outward> saveOutwardGatepass(@Body Outward outward);

    @POST("transaction/getOutwardGatepassList")
    Call<ArrayList<Outward>> getOutwardGatepassList(@Query("empId") ArrayList<Integer> empId, @Query("status") List<Integer> status);

    @POST("transaction/updateOutwardGatepassStatus")
    Call<Info> updateOutwardGatepassStatus(@Query("gpOutwardId") int gpOutwardId, @Query("secId") int secId, @Query("status") int status,@Query("photo") String photo);

    @POST("master/deleteOutwardGatepass")
    Call<Info> deleteOutwardGatepass(@Query("gpOutwardId") int gpOutwardId);

    @POST("master/saveCompany")
    Call<Company> saveCompany(@Body Company company);

    @POST("master/deleteCompany")
    Call<Info> deleteCompany(@Query("companyId") int companyId);

    @POST("getVisitorById")
    Call<VisitorMaster> getVisitorById(@Query("visitorId") int visitorId);
}
