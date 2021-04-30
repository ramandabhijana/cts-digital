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

    function submitUpdateProfileForm() {
        const password = $('input[type="password"][name="password"]').val();
        if ( !(password === "") && password.length < 6 ) {
            alert('The new password must be at least 6 characters long')
            return
        }
        $('#update-profile-form').submit();
    }
</script>
</body>

</html>