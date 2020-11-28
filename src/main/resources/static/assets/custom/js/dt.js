$(document).ready(function() {
	var table = $('#category-view, #topic-view, #question-view', '#employee-view', '#basicSalary-view', '#salary-view').DataTable({
		"order" : [ [ 0, "asc" ] ],
		 "scrollX": true
	});
});