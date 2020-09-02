package com.eis.dailycallregister.Api;


import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Pojo.AreaJntWrkRes;
import com.eis.dailycallregister.Pojo.ChemistDetailRes;
import com.eis.dailycallregister.Pojo.ChemistDoctorNameRes;
import com.eis.dailycallregister.Pojo.ChemistListAWRes;
import com.eis.dailycallregister.Pojo.ChemistProRes;
import com.eis.dailycallregister.Pojo.ConfirmDCRRes;
import com.eis.dailycallregister.Pojo.CustCatTypPatchListRes;
import com.eis.dailycallregister.Pojo.CustDetailsRes;
import com.eis.dailycallregister.Pojo.CustListResponse;
import com.eis.dailycallregister.Pojo.DBList;
import com.eis.dailycallregister.Pojo.DCRDChemListRes;
import com.eis.dailycallregister.Pojo.DCRDDocListRes;
import com.eis.dailycallregister.Pojo.DCRExpenseListRes;
import com.eis.dailycallregister.Pojo.DCRGiftListRes;
import com.eis.dailycallregister.Pojo.DCRProdListRes;
import com.eis.dailycallregister.Pojo.DefaultResponse;
import com.eis.dailycallregister.Pojo.DoctorListAWRes;
import com.eis.dailycallregister.Pojo.EditMtpFormResponse;
import com.eis.dailycallregister.Pojo.EleaningMainRes;
import com.eis.dailycallregister.Pojo.EpidermPopUpRes;
import com.eis.dailycallregister.Pojo.FetchExpdtRes;
import com.eis.dailycallregister.Pojo.GetDCRSummaryMainRes;
import com.eis.dailycallregister.Pojo.GetDcrDateRes;
import com.eis.dailycallregister.Pojo.GetPopupQuesRes;
import com.eis.dailycallregister.Pojo.GetRCPABrandListRes;
import com.eis.dailycallregister.Pojo.GetRCPACompProdLstRes;
import com.eis.dailycallregister.Pojo.GetRCPAPulseChemist;
import com.eis.dailycallregister.Pojo.GetRetailerAlertCnt;
import com.eis.dailycallregister.Pojo.HOChemistListResponse;
import com.eis.dailycallregister.Pojo.HOConfirmDCRRes;
import com.eis.dailycallregister.Pojo.HODCRProductResponse;
import com.eis.dailycallregister.Pojo.HODCRSummaryMainRes;
import com.eis.dailycallregister.Pojo.HODcrDateResponse;
import com.eis.dailycallregister.Pojo.HODcrdChemListResponse;
import com.eis.dailycallregister.Pojo.HODcrdDocDetResponse;
import com.eis.dailycallregister.Pojo.HODcrdHubListResponse;
import com.eis.dailycallregister.Pojo.HODcrdWDListResponse;
import com.eis.dailycallregister.Pojo.HODoctorListResponse;
import com.eis.dailycallregister.Pojo.HOExpenseDefaultDataRes;
import com.eis.dailycallregister.Pojo.HOFrmToTownsDetRes;
import com.eis.dailycallregister.Pojo.HOHubListResponse;
import com.eis.dailycallregister.Pojo.HOLevelListResponse;
import com.eis.dailycallregister.Pojo.HONonFieldWorkListRes;
import com.eis.dailycallregister.Pojo.HOOJTFlagNotifyRes;
import com.eis.dailycallregister.Pojo.HOWDListResponse;
import com.eis.dailycallregister.Pojo.HoDcrdDrListResponse;
import com.eis.dailycallregister.Pojo.HoMtpMgrListResponse;
import com.eis.dailycallregister.Pojo.HoMtpPatchListResponse;
import com.eis.dailycallregister.Pojo.HoMtpPsrListResponse;
import com.eis.dailycallregister.Pojo.HoMtpResponse;
import com.eis.dailycallregister.Pojo.IsDCRCorrectRes;
import com.eis.dailycallregister.Pojo.LoginResponse;
import com.eis.dailycallregister.Pojo.MgrRCPARes;
import com.eis.dailycallregister.Pojo.MgrRcpaDrRes;
import com.eis.dailycallregister.Pojo.MissCallDocsRes;
import com.eis.dailycallregister.Pojo.NewMTPListOfMTHRes;
import com.eis.dailycallregister.Pojo.NewNonFliedWrkRes;
import com.eis.dailycallregister.Pojo.NextMTPListRes;
import com.eis.dailycallregister.Pojo.NonFieldWrkRes;
import com.eis.dailycallregister.Pojo.P1P2P3DrProdRes;
import com.eis.dailycallregister.Pojo.PatchListResponse;
import com.eis.dailycallregister.Pojo.PatientListRes;
import com.eis.dailycallregister.Pojo.ProductListRes;
import com.eis.dailycallregister.Pojo.QseraPopUpRes;
import com.eis.dailycallregister.Pojo.QuizMainRes;
import com.eis.dailycallregister.Pojo.RedicnePopUpRes;
import com.eis.dailycallregister.Pojo.RetailerAndOptions;
import com.eis.dailycallregister.Pojo.SampleAndGiftReceiptRes;
import com.eis.dailycallregister.Pojo.SpclDcrDcrdChLstRes;
import com.eis.dailycallregister.Pojo.SpclDcrdDrListRes;
import com.eis.dailycallregister.Pojo.StateListResponse;
import com.eis.dailycallregister.Pojo.VstCardDrLstRes;
import com.eis.dailycallregister.Pojo.VstPlnDocLstRes;
import com.eis.dailycallregister.Pojo.VstPlnSumRes;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    //to get division names
    @GET("getdbnames.php")
    Call<DBList> getdblist();

    //to login
    @FormUrlEncoded
    @POST("userloginupdated.php")
    Call<LoginResponse> login(
            @Field("ecode") String ecode,
            @Field("password") String password,
            @Field("date") String date,
            @Field("DBPrefix") String DBPrefix
    );

    //to check weather user is resigned or not
    @FormUrlEncoded
    @POST("changeDCRDate.php")
    Call<DefaultResponse> changeDCRDate(
            @Field("empcode") String empcode,
            @Field("netid") String netid,
            @Field("newdcrdate") String newdcrdate,
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String DBPrefix
    );

    //to get dcrdate and also checks weather it is greater than current date or not
    @FormUrlEncoded
    @POST("checkdcrdate.php")
    Call<GetDcrDateRes> getDcrdate(
            @Field("empcode") String ecode,
            @Field("netid") String netid,
            @Field("DBPrefix") String DBPrefix
    );

    //to check MTP. It checks weather the MTP is filled or not of current date and also check MTP of next month on 24 of each month
    @FormUrlEncoded
    @POST("checkcurmthmtp.php")
    Call<DefaultResponse> checkMTP(
            @Field("empcode") String ecode,
            @Field("netid") String netid,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("DBPrefix") String DBPrefix
    );

    //to check sample & gift entries
    @FormUrlEncoded
    @POST("checksamplegift.php")
    Call<DefaultResponse> checkSampleGift(
            @Field("empcode") String ecode,
            @Field("DBPrefix") String DBPrefix
    );

    //to check dcr is blocked or not
    @FormUrlEncoded
    @POST("dcrblockcheck.php")
    Call<DefaultResponse> DCRBlockCheck(
            @Field("empcode") String ecode,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("DBPrefix") String DBPrefix
    );

    //to get holiday in between last confirm dcr and current dcrdate
    @FormUrlEncoded
    @POST("getholdcrdates.php")
    Call<DefaultResponse> getHolidayDcrdates(
            @Field("empcode") String ecode,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("jnwrkandarea.php")
    Call<AreaJntWrkRes> getAreaJntWrk(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("hname") String hname,
            @Field("dcrdate") String dcrdate,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("getdoctordatalist.php")
    Call<DoctorListAWRes> getDoctorDataList(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("tcpid") String tcpid,
            @Field("dcrdate") String dcrdate,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("getchemistdatalist.php")
    Call<ChemistListAWRes> getChemistDataList(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("tcpid") String tcpid,
            @Field("dcrdate") String dcrdate,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("stype") String stype,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("getdcrddoc.php")
    Call<DCRDDocListRes> getDCRDDrs(
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String DBPrefix,
            @Field("netid") String netid
    );

    @FormUrlEncoded
    @POST("SampleAndGiftReceipt.php")
    Call<SampleAndGiftReceiptRes> SampleAndGiftReceipt(
            @Field("empcode") String empcode,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("getdcrdchem.php")
    Call<DCRDChemListRes> getDCRDChem(
            @Field("dcrno") String dcrno,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("div") String div,
            @Field("dcrChQPopup") String dcrChQPopup,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("getdcrdothcust.php")
    Call<DCRDChemListRes> getDCRDOthCust(
            @Field("dcrno") String dcrno,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("div") String div,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("dcrValidate.php")
    Call<DefaultResponse> isDCRCorrectlyFilled(
            @Field("dcrno") String dcrno,
            @Field("netid") String netid,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("DCRExpense.php")
    Call<DCRExpenseListRes> DCRExpenseReq(
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("fetchexpensedata.php")
    Call<FetchExpdtRes> fetchExpData(
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String DBPrefix
    );


    @FormUrlEncoded
    @POST("deleteExpenseEntry.php")
    Call<DefaultResponse> deleteExpenseEntry(
            @Field("dcrno") String dcrno,
            @Field("serial") String serial,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("deleteNonFieldWrk.php")
    Call<DefaultResponse> deleteNonFieldWrkEntry(
            @Field("dcrno") String dcrno,
            @Field("serial") String serial,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("updateremark.php")
    Call<DefaultResponse> saveRemark(
            @Field("dcrno") String dcrno,
            @Field("serial") String serial,
            @Field("remark") String remark,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("getremark.php")
    Call<DefaultResponse> getSubRemark(
            @Field("dcrno") String dcrno,
            @Field("serial") String serial,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("getNonFieldWorkList2.php")
    Call<NewNonFliedWrkRes> getNonFieldWorkList2(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrno") String dcrno,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("visitingcardupload.php")
    Call<VstCardDrLstRes> getVstDrLstFormDB(
            @Field("netid") String netid,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("getAlreadtExistImg.php")
    Call<DefaultResponse> getAlreadtExistImg(
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("deleteDRfromDCR.php")
    Call<DefaultResponse> deleteDRfromDCR(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrhtcpid") String tcpid,
            @Field("dcrno") String dcrno,
            @Field("serial") String serial,
            @Field("finyear") String finyear,
            @Field("dcrdate") String dcrdate,
            @Field("emplvl") String emplvl,
            @Field("field") String field,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("getDeleteExistingImg.php")
    Call<DefaultResponse> getDeleteExistingImg(
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("checkSalesEntryNotFilled.php")
    Call<DefaultResponse> checkSalesEntryNotFilled(
            @Field("netid") String netid,
            @Field("d1d2") String d1d2,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("DCRGift.php")
    Call<DCRGiftListRes> DCRGiftApi(
            @Field("serial") String serial,
            @Field("netid") String netid,
            @Field("dcrno") String dcrno,
            @Field("d1d2") String d1d2,
            @Field("ecode") String ecode,
            @Field("financialyear") String financialyear,
            @Field("logmth") String logmth,
            @Field("logyr") String logyr,
            @Field("DBPrefix") String DBPrefix
    );

    /*@FormUrlEncoded
    @POST("DCRProduct.php")
    Call<DCRProdListRes> DCRProdApi(
            @Field("serial") String serial,
            @Field("netid") String netid,
            @Field("dcrno") String dcrno,
            @Field("d1d2") String d1d2,
            @Field("ecode") String ecode,
            @Field("financialyear") String financialyear,
            @Field("DBPrefix") String DBPrefix
    );*/

    @FormUrlEncoded
    @POST("demo.php")
    Call<DCRProdListRes> DCRProdApi(
            @Field("serial") String serial,
            @Field("netid") String netid,
            @Field("dcrno") String dcrno,
            @Field("d1d2") String d1d2,
            @Field("ecode") String ecode,
            @Field("financialyear") String financialyear,
            @Field("dcrdate") String dcrdate,
            @Field("mth") String mth,
            @Field("yr") String yr,
            @Field("cntcd") String cntcd,
            @Field("logmth") String logmth,
            @Field("logyr") String logyr,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("NonFieldWorkApi.php")
    Call<NonFieldWrkRes> getNonFieldWrk(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrno") String dcrno,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("yesnoquestionpopup.php")
    Call<GetPopupQuesRes> yesNoQuestionPopup(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("drclass") String drclass,
            @Field("d1d2") String d1d2,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("cntcd") String cntcd,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("submitPopupQuesAns.php")
    Call<DefaultResponse> submitPopupQuesAns(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("jsonstr") String jsonstr,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("cntcd") String cntcd,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("UpdateSampleGiftAcceptance.php")
    Call<DefaultResponse> UpdateSampleGiftAcceptance(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("financialyear") String financialyear,
            @Field("jsonarray") String jsonstr,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("get117611771187.php")
    Call<EpidermPopUpRes> get117611771187(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("cntcd") String cntcd,
            @Field("prodid") String prodid,
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("get1098.php")
    Call<QseraPopUpRes> get1098(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("cntcd") String cntcd,
            @Field("prodid") String prodid,
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("get3009.php")
    Call<RedicnePopUpRes> get3009(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("cntcd") String cntcd,
            @Field("prodid") String prodid,
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("submit11761177.php")
    Call<DefaultResponse> submit11761177(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("cntcd") String cntcd,
            @Field("prodid") String prodid,
            @Field("dcrno") String dcrno,
            @Field("startpresc") String startpresc,
            @Field("madeavail") String madeavail,
            @Field("ddorderqty") String ddorderqty,
            @Field("triopackgiven") String triopackgiven,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("submit1098.php")
    Call<DefaultResponse> submit1098(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("cntcd") String cntcd,
            @Field("prodid") String prodid,
            @Field("dcrno") String dcrno,
            @Field("madeavail") String madeavail,
            @Field("NoQSeraHairSerumRx") String NoQSeraHairSerumRx,
            @Field("Noofunitsold") String Noofunitsold,
            @Field("Doctorsfeedback") String Doctorsfeedback,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("submit1187.php")
    Call<DefaultResponse> submit1187(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("cntcd") String cntcd,
            @Field("prodid") String prodid,
            @Field("dcrno") String dcrno,
            @Field("epidermlaunched") String epidermlaunched,
            @Field("epidermsamplegiven") String epidermsamplegiven,
            @Field("epidermprscReceived") String epidermprscReceived,
            @Field("epidermpresno") String epidermpresno,
            @Field("madeavail") String madeavail,
            @Field("epidermnoofunitsavail") String epidermnoofunitsavail,
            @Field("epidermnoofunitsoldafterlaunch") String epidermnoofunitsoldafterlaunch,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("submit3009.php")
    Call<DefaultResponse> submit3009(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("cntcd") String cntcd,
            @Field("prodid") String prodid,
            @Field("dcrno") String dcrno,
            @Field("KMACBriefednConsentRcvd") String KMACBriefednConsentRcvd,
            @Field("NoofRidacneRxWeek1520jul") String NoofRidacneRxWeek1520jul,
            @Field("KMACUploadMaterailRcvdFromDr") String KMACUploadMaterailRcvdFromDr,
            @Field("NoofRidacneRxWeek2431jul") String NoofRidacneRxWeek2431jul,
            @Field("DrAgreedWiththeKMACUploadedMaterial") String DrAgreedWiththeKMACUploadedMaterial,
            @Field("NoofRidacneRxWeek0107aug") String NoofRidacneRxWeek0107aug,
            @Field("HandedOverKMACInstrumentToTheDr") String HandedOverKMACInstrumentToTheDr,
            @Field("NoofRidacneRxWeek0814aug") String NoofRidacneRxWeek0814aug,
            @Field("KMACRelatedallMaterialPlacedDspatientWaitingarena") String KMACRelatedallMaterialPlacedDspatientWaitingarena,
            @Field("NoofRidacneRxWeek1622aug") String NoofRidacneRxWeek1622aug,
            @Field("KMACRunningWellCheckednFdbkUpdatedDr") String KMACRunningWellCheckednFdbkUpdatedDr,
            @Field("NoofRidacneRxWeek2431aug") String NoofRidacneRxWeek2431aug,
            @Field("KMACFdbkTakenFromDr") String KMACFdbkTakenFromDr,
            @Field("NoofRidacneRxWeek0105sep") String NoofRidacneRxWeek0105sep,
            @Field("sectimeKMACRunningWellcheckednFdbkUpdatedDr") String sectimeKMACRunningWellcheckednFdbkUpdatedDr,
            @Field("NoofRidacneRxWeek1630sep") String NoofRidacneRxWeek1630sep,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("checkDCRSummary.php")
    Call<GetDCRSummaryMainRes> getDCRSummary(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("remark") String remark,
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("confirmDCREntry.php")
    Call<ConfirmDCRRes> confirmDCREntry(
            @Field("Dsvlcalls") String Dsvlcalls,
            @Field("DNsvlcalls") String DNsvlcalls,
            @Field("Csvlcalls") String Csvlcalls,
            @Field("CNsvlcalls") String CNsvlcalls,
            @Field("NoPOB") String NoPOB,
            @Field("TotPOB") String TotPOB,
            @Field("Deduction") String Deduction,
            @Field("ecode") String ecode,
            @Field("DCRDate") String DCRDate,
            @Field("netid") String netid,
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("DrMissCallAlert.php")
    Call<MissCallDocsRes> DrMissCallAlert(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("logyr") String logyr,
            @Field("logmth") String logmth,
            @Field("checkmtp") String checkmtp,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("nextMthMTPConf.php")
    Call<NextMTPListRes> nextMthMTPConf(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("wyr") String wyr,
            @Field("wmonth") String wmonth,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("getMTPListOfMth.php")
    Call<NewMTPListOfMTHRes> getMTPListOfMth(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("wyr") String wyr,
            @Field("wmonth") String wmonth,
            @Field("whichmth") String whichmth,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("confirmMTP.php")
    Call<DefaultResponse> confirmMTP(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("yr") String yr,
            @Field("mth") String mth,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("deleteMTPEntry.php")
    Call<DefaultResponse> deleteMTPEntry(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("tcpid") String tcpid,
            @Field("wdate") String wdate,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("getVisitPlanSummary.php")
    Call<VstPlnSumRes> getVisitPlanSummary(
            @Field("netid") String netid,
            @Field("prevfinyr") String prevfinyr,
            @Field("finyr") String finyr,
            @Field("mtpdate") String mtpdate,
            @Field("mode") String mode,
            @Field("cntcd") String cntcd,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("getVisitPlanDocList.php")
    Call<VstPlnDocLstRes> getVisitPlanDocList(
            @Field("netid") String netid,
            @Field("mtpdate") String mtpdate,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("editMTPEntry.php")
    Call<EditMtpFormResponse> editMTPEntry(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("tcpid") String tcpid,
            @Field("wdate") String wdate,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("updateMTPEntry.php")
    Call<DefaultResponse> updateMTPEntry(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("tcpid") String tcpid,
            @Field("wdate") String wdate,
            @Field("objctv") String objctv,
            @Field("mngrjtwrk") String mngrjtwrk,
            @Field("prevtcpid") String prevtcpid,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("rcpa_chemist.php")
    Call<GetRCPAPulseChemist> rcpa_chemist(
            @Field("cntcd") String cntcd,
            @Field("netid") String netid,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("rcpa_brands.php")
    Call<GetRCPABrandListRes> rcpa_brands(
            @Field("cntcd") String cntcd,
            @Field("netid") String netid,
            @Field("yrmth") String yrmth,
            @Field("d1d2") String d1d2,
            @Field("DBPrefix") String dbprefix,
            @Field("dcrdate") String dcrdate,
            @Field("drcntcd") String doccntcd,
            @Field("dcrno") String dcrno
    );

    @FormUrlEncoded
    @POST("rcpa_comp_prod.php")
    Call<GetRCPACompProdLstRes> rcpa_comp_prod(
            @Field("cntcd") String cntcd,
            @Field("netid") String netid,
            @Field("yrmth") String yrmth,
            @Field("prodid") String prodid,
            @Field("DBPrefix") String dbprefix,
            @Field("dcrdate") String dcrdate,
            @Field("drcntcd") String doccntcd,
            @Field("dcrno") String dcrno
    );


    @FormUrlEncoded
    @POST("mgr_rcpa_comp_prod.php")                     //added by aniket 01/09/2019
    Call<GetRCPACompProdLstRes> mgr_rcpa_comp_prod(
            @Field("cntcd") String cntcd,
            @Field("prodid") String prodid,
            @Field("wnetid") String wnetid,
            @Field("DBPrefix") String dbprefix,
            @Field("netid") String netid,
            @Field("date") String date,
            @Field("drcntcd") String drcntcd
    );


    @FormUrlEncoded
    @POST("mgr_rcpa_brands.php")                             //added by aniket 01/09/2019
    Call<GetRCPABrandListRes> mgr_rcpa_brands(
            @Field("drcntcd") String drcntcd,
            @Field("wnetid") String wnetid,
            @Field("netid") String netid,
            @Field("DBPrefix") String dbprefix,
            @Field("date") String date,
            @Field("cntcd") String cntcd,
            @Field("d1d2") String d1d2
    );


    @FormUrlEncoded
    @POST("elearning_first_api.php")
    Call<EleaningMainRes> getElearningData(
            @Field("ecode") String ecode,
            @Field("d1d2") String d1d2,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("fetch_questions_list.php")
    Call<QuizMainRes> getQuesData(
            @Field("testid") String testid,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("saveTestResult.php")
    Call<DefaultResponse> saveTestResult(
            @Field("testid") String testid,
            @Field("ecode") String ecode,
            @Field("percentage") String percentage,
            @Field("TotalCorrect") String TotalCorrect,
            @Field("NoOfQuestions") String NoOfQuestions,
            @Field("TimeTaken") String TimeTaken,
            @Field("DBPrefix") String dbprefix,
            @Field("noOfAttmpts") String noOfAttmpts
    );

    @FormUrlEncoded
    @POST("sysPrm.php")
    Call<DefaultResponse> sysPrm(@Field("DBPrefix") String dbprefix); //added by aniket 14/09/2019


    @FormUrlEncoded
    @POST("getHqPsrList.php")                               //added by aniket 01/09/2019
    Call<MgrRCPARes> getHqPsrListUnderMgr(
            @Field("ecode") String ecode,
            @Field("DBPrefix") String dbprefix,
            @Field("yr") String yr,
            @Field("mth") String mth,
            @Field("lvl") String emplevel);


    @FormUrlEncoded
    @POST("getDrList.php")                     //added by aniket 01/09/2019
    Call<MgrRcpaDrRes> getDrList(
            @Field("netid") String netid,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("chemistProfileList.php")                     //added by aniket 20/11/2019
    Call<ChemistProRes> chemistProfileList(
            @Field("netid") String netid,
            @Field("mth") String mth,
            @Field("yr") String yr,
            @Field("DBPrefix") String dbprefix,
            @Field("menu") String menu
    );

    @FormUrlEncoded
    @POST("savepatientDetails.php")
    Call<DefaultResponse> savePatientData(
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("patientname") String patientname,
            @Field("parentname") String parentname,
            @Field("phonenumber") String phonenumber,
            @Field("gender") String gender,
            @Field("ecode") String ecode,
            @Field("age") String age,
            @Field("DBPrefix") String dbprefix
    );                                                               //added by aniket  26/11/2019

    @FormUrlEncoded
    @POST("getPatientList.php")
    Call<PatientListRes> getPatientList(
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("DBPrefix") String dbprefix
    );                                                                 //added by aniket  26/11/2019


    @FormUrlEncoded
    @POST("getDoctorNameOfChemist.php")
    Call<ChemistDoctorNameRes> getChemistDrName(
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("sttype") String sttype,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("getChemistDataToSet.php")
    Call<ChemistDetailRes> getChemistData(
            @Field("cntcd") String cntcd,
            @Field("netid") String netid,
            @Field("DBPrefix") String dbprefix,
            @Field("menu") String menu
    );

  @FormUrlEncoded
    @POST("getChemistDataToSet.php")
    Call<ChemistDetailRes> updateChemData(
            @Field("cntcd") String cntcd,
            @Field("netid") String netid,
            @Field("DBPrefix") String dbprefix,
            @Field("menu") String menu
    );



    @FormUrlEncoded
    @POST("getRetailerAlert.php")
    Call<GetRetailerAlertCnt> getRetailerCntAlert(
            @Field("netid") String netid,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("props.php")
    Call<RetailerAndOptions> getProps(
            @Field("DBPrefix") String dbprefix
    );


    @FormUrlEncoded
    @POST("deletePatient.php")
    Call<PatientListRes> deletePatient(
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("patno") String patno,
            @Field("DBPrefix") String dbprefix
    );

@FormUrlEncoded
    @POST("hoCurrMthMtp.php")
    Call<HoMtpResponse> getCurrMthHoMtp(
            @Field("ecode") String ecode,
            @Field("date") String date,
            @Field("DBPrefix") String dbprefix
    );

@FormUrlEncoded
    @POST("hoMtpMgrList.php")
    Call<HoMtpMgrListResponse> getMgrListForHoMtp(
            @Field("ecode") String ecode,
            @Field("date") String date,
            @Field("DBPrefix") String dbprefix
    );

@FormUrlEncoded
    @POST("hoMtpPsrList.php")
    Call<HoMtpPsrListResponse> getHoMtpPsrList(
            @Field("emp") String emp,
            @Field("DBPrefix") String dbprefix,
            @Field("date") String date

    );

@FormUrlEncoded
    @POST("hoMtpPsrPatchList.php")
    Call<HoMtpPatchListResponse> getPsrPatchForHoMtp(
            @Field("emp") String emp,
            @Field("mrnetid") String mrnetid,
            @Field("DBPrefix") String dbprefix

    );

@FormUrlEncoded
    @POST("hoMtpAdd.php")
    Call<DefaultResponse> addHoMtp(
        @Field("wdate") String wdate,
        @Field("emp") String emp,
        @Field("mrnetid") String mrnetid,
        @Field("addedby") String addedby,
        @Field("logEmp") String logEmp,
        @Field("tcpIdJsonArray") String tcpIdJsonArray,
        @Field("DBPrefix") String dbprefix
    );

    //to check weather user is resigned or not
    @FormUrlEncoded
    @POST("changeHODCRDate.php")
    Call<DefaultResponse> changeHODCRDate(
            @Field("empcode") String empcode,
            @Field("newdcrdate") String newdcrdate,
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String DBPrefix
    );

    //to get Hodcrdate and also checks weather it is greater than current date or not
    @FormUrlEncoded
    @POST("checkhodcrdate.php") // Added on 09-03-2020 by Patanjali
    Call<HODcrDateResponse> getHoDcrdate(
            @Field("empcode") String ecode,
            @Field("DBPrefix") String DBPrefix
    );

    //to check HO MTP. It checks weather the MTP is filled or not of current date and also check MTP of next month on 24 of each month
    @FormUrlEncoded
    @POST("checkcurmthhomtp.php")  // Added on 09-03-2020 by Patanjali
    Call<DefaultResponse> checkHOMTP(
            @Field("empcode") String ecode,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("DBPrefix") String DBPrefix
    );


    //to check dcr is blocked or not
    @FormUrlEncoded
    @POST("hodcrblockcheck.php") // added on 09-03-2020 by Patanjali
    Call<DefaultResponse> HODCRBlockCheck(
            @Field("empcode") String ecode,
            @Field("dcrdate") String dcrdate,
            @Field("DBPrefix") String DBPrefix
    );


    //to get HO holiday in between last confirm dcr and current dcrdate
    @FormUrlEncoded
    @POST("gethoholdcrdates.php") // added on 09-03-2020 by Patanjali
    Call<DefaultResponse> getHOHolidayDcrdates(
            @Field("empcode") String ecode,
            @Field("dcrdate") String dcrdate,
            @Field("DBPrefix") String DBPrefix
    );

    //By patanjali
    @FormUrlEncoded
    @POST("holevelList.php") //Added on 08-03-2020 By Patanjali
    Call<HOLevelListResponse> getHOLevelList(
            @Field("dcrdate") String dcrdate,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("netid") String netid,
            @Field("hname") String hname,
            @Field("lvl") String lvl,
            @Field("lvl5") String lvl5,
            @Field("lvl4") String lvl4,
            @Field("lvl3") String lvl3,
            @Field("lvl2") String lvl2,
            @Field("lvl1") String lvl1,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("gethodoctordatalist.php") // HO Doctor List added by Patanjali
    Call<HODoctorListResponse> getHODoctorDataList(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("tcpid") String tcpid,
            @Field("dcrdate") String dcrdate,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("srchopt") String srchopt,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("gethodcrddoc.php") // added By Patanjali on 10/03/2020
    Call<HoDcrdDrListResponse> getHODCRDDrs(
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("hodcrproductlist.php") // added on 15/03/2020 by Patanjali
    Call<HODCRProductResponse> HODCRProdApi(
            @Field("serial") String serial,
            @Field("netid") String netid,
            @Field("dcrno") String dcrno,
            @Field("d1d2") String d1d2,
            @Field("ecode") String ecode,
            @Field("financialyear") String financialyear,
            @Field("dcrdate") String dcrdate,
            @Field("mth") String mth,
            @Field("yr") String yr,
            @Field("cntcd") String cntcd,
            @Field("logmth") String logmth,
            @Field("logyr") String logyr,
            @Field("DBPrefix") String DBPrefix
    );


    @FormUrlEncoded
    @POST("HODCRGift.php") // added on 15/03/2020 by Patanjali
    Call<DCRGiftListRes> HODCRGiftApi(
            @Field("serial") String serial,
            @Field("netid") String netid,
            @Field("dcrno") String dcrno,
            @Field("d1d2") String d1d2,
            @Field("ecode") String ecode,
            @Field("DBPrefix") String DBPrefix
    );


    @FormUrlEncoded
    @POST("getHODocDCRRemark.php") // added on 15/03/2020 by Patanjali
    Call<DefaultResponse> getHODocDcrRemark(
            @Field("dcrno") String dcrno,
            @Field("serial") String serial,
            @Field("DBPrefix") String DBPrefix
    );


    @FormUrlEncoded
    @POST("updateHODocDcrRemark.php") // added on 15/03/2020 by Patanjali
    Call<DefaultResponse> saveHODocDcrRemark(
            @Field("dcrno") String dcrno,
            @Field("serial") String serial,
            @Field("remark") String remark,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("getHODocDcrDetail.php") // added on 16/03/2020 by Patanjali
    Call<HODcrdDocDetResponse> getHODocDcrDetail(
            @Field("dcrno") String dcrno,
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("DBPrefix") String DBPrefix
    );



    @FormUrlEncoded
    @POST("gethochemistdatalist.php") // added by Patanjali on 16-03-2020
    Call<HOChemistListResponse> getHOChemistDataList(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("tcpid") String tcpid,
            @Field("dcrdate") String dcrdate,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("srchopt") String srchopt,
            @Field("DBPrefix") String DBPrefix
    );


    @FormUrlEncoded
    @POST("gethodcrdchem.php") // added on 16/03/2020 By Patanjali
    Call<HODcrdChemListResponse> getHODCRDChem(
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String DBPrefix
    );


    @FormUrlEncoded
    @POST("getHOChemPOBEntry.php") // added on 16/03/2020 by Patanjali
    Call<DefaultResponse> getHOChemPOBEntry(
            @Field("dcrno") String dcrno,
            @Field("serial") String serial,
            @Field("DBPrefix") String DBPrefix
    );


    @FormUrlEncoded
    @POST("updateHOChemDcrPOBEntry.php") // added on 16/03/2020 by Patanjali
    Call<DefaultResponse> saveHOChemDcrPOBEntry(
            @Field("dcrno") String dcrno,
            @Field("serial") String serial,
            @Field("cntcd") String cntcd,
            @Field("pob") String pob,
            @Field("rcpa") String rcpa,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("gethohubdatalist.php") // added by Patanjali on 16-03-2020
    Call<HOHubListResponse> getHOHubDataList(
            @Field("ecode") String ecode,
            @Field("statecode") String statecode,
            @Field("srchopt") String srchopt,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("gethodcrdhub.php") // added on 16/03/2020 By Patanjali
    Call<HODcrdHubListResponse> getHODCRDHub(
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String DBPrefix
    );



    @FormUrlEncoded
    @POST("gethowddatalist.php") // added by Patanjali on 17-03-2020
    Call<HOWDListResponse> getHOWDDataList(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("tcpid") String tcpid,
            @Field("dcrdate") String dcrdate,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("srchopt") String srchopt,
            @Field("d1d2") String d1d2,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("gethodcrdwd.php") // added on 17/03/2020 By Patanjali
    Call<HODcrdWDListResponse> getHODCRDWD(
            @Field("dcrno") String dcrno,
            @Field("d1d2") String d1d2,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("getHONonFieldWorkList.php")// added on 18/03/2020 By Patanjali
    Call<HONonFieldWorkListRes> getHONonFieldWorkList(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrno") String dcrno,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("deleteHONonFieldWrk.php") // added on 18/03/2020 By Patanjali
    Call<DefaultResponse> deleteHONonFieldWrkEntry(
            @Field("dcrno") String dcrno,
            @Field("serial") String serial,
            @Field("DBPrefix") String DBPrefix
    );


    @FormUrlEncoded
    @POST("hoExpenseData.php") // added on 16/03/2020 by Patanjali
    Call<HOExpenseDefaultDataRes> getHOExpenseData(
            @Field("dcrno") String dcrno,
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("hoExpenseDataByTown.php") // added on 16/03/2020 by Patanjali
    Call<HOFrmToTownsDetRes> getHOExpenseDataByTown(
            @Field("dcrno") String dcrno,
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("dcrdate") String dcrdate,
            @Field("dcrmth") String dcrmth,
            @Field("dcryr") String dcryr,
            @Field("towns") String towns,
            @Field("DBPrefix") String DBPrefix
    );

    @POST("accessHODcrSummary.php")// added on 27/03/2020 by Patanjali
    Call<IsDCRCorrectRes> isHODCRCorrectlyFilled(
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("checkHODCRSummary.php")// added on 31/03/2020 by Patanjali
    Call<HODCRSummaryMainRes> getHODCRSummary(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("remark") String remark,
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("confirmHODCREntry.php") // added on 31/03/2020 by Patanjali
    Call<HOConfirmDCRRes> confirmHODCREntry(
            @Field("doccalls") String doccalls,
            @Field("chemcalls") String chemcalls,
            @Field("wdcalls") String wdcalls,
            @Field("hubcalls") String hubcalls,
            @Field("NoPOB") String NoPOB,
            @Field("TotPOB") String TotPOB,
            @Field("Deduction") String Deduction,
            @Field("ecode") String ecode,
            @Field("DCRDate") String DCRDate,
            @Field("netid") String netid,
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("updateHOOJTSent.php")// added on 16/04/2020 by Patanjali
    Call<DefaultResponse> updateHOOJTSent(
            @Field("ecode") String ecode,
            @Field("ojtflag") String ojtflag,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("checkHOOJTForNotification.php") // added on 17/04/2020 by Patanjali
    Call<HOOJTFlagNotifyRes> checkHOOJTForNotification(
            @Field("ecode") String ecode,
            @Field("DBPrefix") String dbprefix
    );


    //prithvi - audio msg - 07/04/2020 : start

    @FormUrlEncoded
    @POST("audioMsgViewDet.php")
    Call<DefaultResponse> getAudioMsgViewDet(
            @Field("ecode") String ecode,
            @Field("DBPrefix") String DBPrefix,
            @Field("audioCode") String audioCode
    );

    @FormUrlEncoded
    @POST("saveAudioViewDetls.php")
    Call<DefaultResponse> saveAudioViewDetls(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("audioPopupShow") int audioPopupShow,
            @Field("DBPrefix") String DBPrefix,
            @Field("audioCode") String audioCode
    );
    //prithvi - audio msg - 07/04/2020 : end

    //prithvi - audio msg - 10/04/2020 : start

    @FormUrlEncoded
    @POST("imgMsgViewDet.php")
    Call<DefaultResponse> getImgMsgViewDet(
            @Field("ecode") String ecode,
            @Field("imgCode") String imgCode,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("imgMsgViewSave.php")
    Call<DefaultResponse> saveImgViewDetls(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("audioPopupShow") int audioPopupShow,
            @Field("imgCode") String imgCode,
            @Field("DBPrefix") String DBPrefix
    );
    //prithvi - audio msg - 10/04/2020 : end

    //prithvi - Special DCR 16/04/2020 : Start

    @FormUrlEncoded
    @POST("spclDcrAreaList.php")
    Call<AreaJntWrkRes> getSpclDcrAreaList(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("hname") String hname,
            @Field("cdate") String cdate,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("spclDcrDoctorList.php")
    Call<DoctorListAWRes> getSpclDcrDrList(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("tcpid") String tcpid,
            @Field("cdate") String cdate,
            @Field("sel") String sel,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("spcldcrgetdcrddoc.php")
    Call<SpclDcrdDrListRes> spclDcrGetDcrdDoc(
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String DBPrefix,
            @Field("netid") String netid,
            @Field("emp") String emp,
            @Field("cdate") String cdate
    );

    @FormUrlEncoded
    @POST("spcldcrgetdcrdchem.php")
    Call<SpclDcrDcrdChLstRes> spclDcrGetDcrdChem(
            @Field("dcrno") String dcrno,
            @Field("DBPrefix") String DBPrefix,
            @Field("netid") String netid,
            @Field("emp") String emp,
            @Field("cdate") String cdate,
            @Field("spclDcrChPopup") String spclDcrChPopup,
            @Field("div") String div
    );

    @FormUrlEncoded
    @POST("spcldcrdelrecord.php")
    Call<DefaultResponse> deleteDrChfromSpclDcr(
            @Field("emp") String emp,
            @Field("netid") String netid,
            @Field("dcrno") String dcrno,
            @Field("cdate") String cdate,
            @Field("cntcd") String cntcd,
            @Field("custflg") String custFlg,
            @Field("DBPrefix") String DBPrefix,
            @Field("spclDcrChPopup") String spclDcrChPopup
    );

    @FormUrlEncoded
    @POST("spcldcrgetremark.php")
    Call<DefaultResponse> getRemarkForSpclDcr(
            @Field("dcrno") String dcrno,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("custflg") String custflg,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("spcldcrupdateremark.php")
    Call<DefaultResponse> saveRemarkForSpclDcr(
            @Field("dcrno") String dcrno,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("custflg") String custflg,
            @Field("remark") String remark,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("spclDcrSavePracDet.php")
    Call<DefaultResponse> savePracticeDet(
            @Field("dcrno") String dcrno,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("custflg") String custflg,
            @Field("sel") String sel,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("spclDcrGetDcrNo.php")
    Call<GetDcrDateRes> getSpclDcrno(
            @Field("cdate") String cdate,
            @Field("emp") String emp,
            @Field("netid") String netid,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("spclDcrGetPracDet.php")
    Call<DefaultResponse> getPracticeDet(
            @Field("dcrno") String cdate,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("custflg") String custflg,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("spclDcrUpdtDrPhn.php")
    Call<DefaultResponse> updatePhnNoOfDr(
            @Field("dcrno") String dcrno,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("custflg") String custflg,
            @Field("phnno") String phnno,
            @Field("DBPrefix") String DBPrefix
    );

@FormUrlEncoded
    @POST("spclDcrChemList.php")
    Call<ChemistListAWRes> getSpclDcrChemList(
        @Field("ecode") String ecode,
        @Field("netid") String netid,
        @Field("tcpid") String tcpid,
        @Field("cdate") String cdate,
        @Field("sel") String sel,
        @Field("DBPrefix") String DBPrefix
    );

@FormUrlEncoded
    @POST("spclDcrSubmit.php")
    Call<DefaultResponse> submitSpclDcr(
        @Field("cdate") String cdate,
        @Field("ecode") String ecode,
        @Field("netid") String netid,
        @Field("tcpid") String tcpid,
        @Field("dcrno") String dcrno,
        @Field("DBPrefix") String DBPrefix
    );

@FormUrlEncoded
    @POST("spclDcrSavePob.php")
    Call<DefaultResponse> savePob(
        @Field("dcrno") String dcrno,
        @Field("netid") String netid,
        @Field("ecode") String ecode,
        @Field("cntcd") String cntcd,
        @Field("custflg") String custflg,
        @Field("pob") String pobTxt,
        @Field("cdate") String cdate,
        @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("spclDcrQuestnPopup.php")
    Call<GetPopupQuesRes> getQuestnsForPopup(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("custFlag") String custFlag,
            @Field("d1d2") String d1d2,
            @Field("mth") String mth,
            @Field("yr") String yr,
            @Field("cntcd") String cntcd,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("SpclDcrSavePopupQA.php")
    Call<DefaultResponse> savePopupQASpclDcr(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("cdate") String cdate,
            @Field("jsonstr") String jsonstr,
            @Field("mth") String mth,
            @Field("yr") String yr,
            @Field("cntcd") String cntcd,
            @Field("optType") String optType,
            @Field("DBPrefix") String dbprefix
    );
    //prithvi - Special DCR 16/04/2020 : End

    //prithvi - Chemist Add/Edit/Delete : Start

    @FormUrlEncoded
    @POST("chemDetlsUpdate.php")
    Call<DefaultResponse> updateChemDetails(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("keycontactper") String keycontactper,
            @Field("chemName") String chemName,
            @Field("phnno") String phnno,
            @Field("sttype") String sttype,
            @Field("add1") String add1,
            @Field("add2") String add2,
            @Field("add3") String add3,
            @Field("city") String city,
            @Field("state") String state,
            @Field("pincode") String pincode,
            @Field("newCls") String newCls,
            @Field("newNoVst") String newNoVst,
            @Field("selTcpJson") String selTcpJson,
            @Field("toUpdt") String toUpdt,
            @Field("DBPrefix") String dbprefix
    );


    @FormUrlEncoded
    @POST("getStateList.php")
    Call<StateListResponse> getStateList(
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("chemDetlsValidate.php")
    Call<DefaultResponse> chemDetlsValidate(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("chemName") String chemName,
            @Field("phnno") String phnno,
            @Field("addEdit") String addEdit,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("chemDetlsDelete.php")
    Call<DefaultResponse> deleteChemData(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("menu") String menu,
            @Field("DBPrefix") String dbprefix
    );


    @FormUrlEncoded
    @POST("getPatchList.php")
    Call<PatchListResponse> getPatchAndClsList(
            @Field("netid") String netid,
            @Field("DBPrefix") String dbprefix
    );


    //prithvi - Chemist Add/Edit/Delete : End

    @FormUrlEncoded
    @POST("DocDcrSaveCallType.php")
    Call<DefaultResponse> saveCallTypDet(
            @Field("dcrno") String dcrno,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("sel") String sel,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("sodUpdatePhnNo.php")
    Call<DefaultResponse> saveSodPhnNo(
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("phno") String phonenumber,
            @Field("ecode") String ecode,
            @Field("DBPrefix") String DBPrefix
    );

@FormUrlEncoded
    @POST("sodGetPhnNo.php")
    Call<DefaultResponse> getSodPhnnoFromDb(
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("DBPrefix") String DBPrefix
    );


    @FormUrlEncoded
    @POST("sodAllowedDrList.php")
    Call<MgrRcpaDrRes> getSodAllowedDrList(
            @Field("netid") String netid,
            @Field("DBPrefix") String dbprefix
    );

    /*Customer Add/Edit : Start */
    @FormUrlEncoded
    @POST("CustList.php")
    Call<CustListResponse> getOtherCustList(
            @Field("netid") String netid,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("custTypeCatPatchList.php")
    Call<CustCatTypPatchListRes> getCatTypnPatchLst(
            @Field("netid") String netid,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("custDetlsValidate.php")
    Call<DefaultResponse> custDetlsValidate(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("custName") String chemName,
            @Field("phnno") String phnno,
            @Field("addEdit") String addEdit,
            @Field("DBPrefix") String dbprefix
    );

@FormUrlEncoded
    @POST("custDetails.php")
    Call<CustDetailsRes> getCustData(
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("DBPrefix") String dbprefix
    );

    @FormUrlEncoded
    @POST("custDetlsUpdate.php")
    Call<DefaultResponse> updateCustDetails(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("keycontactper") String keycontactper,
            @Field("custNm") String custNm,
            @Field("phnno") String phnno,
            @Field("custtype") String custtype,
            @Field("custcat") String custcat,
            @Field("add1") String add1,
            @Field("add2") String add2,
            @Field("add3") String add3,
            @Field("city") String city,
            @Field("state") String state,
            @Field("pincode") String pincode,
            @Field("tcpid") String tcpid,
            @Field("toUpdt") String toUpdt,
            @Field("DBPrefix") String dbprefix
    );


    @FormUrlEncoded
    @POST("custDetlsDelete.php")
    Call<DefaultResponse> deleteCustData(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("menu") String menu,
            @Field("DBPrefix") String dbprefix
    );

    /*31-08-2020 p1 to p5 product exposure selection*/
    @FormUrlEncoded
    @POST("p1p2p3OfDr.php")
    Call<P1P2P3DrProdRes> getDrPrdFromDB(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("menu") String menu,
            @Field("DBPrefix") String dbprefix
    );

@FormUrlEncoded
    @POST("getProdList.php")
    Call<ProductListRes> getProducts(
            @Field("div") String div,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("menu") String menu,
            @Field("DBPrefix") String dbprefix
    );

@FormUrlEncoded
    @POST("p1p2p3Save.php")
    Call<DefaultResponse> saveDrsP1toP5(
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("ecode") String ecode,
            @Field("DBPrefix") String dbprefix,
            @Field("p1") String p1,
            @Field("p2") String p2,
            @Field("p3") String p3,
            @Field("p4") String p4,
            @Field("p5") String p5
    );



}
