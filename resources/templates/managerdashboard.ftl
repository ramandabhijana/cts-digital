<#import "managercommon/bootstrap.ftl" as b>

<@b.page>
    <div class="container-fluid">
        <div class="row mt-5">
            <div class="col-xl-3 col-lg-3">
                <div class="card border-primary mb-4">
                    <div class="card-header list-group-item list-group-item-primary">
                        <h6 style="text-align: center;" class="m-0 font-weight-bold">Testers</h6>
                    </div>
                    <div class="card-body">
                        <h2 style="text-align: center;">${testers?size}</h2>
                    </div>
                </div>

            </div>

            <div class="col-xl-3 col-lg-3 ">
                <div class="card border-primary mb-4">
                    <div class="card-header list-group-item list-group-item-primary">
                        <h6 style="text-align: center;" class="m-0 font-weight-bold">Kit Stock Available</h6>
                    </div>
                    <div class="card-body">
                        <h2 style="text-align: center;">${stockAvailable}</h2>
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
                            class=" img-rounder "  width=" 150 " alt=" ">
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
                        <h6>Test Center Manager</h6>

                    </div>
                </div>

            </div>

            <div class=" col-xlg-9 col-lg-8 col-md-7 ">
                <div class=" card shadow-sm mb-4 ">
                    <!-- Card Header - Dropdown -->
                    <div class=" card-header py-3 ">
                        <h6 class=" m-0 font-weight-bold " style=" text-align: center; ">Testers</h6>
                    </div>
                    <!-- Card Body -->
                    <div class=" card-body ">
                        <table class=" table ">
                            <thead>
                            <tr>
                                <th scope=" col ">Username</th>
                                <th scope=" col ">Name</th>
                                <th scope=" col ">Patient Tested</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if testers?? && (testers?size > 0)>
                                <#list testers as tester>
                                    <tr>
                                        <td>${tester["username"]}</td>
                                        <td>${tester["fullName"]}</td>
                                        <td>${tester["patientTested"]}</td>
                                    </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>

                    </div>
                </div>
                <div class=" card shadow-sm mb-4 ">
                    <div class=" card-header py-3 ">
                        <h6 class=" m-0 font-weight-bold " style=" text-align: center; ">Test Kit</h6>
                    </div>
                    <div class=" card-body ">
                        <table class=" table ">
                            <thead>
                            <tr>
                                <th scope=" col ">ID</th>
                                <th scope=" col ">Name</th>
                                <th scope=" col ">Available Stock</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if kits?? && (kits?size > 0)>
                                <#list kits as kit>
                                    <tr>
                                        <td>${kit.id}</td>
                                        <td>${kit.name}</td>
                                        <td>${kit.availableStock}</td>
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