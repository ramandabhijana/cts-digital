<div class="modal fade" id="update_profile">
    <div class="modal-dialog mw-100 w-75">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Update Profile</h5>
            </div>
            <div class="modal-body" style="background-color: aliceblue;">
                <div class="row">
                    <div class="col-lg-4 col-xlg-3 col-md-5">
                        <div class="card">

                            <div class="card-body">
                                <center class="m-t-30">
                                    <img src="https://sion.stikom-bali.ac.id/assets/images/user.png"
                                    class="img-rounder"  width="150" alt="">
                                    <h4 class="card-title m-t-10">${user.firstName} ${user.lastName}</h4>
                                </center>

                            </div>
                            <div><hr></div>
                            <div class="card-body">
                                <small class="text-muted">Full Name</small>
                                <h6>${user.firstName} ${user.lastName}</h6>
                                <small class="text-muted p-t-30 db">Username</small>
                                <h6>${user.username}</h6>
                                <small class="text-muted p-t-30 db">Position</small>
                                <h6>${user.position}</h6>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-8 col-xlg-9 col-md-7">
                        <div class="card">
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="nav-item">
                                    <h6 class="nav-link">Update Profile</h6>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active"  role="tabpanel">
                                    <div class="card-body">
                                        <form class="form-horizontal" id="update-profile-form" action="/user/updateprofile" method="post" onsubmit="alert('Your profile was updated')">
                                            <div class="form-group">
                                                <label class="col-md-12">First Name</label>
                                                <div class="col-md-12">
                                                    <input type="text" name="firstName" value="${user.firstName}" class="form-control form-control-line" required>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-12">Last Name</label>
                                                <div class="col-md-12">
                                                    <input type="text" name="lastName" value="${user.lastName}" class="form-control form-control-line" required>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-12">Username</label>
                                                <div class="col-md-12">
                                                    <input type="text" value="${user.username}" class="form-control form-control-line" readonly>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-12">New Password</label>
                                                <div class="col-md-12">
                                                    <input type="password" id="password" name="password" value="" class="form-control form-control-line "required>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-12">Position</label>
                                                <div class="col-md-12">
                                                    <input type="text" name="position" value="${user.position}" class="form-control form-control-line" readonly >
                                                </div>
                                                <div><br></div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="submit" class="btn btn-success" onclick="submitUpdateProfileForm()">Submit</button>
            </div>
        </div>
    </div>
</div>