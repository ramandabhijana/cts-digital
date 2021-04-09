<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <title>BIT302 Software Engineering Assignment</title>
</head>

<body>
<nav class="navbar sticky-top navbar-light bg-white border-bottom shadow-sm p-3 px-md-4 mb-3 justify-content-between">
    <h5 class="my-0 mr-md-auto font-weight-normal">CTS Digital</h5>
    <a class="btn btn-outline-primary" name="login-button" href="/login">Log in</a>
</nav>

<main role="main">
    <div class="jumbotron container">
        <h1>Let's connect</h1>
        <p class="lead mb-5">Calling all test center managers: Let's unite in this digital movement. Register your test center to be at the forefront of the COVID-19 testing administration along with many other test centers</p>
        <a class="btn btn-lg btn-primary" href="/manager/signup" name="register-btn" role="button">Register now &raquo;</a>
    </div>

    <div class="py-5 bg-light">
        <div class="container">
            <div class="d-flex align-items-center p-3 mb-3 text-black-50 rounded shadow-sm" style="background-color: #e3f2fd;">
                <div class="lh-100">
                    <h6 class="mb-0 text-primary lh-100">Our Test Centers</h6>
                </div>
            </div>
            <div class="mb-5 p-3 bg-white rounded shadow-sm">
                <#if centers?? && (centers?size > 0)>
                    <#list centers as center>
                        <div class="media text-muted pt-3">
                            <img data-src="holder.js/32x32?theme=thumb&bg=007bff&fg=007bff&size=1" alt="" class="mr-2 rounded">
                            <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
                                <strong class="d-block text-gray-dark">${center.name}</strong>
                                Managed by ${center.managerNames[0]}
                            </p>
                        </div>
                    </#list>
                    <#else>
                        <div class="media text-muted pt-3">
                            <p class="media-body pb-3 mb-0 small lh-125 ">
                                <strong class="d-block text-gray-dark">NO DATA AVAILABLE</strong>
                            </p>
                        </div>
                </#if>
            </div>

            <div class="d-flex align-items-center p-3 my-3 text-black-50 rounded shadow-sm" style="background-color: #e3f2fd;">
                <div class="lh-100">
                    <h6 class="mb-0 text-primary lh-100">Covid-19 Tests</h6>
                </div>
            </div>
            <div class="my-3 p-3 bg-white rounded shadow-sm">
                <!-- Empty placeholder -->
                <#if centers?? && (centers?size > 0)>
                    <#list tests as test>
                        <div class="media text-muted pt-3">
                            <div class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
                                <div class="d-flex justify-content-between align-items-center w-100">
                                    <strong class="text-gray-dark">${test.patientName}</strong>
                                    <#assign status= test.status.stringValue >
                                    <#if status?contains("Complete")>
                                        <span class="badge badge-pill badge-success">Completed</span>
                                        <#else>
                                            <span class="badge badge-pill badge-warning">Pending</span>
                                    </#if>
                                </div>
                                <span class="d-block">${test.patientUsername}</span>
                                <span class="d-block pt-3">Tested on ${test.testDate}</span>
                            </div>
                        </div>
                    </#list>
                    <#else>
                        <div class="media text-muted pt-3">
                            <p class="media-body pb-3 mb-0 small lh-125 ">
                                <strong class="d-block text-gray-dark">NO DATA AVAILABLE</strong>
                            </p>
                        </div>
                </#if>

            </div>
        </div>
    </div>
</main>

<footer class="bg-dark" style="padding: 2.5rem 0;
    color: #999;
    text-align: center;
    border-top: .05rem solid #e5e5e5;
    ">
    <p>Software Engineering Project by <br><span class="text-light">Abhijana Agung Ramanda</span> and <span class="text-light">Aldo Wisnu Pratama</span>.</p>

</footer>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>

</html>