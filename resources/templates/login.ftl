<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <title>Login Page</title>
    <style>
        * {
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }

        body {
            background-color: #eff8ff;
        }

        .row {
            background-color: white;
            border-radius: 30px;
        }

        .breadcrumb {
            background-color: white;
        }

        .btn1 {
            border: none;
            outline: none;
            height: 50px;
            width: 100%;
            color: white;
            border-radius: 4px;
            font-weight: bold;
        }

        .btn1:hover {
            background-color: white;
            border: 0.1px solid;
            color: #66b3ff;
        }
    </style>
</head>

<body>
<section class="Form my-5 mx-5">
    <div class="container">

        <div class="row no-gutters shadow">
            <div class="col-lg-5">
                <img src="/static/covidtesting.png" class="login card-img" alt="covidtesting">
            </div>
            <div class="col-lg-7 py-4 px-5">

                <h1 class="font-weight-bold">CTS Digital</h1>
                <h5 class="pb-4">Log into your account</h5>
                <form action="/login" method="POST">
                    <#if error??>
                        <p class="text-danger">${error}</p>
                    </#if>
                    <div class="form-row">
                        <div class="col-lg-7">
                            <input name="username" placeholder="Username" class="form-control my-3 p-4 ${error?contains("Username")?then("is-invalid ", " ")}" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-lg-7">
                            <input name="password" type="password" placeholder="Password" class="form-control my-3 p-4 ${error?contains(" Password ")?then("is-invalid ", " ")}" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-lg-7">
                            <button type="submit" class="btn1 btn-primary mt-3 mb-5">Login</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>
</section>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>

</html>