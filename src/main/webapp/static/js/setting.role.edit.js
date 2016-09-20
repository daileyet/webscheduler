(function(ctx,$,win){
	//View
	ctx.VIEW = {
		'Select_Rolemaps':'#rolemaps-sel',
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
				$(ctx.VIEW.Select_Rolemaps).on('changed.bs.select',function(e){
					var selectedOps = $(ctx.VIEW.Select_Rolemaps).selectpicker('val');
					var joinVal = "";
					if(selectedOps){
						joinVal=selectedOps.join(',');
					}
					$(ctx.VIEW.Input_Rolemaps).val(joinVal);
				});
			}
			
	}//end of definition
	win.ctx = ctx;
	$(win.document).ready(function(){
		ctx.VIEW.init();
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);