<div class="bg-white border-right" id="sidebar-wrapper">
    <div class="sidebar-heading">
        <h4>Patient</h4>
    </div>

    <div class="list-group list-group-flush">
        <a href="/patient/dashboard" class="list-group-item list-group-item-action ${activeIndex?contains("0")?then("active", " ")}">Dashboard</a>
        <a href="/patient/testhistory" class="list-group-item list-group-item-action ${activeIndex?contains("1")?then("active", " ")}">View Tests History</a>
    </div>
</div>