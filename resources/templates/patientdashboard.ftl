<#import "patientcommon/bootstrap.ftl" as b>

<@b.page>
    <div class="row my-5 mx-2">
        <div class="col-lg-4 col-xlg-3 col-md-5">
            <div class="card shadow-sm mb-4">
                <div class="card-header py-3">
                    <h6 style="text-align: center;" class="m-0 font-weight-bold">Profile</h6>
                </div>
                <div class="card-body">
                    <center class="m-t-30">
                        <img src="https://sion.stikom-bali.ac.id/assets/images/user.png" class=" img-rounder "  width=" 150 " alt=" ">
                        <h4 class=" card-title mb-5 ">${patient.firstName} ${patient.lastName}</h4>
                        <h6 class=" card-subtitle ">
                            <a href="#update_profile"
                               class=" btn btn-outline-primary "
                               data-toggle="modal"
                               data-firstName="${patient.firstName}"
                               data-lastName="${patient.lastName}"
                               data-dob="${patient.birthDate}"
                               data-username="${patient.username}"
                               data-type="${patient.type}"
                               data-symptoms="${patient.symptoms}"
                            >Update Profile</a>
                        </h6>
                    </center>
                </div>
                <div><hr></div>
                <div class=" card-body ">
                    <small class="text-muted ">Full Name</small>
                    <h6>${patient.firstName} ${patient.lastName}</h6>
                    <small class=" text-muted p-t-30 db ">Birthdate</small>
                    <h6>${patient.birthDate}</h6>

                </div>
            </div>

        </div>

        <div class=" col-lg-4 col-xlg-3 col-md-5 ">
            <div class=" card shadow-sm mb-4 ">
                <div class=" card-body ">
                    <small class=" text-muted ">Patient Type</small>
                    <h6>${patient.type}</h6>
                    <small class=" text-muted p-t-30 db ">Symptoms</small>
                    <h6>${patient.symptoms}</h6>

                </div>
            </div>

        </div>
    </div>

    <#include "updatepatientprofile.ftl">
</@b.page>