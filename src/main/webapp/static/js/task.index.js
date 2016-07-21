(function(ctx,$,win){
	//View
	ctx.VIEW = {
		"Link_Remove":"a[data-confirm='true']",
		"Dialog_Confirm":"#confirm-delete"
	};
	
	//Model
	ctx.MODEL = {
			
	};
	
	//Controller
	ctx.CONTROLLER = {
			init:function(){
				$(ctx.VIEW.Link_Remove).each(function(){
					var $link = $(this);
					$link.data('href',$link.attr('href'));
					$link.attr('href','#');
				});
				
				this.bindEventListener();
			},
			bindEventListener:function(){
				$(ctx.VIEW.Dialog_Confirm).on('show.bs.modal', function(e) {
					var $link = $(e.relatedTarget);
		            $(this).find('.btn-ok').attr('href', $link.data('href'));
		            var sTaskNmae = $link.parents('tr').find('[data-title="taskname"]').text();
		            $(this).find('[data-title="taskname"]').text(sTaskNmae);
		        });
			}
			
	}//end of definition
	$(win.document).ready(function(){
		ctx.CONTROLLER.init();
	});
})({} ,jQuery,window);