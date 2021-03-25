<#macro page>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <style>
            .list-group-item.active {
                color:black;
                background-color: #dfdfdf;
                border-color: #dfdfdf;
            }
            a.list-group-item:hover {
                background-color: #dfdfdf;
                border-color: #dfdfdf;
            }
            #wrapper {
                overflow-x: hidden;
            }
            #sidebar-wrapper {
                min-height: 90vh;
                margin-left: -15rem;
                -webkit-transition: margin .25s ease-out;
                -moz-transition: margin .25s ease-out;
                -o-transition: margin .25s ease-out;
                transition: margin .25s ease-out;
            }
            #sidebar-wrapper .sidebar-heading {
                padding: 0.875rem 1.25rem;
                font-size: 1.2rem;
            }
            #sidebar-wrapper .list-group {
                width: 15rem;
            }
            #page-content-wrapper {
                min-width: 100vw;
            }
            #wrapper.toggled #sidebar-wrapper {
                margin-left: 0;
            }
            @media (min-width: 768px) {
                #sidebar-wrapper {
                    margin-left: 0;
                }
                #page-content-wrapper {
                    min-width: 0;
                    width: 100%;
                }

                #wrapper.toggled #sidebar-wrapper {
                    margin-left: -15rem;
                }
            }
        </style>
    </head>

    <body class="bg-white">
    <div class="d-flex" id="wrapper">
        <!-- Sidebar -->
        <#include "sidebar.ftl">

        <!-- Page Content -->
        <div id="page-content-wrapper" class="bg-light">
            <#include "../managercommon/navbar.ftl">

            <div class="container-fluid">
                <#nested>
            </div>
        </div>
    </div>

    <footer class="bg-dark" style="padding: 2.5rem 0;
    color: #999;
    text-align: center;
    border-top: .05rem solid #e5e5e5;
    ">
        <p>Software Engineering Project by <br><span class="text-light">Abhijana Agung Ramanda</span> and <span class="text-light">Aldo Wisnu Pratama</span>.</p>

    </footer>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script>
        $("#menu-toggle").click(function(e) {
            e.preventDefault();
            $("#wrapper").toggleClass("toggled");
        });
    </script>
    <script>
        $('#update_modal').on('show.bs.modal', function(e) {
            var testId = $(e.relatedTarget).data('test-id');
            $(e.currentTarget).find('input[name="testId"]').val(testId);
            $('#test-modal-header').text('Update Test with ID ' + testId)
        });

        function submitUpdateResultForm() {
            $('#update-result-form').submit();
        }
    </script>
    </body>

    </html>

</#macro>