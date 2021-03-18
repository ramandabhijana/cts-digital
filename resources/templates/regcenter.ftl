<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <title>Hello, world!</title>
</head>

<body class="bg-light">
<nav class="navbar sticky-top navbar-light bg-white border-bottom shadow-sm p-3 px-md-4 mb-3 justify-content-between">
    <h5 class="my-0 mr-md-auto font-weight-normal">CTS Digital</h5>
</nav>

<div class="container-fluid">
    <div class="row justify-content-center mt-5">
        <div class="col-md-5 col-md-offset-4">
            <div class="alert alert-info" role="alert">
                <h4 class="alert-heading">Almost done!</h4>
                <p>Your account has been created. Now let's fill in the following form to complete the registration.</p>
            </div>
            <div class="panel panel-default p-3 shadow bg-white">
                <div class="panel-body">
                    <form action="/manager/regcenter" method="POST">
                        <#if error??>
                            <p class="text-danger">${error}</p>
                        </#if>
                        <input type="hidden" name="date" value="${date?c}">
                        <input type="hidden" name="code" value="${code}">
                        <div class="form-group">
                            <label for="centerName">Name</label>
                            <input type="text" name="centerName" class="form-control" id="centerName" placeholder="Enter name of your test center">
                        </div>
                        <div class="form-group pb-5">
                            <label for="centerAddress">Address</label>
                            <input type="text" name="centerAddress" class="form-control" id="centerAddress" placeholder="Enter address of your test center">
                        </div>
                        <button type="submit" class="btn btn-primary btn-block">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<footer class="bg-dark fixed-bottom" style="padding: 2.5rem 0;
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