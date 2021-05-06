<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <link rel="stylesheet" href="viewTestHistory.css">
</head>

<body class="bg-light">
<div class="page-wrapper" style="min-height: 586px">

    <div class="container-fluid mt-5">
        <div class="card border-primary shadow-sm mb-4">
            <div class="card-header list-group-item list-group-item-primary py-3">
                <h6 class="m-0 font-weight-bold" style="text-align: center;">TEST HISTORY</h6>
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Date</th>
                        <th scope="col">Test Center</th>
                        <th scope="col">Tester</th>
                        <th scope="col">Test Kit</th>
                        <th scope="col">Result</th>
                        <th scope="col">Result Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if tests?? && (tests?size > 0)>
                        <#list tests as test>
                            <tr>
                                <td>${test["test"].id}</td>
                                <td>${test["test"].testDate}</td>
                                <td>${test["center"]}</td>
                                <td>${test["test"].testerUsername}</td>
                                <td>${test["test"].kitName}</td>

                                <td>${test["test"].resultString}</td>
                                <td>${test["test"].resultDateString}</td>
                            </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>

            </div>

        </div>
    </div>

</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
</body>

</html>