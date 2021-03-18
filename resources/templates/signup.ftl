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

<body class="bg-light">
<nav class="navbar sticky-top navbar-light bg-white border-bottom shadow-sm p-3 px-md-4 mb-3 justify-content-between">
    <h5 class="my-0 mr-md-auto font-weight-normal">CTS Digital</h5>
</nav>

<div class="container-fluid">
    <div class="row justify-content-center mt-5">
        <div class="col-md-5 col-md-offset-4">
            <div class="panel panel-default p-3 shadow bg-white">
                <div class="panel-heading pb-5">
                    <h3 class="panel-title">Create New Manager</h3>
                </div>
                <div class="panel-body">
                    <form action="/manager/signup" method="POST">
                        <div class="form-group">
                            <label for="firstName">First name</label>
                            <input type="text"
                                   name="firstName"
                                   class="form-control ${error?contains("First")?then("is-invalid", " ")}"
                                   id="firstName">
                            <div class="invalid-feedback">
                                ${error}
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last name</label>
                            <input type="text" name="lastName" class="form-control" id="lastName">
                        </div>
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text"
                                   name="username"
                                   class="form-control ${error?contains("Username")?then("is-invalid", " ")}"
                                   id="username">
                            <div class="invalid-feedback">
                                ${error}
                            </div>
                        </div>
                        <div class="form-group pb-3">
                            <label for="password">Password</label>
                            <input type="password"
                                   name="password"
                                   class="form-control ${error?contains("Password")?then("is-invalid", " ")}"
                                   id="password">
                            <div class="invalid-feedback">
                                ${error}
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary btn-block">Sign Up</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<footer class="bg-dark mt-5" style="padding: 2.5rem 0;
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