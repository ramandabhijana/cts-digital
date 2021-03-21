<div class="bg-white border-right" id="sidebar-wrapper">
    <div class="sidebar-heading">
        <h4>Test Center Manager</h4>
    </div>

    <div class="list-group list-group-flush">
        <a href="#" class="list-group-item list-group-item-action ">Dashboard</a>
        <a href="/manager/recordtester" class="list-group-item list-group-item-action ${activeIndex?contains("1")?then("active", " ")}">Record Tester</a>
        <a href="/manager/managekit" class="list-group-item list-group-item-action ${activeIndex?contains("2")?then("active", " ")}">Manage Test Kit Stock</a>
        <a href="/officer/viewreport" class="list-group-item list-group-item-action ${activeIndex?contains("3")?then("active", " ")}">Generate Test Report</a>
    </div>
</div>