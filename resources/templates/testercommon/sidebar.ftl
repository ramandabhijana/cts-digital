<div class="bg-white border-right" id="sidebar-wrapper">
    <div class="sidebar-heading">
        <h4>Test Center Tester</h4>
    </div>

    <div class="list-group list-group-flush">
        <a href="#" class="list-group-item list-group-item-action ">Dashboard</a>
        <a href="/tester/recordtest" class="list-group-item list-group-item-action ${activeIndex?contains("1")?then("active", " ")}">Record New Test</a>
        <a href="/tester/updatetestresult" class="list-group-item list-group-item-action ${activeIndex?contains("2")?then("active", " ")}">Update Test Result</a>
        <a href="/officer/viewreport" class="list-group-item list-group-item-action ${activeIndex?contains("3")?then("active", " ")}">Generate Test Report</a>
    </div>
</div>