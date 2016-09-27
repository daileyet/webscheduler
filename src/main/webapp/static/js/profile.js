(function(ctx,$,win){
	//View
	ctx.VIEW = {
		'Link_Useremail':'#useremail-edit-link',
		'Input_Useremail':'#useremail',
		'Span_Useremail':'#useremail-static',
		'Btn_Cancel1':'#btn-cancel1',
		'Btn_Apply1':'#btn-apply1',
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
				$(ctx.VIEW.Link_Useremail).click(function(){
					var $link = $(this);
					$link.parent().hide();
					$(ctx.VIEW.Input_Useremail).removeClass('hidden');
					$(ctx.VIEW.Btn_Apply1).removeClass('hidden');
					$(ctx.VIEW.Btn_Apply1).show();
				});
				/*
				$(ctx.VIEW.Input_Useremail).blur(function(){
					var sVal = $(ctx.VIEW.Input_Useremail).val();
					$(ctx.VIEW.Span_Useremail).text(sVal);
					$(ctx.VIEW.Input_Useremail).addClass('hidden');
					$(ctx.VIEW.Link_Useremail).parent().show();
					$(ctx.VIEW.Btn_Apply1).hide();
				});*/
				$(ctx.VIEW.Btn_Cancel1).click(function(){
					$(ctx.VIEW.Input_Useremail).addClass('hidden');
					$(ctx.VIEW.Btn_Apply1).addClass('hidden');
					$(ctx.VIEW.Link_Useremail).parent().show();
				});
			}
			
	}//end of definition
	win.ctx = ctx;
	$(win.document).ready(function(){
		ctx.VIEW.init();
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);