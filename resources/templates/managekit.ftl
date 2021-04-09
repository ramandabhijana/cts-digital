<#import "managercommon/bootstrap.ftl" as b>

<@b.page>
    <div class="row mt-5 mx-2">

        <#--        Current Test Kits-->
        <div class="col-md-4 order-md-2 mb-4">

            <h6 class="d-flex justify-content-between align-items-center mb-3">
                <span class="text-muted">Current Test Kits</span>
                <#if kits?? && (kits?size > 0)>
                    <span class="badge badge-secondary badge-pill">${kits?size}</span>
                </#if>
            </h6>
            <ul id="test-kits" class="list-group mb-3">
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


        <div class="col-md-8 order-md-1">

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

            <div class="card">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="new-kit-tab" data-toggle="tab" href="#newkit" role="tab" aria-controls="newkit" aria-selected="true">New Test Kit</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="new-stock-tab" data-toggle="tab" href="#newstock" role="tab" aria-controls="newstock" aria-selected="true">New Stock</a>
                        </li>
                    </ul>
                </div>

                <div class="tab-content">
                    <div class="tab-pane fade show active" id="newkit" role="tabpanel" aria-labelledby="new-kit-tab">
                        <div class="card-body">
                            <form id="new-kit-form" action="/manager/managekit" method="post">
                                <div class="mb-3">
                                    <label for="username">Name</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="kitName" id="kitName" placeholder="Enter the name of test kit" required>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="username">Stock</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="stock" name="stock" placeholder="Enter the stock of test kit" required>
                                    </div>
                                </div>
                                <button class="btn btn-success btn-lg mt-5"
                                        type="submit"
                                >Submit</button>
                            </form>
                        </div>
                    </div>

                    <div class="tab-pane fade" id="newstock" role="tabpanel" aria-labelledby="new-stock-tab">
                        <div class="card-body">
                            <form id="new-stock-form" action="/manager/managekit" method="post">

                                <div class="mb-3">
                                    <label for="kitid">Test Kit ID</label>
                                    <div class="input-group">
                                        <select class="form-control custom-select" name="selectedtestkitid">
                                            <#if kits?? && (kits?size > 0)>
                                                <#list kits as kit>
                                                    <option value="${kit.id}">${kit.id} (${kit.name})</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="stock">Stock</label>
                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">+</div>
                                        </div>
                                        <input type="text" class="form-control"name="newstock" placeholder="Enter the new stock to be added" required>
                                    </div>
                                </div>
                                <#if kits?? && (kits?size > 0)>
                                    <button class="btn btn-success btn-lg mt-5" type="submit">Submit</button>
                                    <#else>
                                        <button class="btn btn-secondary btn-lg mt-5" type="submit" disabled>No Test Kit</button>
                                </#if>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</@b.page>