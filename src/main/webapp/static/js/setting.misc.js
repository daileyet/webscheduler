(function(ctx,$,win){
	//View
	ctx.VIEW = {
		'Select_Logger_level':'#logger_level',
		'Input_Remote_monitor':'#remote_monitor',
		init:function(){
			this.components.init();
		}
	};
	ctx.VIEW.components = {
			init:function(){
			}
	}
	
	//Model
	ctx.MODEL = {
			
	};
	
	//Controller
	ctx.CONTROLLER = {
			init:function(){
				$(ctx.VIEW.Input_Remote_monitor).bootstrapSwitch();
				this.prepared();
				this.bindEventListener();
			},
			prepared:function(){
				
			},
			bindEventListener:function(){
				var _this = this;
				$(ctx.VIEW.Select_Logger_level).on('changed.bs.select',function(e){
					var sNewLevel = $(ctx.VIEW.Select_Logger_level).selectpicker('val');
					_this.updateLoggerLevel(sNewLevel);
				});
				$(ctx.VIEW.Input_Remote_monitor).on('switchChange.bootstrapSwitch', function(event, state) {
					_this.updateRemotable(state);
				});
			},
			updateLoggerLevel:function(sNewLevel){
				var $level = $(ctx.VIEW.Select_Logger_level);
				var _this = this;
				$.ajax({
					type: "post",
					url: $level.data("link"),
					async: false,
					data: {
						"logger_level": sNewLevel
					},
					dataType: "jsonp",
					success: function(data) {
						_this.notifyResult($level.parents('dd').prev(),data);
					},
					error: function(jqXHR,textStatus,errorThrown ) {
						_this.notifyResult($level.parents('dd').prev(),{type:'ERROR',msg:errorThrown});
					}
				});
			},
			updateRemotable:function(bNewState){
				var $remotable = $(ctx.VIEW.Input_Remote_monitor);
				var _this = this;
				$.ajax({
					type: "post",
					url: $remotable.data("link"),
					async: false,
					data: {
						"enableRemote": bNewState
					},
					dataType: "jsonp",
					success: function(data) {
						_this.notifyResult($remotable.parents('dd').prev(),data);
					},
					error: function(jqXHR,textStatus,errorThrown ) {
						_this.notifyResult($remotable.parents('dd').prev(),{type:'ERROR',msg:errorThrown});
					}
				});
			},
			notifyResult:function($dt,resultObj){
				var rst = resultObj || {type:'ERROR',msg:''};
				rst.msg = rst.msg || '' ;
				$dt.find('.misc-result').remove();
				if(rst.type == 'SUCESS'){
					$dt.append('<span class="misc-result text-success glyphicon glyphicon-ok-circle" aria-hidden="true"></span>')
				}else if(rst.type == 'ERROR'){
					$dt.append('<span class="misc-result text-warning glyphicon glyphicon-warning-sign" title="'+rst.msg+'" aria-hidden="true"></span>')
				}
			}
			
	}//end of definition
	win.ctx = ctx;
	$(win.document).ready(function(){
		ctx.VIEW.init();
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);