(function(ctx,$,win){
	//View
	ctx.VIEW = {
		"Link_Remove":"a[data-confirm='true']",
		"Dialog_Confirm":"#confirm-delete",
		"Placeholder_Delname":"[data-title='username']"
	};
	
	//Model
	ctx.MODEL = {
			
	};
	
	//Controller
	ctx.CONTROLLER = {
			init:function(){
				this.bindEventListener();
			},
			bindEventListener:function(){
				$(ctx.VIEW.Dialog_Confirm).on('show.bs.modal', function(e) {
					var $link = $(e.relatedTarget);
		            $(this).find('.btn-ok').attr('href', $link.data('href'));
		            var sDelNmae = $link.parents('tr').find(ctx.VIEW.Placeholder_Delname).text();
		            $(this).find(ctx.VIEW.Placeholder_Delname).text(sDelNmae);
		        });
			}
			
	}//end of definition
	$(win.document).ready(function(){
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);