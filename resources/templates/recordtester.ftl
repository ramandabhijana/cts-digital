<#import "managercommon/bootstrap.ftl" as b>

<@b.page>
    <div class="row justify-content-center">
        <div class="col-lg-8 col-xlg-9 col-md-7 mt-5">
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
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </#if>

            <div class="card shadow-sm">
                <ul class="nav nav-tabs" role="tablist">
                    <li class="nav-item">
                        <h6 class="nav-link">Record Tester</h6>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active"  role="tabpanel">
                        <div class="card-body">
                            <form class="form-horizontal" action="/manager/recordtester" method="post">
                                <div class="form-group">
                                    <label class="col-md-12">First Name</label>
                                    <div class="col-md-12">
                                        <input type="text" name="firstname" class="form-control form-control-line" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-12">Last Name</label>
                                    <div class="col-md-12">
                                        <input type="text" name="lastname" class="form-control form-control-line" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-12">Username</label>
                                    <div class="col-md-12">
                                        <input type="text" name="username" class="form-control form-control-line" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-12">Password</label>
                                    <div class="col-md-12">
                                        <input type="password" id="password" name="password" value="" class="form-control form-control-line "required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-12">Position</label>
                                    <div class="col-md-12">
                                        <input type="text" name="position" value="Tester" class="form-control form-control-line" readonly >
                                    </div>
                                    <div><br></div>
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <button class="btn btn-success">REGISTER</button>
                                        </div>

                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</@b.page>