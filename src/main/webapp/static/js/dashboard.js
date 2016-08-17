(function(ctx, $, win) {
	ctx.VIEW = {
		init : function() {
			ctx.VIEW.components.init();
		}
	};
	ctx.VIEW.components = {
		init : function() {
			ctx.VIEW.components.chart1.init();
			ctx.VIEW.components.chart2.init();
		}
	};
	ctx.VIEW.components.chart1 = {
		init : function() {
			var chartObj = this;
			chartObj.ctx = $("#chart1");
			chartObj.chart = new Chart(chartObj.ctx, {
				type : 'doughnut',
				data : {
					labels : [ "Build-in", "Custom" ],
					datasets : [ {
						data : [ 0, 0 ],
						backgroundColor : [ "#FF6384", "#36A2EB" ],
						hoverBackgroundColor : [ "#FF6384", "#36A2EB" ]
					} ]
				},
				options : {
					animation : {
						animateScale : true
					}
				}
			});
			chartObj.loadDataAndUpdate();
		},
		loadDataAndUpdate : function() {
			var chartObj = this;
			$.ajax({
				type : "post",
				url : chartObj.ctx.data("link"),
				async : true,
				dataType : "jsonp",
				success : function(data) {
					if (data.type == "SUCESS") {
						chartObj.chart.data.datasets[0].data = data.other;
						chartObj.chart.update();
					}
				}
			});
		}
	};

	ctx.VIEW.components.chart2 = {
		init : function() {
			var chartObj = this;
			chartObj.ctx = $("#chart2");
			chartObj.chart = new Chart(chartObj.ctx, {
				type : 'horizontalBar',
				data : {
					labels : [ "Unschedule", "Scheduled", "Running",
							"Completed", "Interrupted", "Invalid" ],
					datasets : [ {
						data : [ 0, 0, 0, 0, 0, 0],
						backgroundColor : [ 'rgba(255, 99, 132, 0.2)',
								'rgba(54, 162, 235, 0.2)',
								'rgba(255, 206, 86, 0.2)',
								'rgba(75, 192, 192, 0.2)',
								'rgba(153, 102, 255, 0.2)',
								'rgba(255, 159, 64, 0.2)' ],
						borderColor : [ 'rgba(255,99,132,1)',
								'rgba(54, 162, 235, 1)',
								'rgba(255, 206, 86, 1)',
								'rgba(75, 192, 192, 1)',
								'rgba(153, 102, 255, 1)',
								'rgba(255, 159, 64, 1)' ],
//						hoverBackgroundColor : [ "#FF6384", "#36A2EB" ],
						borderWidth : 1,
					} ]
				},
				options : {
					
					legend:{
						display: false
					}
				}
			});
			chartObj.loadDataAndUpdate();
		},
		loadDataAndUpdate : function() {
			var chartObj = this;
			$.ajax({
				type : "post",
				url : chartObj.ctx.data("link"),
				async : true,
				dataType : "jsonp",
				success : function(data) {
					if (data.type == "SUCESS") {
						chartObj.chart.data.datasets[0].data = data.other;
						chartObj.chart.update();
					}
				}
			});
		}
	};


	ctx.MODEL = {};
	ctx.CONTROLLER = {
		init : function() {
		}
	};
	win.ctx = ctx;
	$(win.document).ready(function() {
		ctx.VIEW.init();
		ctx.CONTROLLER.init();
	});
})({}, jQuery, window);