<#import "testercommon/bootstrap.ftl" as b>

<@b.page>
    <div class="row justify-content-center my-5 mx-2">

        <#--        Current Test Kits-->
        <div class="col-md-4 order-md-2 mb-4">

            <h6 class="d-flex justify-content-between align-items-center mb-3">
                <span class="text-muted">Current Test Kits</span>
                <#if kits?? && (kits?size > 0)>
                    <span class="badge badge-secondary badge-pill">${kits?size}</span>
                </#if>
            </h6>
            <ul class="list-group mb-3">
                <#if kits?? && (kits?size > 0)>
                    <#list kits as kit>
                        <li class="list-group-item d-flex justify-content-between lh-condensed">
                            <div>
                                <h6 class="my-0">${kit.name}</h6>
                                <small class="text-muted">Test Kit ID: ${kit.id}</small>
                            </div>
                            <span class="text-muted">${kit.availableStock}</span>
                        </li>
                    </#list>
                <#else>
                    <li class="list-group-item d-flex justify-content-between lh-condensed">
                        <div>
                            <h6 class="my-0">NOT AVAILABLE</h6>
                        </div>
                    </li>
                </#if>
            </ul>
        </div>
        <#--        Current Test Kits-->

        <div class="col-md-8 col-md-offset-4">

            <#if error??>
                <div class="alert alert-danger alert-dismissible fade show mb-3" role="alert">
                    <strong>${error}</strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </#if>

            <#if success??>
                <div class="alert alert-success alert-dismissible fade show mb-3" role="alert">
                    <strong>${success}</strong>
                    <#if testDetail??> <p>${testDetail}</p> </#if>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </#if>

            <div class="card">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="reg-patient-tab" data-toggle="tab" href="#regpatient" role="tab" aria-controls="regpatient" aria-selected="true">Registered Patient</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="new-patient-tab" data-toggle="tab" href="#newpatient" role="tab" aria-controls="newpatient" aria-selected="true">New Patient</a>
                        </li>
                    </ul>
                </div>

                <div class="tab-content">
                    <div class="tab-pane fade show active" id="regpatient" role="tabpanel" aria-labelledby="reg-patient-tab">
                        <div class="card-body">
                            <form action="/tester/recordtest" method="post">
                                <div class="mb-3">
                                    <label for="kitid">Test Kit ID</label>
                                    <div class="input-group">
                                        <select class="form-control custom-select" name="selectedTestKitId">
                                            <#if kits?? && (kits?size > 0)>
                                                <#list kits?filter(k -> k.availableStock > 0) as kit>
                                                    <option value="${kit.id}">${kit.id} (${kit.name})</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="patient">Patient Username</label>
                                    <div class="input-group">
                                        <select class="form-control custom-select" name="selectedUsername">
                                            <#if patients?? && (patients?size > 0)>
                                                <#list patients as p>
                                                    <option value="${p.username}">${p.username} (${p.firstName} ${p.lastName})</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>

                                <hr class="my-4">

                                <div class="mb-2">
                                    <!-- <label for="username h4">Patient Type</label> -->
                                    <h5>Patient Type</h5>
                                    <div class="d-block mb-3">
                                        <div class="custom-control custom-radio">
                                            <input name="patientType" type="radio" id="returnee" class="custom-control-input" required checked value="0">
                                            <label class="custom-control-label" for="returnee">Returnee</label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input name="patientType" type="radio" id="quarantined" class="custom-control-input" required value="1">
                                            <label class="custom-control-label" for="quarantined">Quarantined</label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input name="patientType" type="radio" id="closecontact" class="custom-control-input" required value="2">
                                            <label class="custom-control-label" for="closecontact">Close Contact</label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input name="patientType" type="radio" id="infected" class="custom-control-input" required value="3">
                                            <label class="custom-control-label" for="infected">Infected</label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input name="patientType" type="radio" id="suspected" class="custom-control-input" required value="4">
                                            <label class="custom-control-label" for="suspected">Suspected</label>
                                        </div>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <h5>Symptoms</h5>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="symptoms" placeholder="Eg. Fever, Cough, Sneeze" required>
                                    </div>
                                </div>

                                <button class="btn btn-success btn-lg mt-5" type="submit">Submit</button>
                            </form>
                        </div>
                    </div>

                    <div class="tab-pane fade" id="newpatient" role="tabpanel" aria-labelledby="new-patient-tab">
                        <div class="card-body">
                            <form action="/tester/recordtest" method="post">
                                <div class="mb-3">
                                    <label for="kitid">Test Kit ID</label>
                                    <div class="input-group">
                                        <select class="form-control custom-select" name="selectedTestKitId">
                                            <#if kits?? && (kits?size > 0)>
                                                <#list kits?filter(k -> k.availableStock > 0) as kit>
                                                    <option value="${kit.id}">${kit.id} (${kit.name})</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>
                                <hr class="my-4">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="firstName">First name</label>
                                        <input type="text" class="form-control" name="firstName" placeholder="" required>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="lastName">Last name</label>
                                        <input type="text" class="form-control" name="lastName" placeholder="">
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="kitid">Date of Birth</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="dob" placeholder="" required>
                                    </div>
                                    <small id="passwordHelpBlock" class="form-text text-muted">Format: dd/mm/yyyy</small>
                                </div>

                                <div class="mb-3">
                                    <label for="kitid">Username</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="username" placeholder="Enter username for patient" required>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="kitid">Password</label>
                                    <div class="input-group">
                                        <input type="password" class="form-control" name="password" placeholder="Enter password for patient" required>
                                    </div>
                                </div>
                                <hr class="my-4">
                                <div class="mb-3">
                                    <h5>Patient Type</h5>
                                    <div class="d-block mb-3">
                                        <div class="custom-control custom-radio">
                                            <input name="patientType" type="radio" id="returnee_new" class="custom-control-input" required checked value="0">
                                            <label class="custom-control-label" for="returnee_new">Returnee</label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input name="patientType" type="radio" id="quarantined_new" class="custom-control-input" required value="1">
                                            <label class="custom-control-label" for="quarantined_new">Quarantined</label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input name="patientType" type="radio" id="closecontact_new" class="custom-control-input" required value="2">
                                            <label class="custom-control-label" for="closecontact_new">Close Contact</label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input name="patientType" type="radio" id="infected_new" class="custom-control-input" required value="3">
                                            <label class="custom-control-label" for="infected_new">Infected</label>
                                        </div>
                                        <div class="custom-control custom-radio">
                                            <input name="patientType" type="radio" id="suspected_new" class="custom-control-input" required value="4">
                                            <label class="custom-control-label" for="suspected_new">Suspected</label>
                                        </div>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <h5>Symptoms</h5>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="symptoms" placeholder="Eg. Fever, Cough, Sneeze" required>
                                    </div>
                                </div>


                                <button class="btn btn-success btn-lg mt-5" type="submit">Submit</button>
                            </form>
                        </div>
                    </div>


                </div>


            </div>
        </div>
    </div>
</@b.page>