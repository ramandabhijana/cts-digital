<#import "patientcommon/bootstrap.ftl" as b>

<@b.page>
    <a href="#update_profile"
       class="text-primary"
       data-toggle="modal"
       data-firstName="${patient.firstName}"
       data-lastName="${patient.lastName}"
       data-dob="${patient.birthDate}"
       data-username="${patient.username}"
       data-type="${patient.type}"
       data-symptoms="${patient.symptoms}"
    >Update profile</a>

    <#include "updatepatientprofile.ftl">
</@b.page>