(function(ctx,$,win){
	//View
	ctx.VIEW = {
		'Select_Rolemaps_path':'#rolemaps-sel-path',
		'Select_Rolemaps_include':'#rolemaps-sel-include',
		'Input_Rolemaps':'#rolemaps',
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
				this.prepared();
				this.bindEventListener();
			},
			prepared:function(){
				
			},
			bindEventListener:function(){
				var _this = this;
				$(ctx.VIEW.Select_Rolemaps_path).on('changed.bs.select',function(e){
					$(ctx.VIEW.Input_Rolemaps).val(_this.getJoinSelectedVal());
				});
				$(ctx.VIEW.Select_Rolemaps_include).on('changed.bs.select',function(e){
					$(ctx.VIEW.Input_Rolemaps).val(_this.getJoinSelectedVal());
				});
			},
			getJoinSelectedVal:function(){
				var joinVal = "";
				var selectedOps = $(ctx.VIEW.Select_Rolemaps_include).selectpicker('val');
				if(selectedOps){
					joinVal=selectedOps.join(',');
				}
				selectedOps = $(ctx.VIEW.Select_Rolemaps_path).selectpicker('val');
				if(selectedOps && selectedOps.length>0){
					if(joinVal.length>0){
						joinVal = joinVal + ',' +selectedOps.join(',');
					}else{
						joinVal = selectedOps.join(',');
					}
				}
				return joinVal;
			}
			
	}//end of definition
	win.ctx = ctx;
	$(win.document).ready(function(){
		ctx.VIEW.init();
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);