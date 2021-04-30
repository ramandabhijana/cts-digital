<#import "testercommon/bootstrap.ftl" as b>

<@b.page>
    <div class="container-fluid">
        <div class="row mt-5">
            <div class="col-xl-3 col-lg-3 ">
                <div class="card border-primary mb-4">
                    <div class="card-header list-group-item list-group-item-primary">
                        <h6 style="text-align: center;" class="m-0 font-weight-bold">Pending Tests</h6>
                    </div>
                    <div class="card-body">
                        <h2 style="text-align: center;">${pending}</h2>
                    </div>
                </div>

            </div>

            <div class="col-xl-3 col-lg-3 ">
                <div class="card border-primary mb-4">
                    <div class="card-header list-group-item list-group-item-primary">
                        <h6 style="text-align: center;" class="m-0 font-weight-bold">Completed Tests</h6>
                    </div>
                    <div class="card-body">
                        <h2 style="text-align: center;">${completed}</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row mb-5">
            <div class="col-lg-4 col-xlg-3 col-md-5">
                <div class="card shadow-sm mb-4">
                    <div class="card-header py-3">
                        <h6 style="text-align: center;" class="m-0 font-weight-bold">Profile</h6>
                    </div>
                    <div class="card-body">
                        <center class="m-t-30">
                            <img src="https://sion.stikom-bali.ac.id/assets/images/user.png"
                            class=" img-rounder " width=" 150 " alt=" ">
                            <h4 class=" card-title mb-5 ">${user.firstName} ${user.lastName}</h4>
                            <h6 class=" card-subtitle ">
                                <a href="#update_profile"
                                   class=" btn btn-outline-primary "
                                   data-toggle="modal"
                                >Update Profile</a>
                            </h6>
                        </center>
                    </div>
                    <div>
                        <hr>
                    </div>
                    <div class=" card-body ">
                        <small class=" text-muted ">Full Name</small>
                        <h6>${user.firstName} ${user.lastName}</h6>
                        <small class=" text-muted p-t-30 db ">Username</small>
                        <h6>${user.username}</h6>
                        <small class=" text-muted p-t-30 db ">Position</small>
                        <h6>Tester</h6>

                    </div>
                </div>

            </div>

            <div class=" col-xlg-7 col-lg-6 col-md-5 ">
                <div class=" card shadow-sm mb-4 ">
                    <div class=" card-header py-3 ">
                        <h6 class=" m-0 font-weight-bold " style=" text-align: center; ">Test</h6>
                    </div>
                    <div class=" card-body ">
                        <table class=" table ">
                            <thead>
                            <tr>
                                <th scope=" col ">ID</th>
                                <th scope=" col ">Patient Username</th>
                                <th scope=" col ">Patient Name</th>
                                <th scope=" col ">Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if tests?? && (tests?size > 0)>
                                <#list tests as test>
                                    <tr>
                                        <td>${test["id"]}</td>
                                        <td>${test["username"]}</td>
                                        <td>${test["name"]}</td>
                                        <#if test["status"]?contains("1")>
                                            <td>
                                                <span class="badge badge-pill badge-success">Completed</span>
                                            </td>
                                        <#else>
                                            <td>
                                                <span class="badge badge-pill badge-warning">Pending</span>
                                            </td>
                                        </#if>
                                    </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>

                    </div>
                </div>

            </div>
        </div>
    </div>
    <#include "officerupdateprofile.ftl">
</@b.page>