<#import "testercommon/bootstrap.ftl" as b>

<@b.page>
    <div class="row justify-content-center my-5">
        <div class="col-md-10 col-md-offset-5 table-responsive-md shadow-sm bg-white p-3">
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
            <h5>Pending Tests</h5>
            <table class="table table-hover table-bordered">
                <caption>List of uncompleted tests</caption>
                <thead class="thead-dark">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Test Date</th>
                    <th scope="col">Patient Username</th>
                    <th scope="col">Patient Full Name</th>
                    <th scope="col">Test Kit</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <#if tests?? && (tests?size > 0)>
                    <#list tests as test>
                        <tr>
                            <th scope="row">${test.id}</th>
                            <td>${test.testDate}</td>
                            <td>${test.patientUsername}</td>
                            <td>${test.patientName}</td>
                            <td>ID:${test.kitId} - ${test.kitName}</td>
                            <td>
                                <div class="row justify-content-center">
                                    <img width="20" height="20" src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDQ3Ny44NzMgNDc3Ljg3MyIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMiIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgY2xhc3M9IiI+PGc+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+Cgk8Zz4KCQk8cGF0aCBkPSJNMzkyLjUzMywyMzguOTM3Yy05LjQyNiwwLTE3LjA2Nyw3LjY0MS0xNy4wNjcsMTcuMDY3VjQyNi42N2MwLDkuNDI2LTcuNjQxLDE3LjA2Ny0xNy4wNjcsMTcuMDY3SDUxLjIgICAgYy05LjQyNiwwLTE3LjA2Ny03LjY0MS0xNy4wNjctMTcuMDY3Vjg1LjMzN2MwLTkuNDI2LDcuNjQxLTE3LjA2NywxNy4wNjctMTcuMDY3SDI1NmM5LjQyNiwwLDE3LjA2Ny03LjY0MSwxNy4wNjctMTcuMDY3ICAgIFMyNjUuNDI2LDM0LjEzNywyNTYsMzQuMTM3SDUxLjJDMjIuOTIzLDM0LjEzNywwLDU3LjA2LDAsODUuMzM3VjQyNi42N2MwLDI4LjI3NywyMi45MjMsNTEuMiw1MS4yLDUxLjJoMzA3LjIgICAgYzI4LjI3NywwLDUxLjItMjIuOTIzLDUxLjItNTEuMlYyNTYuMDAzQzQwOS42LDI0Ni41NzgsNDAxLjk1OSwyMzguOTM3LDM5Mi41MzMsMjM4LjkzN3oiIGZpbGw9IiMwMjc1ZDgiIGRhdGEtb3JpZ2luYWw9IiMwMDAwMDAiIHN0eWxlPSIiIGNsYXNzPSIiPjwvcGF0aD4KCTwvZz4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgoJPGc+CgkJPHBhdGggZD0iTTQ1OC43NDIsMTkuMTQyYy0xMi4yNTQtMTIuMjU2LTI4Ljg3NS0xOS4xNC00Ni4yMDYtMTkuMTM4Yy0xNy4zNDEtMC4wNS0zMy45NzksNi44NDYtNDYuMTk5LDE5LjE0OUwxNDEuNTM0LDI0My45MzcgICAgYy0xLjg2NSwxLjg3OS0zLjI3Miw0LjE2My00LjExMyw2LjY3M2wtMzQuMTMzLDEwMi40Yy0yLjk3OSw4Ljk0MywxLjg1NiwxOC42MDcsMTAuNzk5LDIxLjU4NSAgICBjMS43MzUsMC41NzgsMy41NTIsMC44NzMsNS4zOCwwLjg3NWMxLjgzMi0wLjAwMywzLjY1My0wLjI5Nyw1LjM5My0wLjg3bDEwMi40LTM0LjEzM2MyLjUxNS0wLjg0LDQuOC0yLjI1NCw2LjY3My00LjEzICAgIGwyMjQuODAyLTIyNC44MDJDNDg0LjI1LDg2LjAyMyw0ODQuMjUzLDQ0LjY1Nyw0NTguNzQyLDE5LjE0MnogTTQzNC42MDMsODcuNDE5TDIxMi43MzYsMzA5LjI4NmwtNjYuMjg3LDIyLjEzNWwyMi4wNjctNjYuMjAyICAgIEwzOTAuNDY4LDQzLjM1M2MxMi4yMDItMTIuMTc4LDMxLjk2Ny0xMi4xNTgsNDQuMTQ1LDAuMDQ0YzUuODE3LDUuODI5LDkuMDk1LDEzLjcyLDkuMTIsMjEuOTU1ICAgIEM0NDMuNzU0LDczLjYzMSw0NDAuNDY3LDgxLjU3NSw0MzQuNjAzLDg3LjQxOXoiIGZpbGw9IiMwMjc1ZDgiIGRhdGEtb3JpZ2luYWw9IiMwMDAwMDAiIHN0eWxlPSIiIGNsYXNzPSIiPjwvcGF0aD4KCTwvZz4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8L2c+PC9zdmc+"
                                    />
                                    <a href="#update_modal" class="text-primary" data-toggle="modal" data-test-id="${test.id}">Update</a>
                                </div>
                            </td>
                        </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
        </div>
    </div>

    <div class="modal fade" id="update_modal">
        <div class="modal-dialog">
            <div class="modal-content ">
                <div class="modal-header ">
                    <h5 id="test-modal-header" class="modal-title"></h5>
                </div>
                <div class="modal-body ">
                    <form id="update-result-form" action="/tester/updatetestresult" method="POST">
                        <input type="hidden" name="testId" value="" />
                        <div class="form-group">
                            <label for="testResult">Result</label>

                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="testResult" id="positiveResult" value="0">
                                <label class="form-check-label" for="positiveResult">
                                    Positive
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="testResult" id="negativeResult" value="1" checked>
                                <label class="form-check-label" for="negativeResult">
                                    Negative
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="resultDate">Issued Date</label>
                            <input type="text" name="resultDate" class="form-control" value="${dateNow}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="displayName">Status</label>
                            <input type="text" name="displayName" class="form-control" value="Completed" readonly>
                        </div>
                    </form>
                </div>
                <div class="modal-footer ">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-success"  data-dismiss="modal" onclick="submitUpdateResultForm()">Submit</button>
                </div>
            </div>
        </div>
    </div>

</@b.page>