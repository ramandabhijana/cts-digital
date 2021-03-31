<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>

<body class="bg-light">
<div class="container-fluid">
    <div class="row justify-content-center mt-5">
        <div class="col-md-10 col-md-offset-4 p-3 shadow-sm bg-white">
            <h2>Test Report</h2>
            <p> <strong>${report.center.name}</strong> <br> <span class="text-secondary">${report.center.address}</span> </p>
            <p>Report generated on ${report.date}</p>
        </div>

        <div class="col-md-10 col-md-offset-4 p-3 shadow-sm bg-white">
            <div class="card-deck">
                <div class="card border-primary mb-3">
                    <div class="card-header list-group-item list-group-item-primary">Total</div>
                    <div class="card-body text-primary">
                        <h1 class="card-title">${report.total}</h1>
                        <p class="card-text">Tests</p>
                    </div>
                </div>
                <div class="card border-danger mb-3">
                    <div class="card-header list-group-item list-group-item-danger">Reactive</div>
                    <div class="card-body text-danger">
                        <h1 class="card-title">${report.positiveNumber}</h1>
                        <p class="card-text">Patients</p>
                    </div>
                </div>
                <div class="card border-success mb-3">
                    <div class="card-header list-group-item list-group-item-success">Non Reactive</div>
                    <div class="card-body text-success">
                        <h1 class="card-title">${report.negativeNumber}</h1>
                        <p class="card-text">Patients</p>
                    </div>
                </div>
            </div>

        </div>

    </div>
    <div class="row justify-content-center my-3">
        <div class="col-md-10 col-md-offset-4 table-responsive shadow-sm p-3 bg-white">
            <h5>History</h5>
            <div class="btn-group btn-group-toggle my-3" data-toggle="buttons">
                <label class="btn btn-outline-primary" onclick="filterSelection('None')">
                    <input type="radio"
                           name="options"
                           id="option1"
                           autocomplete="off"> None
                </label>
                <label class="btn btn-outline-primary" onclick="filterSelection('R')">
                    <input type="radio"
                           name="options"
                           id="option2"
                           autocomplete="off"> Returnee
                </label>
                <label class="btn btn-outline-primary" onclick="filterSelection('Q')">
                    <input type="radio"
                           name="options"
                           id="option3"
                           autocomplete="off"> Quarantined
                </label>
                <label class="btn btn-outline-primary" onclick="filterSelection('C')">
                    <input type="radio"
                           name="options"
                           id="option3"
                           autocomplete="off"> Close Contact
                </label>
                <label class="btn btn-outline-primary" onclick="filterSelection('I')">
                    <input type="radio"
                           name="options"
                           id="option4"
                           autocomplete="off"> Infected
                </label>
                <label class="btn btn-outline-primary" onclick="filterSelection('S')">
                    <input type="radio"
                           name="options"
                           id="option5"
                           autocomplete="off"> Suspected
                </label>
                <label class="btn btn-outline-primary" onclick="filterSelection('Positive')">
                    <input type="radio"
                           name="options"
                           id="option6"
                           autocomplete="off"> Positive
                </label>
                <label class="btn btn-outline-primary" onclick="filterSelection('Negative')">
                    <input type="radio"
                           name="options"
                           id="option6"
                           autocomplete="off"> Negative
                </label>
                <label class="btn btn-outline-primary" onclick="filterSelection('Complete')">
                    <input type="radio"
                           name="options"
                           id="option7"
                           autocomplete="off"> Complete
                </label>
                <label class="btn btn-outline-primary" onclick="filterSelection('Pending')">
                    <input type="radio"
                           name="options"
                           id="option8"
                           autocomplete="off"> Pending
                </label>
            </div>

            <table class="table table-hover table-bordered">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Test Date</th>
                    <th scope="col">Tester Username</th>
                    <th scope="col">Patient Username</th>
                    <th scope="col">Patient Name</th>
                    <th scope="col">Test Kit</th>
                    <th scope="col">Result Date</th>
                    <th scope="col">Result</th>
                </tr>
                </thead>
                <tbody>
                <#if report.tests?? && (report.tests?size > 0)>
                    <#list report.tests as test>
                        <tr class="filterTr ${test.patientType[0]} ${test.status.stringValue} ${test.resultString}">
                            <th scope="row">${test.id}</th>
                            <td>${test.testDate}</td>
                            <td>${test.testerUsername}</td>
                            <td>${test.patientUsername}</td>
                            <td>${test.patientName}</td>
                            <td>ID:${test.kitId} - ${test.kitName}</td>
                            <td>${test.resultDateString}</td>
                            <td>${test.resultString}</td>
                        </tr>
                    </#list>
                </#if>
                </tbody>
            </table>

        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

<script>
    function filterSelection(c) {
        var x, i;
        x = $(".filterTr")
        if (c == "None") c = ""
        for (i = 0; i < x.length; ++i) {
            addClass(x[i], "d-none")
            if (x[i].className.indexOf(c) > -1)
                removeClass(x[i], "d-none")
        }
    }

    function addClass(element, name) {
        var i, arr1, arr2;
        arr1 = element.className.split(" ");
        arr2 = name.split(" ");
        for (i = 0; i < arr2.length; i++) {
            if (arr1.indexOf(arr2[i]) == -1) {
                element.className += " " + arr2[i];
            }
        }
    }

    function removeClass(element, name) {
        var i, arr1, arr2;
        arr1 = element.className.split(" ");
        arr2 = name.split(" ");
        for (i = 0; i < arr2.length; i++) {
            while (arr1.indexOf(arr2[i]) > -1) {
                arr1.splice(arr1.indexOf(arr2[i]), 1);
            }
        }
        element.className = arr1.join(" ");
    }
</script>
</body>

</html>

